package datacleaning.attributecount;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ResultOperation {
	public void combineSameAttribute(){
		
	}
	/**
	 * �����ϴ���������̨���ļ�
	 * @param fileReadingResult
	 * @param regexResult
	 * @param targetAttributeResult
	 * @param fileName
	 * 			������ļ�����������ļ���Ϊ����������̨
	 * @return
	 */
	public static ArrayList<ArrayList<String>> outputReusltToConsole(ArrayList<String> fileReadingResult,
			ArrayList<ArrayList<String>> regexResult,
			ArrayList<String> targetAttributeResult,
			String fileName){
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		//����ȱʧ��������
		int wrongCount = 0;
		int missingCount = 0;	
		writeToTxt(arraylistToString(targetAttributeResult, "    "), fileName);
		//����ԭʼ�ļ�����������ÿһ��
		for (int i = 0; i < fileReadingResult.size(); i++) {
			//ԭʼ�ļ���һ��
			String oneLineString = fileReadingResult.get(i);
			//��������һ��
			ArrayList<String> oneLineRegex = regexResult.get(i);
			//���� �б�־������ƥ��Ľ��, ����23��35��Name��none      none����û�к��κ����Զ���
			ArrayList<String> regexWithFlag = new ArrayList<String>();
			for (String string : oneLineRegex) {
				regexWithFlag.add(string + ";" + "none");
			}
			//�����б�־�����ղ����� ����none��Name
			ArrayList<String> attributeResultWithFlag = new ArrayList<String>();
			for (String string : targetAttributeResult) {
				attributeResultWithFlag.add("none" + ";" + string);
			}
			//����һ���������е�ÿ������
			for(int m = 0; m < regexWithFlag.size(); m++){
				String allAttributeFromRegex = regexWithFlag.get(m);
				String attribute = allAttributeFromRegex.split(";")[2];
				//��������� Ѱ����ͬ����
				for (int n = 0; n < attributeResultWithFlag.size(); n++) {
					String item = attributeResultWithFlag.get(n);
					String compare = item.split(";")[1];
					if (attribute.equals(compare)) {
						allAttributeFromRegex = allAttributeFromRegex.replace("none", compare);
						//�������滻none
						regexWithFlag.set(m, allAttributeFromRegex);
						//�������������滻none
						attributeResultWithFlag.set(n, allAttributeFromRegex);
						
					}
				}
			}
			//���ս�� �������Ժ�λ��
			ArrayList<String> outputResultArrayList = new ArrayList<String>();
			Boolean maybeWrongBoolean = false;
			Boolean missingAttributeBoolean = false;
			//���������
			for (int z = 0; z < attributeResultWithFlag.size(); z++) {
				String string = attributeResultWithFlag.get(z);
				String pre = "";
				String next = "";
				
				if (string.split(";")[0].equals("none")) {
					if(z != 0){
						pre = attributeResultWithFlag.get(z-1);
					}else{  //������ĵ�һ�� �� ���򼯵ĵ�һ���Ƚ�
						String nowStringOnRegex = regexWithFlag.get(0);
						String[] strings = nowStringOnRegex.split(";");
						// ������ǿ� ˵����ǰ����ʶ������
						if (strings[3].equals("none")) {
							outputResultArrayList.add(strings[0] + ";" + strings[1] + ";" + string.split(";")[1]);
							nowStringOnRegex = nowStringOnRegex.replace("none", string.split(";")[1]);
							regexWithFlag.set(z, nowStringOnRegex);
							attributeResultWithFlag.set(z, nowStringOnRegex);
							maybeWrongBoolean = true;
						}
						else {
							outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
							missingAttributeBoolean = true;
						}
					}
					//ͨ�����ǰ������� ���������ҵ������� ��ô�жϸ����Ժ�������� ���뵱ǰ���Զ���������ǲ���ҲΪnone Ϊnone���� ��Ϊȱʧ
					if (regexWithFlag.contains(pre)) {
						int now = regexWithFlag.indexOf(pre)+1;
						//ǰһ�����������һ�� ȱʧ
						if (now == regexWithFlag.size()) {
							outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
							missingAttributeBoolean = true;
							continue;
						}
						String nowStringOnRegex = regexWithFlag.get(now);
						String[] strings = nowStringOnRegex.split(";");
						//������ǰ�������
						if (strings[3].equals("none")) {
							outputResultArrayList.add(strings[0] + ";" + strings[1] + ";" + string.split(";")[1]);
							nowStringOnRegex = nowStringOnRegex.replace("none", string.split(";")[1]);
							regexWithFlag.set(now, nowStringOnRegex);
							attributeResultWithFlag.set(z, nowStringOnRegex);
							maybeWrongBoolean = true;
						}
						else {//ȱʧ
							outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
							missingAttributeBoolean = true;
						}
					}else{//ȱʧ
						outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
						missingAttributeBoolean = true;
					}
				}else{
					String start = string.split(";")[0];
					String end = string.split(";")[1];
					String attr = string.split(";")[2];
					outputResultArrayList.add(start + ";" + end + ";" + attr);
				}
			}
			//���������ַ���
			String stringToWrite = turnRegexResultToString(outputResultArrayList, fileReadingResult.get(i));
			//������ļ�����д���ļ�
			if (fileName.length() != 0) {
				writeToTxt(stringToWrite, fileName);
			}else {
				System.out.println(stringToWrite);
			}
			//ͳ�ƴ����Լ�ȱʧ���Ե���Ŀ����
			if (maybeWrongBoolean) {
				wrongCount++;
//				System.out.println(fileReadingResult.get(i));
//				System.out.println(outputResultArrayList);
			}
			if (missingAttributeBoolean) {
				missingCount++;
			}
			
			//System.out.println(stringToWrite);
			result.add(outputResultArrayList);
		}
		System.out.println("total: " + fileReadingResult.size());
		System.out.println("maybe wrong: " + wrongCount);
		System.out.println("missing: " + missingCount);
		return result;
	}
	
	public static void writeToTxt(String line, String fileName){
		FileWriter fw = null;
		try {
			// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
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
	
	public static String turnRegexResultToString(ArrayList<String> outputResultArrayList, String initString){
		String result = "";
		for (String attribute : outputResultArrayList) {
			String[] itemStrings = attribute.split(";");
			int start = Integer.parseInt(itemStrings[0]);
			int end = Integer.parseInt(itemStrings[1]);
			if (result.length() == 0) {
				if (start == -1) {
					result = "null";
				}else{
					result = initString.substring(start, end);
				}
			}else{
				if (start == -1) {
					result = result + " null";
				}else{
					result = result + " " + initString.substring(start, end); 
				}
			}
		}
		return result;
	}
	
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
}
