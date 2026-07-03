/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness.Supplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.ProductManagement.Product;
/**
 *
 * @author Nakka
 */


public class SupplierReport {

    ArrayList<SupplierSummary> summarylist;

    public SupplierReport(SupplierDirectory supplierdirectory, CustomerDirectory customerdirectory) {
        summarylist = new ArrayList<>();

        // 1) product -> supplier lookup, and one summary per supplier
        HashMap<Product, Supplier> productToSupplier = new HashMap<>();
        HashMap<Supplier, SupplierSummary> supplierToSummary = new HashMap<>();
        for (Supplier supplier : supplierdirectory.getSuplierList()) {
            SupplierSummary summary = new SupplierSummary(supplier);
            supplierToSummary.put(supplier, summary);
            summarylist.add(summary);
            for (Product product : supplier.getProductCatalog().getProductList()) {
                productToSupplier.put(product, supplier);
            }
        }

        int totalCustomers = customerdirectory.getCustomerList().size();

        // 2) attribute every sale to its supplier
        for (CustomerProfile customer : customerdirectory.getCustomerList()) {
            for (Order order : customer.getOrders()) {
                for (OrderItem item : order.getOrderItems()) {
                    Supplier supplier = productToSupplier.get(item.getSelectedProduct());
                    if (supplier == null) continue;
                    supplierToSummary.get(supplier).addSale(customer, item.getOrderItemTotal());
                }
            }
        }

        // 3) compute the derived scores
        for (SupplierSummary summary : summarylist) summary.finalizeMetrics(totalCustomers);

        // best suppliers first
        Collections.sort(summarylist, new Comparator<SupplierSummary>() {
            @Override
            public int compare(SupplierSummary a, SupplierSummary b) {
                return Long.compare(b.getTotalSales(), a.getTotalSales());
            }
        });
    }
    public ArrayList<SupplierSummary> getSummaryList() { return summarylist; }
}
