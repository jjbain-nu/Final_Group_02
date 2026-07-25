/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.MaterialShippingRole;
import Business.Role.PharmacyRole;
import Business.Role.ProcurementRole;
import Business.Role.Role;
import Business.Role.SupplierAdminRole;
import java.util.ArrayList;

/**
 *
 * @author MyPC1
 */
public class HospitalEnterprise extends Enterprise {
    
    public HospitalEnterprise(String name){
        super(name,EnterpriseType.Hospital);
    }
    
     @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new ProcurementRole());
        roles.add(new PharmacyRole());
        
        return roles;
    }
}
