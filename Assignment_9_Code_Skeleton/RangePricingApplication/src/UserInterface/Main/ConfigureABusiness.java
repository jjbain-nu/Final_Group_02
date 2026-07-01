/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Main;

import java.util.ArrayList;
import java.util.Random;
import MarketingManagement.MarketingPersonDirectory;
import MarketingManagement.MarketingPersonProfile;
import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.PersonDirectory;
import TheBusiness.ProductManagement.Product;
import TheBusiness.ProductManagement.ProductCatalog;
import TheBusiness.SalesManagement.SalesPersonDirectory;
import TheBusiness.SalesManagement.SalesPersonProfile;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import TheBusiness.UserAccountManagement.UserAccountDirectory;

class ConfigureABusiness {

    private static final int NUMBER_OF_SUPPLIERS = 50;
    private static final int SUPPLIERS_WITH_PRODUCTS = 30;
    private static final int PRODUCTS_PER_SUPPLIER = 50;
    private static final int NUMBER_OF_CUSTOMERS = 300;
    private static final int MIN_ORDERS_PER_CUSTOMER = 1;
    private static final int MAX_ORDERS_PER_CUSTOMER = 3;
    private static final int MAX_ITEMS_PER_ORDER = 10;

    private static final String[] PRODUCT_TYPES = {
        "Scanner", "Printer", "Photocopier", "Monitor", "Toner Kit",
        "Projector", "Laptop", "Tablet", "Camera", "Server", "Router", "Headset"
    };

    static Business initialize() {

        Business business = new Business("Xerox");
        Random rand = new Random(42); // fixed seed -> reproducible data

        // ---- People who run the business + their login accounts ----
        PersonDirectory persondirectory = business.getPersonDirectory();
        Person salesOrgPerson = persondirectory.newPerson("Xerox sales");
        Person marketingOrgPerson = persondirectory.newPerson("Xerox marketing");

        SalesPersonDirectory salespersondirectory = business.getSalesPersonDirectory();
        SalesPersonProfile salespersonprofile = salespersondirectory.newSalesPersonProfile(salesOrgPerson);

        MarketingPersonDirectory marketingpersondirectory = business.getMarketingPersonDirectory();
        MarketingPersonProfile marketingpersonprofile = marketingpersondirectory.newMarketingPersonProfile(marketingOrgPerson);

        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        uadirectory.newUserAccount(salespersonprofile, "Sales", "XXXX");   // login: Sales / XXXX
        uadirectory.newUserAccount(marketingpersonprofile, "Marketing", "XXXX");

        // ---- 50 Suppliers ----
        SupplierDirectory supplierdirectory = business.getSupplierDirectory();
        ArrayList<Supplier> suppliers = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_SUPPLIERS; i++) {
            suppliers.add(supplierdirectory.newSupplier("Supplier-" + i));
        }

        // ---- 30 suppliers get 50 products each ----
        ArrayList<Product> sellableProducts = new ArrayList<>();
        for (int s = 0; s < SUPPLIERS_WITH_PRODUCTS; s++) {
            Supplier supplier = suppliers.get(s);
            ProductCatalog catalog = supplier.getProductCatalog();
            for (int p = 1; p <= PRODUCTS_PER_SUPPLIER; p++) {
                int floor = 500 + rand.nextInt(20000);
                int target = floor + 1000 + rand.nextInt(20000);   // > floor
                int ceiling = target + 1000 + rand.nextInt(30000); // > target
                String name = PRODUCT_TYPES[rand.nextInt(PRODUCT_TYPES.length)]
                        + " " + (1000 + rand.nextInt(9000)) + " [" + supplier.getName() + "]";
                Product product = catalog.newProduct(name, floor, ceiling, target);
                sellableProducts.add(product);
            }
        }

        // ---- 300 Customers ----
        CustomerDirectory customerdirectory = business.getCustomerDirectory();
        ArrayList<CustomerProfile> customers = new ArrayList<>();
        for (int c = 1; c <= NUMBER_OF_CUSTOMERS; c++) {
            Person customerPerson = persondirectory.newPerson("Customer-" + c);
            customers.add(customerdirectory.newCustomerProfile(customerPerson));
        }

        // ---- Orders: 1-3 per customer, up to 10 items each ----
        MasterOrderList masterorderlist = business.getMasterOrderList();
        for (CustomerProfile customer : customers) {
            int numberOfOrders = MIN_ORDERS_PER_CUSTOMER
                    + rand.nextInt(MAX_ORDERS_PER_CUSTOMER - MIN_ORDERS_PER_CUSTOMER + 1); // 1..3
            for (int o = 0; o < numberOfOrders; o++) {
                Order order = masterorderlist.newOrder(customer, salespersonprofile);
                int numberOfItems = 1 + rand.nextInt(MAX_ITEMS_PER_ORDER); // 1..10
                for (int it = 0; it < numberOfItems; it++) {
                    Product product = sellableProducts.get(rand.nextInt(sellableProducts.size()));
                    int spread = Math.max(1, product.getCeilingPrice() - product.getFloorPrice());
                    int actualPrice = product.getFloorPrice() + rand.nextInt(spread);
                    int quantity = 1 + rand.nextInt(10); // 1..10
                    order.newOrderItem(product, actualPrice, quantity);
                }
                order.Submit();
            }
        }
        return business;
    }
}
