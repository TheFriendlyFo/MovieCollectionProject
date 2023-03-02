import java.util.ArrayList;

public class QuickSort {

    public static <T extends Comparable> void sort(ArrayList<T> sortArray) {
        sort(sortArray, 0, sortArray.size() - 1, 0);
    }

    public static <T extends Comparable> void sort(ArrayList<T> sortArray, int sortType) {
        sort(sortArray, 0, sortArray.size() - 1, sortType);
    }

    public static void sortStr(ArrayList<String> sortArray) {
        sortStr(sortArray, 0, sortArray.size() - 1);
    }


    private static <T extends Comparable> void sort(ArrayList<T> sortArray, int left, int right, int sortType) {
        if (left >= right) return;

        Comparable pivot = sortArray.get((left + right) / 2);
        int index = partition(sortArray, left, right, pivot, sortType);
        sort(sortArray, left, index - 1, sortType);
        sort(sortArray, index, right, sortType);
    }

    private static <T extends Comparable> int partition(ArrayList<T> sortArray, int left, int right, Comparable pivot, int sortType) {
        while (left <= right) {
            while (sortArray.get(left).compareTo(pivot, sortType) < 0) {
                left++;
            }
            while (sortArray.get(right).compareTo(pivot, sortType) > 0) {
                right--;
            }

            if (left <= right) {
                swap(sortArray, left, right);
                left++;
                right--;
            }
        }
        return left;
    }

    private static <T extends Comparable> void swap(ArrayList<T> sortArray, int left, int right) {
        sortArray.set(left, sortArray.set(right, sortArray.get(left)));
    }

    private static void sortStr(ArrayList<String> sortArray, int left, int right) {
        if (left >= right) return;

        String pivot = sortArray.get((left + right) / 2);
        int index = partitionStr(sortArray, left, right, pivot);
        sortStr(sortArray, left, index - 1);
        sortStr(sortArray, index, right);
    }

    private static int partitionStr(ArrayList<String> sortArray, int left, int right, String pivot) {
        while (left <= right) {
            while (sortArray.get(left).compareTo(pivot) < 0) {
                left++;
            }
            while (sortArray.get(right).compareTo(pivot) > 0) {
                right--;
            }

            if (left <= right) {
                swapStr(sortArray, left, right);
                left++;
                right--;
            }
        }
        return left;
    }

    private static void swapStr(ArrayList<String> sortArray, int left, int right) {
        sortArray.set(left, sortArray.set(right, sortArray.get(left)));
    }
}
