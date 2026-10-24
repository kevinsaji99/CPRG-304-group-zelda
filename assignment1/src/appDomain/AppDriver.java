/**
 * Assignment 1 - By @author TeamZelda
 * <p>
 * Members Involved
 * 1. Tejanpreet Singh Dherdi
 * 2. Kevin Mathew Saji
 * 3. Adrian Gorny
 * 4. Gaurab Sapkota
 */
package appDomain;

import java.io.FileNotFoundException;
import java.util.Comparator;

import external.OriginalPriceCompare; 
import external.UpdatedPriceCompare;

import products.Product;

import utilities.BubbleSort;
import utilities.CustomSort;
import utilities.InsertionSort;
import utilities.MergeSort;
import utilities.ProductLoader;
import utilities.QuickSort;
import utilities.SelectionSort;

import exceptions.InvalidProductDataException;

/**
 * Entry point for the sorting benchmark application. Reads a product data
 * file named on the command line, sorts the products in descending order using
 * the requested algorithm and property, and reports the sort time along with a
 * sample of the sorted results.
 *
 * @author Tejanpreet Singh Dherdi 
 */
 
public class AppDriver
{

	/**
	 * Parses the command line arguments, loads the products, runs the chosen
	 * sort with timing, and displays the results. 
	 * 
	 *  Expects three arguments in any order: 
	 *  -f followed by the file name,
     *  -t followed by o, u or d, and 
     *  -s followed by b, s, i, m, q or z.
	 *
	 * Invalid input produces an explanatory message instead of an exception.
	 *
	 * @param args (the command line arguments)
	 */
	public static void main( String[] args )
	{
		String fileName = null;
		char compareType = 0;
		char sortAlg = 0;
		
		// Arguments may appear in any order, so each flag is inspected individually.
		
		for (String arg : args) {
			if( arg.length() < 3 || arg.charAt( 0 ) != '-' )
			{
				System.out.println( "Unrecognized argument: " + arg );
				return;
			}
			
			char flag = Character.toLowerCase(arg.charAt(1));
			String value = arg.substring(2);
			
			switch(flag)
			{
				case 'f':
					fileName = stripQuotes(value); 
					break;
				
				case 't':
					compareType = Character.toLowerCase(value.charAt(0));
					break;
					
				case's':
					sortAlg = Character.toLowerCase(value.charAt(0));
					break;
				
				default:
					printUsage("Unknown flag: -" + arg.charAt(1));
					return;
			}
		}
		
		if( fileName == null )
		{
			printUsage( "Missing required filename : -f<file_name>" );
			return;
		}
		
		if( "oud".indexOf( compareType ) < 0 )
		{
			printUsage( "Invalid compare type. Use -to, -tu or -td." );
			return;
		}
		
		if( "bsimqz".indexOf( sortAlg ) < 0 )
		{
			printUsage( "Invalid sort algorithm. Use -sb, -ss, -si, -sm, -sq or -sz." );
			return;
		}
		
		Product[] products;

		try
		{
			products = ProductLoader.load( fileName );
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "Error: could not find the file: " + fileName );
			System.out.println( "Check the path and try again." );
			return;
		}
		catch( InvalidProductDataException e )
		{
			System.out.println( "Error: the data file could not be read." );
			System.out.println( e.getMessage() );
			return;
		}

		if( products.length == 0 )
		{
			System.out.println( "The data file contains no products: " + fileName );
			return;
		}
		
		Comparator<Product> comp = resolveComparator( compareType );

		// Time the sort only; file reading and display are excluded.
		long start = System.nanoTime();
		runSort( products, sortAlg, comp );
		long elapsed = System.nanoTime() - start;

