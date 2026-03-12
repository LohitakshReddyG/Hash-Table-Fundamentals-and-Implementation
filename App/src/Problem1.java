import java.util.*;

public class Problem1 {

    private HashMap<String, Integer> usernameMap = new HashMap<>();
    private HashMap<String, Integer> attemptFrequency = new HashMap<>();

    public boolean checkAvailability(String username) {
        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);
        return !usernameMap.containsKey(username);
    }

    public void registerUser(String username, int userId) {
        if (usernameMap.containsKey(username)) {
            System.out.println("Username already taken.");
        } else {
            usernameMap.put(username, userId);
            System.out.println("Registration successful!");
        }
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!usernameMap.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        if (username.contains("_")) {
            String alt = username.replace("_", ".");
            if (!usernameMap.containsKey(alt)) {
                suggestions.add(alt);
            }
        }

        return suggestions;
    }

    public String getMostAttempted() {
        String popular = "";
        int maxAttempts = 0;
        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {
            if (entry.getValue() > maxAttempts) {
                maxAttempts = entry.getValue();
                popular = entry.getKey();
            }
        }
        if (popular.equals("")) {
            return "No attempts yet.";
        }
        return popular + " (" + maxAttempts + " attempts)";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Problem1 system = new Problem1();

        while (true) {

            System.out.println("\n===== Username System =====");
            System.out.println("1. Check Username Availability");
            System.out.println("2. Register Username");
            System.out.println("3. Suggest Alternatives");
            System.out.println("4. Get Most Attempted Username");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    if (system.checkAvailability(username)) {
                        System.out.println("Username is available.");
                    } else {
                        System.out.println("Username already taken.");
                    }
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String user = sc.nextLine();
                    System.out.print("Enter userId: ");
                    int id = sc.nextInt();
                    system.registerUser(user, id);
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    String name = sc.nextLine();

                    List<String> suggestions = system.suggestAlternatives(name);

                    System.out.println("Suggestions: " + suggestions);
                    break;
                case 4:
                    System.out.println("Most attempted: " + system.getMostAttempted());
                    break;
                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}