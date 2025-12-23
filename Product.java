package smartstore.inventorymanagment.entity;


import java.io.Serializable;

public class Product implements Serializable, Comparable<Product> {
    private static final long serialVersionUID = 1L;

    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String categoryId; // הקישור לקטגוריה (יחס 1:N)

    public Product(String productId, String name, double price, int quantity, String categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public String getProductId() { return productId; }
    public String getCategoryId() { return categoryId; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return String.format("Product [ID=%s, Name=%-10s, Price=%.2f, Qty=%d, CatID=%s]",
                productId, name, price, quantity, categoryId);
    }

    @Override
    public int compareTo(Product other) { // מיון לפי כמות במלאי [cite: 21, 31, 56]
        return Integer.compare(this.quantity, other.quantity);
    }
}