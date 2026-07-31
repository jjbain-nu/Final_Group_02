package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.ProductionOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.ProductionAdminRole.ProductionAdminWorkAreaJPanel;

/**
 *
 * @author vy
 */
public class ProductionAdminRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                            UserAccount account,
                            Organization organization,
                            Enterprise enterprise,
                            EcoSystem business) {
        return new ProductionAdminWorkAreaJPanel(
                userProcessContainer,
                account,
                (ProductionOrganization) organization,
                enterprise);
    }
}