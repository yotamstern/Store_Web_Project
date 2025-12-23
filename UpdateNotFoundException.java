package smartstore.inventorymanagment.exceptions;

public class UpdateNotFoundException extends RuntimeException {
    public UpdateNotFoundException(String message) {
        super(message); // Cannot update item with ID X. It is not found
    }
}