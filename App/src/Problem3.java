import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttlSeconds) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class Problem3 {
    private int maxCacheSize = 5;
    private LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<String, DNSEntry>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > maxCacheSize;
                }
            };

    private int cacheHits = 0;
    private int cacheMisses = 0;

    private String queryUpstreamDNS(String domain) {
        Random rand = new Random();
        return "172.217.14." + (rand.nextInt(200) + 1);
    }

    public String resolve(String domain, int ttl) {
        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);
            if (!entry.isExpired()) {
                cacheHits++;
                System.out.println("Cache HIT → " + entry.ipAddress);
                return entry.ipAddress;
            } else {
                System.out.println("Cache EXPIRED");
                cache.remove(domain);
            }
        }
        cacheMisses++;
        System.out.println("Cache MISS → Querying upstream DNS...");
        String ip = queryUpstreamDNS(domain);
        DNSEntry newEntry = new DNSEntry(domain, ip, ttl);
        cache.put(domain, newEntry);
        System.out.println(domain + " → " + ip + " (TTL: " + ttl + "s)");
        return ip;
    }

    // Remove expired entries
    public void cleanExpiredEntries() {
        Iterator<Map.Entry<String, DNSEntry>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, DNSEntry> entry = iterator.next();
            if (entry.getValue().isExpired()) {
                iterator.remove();
            }
        }
    }

    public void getCacheStats() {
        int total = cacheHits + cacheMisses;
        if (total == 0) {
            System.out.println("No requests yet.");
            return;
        }
        double hitRate = (cacheHits * 100.0) / total;
        System.out.println("Cache Hits: " + cacheHits);
        System.out.println("Cache Misses: " + cacheMisses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Problem3 dns = new Problem3();
        while (true) {
            System.out.println("\n===== DNS Cache System =====");
            System.out.println("1. Resolve Domain");
            System.out.println("2. Clean Expired Entries");
            System.out.println("3. Cache Statistics");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter domain: ");
                    String domain = sc.nextLine();
                    System.out.print("Enter TTL (seconds): ");
                    int ttl = sc.nextInt();
                    dns.resolve(domain, ttl);
                    break;
                case 2:
                    dns.cleanExpiredEntries();
                    System.out.println("Expired entries removed.");
                    break;
                case 3:
                    dns.getCacheStats();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}