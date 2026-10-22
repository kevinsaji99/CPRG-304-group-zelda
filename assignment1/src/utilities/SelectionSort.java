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
 */

public final class SelectionSort {

	public static void sort(Product[] array) {

		selectionSort(array, null);

	}

	public static void sort(Product[] array, Comparator<Product> comp) {

		selectionSort(array, comp);

	}

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

	private static void swapElements(Product[] array, int i, int j) {
		Product temp = array[i];
		array[i] = array[j];
		array[j] = temp;

	}

	private static int compare(Product product1, Product product2, Comparator<Product> comp) {
		if (comp == null) {
			return product1.compareTo(product2);
		}

		else {
			return comp.compare(product1, product2);
		}
	}
}
