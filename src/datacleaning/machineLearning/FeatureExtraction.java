package datacleaning.machineLearning;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeatureExtraction {
	
	public ArrayList<String> getFeatureSet(String attribute){
		ArrayList<String> result = new ArrayList<String>();
		
		int length = getLength(attribute);
		result.add(length + "");
		
		int numCount = getNumCount(attribute);
		result.add(numCount + "");
		
		int chineseCount = getChineseCount(attribute);
		result.add(chineseCount + "");
		
		int containsLocationCount = getContainsLocationCount(attribute);
		result.add(containsLocationCount + "");
		
		int containsFirstName = getContainsFirstNameCount(attribute);
		result.add(containsFirstName + "");
		
		int firstTwoNum = getFirstTwoNum(attribute);
		result.add(firstTwoNum + "");
		
		int firstThreeNum = getFirstThreeNum(attribute);
		result.add(firstThreeNum + "");
		
		int containsSpecialWordCount = getContainsSpecialWordCount(attribute);
		result.add(containsSpecialWordCount + "");
		
		int containsSpecialmarkCount = getContainsSpecialmarkCount(attribute);
		result.add(containsSpecialmarkCount + "");
		
		int containsAt = ifContainsAt(attribute);
		result.add(containsAt + "");
		
		int containsDate = ifContainsDate(attribute);
		result.add(containsDate + "");
		
		int onlyNum = ifOnlyNum(attribute);
		result.add(onlyNum + "");
		
		int onlyEnglish = ifOnlyEnglish(attribute);
		result.add(onlyEnglish + "");
		
		int onlyChinese = ifOnlyChinese(attribute);
		result.add(onlyChinese + "");
		
		return result;
	}
	
	public int CountByRegex(String regex, String attribute){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(attribute);
		
		int count = 0;
		while(matcher.find()){
			++count;
		}
		
		return count;
	}
	
	public int getLength(String attribute){
		
		return attribute.length();
	}
	
	public int getNumCount(String attribute){
		String regex = "\\d";
		
		return CountByRegex(regex, attribute);
	}
	
	public int getChineseCount(String attribute){
		String regex = "[\\u4E00-\\u9FA5]";
		
		return CountByRegex(regex, attribute);
	}
	
	public int getContainsLocationCount(String attribute){
		String regex = "[\u7701\u5e02\u533a\u53bf\u6751\u8857\u8def\u5df7\u53f7]";
		
		return CountByRegex(regex, attribute);
	}
	
	public int getContainsFirstNameCount(String attribute){
		String regex = "[\u8d75\u94b1\u5b59\u674e\u5468\u5434\u90d1\u738b"
	+"\u51af\u9648\u696e\u536b\u848b\u6c88\u97e9\u6768"
				+"\u6731\u79e6\u5c24\u8bb8\u4f55\u5415\u65bd\u5f20"
	+"\u5b54\u66f9\u4e25\u534e\u91d1\u9b4f\u9676\u59dc"
				+"\u621a\u8c22\u90b9\u55bb\u67cf\u6c34\u7aa6\u7ae0"
	+"\u4e91\u82cf\u6f58\u845b\u595a\u8303\u5f6d\u90ce"
				+"\u9c81\u97e6\u660c\u9a6c\u82d7\u51e4\u82b1\u65b9"
	+"\u4fde\u4efb\u8881\u67f3\u9146\u9c8d\u53f2\u5510"
				+"\u8d39\u5ec9\u5c91\u859b\u96f7\u8d3a\u502a\u6c64"
	+"\u6ed5\u6bb7\u7f57\u6bd5\u90dd\u90ac\u5b89\u5e38"
				+"\u4e50\u4e8e\u65f6\u5085\u76ae\u535e\u9f50\u5eb7"
	+"\u4f0d\u4f59\u5143\u535c\u987e\u5b5f\u5e73\u9ec4"
				+"\u548c\u7a46\u8427\u5c39\u59da\u90b5\u6e5b\u6c6a"
	+"\u7941\u6bdb\u79b9\u72c4\u7c73\u8d1d\u660e\u81e7"
				+"\u8ba1\u4f0f\u6210\u6234\u8c08\u5b8b\u8305\u5e9e"
	+"\u718a\u7eaa\u8212\u5c48\u9879\u795d\u8463\u6881\u675c\u962e\u84dd\u95fd\u5e2d\u5b63\u9ebb\u5f3a\u8d3e\u8def\u5a04\u5371\u6c5f\u7ae5\u989c\u90ed\u6885\u76db\u6797\u5201\u953a\u5f90\u4e18\u9a86\u9ad8\u590f\u8521\u7530\u6a0a\u80e1\u51cc\u970d\u865e\u4e07\u652f\u67ef\u661d\u7ba1\u5362\u83ab\u7ecf\u623f\u88d8\u7f2a\u5e72\u89e3\u5e94\u5b97\u4e01\u5ba3\u8d32\u9093\u90c1\u5355\u676d\u6d2a\u5305\u8bf8\u5de6\u77f3\u5d14\u5409\u94ae\u9f9a\u7a0b\u5d47\u90a2\u6ed1\u88f4\u9646\u8363\u7fc1\u8340\u7f8a\u65bc\u60e0\u7504\u9eb9\u5bb6\u5c01\u82ae\u7fbf\u50a8\u9773\u6c72\u90b4\u7cdc\u677e\u4e95\u6bb5\u5bcc\u5deb\u4e4c\u7126\u5df4\u5f13\u7267\u9697\u5c71\u8c37\u8f66\u4faf\u5b93\u84ec\u5168\u90d7\u73ed\u4ef0\u79cb\u4ef2\u4f0a\u5bab\u5b81\u4ec7\u683e\u66b4\u7518\u659c\u5389\u620e\u7956\u6b66\u7b26\u5218\u666f\u8a79\u675f\u9f99\u53f6\u5e78\u53f8\u97f6\u90dc\u9ece\u84df\u8584\u5370\u5bbf\u767d\u6000\u84b2\u90b0\u4ece\u9102\u7d22\u54b8\u7c4d\u8d56\u5353\u853a\u5c60\u8499\u6c60\u4e54\u9634\u90c1\u80e5\u80fd\u82cd\u53cc\u95fb\u8398\u515a\u7fdf\u8c2d\u8d21\u52b3\u9004\u59ec\u7533\u6276\u5835\u5189\u5bb0\u90e6\u96cd\u90e4\u74a9\u6851\u6842\u6fee\u725b\u5bff\u901a\u8fb9\u6248\u71d5\u5180\u90cf\u6d66\u5c1a\u519c\u6e29\u522b\u5e84\u664f\u67f4\u77bf\u960e\u5145\u6155\u8fde\u8339\u4e60\u5ba6\u827e\u9c7c\u5bb9\u5411\u53e4\u6613\u614e\u6208\u5ed6\u5ebe\u7ec8\u66a8\u5c45\u8861\u6b65\u90fd\u803f\u6ee1\u5f18\u5321\u56fd\u6587\u5bc7\u5e7f\u7984\u9619\u4e1c\u6b27\u6bb3\u6c83\u5229\u851a\u8d8a\u5914\u9686\u5e08\u5de9\u538d\u8042\u6641\u52fe\u6556\u878d\u51b7\u8a3e\u8f9b\u961a\u90a3\u7b80\u9976\u7a7a\u66fe\u6bcb\u6c99\u4e5c\u517b\u97a0\u987b\u4e30\u5de2\u5173\u84af\u76f8\u67e5\u540e\u8346\u7ea2\u6e38\u7afa\u6743\u9011\u76d6\u76ca\u6853\u516c]";
		
		return CountByRegex(regex, attribute);
	}
	
	public int getFirstTwoNum(String attribute){
		if (Character.isDigit(attribute.charAt(0)) && Character.isDigit(attribute.charAt(1))) {
			
			return Integer.parseInt(attribute.substring(0, 2));
		}
		
		return 0;
	}
	
	public int getFirstThreeNum(String attribute){
		if (Character.isDigit(attribute.charAt(0)) && Character.isDigit(attribute.charAt(1))
				&& Character.isDigit(attribute.charAt(2))) {
			
			return Integer.parseInt(attribute.substring(0, 2));
		}
		
		return 0;
	}
	
	public int getContainsSpecialWordCount(String attribute){
		return 0;
	}
	
	public int getContainsSpecialmarkCount(String attribute){
		//String regex = "[\\-\\_\\:\\;\\(\\)\\*\\.\\,\\#]";
		String regex = "\\pP";
		
		return CountByRegex(regex, attribute);
	}
	/**
	 * 0代表没有@ 1代表有
	 * @param attribute
	 * @return
	 */
	public int ifContainsAt(String attribute){
		if (attribute.contains("@")) {
			return 1;
		}
		return 0;
	}
	
	public int ifContainsDate(String attribute){
		String regex = "((?<!\\d)((((1[6-9]|[2-9]\\d)\\d{2})"
			+ "(-|\\s|/|\\.|\\u5e74))?(1[02]|0?[13578])(-|\\s|/|\\.|\\u6708)"
			+ "([12]\\d|3[01]|0?[1-9])(-|\\s|/|\\.|\\u65e5)?)|"
			+ "((((1[6-9]|[2-9]\\d)\\d{2})(-|\\s|/|.|\\u5e74)?)"
			+ "(1[012]|0?[13456789])(-|\\s|/|\\.|\\u65e5)"
			+ "([12]\\d|30|0?[1-9])(-|\\s|/|\\.|\\u65e5)?)|(((1[6-9]|[2-9]\\d)"
			+ "\\d{2})-0?2-(1\\d|2[0-8]|0?[1-9]))|(((1[6-9]|[2-9]\\d)"
			+ "(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))"
			+ "-0?2-29-))";
		
		return CountByRegex(regex, attribute);
	}
	
	/**
	 * 0 不是纯数字 1：纯数字 2：有空格 - _ + .的纯数字
	 * @param attribute
	 * @return
	 */
	public int ifOnlyNum(String attribute){
		String regexNum = "\\d";
		String regexNumAndMark = "[\\d\\s\\-\\_\\+\\.]";
		if (CountByRegex(regexNum, attribute) == attribute.length()) {
			return 1;
		}else if (CountByRegex(regexNumAndMark, attribute) == attribute.length()) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * 0 不是纯字母 1：纯字母 2：有空格 - _ .的纯字母
	 * @param attribute
	 * @return
	 */
	public int ifOnlyEnglish(String attribute){
		String regexNum = "\\w";
		String regexNumAndMark = "[\\w\\s\\-\\_\\.]";
		if (CountByRegex(regexNum, attribute) == attribute.length()) {
			return 1;
		}else if (CountByRegex(regexNumAndMark, attribute) == attribute.length()) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * 0 不是纯汉字 1：纯汉字 2：有空格 - _ .的纯汉字
	 * @param attribute
	 * @return
	 */
	public int ifOnlyChinese(String attribute){
		String regexNum = "[\\u4E00-\\u9FA5]";
		String regexNumAndMark = "[[\\u4E00-\\u9FA5]\\s\\-\\_\\.]";
		if (CountByRegex(regexNum, attribute) == attribute.length()) {
			return 1;
		}else if (CountByRegex(regexNumAndMark, attribute) == attribute.length()) {
			return 2;
		}
		return 0;
	}
	
}
