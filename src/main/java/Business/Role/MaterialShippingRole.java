/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.SupplierOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.MaterialShippingRole.MaterialShippingWorkPanel;

/**
 *
 * @author yu101
 */
public class MaterialShippingRole extends Role {
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
    return new MaterialShippingWorkPanel(userProcessContainer, account, (SupplierOrganization)organization, enterprise);
       
}
    @Override
    public String toString() {
        return "Material Shipping";
    }
    
}
