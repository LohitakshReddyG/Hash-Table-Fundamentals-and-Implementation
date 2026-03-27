import java.util.*;

class Tran {
    int id;
    double amount;
    String merchant;
    String account;
    long timestamp; // in milliseconds

    Tran(int id, double amount, String merchant, String account, long timestamp) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.timestamp = timestamp;
    }
}

public class Problem9 {

    private List<Tran> transactions = new ArrayList<>();

    // Add transaction
    public void addTransaction(Tran t) {
        transactions.add(t);
    }

    // Classic Two-Sum
    public List<int[]> findTwoSum(double target) {
        List<int[]> result = new ArrayList<>();
        Map<Double, Transaction> map = new HashMap<>();

        for (Tran t : transactions) {
            double complement = target - t.amount;
            if (map.containsKey(complement)) {
                result.add(new int[]{map.get(complement).id, t.id});
            }
            map.put(t.amount, t);
        }
        return result;
    }

    // Two-Sum with time window in milliseconds
    public List<int[]> findTwoSumInWindow(double target, long windowMs) {
        List<int[]> result = new ArrayList<>();
        Map<Double, List<Transaction>> map = new HashMap<>();

        for (Tran t : transactions) {
            double complement = target - t.amount;
            if (map.containsKey(complement)) {
                for (Tran other : map.get(complement)) {
                    if (Math.abs(t.timestamp - other.timestamp) <= windowMs) {
                        result.add(new int[]{other.id, t.id});
                    }
                }
            }
            map.putIfAbsent(t.amount, new ArrayList<>());
            map.get(t.amount).add(t);
        }
        return result;
    }

    // K-Sum (recursive)
    public List<List<Integer>> findKSum(int k, double target) {
        List<List<Integer>> result = new ArrayList<>();
        findKSumHelper(transactions, 0, k, target, new ArrayList<>(), result);
        return result;
    }

    private void findKSumHelper(List<Transaction> list, int start, int k, double target,
                                List<Integer> path, List<List<Integer>> result) {
        if (k == 0) {
            if (Math.abs(target) < 0.001) { // floating point tolerance
                result.add(new ArrayList<>(path));
            }
            return;
        }

        for (int i = start; i < list.size(); i++) {
            path.add(list.get(i).id);
            findKSumHelper(list, i + 1, k - 1, target - list.get(i).amount, path, result);
            path.remove(path.size() - 1);
        }
    }

    // Detect duplicates: same amount, same merchant, different accounts
    public List<String> detectDuplicates() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> duplicates = new ArrayList<>();

        for (Tran t : transactions) {
            String key = t.amount + "|" + t.merchant;
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.account);
        }

        for (String key : map.keySet()) {
            Set<String> accounts = new HashSet<>(map.get(key));
            if (accounts.size() > 1) {
                duplicates.add(key + ", accounts: " + accounts);
            }
        }
        return duplicates;
    }

    // Sample main with input from user
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Problem9 system = new Problem9();

        while (true) {
            System.out.println("\n===== Transaction System =====");
            System.out.println("1. Add Transaction");
            System.out.println("2. Find Two-Sum");
            System.out.println("3. Find Two-Sum in 1-hour Window");
            System.out.println("4. Find K-Sum");
            System.out.println("5. Detect Duplicates");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Merchant: ");
                    String merchant = sc.nextLine();
                    System.out.print("Account: ");
                    String account = sc.nextLine();
                    System.out.print("Timestamp (ms): ");
                    long ts = sc.nextLong();
                    sc.nextLine();
                    system.addTransaction(new Transaction(id, amount, merchant, account, ts));
                    System.out.println("Transaction added.");
                    break;

                case 2:
                    System.out.print("Target Amount: ");
                    double target = sc.nextDouble();
                    sc.nextLine();
                    List<int[]> twoSum = system.findTwoSum(target);
                    System.out.println("Two-Sum Pairs:");
                    for (int[] pair : twoSum) System.out.println(Arrays.toString(pair));
                    break;

                case 3:
                    System.out.print("Target Amount: ");
                    target = sc.nextDouble();
                    sc.nextLine();
                    long window = 3600 * 1000; // 1 hour in ms
                    List<int[]> twoSumWindow = system.findTwoSumInWindow(target, window);
                    System.out.println("Two-Sum Pairs within 1-hour:");
                    for (int[] pair : twoSumWindow) System.out.println(Arrays.toString(pair));
                    break;

                case 4:
                    System.out.print("K: ");
                    int k = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Target Amount: ");
                    target = sc.nextDouble();
                    sc.nextLine();
                    List<List<Integer>> kSum = system.findKSum(k, target);
                    System.out.println("K-Sum Results:");
                    for (List<Integer> res : kSum) System.out.println(res);
                    break;

                case 5:
                    List<String> duplicates = system.detectDuplicates();
                    System.out.println("Duplicate Transactions:");
                    for (String d : duplicates) System.out.println(d);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}