package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.WholesalerRole.WholesalerWorkflowJPanel;

/**
 * Role responsible for preparing wholesaler shipping orders.
 */
public class ShippingOrderStaffRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account,
            Organization organization, Enterprise enterprise, EcoSystem business) {
        return new WholesalerWorkflowJPanel(userProcessContainer, WholesalerWorkflowJPanel.View.SHIPPING_ORDER_STAFF, account, organization, enterprise);
    }

    @Override
    public String toString() {
        return "Shipping Order Staff";
    }
}
