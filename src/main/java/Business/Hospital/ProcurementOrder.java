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
public class ProcurementOrder extends WorkRequest{
    private Medicine medicine;
    private int requestQty;

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
    
    
    
    
}
