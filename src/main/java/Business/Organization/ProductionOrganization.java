/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;
import Business.Role.Role;
import Business.Role.ProductionAdminRole;
import Business.Role.ProductionRole;
import java.util.ArrayList;


/**
 *
 * @author vyngo
 */
public class ProductionOrganization extends Organization{
    public ProductionOrganization() {
        super(Type.Production.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new ProductionAdminRole());
        roles.add(new ProductionRole());
        return roles;
    }
}
    

