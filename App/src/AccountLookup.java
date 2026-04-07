import java.util.*;

public class AccountLookup {

    //  Linear Search: First Occurrence
    public static int linearFirst(String[] arr, String target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear First -> Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear First -> Comparisons: " + comparisons);
        return -1;
    }

    //  Linear Search: Last Occurrence
    public static int linearLast(String[] arr, String target) {
        int comparisons = 0;

        for (int i = arr.length - 1; i >= 0; i--) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear Last -> Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear Last -> Comparisons: " + comparisons);
        return -1;
    }

    //  Binary Search: Find any occurrence
    public static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            int cmp = arr[mid].compareTo(target);

            if (cmp == 0) {
                System.out.println("Binary Search -> Comparisons: " + comparisons);
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary Search -> Comparisons: " + comparisons);
        return -1;
    }

    //  Find First Occurrence using Binary Search
    public static int binaryFirst(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                high = mid - 1; // go left
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    //  Find Last Occurrence using Binary Search
    public static int binaryLast(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                low = mid + 1; // go right
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    //  Count occurrences
    public static int countOccurrences(String[] arr, String target) {
        int first = binaryFirst(arr, target);
        int last = binaryLast(arr, target);

        if (first == -1) return 0;
        return last - first + 1;
    }

    // Main Method
    public static void main(String[] args) {

        String[] logs = {"accB", "accA", "accB", "accC"};

        // Linear Search
        int firstLinear = linearFirst(logs, "accB");
        int lastLinear = linearLast(logs, "accB");

        System.out.println("Linear First Index: " + firstLinear);
        System.out.println("Linear Last Index: " + lastLinear);

        // Sort before Binary Search
        Arrays.sort(logs);
        System.out.println("\nSorted Logs: " + Arrays.toString(logs));

        // Binary Search
        int index = binarySearch(logs, "accB");
        int count = countOccurrences(logs, "accB");

        System.out.println("Binary Found Index: " + index);
        System.out.println("Total Count: " + count);
    }
}