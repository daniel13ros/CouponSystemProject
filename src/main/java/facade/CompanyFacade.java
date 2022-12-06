package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemExceptions;

import java.sql.SQLException;
import java.util.List;

public interface CompanyFacade {

    void addCoupon(Coupon coupon) throws SQLException, CouponSystemExceptions;
    void updateCoupon(Integer couponId, Coupon coupon) throws SQLException, CouponSystemExceptions;
    void deleteCoupon(int couponId) throws SQLException, CouponSystemExceptions;
    List<Coupon> getCompanyCoupons() throws SQLException;
    List<Coupon> getCompanyCoupons(Category category) throws SQLException;
    List<Coupon> getCompanyCoupons(double maxPrice) throws SQLException;
    void getCompanyDetails() throws SQLException;

    boolean login(String email, String password) throws SQLException, CouponSystemExceptions;
}
