package manager;


public class Berth {

    private String berthID;       
    private String category;      
    private int capacity;       
    private double dockingFee;   
    private BerthStatus status;   

    
    public Berth(String berthID, String category, int capacity, double dockingFee) {
        this.berthID = berthID;
        this.category = category;
        this.capacity = capacity;
        this.dockingFee = dockingFee;
        this.status = BerthStatus.FREE; 
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

    public void displayInfo() {
        System.out.println("Berth " + berthID
                + " | Category: " + category
                + " | Capacity: " + capacity
                + " | Fee: " + dockingFee
                + " | Status: " + status);
    }
}