		displayResults( products, compareType );
		System.out.println( sortName( sortAlg ) + " run time was: " + elapsed + " nanoseconds" );
		
	}
	
	/**
	 * Removes a matching pair of surrounding double quotes from a path.
	 *
	 * @param value (the raw argument value)
	 * @return the value without wrapping quotes
	 */
	private static String stripQuotes( String value )
	{
		if( value.length() >= 2 && value.startsWith( "\"" ) && value.endsWith( "\"" ) )
		{
			return value.substring( 1, value.length() - 1 );
		}

		return value;
	}
	
	/**
	 * Returns the comparator matching the requested compare type.
	 *
	 * @param compareType (o for original price, u for updated price, d for the calculated difference)
	 * 
	 * @return a Comparator, or null when natural ordering should be used
	 */
	private static Comparator<Product> resolveComparator( char compareType )
	{
		switch( compareType )
		{
			case 'o':
				return new OriginalPriceCompare();
			case 'u':
				return new UpdatedPriceCompare();
			default:
				return null; // 'd' uses compareTo via calcRealDiff
		}
	}
	
	/**
	 * Sorts the array in descending order using the requested algorithm.
	 *
	 * @param array   (the products to sort, rearranged in place)
	 * @param sortAlg (b, s, i, m, q or z)
	 * @param comp    (the comparator to use, or null for natural ordering)
	 */
	private static void runSort( Product[] array, char sortAlg, Comparator<Product> comp )
	{
		switch( sortAlg )
		{
			case 'b':
				if( comp == null ) BubbleSort.sort( array );    else BubbleSort.sort( array, comp );
				break;
			case 's':
				if( comp == null ) SelectionSort.sort( array ); else SelectionSort.sort( array, comp );
				break;
			case 'i':
				if( comp == null ) InsertionSort.sort( array ); else InsertionSort.sort( array, comp );
				break;
			case 'm':
				if( comp == null ) MergeSort.sort( array );     else MergeSort.sort( array, comp );
				break;
			case 'q':
				if( comp == null ) QuickSort.sort( array );     else QuickSort.sort( array, comp );
				break;
			default:
				if( comp == null ) CustomSort.sort( array );    else CustomSort.sort( array, comp );
				break;
		}
	}
	
	/**
	 * Prints the sorted products at the interval required for the array size:
	 * every product for 10 or fewer, every 100th for 1000 or fewer, otherwise
	 * every 1000th, always including the first and last.
	 *
	 * @param array       (the sorted products)
	 * @param compareType (the property that was sorted on, used for labelling)
	 */
	private static void displayResults( Product[] array, char compareType )
	{
		int n = array.length;

		if( n <= 10 )
		{
			for( int i = 0; i < n; i++ )
			{
				System.out.printf( "%d-th element:\t%s\t%s%n",
						i + 1, array[i].getClass().getName(), valueLabel( array[i], compareType ) );
			}

			return;
		}

		int interval = ( n <= 1000 ) ? 100 : 1000;

		System.out.printf( "First element is:\t%s\t%s%n",
				array[0].getClass().getName(), valueLabel( array[0], compareType ) );

		for( int i = interval; i < n - 1; i += interval )
		{
			System.out.printf( "%d-th element:\t%s\t%s%n",
					i, array[i].getClass().getName(), valueLabel( array[i], compareType ) );
		}

		System.out.printf( "Last element is:\t%s\t%s%n",
				array[n - 1].getClass().getName(), valueLabel( array[n - 1], compareType ) );
	}

	/**
	 * Builds the labelled value for a product based on the compare type.
	 *
	 * @param product     (the product to describe)
	 * @param compareType (o, u or d)
	 * @return a label such as "Original Price: 92.99"
	 */
	private static String valueLabel( Product product, char compareType )
	{
		switch( compareType )
		{
			case 'o':
				return "Original Price: " + product.getOriginalPrice();
			case 'u':
				return "Updated Price: " + product.getUpdatedPrice();
			default:
				return "Real Difference: " + product.calcRealDiff();
		}
	}
	
	/**
	 * Returns the display name for a sort algorithm code.
	 *
	 * @param sortAlg b, s, i, m, q or z
	 * @return the human readable and understandable algorithm name
	 */
	private static String sortName( char sortAlg )
	{
		switch( sortAlg )
		{
			case 'b':  return "Bubble Sort";
			case 's':  return "Selection Sort";
			case 'i':  return "Insertion Sort";
			case 'm':  return "Merge Sort";
			case 'q':  return "Quick Sort";
			default:   return "Custom Sort";
		}
	}
	
	/**
	 * Prints an error message followed by the expected usage.
	 *
	 * @param message (the specific problem found in the arguments)
	 */
	private static void printUsage( String message )
	{
		System.out.println( "Error: " + message );
		System.out.println( "Usage: java -jar Sort.jar -f<file_name> -t<o|u|d> -s<b|s|i|m|q|z>" );
		System.out.println( "  -f  file name and path, no space after the flag" );
		System.out.println( "  -t  o = original price, u = updated price, d = calculated difference" );
		System.out.println( "  -s  b = bubble, s = selection, i = insertion, m = merge, q = quick, z = custom" );
		
	}
}


 