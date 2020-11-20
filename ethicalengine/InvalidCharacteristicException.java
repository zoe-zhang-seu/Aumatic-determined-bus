package ethicalengine;
/**
 * 
 * @author zhangyanni
 * 1132371 name: yanni zhang , username: YANNZHANG
 * final project
 *
 */
public class InvalidCharacteristicException extends Exception {

	int lineNumber;
	/**
	 * 
	 * @param n: is the wrong line number
	 */
	public InvalidCharacteristicException(int n) 
	{
        super();
        lineNumber = n;
    }

	
	public String toString() 
	{
	      return "WARNING: invalid number format in config file in line " + lineNumber;
	}
}