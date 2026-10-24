package utilities;
 
import java.util.Comparator;
 
import products.Product;
 
// The "custom sort" required by this assignment is implemented as a Shell Sort.
 
/**
 * Custom sorting utility (the assignment's custom sort is a SHELL SORT) that
 * arranges products using the shell sort algorithm.
 * <p>
 * Shell sort is an improved insertion sort. Instead of only comparing
 * neighbouring products, it first compares and moves products that are far
 * apart (the "gap"). The gap then shrinks each round until it reaches 1, which
 * is a final insertion sort pass over an array that is already almost sorted.
 * Moving products long distances early on means far fewer shifts are needed
 * later. Products end up in descending order (largest to smallest).
 * </p>
 * <p>
 * The gap sequence used here starts at half the array length and halves each
 * round (n/2, n/4, ... 1). With this sequence the worst case is O(n^2), and
 * in practice it is usually much faster than a plain insertion sort. It sorts
 * in place using O(1) extra space and is not stable.
 * </p>
 *
 * @author Adrian Gorny
 */
public final class CustomSort
{
	/**
	 * Private constructor that prevents instantiation, since this class only
	 * contains static sorting methods. It sets up nothing and takes no inputs.
	 */
	private CustomSort()
	{
	}
 
	/**
	 * Custom sort (shell sort): sorts the products in descending order using
	 * their natural ordering, as defined by {@link Product#compareTo(Product)}.
	 *
	 * @param array the products to sort; sorted in place
	 * @throws NullPointerException if the array contains a {@code null} product
	 */
	public static void sort( Product[] array )
	{
		shellSort( array, null );
	}
 
	/**
	 * Custom sort (shell sort): sorts the products in descending order using
	 * the supplied comparator.
	 *
	 * @param array the products to sort; sorted in place
	 * @param comp  the comparator that decides the order of two products, or
	 * {@code null} to use the natural ordering of {@link Product}
	 * @throws NullPointerException if the array contains a {@code null} product
	 */
	public static void sort( Product[] array, Comparator<Product> comp )
	{
		shellSort( array, comp );
	}
 
	/**
	 * Performs the shell sort (the custom sort). For each gap, every product from index
	 * {@code gap} onward is insertion-sorted among the products that are a
	 * multiple of {@code gap} positions before it.
	 *
	 * @param array the products to sort
	 * @param comp  the comparator to use, or {@code null} for natural ordering
	 * @throws NullPointerException if the array contains a {@code null} product
	 */
	private static void shellSort( Product[] array, Comparator<Product> comp )
	{
		if( array == null || array.length < 2 )
		{
			return;
		}
 
		// Start with a large gap and halve it each round until it reaches 1.
		for( int gap = array.length / 2; gap > 0; gap /= 2 )
		{
			// Gapped insertion sort: treat products that are 'gap' apart as a sub-list.
			for( int i = gap; i < array.length; i++ )
			{
				Product current = array[i]; // the product being placed
				int j = i;
 
				// Descending: shift smaller products toward the end of the array
				// until the correct spot for 'current' is found.
				while( j >= gap && compare( array[j - gap], current, comp ) < 0 )
				{
					array[j] = array[j - gap];
					j -= gap;
				}
 
				array[j] = current;
			}
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
	 *  or if {@code p2} is {@code null} and the comparison needs to read it
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