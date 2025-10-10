package app;

import manager.Berth;
import manager.BerthStatus;
import manager.DockReservation;
import model.BerthManager;

import java.util.Scanner;


public class MainApp {

    private static Scanner scanner = new Scanner(System.in);

    private static BerthManager berthManager = new BerthManager();

    public static void main(String[] args) {
        berthManager.addBerth("B101", "Container", 200, 500.0);
        berthManager.addBerth("B102", "Passenger", 100, 300.0);

        int choice = 0;
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
                    berthManager.displayAllBerths();
                    break;
                case 2:
                    makeBerthReservation();
                    break;
                case 3:
                    vesselDocking();
                    break;
                case 4:
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


    private static void displayMenu() {
        System.out.println("\n--- Maritime Port Operations System (MPOS) ---");
        System.out.println("1. Display all berths");
        System.out.println("2. Make a berth reservation");
        System.out.println("3. Vessel docking");
        System.out.println("4. Vessel undocking");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }


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


    private static void vesselDocking() {
        if (berthManager.isReservationListEmpty()) {
            System.out.println("No berth reservations available.");
            return;
        }

        System.out.print("Enter ship name for docking: ");
        String shipName = scanner.nextLine();

        DockReservation reservation = berthManager.removeReservationByShipName(shipName);
        if (reservation == null) {
            System.out.println("No reservation found for this ship.");
            return;
        }

        Berth allocatedBerth = findAvailableBerth(reservation.getRequestedBerthType());
        if (allocatedBerth != null) {
            berthManager.updateBerthStatus(allocatedBerth.getBerthID(), BerthStatus.OCCUPIED);
            System.out.println("Docking successful. Berth allocated: " + allocatedBerth.getBerthID());
        } else {
            System.out.println("No available berth found for the requested type.");
        }
    }

   
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
