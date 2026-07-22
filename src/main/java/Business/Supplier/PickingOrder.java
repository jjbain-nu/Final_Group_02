/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

import Business.Organization.SupplierOrganization;
import Business.WorkQueue.WorkRequest;
import java.util.Date;

/**
 *
 * @author yu101
 */
public class PickingOrder extends WorkRequest {
    
    private String pickingOrderId;
    private MaterialRequest materialRequest;
    private SupplierOrganization organization;
    
    public PickingOrder(String id, MaterialRequest mr, SupplierOrganization org){
        
        this.pickingOrderId = id;
        this.materialRequest = mr;
        this.organization = org;
        
        this.setRequestDate(new Date());
        this.setStatus("Allocated");
    }

    public String getPickingOrderId() {
        return pickingOrderId;
    }

    public void setPickingOrderId(String pickingOrderId) {
        this.pickingOrderId = pickingOrderId;
    }

    public MaterialRequest getMaterialRequest() {
        return materialRequest;
    }

    public void setMaterialRequest(MaterialRequest materialRequest) {
        this.materialRequest = materialRequest;
    }

    public SupplierOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(SupplierOrganization organization) {
        this.organization = organization;
    }
    
    @Override
    public String toString(){
        return pickingOrderId;
    }
    
}
