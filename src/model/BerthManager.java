package model;

import manager.Berth;
import manager.BerthStatus;
import manager.DockReservation;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

/*
 * Bu sınıf, liman rıhtım bilgilerini HashMap kullanarak yönetir ve
 * gemi yanaşma rezervasyonlarını LinkedList kullanarak saklar.
 */
public class BerthManager {

    // Rıhtım bilgileri için HashMap: Anahtar -> berthID, Değer -> Berth nesnesi
    private Map<String, Berth> berths;
    // Gemi yanaşma rezervasyonları için LinkedList
    private List<DockReservation> reservations;

    // Yapıcı metot: HashMap ve LinkedList nesnelerini başlatır.
    public BerthManager() {
        this.berths = new HashMap<>();
        this.reservations = new LinkedList<>();
    }

    // Yeni rıhtım ekleme metodu
    public void addBerth(String berthID, String category, int capacity, double dockingFee) {
        Berth berth = new Berth(berthID, category, capacity, dockingFee);
        berths.put(berthID, berth);
    }

    // Belirtilen rıhtım kimliğine göre rıhtım döndürür
    public Berth getBerth(String berthID) {
        return berths.get(berthID);
    }

    // Rıhtım durumunu güncelleyen metot
    public void updateBerthStatus(String berthID, BerthStatus newStatus) {
        Berth berth = berths.get(berthID);
        if (berth != null) {
            berth.setStatus(newStatus);
        }
    }

    // Tüm rıhtım bilgilerini ekrana yazdıran metot
    public void displayAllBerths() {
        for (Berth berth : berths.values()) {
            berth.displayInfo();
        }
    }

    // Yeni gemi yanaşma rezervasyonu ekler
    public void addReservation(DockReservation reservation) {
        reservations.add(reservation);
    }

    // Geminin adıyla eşleşen ilk rezervasyonu arayıp listeden çıkarır (vessel docking sırasında kullanılır)
    public DockReservation removeReservationByShipName(String shipName) {
        Iterator<DockReservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            DockReservation r = iterator.next();
            if (r.getShipName().equalsIgnoreCase(shipName)) {
                iterator.remove();
                return r;
            }
        }
        return null; // Hiçbir rezervasyon bulunamazsa null döndürür.
    }

    // Rezervasyon listesinin boş olup olmadığını kontrol eder
    public boolean isReservationListEmpty() {
        return reservations.isEmpty();
    }

    // Yardımcı amaçlı: Rıhtımların HashMap'ini döndürür.
    public Map<String, Berth> getBerths() {
        return berths;
    }
}