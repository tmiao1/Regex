package datacleaning.Regex;

public class BankCardNum extends InfoRegex{
	private static String regex = "6\\d{5}\\s?\\d{10}\\s?\\d{3}";	
	private static String type = "BankCardNum";
	public BankCardNum() {
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
