package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentDAO {
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        Connection connection = JDBC.getConnection();
        String query = "select * from client_schedule.appointments";

        try {
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toLocalDateTime(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                appointments.add(appointment);
            }
        } catch (SQLException sqlException) {}
        return appointments;
    }
}