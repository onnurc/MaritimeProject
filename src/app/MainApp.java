package app;

import manager.Berth;
import manager.BerthStatus;
import manager.DockReservation;
import model.BerthManager;

import java.util.Scanner;

/*
 * Bu sınıf, “Maritime Port Operations System (MPOS)” için ana giriş noktasıdır.
 * Uygulama, rıhtım rezervasyonlarını yönetmek ve gemilerin limana yanaşma (vessel docking)
 * ile limandan ayrılma (vessel undocking) işlemlerini koordine etmek üzere tasarlanmıştır.
 *
 * - Menünün 2. seçeneği: make a berth reservation
 * - Menünün 3. seçeneği: vessel docking
 * - Menünün 4. seçeneği: vessel undocking
 */
public class MainApp {

    // Konsol etkileşimleri için Scanner nesnesi
    private static Scanner scanner = new Scanner(System.in);

    // Rıhtım ve rezervasyon yönetiminden sorumlu sınıf
    private static BerthManager berthManager = new BerthManager();

    public static void main(String[] args) {
        // Örnek rıhtımlar ekleniyor (BerthManager üzerinden)
        berthManager.addBerth("B101", "Container", 200, 500.0);
        berthManager.addBerth("B102", "Passenger", 100, 300.0);

        int choice = 0;
        // Ana menü döngüsü
        while (choice != 5) {
            displayMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı girin.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Tüm rıhtımları görüntüle
                    berthManager.displayAllBerths();
                    break;
                case 2:
                    // Yeni rıhtım rezervasyonu oluştur (make a berth reservation)
                    makeBerthReservation();
                    break;
                case 3:
                    // Gemi yanaşma işlemi (vessel docking)
                    vesselDocking();
                    break;
                case 4:
                    // Gemi ayrılma işlemi (vessel undocking)
                    vesselUndocking();
                    break;
                case 5:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    /*
     * displayMenu metodu:
     * Konsolda ana menüyü görüntüleyerek kullanıcının seçim yapmasını sağlar.
     * Burada “vessel docking” ve “vessel undocking” terimleri kullanılır.
     */
    private static void displayMenu() {
        System.out.println("\n--- Maritime Port Operations System (MPOS) ---");
        System.out.println("1. Display all berths");
        System.out.println("2. Make a berth reservation");
        System.out.println("3. Vessel docking");
        System.out.println("4. Vessel undocking");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    /*
     * makeBerthReservation metodu:
     * Kullanıcıdan gemi adı ve istenen rıhtım tipini alarak
     * yeni bir rıhtım rezervasyonu (DockReservation) oluşturur.
     */
    private static void makeBerthReservation() {
        System.out.print("Enter ship name: ");
        String shipName = scanner.nextLine();

        System.out.print("Enter requested berth type (Container/Passenger/etc.): ");
        String requestedType = scanner.nextLine();

        // Rezervasyon kimliği için basit zaman damgası
        String reservationID = "R" + System.currentTimeMillis();

        DockReservation reservation = new DockReservation(reservationID, shipName, requestedType);
        berthManager.addReservation(reservation);

        System.out.println("Berth reservation created with ID: " + reservationID);
    }

    /*
     * vesselDocking metodu:
     * Gemi yanaşma (docking) sürecini yönetir.
     * 1) Kullanıcıdan gemi adını alır
     * 2) Rezervasyon listesinde gemiyi arayıp siler
     * 3) Uygun rıhtım varsa durumunu OCCUPIED yapar
     */
    private static void vesselDocking() {
        if (berthManager.isReservationListEmpty()) {
            System.out.println("No berth reservations available.");
            return;
        }

        System.out.print("Enter ship name for docking: ");
        String shipName = scanner.nextLine();

        // Rezervasyon listesinde gemi adına göre arama
        DockReservation reservation = berthManager.removeReservationByShipName(shipName);
        if (reservation == null) {
            System.out.println("No reservation found for this ship.");
            return;
        }

        // Uygun rıhtım arama
        Berth allocatedBerth = findAvailableBerth(reservation.getRequestedBerthType());
        if (allocatedBerth != null) {
            berthManager.updateBerthStatus(allocatedBerth.getBerthID(), BerthStatus.OCCUPIED);
            System.out.println("Docking successful. Berth allocated: " + allocatedBerth.getBerthID());
        } else {
            System.out.println("No available berth found for the requested type.");
        }
    }

    /*
     * vesselUndocking metodu:
     * Gemi ayrılma (undocking) sürecini yönetir.
     * 1) Kullanıcıdan rıhtım numarası alır
     * 2) Rıhtım durumunu FREE yapar
     */
    private static void vesselUndocking() {
        System.out.print("Enter berth ID for undocking: ");
        String berthID = scanner.nextLine();

        Berth berth = berthManager.getBerth(berthID);
        if (berth == null) {
            System.out.println("Berth not found.");
            return;
        }

        if (berth.getStatus() == BerthStatus.OCCUPIED) {
            berthManager.updateBerthStatus(berthID, BerthStatus.FREE);
            System.out.println("Berth " + berthID + " is now FREE.");
        } else {
            System.out.println("Berth is already FREE.");
        }
    }

    /*
     * findAvailableBerth metodu:
     * Belirtilen kategoriye ve FREE durumuna sahip ilk rıhtımı bulur.
     * Rıhtım bulunursa döndürür, bulunamazsa null döndürür.
     */
    private static Berth findAvailableBerth(String requestedType) {
        for (String berthID : berthManager.getBerths().keySet()) {
            Berth berth = berthManager.getBerths().get(berthID);
            if (berth.getCategory().equalsIgnoreCase(requestedType)
                    && berth.getStatus() == BerthStatus.FREE) {
                return berth;
            }
        }
        return null;
    }
}