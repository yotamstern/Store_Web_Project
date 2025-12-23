package smartstore.inventorymanagment.entity;


import java.io.Serializable;
import java.util.Objects;


public class Category implements Serializable, Comparable<Category> {
    private static final long serialVersionUID = 1L;

    private String categoryId;
    private String name;

    public Category(String categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getCategoryId() { return categoryId; }
    public String getName() { return name; }

    @Override
    public String toString() { // מימוש להצגה בממשק
        return "Category [ID=" + categoryId + ", Name=" + name + "]";
    }

    @Override
    public int compareTo(Category other) { // מיון לפי שם
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) { // השוואה לפי מזהה
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId);
    }
}