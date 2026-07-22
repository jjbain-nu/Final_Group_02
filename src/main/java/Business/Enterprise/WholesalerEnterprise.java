package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

/**
 * Represents a wholesaler enterprise in the ecosystem.
 */
public class WholesalerEnterprise extends Enterprise {

    public WholesalerEnterprise(String name) {
        super(name, EnterpriseType.Wholesaler);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}
