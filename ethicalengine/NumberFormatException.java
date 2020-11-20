package ethicalengine;
/**
 * 
 * @author zhangyanni
 * 1132371 name: yanni zhang , username: YANNZHANG
 * final project
 *
 */
public class NumberFormatException extends Exception
{
	int lineNumber;
	/**
	 * 
	 * @param n: is the wrong line number
	 */
	public NumberFormatException(int n) 
	{
        super();
        lineNumber = n;
    }

	
	public String toString() 
	{
	      return "WARNING: invalid number format in config file in line ";
	}
}
