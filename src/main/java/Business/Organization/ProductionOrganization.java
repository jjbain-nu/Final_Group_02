/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;
import Business.Role.Role;
import Business.Role.ProductionAdminRole;
import Business.Role.ProductionRole;
import Business.Production.ProductionPlanDirectory;
import Business.Supplier.MaterialInventoryDirectory;
import java.util.ArrayList;


/**
 *
 * @author vyngo
 */
public class ProductionOrganization extends Organization{

    private ProductionPlanDirectory productionPlanDirectory;
    private MaterialInventoryDirectory rawMaterialInventoryDirectory;

    public ProductionOrganization() {
        super(Type.Production.getValue());
        this.productionPlanDirectory = new ProductionPlanDirectory();
        this.rawMaterialInventoryDirectory = new MaterialInventoryDirectory();
    }

    public ProductionPlanDirectory getProductionPlanDirectory() {
        return productionPlanDirectory;
    }

    public void setProductionPlanDirectory(ProductionPlanDirectory productionPlanDirectory) {
        this.productionPlanDirectory = productionPlanDirectory;
    }

    public MaterialInventoryDirectory getRawMaterialInventoryDirectory() {
        return rawMaterialInventoryDirectory;
    }

    public void setRawMaterialInventoryDirectory(MaterialInventoryDirectory rawMaterialInventoryDirectory) {
        this.rawMaterialInventoryDirectory = rawMaterialInventoryDirectory;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new ProductionAdminRole());
        roles.add(new ProductionRole());
        return roles;
    }
}
    

