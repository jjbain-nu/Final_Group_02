/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Hospital;

import Business.Supplier.Material;
import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class MedicineCatalog {
    
     private ArrayList<Medicine>medicineList;
    
     public MedicineCatalog(){
        this.medicineList = new ArrayList<>();
        
    }

    public ArrayList<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(ArrayList<Medicine> medicineList) {
        this.medicineList = medicineList;
    }
     
    public Medicine addMedicine(String id , String name,int price){
    Medicine m = new Medicine(id,name,price);
    medicineList.add(m);
    return m;
        
    }
    
}


