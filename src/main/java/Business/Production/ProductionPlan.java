/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Production;

import java.util.Date;

/**
 *
 * @author vyngo
 */
public class ProductionPlan {

    private String planId;
    private String productName;
    private int qty;
    private Date planDate;
    private String status;

    public ProductionPlan(String planId, String productName, int qty) {
        this.planId = planId;
        this.productName = productName;
        this.qty = qty;
        this.planDate = new Date();
        this.status = "Created";
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return planId + " - " + productName;
    }
}
