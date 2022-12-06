package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemExceptions;
import exceptions.ErrMsg;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerFacadeImpl extends ClientFacade implements CustomerFacade {

    private int customerID;

    public CustomerFacadeImpl(int customerID) {
        this.customerID = customerID;
    }
    public CustomerFacadeImpl() {
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @Override
    public boolean login(String email, String password) throws SQLException, CouponSystemExceptions {
        if(!customersDAO.isCustomerExists(email,password)) throw new CouponSystemExceptions(ErrMsg.EMAIL_OR_PASS_WRONG);
        customerID=customersDAO.getIdByEmail(email);
        return true;
    }

    @Override
    public void purchaseCoupon(Coupon coupon) throws SQLException, CouponSystemExceptions {
        if (this.couponsDAO.isExistCouponByIdAndCustomerId(coupon.getId())) {
            throw new CouponSystemExceptions(ErrMsg.COUPON_IS_EXIST_IN_CUSTOMER);
        }
        if (this.couponsDAO.getSingle(coupon.getId()).getAmount()<1) {
            throw new CouponSystemExceptions(ErrMsg.COUPON_AMOUNT_IS_ZERO);
        }
        if (!this.couponsDAO.getSingle(coupon.getId()).getEndDate().after(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemExceptions(ErrMsg.COUPON_EXPIRED);
        }
        if(this.customersDAO.customerCounter()==0){
            throw new CouponSystemExceptions(ErrMsg.NO_CUSTOMERS);
        }
            couponsDAO.addCouponPurchase(customerID, coupon.getId());
            updateAmountOfCoupon(coupon);
    }
    private void updateAmountOfCoupon(Coupon coupon) throws SQLException {
            coupon.setAmount(coupon.getAmount() - 1);
            couponsDAO.updateWithNoIdAndCompanyName(coupon.getId(), coupon);
    }

    @Override
    public List<Coupon> getCustomerCoupons() throws SQLException {
       return this.couponsDAO.getAllCouponsByCustomerId(customerID);
    }

    @Override
    public List<Coupon> getCustomerCoupons(Category category) throws SQLException {
        return this.couponsDAO.getAllCouponsByCustomerIdCategory(customerID,(category.getId()));

    }

    @Override
    public List<Coupon> getCustomerCoupons(double maxPrice) throws SQLException {
        return couponsDAO.getAllCouponsByCustomerIdMacPrice(customerID, maxPrice);
    }

    @Override
    public Customer getCustomerDetails() throws SQLException {
        return customersDAO.getSingle(customerID);
    }
}
