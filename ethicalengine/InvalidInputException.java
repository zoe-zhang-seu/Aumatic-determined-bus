package ethicalengine;
/**
 * 
 * @author zhangyanni
 * 1132371 name: yanni zhang , username: YANNZHANG
 * final project
 *
 */
public class InvalidInputException extends Exception 
{
	/**
	 * a custom exception 
	 */
	public InvalidInputException() 
	{
        super();
       
    }

	
	public String toString() 
	{
	      return "Invalid response";
	}
}
