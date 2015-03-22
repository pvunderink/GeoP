package peppi.novum.geop;

import java.io.File;

import peppi.novum.geop.gui.MainWindow;
import peppi.novum.geop.network.NetworkManager;

public class Main {

	public static String VERSION = "Beta 1.3";
	public static String TITLE = "GeoP";
	public static String FILE_PATH = System.getProperty("user.home") + File.separator + "GeoP" + File.separator;
	public static MainWindow MAIN_WINDOW;
	public static NetworkManager NETWORK;

	public static void main(String[] args) {
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}

		new Thread("Startup") {
			public void run() {
				MAIN_WINDOW = new MainWindow();
			}
		}.start();
	}

}