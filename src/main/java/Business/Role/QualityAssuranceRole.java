package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.QualityAssuranceOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.QualityAssuranceRole.QualityAssuranceWorkAreaJPanel;

/**
 *
 * @author vy
 */
public class QualityAssuranceRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                            UserAccount account,
                            Organization organization,
                            Enterprise enterprise,
                            EcoSystem business) {
        return new QualityAssuranceWorkAreaJPanel(
                userProcessContainer,
                account,
                (QualityAssuranceOrganization) organization,
                enterprise);
    }
}