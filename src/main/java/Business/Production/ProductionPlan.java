/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Production;

import Business.Hospital.Medicine;
import Business.WorkQueue.ManufacturerReplenishmentRequest;
import java.util.Date;

/**
 *
 * @author vyngo
 */
public class ProductionPlan {

    private String planId;
    private String productName;
    private Medicine medicine;
    private int qty;
    private Date planDate;
    private String status;
    private ManufacturerReplenishmentRequest sourceReplenishment;

    public ProductionPlan(String planId, String productName, int qty) {
        this.planId = planId;
        this.productName = productName;
        this.qty = qty;
        this.planDate = new Date();
        this.status = "Created";
    }

    /**
     * Preferred constructor: ties the plan to a real Medicine from the
     * shared MedicineCatalog instead of a free-typed product name, so
     * downstream ProductionOrder/dashboard code can match against the same
     * medicine records used by Wholesalers and Hospitals.
     */
    public ProductionPlan(String planId, Medicine medicine, int qty) {
        this.planId = planId;
        this.medicine = medicine;
        this.productName = medicine == null ? null : medicine.getMedicineName();
        this.qty = qty;
        this.planDate = new Date();
        this.status = "Created";
    }

    /**
     * Preferred constructor: creates the plan directly from a Wholesaler's
     * ManufacturerReplenishmentRequest, carrying the medicine, quantity and
     * requesting wholesaler through so downstream steps (Production Order,
     * Finished Goods, QA, Delivery Request) can trace back to it without
     * asking the user to re-select anything.
     */
    public ProductionPlan(String planId, ManufacturerReplenishmentRequest sourceReplenishment) {
        this.planId = planId;
        this.sourceReplenishment = sourceReplenishment;
        this.medicine = sourceReplenishment == null ? null : sourceReplenishment.getMedicine();
        this.productName = this.medicine == null ? null : this.medicine.getMedicineName();
        this.qty = sourceReplenishment == null ? 0 : sourceReplenishment.getQuantity();
        this.planDate = new Date();
        this.status = "Created";
    }

    public ManufacturerReplenishmentRequest getSourceReplenishment() {
        return sourceReplenishment;
    }

    public void setSourceReplenishment(ManufacturerReplenishmentRequest sourceReplenishment) {
        this.sourceReplenishment = sourceReplenishment;
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

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return planId + " - " + productName;
    }
}
