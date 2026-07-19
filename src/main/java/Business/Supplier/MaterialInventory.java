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
    private int qty;
    
    public MaterialInventory(Material material, int qty){
        this.material = material;
        this.qty = qty;
    } 

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
    public String toString(){
        return this.material.getMaterialName();
        
        
    }
    
}
