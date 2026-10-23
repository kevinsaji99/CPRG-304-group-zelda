package utilities;

import java.util.Comparator;
 
import products.Product;
 
public final class MergeSort
{
	public static void sort( Product[] array )
	{
		mergeSort( array, null );
	}
 
	public static void sort( Product[] array, Comparator<Product> comp )
	{
		mergeSort( array, comp );
	}
 
	private static void mergeSort( Product[] array, Comparator<Product> comp )
	{
		if( array == null || array.length < 2 )
		{
			return;
		}
 
		Product[] temp = new Product[array.length];
		split( array, temp, 0, array.length - 1, comp );
	}
 
	private static void split( Product[] array, Product[] temp, int low, int high, Comparator<Product> comp )
	{
		if( low >= high )
		{
			return;
		}
 
		int mid = low + ( high - low ) / 2;
 
		split( array, temp, low, mid, comp );
		split( array, temp, mid + 1, high, comp );
		merge( array, temp, low, mid, high, comp );
	}
 
	private static void merge( Product[] array, Product[] temp, int low, int mid, int high, Comparator<Product> comp )
	{
		int left = low;
		int right = mid + 1;
		int index = low;
 
		while( left <= mid && right <= high )
		{
			// Descending: take from the left half when it is greater than or equal to the right.
			if( compare( array[left], array[right], comp ) >= 0 )
			{
				temp[index++] = array[left++];
			}
			else
			{
				temp[index++] = array[right++];
			}
		}
 
		while( left <= mid )
		{
			temp[index++] = array[left++];
		}
 
		while( right <= high )
		{
			temp[index++] = array[right++];
		}
 
		for( int i = low; i <= high; i++ )
		{
			array[i] = temp[i];
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
