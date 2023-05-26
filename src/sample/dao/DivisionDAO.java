package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Division;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

public class DivisionDAO {
    /**
     * get all divisions from database
     * @return
     */
    public static ObservableList<Division> getDivisions(){
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
                        rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Country_ID"));
                divisions.add(division);
            }
        } catch (SQLException sqlException) {return null;}
        return divisions;
    }

    /**
     * get division by ID number
     * @param divisionID
     * @return
     * @throws SQLException
     */

    public static Division getDivision(int divisionID) throws SQLException {
        ObservableList<Division> divisions = getDivisions();
        for(Division division : divisions){
            if (division.getDivisionID() == divisionID){
                return division;
            }
        }
        return null;
    }

    /**
     * get division by name
     * @param divisionName
     * @return
     * @throws SQLException
     */

    public static Division getDivision(String divisionName) throws SQLException {
        ObservableList<Division> divisions = getDivisions();
        for(Division division : divisions){
            if (division.getDivision().compareToIgnoreCase(divisionName)==0){
                return division;
            }
        }
        return null;
    }

    /**
     * get all divisions by country
     * @param countryID
     * @return
     * @throws SQLException
     */

    public static ObservableList<String> getDivisionsFromCountryID(int countryID) throws SQLException {
        if (countryID < 1 || countryID > 3){return null;}
        ObservableList<Division> divisions = getDivisions();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for(Division division : divisions){
            if(division.getCountryID() == countryID){
                divisionNames.add(division.getDivision());
            }
        }
        return divisionNames;
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
