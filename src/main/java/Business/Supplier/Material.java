/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

/**
 *
 * @author yu101
 */
public class Material {
    
    private String materialId;
    private String materialName;
    private int materialPrice;
    private int materialWeight;
    
    public Material(String materialId,String materialName,int materialWeight){
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialWeight = materialWeight;
        
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(int materialPrice) {
        this.materialPrice = materialPrice;
    }

    public int getMaterialWeight() {
        return materialWeight;
    }

    public void setMaterialWeight(int materialWeight) {
        this.materialWeight = materialWeight;
    }
    
    @Override
    public String toString(){
        return materialName;
    }
    
}
