package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.AppointmentDAO;
import sample.dao.ContactDAO;
import sample.model.Appointment;
import sample.model.Contact;

public class reportContactScheduleController{

    @FXML private ChoiceBox<String> ChoiceBoxContact;

    @FXML private TableView<Appointment> TableViewContact;
    @FXML private TableColumn<?, ?> TableColumnAppointmentID;
    @FXML private TableColumn<?, ?> TableColumnCustomerID;
    @FXML private TableColumn<?, ?> TableColumnDescription;
    @FXML private TableColumn<?, ?> TableColumnEnd;
    @FXML private TableColumn<?, ?> TableColumnStart;
    @FXML private TableColumn<?, ?> TableColumnTitle;
    @FXML private TableColumn<?, ?> TableColumnType;

    /**
     * initialize form
     */

    @FXML
    public void initialize(){
        TableColumnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        TableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumnStart.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        TableColumnEnd.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        TableColumnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        populateChoiceBox();
    }

    /**
     * populate form with contact schedules
     */

    private void populateChoiceBox(){
        ObservableList<Contact> allContacts = ContactDAO.getContacts();
        if(allContacts.isEmpty()){return;}
        for (Contact contact: allContacts) {
            ChoiceBoxContact.getItems().add(contact.getContactName());
        }
        ChoiceBoxContact.setValue(null);
    }

    /**
     * return to appointments form
     * @throws IOException
     */

    @FXML
    void ClickReturn() throws IOException {
        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/appointment.fxml");
        assert resource != null;
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * change contact and get appointments
     * @throws SQLException
     */

    public void ClickChange() throws SQLException {
        String contactName = ChoiceBoxContact.getValue();
        if(contactName.isEmpty()){return;}
        Contact contact = ContactDAO.getContact(contactName);
        if(contact == null){return;}
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointmentsFromContact(contact.getContactID());
        if(appointments.isEmpty()){return;}
        TableViewContact.setItems(appointments);
    }
}
