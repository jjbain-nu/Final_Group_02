/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;

import Business.Hospital.MedicineInventoryDirectory;
import Business.Role.PharmacyRole;
import Business.Role.ProcurementRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class PharmacyOrganization extends Organization {
    
    private MedicineInventoryDirectory medicineInventoryDirectory;
    
     public PharmacyOrganization() {
        super(Organization.Type.Pharmacy.getValue());
        
        medicineInventoryDirectory = new MedicineInventoryDirectory();
        
     }

    public MedicineInventoryDirectory getMedicineInventoryDirectory() {
        return medicineInventoryDirectory;
    }

    public void setMedicineInventoryList(MedicineInventoryDirectory medicineInventoryDirectory) {
        this.medicineInventoryDirectory = medicineInventoryDirectory;
    }
     
     
      @Override
        public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new PharmacyRole());
        return roles;
    }
}
