import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import datacleaning.Util.Operations;
import datacleaning.attributecount.AttributeCount;
import datacleaning.attributecount.ResultOperation;
import datacleaning.machineLearning.MachineLearning;
import datacleaning.machineLearning.PrepareWekeFileArff;

public class Main {
	/*******************清洗前的文件******************/
	//public static String fileName = "/Users/miao/哈工大/项目/数据清洗/toDoFileList/爱慕网--160万/amimer1.txt";
	//public static String fileName = "/Users/miao/哈工大/项目/数据清洗/toDoFileList/users2.txt";

	public static String fileName = "D:\\数据清洗文件\\toDoFileList\\爱慕网--160万\\amimer1.txt";
	
	/*******************清洗后的目标文件******************/
	//public static String targetFileName = "/Users/miao/哈工大/result.txt";
	public static String targetFileName = "D:\\result.txt";
	
	/*******************目标arff文件******************/
	//public static String arffFileName = "/Users/miao/哈工大/result.arff";
	public static String arffFileName = "D:\\result.arff";
	
	public static String targetModelName = "D:\\J48.model";
	
	public static String csvFileName = "D:\\right.csv";
	
	public static String wrongCsvFileName = "D:\\wrong.csv";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			dataCleaning();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*****************测试正则表达式*****************/
//		String string = "2011052596240-----孙懿----------15811555621-----建外大街22号赛特广场11层:117.00-----";
//		ArrayList<String> oneLineResult = Regex(string);
		
		/*****************测试文件解析模块*****************/
		//testExcelFileReader();
		//testSqlReader();
		//testReadTxtFile();
		/*****************测试机器学习模块*****************/
//		String attribute = "于溪淼";
//		FeatureExtraction featureExtraction = new FeatureExtraction();
//		ArrayList<String> result = featureExtraction.getFeatureSet(attribute);
//		String resultToCsvLine = ResultOperation.arraylistToString(result, ",");
//		System.out.println(resultToCsvLine);
	}
	
	public static void dataCleaning() throws Exception{
		ArrayList<String> fileReadingResult = null;//存储读取文件的结果（按行存储）
		/*
		 * 在regexResult中存储正则匹配的结果，
		 * {"23;35;Telephone","45;60;Name","63;75;Gender"}
		 * {"20;32;Telephone","45;60;Name","63;75;Gender"}
		 * {"25;38;Telephone","45;60;Name","63;75;Gender"}
		 */
		ArrayList<ArrayList<String>> regexResult = new ArrayList<ArrayList<String>>(); //存储正则表达式的结果，每个一维数组存储一行的匹配结果
		
		ArrayList<HashMap<String, Integer>> countResult = new ArrayList<HashMap<String,Integer>>();
		
		HashMap<String, Integer> totalCountResultHashMap = new HashMap<String, Integer>();
		
		ArrayList<Map.Entry<ArrayList<String>, Integer>> resultByLine = new ArrayList<Map.Entry<ArrayList<String>,Integer>>();
		
		ArrayList<String> targetAttributeResult = new ArrayList<String>();
		
		ArrayList<ArrayList<String>> finalResult = new ArrayList<ArrayList<String>>();
		
		ArrayList<ArrayList<String>> finalResultMaybeWrong = new ArrayList<ArrayList<String>>();
		
		ArrayList<ArrayList<String>> finalResultRight = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> fileReadingResultRight = new ArrayList<String>();
		
		ArrayList<String> fileReadingResultWrong = new ArrayList<String>();
		
		if (fileName.endsWith(".txt")) {
			TxtReader txtReader = new TxtReader();
			 fileReadingResult = txtReader.readTxtFile(fileName);
		}
		for (Iterator<String> iterator = fileReadingResult.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			ArrayList<String> oneLineResult = Regex(string);
			regexResult.add(oneLineResult);   //将每一行的结果添加到总结果中，总结果为二维数组
		}
		
		//计算每一列存在的属性及数量
		AttributeCount.countAttribute(regexResult, countResult);
		//计算每个属性总共的数量
		totalCountResultHashMap = AttributeCount.countTotalAttribute(countResult);
		//根据行计算由相同属性的构成的行的数量
		resultByLine = AttributeCount.countRegexResultByLine(regexResult);
		//合并按行统计的结果，以一定阈值筛选行属性的形式，最终将筛选出的行属性取并集得到属性集
		targetAttributeResult = AttributeCount.analyseAttributeByLine(resultByLine, regexResult.size());
		
//		System.out.println(totalCountResultHashMap);
//		System.out.println(countResult);
//		System.out.println(resultByLine);
//		System.out.println(targetAttributeResult);
		
//		finalResult = ResultOperation.outputReusltToConsole(fileReadingResult, regexResult, targetAttributeResult, targetFileName);
		finalResult = ResultOperation.outputReusltToConsole(fileReadingResult, regexResult, targetAttributeResult, "");
		finalResultMaybeWrong = ResultOperation.getWrongResult();
		finalResultRight = ResultOperation.getRightResult();
		fileReadingResultRight = ResultOperation.getRightFileReadingResult();
		fileReadingResultWrong = ResultOperation.getWrongFileReadingResult();
		
		Operations.writeToCsv(finalResultRight, fileReadingResultRight, csvFileName, targetAttributeResult);
		Operations.writeToCsv(finalResultMaybeWrong, fileReadingResultWrong, wrongCsvFileName, targetAttributeResult);
//
//		PrepareWekeFileArff.writeArff(finalResultRight, fileReadingResultRight, arffFileName, targetAttributeResult);
		
		MachineLearning machineLearning = new MachineLearning();
		//machineLearning.createModel(arffFileName, targetModelName);		
		ArrayList<String> dataToPredict = new ArrayList<String>();
		dataToPredict.add("于溪淼");
		machineLearning.doPredict(dataToPredict, targetAttributeResult);
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
		
		//printArrayListResult(result, initString);
		
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
