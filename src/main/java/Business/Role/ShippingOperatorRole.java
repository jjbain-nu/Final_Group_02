package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.WholesalerRole.ShippingOperatorWorkAreaJPanel;

/**
 * Role responsible for executing wholesaler shipments.
 */
public class ShippingOperatorRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account,
            Organization organization, Enterprise enterprise, EcoSystem business) {
        return new ShippingOperatorWorkAreaJPanel(enterprise);
    }

    @Override
    public String toString() {
        return "Shipping Operator";
    }
}
