package Business.WorkQueue;

import Business.Hospital.Medicine;

/**
 * A wholesaler's request to a manufacturer for additional medicine stock.
 * The same request is shared between the Wholesaler Inventory Organization and
 * the Manufacturer Production Organization.
 */
public class ManufacturerReplenishmentRequest extends WorkRequest {
    public static final String REQUESTED_FROM_MANUFACTURER = "Requested from Manufacturer";
    public static final String DELIVERED = "Delivered";
    public static final String RECEIVED = "Received";
    private Medicine medicine;
    private int quantity;
    private String requestingWholesaler;

    public ManufacturerReplenishmentRequest(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
        setStatus(REQUESTED_FROM_MANUFACTURER);
        setMessage("Manufacturer replenishment: " + medicine.getMedicineName());
    }

    public ManufacturerReplenishmentRequest(Medicine medicine, int quantity, String requestingWholesaler) {
        this(medicine, quantity);
        this.requestingWholesaler = requestingWholesaler;
        setMessage("Manufacturer replenishment for " + requestingWholesaler + ": " + medicine.getMedicineName());
    }

    public Medicine getMedicine() { return medicine; }
    public int getQuantity() { return quantity; }
    public String getRequestingWholesaler() { return requestingWholesaler; }

    @Override
    public String toString() { return medicine.getMedicineName() + " (" + quantity + ")"; }
}
