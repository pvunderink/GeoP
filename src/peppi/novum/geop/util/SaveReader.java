package peppi.novum.geop.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import peppi.novum.geop.Main;

public class SaveReader {

	private static Charset cs = Charset.forName("UTF-8");

	public static WordList readWordList(File file) throws IOException {
		List<GeopWord> list = new ArrayList<GeopWord>();

		boolean default_dir = true;

		String label0 = "Begrip";
		String label1 = "Definitie";

		boolean dir0_1 = true;
		boolean dir1_0 = false;
		int mode0_1 = 1;
		int mode1_0 = 0;
		boolean case0_1 = true;
		boolean case1_0 = false;

		if (file != null && file.exists()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			try {
				if (br.ready()) {
					String line = new String(br.readLine().getBytes(cs), cs);

					while (line != null) {
						if (line.startsWith("#")) {
							line = line.substring(1);
							String[] parts = line.split("=", 2);

							if (line.startsWith("default dir")) {
								if (parts.length > 1) {
									try {
										Boolean b = Boolean.parseBoolean(parts[1]);

										default_dir = b;
									} catch (Exception ex) {
									}
								}
							} else if (line.startsWith("label 0")) {
								if (parts.length > 1) {
									label0 = parts[1];
								}
							} else if (line.startsWith("label 1")) {
								if (parts.length > 1) {
									label1 = parts[1];
								}
							} else if (line.startsWith("dir 0-1")) {
								if (parts.length > 1) {
									try {
										Boolean b = Boolean.parseBoolean(parts[1]);

										dir0_1 = b;
									} catch (Exception ex) {
									}
								}
							} else if (line.startsWith("dir 1-0")) {
								if (parts.length > 1) {
									try {
										Boolean b = Boolean.parseBoolean(parts[1]);

										dir1_0 = b;
									} catch (Exception ex) {
									}
								}
							} else if (line.startsWith("case 0-1")) {
								if (parts.length > 1) {
									try {
										Boolean b = Boolean.parseBoolean(parts[1]);

										case0_1 = b;
									} catch (Exception ex) {
									}
								}
							} else if (line.startsWith("case 1-0")) {
								if (parts.length > 1) {
									try {
										Boolean b = Boolean.parseBoolean(parts[1]);

										case1_0 = b;
									} catch (Exception ex) {
									}
								}
							} else if (line.startsWith("mode 0-1")) {
								if (parts.length > 1) {
									try {
										Integer i = Integer.parseInt(parts[1]);

										mode0_1 = i;
									} catch (Exception ex) {
									}
								}
							} else if (line.startsWith("mode 1-0")) {
								if (parts.length > 1) {
									try {
										Integer i = Integer.parseInt(parts[1]);

										mode0_1 = i;
									} catch (Exception ex) {
									}
								}
							}
						} else if (line.contains("=")) {
							String[] parts = line.split("=", 3);
							list.add(new GeopWord(new String(parts[0].getBytes(cs), cs), new String(parts[1].getBytes(cs), cs), new String(parts[2].getBytes(cs), cs)));
						}

						String unsafeLine = br.readLine();

						if (unsafeLine != null) {
							line = new String(unsafeLine.getBytes(cs), cs);
						} else {
							line = null;
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				br.close();
			}
		}

		WordList result = new WordList(list, default_dir, label0, label1, dir0_1, dir1_0, case0_1, case1_0, mode0_1, mode1_0);

		return result;
	}

	public static HashMap<File, List<File>> loadLists() {
		HashMap<File, List<File>> result = new HashMap<File, List<File>>();

		File folder = new File(Main.FILE_PATH + "Lists");

		if (!folder.exists()) {
			folder.mkdirs();
		} else {
			for (File f : folder.listFiles()) {
				if (f.isDirectory()) {
					List<File> files = new ArrayList<File>();

					for (File f1 : f.listFiles()) {
						if (f1.getName().endsWith(".gop")) {
							files.add(f1);
						}
					}

					if (!files.isEmpty()) {
						result.put(f, files);
					}
				}
			}
		}

		return result;
	}

	public static void saveDownload(File file, List<String> lines) throws Exception {
		if (file.exists()) {
			file.delete();
		} else {
			if (file.getParentFile() != null) {
				file.getParentFile().mkdirs();
			}
		}

		file.createNewFile();

		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		for (String line : lines) {
			output.write(new String(line.getBytes(cs), cs) + "\n");
		}
		output.close();
	}
}
