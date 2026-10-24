package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorting utility that arranges products using the insertion sort algorithm.
 * <p>
 * Each pass removes the next unsorted product and shifts every smaller
 * product in the sorted region one position to the right, then drops the
 * product into the gap that opens up. Products end up in descending order
 * (largest to smallest).
 * </p>
 * 
 * @author Tejanpreet Singh Dherdi
 */


public class InsertionSort {
	
	/**
	 * Sorts the products in descending order using their natural ordering,
	 * which compares the calculated real difference of each product.
	 *
	 * @param array (the products to sort, rearranged in place)
	 */
	
	public static void sort (Product[] array)
	{
		insertionSort(array , null);
	}
	
	/**
	 * Sorts the products in descending order using the supplied comparator.
	 *
	 * @param array (the products to sort, rearranged in place)
	 * @param comp  (the comparator that defines the ordering)
	 */
	public static void sort(Product[] array , Comparator<Product> comp)
	{
		insertionSort( array , comp);
    }
	
	private static void insertionSort(Product[] array , Comparator<Product> comp)
	{
		// array[0] is treated as a sorted region of one, so start at index 1.
		for (int i = 1 ; i< array.length ; i++)
		{
			Product key = array[i];
			int j = i-1;
			
			// Descending: shift products smaller than the key one slot right.
		    // The bounds check must come first so array[j] is never read at j = -1.
			
			while (j >= 0 && compare(array[j] , key , comp) < 0){
				array[j+1] = array[j];
				j--;
				
			}
			
			// j now sits one slot before the key's correct position.
			array[j+1] = key;
			
		}
	}

	private static int compare(Product product1, Product product2 , Comparator<Product> comp) {
		if( comp == null )
		{
			return product1.compareTo( product2 );
		}
		else
		{
			return comp.compare(  product1 , product2 );
		}
	}
	
	
}
