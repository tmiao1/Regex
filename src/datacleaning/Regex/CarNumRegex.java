package datacleaning.Regex;

public class CarNumRegex extends InfoRegex{
	private static String regex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z|0-9]{5}";	
	private static String type = "CarNum";
	public CarNumRegex() {
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
