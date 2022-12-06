package dao;

import beans.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO extends DAO<Coupon, Integer> {
    void addCouponPurchase(int customersId,int couponId) throws SQLException;
    void deleteCouponPurchase(int customersId,int couponId) throws SQLException;
    void deleteCouponPurchaseByCustomerId(int customersId)throws SQLException;
    void deleteCouponPurchaseByCouponId(int couponId)throws SQLException;

    boolean isExistCouponByTitle(String title) throws SQLException;
    boolean isExistCouponById(int couponId) throws SQLException;
    boolean isExistCouponByIdAndCustomerId(int couponId) throws SQLException;
    void updateWithNoIdAndCompanyName(Integer id,Coupon coupon) throws SQLException;
    List<Coupon> getAllCouponsByCompanyAndCategory(int companyId,int categoryId) throws SQLException;
    List<Coupon> getAllCouponsUnderMaxPrice(int companyId, double maxPrice) throws SQLException;
    List<Coupon> getAllCouponsByCustomerAndCategory(int customerId,int categoryId) throws SQLException;
    List<Coupon> getAllCouponsByCustomerId(int customerId) throws SQLException;
    List<Coupon> getAllCouponsByCustomerIdCategory(int customerId,int categoryId) throws SQLException;
    List<Coupon> getAllCouponsByCustomerIdMacPrice(int customerId,double maxPrice) throws SQLException;

    List<Coupon> getAllCouponsByCompany(int companyId) throws SQLException;

    List<Coupon> getAllExpired() throws SQLException;
}
