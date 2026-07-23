/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Fleet;

import java.util.ArrayList;

public class FleetDirectory {

    private ArrayList<Truck> truckList;

    public FleetDirectory() {
        truckList = new ArrayList<>();
    }

    public ArrayList<Truck> getTruckList() {
        return truckList;
    }

    public Truck addTruck(String licensePlate, String model, double capacityKg) {
        Truck t = new Truck(licensePlate, model, capacityKg);
        truckList.add(t);
        return t;
    }

    public boolean removeTruck(Truck t) {
        return truckList.remove(t);
    }
}
