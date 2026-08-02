/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Hospital;

/**
 *
 * @author yu101
 */
public class MedicineInventory {
    
    private Medicine medicine;
    private int quantity;
    private int standardStock;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStandardStock() {
        return standardStock;
    }

    public void setStandardStock(int standardStock) {
        this.standardStock = standardStock;
    }
    
    public int getShortageQty(){
        return Math.max(0,standardStock - quantity);
        
    }
    
    public int getSurplus(){
        return Math.max(0,quantity-standardStock );
        
    }
    
    
    public boolean isShortage(){
        return quantity < standardStock;
    }
    
        public boolean isExcess(){
        return quantity > standardStock;
    }

    public String toString(){
        return medicine.getMedicineId();
    }
    
    
    
}
