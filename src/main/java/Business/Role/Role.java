/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author raunak
 */
public abstract class Role {
    
    public enum RoleType{
        Admin("Admin"),
        Doctor("Doctor"),
        LabAssistant("Lab Assistant"),
        
        SupplierAdmin("Supplier Admin"),
        MaterialShipping("Material Shipping"),
        ProductionAdmin("Production Admin"),
        Production("Production"),
        QualityAssurance("Quality Assurance"),
        TransportAdmin("Transport Admin"),
        Driver("Driver"),
        InventoryAdmin("Wholesaler Inventory Admin"),
        ShippingOrder("Wholesaler Shipping Order"),
        Shipping("Wholesaler Shipping"),
        Procurement("Hospital Procurement"),
        Pharmacy("Hospital Pharmacy");
        
        private String value;
        private RoleType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
    public abstract JPanel createWorkArea(JPanel userProcessContainer, 
            UserAccount account, 
            Organization organization, 
            Enterprise enterprise, 
            EcoSystem business);

    @Override
    public String toString() {
        return this.getClass().getName();
    }
    
    
}