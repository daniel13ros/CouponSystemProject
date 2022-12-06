package db;


import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ConvertUtils {

    public static List<?> toList(ResultSet resultSet) throws SQLException {

        List<Map<String, Object>> list = new ArrayList<>();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        final int columnCount = resultSetMetaData.getColumnCount();

        while (resultSet.next()) {
            Object[] values = new Object[columnCount];
            Map<String, Object> keyAndValue = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                values[i - 1] = resultSet.getObject(i);
                keyAndValue.put(resultSetMetaData.getColumnName(i), values[i - 1]);
            }
            list.add(keyAndValue);
        }
        return list;
    }
    public static boolean objectToBool(Map<String, Object> map) {
        return ((long) map.get("res") == 1);
    }

    public static double objectToDouble(Map<String, Object> map) {
        return ((double) map.get("res"));
    }
    public static int objectToInt(Map<String, Object> map) {
        return map.get("res").hashCode();
    }

    public static Company objectToCompany(Map<String, Object> map,List<Coupon>couponList) {
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String password =  (String)map.get("password");
        return new Company(id,name,email,password,couponList);
    }

    public static Coupon objectToCoupon(Map<String, Object> map) {
        int id = (int) map.get("id");
        int companyId = (int) map.get("company_id");
        Category category =  Category.values()[(int) map.get("category_id")-1];
        String title =  (String)map.get("title");
        String description =  (String)map.get("description");
        Date startDate=(Date) map.get("start_date");
        Date endDate=(Date) map.get("end_date");
        int amount=(int) map.get("amount");
        double price=(double) map.get("price");
        String image=(String) map.get("image");

        return new Coupon(id,companyId,category,title,description,startDate,endDate,amount,price,image);
    }

    public static Customer objectToCustomer(Map<String, Object> map) {
        int id = (int) map.get("id");
        String firstName = (String) map.get("first_name");
        String lastName = (String) map.get("last_name");
        String email =  (String)map.get("email");
        String password =  (String)map.get("password");


        return new Customer(id,firstName,lastName,email,password);
    }

    public static Category objectToCategory(Map<String, Object> map) {
        String st= (String) map.get("name");
        return Category.valueOf(st);
    }
}
