package facade;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemExceptions;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminFacadeImpl extends ClientFacade implements AdminFacade{

    private String EMAIL="admin@admin.com";
    private String PASSWORD="admin@admin.com";

    public AdminFacadeImpl() {
    }



    @Override
    public void addCustomer(Customer customer) throws SQLException, CouponSystemExceptions {
        if(this.customersDAO.isCustomerExistsByEmail(customer.getEmail())) {
            throw new CouponSystemExceptions(ErrMsg.CUSTOMER_NAME_EMAIL_EXIST);
        }
        this.customersDAO.add(customer);
    }

    @Override
    public void updateCustomer(Integer customerId,Customer customer) throws SQLException, CouponSystemExceptions {
        if(this.customersDAO.isCustomerExistsByName(customer.getFirstName())) {
            throw new CouponSystemExceptions(ErrMsg.CUSTOMER_NAME_EXIST);
        }
        this.customersDAO.update(customerId,customer);
    }

    @Override
    public void deleteCustomer(int id) throws SQLException, CouponSystemExceptions {
        if(this.customersDAO.isCustomerExistsById(id)) {
           throw new CouponSystemExceptions(ErrMsg.CUSTOMER_NOT_EXIST);
        } for (Coupon c : customersDAO.getSingle(id).getCoupons()) {
            this.couponsDAO.deleteCouponPurchaseByCustomerId(id);
        }
        this.customersDAO.delete(id);
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return new ArrayList<>(this.customersDAO.getAll());
    }

    @Override
    public Customer getSingleCustomer(int id) throws SQLException {
        return this.customersDAO.getSingle(id);
    }

    @Override
    public void addCompany(Company company) throws CouponSystemExceptions, SQLException {
        if(this.companiesDAO.isCompanyExistsByName(company.getName())){
            throw new CouponSystemExceptions(ErrMsg.COMPANY_NAME_EXIST);
        }if(this.companiesDAO.isCompanyExistsByEmail(company.getEmail())){
            throw new CouponSystemExceptions(ErrMsg.COMPANY_EMAIL_EXIST);
        }
        this.companiesDAO.add(company);
    }

    @Override
    public void updateCompany(Integer companyId, Company company) throws SQLException, CouponSystemExceptions {
        if(this.companiesDAO.isCompanyExistsByEmail(company.getEmail())){
            throw new CouponSystemExceptions(ErrMsg.COMPANY_EMAIL_EXIST);
        }
        this.companiesDAO.updateCompanyEmailPassword( companyId, company);
    }

    @Override
    public void deleteCompany(int id) throws SQLException, CouponSystemExceptions {
        if(!this.companiesDAO.isCompanyExistById(id)) {
            throw new CouponSystemExceptions(ErrMsg.COMPANY_NOT_EXIST);
        }
        for (Coupon c:companiesDAO.getSingle(id).getCoupons()) {
            couponsDAO.deleteCouponPurchaseByCouponId(c.getId());
        }
        this.companiesDAO.delete(id);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        return this.companiesDAO.getAll();
    }

    @Override
    public Company getSingleCompany(int id) throws SQLException {
        return this.companiesDAO.getSingle(id);    }

    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        if(!email.equals(EMAIL) || !password.equals(PASSWORD)){
            throw new CouponSystemExceptions(ErrMsg.EMAIL_OR_PASS_WRONG);
        }
        return true;
    }
}
