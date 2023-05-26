package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Customer;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerDAO {
    /**
     * get all customers from db
     * @return
     */
    public static ObservableList<Customer> getCustomers(){
        ObservableList<Customer> customers= FXCollections.observableArrayList();

        Connection connection = JDBC.getConnection();
        String query = "select * from client_schedule.customers " +
                "inner join  client_schedule.first_level_divisions " +
                "on client_schedule.customers.Division_ID = " +
                "client_schedule.first_level_divisions.Division_ID";

        try {
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer= new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Division_ID"));
                customer.setDivision(rs.getString("Division"));
                customers.add(customer);
            }
        } catch (SQLException sqlException) {return null;}
        return customers;
    }

    /**
     * get customer from database
     * @param customerID
     * @return
     */

    public static Customer getCustomer(int customerID){
        ObservableList<Customer> customers = getCustomers();
        if(customers.isEmpty()){return null;}
        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        return null;
    }

    /**
     * remove customer from database
     * @param customer
     */

    public static void deleteCustomer(Customer customer){
        if(customer == null){return;}
        Connection connection = JDBC.getConnection();
        String query = "delete from client_schedule.customers where Customer_ID = ?";

        try{
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, customer.getCustomerID());
            ps.executeUpdate();
        }
        catch(SQLException sqlException){sqlException.printStackTrace();}
    }

    /**
     * update customer in database or add customer
     * @param customer
     */

    public static void updateCustomer(Customer customer){
        if (customer == null){return;}
        Connection connection = JDBC.getConnection();

        String query = "insert into client_schedule.customers " +
                "(Customer_ID, Customer_Name, Address, Postal_Code, " +
                "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "values (?,?,?,?,?,?,?,?,?,?) on duplicate key update " +
                "Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                "Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, " +
                "Last_Updated_By = ?, Division_ID = ?";

        try{
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, customer.getCustomerID());
            ps.setString(2, customer.getCustomerName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhone());
            ps.setObject(6, Timestamp.from(customer.getCreateDateUTC().toInstant()));
            ps.setString(7, customer.getCreatedBy());
            ps.setObject(8,Timestamp.from(customer.getLastUpdateUTC().toInstant()));
            ps.setString(9, customer.getLastUpdatedBy());
            ps.setInt(10, customer.getDivisionID());
            ps.setInt(11, customer.getCustomerID());
            ps.setString(12, customer.getCustomerName());
            ps.setString(13, customer.getAddress());
            ps.setString(14, customer.getPostalCode());
            ps.setString(15, customer.getPhone());
            ps.setObject(16, Timestamp.from(customer.getCreateDateUTC().toInstant()));
            ps.setString(17, customer.getCreatedBy());
            ps.setObject(18,Timestamp.from(customer.getLastUpdateUTC().toInstant()));
            ps.setString(19, customer.getLastUpdatedBy());
            ps.setInt(20, customer.getDivisionID());
            ps.executeUpdate();

        }catch(SQLException sqlException){sqlException.printStackTrace();}
    }

    /**
     * generate new customer id
     * @return
     */

    public static int newCustomerID(){
        ObservableList<Customer> customers= getCustomers();
        List<Integer> customerIDs = new ArrayList<>();
        for (Customer customer: customers) {
            customerIDs.add(customer.getCustomerID());
        }
        int largest = Collections.max(customerIDs);
        return largest + 1;
    }

}

//SELECT `customers`.`Customer_ID`,
//        `customers`.`Customer_Name`,
//        `customers`.`Address`,
//        `customers`.`Postal_Code`,
//        `customers`.`Phone`,
//        `customers`.`Create_Date`,
//        `customers`.`Created_By`,
//        `customers`.`Last_Update`,
//        `customers`.`Last_Updated_By`,
//        `customers`.`Division_ID`
//        FROM `client_schedule`.`customers`;
