package datacleaning.Regex;

public class MoneyRegex extends InfoRegex{
	private static String regex = "(?<!\\d|\\w|[\\u4e00-\\u9fa5])\\d+\\.\\d{0,2}(?!\\d|\\w|[\\u4e00-\\u9fa5])";	
	private static String type = "Money";
	public MoneyRegex() {
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
