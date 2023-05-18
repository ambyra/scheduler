package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Appointment;
import sample.model.Customer;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerDAO {
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers= FXCollections.observableArrayList();

        Connection connection = JDBC.getConnection();
        String query = "select * from client_schedule.customers";

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
                customers.add(customer);
            }
        } catch (SQLException sqlException) {}
        return customers;
    }

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
    public static void updateCustomer(Customer customer) throws SQLException{
        if (customer == null){return;}
        Connection connection = JDBC.getConnection();

        String query = "replace into client_schedule.customers " +
                "(Customer_ID, Customer_Name, Address, Postal_Code, " +
                "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID, " +
                "User_ID, Contact_ID) values (?,?,?,?,?,?,?,?,?,?) ";

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
            ps.executeUpdate();

        }catch(SQLException sqlException){sqlException.printStackTrace();}
    }

    public static int newCustomerID() throws SQLException {
        ObservableList<Customer> customers= getCustomers();
        List<Integer> customerIDs = new ArrayList<Integer>();
        for (Customer customer: customers) {
            customerIDs.add(customer.getCustomerID());
        }
        int largest = Collections.max(customerIDs);
        return largest + 1;
    }
}

