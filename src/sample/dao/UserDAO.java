package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.controller.loginController;
import sample.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

public class UserDAO {
    public static ObservableList<User> getUsers() throws SQLException {
        ObservableList<User> users= FXCollections.observableArrayList();

        Connection connection = JDBC.getConnection();
        String query = "select * from client_schedule.users";

        try {
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password"),
                        rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Last_Updated_By"));
                users.add(user);
            }
        } catch (SQLException sqlException) {}
        return users;
    }

    public static User getCurrentUser() throws SQLException {
        ObservableList<User> users = getUsers();
        int currentUser = loginController.getCurrentUserID();
        for (User user: users){
            if (user.getUserID() == currentUser){
                return user;
            }
        }
        return null;

    }
}

//
//SELECT `users`.`User_ID`,
//        `users`.`User_Name`,
//        `users`.`Password`,
//        `users`.`Create_Date`,
//        `users`.`Created_By`,
//        `users`.`Last_Update`,
//        `users`.`Last_Updated_By`
//        FROM `client_schedule`.`users`;
