package dao;

import beans.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    void add(Category category) throws SQLException;
    List<Category> getAll() throws SQLException;
}
