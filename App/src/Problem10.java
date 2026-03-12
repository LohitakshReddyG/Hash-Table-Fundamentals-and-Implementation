import java.util.*;

class VideoData {
    String videoId;
    String content;
    VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}
public class Problem10 {
    private final int L1_CAPACITY = 10000;
    private final int L2_CAPACITY = 100000;
    private LinkedHashMap<String, VideoData> L1Cache;
    private HashMap<String, String> L2Cache; // videoId -> SSD path
    private HashMap<String, VideoData> database; // all videos
    private HashMap<String, Integer> accessCount; // for promotions
    private int L1Hits = 0, L2Hits = 0, L3Hits = 0, totalRequests = 0;
    private double L1Time = 0, L2Time = 0, L3Time = 0;
    public Problem10() {
        L1Cache = new LinkedHashMap<>(L1_CAPACITY, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                return size() > L1_CAPACITY;
            }
        };
        L2Cache = new HashMap<>();
        database = new HashMap<>();
        accessCount = new HashMap<>();
    }
    public void addVideoToDB(String videoId, String content) {
        database.put(videoId, new VideoData(videoId, content));
    }
    public VideoData getVideo(String videoId) {
        totalRequests++;
        if (L1Cache.containsKey(videoId)) {
            L1Hits++;
            L1Time += 0.5;
            System.out.println("L1 Cache HIT (0.5ms)");
            return L1Cache.get(videoId);
        }
        if (L2Cache.containsKey(videoId)) {
            L2Hits++;
            L2Time += 5;
            System.out.println("L1 Cache MISS → L2 Cache HIT (5ms)");
            int count = accessCount.getOrDefault(videoId, 0) + 1;
            accessCount.put(videoId, count);
            if (count > 3 && database.containsKey(videoId)) { // threshold
                L1Cache.put(videoId, database.get(videoId));
                System.out.println("Promoted to L1 Cache");
            }
            return database.get(videoId);
        }
        if (database.containsKey(videoId)) {
            L3Hits++;
            L3Time += 150;
            System.out.println("L1 & L2 Cache MISS → L3 Database HIT (150ms)");
            L2Cache.put(videoId, "/ssd/path/" + videoId);
            accessCount.put(videoId, 1);
            System.out.println("Added to L2 Cache (access count: 1)");
            return database.get(videoId);
        }
        System.out.println("Video not found in database.");
        return null;
    }
    public void getStatistics() {
        double L1HitRate = totalRequests == 0 ? 0 : L1Hits * 100.0 / totalRequests;
        double L2HitRate = totalRequests == 0 ? 0 : L2Hits * 100.0 / totalRequests;
        double L3HitRate = totalRequests == 0 ? 0 : L3Hits * 100.0 / totalRequests;
        double avgL1Time = L1Hits == 0 ? 0 : L1Time / L1Hits;
        double avgL2Time = L2Hits == 0 ? 0 : L2Time / L2Hits;
        double avgL3Time = L3Hits == 0 ? 0 : L3Time / L3Hits;
        double overallHitRate = L1HitRate + L2HitRate + L3HitRate;
        double overallAvgTime = (L1Time + L2Time + L3Time) / totalRequests;
        System.out.println("\n=== Cache Statistics ===");
        System.out.println(String.format("L1: Hit Rate %.2f%%, Avg Time %.2fms", L1HitRate, avgL1Time));
        System.out.println(String.format("L2: Hit Rate %.2f%%, Avg Time %.2fms", L2HitRate, avgL2Time));
        System.out.println(String.format("L3: Hit Rate %.2f%%, Avg Time %.2fms", L3HitRate, avgL3Time));
        System.out.println(String.format("Overall: Hit Rate %.2f%%, Avg Time %.2fms", overallHitRate, overallAvgTime));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Problem10 cacheSystem = new Problem10();
        for (int i = 1; i <= 20; i++) {
            cacheSystem.addVideoToDB("video_" + i, "Content of video " + i);
        }
        while (true) {
            System.out.println("\n===== Multi-Level Cache System =====");
            System.out.println("1. Get Video");
            System.out.println("2. Show Statistics");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter video ID: ");
                    String vid = sc.nextLine();
                    cacheSystem.getVideo(vid);
                    break;
                case 2:
                    cacheSystem.getStatistics();
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