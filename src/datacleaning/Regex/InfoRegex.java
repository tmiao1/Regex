package datacleaning.Regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoRegex {
	private Pattern pattern;
	private Matcher matcher;
	private String regex;
	private String type;
	
	public InfoRegex(String type, String regex){
		this.regex = regex;
		this.type = type;
	}
	
	public String getRegex(){
		return regex;
	}
	
	public String getType(){
		return type;
	}
	/**
	 * 根据正则表达式匹配出结果，例：23;35;Telephone
	 * @param initString
	 * @param result
	 * @return
	 */
	public ArrayList<String> doRegex(String initString, ArrayList<String> result) {
		// TODO Auto-generated method stub
		//HashMap<String, String> result = new HashMap<String, String>();
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(initString);
		String itemResult = "";               //存储匹配出的一条结果
		
		while(matcher.find()){
				itemResult = matcher.start() + ";" + matcher.end() + ";" + type;
				result.add(itemResult);
		}
		
		return result;
	}
	
}
