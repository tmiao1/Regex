import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import datacleaning.FileReader.ExcelReader;
import datacleaning.FileReader.SqlReader;
import datacleaning.FileReader.TxtReader;
import datacleaning.Regex.AddressRegex;
import datacleaning.Regex.BankCardNum;
import datacleaning.Regex.CarNumRegex;
import datacleaning.Regex.DateRegex;
import datacleaning.Regex.EmailRegex;
import datacleaning.Regex.FixedTelephone;
import datacleaning.Regex.MoneyRegex;
import datacleaning.Regex.NameRegex;
import datacleaning.Regex.TelRegex;
import datacleaning.Regex.UnknownRegex;
import datacleaning.attributecount.AttributeCount;

public class Main {
	
	public static String fileName = "/Users/miao/������/��Ŀ/������ϴ/toDoFileList/��Ľ��--160��/amimer1.txt";
	//public static String fileName = "D:\\������ϴ�ļ�\\toDoFileList\\��Ľ��--160��\\amimer1.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		dataCleaning();
		/*****************����������ʽ*****************/
		String string = "2011052596240-----��ܲ----------15811555621-----������22�����ع㳡11��:117.00-----";
		ArrayList<String> oneLineResult = Regex(string);
		
		/*****************�����ļ�����ģ��*****************/
		//testExcelFileReader();
		//testSqlReader();
		//testReadTxtFile();
	}
	
	public static void dataCleaning(){
		ArrayList<String> fileReadingResult = null;//�洢��ȡ�ļ��Ľ�������д洢��
		/*
		 * ��regexResult�д洢����ƥ��Ľ����
		 * {"23;35;Telephone","45;60;Name","63;75;Gender"}
		 * {"20;32;Telephone","45;60;Name","63;75;Gender"}
		 * {"25;38;Telephone","45;60;Name","63;75;Gender"}
		 */
		ArrayList<ArrayList<String>> regexResult = new ArrayList<ArrayList<String>>(); //�洢������ʽ�Ľ����ÿ��һά����洢һ�е�ƥ����
		
		ArrayList<HashMap<String, Integer>> countResult = new ArrayList<HashMap<String,Integer>>();
		
		HashMap<String, Integer> totalCountResultHashMap = new HashMap<String, Integer>();
		
		if (fileName.endsWith(".txt")) {
			TxtReader txtReader = new TxtReader();
			 fileReadingResult = txtReader.readTxtFile(fileName);
		}
		for (Iterator<String> iterator = fileReadingResult.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			ArrayList<String> oneLineResult = Regex(string);
			regexResult.add(oneLineResult);   //��ÿһ�еĽ����ӵ��ܽ���У��ܽ��Ϊ��ά����
		}

		AttributeCount.countAttribute(regexResult, countResult);
		totalCountResultHashMap = AttributeCount.analyseAttribute(countResult);
		List<Map.Entry<ArrayList<String>, Integer>> resultByLine = AttributeCount.countRegexResultByLine(regexResult);
		System.out.println(totalCountResultHashMap);
		System.out.println(countResult);
		System.out.println(resultByLine);
		Integer totalInteger = 0;
		for (Entry<ArrayList<String>, Integer> entry : resultByLine) {
			totalInteger = totalInteger + entry.getValue();
		}
		System.out.println(totalInteger);
		//printArrayListResult(regexResult.get(3), fileReadingResult.get(3));
	}
	
	public static void testReadTxtFile(){
		String txtFile = "/Users/miao/������/��Ŀ/������ϴ/toDoFileList/��Ľ��--160��/amimer1.txt";
		
		TxtReader txtReader = new TxtReader();
		ArrayList<String> result = txtReader.readTxtFile(txtFile);
		for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
	}
	
	public static void testSqlReader(){
//		String fileName = "C:/Users/think/Desktop/t_prov_city_area_street.sql";
//		String tableName = SqlReader.getTableNameFromSqlFile(fileName);
//		System.out.println(tableName);
		ArrayList<ArrayList<String>> result = SqlReader.readInfoFromMySql("s_city");
			for (ArrayList<String> arrayList : result) {
				for (String string : arrayList) {
					System.out.print(string + " ");
				}
				System.out.print("\r\n");
			}
	}
	
	public static void testExcelFileReader() {
		ExcelReader excelReader = new ExcelReader();
		ArrayList<ArrayList<ArrayList<String>>> result = excelReader.randomReadExcelByPercent(1, "/Users/miao/Desktop/a.xlsx");
//		ArrayList<ArrayList<ArrayList<String>>> result = excelReader.readExcel("D://b.xlsx");
		for (ArrayList<ArrayList<String>> arrayList : result) {
			
			for (ArrayList<String> arrayList2 : arrayList) {
				for (String string : arrayList2) {
					System.out.print(string+" ");
				}
				System.out.print("\r\n");
			}
			//System.out.println(arrayList.size());
		}
		
	}
	
	public static ArrayList<String> Regex(String initString){
		//�洢ʶ��ṹ key����ʼλ��  value������λ��
		ArrayList<String> result = new ArrayList<String>();
		
		//String initString = "abcdefghijkl@qq.commnopqrst+8618704634158uvwx18704634152yz";
		//String string = "adc3322frg";
		//ʹ��telRegexʶ��绰��
		TelRegex telRegex = new TelRegex();
		telRegex.doRegex(initString, result);
		
		EmailRegex emailRegex = new EmailRegex();
		emailRegex.doRegex(initString, result);
		
		AddressRegex addressRegex = new AddressRegex();
		addressRegex.doRegex(initString, result);
		
		NameRegex nameRegex = new NameRegex();
		nameRegex.doRegex(initString, result);
		
		FixedTelephone fixedTelephone = new FixedTelephone();
		fixedTelephone.doRegex(initString, result);
		
		DateRegex dateRegex = new DateRegex();
		dateRegex.doRegex(initString, result);
		
		BankCardNum bankCardNum = new BankCardNum();
		bankCardNum.doRegex(initString, result);
		
		CarNumRegex carNumRegex = new CarNumRegex();
		carNumRegex.doRegex(initString, result);
		
		MoneyRegex moneyRegex = new MoneyRegex();
		moneyRegex.doRegex(initString, result);
		
		result = AttributeCount.regexResultPretreatment(result);
		
		UnknownRegex unknownRegex = new UnknownRegex();
		unknownRegex.doRegex(initString, result);
		
		result = AttributeCount.regexResultPretreatment(result);
		
		printArrayListResult(result, initString);
		
		return result;
	}
	
	public static void printArrayListResult(ArrayList<String> result, String initString){
		for (String string : result) {
			String[] startEndType = string.split(";");
			int startPosition = Integer.parseInt(startEndType[0]);
			int endPosition = Integer.parseInt(startEndType[1]);
			String type = startEndType[2];
			System.out.println(type + ": "+ initString.substring(startPosition, endPosition));
		}
	}
	
	/**
	 * �����ֶ����ͣ���ӡ��Ӧƥ�����ֵ
	 * @param type
	 *     �ֶ����ͣ�����"Telephone"
	 * @param result
	 *     ����ƥ��ķ��ؽ����hashmap<����, ��ʼ������[����ʼ������]>
	 * @param initString
	 */
	public static void printValueByType(String type, HashMap<String, String> result, String initString){
		String[] startEnds = null;
		startEnds = result.get(type).split(";");
		for (String startEnd : startEnds) {
			String[] tempStartEnd = startEnd.split(",");
			int start = Integer.parseInt(tempStartEnd[0]);
			int end = Integer.parseInt(tempStartEnd[1]);
			System.out.println(type + ": " + initString.substring(start, end));
		}
	}
	/**
	 * ��ӡȫ��ƥ�����ֵ
	 * @param result
	 *     ����ƥ��ķ��ؽ����hashmap<����, ��ʼ������[����ʼ������]>
	 * @param initString
	 */
	public static void printAllValue(HashMap<String, String> result, String initString){
		Iterator<Entry<String, String>> iterator = result.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			String[] startEnds = entry.getValue().split(";");
			String type = entry.getKey();
			for (String startEnd : startEnds) {
				String[] tempStartEnd = startEnd.split(",");
				int start = Integer.parseInt(tempStartEnd[0]);
				int end = Integer.parseInt(tempStartEnd[1]);
				System.out.println(type + ": " + initString.substring(start, end));
			}
		}
	}

}
