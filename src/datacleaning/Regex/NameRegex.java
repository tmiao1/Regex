package datacleaning.Regex;


public class NameRegex extends InfoRegex{
	private static String regex = "(?<![0-9A-Za-z\\u4E00-\\u9FA5])([\\u4E00-\\u5e01]|[\\u5e03-\\u7700]|[\\u7702-\\u9FA5]){2,3}(?![0-9A-Za-z\\u4E00-\\u9FA5])";	
	private static String type = "Name";
	public NameRegex() {
		//父类的构造函数
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
