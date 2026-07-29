/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Hospital;

import Business.WorkQueue.WholesaleWorkRequest;

/**
 *
 * @author yu101
 */
/**
 * A hospital request that is fulfilled through the wholesaler shipping workflow.
 * The same instance is shared by Hospital Procurement, Wholesaler Inventory,
 * Wholesaler Shipping, and Hospital Pharmacy queues.
 */
public class ProcurementRequest extends WholesaleWorkRequest {
    private static int count = 1;

    private int procurementId;
    private Medicine medicine;
    private int requestQty;
    private ReplenishmentRequest replenishmentRequest;

    public ProcurementRequest() {
        super(RequestType.TRANSFER, "Hospital procurement request", 1);
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getRequestQty() {
        return requestQty;
    }

    public void setRequestQty(int requestQty) {
        this.requestQty = requestQty;
    }

    public int getProcurementId() {
        return procurementId;
    }

    public void setProcurementId(int procurementId) {
        this.procurementId = procurementId;
    }

    public ReplenishmentRequest getReplenishmentRequest() {
        return replenishmentRequest;
    }

    public void setReplenishmentRequest(ReplenishmentRequest replenishmentRequest) {
        this.replenishmentRequest = replenishmentRequest;
    }

    @Override
    public String getItemDescription() {
        return medicine == null ? super.getItemDescription() : medicine.getMedicineName();
    }

    @Override
    public int getQuantity() {
        return requestQty > 0 ? requestQty : super.getQuantity();
    }
    
    
    
    @Override
    public String toString(){
        return String.valueOf(procurementId);
        
    }
    
}
