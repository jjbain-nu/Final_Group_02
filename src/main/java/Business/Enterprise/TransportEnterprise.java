/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

/**
 * A Transport Enterprise represents a logistics company in the pharma
 * supply chain. It hosts one or more Transport Organizations that
 * contain Transport Administrators and Drivers.
 */
public class TransportEnterprise extends Enterprise {

    public TransportEnterprise(String name) {
        super(name, EnterpriseType.Transport);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        return null;
    }
}