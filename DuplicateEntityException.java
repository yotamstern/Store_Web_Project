package smartstore.inventorymanagment.exceptions;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message); // Cannot add new item with name X
    }
}