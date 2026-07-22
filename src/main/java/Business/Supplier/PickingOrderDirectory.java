/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

import Business.Organization.SupplierOrganization;
import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class PickingOrderDirectory {
    
    private ArrayList<PickingOrder> pickingOrderList;
    
    public PickingOrderDirectory(){
        this.pickingOrderList = new ArrayList<>();
        
    }
    
    public String generatePickingOrderId(){
        return String.format("PO-%04d",pickingOrderList.size() + 1);
        
    }

    public ArrayList<PickingOrder> getPickingOrderList() {
        return pickingOrderList;
    }

    public void setPickingOrderList(ArrayList<PickingOrder> pickingOrderList) {
        this.pickingOrderList = pickingOrderList;
    }
    
    public PickingOrder addPickingOrder(MaterialRequest mr, SupplierOrganization org){
        String id = String.format("PO-%04d",pickingOrderList.size() + 1);
        
        PickingOrder po = new PickingOrder(id,mr,org);
        this.pickingOrderList.add(po);
        return po;
        
    }
    
}
