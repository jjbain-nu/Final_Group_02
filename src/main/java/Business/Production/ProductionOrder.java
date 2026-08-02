/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Production;

import Business.Hospital.Medicine;
import Business.WorkQueue.WorkRequest;
import java.util.Date;

/**
 *
 * @author vyngo
 */
public class ProductionOrder extends WorkRequest {

    private String productName;
    private Medicine medicine;
    private int qty;
    private String planId;

    public ProductionOrder(String productName, int qty) {
        this.productName = productName;
        this.qty = qty;
        setStatus("Sent");
        setRequestDate(new Date());
    }

    /**
     * Preferred constructor: references a real Medicine from the shared
     * MedicineCatalog instead of a free-typed product name, so the Supply
     * Chain Control Tower dashboard can match production orders with the
     * same medicine inventory records used by Wholesalers and Hospitals.
     */
    public ProductionOrder(Medicine medicine, int qty) {
        this.medicine = medicine;
        this.productName = medicine == null ? null : medicine.getMedicineName();
        this.qty = qty;
        setStatus("Sent");
        setRequestDate(new Date());
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getProductName() {
        return medicine != null ? medicine.getMedicineName() : productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        if (medicine != null) {
            this.productName = medicine.getMedicineName();
        }
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return getProductName();
    }
}