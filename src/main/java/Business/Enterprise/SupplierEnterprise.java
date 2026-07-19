/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Enterprise;

import Business.Role.MaterialShippingRole;
import Business.Role.Role;
import Business.Role.SupplierAdminRole;
import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class SupplierEnterprise extends Enterprise{
    
    public SupplierEnterprise(String name){
        super(name,EnterpriseType.Supplier);
    }
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new SupplierAdminRole());
        roles.add(new MaterialShippingRole());
        
        return roles;
    }
        
}
