/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/
package Business;

import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;
import Business.Profiles.FacultyProfile;
import Business.Profiles.FacultyDirectory;

import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;

class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Information Systems");

        // Create Persons
        PersonDirectory persondirectory = business.getPersonDirectory();
        // person representing sales organization        
        Person person001 = persondirectory.newPerson("John Smith");
        person001.setEmail("john.smith@university.edu");
        person001.setPhoneNumber("617-555-0001");

        Person person002 = persondirectory.newPerson("Gina Montana");
        Person person003 = persondirectory.newPerson("Adam Rollen");
        person003.setEmail("adam.rollen@university.edu");
        person003.setPhoneNumber("617-555-0003");

        Person person004 = persondirectory.newPerson("Susan Carter");
        person004.setEmail("susan.carter@university.edu");
        person004.setPhoneNumber("617-555-0004");

        Person person005 = persondirectory.newPerson("Jim Dellon");
        Person person006 = persondirectory.newPerson("Anna Shnider");
        Person person007 = persondirectory.newPerson("Laura Brown");
        Person person008 = persondirectory.newPerson("Jack While");
        Person person009 = persondirectory.newPerson("Fidelity"); //we use this as customer

        // Create Admins to manage the business
        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
        EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(person001);
        employeeprofile0.setDepartment("Administration");
        employeeprofile0.setTitle("System Administrator");
        
        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile studentprofile0 = studentdirectory.newStudentProfile(person003);
        studentprofile0.setNuid("001234567");
        
        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile facultyprofile0 = facultydirectory.newFacultyProfile(person004);
        facultyprofile0.setDepartment("Information Systems");
        facultyprofile0.setTitle("Associate Professor");
        
        // Create User accounts that link to specific profiles
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        uadirectory.newUserAccount(employeeprofile0, "admin", "****");
        uadirectory.newUserAccount(studentprofile0, "adam", "****");
        uadirectory.newUserAccount(facultyprofile0, "susan", "****");
        return business;
    }
}
