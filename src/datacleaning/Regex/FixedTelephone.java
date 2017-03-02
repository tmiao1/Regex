package datacleaning.Regex;

public class FixedTelephone extends InfoRegex{
	private static String regex = "(?<!\\d)(0[0-9]{2,4} \\-|0[0-9]{2,4}\\-|0[0-9]{2,4})?([2-9][0-9]{6,7})(\\- [0-9]{1,4}|\\-[0-9]{1,4})?(?!\\d)";	
//	private static String regex = "(?<!\\d)([2-9][0-9]{6,7})(?!\\d)";	
	private static String type = "FixedTelephone";
	public FixedTelephone() {
		super(type, regex);
		// TODO Auto-generated constructor stub
	}
}
