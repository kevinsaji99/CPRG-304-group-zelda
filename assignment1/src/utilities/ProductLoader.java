package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exceptions.InvalidProductDataException;
import products.Dresses;
import products.Jackets;
import products.Jeans;
import products.Product;
import products.Shoes;
import products.Shorts;
import products.Skirts;
import products.TShirts;


/**
 * Utility class that reads a product data file into a fixed-size array.
 * <p>
 * The first value in the file is the number of products, which sets the
 * size of the array. Each following line holds a product type and two prices
 * separated by spaces, for example: Shorts 5.25 23.36
 * </p>
 * <p>
 * Any problem with the file's contents is reported with an
 * {@link InvalidProductDataException} that describes what is wrong.
 * </p>
 *
 * @author Kevin Saji
 */
public final class ProductLoader
{
	
	/**
	 * Reads every product in the data file into an array.
	 * <p>
	 * The file is always closed, whether loading succeeds or fails.
	 * </p>
	 *
	 * @param fileName the name (and path) of the data file
	 * @return an array holding one product per data line, in file order
	 * @throws FileNotFoundException if the data file does not exist
	 * @throws InvalidProductDataException if the count, a product type, or a
	 *         price is invalid, or the file has fewer products than the count
	 */
	public static Product[] load( String fileName )
			throws FileNotFoundException, InvalidProductDataException
	{
		Scanner in = new Scanner( new File( fileName ) );
		Product[] products;

		try
		{
			products = readProducts( in, fileName );
		}
		catch( InvalidProductDataException e )
		{
			in.close();
			throw e;
		}

		in.close();
		return products;
	}


	/**
	 * Reads the product count and then every product from an open scanner.
	 *
	 * @param in the scanner reading the data file
	 * @param fileName the name of the data file, used in error messages
	 * @return an array holding one product per data line, in file order
	 * @throws InvalidProductDataException if the file is empty, the count, a
	 *         product type, or a price is invalid, or the file has fewer
	 *         products than the count says
	 */
	private static Product[] readProducts( Scanner in, String fileName ) throws InvalidProductDataException
	{
		if( !in.hasNext() )
		{
			throw new InvalidProductDataException( "The data file " + fileName + " is empty." );
		}

		int count = parseCount( in.next() );
		Product[] products = new Product[count];

		for( int i = 0; i < count; i++ )
		{
			if( !in.hasNext() )
			{
				throw new InvalidProductDataException( "The data file says it has " + count
						+ " products but only " + i + " were found." );
			}

			String type = in.next();
			double originalPrice = parsePrice( in, type, i + 1 );
			double updatedPrice = parsePrice( in, type, i + 1 );

			products[i] = createProduct( type, originalPrice, updatedPrice, i + 1 );
		}

		return products;
	}


	/**
	 * Converts the first value in the file into the product count.
	 *
	 * @param value the first value read from the file
	 * @return the number of products in the file
	 * @throws InvalidProductDataException if the value is not a whole number
	 *         or is negative
	 */
	private static int parseCount( String value ) throws InvalidProductDataException
	{
		int count;

		try
		{
			count = Integer.parseInt( value );
		}
		catch( NumberFormatException e )
		{
			throw new InvalidProductDataException( "The first line must be the number of products, found: "
					+ value );
		}

		if( count < 0 )
		{
			throw new InvalidProductDataException( "The number of products cannot be negative: " + count );
		}

		return count;
	}


	/**
	 * Reads the next value in the file as a price.
	 *
	 * @param in the scanner reading the data file
	 * @param type the product type on the current line, used in error messages
	 * @param productNumber the position of the product in the file, used in
	 *        error messages
	 * @return the price
	 * @throws InvalidProductDataException if the price is missing or is not
	 *         a number
	 */
	private static double parsePrice( Scanner in, String type, int productNumber )
			throws InvalidProductDataException
	{
		if( !in.hasNext() )
		{
			throw new InvalidProductDataException( "Product " + productNumber + " (" + type
					+ ") is missing a price." );
		}

		String value = in.next();

		try
		{
			return Double.parseDouble( value );
		}
		catch( NumberFormatException e )
		{
			throw new InvalidProductDataException( "Product " + productNumber + " (" + type
					+ ") has a price that is not a number: " + value );
		}
	}


	/**
	 * Creates the subclass of {@link Product} that matches the product type.
	 *
	 * @param type the product type, for example Jeans
	 * @param originalPrice the product's original price
	 * @param updatedPrice the product's updated price
	 * @param productNumber the position of the product in the file, used in
	 *        error messages
	 * @return a new product of the matching subclass
	 * @throws InvalidProductDataException if the type is not one of the seven
	 *         product types
	 */
	private static Product createProduct( String type, double originalPrice, double updatedPrice,
			int productNumber ) throws InvalidProductDataException
	{
		if( type.equals( "Jeans" ) )
		{
			return new Jeans( originalPrice, updatedPrice );
		}
		else if( type.equals( "TShirts" ) )
		{
			return new TShirts( originalPrice, updatedPrice );
		}
		else if( type.equals( "Skirts" ) )
		{
			return new Skirts( originalPrice, updatedPrice );
		}
		else if( type.equals( "Dresses" ) )
		{
			return new Dresses( originalPrice, updatedPrice );
		}
		else if( type.equals( "Shorts" ) )
		{
			return new Shorts( originalPrice, updatedPrice );
		}
		else if( type.equals( "Jackets" ) )
		{
			return new Jackets( originalPrice, updatedPrice );
		}
		else if( type.equals( "Shoes" ) )
		{
			return new Shoes( originalPrice, updatedPrice );
		}
		else
		{
			throw new InvalidProductDataException( "Product " + productNumber + " has an unknown type: "
					+ type + " (expected Jeans, TShirts, Skirts, Dresses, Shorts, Jackets or Shoes)" );
		}
	}
}