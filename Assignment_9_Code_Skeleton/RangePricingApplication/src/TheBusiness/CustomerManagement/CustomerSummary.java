/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheBusiness.CustomerManagement;

import TheBusiness.OrderManagement.Order;

/**
 *
 * @author Anish
 */

public class CustomerSummary {
    CustomerProfile customer;
    int ordertotal;

    public CustomerSummary(CustomerProfile cp) {
        customer = cp;
        ordertotal = cp.getTotalSales(); // total sales to this customer
    }
    public CustomerProfile getCustomer() { return customer; }
    public String getCustomerName() { return customer.getCustomerName(); }
    public int getTotalSales() { return ordertotal; }
}
