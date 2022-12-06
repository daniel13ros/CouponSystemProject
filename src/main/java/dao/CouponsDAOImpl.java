package dao;

import beans.Category;
import beans.Company;
import beans.Coupon;
import db.ConvertUtils;
import db.JDBCUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDAOImpl implements CouponsDAO{

    private static final String INSERT_COUPON = "INSERT INTO `java-151-cs1`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COUPON = "UPDATE `java-151-cs1`.`coupons` SET `company_id`, `category_id`, `title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image` =? WHERE (`id` = ?)";
    private static final String UPDATE_COUPON_WITH_NO_ID_COMPANY_NAME = "UPDATE `java-151-cs1`.`coupons` SET  `category_id`=?, `title`=?,`description`=?,`start_date`=?,`end_date`=?,`amount`=?,`price`=?,`image` =? WHERE (`id` = ?)";
    private static final String DELETE_COUPON = "DELETE FROM `java-151-cs1`.`coupons` WHERE (`id` = ?)";
    private static final String ALL_COUPONS = "SELECT * FROM `java-151-cs1`.coupons";
    private static final String ALL_COUPONS_BY_CATEGORY_AND_COMPANY = "SELECT * FROM `java-151-cs1`.coupons as res WHERE (`company_id`=? AND `category_id`=?)";
    private static final String ALL_COUPONS_BY_COMPANY = "SELECT * FROM `java-151-cs1`.coupons as res WHERE `company_id`=?";
    private static final String ALL_COUPONS_BY_CATEGORY_AND_CUSTOMER = "SELECT * FROM `java-151-cs1`.coupons as res WHERE (`customer_id`=? AND `category_id`=?)";
    private static final String ALL_COUPONS_UNDER_MAX_PRICE = "SELECT * FROM `java-151-cs1`.coupons as res WHERE (`company_id`=? AND `price`< ?)";
    private static final String ALL_COUPONS_CUSTOMER_VS_COUPONS_ID = "SELECT * FROM `java-151-cs1`.coupons LEFT JOIN `java-151-cs1`.customers_vs_coupons ON coupons.id=customers_vs_coupons.coupon_id WHERE customer_id=?";
    private static final String ALL_COUPONS_CUSTOMER_VS_COUPONS_ID_CATEGORY = "SELECT * FROM `java-151-cs1`.coupons LEFT JOIN `java-151-cs1`.customers_vs_coupons ON coupons.id=customers_vs_coupons.coupon_id WHERE customer_id=? And category_id=?";
    private static final String ALL_COUPONS_CUSTOMER_VS_COUPONS_ID_MAX_PRICE = "SELECT * FROM `java-151-cs1`.coupons LEFT JOIN `java-151-cs1`.customers_vs_coupons ON coupons.id=customers_vs_coupons.coupon_id WHERE customer_id=? And price < ?";
    private static final String SINGLE_COUPON = "SELECT * FROM `java-151-cs1`.coupons WHERE (`id` = ?)";
    private static final String INSERT_COUPON_PURCHASE = "INSERT INTO `java-151-cs1`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?)";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `java-151-cs1`.`customers_vs_coupons` WHERE (`customer_id`, `coupon_id`) VALUES (?, ?)";
    private static final String DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID = "DELETE FROM `java-151-cs1`.`customers_vs_coupons` WHERE (`coupon_id`=?) ";
    private static final String DELETE_COUPON_PURCHASE_BY_COUPON_ID = "DELETE FROM `java-151-cs1`.`customers_vs_coupons` WHERE `coupon_id`=?";
    private static final String IS_EXIST_CUSTOMER_BY_TITLE = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.coupons WHERE `title`=? ) as res";
    private static final String IS_EXIST_CUSTOMER_BY_ID = "SELECT EXISTS ( SELECT * FROM `java-151-cs1`.coupons WHERE `id`=? ) as res";
    private static final String IS_EXIST_COUPON_BY_CUSTOMER_ID = "SELECT EXISTS (SELECT * FROM `java-151-cs1`.coupons LEFT JOIN `java-151-cs1`.customers_vs_coupons ON coupons.id=customers_vs_coupons.coupon_id WHERE coupon_id=?)as res";
    private static final String ALL_COUPONS_THAT_EXPIRED_BY_DATE = "SELECT * FROM `java-151-cs1`.coupons WHERE `end_date` < ?";
    @Override
    public void addCouponPurchase(int customersId, int couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customersId);
        params.put(2, couponId);
        JDBCUtils.runQuery(INSERT_COUPON_PURCHASE, params);
    }
    @Override
    public void deleteCouponPurchase(int customersId, int couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customersId);
        params.put(2, couponId);
        JDBCUtils.runQuery(DELETE_COUPON_PURCHASE, params);
    }
    @Override
    public void deleteCouponPurchaseByCustomerId(int customersId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customersId);
        JDBCUtils.runQuery(DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID, params);
    }
    @Override
    public void deleteCouponPurchaseByCouponId(int couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        JDBCUtils.runQuery(DELETE_COUPON_PURCHASE_BY_COUPON_ID, params);
    }
    @Override
    public boolean isExistCouponByTitle(String title) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, title);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_CUSTOMER_BY_TITLE, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public boolean isExistCouponById(int couponId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_CUSTOMER_BY_ID, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public boolean isExistCouponByIdAndCustomerId(int couponId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> list = JDBCUtils.runQueryWithResult(IS_EXIST_COUPON_BY_CUSTOMER_ID, params);
        result = ConvertUtils.objectToBool((Map<String, Object>) list.get(0));
        return result;
    }
    @Override
    public void updateWithNoIdAndCompanyName(Integer id, Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCategory().getId());
        params.put(2, coupon.getTitle());
        params.put(3, coupon.getDescription());
        params.put(4, coupon.getStartDate());
        params.put(5, coupon.getEndDate());
        params.put(6, coupon.getAmount());
        params.put(7, coupon.getPrice());
        params.put(8, coupon.getImage());
        params.put(9, id);
        JDBCUtils.runQuery(UPDATE_COUPON_WITH_NO_ID_COMPANY_NAME, params);
    }
    @Override
    public List<Coupon> getAllCouponsByCompanyAndCategory(int companyId,int categoryId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, categoryId);
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_BY_CATEGORY_AND_COMPANY,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
    @Override
    public List<Coupon> getAllCouponsByCustomerId(int customerId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_CUSTOMER_VS_COUPONS_ID,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
    @Override
    public List<Coupon> getAllCouponsByCustomerIdCategory(int customerId,int categoryId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2,categoryId);
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_CUSTOMER_VS_COUPONS_ID_CATEGORY,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
    @Override
    public List<Coupon> getAllCouponsByCustomerIdMacPrice(int customerId, double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2,maxPrice);
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_CUSTOMER_VS_COUPONS_ID_MAX_PRICE,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
    @Override
    public List<Coupon> getAllCouponsUnderMaxPrice(int companyId, double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, maxPrice);
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_UNDER_MAX_PRICE,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
    @Override
    public List<Coupon> getAllCouponsByCustomerAndCategory(int customerId, int categoryId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, categoryId);
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_BY_CATEGORY_AND_CUSTOMER,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
    @Override
    public List<Coupon> getAllCouponsByCompany(int companyId) throws SQLException {
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
    @Override
    public List<Coupon> getAllExpired() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, Date.valueOf(LocalDate.now()));
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS_THAT_EXPIRED_BY_DATE,params);
        for (Object obj : list) {
            Coupon c = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(c);
        }
        return coupons;
    }
    @Override
    public void add(Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().getId());
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        JDBCUtils.runQuery(INSERT_COUPON, params);
    }
    @Override
    public void update(Integer integer, Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory());
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        JDBCUtils.runQuery(UPDATE_COUPON, params);
    }
    @Override
    public void delete(Integer couponId) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        JDBCUtils.runQuery(DELETE_COUPON, params);
    }
    @Override
    public List<Coupon> getAll() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = JDBCUtils.runQueryWithResult(ALL_COUPONS);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }
    @Override
    public Coupon getSingle(Integer couponId) throws SQLException {
        Coupon coupon = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> list = JDBCUtils.runQueryWithResult(SINGLE_COUPON, params);
        coupon = ConvertUtils.objectToCoupon((Map<String, Object>) list.get(0));
        return coupon;
    }
}
