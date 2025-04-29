package manager;

/*
 * Bu sınıf, liman rıhtım bilgilerini temsil eder.
 * Her rıhtım; rıhtım kimliği, kategori, kapasite, yanaşma ücreti ve durum (FREE: boş, OCCUPIED: gemi yanaşmış/docked) bilgilerini içerir.
 */
public class Berth {

    private String berthID;       // Rıhtım kimliği
    private String category;      // Rıhtım kategorisi (ör. Container, Passenger vb.)
    private int capacity;         // Rıhtım kapasitesi
    private double dockingFee;    // Yanaşma ücreti
    private BerthStatus status;   // Rıhtım durumu: FREE veya OCCUPIED (docked)

    // Yapıcı metot: Berth nesnesini oluşturur.
    public Berth(String berthID, String category, int capacity, double dockingFee) {
        this.berthID = berthID;
        this.category = category;
        this.capacity = capacity;
        this.dockingFee = dockingFee;
        this.status = BerthStatus.FREE; // Varsayılan olarak rıhtım boş kabul edilir.
    }

    public String getBerthID() {
        return berthID;
    }

    public String getCategory() {
        return category;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getDockingFee() {
        return dockingFee;
    }

    public BerthStatus getStatus() {
        return status;
    }

    public void setStatus(BerthStatus status) {
        this.status = status;
    }

    // Berth bilgilerini denizcilik terimleriyle ekrana yazdırır.
    public void displayInfo() {
        System.out.println("Berth " + berthID
                + " | Category: " + category
                + " | Capacity: " + capacity
                + " | Fee: " + dockingFee
                + " | Status: " + status);
    }
}