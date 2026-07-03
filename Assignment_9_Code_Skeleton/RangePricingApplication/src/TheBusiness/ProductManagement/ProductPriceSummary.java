/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness.ProductManagement;

/**
 *
 * @author Nakka
 */

public class ProductPriceSummary {
    private String productName;
    private String supplierName;
    private int price;

    public ProductPriceSummary(String productName, String supplierName, int price) {
        this.productName = productName;
        this.supplierName = supplierName;
        this.price = price;
    }
    public String getProductName() { 
        return productName; 
    }
    public String getSupplierName() { 
        return supplierName; 
    }
    public int getPrice() { 
        return price; 
    }
}
