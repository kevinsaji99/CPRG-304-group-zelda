package utilities;

import java.util.Comparator;

import products.Product;

/**
 * Quick sort utility that arranges products in descending order.
 *It uses median-of-three pivot selection and three-way partitioning.
 *
 * @author Gaurab Sapkota
 */
public final class QuickSort
{
     /**
     * Private constructor that prevents instances of this utility class
     * from being created.
     */
    private QuickSort()
    {
    }
/**
     * Sorts the products in descending order using their natural ordering.
     *
     * @param array the array of products to sort in place
     * @throws NullPointerException if a product comparison encounters null
     */
    public static void sort(Product[] array)
    {
        sort(array, null);
    }
/**
     * Sorts the products in descending order using the supplied comparator,
     * or their natural ordering if the comparator is null.
     *
     * @param array the array of products to sort in place
     * @param comp the comparator to use, or null for natural ordering
     * @throws NullPointerException if a product comparison encounters null
     */
    public static void sort(Product[] array, Comparator<Product> comp)
    {
        if (array == null || array.length < 2)
        {
            return;
        }

        quickSort(array, 0, array.length - 1, comp);
    }

     /**
     * Sorts the specified section of the array using three-way partitioning.
     * Products greater than the pivot are moved to the left, smaller products
     * to the right, and equal products remain in the middle section.
     *
     * @param array the array being sorted
     * @param low the first index of the section to sort
     * @param high the last index of the section to sort
     * @param comp the comparator to use, or null for natural ordering
     * @throws NullPointerException if a product comparison encounters null
     */
    private static void quickSort(Product[] array,
                                  int low,
                                  int high,
                                  Comparator<Product> comp)
    {
        if (low >= high)
        {
            return;
        }

        int middle = low + (high - low) / 2;

        Product pivot = medianOfThree(
                array[low],
                array[middle],
                array[high],
                comp);

        int greater = low;
        int current = low;
        int smaller = high;

        while (current <= smaller)
        {
            int result = compare(array[current], pivot, comp);

            // Descending order:
            // larger elements move left
            if (result > 0)
            {
                swap(array, greater, current);
                greater++;
                current++;
            }
            else if (result < 0)
            {
                swap(array, current, smaller);
                smaller--;
            }
            else
            {
                current++;
            }
        }

        quickSort(array, low, greater - 1, comp);
        quickSort(array, smaller + 1, high, comp);
    }
   /**
     * Selects the median of three products according to the chosen ordering.
     *
     * @param first the first product
     * @param second the second product
     * @param third the third product
     * @param comp the comparator to use, or null for natural ordering
     * @return the product whose ordering falls between the other two
     * @throws NullPointerException if a product comparison encounters null
     */
    private static Product medianOfThree(Product first,
                                         Product second,
                                         Product third,
                                         Comparator<Product> comp)
    {
        if (compare(first, second, comp) < 0)
        {
            if (compare(second, third, comp) < 0)
            {
                return second;
            }
            else if (compare(first, third, comp) < 0)
            {
                return third;
            }
            else
            {
                return first;
            }
        }
        else
        {
            if (compare(first, third, comp) < 0)
            {
                return first;
            }
            else if (compare(second, third, comp) < 0)
            {
                return third;
            }
            else
            {
                return second;
            }
        }
    }
 /**
     * Compares two products using the supplied comparator or their natural
     * ordering when no comparator is provided.
     *
     * @param p1 the first product
     * @param p2 the second product
     * @param comp the comparator to use, or null for natural ordering
     * @return a negative integer, zero, or a positive integer according to
     *         the ordering of the two products
     * @throws NullPointerException if the comparison operation encounters null
     */
    private static int compare(Product p1,
                               Product p2,
                               Comparator<Product> comp)
    {
        if (comp == null)
        {
            return p1.compareTo(p2);
        }
        else
        {
            return comp.compare(p1, p2);
        }
    }
 /**
     * Exchanges the products at two positions in the array.
     *
     * @param array the array containing the products
     * @param first the index of the first product
     * @param second the index of the second product
     */
    private static void swap(Product[] array,
                             int first,
                             int second)
    {
        Product temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }
}
