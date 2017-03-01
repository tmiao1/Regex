package datacleaning.Regex;


public class NameRegex extends InfoRegex{
	private static String regex = "(?!\\d)(86|\\+86|\\+86\\s|86\\s)?((13)|(14)|(15)|(17)|(18))\\d{9}(?!\\d)";	
	private static String type = "tel";
	public NameRegex() {
		//父类的构造函数
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
