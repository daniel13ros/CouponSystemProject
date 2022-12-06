package dao;

import beans.Category;
import beans.Company;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAOImpl implements CategoryDAO{

    private static final String INSERT_CATEGORY = "INSERT INTO `java-151-cs1`.`categories` (`name`) VALUES (?)";
    private static final String ALL_CATEGORIES = "SELECT * FROM `java-151-cs1`.categories";

    @Override
    public void add(Category category) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, category.name());
        JDBCUtils.runQuery(INSERT_CATEGORY, params);
    }

    @Override
    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(ALL_CATEGORIES);
        for (Object obj : list) {
            Category category = ConvertUtils.objectToCategory((Map<String, Object>) obj);
            categories.add(category);
        }
        return categories;
    }
}
