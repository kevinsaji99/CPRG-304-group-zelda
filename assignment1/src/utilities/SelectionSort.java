package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorting utility that arranges products using the selection sort algorithm.
 * <p>
 * Each pass scans the unsorted region for the largest remaining product and
 * swaps it into the boundary position, so the sorted region grows from the
 * front. Products end up in descending order (largest to smallest).
 * </p>
 * 
 * @author Tejanpreet Singh Dherdi
 */

public final class SelectionSort {

	/**
	 * Sorts the products in descending order using their natural ordering,
	 * which compares the calculated real difference of each product.
	 *
	 * @param array (the products to sort, rearranged in place)
	 */
	
	public static void sort(Product[] array) {

		selectionSort(array, null);

	}
	
	/**
	 * Sorts the products in descending order using the supplied comparator.
	 *
	 * @param array (the products to sort, rearranged in place)
	 * @param comp  (the comparator that defines the ordering)
	 */

	public static void sort(Product[] array, Comparator<Product> comp) {

		selectionSort(array, comp);

	}
	
	/**
	 * Performs the selection sort. On each pass the largest product in the
	 * unsorted region is located and swapped into the boundary position.
	 *
	 * @param array (the products to sort, rearranged in place)
	 * @param comp  (the comparator to use, or null to use natural ordering)
	 */

	public static void selectionSort(Product[] array, Comparator<Product> comp) {
		for (int i = 0; i < array.length - 1; i++) {
			int maxIndex = i;

			for (int j = i + 1; j < array.length; j++) {
				// Descending: track the largest product in the unsorted region
				if (compare(array[j], array[maxIndex], comp) > 0) {
					maxIndex = j;
				}
			}

			if (maxIndex != i) {
				swapElements(array, i, maxIndex);
			}
		}
	}
	
	/**
	 * Swaps the products at two positions in the array.
	 *
	 * @param array (the array containing the products)
	 * @param i     (the index of the first product)
	 * @param j     (the index of the second product)
	 */

	private static void swapElements(Product[] array, int i, int j) {
		Product temp = array[i];
		array[i] = array[j];
		array[j] = temp;

	}
	
	/**
	 * Compares two products using the comparator, or their natural ordering
	 * when no comparator is given.
	 *
	 * @param product1  (the first product)
	 * @param product2  (the second product)
	 * @param comp      (the comparator to use, or null to use natural ordering)
	 * @return a negative number, zero, or a positive number when p1 is less
	 *         than, equal to, or greater than p2
	 */

	private static int compare(Product product1, Product product2, Comparator<Product> comp) {
		if (comp == null) {
			return product1.compareTo(product2);
		}

		else {
			return comp.compare(product1, product2);
		}
	}
}
