/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.UserAccounts;

import Business.Profiles.Profile;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 *
 * @author kal bugrara
 */
public class UserAccount {
    
    Profile profile; //should fit in one of three roles
    String username;
    String password;
    private Date lastLogin;     // last time the user accessed the app
    private Date lastUpdated;   // last time this user account updated
    private static final SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public UserAccount (Profile profile, String un, String pw){
        username = un;
        password = pw;
        this.profile = profile;
        this.lastUpdated = new Date();

    }

    public String getPersonId(){
        return profile.getPerson().getPersonId();
    }
    public String getUserLoginName(){
        return username;
    }
    public void setUserLoginName(String username) {
        this.username = username;
        touch();   // any change refreshes the "last updated" stamp automatically
    }
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) {
        this.password = password;
        touch();
    }

    public boolean isMatch(String id){
        if(getPersonId().equals(id)) return true;
        return false;
    }
    public boolean isUsernameMatch(String un) {    // "Adam" should match "adam"
        return username.equalsIgnoreCase(un);
    }
        
    public boolean IsValidUser(String un, String pw){
        
        if (username.equalsIgnoreCase(un) && password.equals(pw)) return true;
        else return false;
    }
    public String getRole(){
        return profile.getRole();
    }
        
    public Profile getAssociatedPersonProfile(){
        return profile;
    }
    
    // ---- activity / audit tracking ----
    public void markLogin() { this.lastLogin = new Date(); }   // call on successful login
    public void touch()     { this.lastUpdated = new Date(); } // call on any edit

    public Date getLastLogin() { return lastLogin; }
    public Date getLastUpdated() { return lastUpdated; }

    // Ternary operator: if null show "Never"/"-", otherwise the formatted date.
    public String getLastLoginText()   { return (lastLogin   == null) ? "Never" : FMT.format(lastLogin); }
    public String getLastUpdatedText() { return (lastUpdated == null) ? "-"     : FMT.format(lastUpdated); }

    public String getStatus() { return (lastLogin == null) ? "Never logged in" : "Active"; }
        
    @Override
    public String toString(){  
        return getUserLoginName();
    }
        
}

