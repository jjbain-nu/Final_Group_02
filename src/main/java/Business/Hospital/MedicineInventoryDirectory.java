/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Hospital;

import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class MedicineInventoryDirectory {
    
    private ArrayList<MedicineInventory>inventoryList;
    
    public MedicineInventoryDirectory(){
        inventoryList = new ArrayList<>();
        
    }

    public ArrayList<MedicineInventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(ArrayList<MedicineInventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
    
    public void addInventory(Medicine medicine, int qty,int standardQty){
        MedicineInventory inv = new MedicineInventory();
        inv.setMedicine(medicine);
        inv.setQuantity(qty);
        inv.setStandardStock(standardQty);
        
        inventoryList.add(inv);
        
    }
    
    public MedicineInventory findInventoryByMedicine(Medicine medicine){
        
        for(MedicineInventory mi :inventoryList ){
            if(medicine.equals(mi.getMedicine())){
                return mi;
                }
        }
        return null;
    }
    
    
}
