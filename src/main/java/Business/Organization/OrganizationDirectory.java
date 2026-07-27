package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;

public class OrganizationDirectory {
    private ArrayList<Organization> organizationList;
    public OrganizationDirectory() { organizationList = new ArrayList<>(); }
    public ArrayList<Organization> getOrganizationList() { return organizationList; }

    public Organization createOrganization(Type type) {
        Organization organization = null;
        if (type == Type.Doctor) organization = new DoctorOrganization();
        else if (type == Type.Lab) organization = new LabOrganization();
        else if (type == Type.Supplier) organization = new SupplierOrganization();
        else if (type == Type.Inventory) organization = new InventoryOrganization();
        else if (type == Type.Shipping) organization = new ShippingOrganization();
        else if (type == Type.Transport) organization = new TransportOrganization();
        if (organization != null) organizationList.add(organization);
        return organization;
    }
}
