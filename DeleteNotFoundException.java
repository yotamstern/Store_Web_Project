package smartstore.inventorymanagment.exceptions;

public class DeleteNotFoundException extends RuntimeException {
    public DeleteNotFoundException(String message) {
        super(message); //  Item with ID X was not deleted
    }
}