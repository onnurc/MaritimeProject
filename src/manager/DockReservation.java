package manager;

/*
 * Bu sınıf, gemi yanaşma rezervasyon bilgilerini temsil eder.
 * Her rezervasyon; rezervasyon kimliği, gemi adı ve istenen rıhtım tipini içerir.
 */
public class DockReservation {

    private String reservationID;      // Rezervasyon kimliği
    private String shipName;           // Gemi adı
    private String requestedBerthType; // İstenen rıhtım tipi (kategori)

    // Yapıcı metot: Gemi yanaşma rezervasyon nesnesini oluşturur.
    public DockReservation(String reservationID, String shipName, String requestedBerthType) {
        this.reservationID = reservationID;
        this.shipName = shipName;
        this.requestedBerthType = requestedBerthType;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getShipName() {
        return shipName;
    }

    public String getRequestedBerthType() {
        return requestedBerthType;
    }
}