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
 * <p>
 * Sorting can be performed using either the natural ordering defined in
 * {@link Product#compareTo(Product)} or a custom {@link Comparator}.
 * </p>
 *
 * @author Kevin Saji
 */
public final class BubbleSort
{
	/**
	 * Sorts the array in descending order using the natural ordering of
	 * {@code Product} (the calculated real difference).
	 *
	 * @param array the array of products to sort
	 */
	public static void sort( Product[] array )
	{
		bubbleSort( array, null );
	}
 
	/**
	 * Sorts the array in descending order using the provided comparator.
	 *
	 * @param array the array of products to sort
	 * @param comp the comparator defining the order (original or updated price)
	 */
	public static void sort( Product[] array, Comparator<Product> comp )
	{
		bubbleSort( array, comp );
	}
 
	/**
	 * Performs the bubble sort on the array, in place.
	 * <p>
	 * Each pass moves the smallest remaining product to the end of the
	 * unsorted section. The sort stops early when a pass makes no swaps.
	 * </p>
	 *
	 * @param array the array of products to sort
	 * @param comp the comparator to use, or {@code null} for natural ordering
	 */
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
 
	/**
	 * Compares two products using the comparator if one is provided, or the
	 * natural ordering of {@code Product} otherwise.
	 *
	 * @param p1 the first product
	 * @param p2 the second product
	 * @param comp the comparator to use, or {@code null} for natural ordering
	 * @return a negative integer, zero, or a positive integer as p1 is less
	 *         than, equal to, or greater than p2
	 */
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
 
	/**
	 * Swaps two elements in the array.
	 *
	 * @param array the array containing the elements
	 * @param i the index of the first element
	 * @param j the index of the second element
	 */
	private static void swapElements( Product[] array, int i, int j )
	{
		Product temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}