import java.util.*;

public class Problem2 {
    private HashMap<String, Integer> inventory = new HashMap<>();
    private HashMap<String, LinkedHashMap<Integer, Boolean>> waitingList = new HashMap<>();

    public void addProduct(String productId, int stock) {
        inventory.put(productId, stock);
        waitingList.put(productId, new LinkedHashMap<>());
    }

    public void checkStock(String productId) {
        if (!inventory.containsKey(productId)) {
            System.out.println("Product not found.");
            return;
        }
        int stock = inventory.get(productId);
        System.out.println(productId + " → " + stock + " units available");
    }

    public synchronized void purchaseItem(String productId, int userId) {
        if (!inventory.containsKey(productId)) {
            System.out.println("Product does not exist.");
            return;
        }
        int stock = inventory.get(productId);
        if (stock > 0) {
            stock--;
            inventory.put(productId, stock);
            System.out.println("Success! Purchase complete.");
            System.out.println("Remaining stock: " + stock);
        }
        else {
            LinkedHashMap<Integer, Boolean> queue = waitingList.get(productId);
            queue.put(userId, true);
            int position = queue.size();
            System.out.println("Stock unavailable.");
            System.out.println("User added to waiting list. Position #" + position);
        }
    }

    public void showWaitingList(String productId) {
        if (!waitingList.containsKey(productId)) {
            System.out.println("Product not found.");
            return;
        }
        LinkedHashMap<Integer, Boolean> queue = waitingList.get(productId);
        if (queue.isEmpty()) {
            System.out.println("Waiting list is empty.");
            return;
        }
        System.out.println("Waiting List (FIFO):");
        for (Integer user : queue.keySet()) {
            System.out.println("User ID: " + user);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Problem2 manager = new Problem2();
        while (true) {
            System.out.println("\n===== Flash Sale Inventory Manager =====");
            System.out.println("1. Add Product");
            System.out.println("2. Check Stock");
            System.out.println("3. Purchase Product");
            System.out.println("4. Show Waiting List");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    String productId = sc.nextLine();
                    System.out.print("Enter Stock Count: ");
                    int stock = sc.nextInt();
                    manager.addProduct(productId, stock);
                    System.out.println("Product added successfully.");
                    break;
                case 2:
                    System.out.print("Enter Product ID: ");
                    String checkId = sc.nextLine();
                    manager.checkStock(checkId);
                    break;
                case 3:
                    System.out.print("Enter Product ID: ");
                    String buyId = sc.nextLine();
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    manager.purchaseItem(buyId, userId);
                    break;
                case 4:
                    System.out.print("Enter Product ID: ");
                    String waitId = sc.nextLine();
                    manager.showWaitingList(waitId);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}