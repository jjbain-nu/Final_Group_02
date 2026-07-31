package Business.Organization;

import Business.Role.Role;
import Business.Role.QualityAssuranceRole;
import java.util.ArrayList;

/**
 *
 * @author vy
 */
public class QualityAssuranceOrganization extends Organization {

    public QualityAssuranceOrganization() {
        super(Type.QualityAssurance.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new QualityAssuranceRole());
        return roles;
    }
}