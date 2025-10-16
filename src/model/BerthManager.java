package model;

import manager.Berth;
import manager.BerthStatus;
import manager.DockReservation;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;


public class BerthManager {


    private Map<String, Berth> berths;
    private List<DockReservation> reservations;

    public BerthManager() {
        this.berths = new HashMap<>();
        this.reservations = new LinkedList<>();
    }

    public void addBerth(String berthID, String category, int capacity, double dockingFee) {
        Berth berth = new Berth(berthID, category, capacity, dockingFee);
        berths.put(berthID, berth);
    }

    public Berth getBerth(String berthID) {
        return berths.get(berthID);
    }

    public void updateBerthStatus(String berthID, BerthStatus newStatus) {
        Berth berth = berths.get(berthID);
        if (berth != null) {
            berth.setStatus(newStatus);
        }
    }
    public void displayAllBerths() {
        for (Berth berth : berths.values()) {
            berth.displayInfo();
        }
    }
    public void addReservation(DockReservation reservation) {
        reservations.add(reservation);
    }
    public DockReservation removeReservationByShipName(String shipName) {
        Iterator<DockReservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            DockReservation r = iterator.next();
            if (r.getShipName().equalsIgnoreCase(shipName)) {
                iterator.remove();
                return r;
            }
        }
        return null; 
    }
    public boolean isReservationListEmpty() {
        return reservations.isEmpty();
    }
    public Map<String, Berth> getBerths() {
        return berths;
    }
}
