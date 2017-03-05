package datacleaning.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Operations {
	
	public static String arraylistToString(ArrayList<String> arrayList, String separator) {
		String resultString = "";
		for (String string : arrayList) {
			if (resultString.length() == 0) {
				resultString = string;
			}else {
				resultString = resultString + separator + string;
			}
		}
		return resultString;
	}
	
	public static void writeOneLineToFile(String line, String fileName){
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File(fileName);
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(line);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
