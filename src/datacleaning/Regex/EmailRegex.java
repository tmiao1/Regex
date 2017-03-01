package datacleaning.Regex;


public class EmailRegex extends InfoRegex{
	private static String regex = "((\\w|_){4,})@(\\w+)\\.com(.cn)?";	
	private static String type = "Email";
	public EmailRegex() {
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
