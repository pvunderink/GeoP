package peppi.novum.geop.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PacketDecoder {

	private static String DIR_SEPARATOR = "/%DIR%/";
	private static String FILE_SEPARATOR = "/%FILE%/";
	private static String LINE_SEPARATOR = "/%LINE%/";

	private static Charset cs = Charset.forName("UTF-8");

	public static HashMap<String, List<String>> decodeFileListPacket(String packet) {
		HashMap<String, List<String>> result = new HashMap<String, List<String>>();

		for (String dir : packet.split(DIR_SEPARATOR)) {
			List<String> files = new ArrayList<String>();
			
			if (dir.isEmpty()) {
				continue;
			}
			
			for (String file : dir.split(FILE_SEPARATOR)) {
				if (file.isEmpty() || dir.startsWith(file)) {
					continue;
				}
				files.add(file);
			}
			
			dir = dir.substring(0, dir.indexOf("/%"));
			
			result.put(dir, files);
		}

		return result;
	}
	
	public static List<String> decodeDownload(String download) {
		List<String> result = new ArrayList<String>();
		
		for (String line : download.split(LINE_SEPARATOR)) {
			result.add(new String(line.getBytes(cs), cs));
		}
		
		return result;
	}

}
