package datacleaning.Regex;

public class AddressRegex extends InfoRegex{
	private static String regex = "	((([\\u4e00-\\u9fa5]*\\u7701)?([\\u4e00-\\u7700]|\\s*|[\\u7702-\\u9fa5])*[\\w]?[\\u5e02|\\u53bf|\\u53f7|\\u533a|\\u680b]([\\u4e00-\\u9fa5]|\\d|\\s*)*([-]{0,2}\\d+)?)+)([\\u4e00-\\u9fa5]|\\w)*";	
	private static String type = "Name";
	public AddressRegex() {
		//父类的构造函数
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
