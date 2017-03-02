package datacleaning.Regex;

public class DateRegex extends InfoRegex{
	private static String regex = "((?<!\\d)((((1[6-9]|[2-9]\\d)\\d{2})"
			+ "(-|\\s|/|\\.|\\u5e74))?(1[02]|0?[13578])(-|\\s|/|\\.|\\u6708)"
			+ "([12]\\d|3[01]|0?[1-9])(-|\\s|/|\\.|\\u65e5)?)|"
			+ "((((1[6-9]|[2-9]\\d)\\d{2})(-|\\s|/|.|\\u5e74)?)"
			+ "(1[012]|0?[13456789])(-|\\s|/|\\.|\\u65e5)"
			+ "([12]\\d|30|0?[1-9])(-|\\s|/|\\.|\\u65e5)?)|(((1[6-9]|[2-9]\\d)"
			+ "\\d{2})-0?2-(1\\d|2[0-8]|0?[1-9]))|(((1[6-9]|[2-9]\\d)"
			+ "(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))"
			+ "-0?2-29-))";	
	private static String type = "Date";
	public DateRegex() {
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
