package datacleaning.Regex;

public class MoneyRegex extends InfoRegex{
	private static String regex = "(?<!\\d)\\d+\\.\\d{0,2}(?!\\d)";	
	private static String type = "Money";
	public MoneyRegex() {
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
