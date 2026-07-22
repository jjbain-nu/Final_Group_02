/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

/**
 *
 * @author yu101
 */
public class MaterialInventory {
    
    private Material material;
    private int availableQty;
    private int pickingQty;
    
    
    public MaterialInventory(Material material, int qty){
        this.material = material;
        this.availableQty = qty;
        this.pickingQty = 0;
    } 

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int qty) {
        this.availableQty = qty;
    }

    public int getPickingQty() {
        return pickingQty;
    }

    public void setPickingQty(int pickingQty) {
        this.pickingQty = pickingQty;
    }
    
    
    
    public String toString(){
        return this.material.getMaterialName();
        
        
    }
    
}
