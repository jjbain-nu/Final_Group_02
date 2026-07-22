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
import Business.Supplier.MaterialRequest;
import Business.UserAccount.UserAccount;
import com.github.javafaker.Faker;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
                "SEA",
                "1111",
                enterpriseAdminEmp,
                new AdminRole());
        
        Employee supplierAdminEmp01 = supplierOrg.getEmployeeDirectory().createEmployee("SupplierAdmin01");
        Employee MaterialShipping01 = supplierOrg.getEmployeeDirectory().createEmployee("MaterialShipping01");
        
        //create user account
        UserAccount sa01 = supplierOrg.getUserAccountDirectory().createUserAccount("SA","1111",supplierAdminEmp01, new SupplierAdminRole());
        UserAccount ms01 = supplierOrg.getUserAccountDirectory().createUserAccount("MS","1111",MaterialShipping01, new MaterialShippingRole());        

        System.out.println("Supplier Admin Created");
        
        Employee employee = system.getEmployeeDirectory().createEmployee("sysadmin");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        UserAccount labManager = system.getUserAccountDirectory().createUserAccount("labManager", "sysadmin", employee, new LabManagerRole());
        
        
        //Create 100 sample material and material inventory
        MaterialCatalog catalog = supplierEnterprise.getMaterialCatalog();
        Faker faker = new Faker();
        
        String[] materials = {
          "Paracetamol API",
          "Ibuprofen API",
          "Amoxicillin API",
          "Metformin API",
          "Atorvastatin API",
          "Losartan API",
          "Lactose",
          "Povidone",
          "Magnesium Stearate",
          "Microcrystalline Cellulose"
};
        
        for(int i = 0 ; i<100;i++ ){
            String materialId = faker.regexify("[A-Z]{3}[0-9]{4}");
            String materialName = faker.options().option(materials) + " - " + faker.regexify("[A-Z]{1}[0-9]{2}");
            int materialWeight = faker.number().numberBetween(1,100);
            
            Material material = catalog.addMaterial(materialId, materialName, materialWeight);
            int qty = faker.number().numberBetween(0,100);
            
            supplierOrg.getMaterialInventoryDirectory().addInventory(material, qty);
            
        }
        
        //Create 10 sample material requests from manufacturer
        Random rand = new Random();
        
        for (int i = 0; i<10 ; i++){
            Material randomMaterial = catalog.getMaterialList().get(rand.nextInt(catalog.getMaterialList().size()));
            int qty = faker.number().numberBetween(1,10);
            Date requestDate = faker.date().past(5, TimeUnit.DAYS);
            
            MaterialRequest mr = new MaterialRequest(randomMaterial,qty);
            mr.setMessage("Request: "+ randomMaterial.getMaterialName());
            mr.setSender(ua);
            mr.setReceiver(sa01);  
            mr.setStatus("Sent");
            mr.setRequestDate(requestDate);
            
                   
            supplierOrg.getWorkQueue().getWorkRequestList().add(mr);
        }
        
        return system;
    }
    
}
