package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static Appointment getAppointment(int appointmentID) throws SQLException {
        ObservableList<Appointment> appointments = getAppointments();
        for (Appointment appointment: appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;
    }

    public static int newAppointmentID() throws SQLException {
        ObservableList<Appointment> appointments = getAppointments();
        List<Integer> appointmentIDs = new ArrayList<Integer>();
        for (Appointment appointment: appointments) {
            appointmentIDs.add(appointment.getAppointmentID());
        }
        int largest = Collections.max(appointmentIDs);
        return largest + 1;
    }

    public static void updateAppointment(Appointment appointment) throws SQLException{
        Connection connection = JDBC.getConnection();
        String query = "insert or update client_schedule.appointments " +
            "set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, " +
            "Created_By = ?, Last_Update = ?, Last_Updated_By =?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "+
            "where Appointment_ID = ?";

        try{
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3,appointment.getLocation());
            ps.setString(4,appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(6,Timestamp.valueOf(appointment.getEnd()));
            ps.setTimestamp(7,Timestamp.valueOf(appointment.getCreateDate()));
            ps.setString(8,appointment.getCreatedBy());
            ps.setTimestamp(9,Timestamp.valueOf(appointment.getLastUpdate()));
            ps.setString(10, appointment.getLastUpdatedBy());
            ps.setInt(11, appointment.getCustomerID());
            ps.setInt(12, appointment.getUserID());
            ps.setInt(13, appointment.getContactID());

            ps.executeUpdate(); //not executeupdate

        }catch(SQLException sqlException){}
    }
}

/*

String query = " update client_schedule.appointments " +
"set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, " +
"Created_By = ?, Last_Update = ?, Last_Updated_By =?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "+
"where Appointment_ID = ?";
 */

//Appointment_ID INT(10) (PK)
//Title VARCHAR(50)
//Description VARCHAR(50)
// Location VARCHAR(50)
//Type VARCHAR(50)
//Start DATETIME
//End DATETIME
//Create_Date DATETIME
//Created_By VARCHAR(50)
//Last_Update TIMESTAMP
//Last_Updated_By VARCHAR(50)
//Customer_ID INT(10) (FK)
//User_ID INT(10) (FK)
//Contact_ID INT(10) (FK)