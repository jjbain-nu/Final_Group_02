package Business;

import Business.Employee.Employee;
import Business.Enterprise.SupplierEnterprise;
import Business.Network.Network;
import Business.Organization.SupplierOrganization;
import Business.Role.LabManagerRole;
import Business.Role.MaterialShippingRole;
import Business.Role.SupplierAdminRole;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;

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
        Employee supplierAdminEmp01 = supplierOrg.getEmployeeDirectory().createEmployee("SupplierAdmin01");
        Employee MaterialShipping01 = supplierOrg.getEmployeeDirectory().createEmployee("MaterialShipping01");
        
        //create user account
        supplierOrg.getUserAccountDirectory().createUserAccount("supplierAdmin","****",supplierAdminEmp01, new SupplierAdminRole());
        supplierOrg.getUserAccountDirectory().createUserAccount("MaterialShipping","****",MaterialShipping01, new MaterialShippingRole());        

        
        Employee employee = system.getEmployeeDirectory().createEmployee("sysadmin");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        UserAccount labManager = system.getUserAccountDirectory().createUserAccount("labManager", "sysadmin", employee, new LabManagerRole());
        
        return system;
    }
    
}
