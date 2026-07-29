package Business.Organization;

import Business.Role.InventoryAdminRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 * Inventory department within a wholesaler enterprise.
 */
public class InventoryOrganization extends Organization {

    public InventoryOrganization() {
        super(Type.Inventory.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new InventoryAdminRole());
        return roles;
    }
}
