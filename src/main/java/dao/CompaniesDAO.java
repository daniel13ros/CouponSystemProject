package dao;

import beans.Company;
import beans.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO  extends DAO<Company, Integer>{

    boolean isCompanyExists(String email,String password) throws SQLException;
    boolean isCompanyExistsByName(String name) throws SQLException;
    boolean isCompanyExistsByEmail(String email) throws SQLException;
    void updateCompanyEmailPassword(Integer id, Company company) throws SQLException;

    boolean isCompanyExistById(int id) throws SQLException;

    int getIdByEmail(String email) throws SQLException;
    int companyCounter() throws SQLException;


    int getIdByEmailPassword(String email, String password) throws SQLException;
}
