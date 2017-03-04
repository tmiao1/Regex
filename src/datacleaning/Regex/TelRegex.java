package datacleaning.Regex;


public class TelRegex extends InfoRegex{
	private static String regex = "(?<!\\d|\\w|[\\u4e00-\\u9fa5])(86|\\+86|\\+86\\s|86\\s)?((13)|(14)|(15)|(17)|(18))\\d{9}(?!\\d|\\w|[\\u4e00-\\u9fa5])";

	private static String type = "Telephone";
	public TelRegex() {
		//父类的构造函数
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
