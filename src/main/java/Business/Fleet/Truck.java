/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Fleet;

/**
 * A single truck in the transport organization's fleet.
 * Tracked by license plate, cargo capacity (kg), and availability status.
 */
public class Truck {

    public static final String STATUS_AVAILABLE = "Available";
    public static final String STATUS_ASSIGNED  = "Assigned";
    public static final String STATUS_MAINTENANCE = "Maintenance";

    private String licensePlate;
    private String model;
    private double capacityKg;
    private String status;   // Available / Assigned / Maintenance

    public Truck(String licensePlate, String model, double capacityKg) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.capacityKg = capacityKg;
        this.status = STATUS_AVAILABLE;
    }

    public String getLicensePlate()          { return licensePlate; }
    public void   setLicensePlate(String v)  { this.licensePlate = v; }
    public String getModel()                 { return model; }
    public void   setModel(String v)         { this.model = v; }
    public double getCapacityKg()            { return capacityKg; }
    public void   setCapacityKg(double v)    { this.capacityKg = v; }
    public String getStatus()                { return status; }
    public void   setStatus(String v)        { this.status = v; }

    @Override
    public String toString() {
        return licensePlate + " (" + model + ")";
    }
}
