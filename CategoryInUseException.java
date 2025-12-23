package smartstore.inventorymanagment.exceptions;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(String message) {
        super(message); // Cannot delete category. Products are still linked to it
    }
}