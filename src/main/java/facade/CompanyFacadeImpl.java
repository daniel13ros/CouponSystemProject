package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemExceptions;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyFacadeImpl extends ClientFacade implements CompanyFacade {

    private int companyId;

    public CompanyFacadeImpl(int companyId) {
        this.companyId = companyId;
    }
    public CompanyFacadeImpl() {
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean login(String email, String password) throws SQLException, CouponSystemExceptions {
        if(!companiesDAO.isCompanyExists(email,password)) throw new CouponSystemExceptions(ErrMsg.EMAIL_OR_PASS_WRONG);
        companyId=companiesDAO.getIdByEmail(email);
        return true;
    }

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, CouponSystemExceptions {
            if (this.couponsDAO.isExistCouponByTitle(coupon.getTitle())) {
                throw new CouponSystemExceptions(ErrMsg.COUPON_EXIST_BT_TITLE);
            } if (this.companiesDAO.companyCounter()==0) {
            throw new CouponSystemExceptions(ErrMsg.NO_COMPANIES);
        }
            this.couponsDAO.add(coupon);
    }


    @Override
    public void updateCoupon(Integer couponId, Coupon coupon) throws SQLException, CouponSystemExceptions {
        this.couponsDAO.updateWithNoIdAndCompanyName(couponId, coupon);
    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException, CouponSystemExceptions {
        if(!this.couponsDAO.isExistCouponById(couponId)) {
            throw new CouponSystemExceptions(ErrMsg.COUPON_NOT_EXIST);
        }
        this.couponsDAO.deleteCouponPurchaseByCustomerId(couponId);
        this.couponsDAO.delete(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupons() throws SQLException {
        return this.couponsDAO.getAllCouponsByCompany(companyId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(Category category) throws SQLException {
        return this.couponsDAO.getAllCouponsByCompanyAndCategory(companyId, category.getId());
    }

    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice) throws SQLException {
        return this.couponsDAO.getAllCouponsUnderMaxPrice(companyId, maxPrice);
    }

    @Override
    public void getCompanyDetails() throws SQLException {
        System.out.println("Company details: ");
        System.out.println(companiesDAO.getSingle(companyId));

    }
}
