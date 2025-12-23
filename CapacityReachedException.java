package smartstore.inventorymanagment.exceptions;

public class CapacityReachedException extends RuntimeException {
    public CapacityReachedException(String message) {
        super(message); // Cannot save more than 100 items
    }
}