/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

import Business.WorkQueue.WorkRequest;

/**
 *
 * @author yu101
 */
public class ShippingOrder extends WorkRequest {
    
    private static int count = 1;
    private String shippingOrderId;
    private PickingOrder pickingOrder;
    
    public ShippingOrder(){
        shippingOrderId = "SO" + count++;
        
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        ShippingOrder.count = count;
    }

    public String getShippingOrderId() {
        return shippingOrderId;
    }

    public void setShippingOrderId(String shippingOrderId) {
        this.shippingOrderId = shippingOrderId;
    }

    public PickingOrder getPickingOrder() {
        return pickingOrder;
    }

    public void setPickingOrder(PickingOrder pickingOrder) {
        this.pickingOrder = pickingOrder;
    }
    
    @Override
    public String toString(){
        return shippingOrderId;
    }
    
}
