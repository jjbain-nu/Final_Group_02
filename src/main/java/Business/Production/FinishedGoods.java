/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Production;

import Business.WorkQueue.ManufacturerReplenishmentRequest;
import Business.WorkQueue.WorkRequest;
import java.util.Date;

/**
 *
 * @author vyngo
 */
public class FinishedGoods extends WorkRequest {

    private String productName;
    private int qty;
    private String planId;
    private ManufacturerReplenishmentRequest sourceReplenishment;

    public FinishedGoods(String productName, int qty) {
        this.productName = productName;
        this.qty = qty;
        setStatus("Sent");
        setRequestDate(new Date());
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

    public ManufacturerReplenishmentRequest getSourceReplenishment() {
        return sourceReplenishment;
    }

    public void setSourceReplenishment(ManufacturerReplenishmentRequest sourceReplenishment) {
        this.sourceReplenishment = sourceReplenishment;
    }

    @Override
    public String toString() {
        return productName;
    }
}