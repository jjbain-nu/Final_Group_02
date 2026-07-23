package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.WholesalerRole.WholesalerWorkAreaJPanel;

/**
 * Administrative role for a wholesaler inventory organization.
 */
public class InventoryAdminRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account,
            Organization organization, Enterprise enterprise, EcoSystem business) {
        return new WholesalerWorkAreaJPanel(account, enterprise, "Inventory Admin Work Area");
    }

    @Override
    public String toString() {
        return RoleType.InventoryAdmin.toString();
    }
}
