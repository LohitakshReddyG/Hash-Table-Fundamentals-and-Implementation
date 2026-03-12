import java.util.*;
class ParkingSpot {
    String licensePlate;
    long entryTime;
    boolean occupied;
    ParkingSpot() {
        this.licensePlate = null;
        this.entryTime = 0;
        this.occupied = false;
    }
}
public class Problem8 {
    private final int CAPACITY = 500;
    private ParkingSpot[] spots = new ParkingSpot[CAPACITY];
    private int totalProbes = 0;
    private int vehiclesParked = 0;
    private Map<Integer, Integer> hourOccupancy = new HashMap<>(); // hour -> count
    public Problem8() {
        for (int i = 0; i < CAPACITY; i++) {
            spots[i] = new ParkingSpot();
        }
    }
    private int hash(String licensePlate) {
        int hash = 0;
        for (char c : licensePlate.toCharArray()) {
            hash = (hash * 31 + c) % CAPACITY;
        }
        return hash;
    }
    public void parkVehicle(String licensePlate) {
        int preferred = hash(licensePlate);
        int spotIndex = preferred;
        int probes = 0;
        while (spots[spotIndex].occupied) {
            probes++;
            spotIndex = (spotIndex + 1) % CAPACITY;
            if (probes >= CAPACITY) {
                System.out.println("Parking Full! Cannot park vehicle.");
                return;
            }
        }
        spots[spotIndex].licensePlate = licensePlate;
        spots[spotIndex].entryTime = System.currentTimeMillis();
        spots[spotIndex].occupied = true;
        totalProbes += probes;
        vehiclesParked++;
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        hourOccupancy.put(hour, hourOccupancy.getOrDefault(hour, 0) + 1);
        System.out.println("Vehicle " + licensePlate + " parked at spot #" + spotIndex +
                " (" + probes + " probes)");
    }
    public void exitVehicle(String licensePlate) {
        int preferred = hash(licensePlate);
        int spotIndex = preferred;
        int probes = 0;
        while (spots[spotIndex].occupied) {
            if (spots[spotIndex].occupied && licensePlate.equals(spots[spotIndex].licensePlate)) {
                long exitTime = System.currentTimeMillis();
                long durationMs = exitTime - spots[spotIndex].entryTime;
                double hours = durationMs / (1000.0 * 60 * 60);
                double fee = hours * 5.5; // $5.5 per hour
                spots[spotIndex].occupied = false;
                spots[spotIndex].licensePlate = null;
                spots[spotIndex].entryTime = 0;
                vehiclesParked--;
                System.out.println("Vehicle " + licensePlate + " exited from spot #" + spotIndex +
                        ", Duration: " + String.format("%.2f", hours) +
                        "h, Fee: $" + String.format("%.2f", fee));
                return;
            }
            probes++;
            spotIndex = (spotIndex + 1) % CAPACITY;
            if (probes >= CAPACITY) break;
        }
        System.out.println("Vehicle " + licensePlate + " not found in parking lot.");
    }
    public void getStatistics() {
        double occupancy = (vehiclesParked * 100.0) / CAPACITY;
        double avgProbes = vehiclesParked == 0 ? 0 : totalProbes * 1.0 / vehiclesParked;
        int peakHour = -1, max = 0;
        for (int hour : hourOccupancy.keySet()) {
            if (hourOccupancy.get(hour) > max) {
                max = hourOccupancy.get(hour);
                peakHour = hour;
            }
        }
        System.out.println("\n=== Parking Lot Statistics ===");
        System.out.println("Occupancy: " + String.format("%.2f", occupancy) + "%");
        System.out.println("Average Probes: " + String.format("%.2f", avgProbes));
        if (peakHour != -1)
            System.out.println("Peak Hour: " + peakHour + ":00 - " + (peakHour + 1) + ":00");
        else
            System.out.println("No peak hour data yet.");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Problem8 parkingLot = new Problem8();
        while (true) {
            System.out.println("\n===== Parking Lot System =====");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Exit Vehicle");
            System.out.println("3. Get Statistics");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter License Plate: ");
                    String lp = sc.nextLine();
                    parkingLot.parkVehicle(lp);
                    break;
                case 2:
                    System.out.print("Enter License Plate: ");
                    lp = sc.nextLine();
                    parkingLot.exitVehicle(lp);
                    break;
                case 3:
                    parkingLot.getStatistics();
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