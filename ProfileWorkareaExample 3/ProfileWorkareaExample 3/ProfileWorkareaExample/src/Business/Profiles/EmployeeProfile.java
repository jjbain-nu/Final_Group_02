/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Profiles;

import Business.Person.Person;

public class EmployeeProfile extends Profile {
    
    private String department;
    private String title;

    public EmployeeProfile(Person p) {
        super(p); 
    }
    @Override
    public String getRole(){
        return  "Admin";
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }        
}