package exceptions;

@SuppressWarnings( "serial" )
public class InvalidProductDataException extends Exception
{
	public InvalidProductDataException()
	{
		super();
	}
	
	public InvalidProductDataException( String message )
	{
		super( message );
	}
}
