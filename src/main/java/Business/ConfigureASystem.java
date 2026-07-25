package Business;

import Business.Employee.Employee;
import Business.Role.LabManagerRole;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import Business.Enterprise.Enterprise;
import Business.Enterprise.SupplierEnterprise;
import Business.Enterprise.TransportEnterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Organization.SupplierOrganization;
import Business.Organization.TransportOrganization;
import Business.Role.AdminRole;
import Business.Role.DriverRole;
import Business.Role.MaterialShippingRole;
import Business.Role.SupplierAdminRole;
import Business.Role.TransportAdminRole;
import Business.Supplier.Material;
import Business.Supplier.MaterialCatalog;
import Business.Supplier.MaterialRequest;
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
        
        //create an enterprise
        //initialize some organizations
        //have some employees 
        //create user account
        
        
        Employee employee = system.getEmployeeDirectory().createEmployee("sysadmin");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        UserAccount labManager = system.getUserAccountDirectory().createUserAccount("labManager", "sysadmin", employee, new LabManagerRole());
        
        // --- Seed a Transport network so the demo has something to log in to ---
        Network network = system.createAndAddNetwork();
        network.setName("Pharma Supply Chain Network");

        TransportEnterprise transEnt = (TransportEnterprise)
                network.getEnterpriseDirectory().createAndAddEnterprise(
                        "FastLane Logistics", Enterprise.EnterpriseType.Transport);

        TransportOrganization transOrg = (TransportOrganization)
                transEnt.getOrganizationDirectory().createOrganization(
                        Organization.Type.Transport);

        // Seed one admin user + one driver user under the Transport Organization
        Employee adminEmp = transOrg.getEmployeeDirectory().createEmployee("Alice Admin");
        transOrg.getUserAccountDirectory().createUserAccount(
                "transadmin", "transadmin", adminEmp, new TransportAdminRole());

        Employee drvEmp = transOrg.getEmployeeDirectory().createEmployee("Dan Driver");
        transOrg.getUserAccountDirectory().createUserAccount(
                "driver", "driver", drvEmp, new DriverRole());

        // Seed a couple of trucks in the fleet
        transOrg.getFleetDirectory().addTruck("TN-01-AA-1234", "Volvo FH16", 20000);
        transOrg.getFleetDirectory().addTruck("TN-01-BB-5678", "Tata LPT 1613", 12000);
        
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
