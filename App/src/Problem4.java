import java.util.*;

public class Problem4 {
    private HashMap<String, Set<String>> ngramIndex = new HashMap<>();
    private HashMap<String, List<String>> documentNgrams = new HashMap<>();
    private int N = 5;
    public List<String> generateNgrams(String text) {
        List<String> ngrams = new ArrayList<>();
        String[] words = text.toLowerCase().split("\\s+");
        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder gram = new StringBuilder();
            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }
            ngrams.add(gram.toString().trim());
        }
        return ngrams;
    }

    public void addDocument(String docId, String text) {
        List<String> ngrams = generateNgrams(text);
        documentNgrams.put(docId, ngrams);
        for (String gram : ngrams) {
            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
        System.out.println("Document added with " + ngrams.size() + " n-grams.");
    }

    public void analyzeDocument(String docId) {
        if (!documentNgrams.containsKey(docId)) {
            System.out.println("Document not found.");
            return;
        }
        List<String> ngrams = documentNgrams.get(docId);
        HashMap<String, Integer> matchCount = new HashMap<>();
        for (String gram : ngrams) {
            if (ngramIndex.containsKey(gram)) {
                for (String otherDoc : ngramIndex.get(gram)) {
                    if (!otherDoc.equals(docId)) {
                        matchCount.put(otherDoc,
                                matchCount.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }
        System.out.println("Extracted " + ngrams.size() + " n-grams");
        for (String otherDoc : matchCount.keySet()) {
            int matches = matchCount.get(otherDoc);
            double similarity = (matches * 100.0) / ngrams.size();
            System.out.println("Found " + matches + " matching n-grams with " + otherDoc);
            System.out.println("Similarity: " + String.format("%.2f", similarity) + "%");
            if (similarity > 60) {
                System.out.println("PLAGIARISM DETECTED");
            } else if (similarity > 10) {
                System.out.println("Suspicious similarity");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Problem4 system = new Problem4();
        while (true) {
            System.out.println("\n===== Plagiarism Detection System =====");
            System.out.println("1. Add Document");
            System.out.println("2. Analyze Document");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Document ID: ");
                    String id = sc.nextLine();
                    System.out.println("Enter Document Text:");
                    String text = sc.nextLine();
                    system.addDocument(id, text);
                    break;
                case 2:
                    System.out.print("Enter Document ID to analyze: ");
                    String docId = sc.nextLine();
                    system.analyzeDocument(docId);
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