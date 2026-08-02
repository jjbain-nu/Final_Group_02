/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;

import Business.Fleet.FleetDirectory;
import Business.Role.DriverRole;
import Business.Role.Role;
import Business.Role.TransportAdminRole;
import java.util.ArrayList;

/**
 * TransportOrganization is the operating unit inside a TransportEnterprise.
 * It supports two roles: TransportAdminRole (dispatch/scheduling) and
 * DriverRole (execution: loading, delivery, unloading, proof-of-delivery).
 *
 * It also owns a FleetDirectory that tracks the trucks the Transport Admin
 * can assign to a delivery.
 */
public class TransportOrganization extends Organization {

    private FleetDirectory fleetDirectory;

    public TransportOrganization() {
        super(Organization.Type.Transport.getValue());
        fleetDirectory = new FleetDirectory();
    }


    public FleetDirectory getFleetDirectory() {
        return fleetDirectory;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new TransportAdminRole());
        roles.add(new DriverRole());
        return roles;
    }
}
