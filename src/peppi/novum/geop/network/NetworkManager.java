package peppi.novum.geop.network;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import peppi.novum.geop.Main;
import peppi.novum.geop.gui.DownloadWindow;
import peppi.novum.geop.gui.MessageWindow;
import peppi.novum.geop.util.PacketDecoder;
import peppi.novum.geop.util.SaveReader;

public class NetworkManager {

	InetAddress SERVER_IP;
	int SERVER_PORT;

	String IDENTIFIER = "/%GeoP%/";
	String PACKET_END = "/%END%/";
	String ARG_SEPARATOR = "/%ARG%/";

	String DIR_SEPARATOR = "/%DIR%/";
	String FILE_SEPARATOR = "/%FILE%/";

	// Receive
	String PRE_FILE_SEND = "/%PRE%/";
	String FILE_SEND = "/%SEND%/";
	String PRE_FILE_LIST = "/%PLF%/";
	String CONFIRM_LIST_FILES = "/%CONLF%/";
	String FILE_LIST = "/%LF%/";
	String CONFIRM_PING = "/%CONPING%/";

	// Send
	String LIST_FILES = "/%LF%/";
	String REQUEST_FILE = "/%RF%/";
	String CONFIRM_REQUEST = "/%CR%/";
	String PING = "/%PING%/";

	Charset cs = Charset.forName("UTF-8");

	DatagramSocket socket;
	boolean running = false;

	Thread receive, send, ping;

	DownloadWindow window;

	boolean pingAnswered = false;
	long expectedFileSize = 0;
	String expectedFile = "";
	
	long expectedListSize = 0;

	public NetworkManager(InetAddress serverIP, int serverPort) {
		try {
			socket = new DatagramSocket(0);
		} catch (SocketException ex) {
			ex.printStackTrace();
		}

		this.SERVER_IP = serverIP;
		this.SERVER_PORT = serverPort;

		running = true;

		receive();
	}

	public void receive() {
		receive = new Thread("Receive") {
			public void run() {
				while (running) {
					byte[] data;

					if (expectedFileSize > 0) {
						data = new byte[(int) expectedFileSize];
					} else if (expectedListSize > 0) {
						data = new byte[(int) expectedListSize];
					} else {
						data = new byte[1024];
					}
					DatagramPacket rawpacket = new DatagramPacket(data, data.length);

					InetAddress ip;
					int port;

					try {
						socket.receive(rawpacket);
						ip = rawpacket.getAddress();
						port = rawpacket.getPort();

						if (!ip.getHostAddress().equals(SERVER_IP.getHostAddress()) || port != SERVER_PORT) {
							continue;
						}

						String packet = new String(rawpacket.getData(), cs).trim();
						packet = packet.split(PACKET_END)[0].trim();

						if (packet.startsWith(IDENTIFIER)) {
							packet = packet.substring(IDENTIFIER.length() - 1, packet.length());
							String[] args = packet.split(ARG_SEPARATOR);

							if (args.length > 1) {
								String identifier = args[1];

								if (identifier.equalsIgnoreCase(CONFIRM_PING)) {
									send(toBytes(LIST_FILES));
								} else if (identifier.equalsIgnoreCase(PRE_FILE_LIST)) {
									System.out.println("Connected to server");

									long listsize = Long.parseLong(args[2]);

									expectedListSize = listsize + 128 + listsize / 2;
									
									send(toBytes(CONFIRM_LIST_FILES));
								} else if (identifier.equalsIgnoreCase(FILE_LIST)) {
									System.out.println("Connected to server");

									String message = args[2];

									HashMap<String, List<String>> files = PacketDecoder.decodeFileListPacket(message);
									
									pingAnswered = true;

									expectedListSize = 0;
									
									if (window != null) {
										window.load(files);
									}
								} else if (identifier.equalsIgnoreCase(PRE_FILE_SEND)) {
									if (args.length > 3) {
										String file = args[2];
										long filesize = Long.parseLong(args[3]);

										expectedFileSize = filesize + 1024 + (filesize / 2);
										expectedFile = file;

										send(toBytes(CONFIRM_REQUEST, file));
									}
								} else if (identifier.equalsIgnoreCase(FILE_SEND)) {
									if (expectedFile != "") {
										if (args.length > 2) {
											File file = new File(Main.FILE_PATH + expectedFile);
											List<String> lines = PacketDecoder.decodeDownload(args[2]);

											try {
												SaveReader.saveDownload(file, lines);
											} catch (Exception e) {
												e.printStackTrace();
											}

											Main.MAIN_WINDOW.loadTree();

											new MessageWindow("Download successful", Color.GREEN);

											expectedFile = "";
										}
									}
									expectedFileSize = 0;
								}
							}
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		};
		receive.start();
	}

	public void openConnection(DownloadWindow window) {
		ping();
		this.window = window;
	}

	public void requestDownload(String file) {
		send(toBytes(REQUEST_FILE, file));
	}

	public void ping() {
		ping = new Thread("Ping") {
			public void run() {
				send(toBytes(PING));

				int count = 0;

				while (!pingAnswered) {
					if (count < 4) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						count++;
					} else {
						window.dispose();
						new MessageWindow("Could not connect to the server", Color.RED);
						break;
					}
				}

				pingAnswered = false;
			}
		};
		ping.start();
	}

	public void send(final byte[] message) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(message, message.length, SERVER_IP, SERVER_PORT);

				try {
					socket.send(packet);
				} catch (IOException ex) {
					new MessageWindow("No internet connection", Color.RED);
				}
			}
		};
		send.start();
	}

	public byte[] toBytes(String identifier, String... args) {
		String msg = new String((IDENTIFIER + ARG_SEPARATOR + identifier).getBytes(cs), cs);

		for (String s : args) {
			new String(s.getBytes(cs), cs);
			msg += ARG_SEPARATOR + s;
		}

		msg += PACKET_END;

		return msg.getBytes(cs);
	}

}
