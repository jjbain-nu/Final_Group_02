/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;

import Business.Fleet.Truck;
import Business.UserAccount.UserAccount;
import java.util.Date;

/**
 * A DeliveryWorkRequest represents a shipment request sent to a
 * Transport Organization (typically by a Manufacturer, Supplier, or
 * Wholesaler). It is enriched as the workflow progresses:
 *
 *   sender ----> transport org WorkQueue     (initial: status = "Received")
 *   admin sets pickup/drop/scheduled date    (status = "Scheduled")
 *   admin assigns truck + driver             (status = "Assigned")
 *   driver reports Loading                   (status = "Loaded")
 *   driver reports Unloading/Inspection      (status = "Delivered")
 *   driver uploads proof                     (status = "Completed" + proofRef)
 *   admin reports back to manufacturer       (status = "Reported")
 */
public class DeliveryWorkRequest extends WorkRequest {

    public static final String STATUS_RECEIVED   = "Received";
    public static final String STATUS_SCHEDULED  = "Scheduled";
    public static final String STATUS_ASSIGNED   = "Assigned";
    public static final String STATUS_LOADED     = "Loaded";
    public static final String STATUS_DELIVERED  = "Delivered";
    public static final String STATUS_COMPLETED  = "Completed";
    public static final String STATUS_REPORTED   = "Reported";

    private String pickupLocation;
    private String dropLocation;
    private String cargoDescription;
    private double cargoWeightKg;
    private Date   scheduledDate;

    private Truck assignedTruck;
    private UserAccount assignedDriver;

    private String proofReference;      // e.g. photo URL, signature id
    private String inspectionResult;    // OK / Damaged / Rejected
    private String deliveryReport;      // final report text back to sender
    
    private WorkRequest sourceRequest;   // the request this delivery fulfils

    // Getters / setters
    public String getPickupLocation()          { return pickupLocation; }
    public void   setPickupLocation(String v)  { this.pickupLocation = v; }
    public String getDropLocation()            { return dropLocation; }
    public void   setDropLocation(String v)    { this.dropLocation = v; }
    public String getCargoDescription()        { return cargoDescription; }
    public void   setCargoDescription(String v){ this.cargoDescription = v; }
    public double getCargoWeightKg()           { return cargoWeightKg; }
    public void   setCargoWeightKg(double v)   { this.cargoWeightKg = v; }
    public Date   getScheduledDate()           { return scheduledDate; }
    public void   setScheduledDate(Date v)     { this.scheduledDate = v; }
    public Truck  getAssignedTruck()           { return assignedTruck; }
    public void   setAssignedTruck(Truck v)    { this.assignedTruck = v; }
    public UserAccount getAssignedDriver()     { return assignedDriver; }
    public void   setAssignedDriver(UserAccount v) { this.assignedDriver = v; }
    public String getProofReference()          { return proofReference; }
    public void   setProofReference(String v)  { this.proofReference = v; }
    public String getInspectionResult()        { return inspectionResult; }
    public void   setInspectionResult(String v){ this.inspectionResult = v; }
    public String getDeliveryReport()          { return deliveryReport; }
    public void   setDeliveryReport(String v)  { this.deliveryReport = v; }
    public WorkRequest getSourceRequest()       { return sourceRequest; }
    public void setSourceRequest(WorkRequest r)  { this.sourceRequest = r; }
}
