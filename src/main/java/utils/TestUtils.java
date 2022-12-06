package utils;

import beans.*;
import db.ConnectionPool;
import db.DatabaseManager;
import exceptions.CouponSystemExceptions;
import facade.*;
import jobs.DailyRemoverExpiredCoupons;
import manager.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TestUtils {
    private static DailyRemoverExpiredCoupons dailyRemoverExpiredCoupons = new DailyRemoverExpiredCoupons();
    private static Thread thread = new Thread(dailyRemoverExpiredCoupons);

    public static void testAll() throws SQLException, CouponSystemExceptions {

        SyntaxAndOrganizationUtils.startOrEnd("start");

        DatabaseManager.dropAndCreateStrategy();
        thread.start();


        Customer customer1 = new Customer(1, "daniel", "rosman", "daniel@rosman.com", "1234");
        Customer customer2 = new Customer(2, "ido", "samoha", "ido@samoha.com", "1234");
        Customer customer3 = new Customer(3, "omri", "meroz", "omri@meroz.com", "1234");
        Customer customer4 = new Customer(4, "shir", "mayer", "shir@mayer.com", "1234");
        Customer customer5 = new Customer(5, "ofek", "rajporker", "ofek@rajpurker.com", "1234");

        Company company1 = new Company(1, "walla", "walla@walla.com", "1234", new ArrayList<>());
        Company company2 = new Company(2, "adidas", "adidas@adidas.com", "1234", new ArrayList<>());
        Company company3 = new Company(3, "puma", "puma@puma.com", "1234", new ArrayList<>());
        Company company4 = new Company(4, "microsoft", "microsoft@microsoft.com", "1234", new ArrayList<>());
        Company company5 = new Company(5, "electra", "electra@electra.com", "1234", new ArrayList<>());

        Coupon coupon1 = new Coupon(1, company1.getId(), Category.Sport, "shoes", "lebron5", Date.valueOf(LocalDate.now().plusYears(1)), Date.valueOf(LocalDate.now().plusMonths(1).plusDays(1)), 4, 650, "aaa");
        Coupon coupon2 = new Coupon(2, company1.getId(), Category.Restaurant, "nafis", "one+one", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusYears(1)), 5, 100, "bbb");
        Coupon coupon3 = new Coupon(3, company1.getId(), Category.Electricity, "pc", "lenovo7", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusYears(1)), 5, 5600, "ccc");
        Coupon coupon4 = new Coupon(4, company1.getId(), Category.Restaurant, "BBB", "one+one", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusYears(1).minusDays(15)), 5, 100, "bbb");
        Coupon coupon5 = new Coupon(5, company1.getId(), Category.Electricity, "tv", "dellXV", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusYears(1).plusMonths(3)), 9, 5600, "ccc");

        // Admin facade logic functions:
        AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin@admin.com", ClientType.ADMINISTRATOR);

        adminFacade.addCompany(company1);
        adminFacade.addCompany(company2);
        adminFacade.addCompany(company3);
        adminFacade.addCompany(company4);
        adminFacade.addCompany(company5);

        adminFacade.addCustomer(customer1);
        adminFacade.addCustomer(customer2);
        adminFacade.addCustomer(customer3);
        adminFacade.addCustomer(customer4);
        adminFacade.addCustomer(customer5);
//
//
        SyntaxAndOrganizationUtils.title("Get all companies:");
        adminFacade.getAllCompanies().forEach(System.out::println);

        SyntaxAndOrganizationUtils.title("Get all customers:");
        adminFacade.getAllCustomers().forEach(System.out::println);

        SyntaxAndOrganizationUtils.title("Get single company by id:");
        System.out.println(adminFacade.getSingleCompany(company1.getId()));

        SyntaxAndOrganizationUtils.title("get single customer by id:");
        System.out.println(adminFacade.getSingleCustomer(customer1.getId()));



        company1.setEmail("zara@zara.new.com");
        adminFacade.updateCompany(company1.getId(), company1);

        customer1.setFirstName("dani");
        adminFacade.updateCustomer(customer1.getId(), customer1);

        adminFacade.deleteCompany(company5.getId());
        adminFacade.deleteCustomer(customer5.getId());

        //Company facade logic functions
        CompanyFacade companyFacade1 = (CompanyFacade) LoginManager.getInstance().login("zara@zara.new.com", "1234", ClientType.COMPANY);

        companyFacade1.addCoupon(coupon1);
        companyFacade1.addCoupon(coupon2);
        companyFacade1.addCoupon(coupon3);
        companyFacade1.addCoupon(coupon4);
        companyFacade1.addCoupon(coupon5);

        SyntaxAndOrganizationUtils.title("Get all coupons of company 1:");
        companyFacade1.getCompanyCoupons().forEach(System.out::println);
        SyntaxAndOrganizationUtils.title("Get all coupons of company 1 below max price:");
        companyFacade1.getCompanyCoupons(150).forEach(System.out::println);
        SyntaxAndOrganizationUtils.title("Get all coupons of company 1 by category:");
        companyFacade1.getCompanyCoupons(Category.Restaurant).forEach(System.out::println);
        companyFacade1.getCompanyDetails();

        coupon1.setAmount(15);
        companyFacade1.updateCoupon(coupon1.getId(), coupon1);

        companyFacade1.deleteCoupon(coupon1.getId());

        //Customer facade logic functions
        CustomerFacade customerFacade1 = (CustomerFacade) LoginManager.getInstance().login("daniel@rosman.com", "1234", ClientType.CUSTOMER);

        customerFacade1.purchaseCoupon(coupon2);
        customerFacade1.purchaseCoupon(coupon3);
        customerFacade1.purchaseCoupon(coupon4);

        SyntaxAndOrganizationUtils.title("Get customer 1 coupons:");
        customerFacade1.getCustomerCoupons().forEach(System.out::println);
        SyntaxAndOrganizationUtils.title("Get customer 1 coupons below max price:");
        customerFacade1.getCustomerCoupons(120).forEach(System.out::println);
        SyntaxAndOrganizationUtils.title("Get customer 1 coupons by category:");
        customerFacade1.getCustomerCoupons(Category.Restaurant).forEach(System.out::println);
        SyntaxAndOrganizationUtils.title("Get customer 1 details:");
        System.out.println(customerFacade1.getCustomerDetails());

        dailyRemoverExpiredCoupons.stop();
        thread.interrupt();
        ConnectionPool.getInstance().closeAll();
        SyntaxAndOrganizationUtils.startOrEnd("end");
    }
}