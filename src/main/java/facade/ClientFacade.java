package facade;

import dao.*;
import exceptions.CouponSystemExceptions;

import java.sql.SQLException;

public abstract class ClientFacade {

    protected CustomersDAO customersDAO=new CustomersDAOImpl();
    protected CompaniesDAO companiesDAO=new CompaniesDAOImpl();
    protected CouponsDAO couponsDAO=new CouponsDAOImpl();

    public abstract boolean login(String email,String password) throws SQLException, CouponSystemExceptions;
}
