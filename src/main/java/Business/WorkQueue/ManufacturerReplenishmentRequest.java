package Business.WorkQueue;

import Business.Hospital.Medicine;

/**
 * A wholesaler's request to a manufacturer for additional medicine stock.
 * TODO: Route this request to a Manufacturer Organization when that enterprise
 * workflow is implemented.
 */
public class ManufacturerReplenishmentRequest extends WorkRequest {
    public static final String REQUESTED_FROM_MANUFACTURER = "Requested from Manufacturer";
    private Medicine medicine;
    private int quantity;

    public ManufacturerReplenishmentRequest(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
        setStatus(REQUESTED_FROM_MANUFACTURER);
        setMessage("Manufacturer replenishment: " + medicine.getMedicineName());
    }

    public Medicine getMedicine() { return medicine; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() { return medicine.getMedicineName() + " (" + quantity + ")"; }
}
