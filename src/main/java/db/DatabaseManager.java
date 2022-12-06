package db;

import beans.Category;
import dao.CategoryDAOImpl;

import java.sql.SQLException;


public class DatabaseManager {

    private static CategoryDAOImpl categoryDAO=new CategoryDAOImpl();
    private static final String CREATE_SCHEMA = "create schema `java-151-cs1`";
    private static final String DROP_SCHEMA = "drop schema `java-151-cs1`";
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `java-151-cs1`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";
    public static final String CREATE_TABLE_CUSTOMERS="CREATE TABLE `java-151-cs1`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";
    public static final String CREATE_TABLE_CATEGORIES="CREATE TABLE `java-151-cs1`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";
    public static final String CREATE_TABLE_COUPONS="CREATE TABLE `java-151-cs1`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `java-151-cs1`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `java-151-cs1`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";
    public static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS="CREATE TABLE `java-151-cs1`.`customers_vs_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `java-151-cs1`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `java-151-cs1`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

    public static void dropAndCreateStrategy() throws SQLException {
        JDBCUtils.runQuery(DROP_SCHEMA);
        JDBCUtils.runQuery(CREATE_SCHEMA);
        JDBCUtils.runQuery(CREATE_TABLE_COMPANIES);
        JDBCUtils.runQuery(CREATE_TABLE_CUSTOMERS);
        JDBCUtils.runQuery(CREATE_TABLE_CATEGORIES);
        JDBCUtils.runQuery(CREATE_TABLE_COUPONS);
        JDBCUtils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
        for (Category category:Category.values()) {
            categoryDAO.add(Category.valueOf(category.name()));
        }
    }
}
