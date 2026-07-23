package Business.WorkQueue;

/**
 * Internal wholesaler fulfillment request. External manufacturer, transporter,
 * and other-wholesaler integration is intentionally outside this request's scope.
 */
public class WholesaleWorkRequest extends WorkRequest {

    public enum RequestType {
        REPLENISHMENT("Replenishment"),
        TRANSFER("Transfer");

        private final String label;

        RequestType(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static final String REQUESTED = "Requested";
    public static final String RECEIVED_BY_STAFF = "Received by Shipping Staff";
    public static final String PICKING = "Picking";
    public static final String PICKED = "Picked";
    public static final String PACKED = "Packed";
    public static final String SHIPPED = "Shipped";
    public static final String RECEIVED_INTO_INVENTORY = "Received into Inventory";

    private RequestType requestType;
    private String itemDescription;
    private int quantity;

    public WholesaleWorkRequest(RequestType requestType, String itemDescription, int quantity) {
        this.requestType = requestType;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        setStatus(REQUESTED);
        setMessage(requestType + ": " + itemDescription);
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return requestType + " - " + itemDescription + " (" + quantity + ") [" + getStatus() + "]";
    }
}
