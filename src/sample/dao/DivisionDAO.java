package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Division;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {
    public static ObservableList<Division> getCustomers() throws SQLException {
        ObservableList<Division> divisions= FXCollections.observableArrayList();

        Connection connection = JDBC.getConnection();
        String query = "select * from client_schedule.first_level_divisions";

        try {
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Division division = new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toLocalDateTime(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Country_ID"));
                divisions.add(division);
            }
        } catch (SQLException sqlException) {}
        return divisions;
    }
}
//SELECT `first_level_divisions`.`Division_ID`,
//        `first_level_divisions`.`Division`,
//        `first_level_divisions`.`Create_Date`,
//        `first_level_divisions`.`Created_By`,
//        `first_level_divisions`.`Last_Update`,
//        `first_level_divisions`.`Last_Updated_By`,
//        `first_level_divisions`.`Country_ID`
//        FROM `client_schedule`.`first_level_divisions`;
