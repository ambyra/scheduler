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
                        //TODO: timezone not loading at right time
                        rs.getTimestamp("Start").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getTimestamp("End").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC")),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                appointments.add(appointment);

            }
        } catch (SQLException sqlException) {}
        return appointments;
    }

    public static ObservableList<Appointment> getAppointments(Customer customer) throws SQLException {
        ObservableList<Appointment> allAppointments = getAppointments();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        for(Appointment appointment : allAppointments){
            if(appointment.getCustomerID() == customer.getCustomerID()){
                customerAppointments.add(appointment);
            }
        }
        return customerAppointments;
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
        if(appointment == null){return;}
        Connection connection = JDBC.getConnection();

        String query = "replace into client_schedule.appointments " +
                "(Appointment_ID, Title, Description, Location, " +
                "Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, " +
                "User_ID, Contact_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

        try{
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, appointment.getAppointmentID());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4,appointment.getLocation());
            ps.setString(5,appointment.getType());
            ps.setObject(6, Timestamp.from(appointment.getStartUTC().toInstant()));
            ps.setObject(7,Timestamp.from(appointment.getEndUTC().toInstant()));
            ps.setObject(8,Timestamp.from(appointment.getCreateDateUTC().toInstant()));
            ps.setString(9,appointment.getCreatedBy());
            ps.setObject(10,Timestamp.from(appointment.getLastUpdateUTC().toInstant()));
            ps.setString(11, appointment.getLastUpdatedBy());
            ps.setInt(12, appointment.getCustomerID());
            ps.setInt(13, appointment.getUserID());
            ps.setInt(14, appointment.getContactID());
            ps.executeUpdate();

        }catch(SQLException sqlException){sqlException.printStackTrace();}
    }

    public static void deleteAppointment(Appointment appointment){
        if(appointment == null){return;}
        Connection connection = JDBC.getConnection();
        String query = "delete from client_schedule.appointments where Appointment_ID = ?";

        try{
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, appointment.getAppointmentID());
            ps.executeUpdate();
        }
        catch(SQLException sqlException){sqlException.printStackTrace();}
    }

    public static void deleteAppointments(Customer customer){
        //todo: implement
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