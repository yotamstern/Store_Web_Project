package smartstore.inventorymanagment.dal;

import jakarta.annotation.PostConstruct;
import smartstore.inventorymanagment.entity.Product;
import smartstore.inventorymanagment.exceptions.*;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository // הגדרת המחלקה כ-Spring Bean
public class ProductFileDao implements ProductDao {
    // הגדרת נתיב הקובץ בתיקיית הפרויקט
    private static final String FILE_NAME = "./products.dat";
    private List<Product> products = new ArrayList<>(); // שימוש ב-ArrayList לאחסון

    @PostConstruct // טעינה אוטומטית בעליית הקונטיינר
    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            products = (List<Product>) ois.readObject(); // טעינה באמצעות serialization
        } catch (Exception e) {
            System.out.println("No products file found. Starting fresh.");
        }
    }

    // שיטה פנימית לשמירת הרשימה כולה לקובץ
    private void saveToFile() throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(products);
        } catch (IOException e) {
            throw new Exception("File system error while saving products: " + e.getMessage()); // [cite: 41, 42]
        }
    }

    @Override
    public void save(Product product) throws Exception {
        // בדיקה האם המוצר כבר קיים במערכת
        if (products.contains(product)) {
            throw new DuplicateEntityException("Cannot add product. ID " + product.getProductId() + " already exists.");
        }
        products.add(product); // הוספת ישות
        saveToFile();
    }

    @Override
    public void update(Product product) throws Exception {
        int index = products.indexOf(product); // חיפוש לפי equals שמומש בישות
        if (index == -1) {
            // זריקת חריגה מותאמת אישית אם לא נמצא
            throw new UpdateNotFoundException("Cannot update product with ID " + product.getProductId() + ". It is not found.");
        }
        products.set(index, product); // עריכת ישות
        saveToFile();
    }

    @Override
    public void delete(String id) throws Exception {
        // מחיקת ישות מהמאגר
        boolean removed = products.removeIf(p -> p.getProductId().equals(id));
        if (!removed) {
            // זריקת חריגה מותאמת אישית אם המחיקה נכשלה
            throw new DeleteNotFoundException("Product with ID " + id + " was not deleted.");
        }
        saveToFile();
    }

    @Override
    public List<Product> getAll() {
        // החזרת רשימה של כל הישויות
        return new ArrayList<>(products);
    }

    @Override
    public Product get(String id) throws Exception {
        // טעינת ישות בודדת לפי מזהה
        for (Product p : products) {
            if (p.getProductId().equals(id)) {
                return p; // החזרת האובייקט אם נמצא
            }
        }
        return null; // החזרה של null אם לא נמצא
    }
}