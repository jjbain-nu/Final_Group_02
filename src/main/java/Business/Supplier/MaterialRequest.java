/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

import Business.WorkQueue.WorkRequest;
import java.util.Date;

/**
 *
 * @author yu101
 */
public class MaterialRequest extends WorkRequest {
    
    private Material material;
    private int qty;
    
    public MaterialRequest(Material material,int qty){
        this.material = material;
        this.qty = qty;
        setStatus("sent");
        setRequestDate(new Date());
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
    
    @Override
    public String toString(){
        return material.getMaterialName() + "(" + qty +")";
    }
    
}
