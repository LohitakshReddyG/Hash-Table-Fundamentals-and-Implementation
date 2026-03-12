import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class Problem5 {
    private HashMap<String, Integer> pageViews = new HashMap<>();
    private HashMap<String, HashSet<String>> uniqueVisitors = new HashMap<>();
    private HashMap<String, Integer> trafficSources = new HashMap<>();
    public void processEvent(Event event) {
        pageViews.put(event.url, pageViews.getOrDefault(event.url, 0) + 1);
        uniqueVisitors.putIfAbsent(event.url, new HashSet<>());
        uniqueVisitors.get(event.url).add(event.userId);
        trafficSources.put(event.source,trafficSources.getOrDefault(event.source, 0) + 1);
        System.out.println("Event processed successfully.");
    }

    public void showTopPages() {
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        System.out.println("\nTop Pages:");
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();
            System.out.println((count + 1) + ". " + url +" - " + views + " views (" + unique + " unique)");
            count++;
            if (count == 10) break;
        }
    }

    public void showTrafficSources() {
        int total = 0;
        for (int count : trafficSources.values()) {
            total += count;
        }
        System.out.println("\nTraffic Sources:");
        for (String source : trafficSources.keySet()) {
            int count = trafficSources.get(source);
            double percent = (count * 100.0) / total;
            System.out.println(source + ": " +
                    String.format("%.2f", percent) + "%");
        }
    }
    public void getDashboard() {
        showTopPages();
        showTrafficSources();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Problem5 analytics = new Problem5();
        while (true) {
            System.out.println("\n===== Real-Time Analytics Dashboard =====");
            System.out.println("1. Process Page View Event");
            System.out.println("2. Show Dashboard");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Page URL: ");
                    String url = sc.nextLine();
                    System.out.print("Enter User ID: ");
                    String userId = sc.nextLine();
                    System.out.print("Enter Traffic Source (Google/Facebook/Direct/etc): ");
                    String source = sc.nextLine();
                    Event event = new Event(url, userId, source);
                    analytics.processEvent(event);
                    break;
                case 2:
                    analytics.getDashboard();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}