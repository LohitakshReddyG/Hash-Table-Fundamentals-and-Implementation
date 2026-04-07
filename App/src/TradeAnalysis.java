import java.util.*;

class Trade {
    int id;
    int volume;

    Trade(int id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class TradeAnalysis {

    // Merge Sort (Ascending)
    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    private static void merge(Trade[] arr, int left, int mid, int right) {
        Trade[] temp = new Trade[right - left + 1];

        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i].volume <= arr[j].volume) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        for (int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
        }
    }

    //  Quick Sort (Descending)
    public static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(Trade[] arr, int low, int high) {
        int pivot = arr[high].volume;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].volume > pivot) { // DESC
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(Trade[] arr, int i, int j) {
        Trade temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    //  Merge Two Sorted Arrays
    public static Trade[] mergeTwoSorted(Trade[] a, Trade[] b) {
        int i = 0, j = 0, k = 0;
        Trade[] result = new Trade[a.length + b.length];
        while (i < a.length && j < b.length) {
            if (a[i].volume <= b[j].volume) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];

        return result;
    }
    //  Total Volume
    public static int totalVolume(Trade[] arr) {
        int sum = 0;
        for (Trade t : arr) {
            sum += t.volume;
        }
        return sum;
    }
    //  Main Method
    public static void main(String[] args) {
        Trade[] trades = {
                new Trade(3, 500),
                new Trade(1, 100),
                new Trade(2, 300)
        };
        // Merge Sort (ASC)
        mergeSort(trades, 0, trades.length - 1);
        System.out.println("Merge Sort (ASC): " + Arrays.toString(trades));

        // Quick Sort (DESC)
        quickSort(trades, 0, trades.length - 1);
        System.out.println("Quick Sort (DESC): " + Arrays.toString(trades));
        // Morning & Afternoon sessions
        Trade[] morning = {
                new Trade(1, 100),
                new Trade(2, 300)
        };

        Trade[] afternoon = {
                new Trade(3, 500)
        };
        // Merge both sessions
        Trade[] merged = mergeTwoSorted(morning, afternoon);
        System.out.println("Merged Trades: " + Arrays.toString(merged));
        // Total volume
        System.out.println("Total Volume: " + totalVolume(merged));
    }
}