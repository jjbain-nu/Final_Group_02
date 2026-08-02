/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Production;

import Business.Hospital.Medicine;
import java.util.ArrayList;

/**
 *
 * @author vyngo
 */
public class ProductionPlanDirectory {

    private ArrayList<ProductionPlan> productionPlanList;

    public ProductionPlanDirectory() {
        this.productionPlanList = new ArrayList<>();
    }

    public String generatePlanId() {
        return String.format("PP-%04d", productionPlanList.size() + 1);
    }

    public ArrayList<ProductionPlan> getProductionPlanList() {
        return productionPlanList;
    }

    public void setProductionPlanList(ArrayList<ProductionPlan> productionPlanList) {
        this.productionPlanList = productionPlanList;
    }

    public ProductionPlan addProductionPlan(String productName, int qty) {
        String id = generatePlanId();
        ProductionPlan plan = new ProductionPlan(id, productName, qty);
        this.productionPlanList.add(plan);
        return plan;
    }

    /**
     * Preferred overload: creates the plan from a real Medicine in the
     * shared MedicineCatalog instead of a free-typed product name.
     */
    public ProductionPlan addProductionPlan(Medicine medicine, int qty) {
        String id = generatePlanId();
        ProductionPlan plan = new ProductionPlan(id, medicine, qty);
        this.productionPlanList.add(plan);
        return plan;
    }
}
