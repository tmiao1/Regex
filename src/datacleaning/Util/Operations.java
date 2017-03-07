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
	
	public static void writeToCsv(ArrayList<ArrayList<String>> finalResult, ArrayList<String> fileReadingResult
			, String fileName, ArrayList<String> targetAttributeResult){
		String attributeSet = Operations.arraylistToString(targetAttributeResult, ",");
		writeOneLineToFile(attributeSet, fileName);
		for (int i = 0; i < finalResult.size(); i++) {
			ArrayList<String> oneLineInFinalResult = finalResult.get(i);
			String onelineInFile = fileReadingResult.get(i);
			String stringToWriteString = "";
			for (String string : oneLineInFinalResult) {
				
				int start = Integer.parseInt(string.split(";")[0]);
				int end = Integer.parseInt(string.split(";")[1]);
				String attributeString = string.split(";")[2];
				if (start != -1) {
					String valueString = onelineInFile.substring(start, end);
					if (stringToWriteString.length() == 0) {
						stringToWriteString = valueString;
					}else {
						stringToWriteString = stringToWriteString + "," + valueString;
					}
				}else{
					stringToWriteString = stringToWriteString + ",";
				}
				
			}
			writeOneLineToFile(stringToWriteString, fileName);
		}
	}
}
