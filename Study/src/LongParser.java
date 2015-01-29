public class LongParser {
	public static class InvalidInputException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5448156649758350771L;

		public InvalidInputException() {
			super();
		}

		public InvalidInputException(String message) {
			super(message);
		}

		public InvalidInputException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	/**
	 * 
	 * Method parse a string representation of long and returns a the java primitive long. Limitation is that string consisting 
	 * of only zeros, 20 or more will generate an InvalidInputException, where as the have 19 or less zeros will evaluate to zero.
	 * 
	 * @param s, string representation of long
	 * @return the result long
	 * @throws InvalidInputException
	 */
	public static long stringToLong(String s) throws InvalidInputException
	{
		if (s == null) 
		{
			throw new InvalidInputException("Input cannot be null");
		}
			
		boolean negative = false;
		int start = 0;
		
		if (s.startsWith("-"))
		{			
			if (s.length() > 20)
			{
				throw new InvalidInputException("Value out of range");
			}
			
			negative = true;
			start = 1;
		}
		else
		{
			if (s.length() > 19) 
			{
				throw new InvalidInputException("Value out of range");
			} 
		}
		
		long result = 0;
		
		for (int i = s.length() - 1; i >= start; i--) 
		{
			char c = s.charAt(i);
			int digitValue = charToInt(c);
			
			int power = s.length() - i - 1;
			
			long append = (long) (digitValue * Math.pow(10, power));
			
			//Check to see if we might be going out of range 
			
			if (negative) 
			{				
				if (Long.MAX_VALUE - append < result - 1) 
				{
					throw new InvalidInputException("Value out of range");
				}
			} 
			else 
			{
				if (Long.MAX_VALUE - append < result) 
				{
					throw new InvalidInputException("Value out of range");
				}
			}
						
			result += append;
		}
		
		if (negative)
		{
			result *= -1;
		}
		
		return result;
	}
	
	public static void testStringToLong()
	{
		assert(testInvalidString());
		assert(testOutOfMaxRange());
		assert(testOutOfMinRange());
		assert(testValidStrings());
	}
	
	private static boolean testInvalidString()
	{
		try {
			long i = stringToLong("--100");
			return false;
		} catch (InvalidInputException e) {
			return true;
		}
	}
	
	private static boolean testOutOfMaxRange()
	{
		try {
			stringToLong("9223372036854775808");
			return false;
		} catch (InvalidInputException e) {
			return true;
		}
	}
	
	private static boolean testOutOfMinRange()
	{
		try {
			stringToLong("-9223372036854775809");
			return false;
		} catch (InvalidInputException e) {
			return true;
		}
	}
	
	private static boolean testValidStrings()
	{
		try {
			if (stringToLong("-9223372036854775808") != -9223372036854775808L)
				return false;
			if (stringToLong("-1234") != -1234)
				return false;
			if (stringToLong("-1") != -1)
				return false;
			if (stringToLong("0") != 0)
				return false;
			if (stringToLong("00000") != 0)
				return false;
			if (stringToLong("12345") != 12345)
				return false;
			if (stringToLong("9223372036854775807") != 9223372036854775807L)
				return false;
			return true;
		} catch (InvalidInputException e) {
			return false;
		}
	}
	
	private static int charToInt(char c) throws InvalidInputException
	{
		switch (c) {
			case '0' :
				return 0;
			case '1' :
				return 1;
			case '2' :
				return 2;
			case '3' :
				return 3;
			case '4' :
				return 4;
			case '5' :
				return 5;
			case '6' :
				return 6;
			case '7' :
				return 7;
			case '8' :
				return 8;
			case '9' :
				return 9;
			default:
				throw new InvalidInputException("Non-numberic character in string.");
		}
	}
	
	public static void main(String[] args) {
		LongParser.testStringToLong();
	}

}
