package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import sample.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    public static ObservableList<Country> getCountries() throws SQLException {
        ObservableList<Country> countries= FXCollections.observableArrayList();

        Connection connection = JDBC.getConnection();
        String query = "select * from client_schedule.countries";

        try {
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Country contact = new Country(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toLocalDateTime(),
                        rs.getString("Last_Updated_By"));
                countries.add(contact);
            }
        } catch (SQLException sqlException) {}
        return countries;
    }
}

//SELECT `countries`.`Country_ID`,
//        `countries`.`Country`,
//        `countries`.`Create_Date`,
//        `countries`.`Created_By`,
//        `countries`.`Last_Update`,
//        `countries`.`Last_Updated_By`
//        FROM `client_schedule`.`countries`;