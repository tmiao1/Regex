package datacleaning.Regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnknownRegex {
	private static String regex = "(?<![0-9A-Za-z\\u4E00-\\u9FA5])[0-9A-Za-z\\u4E00-\\u9FA5]+(?![0-9A-Za-z\\u4E00-\\u9FA5])";	
	private static String type = "Unknown";
	private Pattern pattern;
	private Matcher matcher;

	/**
	 * 根据正则表达式匹配出结果，例：23;35;Telephone
	 * @param initString
	 * @param result
	 * @return
	 */
	public ArrayList<String> doRegex(String initString, ArrayList<String> result) {
		// TODO Auto-generated method stub
		//HashMap<String, String> result = new HashMap<String, String>();
		initString = deleteFindedAttribute(initString, result);
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(initString);
		String itemResult = "";               //存储匹配出的一条结果
		
		while(matcher.find()){
				itemResult = matcher.start() + ";" + matcher.end() + ";" + type;
				result.add(itemResult);
		}
		
		return result;
	}
	
	public String deleteFindedAttribute(String initString, ArrayList<String> result){
		for (String string : result) {
			String[] arrayStrings = string.split(";");
			int start = Integer.parseInt(arrayStrings[0]);
			int end = Integer.parseInt(arrayStrings[1]);
			String newString = "";
			for(int i = start; i < end; i++){
				newString = newString.concat("*");
			}
			String tempString = initString.substring(start, end);
			initString = initString.replace(tempString, newString);
		}
		return initString;
	}
}
