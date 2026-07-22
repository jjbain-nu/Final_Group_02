/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class MaterialInventoryDirectory {
    
    private ArrayList<MaterialInventory> inventoryList;
    
    public MaterialInventoryDirectory(){
        inventoryList = new ArrayList<>();
        
    }

    public ArrayList<MaterialInventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(ArrayList<MaterialInventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
    
    public MaterialInventory addInventory(Material material, int qty){
        MaterialInventory mi = new MaterialInventory(material,qty);
        inventoryList.add(mi);
        return mi;
        
    }
    
    public MaterialInventory findInventoryByMaterial(Material material){
        for(MaterialInventory mi : inventoryList){
            if(mi.getMaterial().equals(material)){
                return mi;
            }
        }
        return null;
    }
    
}
