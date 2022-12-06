package dao;

import beans.Customer;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDAOImpl implements CustomersDAO, DAO<Customer, Integer>{


    private static final String INSERT_CUSTOMER = "INSERT INTO `java-151-cs1`.`customers` (`first_name`, `last_name`, `email`,`password`) VALUES (?, ?, ?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `java-151-cs1`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String DELETE_CUSTOMER = "DELETE FROM `java-151-cs1`.`customers` WHERE (`id` = ?);";
    private static final String ALL_CUSTOMER = "SELECT * FROM `java-151-cs1`.customers";
    private static final String SINGLE_CUSTOMER = "SELECT * FROM `java-151-cs1`.customers WHERE (`id` = ?)";
    private static final String ID_BY_EMAIL = "SELECT id FROM `java-151-cs1`.customers WHERE (`email` = ?)";
    private static final String ID_BY_EMAIL_PASSWORD = "SELECT id FROM `java-151-cs1`.customers WHERE (`email` = ?) and (`password` = ?)";
    private static final String IS_EXIST_CUSTOMER = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.customers WHERE `email`=? AND `password`=?) as res";
    private static final String IS_EXIST_CUSTOMER_BY_ID = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.companies WHERE `id`=?) as res";
    private static final String IS_EXIST_CUSTOMER_BY_NAME = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.companies WHERE `name`=? ) as res";
    private static final String IS_EXIST_CUSTOMER_BY_EMAIL = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.companies WHERE `email`=? ) as res";
    private static final String CUSTOMERS_COUNTER = "SELECT COUNT(id) AS NumberOfCustomers FROM `java-151-cs1`.customers";


    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2,password);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_CUSTOMER, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public boolean isCustomerExistsByName(String name) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_CUSTOMER_BY_NAME, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public boolean isCustomerExistsByEmail(String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_CUSTOMER_BY_EMAIL, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public boolean isCustomerExistsById(int id) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_CUSTOMER_BY_ID, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public int getIdByEmail(String email) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        return (int) ((Map<String, Object>)JDBCUtils.runQueryWithResult(ID_BY_EMAIL, params).get(0)).get("id");
    }

    @Override
    public int getIdByEmailPassword(String email, String password) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        return (int) ((Map<String, Object>)JDBCUtils.runQueryWithResult(ID_BY_EMAIL_PASSWORD, params).get(0)).get("id");
    }

    @Override
    public int customerCounter() throws SQLException {
        return (int) ((Map<String, Object>) JDBCUtils.runQueryWithResult(CUSTOMERS_COUNTER).get(0)).get("NumberOfCustomers").hashCode();
    }
    @Override
    public void add(Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        JDBCUtils.runQuery(String.valueOf(INSERT_CUSTOMER), params);
    }
    @Override
    public void update(Integer integer, Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, integer);
        JDBCUtils.runQuery(UPDATE_CUSTOMER, params);
    }
    @Override
    public void delete(Integer id) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        JDBCUtils.runQuery(DELETE_CUSTOMER, params);
    }
    @Override
    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(ALL_CUSTOMER);
        for (Object obj : list) {
            Customer customer = ConvertUtils.objectToCustomer((Map<String, Object>) obj);
            customers.add(customer);
        }
        return customers;
    }
    @Override
    public Customer getSingle(Integer customerID) throws SQLException {
        Customer customer = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        List<?> list = JDBCUtils.runQueryWithResult(SINGLE_CUSTOMER, params);
        customer = ConvertUtils.objectToCustomer((Map<String, Object>) list.get(0));
        return customer;
    }
}
