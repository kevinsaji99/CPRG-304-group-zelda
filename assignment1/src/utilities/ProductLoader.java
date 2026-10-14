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


public final class ProductLoader
{
	
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
