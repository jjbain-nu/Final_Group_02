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
public class QualityInspectionResult extends WorkRequest {

    private String productName;
    private boolean passed;
    private String planId;
    private ManufacturerReplenishmentRequest sourceReplenishment;

    public QualityInspectionResult(String productName, boolean passed) {
        this.productName = productName;
        this.passed = passed;
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

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public ManufacturerReplenishmentRequest getSourceReplenishment() {
        return sourceReplenishment;
    }

    public void setSourceReplenishment(ManufacturerReplenishmentRequest sourceReplenishment) {
        this.sourceReplenishment = sourceReplenishment;
    }

    @Override
    public String toString() {
        return productName + " - " + (passed ? "Passed" : "Failed");
    }
}




