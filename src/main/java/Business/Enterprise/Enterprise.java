package Business.Enterprise;

import Business.Organization.Organization;
import Business.Organization.Organization.Type;
import Business.Organization.OrganizationDirectory;

/**
 * Base type for all enterprises in the ecosystem.
 */
public abstract class Enterprise extends Organization {

    private EnterpriseType enterpriseType;
    private OrganizationDirectory organizationDirectory;

    public enum EnterpriseType {
        Supplier("Supplier"),
        Manufacturer("Manufacturer"),
        Transport("Transport"),
        Wholesaler("Wholesaler"),
        Hospital("Hospital");

        private final String value;

        EnterpriseType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public Enterprise(String name, EnterpriseType type) {
        super(name);
        enterpriseType = type;
        organizationDirectory = new OrganizationDirectory();
    }

    public OrganizationDirectory getOrganizationDirectory() {
        return organizationDirectory;
    }

    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(EnterpriseType enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    /**
     * Defines which organization types this enterprise is allowed to contain.
     * Subclasses override this for their supported organization types.
     */
    public boolean supportsOrganization(Type type) {
        return false;
    }
}
