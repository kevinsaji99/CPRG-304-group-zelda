package exceptions;

/**
 * Checked exception thrown when a Product is given invalid data,
 * such as a bad price, name, or other field value.
 * @author Kevin Saji
 */
@SuppressWarnings( "serial" )
public class InvalidProductDataException extends Exception
{
	/**
	 * Creates a new InvalidProductDataException with no detail message.
	 */
	public InvalidProductDataException()
	{
		super();
	}

	/**
	 * Creates a new InvalidProductDataException with the specified
	 * detail message.
	 *
	 * @param message a description of what product data was invalid
	 */
	public InvalidProductDataException( String message )
	{
		super( message );
	}
}