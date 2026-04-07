public class ClientRiskRanking {

    static class Client {
        String name;
        int riskScore;
        double accountBalance;

        Client(String name, int riskScore, double accountBalance) {
            this.name = name;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }
    }

    public static void bubbleSortByRiskAsc(Client[] clients) {
        int n = clients.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                }
            }
        }
    }

    public static void insertionSortDesc(Client[] clients) {
        for (int i = 1; i < clients.length; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 && clients[j].riskScore < key.riskScore) {
                clients[j + 1] = clients[j];
                j--;
            }

            clients[j + 1] = key;
        }
    }

    public static void printTop10(Client[] clients) {
        int limit = Math.min(10, clients.length);
        for (int i = 0; i < limit; i++) {
            System.out.println(clients[i].name + " (" + clients[i].riskScore + ")");
        }
    }

    public static void main(String[] args) {
        Client[] clients = {
                new Client("C", 80, 5000),
                new Client("A", 20, 2000),
                new Client("B", 50, 3000)
        };

        bubbleSortByRiskAsc(clients);
        insertionSortDesc(clients);
        printTop10(clients);
    }
}