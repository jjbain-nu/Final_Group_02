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
public class ReplenishmentRequest extends WorkRequest {
    
    private static int count = 1;

    private int replenishId;
    private Medicine medicine;
    private int replenishQty;

    public ReplenishmentRequest() {
        replenishId = count++;
    }
    
    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getReplenishQty() {
        return replenishQty;
    }

    public void setReplenishQty(int replenishQty) {
        this.replenishQty = replenishQty;
    }

    @Override
    public String toString(){
        return String.valueOf(replenishId);
        
    }
    
}
