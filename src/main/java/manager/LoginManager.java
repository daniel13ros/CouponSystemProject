package manager;

import beans.ClientType;
import dao.*;
import exceptions.CouponSystemExceptions;
import exceptions.ErrMsg;
import facade.*;

import java.sql.SQLException;

import static beans.ClientType.COMPANY;

public class LoginManager {
    private static LoginManager instance=null;
    protected CustomersDAO customersDAO=new CustomersDAOImpl();
    protected CompaniesDAO companiesDAO=new CompaniesDAOImpl();


    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, CouponSystemExceptions {
        switch (clientType){
            case ADMINISTRATOR:
                AdminFacade adminFacade=new AdminFacadeImpl();
                if(adminFacade.login(email, password)){
                   return (ClientFacade) adminFacade;
                }
                return null;
            case COMPANY:
               CompanyFacade companyFacade=new CompanyFacadeImpl();
               if(companyFacade.login(email,password)) {
                   ((CompanyFacadeImpl) companyFacade).setCompanyId(companiesDAO.getIdByEmailPassword(email,password));
                   return (ClientFacade) companyFacade;
               }
                return null;
            case CUSTOMER:
                CustomerFacade customerFacade=new CustomerFacadeImpl();
                if(customerFacade.login(email,password)){
                     ((CustomerFacadeImpl) customerFacade).setCustomerID(customersDAO.getIdByEmailPassword(email,password));
                    return (ClientFacade) customerFacade;
                }
                return null;
            default:
                System.out.println("Wrong information details!!");
                return null;
        }
    }

}
