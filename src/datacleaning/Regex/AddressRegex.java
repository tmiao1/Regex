package datacleaning.Regex;

public class AddressRegex extends InfoRegex{
//	private static String regex = "([\\u4e00-\\u9fa5]*\\u5e02)|([\\u4e00-\\u9fa5]*\\u53bf)";
//	private static String regex = "([([\\u4e00-\\u9fa5]*\\u5e02)|([\\u4e00-\\u9fa5]*\\u53bf)"
//		+"|([\\u4e00-\\u9fa5]*\\u53f7)|([\\u4e00-\\u9fa5]*\\u533a)|([\\u4e00-\\u9fa5]*\\u680b)])+";//市县号区栋	
	private static String regex = "(([\\u4e00-\\u9fa5]*\\u7701)?"//省
			+"([\\u4e00-\\u9fa5]*\\u5e02|[\\u4e00-\\u9fa5]*\\u53bf"
			+"|[\\u4e00-\\u9fa5]*\\u53f7|[\\u4e00-\\u9fa5]*\\u533a|[\\u4e00-\\u9fa5]*\\u680b|[\\u4e00-\\u9fa5]*\\u697c)+"//市县号区栋
			+"([\\u4e00-\\u9fa5]|\\d|\\w|-\\d)*)";	
	private static String type = "Address";
	public AddressRegex() {
		//父类的构造函数
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
