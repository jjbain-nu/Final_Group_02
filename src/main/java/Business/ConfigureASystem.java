package Business;

import Business.Employee.Employee;
import Business.Role.LabManagerRole;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import Business.Enterprise.Enterprise;
import Business.Enterprise.TransportEnterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Organization.TransportOrganization;
import Business.Role.DriverRole;
import Business.Role.TransportAdminRole;

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
        
        return system;
    }
    
}
