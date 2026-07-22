/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;

import Business.Role.DoctorRole;
import Business.Role.MaterialShippingRole;
import Business.Role.Role;
import Business.Role.SupplierAdminRole;
import Business.Supplier.MaterialInventoryDirectory;
import Business.Supplier.PickingOrderDirectory;
import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class SupplierOrganization extends Organization {
        
    private MaterialInventoryDirectory materialInventoryDirectory;
    private PickingOrderDirectory pickingOrderDirectory;
    
     public SupplierOrganization() {
        super(Organization.Type.Supplier.getValue());
        materialInventoryDirectory = new MaterialInventoryDirectory();
        pickingOrderDirectory = new PickingOrderDirectory();
    }

     
    public MaterialInventoryDirectory getMaterialInventoryDirectory() {
        return materialInventoryDirectory;
    }

    public void setMaterialInventoryDirectory(MaterialInventoryDirectory materialInventoryDirectory) {
        this.materialInventoryDirectory = materialInventoryDirectory;
    }

    public PickingOrderDirectory getPickingOrderDirectory() {
        return pickingOrderDirectory;
    }

    public void setPickingOrderDirectory(PickingOrderDirectory pickingOrderDirectory) {
        this.pickingOrderDirectory = pickingOrderDirectory;
    }
    
    
     
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new SupplierAdminRole());
        roles.add(new MaterialShippingRole());
        return roles;
    }
}
