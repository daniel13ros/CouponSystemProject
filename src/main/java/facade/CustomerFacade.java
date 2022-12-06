package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemExceptions;

import java.sql.SQLException;
import java.util.List;

public interface CustomerFacade {

    void purchaseCoupon(Coupon coupon) throws SQLException, CouponSystemExceptions;
    List<Coupon> getCustomerCoupons() throws SQLException;
    List<Coupon> getCustomerCoupons(Category category) throws SQLException;
    List<Coupon> getCustomerCoupons( double maxPrice) throws SQLException;
    Customer getCustomerDetails() throws SQLException;

    boolean login(String email, String password) throws SQLException, CouponSystemExceptions;
}
