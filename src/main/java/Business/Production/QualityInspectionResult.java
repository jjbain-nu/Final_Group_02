/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Production;

import Business.WorkQueue.WorkRequest;
import java.util.Date;

/**
 *
 * @author vyngo
 */
public class QualityInspectionResult extends WorkRequest {

    private String productName;
    private boolean passed;

    public QualityInspectionResult(String productName, boolean passed) {
        this.productName = productName;
        this.passed = passed;
        setStatus("Sent");
        setRequestDate(new Date());
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

    @Override
    public String toString() {
        return productName + " - " + (passed ? "Passed" : "Failed");
    }
}




