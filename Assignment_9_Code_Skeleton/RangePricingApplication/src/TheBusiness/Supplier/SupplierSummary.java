/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness.Supplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import TheBusiness.CustomerManagement.CustomerProfile;
/**
 *
 * @author Nakka
 */
public class SupplierSummary {

    private Supplier supplier;
    private long totalSales;
    private HashMap<CustomerProfile, Long> customerSpend;

    private double loyaltyScore;
    private double averageSpendingPerCustomer;
    private double top5SalesScore;

    public SupplierSummary(Supplier supplier) {
        this.supplier = supplier;
        this.totalSales = 0;
        this.customerSpend = new HashMap<>();
    }

    public void addSale(CustomerProfile customer, int amount) {
        totalSales += amount;
        Long current = customerSpend.get(customer);
        customerSpend.put(customer, current == null ? (long) amount : current + amount);
    }

    public void finalizeMetrics(int totalNumberOfCustomers) {
        int distinct = customerSpend.size();
        loyaltyScore = totalNumberOfCustomers > 0 ? (double) distinct / totalNumberOfCustomers : 0;
        averageSpendingPerCustomer = distinct > 0 ? (double) totalSales / distinct : 0;

        ArrayList<Long> spends = new ArrayList<>(customerSpend.values());
        Collections.sort(spends, Collections.reverseOrder());
        long top5 = 0;
        for (int i = 0; i < Math.min(5, spends.size()); i++) top5 += spends.get(i);
        top5SalesScore = totalSales > 0 ? (double) top5 / totalSales : 0;
    }

    public String getSupplierName() { 
        return supplier.getName(); 
    }
    
    public long getTotalSales() { 
        return totalSales; 
    }
    
    public int getNumberOfDistinctCustomers() { 
        return customerSpend.size(); 
    }
    
    public double getLoyaltyScore() { 
        return loyaltyScore; 
    }
    
    public double getAverageSpendingPerCustomer() { 
        return averageSpendingPerCustomer; 
    }
    
    public double getTop5SalesScore() { 
        return top5SalesScore; 
    }
}
