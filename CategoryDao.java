package smartstore.inventorymanagment.dal;

import smartstore.inventorymanagment.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll() throws Exception;

    void save(Category category) throws Exception;

    void update(Category category) throws Exception;

    void delete(String id) throws Exception;

    Category get(String id) throws Exception;
}
