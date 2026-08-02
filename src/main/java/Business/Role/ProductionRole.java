package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.ProductionOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.ProductionRole.ProductionWorkAreaJPanel;

/**
 *
 * @author vy
 */
public class ProductionRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                            UserAccount account,
                            Organization organization,
                            Enterprise enterprise,
                            EcoSystem business) {
        return new ProductionWorkAreaJPanel(
                userProcessContainer,
                account,
                (ProductionOrganization) organization,
                enterprise);
    }
}