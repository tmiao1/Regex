import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import datacleaning.FileReader.ExcelReader;
import datacleaning.FileReader.SqlReader;
import datacleaning.FileReader.TxtReader;
import datacleaning.Regex.EmailRegex;
import datacleaning.Regex.TelRegex;
import datacleaning.attributecount.AttributeCount;

public class Main {
	
	//public static String fileName = "/Users/miao/哈工大/项目/数据清洗/toDoFileList/爱慕网--160万/amimer1.txt";
	public static String fileName = "D:\\数据清洗文件\\toDoFileList\\爱慕网--160万\\amimer1.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dataCleaning();
		/*****************测试正则表达式*****************/
		//ArrayList<String> oneLineResult = Regex(string);
		
		/*****************测试文件解析模块*****************/
		//testExcelFileReader();
		//testSqlReader();
		//testReadTxtFile();
	}
	
	public static void dataCleaning(){
		ArrayList<String> fileReadingResult = null;//存储读取文件的结果（按行存储）
		/*
		 * 在regexResult中存储正则匹配的结果，
		 * {"23;35;Telephone","45;60;Name","63;75;Gender"}
		 * {"20;32;Telephone","45;60;Name","63;75;Gender"}
		 * {"25;38;Telephone","45;60;Name","63;75;Gender"}
		 */
		ArrayList<ArrayList<String>> regexResult = new ArrayList<ArrayList<String>>(); //存储正则表达式的结果，每个一维数组存储一行的匹配结果
		
		ArrayList<HashMap<String, Integer>> countResult = new ArrayList<HashMap<String,Integer>>();
		
		if (fileName.endsWith(".txt")) {
			TxtReader txtReader = new TxtReader();
			 fileReadingResult = txtReader.readTxtFile(fileName);
		}
		for (Iterator<String> iterator = fileReadingResult.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			ArrayList<String> oneLineResult = Regex(string);
			Collections.sort(oneLineResult);
			regexResult.add(oneLineResult);   //将每一行的结果添加到总结果中，总结果为二维数组
		}
		AttributeCount attributeCount = new AttributeCount();
		attributeCount.countAttribute(regexResult, countResult);
		//printArrayListResult(regexResult.get(3), fileReadingResult.get(3));

	}
	
	public static void testReadTxtFile(){
		String txtFile = "/Users/miao/哈工大/项目/数据清洗/toDoFileList/爱慕网--160万/amimer1.txt";
		
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
		//存储识别结构 key：开始位置  value：结束位置
		ArrayList<String> result = new ArrayList<String>();
		
		//String initString = "abcdefghijkl@qq.commnopqrst+8618704634158uvwx18704634152yz";
		//String string = "adc3322frg";
		//使用telRegex识别电话号
		TelRegex telRegex = new TelRegex();
		telRegex.doRegex(initString, result);
		
		EmailRegex emailRegex = new EmailRegex();
		emailRegex.doRegex(initString, result);
		
		//printArrayListResult(result, initString);
		//printAllValue(result, string);
		
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
	 * 根据字段类型，打印相应匹配出的值
	 * @param type
	 *     字段类型，例如"Telephone"
	 * @param result
	 *     正则匹配的返回结果，hashmap<类型, 开始，结束[；开始，结束]>
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
	 * 打印全部匹配出的值
	 * @param result
	 *     正则匹配的返回结果，hashmap<类型, 开始，结束[；开始，结束]>
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
