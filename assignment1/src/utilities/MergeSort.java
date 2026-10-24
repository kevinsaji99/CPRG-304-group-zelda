package utilities;
 
import java.util.Comparator;
 
import products.Product;
 
/**
 * Sorting utility that arranges products using the merge sort algorithm.
 * <p>
 * Merge sort is a divide-and-conquer algorithm. The array is repeatedly split
 * in half until each piece holds a single product (which is already sorted),
 * then neighbouring pieces are merged back together in sorted order. Products
 * end up in descending order (largest to smallest).
 * </p>
 * <p>
 * Time complexity is O(n log n) in the best, average and worst cases. It uses
 * O(n) extra space for a temporary array, and it is stable: products that
 * compare as equal keep their original relative order.
 * </p>
 *
 * @author Adrian Gorny
 */
public final class MergeSort
{
	/**
	 * Private constructor that prevents instantiation, since this class only
	 * contains static sorting methods. It sets up nothing and takes no inputs.
	 */
	private MergeSort()
	{
	}
 
	/**
	 * Sorts the products in descending order using their natural ordering,
	 * as defined by {@link Product#compareTo(Product)}.
	 *
	 * @param array the products to sort; sorted in place
	 * @throws NullPointerException if the array contains a {@code null} product
	 */
	public static void sort( Product[] array )
	{
		mergeSort( array, null );
	}
 
	/**
	 * Sorts the products in descending order using the supplied comparator.
	 *
	 * @param array the products to sort; sorted in place
	 * @param comp  the comparator that decides the order of two products, or
	 *              {@code null} to use the natural ordering of {@link Product}
	 * @throws NullPointerException if the array contains a {@code null} product
	 */
	public static void sort( Product[] array, Comparator<Product> comp )
	{
		mergeSort( array, comp );
	}
 
	/**
	 * Entry point for the sort. Does nothing for a null or single-element array,
	 * otherwise creates the shared temporary array and starts the recursion.
	 *
	 * @param array the products to sort
	 * @param comp  the comparator to use, or {@code null} for natural ordering
	 * @throws NullPointerException if the array contains a {@code null} product
	 */
	private static void mergeSort( Product[] array, Comparator<Product> comp )
	{
		if( array == null || array.length < 2 )
		{
			return;
		}
 
		// One temporary array is created once and reused by every merge.
		Product[] temp = new Product[array.length];
		split( array, temp, 0, array.length - 1, comp );
	}
 
	/**
	 * Recursively splits the range {@code low..high} in half, sorts each half,
	 * then merges the two sorted halves.
	 *
	 * @param array the array being sorted
	 * @param temp  the temporary array used while merging
	 * @param low   the first index of the range (inclusive)
	 * @param high  the last index of the range (inclusive)
	 * @param comp  the comparator to use, or {@code null} for natural ordering
	 * @throws NullPointerException if the range contains a {@code null} product
	 */
	private static void split( Product[] array, Product[] temp, int low, int high, Comparator<Product> comp )
	{
		// A range of zero or one products is already sorted.
		if( low >= high )
		{
			return;
		}
 
		int mid = low + ( high - low ) / 2; // avoids int overflow of (low + high)
 
		split( array, temp, low, mid, comp );
		split( array, temp, mid + 1, high, comp );
		merge( array, temp, low, mid, high, comp );
	}
 
	/**
	 * Merges two sorted neighbouring ranges, {@code low..mid} and
	 * {@code mid+1..high}, into one sorted range in descending order.
	 *
	 * @param array the array being sorted
	 * @param temp  the temporary array the merged result is built in
	 * @param low   the first index of the left range
	 * @param mid   the last index of the left range
	 * @param high  the last index of the right range
	 * @param comp  the comparator to use, or {@code null} for natural ordering
	 * @throws NullPointerException if the ranges contain a {@code null} product
	 */
	private static void merge( Product[] array, Product[] temp, int low, int mid, int high, Comparator<Product> comp )
	{
		int left = low;
		int right = mid + 1;
		int index = low;
 
		// Repeatedly take the larger front product from the two ranges.
		while( left <= mid && right <= high )
		{
			// Descending: take from the left half when it is greater than or equal to the right.
			// Using >= (not >) takes the left product on ties, which keeps the sort stable.
			if( compare( array[left], array[right], comp ) >= 0 )
			{
				temp[index++] = array[left++];
			}
			else
			{
				temp[index++] = array[right++];
			}
		}
 
		// Copy over whatever is left in the left range.
		while( left <= mid )
		{
			temp[index++] = array[left++];
		}
 
		// Copy over whatever is left in the right range.
		while( right <= high )
		{
			temp[index++] = array[right++];
		}
 
		// Copy the merged range back into the original array.
		for( int i = low; i <= high; i++ )
		{
			array[i] = temp[i];
		}
	}
 
	/**
	 * Compares two products using the comparator, or their natural ordering
	 * when no comparator is given.
	 *
	 * @param p1   the first product
	 * @param p2   the second product
	 * @param comp the comparator to use, or {@code null} for natural ordering
	 * @return a negative number, zero, or a positive number when {@code p1} is
	 * less than, equal to, or greater than {@code p2}
	 * @throws NullPointerException if {@code p1} is {@code null}, 
	 * or if {@code p2} is {@code null} and the comparison needs to read it
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
}
 