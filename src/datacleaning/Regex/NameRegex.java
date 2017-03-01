package datacleaning.Regex;


public class NameRegex extends InfoRegex{
	private static String regex = "[^\\u4E00-\\u9FA5][\\u4E00-\\u9FA5]{2,3}[^\\u4E00-\\u9FA5]";	
	private static String type = "Name";
	public NameRegex() {
		//父类的构造函数
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
