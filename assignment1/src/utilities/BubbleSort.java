package utilities;

import java.util.Comparator;

import products.Product;

/**
 * Sorting utility that arranges products using the bubble sort algorithm.
 * <p>
 * Each pass compares adjacent products and swaps them when the left one
 * is smaller, so the smallest remaining product moves to the end of the
 * array. Sorting stops early if a pass makes no swaps. Products end up in
 * descending order (largest to smallest).
 * </p>
 */
public final class BubbleSort
{
	public static void sort( Product[] array )
	{
		bubbleSort( array, null );
	}

	public static void sort( Product[] array, Comparator<Product> comp )
	{
		bubbleSort( array, comp );
	}

	private static void bubbleSort( Product[] array, Comparator<Product> comp )
	{
		for( int pass = 0; pass < array.length - 1; pass++ )
		{
			boolean swapped = false;

			for( int j = 0; j < array.length - 1 - pass; j++ )
			{
				// Descending: swap when the left product is smaller than the right one.
				if( compare( array[j], array[j + 1], comp ) < 0 )
				{
					swapElements( array, j, j + 1 );
					swapped = true;
				}
			}

			if( !swapped )
			{
				break; // no swaps means the array is already sorted
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

	private static void swapElements( Product[] array, int i, int j )
	{
		Product temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
