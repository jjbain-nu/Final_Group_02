/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness.ProductManagement;

/**
 *
 * @author Nakka
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;

public class ExpensiveProductsReport {

    ArrayList<ProductPriceSummary> summarylist;

    public ExpensiveProductsReport(SupplierDirectory supplierdirectory) {
        summarylist = new ArrayList<>();
        for (Supplier supplier : supplierdirectory.getSuplierList()) {
            for (Product product : supplier.getProductCatalog().getProductList()) {
                summarylist.add(new ProductPriceSummary(
                        product.getName(), supplier.getName(), product.getPrice()));
            }
        }
        // most expensive first
        Collections.sort(summarylist, new Comparator<ProductPriceSummary>() {
            @Override
            public int compare(ProductPriceSummary a, ProductPriceSummary b) {
                return Integer.compare(b.getPrice(), a.getPrice());
            }
        });
    }
    public ArrayList<ProductPriceSummary> getSummaryList() { return summarylist; }
}