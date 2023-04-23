package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toLocalDateTime(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Division_ID"));
                customers.add(customer);
            }
        } catch (SQLException sqlException) {}
        return customers;
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