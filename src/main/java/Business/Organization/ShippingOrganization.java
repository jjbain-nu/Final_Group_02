package Business.Organization;

import Business.Role.Role;
import Business.Role.ShippingOperatorRole;
import Business.Role.ShippingOrderStaffRole;
import java.util.ArrayList;

/**
 * Shipping department within a wholesaler enterprise.
 */
public class ShippingOrganization extends Organization {

    public ShippingOrganization() {
        super(Type.Shipping.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new ShippingOrderStaffRole());
        roles.add(new ShippingOperatorRole());
        return roles;
    }
}
