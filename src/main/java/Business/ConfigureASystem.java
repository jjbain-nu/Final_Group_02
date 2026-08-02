package Business;

import Business.Employee.Employee;
import Business.Role.LabManagerRole;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import Business.Enterprise.Enterprise;
import Business.Enterprise.HospitalEnterprise;
import Business.Enterprise.ManufacturerEnterprise;
import Business.Enterprise.SupplierEnterprise;
import Business.Enterprise.TransportEnterprise;
import Business.Enterprise.WholesalerEnterprise;
import Business.Hospital.Medicine;
import Business.Hospital.MedicineCatalog;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Organization.PharmacyOrganization;
import Business.Organization.ProcurementOrganization;
import Business.Organization.InventoryOrganization;
import Business.Organization.ProductionOrganization;
import Business.Organization.QualityAssuranceOrganization;
import Business.Organization.ShippingOrganization;
import Business.Organization.SupplierOrganization;
import Business.Organization.TransportOrganization;
import Business.Role.AdminRole;
import Business.Role.DriverRole;
import Business.Role.MaterialShippingRole;
import Business.Role.InventoryAdminRole;
import Business.Role.PharmacyRole;
import Business.Role.ProcurementRole;
import Business.Role.ProductionAdminRole;
import Business.Role.ProductionRole;
import Business.Role.QualityAssuranceRole;
import Business.Role.ShippingOperatorRole;
import Business.Role.ShippingOrderStaffRole;
import Business.Role.SupplierAdminRole;
import Business.Role.TransportAdminRole;
import Business.Production.ProductionOrder;
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
        
         //create a supplier enterprise
        SupplierEnterprise supplierEnterprise = new SupplierEnterprise("Supplier A");
        
        //initialize supplier organizations
        SupplierOrganization supplierOrg = new SupplierOrganization();
        supplierEnterprise.getOrganizationDirectory().getOrganizationList().add(supplierOrg);
        network.getEnterpriseDirectory().getEnterpriseList().add(supplierEnterprise);
        
        //have some supplier enterprise employees 
        Employee enterpriseAdminEmp = supplierEnterprise.getEmployeeDirectory().createEmployee("supplierEnterpriseAdmin");
        supplierEnterprise.getUserAccountDirectory().createUserAccount(
                "SEA",
                "1111",
                enterpriseAdminEmp,
                new AdminRole());
        
        // have some supplier employees
        Employee supplierAdminEmp01 = supplierOrg.getEmployeeDirectory().createEmployee("SupplierAdmin01");
        Employee MaterialShipping01 = supplierOrg.getEmployeeDirectory().createEmployee("MaterialShipping01");
        
        //create user account
        UserAccount sa01 = supplierOrg.getUserAccountDirectory().createUserAccount("SA","1111",supplierAdminEmp01, new SupplierAdminRole());
        UserAccount ms01 = supplierOrg.getUserAccountDirectory().createUserAccount("MS","1111",MaterialShipping01, new MaterialShippingRole());

        //create a manufacturer enterprise (Production + Quality Assurance)
        ManufacturerEnterprise manufacturerEnterprise = new ManufacturerEnterprise("Manufacturer");
        network.getEnterpriseDirectory().getEnterpriseList().add(manufacturerEnterprise);

        ProductionOrganization productionOrg = new ProductionOrganization();
        manufacturerEnterprise.getOrganizationDirectory().getOrganizationList().add(productionOrg);

        QualityAssuranceOrganization qaOrg = new QualityAssuranceOrganization();
        manufacturerEnterprise.getOrganizationDirectory().getOrganizationList().add(qaOrg);

        Employee manufacturerAdminEmp = manufacturerEnterprise.getEmployeeDirectory().createEmployee("manufacturerEnterpriseAdmin");
        manufacturerEnterprise.getUserAccountDirectory().createUserAccount("MEA", "1111", manufacturerAdminEmp, new AdminRole());

        Employee productionAdminEmp = productionOrg.getEmployeeDirectory().createEmployee("ProductionAdmin01");
        Employee productionOperatorEmp = productionOrg.getEmployeeDirectory().createEmployee("ProductionOperator01");
        Employee qaEmp = qaOrg.getEmployeeDirectory().createEmployee("QA01");

        UserAccount pa01 = productionOrg.getUserAccountDirectory().createUserAccount("PA", "1111", productionAdminEmp, new ProductionAdminRole());
        UserAccount po01 = productionOrg.getUserAccountDirectory().createUserAccount("PO", "1111", productionOperatorEmp, new ProductionRole());
        UserAccount qa01 = qaOrg.getUserAccountDirectory().createUserAccount("QA", "1111", qaEmp, new QualityAssuranceRole());

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
            mr.setSender(pa01);
            mr.setReceiver(sa01);
            mr.setStatus("Sent");
            mr.setRequestDate(requestDate);
            
                   
            supplierOrg.getWorkQueue().getWorkRequestList().add(mr);
        }

        // Sample production orders with a range of dashboard-facing statuses,
        // for the Supply Chain Control Tower to display Manufacturer production
        // status alongside other enterprises. These are separate from the
        // internal Sent/Completed/Registered workflow statuses used by the
        // Production Operator screens above.
        String[] sampleProducts = {"Paracetamol 500mg", "Ibuprofen 200mg", "Amoxicillin 250mg", "Metformin 500mg"};
        String[] sampleStatuses = {"Pending", "In Production", "Ready to Ship", "Shipped"};
        for (int i = 0; i < sampleStatuses.length; i++) {
            ProductionOrder sampleOrder = new ProductionOrder(sampleProducts[i], faker.number().numberBetween(50, 500));
            sampleOrder.setMessage("Production order: " + sampleProducts[i]);
            sampleOrder.setSender(pa01);
            sampleOrder.setReceiver(po01);
            sampleOrder.setStatus(sampleStatuses[i]);
            sampleOrder.setRequestDate(faker.date().past(5, TimeUnit.DAYS));
            productionOrg.getWorkQueue().getWorkRequestList().add(sampleOrder);
        }

          //create hospital enterprises
        HospitalEnterprise hospitalEnterpriseA = new HospitalEnterprise("Hospital A");
        HospitalEnterprise hospitalEnterpriseB = new HospitalEnterprise("Hospital B");
        HospitalEnterprise hospitalEnterpriseC = new HospitalEnterprise("Hospital C");
       
        network.getEnterpriseDirectory().getEnterpriseList().add(hospitalEnterpriseA);
        network.getEnterpriseDirectory().getEnterpriseList().add(hospitalEnterpriseB);
        network.getEnterpriseDirectory().getEnterpriseList().add(hospitalEnterpriseC);
        
         //create hospital organizations
        ProcurementOrganization procurementOrgA = new ProcurementOrganization();
        PharmacyOrganization pharmacyOrgA = new PharmacyOrganization();
        hospitalEnterpriseA.getOrganizationDirectory().getOrganizationList().add(procurementOrgA);
        hospitalEnterpriseA.getOrganizationDirectory().getOrganizationList().add(pharmacyOrgA);
        
        ProcurementOrganization procurementOrgB = new ProcurementOrganization();
        PharmacyOrganization pharmacyOrgB = new PharmacyOrganization();
        hospitalEnterpriseB.getOrganizationDirectory().getOrganizationList().add(procurementOrgB);
        hospitalEnterpriseB.getOrganizationDirectory().getOrganizationList().add(pharmacyOrgB);
        
        ProcurementOrganization procurementOrgC = new ProcurementOrganization();
        PharmacyOrganization pharmacyOrgC = new PharmacyOrganization();
        hospitalEnterpriseC.getOrganizationDirectory().getOrganizationList().add(procurementOrgC);
        hospitalEnterpriseC.getOrganizationDirectory().getOrganizationList().add(pharmacyOrgC);
        
         //have some hospital enterprice admin employees 
        Employee hospitalEnterpriseAdminA = hospitalEnterpriseA.getEmployeeDirectory().createEmployee("hospitalEnterpriseAdminA");
        Employee hospitalEnterpriseAdminB = hospitalEnterpriseB.getEmployeeDirectory().createEmployee("hospitalEnterpriseAdminB");
        Employee hospitalEnterpriseAdminC = hospitalEnterpriseC.getEmployeeDirectory().createEmployee("hospitalEnterpriseAdminC");
        
        hospitalEnterpriseA.getUserAccountDirectory().createUserAccount(
                "HEA-A",
                "1111",
                hospitalEnterpriseAdminA,
                new AdminRole());
        
        hospitalEnterpriseB.getUserAccountDirectory().createUserAccount(
                "HEA-B",
                "1111",
                hospitalEnterpriseAdminB,
                new AdminRole());
         
        hospitalEnterpriseC.getUserAccountDirectory().createUserAccount(
                "HEA-C",
                "1111",
                hospitalEnterpriseAdminC,
                new AdminRole());
        
        // have some hospital employees
        
        Employee procurementEmpA = procurementOrgA.getEmployeeDirectory().createEmployee("Procurement A");
        Employee procurementEmpB = procurementOrgB.getEmployeeDirectory().createEmployee("Procurement B");
        Employee procurementEmpC = procurementOrgC.getEmployeeDirectory().createEmployee("Procurement C");
        
        Employee PharmacyEmpA = pharmacyOrgA.getEmployeeDirectory().createEmployee("Pharmacy A");
        Employee PharmacyEmpB = pharmacyOrgB.getEmployeeDirectory().createEmployee("Pharmacy B");
        Employee PharmacyEmpC = pharmacyOrgC.getEmployeeDirectory().createEmployee("Pharmacy C");

        UserAccount proA = procurementOrgA.getUserAccountDirectory().createUserAccount("ProA","1111",procurementEmpA, new ProcurementRole());
        UserAccount proB = procurementOrgB.getUserAccountDirectory().createUserAccount("ProB","1111",procurementEmpB, new ProcurementRole());
        UserAccount proC = procurementOrgC.getUserAccountDirectory().createUserAccount("ProC","1111",procurementEmpC, new ProcurementRole());
        
        UserAccount phaA = pharmacyOrgA.getUserAccountDirectory().createUserAccount("PhaA","1111",PharmacyEmpA, new PharmacyRole());
        UserAccount phaB = pharmacyOrgB.getUserAccountDirectory().createUserAccount("PhaB","1111",PharmacyEmpB, new PharmacyRole());
        UserAccount phaC = pharmacyOrgC.getUserAccountDirectory().createUserAccount("PhaC","1111",PharmacyEmpC, new PharmacyRole());
        
        //Create sample medicines and hospital inventory
        MedicineCatalog sharedCatalog = new MedicineCatalog();
               
        String[] medicines = {
        "Paracetamol Tablet",
        "Ibuprofen Tablet",
        "Amoxicillin Capsule",
        "Metformin Tablet",
        "Atorvastatin Tablet",
        "Losartan Tablet",
        "Aspirin Tablet",
        "Omeprazole Capsule",
        "Insulin Injection",
        "Azithromycin Tablet"
};
        
        for(String medicineName : medicines){
               
            String medicineId = faker.regexify("[A-Z]{3}[0-9]{4}");
            int medicinePrice = faker.number().numberBetween(100,2000);
            
            Medicine medicine = sharedCatalog.addMedicine(medicineId, medicineName, medicinePrice);
            
            int qtyA = faker.number().numberBetween(0,100);
            int standardqtyA = faker.options().option(30,40,50,60,70);
            pharmacyOrgA.getMedicineInventoryDirectory().addInventory(medicine,qtyA, standardqtyA);
            
            int qtyB = faker.number().numberBetween(0,100);
            int standardqtyB = faker.options().option(30,40,50,60,70);
            pharmacyOrgB.getMedicineInventoryDirectory().addInventory(medicine, qtyB,standardqtyB);
            
            int qtyC = faker.number().numberBetween(0,100);
            int standardqtyC = faker.options().option(30,40,50,60,70);
            pharmacyOrgC.getMedicineInventoryDirectory().addInventory(medicine, qtyC,standardqtyC);
        
        }
        
        hospitalEnterpriseA.setMedicineCatalog(sharedCatalog);
        hospitalEnterpriseB.setMedicineCatalog(sharedCatalog);
        hospitalEnterpriseC.setMedicineCatalog(sharedCatalog);

        // Seed three independent wholesalers, each with the organizations and
        // role accounts required to exercise the internal wholesaler workflow.
        WholesalerEnterprise wholesalerA = null;
        WholesalerEnterprise wholesalerB = null;
        WholesalerEnterprise wholesalerC = null;
        
        for (String suffix : new String[]{"A", "B", "C"}) {
            WholesalerEnterprise wholesaler = new WholesalerEnterprise("Wholesaler " + suffix);
            network.getEnterpriseDirectory().getEnterpriseList().add(wholesaler);

            if (suffix.equals("A")) {
                wholesalerA = wholesaler;
            } else if (suffix.equals("B")) {
                wholesalerB = wholesaler;
            } else if (suffix.equals("C")) {
                wholesalerC = wholesaler;
            }
            
            String enterpriseAdminUsername = "WEA-" + suffix;
            String pass = "1111";
            Employee enterpriseAdmin = wholesaler.getEmployeeDirectory().createEmployee("Wesley Wholesaler Admin " + suffix);
            wholesaler.getUserAccountDirectory().createUserAccount(
                    enterpriseAdminUsername, pass, enterpriseAdmin, new AdminRole());

            InventoryOrganization inventoryOrg = new InventoryOrganization();
            ShippingOrganization shippingOrg = new ShippingOrganization();
            wholesaler.getOrganizationDirectory().getOrganizationList().add(inventoryOrg);
            wholesaler.getOrganizationDirectory().getOrganizationList().add(shippingOrg);

            String inventoryUsername = "Inventory" + suffix;
            Employee inventoryEmployee = inventoryOrg.getEmployeeDirectory().createEmployee("william Inventory " + suffix);
            inventoryOrg.getUserAccountDirectory().createUserAccount(
                    inventoryUsername, pass, inventoryEmployee, new InventoryAdminRole());

            String shippingStaffUsername = "ShipStaff" + suffix;
            Employee shippingStaffEmployee = shippingOrg.getEmployeeDirectory().createEmployee("wanda Shipping Staff " + suffix);
            shippingOrg.getUserAccountDirectory().createUserAccount(
                    shippingStaffUsername, pass, shippingStaffEmployee, new ShippingOrderStaffRole());

            String shippingOperatorUsername = "ShipOp" + suffix;
            Employee shippingOperatorEmployee = shippingOrg.getEmployeeDirectory().createEmployee("warren Shipping Operator " + suffix);
            shippingOrg.getUserAccountDirectory().createUserAccount(
                    shippingOperatorUsername, pass, shippingOperatorEmployee, new ShippingOperatorRole());
        }
        
            hospitalEnterpriseA.setAssignedWholesaler(wholesalerA);
            hospitalEnterpriseB.setAssignedWholesaler(wholesalerB);
            hospitalEnterpriseC.setAssignedWholesaler(wholesalerC);
        
        return system;
    }
    
}
