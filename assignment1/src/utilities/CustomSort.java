package utilities;

import java.util.Comparator;
 
import products.Product;
  
public final class CustomSort
{
	public static void sort( Product[] array )
	{
		shellSort( array, null );
	}
 
	public static void sort( Product[] array, Comparator<Product> comp )
	{
		shellSort( array, comp );
	}
 
	private static void shellSort( Product[] array, Comparator<Product> comp )
	{
		if( array == null || array.length < 2 )
		{
			return;
		}
 
		for( int gap = array.length / 2; gap > 0; gap /= 2 )
		{
			for( int i = gap; i < array.length; i++ )
			{
				Product current = array[i];
				int j = i;
 
				// Descending: shift smaller products toward the end of the array.
				while( j >= gap && compare( array[j - gap], current, comp ) < 0 )
				{
					array[j] = array[j - gap];
					j -= gap;
				}
 
				array[j] = current;
			}
		}
	}
 
	private static int compare( Product p1, Product p2, Comparator<Product> comp )
	{
		if( comp == null )
		{
			return p1.compareTo( p2 );
		}
		else
		{
			return comp.compare( p1, p2 );
		}
	}
}