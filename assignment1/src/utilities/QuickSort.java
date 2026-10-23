package utilities;

import java.util.Comparator;

/**
 * @author Gaurab Sapkota
 */

public class QuickSort {

  
    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException(
                    "Comparator cannot be null.");
        }

        if (array == null || array.length < 2) {
            return;
        }

        quickSort(array, 0, array.length - 1, comparator);
    }




  
    private static <T> void quickSort(
            T[] array, int low, int high,
            Comparator<? super T> comparator) {

        if (low >= high) {
            return;
        }

        int middle = low + (high - low) / 2;

        T pivot = medianOfThree(
                array[low], array[middle], array[high], comparator);

        int greater = low;
        int current = low;
        int smaller = high;

        while (current <= smaller) {
            int result = comparator.compare(array[current], pivot);

            if (result > 0) {
                swap(array, greater, current);
                greater++;
                current++;
            } else if (result < 0) {
                swap(array, current, smaller);
                smaller--;
            } else {
                current++;
            }
        }

        quickSort(array, low, greater - 1, comparator);
        quickSort(array, smaller + 1, high, comparator);
    }



  
    private static <T> T medianOfThree(
            T first, T second, T third,
            Comparator<? super T> comparator) {

        if (comparator.compare(first, second) < 0) {
            if (comparator.compare(second, third) < 0) {
                return second;
            } else if (comparator.compare(first, third) < 0) {
                return third;
            } else {
                return first;
            }
        } else {
            if (comparator.compare(first, third) < 0) {
                return first;
            } else if (comparator.compare(second, third) < 0) {
                return third;
            } else {
                return second;
            }
        }
    }


  
    private static <T> void swap(T[] array, int first, int second) {
        T temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }
}
