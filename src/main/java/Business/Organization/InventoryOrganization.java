package Business.Organization;

import Business.Role.InventoryAdminRole;
import Business.Role.Role;
import Business.Hospital.MedicineInventoryDirectory;
import java.util.ArrayList;

/**
 * Inventory department within a wholesaler enterprise.
 */
public class InventoryOrganization extends Organization {
    private MedicineInventoryDirectory medicineInventoryDirectory;

    public InventoryOrganization() {
        super(Type.Inventory.getValue());
        medicineInventoryDirectory = new MedicineInventoryDirectory();
    }

    public MedicineInventoryDirectory getMedicineInventoryDirectory() {
        return medicineInventoryDirectory;
    }

    public void setMedicineInventoryDirectory(MedicineInventoryDirectory medicineInventoryDirectory) {
        this.medicineInventoryDirectory = medicineInventoryDirectory;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new InventoryAdminRole());
        return roles;
    }
}
