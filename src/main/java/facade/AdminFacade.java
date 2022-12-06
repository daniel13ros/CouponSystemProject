package facade;

import beans.Company;
import beans.Customer;
import exceptions.CouponSystemExceptions;

import java.sql.SQLException;
import java.util.List;

public interface AdminFacade {
    boolean login(String email,String password) throws CouponSystemExceptions;
    void addCustomer(Customer customer) throws SQLException, CouponSystemExceptions;
    void updateCustomer(Integer customerId,Customer customer) throws SQLException, CouponSystemExceptions;
    void deleteCustomer(int id) throws SQLException, CouponSystemExceptions;
    List<Customer>getAllCustomers() throws SQLException;
    Customer getSingleCustomer(int id) throws SQLException;

    void addCompany(Company company) throws SQLException, CouponSystemExceptions;
    void updateCompany(Integer companyId,Company company) throws SQLException, CouponSystemExceptions;
    void deleteCompany(int id) throws SQLException, CouponSystemExceptions;
    List<Company>getAllCompanies() throws SQLException;
    Company getSingleCompany(int id) throws SQLException;
}
