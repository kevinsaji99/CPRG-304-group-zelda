package utilities;

import java.util.Comparator;

import products.Product;

/**
 * Quick sort utility that arranges products in descending order.
 *
 * @author Gaurab Sapkota
 */
public final class QuickSort
{
    private QuickSort()
    {
    }

    public static void sort(Product[] array)
    {
        sort(array, null);
    }

    public static void sort(Product[] array, Comparator<Product> comp)
    {
        if (array == null || array.length < 2)
        {
            return;
        }

        quickSort(array, 0, array.length - 1, comp);
    }

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

    private static void swap(Product[] array,
                             int first,
                             int second)
    {
        Product temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }
}
