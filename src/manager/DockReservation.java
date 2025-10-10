package manager;


public class DockReservation {

    private String reservationID;      
    private String shipName;          
    private String requestedBerthType; 

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
