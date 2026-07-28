/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Hospital;

import Business.WorkQueue.WorkRequest;

/**
 *
 * @author yu101
 */
public class ProcurementRequest extends WorkRequest{
    private static int count = 1;

    private int procurementId;
    private Medicine medicine;
    private int requestQty;
    private ReplenishmentRequest replenishmentRequest;

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
    public String toString(){
        return String.valueOf(procurementId);
        
    }
    
}
