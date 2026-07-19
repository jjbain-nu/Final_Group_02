package Business;

import Business.Employee.Employee;
import Business.Enterprise.SupplierEnterprise;
import Business.Network.Network;
import static Business.Organization.Organization.Type.Admin;
import Business.Organization.SupplierOrganization;
import Business.Role.AdminRole;
import Business.Role.LabManagerRole;
import Business.Role.MaterialShippingRole;
import Business.Role.SupplierAdminRole;
import Business.Role.SystemAdminRole;
import Business.Supplier.Material;
import Business.Supplier.MaterialCatalog;
import Business.UserAccount.UserAccount;
import com.github.javafaker.Faker;

/**
 *
 * @author rrheg
 */
public class ConfigureASystem {
    
    public static EcoSystem configure(){
        
        EcoSystem system = EcoSystem.getInstance();
        
        //Create a network
        Network network = system.createAndAddNetwork();
        network.setName("Network");
        
        //create an enterprise
        SupplierEnterprise supplierEnterprise = new SupplierEnterprise("Supplier");
        
        //initialize some organizations
        SupplierOrganization supplierOrg = new SupplierOrganization();
        supplierEnterprise.getOrganizationDirectory().getOrganizationList().add(supplierOrg);
        network.getEnterpriseDirectory().getEnterpriseList().add(supplierEnterprise);
        
        //have some employees 
        Employee enterpriseAdminEmp = supplierEnterprise.getEmployeeDirectory().createEmployee("supplierEnterpriseAdmin");
        supplierEnterprise.getUserAccountDirectory().createUserAccount(
                "supplierEnterpriseAdmin",
                "1111",
                enterpriseAdminEmp,
                new AdminRole());
        
        Employee supplierAdminEmp01 = supplierOrg.getEmployeeDirectory().createEmployee("SupplierAdmin01");
        Employee MaterialShipping01 = supplierOrg.getEmployeeDirectory().createEmployee("MaterialShipping01");
        
        //create user account
        supplierOrg.getUserAccountDirectory().createUserAccount("supplierAdmin","supplierAdmin",supplierAdminEmp01, new SupplierAdminRole());
        supplierOrg.getUserAccountDirectory().createUserAccount("materialShipping","materialShipping",MaterialShipping01, new MaterialShippingRole());        

        System.out.println("Supplier Admin Created");
        
        Employee employee = system.getEmployeeDirectory().createEmployee("sysadmin");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        UserAccount labManager = system.getUserAccountDirectory().createUserAccount("labManager", "sysadmin", employee, new LabManagerRole());
        
        
        //Create sample material and material inventory
        MaterialCatalog catalog = supplierEnterprise.getMaterialCatalog();
        Faker faker = new Faker();
        
        for(int i = 0 ; i<100;i++ ){
            String materialId = faker.regexify("[A-Z]{3}[0-9]{4}");
            String materialName = faker.commerce().productName();
            int materialWeight = faker.number().numberBetween(1,100);
            
            Material material = catalog.addMaterial(materialId, materialName, materialWeight);
            int qty = faker.number().numberBetween(0,1000);
            
            supplierOrg.getMaterialInventoryDirectory().addInventory(material, qty);
            
        }
        
        return system;
    }
    
}
