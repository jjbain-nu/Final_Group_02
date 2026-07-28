/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Employee;

import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class EmployeeDirectory {
    
    private ArrayList<Employee> employeeList;

    public EmployeeDirectory() {
        employeeList = new ArrayList();
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }
    
    public Employee createEmployee(String name){
        
        int id = generatedId();
        
        Employee employee = new Employee();
        employee.setName(name);
        employee.setId(id);
        employeeList.add(employee);
        return employee;
    }
    
    public int generatedId(){
        
        int id = 1;
        
        while(true){
            
            boolean exist = false;
            
            for (Employee employee :employeeList ){
                if(employee.getId()==id){
                    exist =true;
                    break;
                    }
            }
            
            if(!exist){
                return id;
                        
                }
            id++;
            
            
        }
        
    }
}