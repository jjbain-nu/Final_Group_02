/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Profiles;

import Business.Person.Person;

/**
 *
 * @author kal bugrara
 */
public class StudentProfile extends Profile {

    Person person; //check later
    private String nuid;
//    Transcript transcript;
    //   EmploymentHistroy employmenthistory;

    public StudentProfile(Person p) {
        super(p);

//        transcript = new Transcript(this);
//        employmenthistory = new EmploymentHistroy();
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public String getNuid() {
        return nuid;
    }

    public void setNuid(String nuid) {
        this.nuid = nuid;
    }
    
    public boolean isNuidMatch(String n) {
        if (nuid == null) {
            return false;
        }
        return nuid.equals(n);
    }

    public boolean isMatch(String id) {
        return person.getPersonId().equals(id);
    }

}
