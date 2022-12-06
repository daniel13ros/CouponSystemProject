package dao;

import beans.Company;
import beans.Coupon;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDAOImpl implements CompaniesDAO {
    private static final String INSERT_COMPANY = "INSERT INTO `java-151-cs1`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?)";
    private static final String UPDATE_COMPANY = "UPDATE `java-151-cs1`.`companies` SET `name` = ?, `email` = ? WHERE (`id` = ?)";
    private static final String UPDATE_COMPANY_EMAIL_PASSWORD = "UPDATE `java-151-cs1`.`companies` SET  `email`=?, `password` =? WHERE (`id` = ?)";
    private static final String DELETE_COMPANY = "DELETE FROM `java-151-cs1`.`companies` WHERE (`id` = ?)";
    private static final String ALL_COMPANIES = "SELECT * FROM `java-151-cs1`.companies";
    private static final String SINGLE_COMPANY = "SELECT * FROM `java-151-cs1`.companies as res WHERE (`id` = ?)";
    private static final String IS_EXIST_COMPANY = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.companies WHERE `email`=? AND `password`=?) as res";
    private static final String IS_EXIST_COMPANY_BY_NAME = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.companies WHERE `name`=? ) as res";
    private static final String IS_EXIST_COMPANY_BY_EMAIL = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.companies WHERE `email`=? ) as res";
    private static final String IS_EXIST_COMPANY_BY_ID = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.companies WHERE `id`=? ) as res";
    private static final String ID_BY_EMAIL = "SELECT id FROM `java-151-cs1`.companies WHERE (`email` = ?)";
    private static final String ID_BY_EMAIL_PASSWORD = "SELECT id FROM `java-151-cs1`.companies WHERE (`email` = ?) and (`password` = ?)";
    private static final String COMPANIES_COUNTER = "SELECT COUNT(id) AS NumberOfCompanies FROM `java-151-cs1`.companies";
    private static final String ALL_COUPONS_BY_COMPANY = "SELECT * FROM `java-151-cs1`.coupons as res WHERE `company_id`=?";

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2,password);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_COMPANY, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public boolean isCompanyExistsByName(String name) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_COMPANY_BY_NAME, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public boolean isCompanyExistsByEmail(String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_COMPANY_BY_EMAIL, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public void updateCompanyEmailPassword(Integer id, Company company) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getEmail());
        params.put(2, company.getPassword());
        params.put(3, id);
        JDBCUtils.runQuery(UPDATE_COMPANY_EMAIL_PASSWORD, params);
    }
    @Override
    public boolean isCompanyExistById(int id) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_COMPANY_BY_ID, params);
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
    public int companyCounter() throws SQLException {
        return (int) ((Map<String, Object>) JDBCUtils.runQueryWithResult(COMPANIES_COUNTER).get(0)).get("NumberOfCompanies").hashCode();
    }

    @Override
    public int getIdByEmailPassword(String email, String password) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        return (int) ((Map<String, Object>)JDBCUtils.runQueryWithResult(ID_BY_EMAIL_PASSWORD, params).get(0)).get("id");
    }

    @Override
    public void add(Company company) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        JDBCUtils.runQuery(INSERT_COMPANY, params);
    }
    @Override
    public void update(Integer integer, Company company) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, integer);
        JDBCUtils.runQuery(UPDATE_COMPANY, params);
    }
    @Override
    public void delete(Integer companyId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        JDBCUtils.runQuery(DELETE_COMPANY, params);
    }
    @Override
    public List<Company> getAll() throws SQLException {
        List<Company> companies = new ArrayList<>();
        List<Map<String,Object>> relist = (List<Map<String, Object>>) JDBCUtils.runQueryWithResult(ALL_COMPANIES);
        for (Map<String,Object>mapper : relist) {
            companies.add( ConvertUtils.objectToCompany(mapper,couponsListGenerator((Integer) mapper.get("id"))));
        }
        return companies;
    }
    @Override
    public Company getSingle(Integer companyId) throws SQLException {
        Company company =null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> list = JDBCUtils.runQueryWithResult(SINGLE_COMPANY, params);
        company = ConvertUtils.objectToCompany((Map<String, Object>) list.get(0),couponsListGenerator(companyId));
        return company;
    }
    public static List<Coupon> couponsListGenerator(Integer companyId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_BY_COMPANY,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
}
