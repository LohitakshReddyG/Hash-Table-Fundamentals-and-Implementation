import java.util.*;

public class RiskThresholdLookup {

    // 🔹 Linear Search (Unsorted)
    public static int linearSearch(int[] arr, int target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear -> Found at index " + i +
                        ", Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear -> Not Found, Comparisons: " + comparisons);
        return -1;
    }

    // 🔹 Binary Search (Find insertion point / lower_bound)
    public static int binaryInsertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                System.out.println("Binary -> Found at index " + mid +
                        ", Comparisons: " + comparisons);
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary -> Not Found, Insertion Index: " + low +
                ", Comparisons: " + comparisons);
        return low; // insertion position
    }

    // 🔹 Floor (largest ≤ target)
    public static int findFloor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int floor = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                System.out.println("Floor Comparisons: " + comparisons);
                return arr[mid];
            } else if (arr[mid] < target) {
                floor = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Floor Comparisons: " + comparisons);
        return floor;
    }

    // 🔹 Ceiling (smallest ≥ target)
    public static int findCeiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int ceil = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                System.out.println("Ceiling Comparisons: " + comparisons);
                return arr[mid];
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                ceil = arr[mid];
                high = mid - 1;
            }
        }

        System.out.println("Ceiling Comparisons: " + comparisons);
        return ceil;
    }

    // 🧪 Main Method
    public static void main(String[] args) {

        int[] unsorted = {50, 10, 100, 25};
        int target = 30;

        // Linear Search (unsorted)
        linearSearch(unsorted, target);

        // Sort for Binary Search
        int[] sorted = {10, 25, 50, 100};
        System.out.println("\nSorted Risks: " + Arrays.toString(sorted));

        // Binary Search (insertion point)
        int index = binaryInsertionPoint(sorted, target);

        // Floor & Ceiling
        int floor = findFloor(sorted, target);
        int ceil = findCeiling(sorted, target);

        System.out.println("\nResult:");
        System.out.println("Insertion Index: " + index);
        System.out.println("Floor: " + floor);
        System.out.println("Ceiling: " + ceil);
    }
}