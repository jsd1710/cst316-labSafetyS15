
public class MyDataInitializationException extends Exception 
{
	MyDataInitializationException()
	{
		super("Caught an error in the Data Initialization Phase.");
	}
	MyDataInitializationException(String message)
	{
		super(message);
	}
}
