package dao;

import beans.Company;
import beans.Customer;

import java.sql.SQLException;

public interface CustomersDAO extends  DAO<Customer, Integer>{
    boolean isCustomerExists(String email,String password) throws SQLException;
    boolean isCustomerExistsByName(String name) throws SQLException;
    boolean isCustomerExistsByEmail(String email) throws SQLException;
    boolean isCustomerExistsById(int id) throws SQLException;

    int customerCounter() throws SQLException;
    int getIdByEmail(String email) throws SQLException;

    int getIdByEmailPassword(String email, String password) throws SQLException;
}
