/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheBusiness.CustomerManagement;

/**
 *
 * @author Anish
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomersReport {
    ArrayList<CustomerSummary> customerlist;

    public CustomersReport() { customerlist = new ArrayList(); }

    public void addCustomerSummary(CustomerSummary cs) { customerlist.add(cs); }

    public ArrayList<CustomerSummary> getCustomerSummaryList() { return customerlist; }

    // Sorted from highest total sales to lowest.
    public ArrayList<CustomerSummary> getSortedByTotalSales() {
        ArrayList<CustomerSummary> sorted = new ArrayList<>(customerlist);
        Collections.sort(sorted, new Comparator<CustomerSummary>() {
            @Override
            public int compare(CustomerSummary a, CustomerSummary b) {
                return Integer.compare(b.getTotalSales(), a.getTotalSales());
            }
        });
        return sorted;
    }
}
