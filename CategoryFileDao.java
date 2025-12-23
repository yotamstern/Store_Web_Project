package smartstore.inventorymanagment.dal;

import jakarta.annotation.PostConstruct;
import smartstore.inventorymanagment.entity.Category;
import smartstore.inventorymanagment.exceptions.*; // ייבוא החריגות המותאמות אישית
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryFileDao implements CategoryDao {
    private static final String FILE_NAME = "./categories.dat";
    private List<Category> categories = new ArrayList<>();

    @PostConstruct
    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            categories = (List<Category>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error loading categories: " + e.getMessage());
        }
    }

    // כל שיטה שכותבת מידע תכריז על זריקת חריגה
    private void saveToFile() throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(categories); // כתיבה באמצעות serialization
        } catch (IOException e) {
            throw new Exception("File system error: " + e.getMessage());
        }
    }

    @Override
    public void save(Category category) throws Exception {
        // בדיקה האם אובייקט קיים בתוך ה-ArrayList באמצעות contains
        // מניעת כפילות מזהה כבר בשלב השמירה
        if (categories.contains(category)) {
            throw new DuplicateEntityException("Cannot add category. ID " + category.getCategoryId() + " already exists.");
        }
        categories.add(category);
        saveToFile();
    }

    @Override
    public void update(Category category) throws Exception {
        int index = categories.indexOf(category);
        if (index == -1) {
            // אם המשימה לעדכון לא נמצאה, נזרוק חריגה מותאמת אישית
            throw new UpdateNotFoundException("Cannot update category with ID " + category.getCategoryId() + ". It is not found.");
        }
        categories.set(index, category);
        saveToFile();
    }

    @Override
    public void delete(String id) throws Exception {
        boolean removed = categories.removeIf(c -> c.getCategoryId().equals(id));
        if (!removed) {
            // אם המשימה למחיקה לא נמצאה, נזרוק חריגה מותאמת אישית
            throw new DeleteNotFoundException("Category with ID " + id + " was not deleted.");
        }
        saveToFile();
    }

    @Override
    public List<Category> getAll() {
        // החזרת רשימה (ב-Service נבצע את המיון הסופי לפני התצוגה)
        return new ArrayList<>(categories);
    }

    @Override
    public Category get(String id) throws Exception {
        for (Category c : categories) {
            if (c.getCategoryId().equals(id)) {
                return c; // החזרת אובייקט שנמצא
            }
        }
        return null; // אם לא נמצאה, תחזיר null
    }
}