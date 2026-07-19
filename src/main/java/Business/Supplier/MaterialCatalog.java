/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

import java.util.ArrayList;

/**
 *
 * @author yu101
 */
public class MaterialCatalog {
    
    private ArrayList<Material>materialList;
    
    public MaterialCatalog(){
        this.materialList = new ArrayList<>();
        
    }

    public ArrayList<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(ArrayList<Material> materialList) {
        this.materialList = materialList;
    }
   
    public Material addMaterial(String id , String name,int weight){
        Material m = new Material(id,name,weight);
        materialList.add(m);
        return m;
        
    }
    
    
}
