/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Hospital.MedicineCatalog;
import Business.Role.MaterialShippingRole;
import Business.Role.PharmacyRole;
import Business.Role.ProcurementRole;
import Business.Role.Role;
import Business.Role.SupplierAdminRole;
import Business.Supplier.MaterialCatalog;
import java.util.ArrayList;

/**
 *
 * @author MyPC1
 */
public class HospitalEnterprise extends Enterprise {
    
    private MedicineCatalog medicineCatalog;
    private WholesalerEnterprise assignedWholesaler;
    
    
    public HospitalEnterprise(String name){
        super(name,EnterpriseType.Hospital);
        
        this.medicineCatalog = new MedicineCatalog();
        
        
    }
    
    public WholesalerEnterprise getAssignedWholesaler() {
        return assignedWholesaler;
    }

    public void setAssignedWholesaler(WholesalerEnterprise assignedWholesaler) {
        this.assignedWholesaler = assignedWholesaler;
    }

    public MedicineCatalog getMedicineCatalog() {
        return medicineCatalog;
    }

    public void setMedicineCatalog(MedicineCatalog medicineCatalog) {
        this.medicineCatalog = medicineCatalog;
    }
    
    
    
    
    
     @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new ProcurementRole());
        roles.add(new PharmacyRole());
        
        return roles;
    }

    @Override
    public boolean supportsOrganization(Type type) {
        return type == Type.Doctor || type == Type.Lab || type == Type.Supplier;
    }
}
