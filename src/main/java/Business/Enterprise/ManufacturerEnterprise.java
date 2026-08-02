/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Enterprise;

import Business.Role.ProductionAdminRole;
import Business.Role.ProductionRole;
import Business.Role.QualityAssuranceRole;
import Business.Role.Role;
import java.util.ArrayList;


/**
 *
 * @author vyngo
 */
public class ManufacturerEnterprise extends Enterprise {

    public ManufacturerEnterprise(String name) {
        super(name, EnterpriseType.Manufacturer);
    }
@Override
public ArrayList<Role> getSupportedRole() {
    ArrayList<Role> roles = new ArrayList<>();
    roles.add(new ProductionAdminRole());
    roles.add(new ProductionRole());
    roles.add(new QualityAssuranceRole());
    return roles;
}
}

