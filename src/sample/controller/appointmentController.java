package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.AppointmentDAO;
import sample.dao.ContactDAO;
import sample.dao.CustomerDAO;
import sample.dao.UserDAO;
import sample.model.Appointment;
import sample.model.Contact;
import sample.model.Customer;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
    @FXML private ChoiceBox<Integer> ChoiceBoxContactID;
    @FXML private ChoiceBox<Integer> ChoiceBoxCustomerID;
    @FXML private ChoiceBox<Integer> ChoiceBoxUserID;

    @FXML private DatePicker DatePickerEndDate;
    @FXML private DatePicker DatePickerStartDate;

    @FXML private ToggleGroup RadioGroupAppointments;
    @FXML private RadioButton RadioButtonAllAppointments;
    @FXML private RadioButton RadioButtonMonth;
    @FXML private RadioButton RadioButtonWeek;

    @FXML private TableView<Appointment> TableViewAppointments;

    @FXML private TableColumn<?, ?> TableColumnAppointmentID;
    @FXML private TableColumn<?, ?> TableColumnContact;
    @FXML private TableColumn<?, ?> TableColumnCustomerID;
    @FXML private TableColumn<?, ?> TableColumnDescription;
    @FXML private TableColumn<?, ?> TableColumnEnd;
    @FXML private TableColumn<?, ?> TableColumnStart;
    @FXML private TableColumn<?, ?> TableColumnTitle;
    @FXML private TableColumn<?, ?> TableColumnType;
    @FXML private TableColumn<?, ?> TableColumnUserID;
    @FXML private TableColumn<?, ?> TableColumnLocation;

    @FXML private TextField TextFieldAppointmentID;
    @FXML private TextField TextFieldDescription;
    @FXML private TextField TextFieldEndTime;
    @FXML private TextField TextFieldLocation;
    @FXML private TextField TextFieldStartTime;
    @FXML private TextField TextFieldTitle;
    @FXML private TextField TextFieldType;

    @FXML private Button ButtonAdd;
    @FXML private Button ButtonEdit;
    @FXML private Button ButtonSave;
    @FXML private Button ButtonCancel;
    @FXML private Button ButtonDelete;

    @FXML private Button ButtonCustomers;
    @FXML private Button ButtonTotal;
    @FXML private Button ButtonContact;
    @FXML private Button ButtonAdditional;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        TableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumnContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        TableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumnStart.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        TableColumnEnd.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        TableColumnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        TableColumnUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        startRadioGroupAppointmentsListener();

        try {
            selectState();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void startRadioGroupAppointmentsListener(){
        //lambda
        RadioGroupAppointments.selectedToggleProperty().
                addListener((observable, oldValue, newValue) -> {
                    if(newValue.equals(RadioButtonAllAppointments)){
                        displayTableViewAppointments(9999);
                    }
                    if(newValue.equals(RadioButtonMonth)){
                        displayTableViewAppointments(30);
                    }
                    if(newValue.equals(RadioButtonWeek)){
                        displayTableViewAppointments(7);
                    }
                });
    }

    private void checkAppointmentHours() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();

        for(Appointment appointment: allAppointments){
            ZonedDateTime appointmentStart = appointment.getStartEST();
            ZonedDateTime appointmentEnd = appointment.getEndEST();

            int appointmentStartHour = appointmentStart.getHour();
            int appointmentEndHour = appointmentEnd.getHour();

            if(appointmentStartHour < 8 || appointmentEndHour > 22){
                sendAlert("Appointment #" +
                        appointment.getAppointmentID() +
                        " is outside of business hours, " +
                        "8:00 a.m. to 10:00 p.m. EST, including weekends");
            }
        }
    }

    private boolean checkAppointmentHours(Appointment appointment){
        ZonedDateTime appointmentStart = appointment.getStartEST();
        ZonedDateTime appointmentEnd = appointment.getEndEST();

        int appointmentStartHour = appointmentStart.getHour();
        int appointmentEndHour = appointmentEnd.getHour();

        if(appointmentStartHour < 8 || appointmentEndHour > 22){
            sendAlert("Cannot create appointment. " +
                    "Appointment must be scheduled during business hours, "+
                    "8:00 a.m. to 10:00 p.m. EST, including weekends");
            return true;
        }
        return false;
    }

    void checkAppointmentUpcoming() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));
        boolean isAppointment = false;
        for (Appointment appointment : allAppointments) {
            ZonedDateTime appStart = appointment.getStartUTC();
            if (appStart.isAfter(currentTime) && appStart.isBefore(currentTime.plusMinutes(15))) {
                sendAlert("Upcoming appointment within 15 minutes.\n" +
                        "Appointment #" + appointment.getAppointmentID() + "\n" +
                        "Starts: " + appointment.getStartLocal());
                isAppointment = true;
            }
        }
        if (!isAppointment) {sendAlert("No upcoming appointment within 15 minutes");}
    }

    void checkAppointmentOverlap() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        for(Appointment appointment1: allAppointments){
            ZonedDateTime start1 = appointment1.getStart();
            ZonedDateTime end1 = appointment1.getEnd();
            for(Appointment appointment2: allAppointments){
                if(appointment1 != appointment2){
                    ZonedDateTime start2 = appointment2.getStart();
                    ZonedDateTime end2 = appointment2.getEnd();
                    if(
                        start1.isBefore(start2) && end1.isAfter(end2) ||
                        start1.isBefore(end2) && end1.isAfter(end2) ||
                        start1.isBefore(start2) && end1.isAfter(end2) ||
                        start1.isAfter(start2) && end1.isBefore(end2))
                    {
                        String alertString ="Appointment " + appointment1.getAppointmentID() +
                                " overlaps with Appointment " + appointment2.getAppointmentID();
                        sendAlert(alertString);
                    }
                }
            }
        }
    }

    private boolean checkAppointmentOverlap(Appointment appointment1) throws SQLException {
        ZonedDateTime start1 = appointment1.getStart();
        ZonedDateTime end1 = appointment1.getEnd();
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        for(Appointment appointment2: allAppointments){
            if(appointment1 != appointment2){
                ZonedDateTime start2 = appointment2.getStart();
                ZonedDateTime end2 = appointment2.getEnd();
                if(
                        start1.isBefore(start2) && end1.isAfter(end2) ||
                                start1.isBefore(end2) && end1.isAfter(end2) ||
                                start1.isBefore(start2) && end1.isAfter(end2) ||
                                start1.isAfter(start2) && end1.isBefore(end2))
                {
                    String alertString ="Cannot create appointment. This appointment"+
                            " overlaps with Appointment " + appointment2.getAppointmentID();
                    sendAlert(alertString);
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    void ClickAdd() throws SQLException {
        clearBoxes();
        setBoxesEnabled(true);
        populateChoiceBoxes();

        TableViewAppointments.setDisable(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);

        int newAppointmentID = AppointmentDAO.newAppointmentID();
        TextFieldAppointmentID.setText(String.valueOf(newAppointmentID));
    }

    @FXML
    void ClickEdit() throws SQLException {
        Appointment selectedAppointment = getAppointmentFromSelection();
        if(selectedAppointment == null){return;}

        TableViewAppointments.setDisable(true);
        clearBoxes();
        setBoxesEnabled(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);
        setBoxes(selectedAppointment);
    }

    @FXML
    void ClickSave() throws SQLException {
        Appointment appointment = parseInput();
        if(appointment != null){
            AppointmentDAO.updateAppointment(appointment);
            //exit state
            selectState();
        }
    }

    private Appointment parseInput() throws SQLException {
        int appointmentid = 0;
        int contactid = 0;
        int customerid = 0;
        int userid = 0;

        try{
            appointmentid = Integer.parseInt(TextFieldAppointmentID.getText());
            contactid = ChoiceBoxContactID.getValue();
            customerid = ChoiceBoxCustomerID.getValue();
            userid = ChoiceBoxUserID.getValue();
        }catch(NullPointerException e){
            sendAlert("ID Field cannot be empty");
            return null;
        }

        String title = TextFieldTitle.getText();
        if(title.isEmpty()){
            sendAlert("Title cannot be empty");
            return null;
        }

        String description = TextFieldDescription.getText();
        if(description.isEmpty()){
            sendAlert("Description cannot be empty");
            return null;
        }

        String location = TextFieldLocation.getText();
        if(location.isEmpty()){
            sendAlert("Location cannot be empty");
            return null;
        }

        String type = TextFieldType.getText();
        if(type.length()==0){
            sendAlert("Type cannot be empty");
            return null;
        }

        LocalDate startDate = DatePickerStartDate.getValue();
        LocalTime startTime = parseTime(TextFieldStartTime.getText());
        if (startTime==null){
            sendAlert("Enter a valid start time in the format of HH:mm");
            return null;
        }

        ZonedDateTime startSystem= ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault());

        LocalDate endDate = DatePickerEndDate.getValue();
        LocalTime endTime = parseTime(TextFieldEndTime.getText());
        if (endTime==null){
            sendAlert("Enter a valid end time in the format of HH:mm");
            return null;
        }

        ZonedDateTime endSystem = ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault());

        if(startSystem.isAfter(endSystem)){
            sendAlert("Appointment end must be after appointment start");
            return null;
        }

        User currentUser = UserDAO.getCurrentUser();
        ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("UTC"));
        String createdBy = currentUser.getUserName();

        //check if appointment already exists
        Appointment oldAppointment = AppointmentDAO.getAppointment(appointmentid);
        if(oldAppointment != null){
            createDate  = oldAppointment.getCreateDate();
            createdBy = oldAppointment.getCreatedBy();
        }

        ZonedDateTime lastUpdate = ZonedDateTime.now(ZoneId.of("UTC"));
        String lastUpdatedBy = currentUser.getUserName();

        Appointment appointment = new Appointment(appointmentid, title, description, location, type,
                startSystem, endSystem, createDate, createdBy, lastUpdate, lastUpdatedBy,
                customerid, userid, contactid);

        if(checkAppointmentOverlap(appointment)){return null;}
        if(checkAppointmentHours(appointment)){return null;}
        return appointment;
    }

    LocalTime parseTime(String time){
        LocalTime parsedTime;
        try{
            parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

        }catch (DateTimeParseException e){
            return null;
        }
        return parsedTime;
    }

    @FXML
    void ClickCancel () throws SQLException {
        selectState();
    }

    @FXML
    void ClickDelete () throws SQLException {
        Appointment selectedAppointment = getAppointmentFromSelection();
        if(selectedAppointment != null){
            AppointmentDAO.deleteAppointment(selectedAppointment);
            sendAlert("Appointment ID#" +
                    selectedAppointment.getAppointmentID() +
                    " deleted.");
        }else{
            sendAlert("No appointment selected.");
        }
        selectState();
    }

    @FXML
    void ClickCustomers () throws IOException {
        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/customer.fxml");
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML void ClickTotal() throws SQLException, IOException {
        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/reportTotal.fxml");
        assert resource != null;
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML void ClickContact(){//TODO: implement
        //a schedule for each contact in your organization that includes
        // appointment ID, title, type and description, start date and time,
        // end date and time, and customer ID
    }
    @FXML void ClickAdditional(){} //TODO: implement

    void selectState() throws SQLException {
        TableViewAppointments.setDisable(false);
        displayTableViewAppointments(9999);
        RadioButtonAllAppointments.setSelected(true);

        clearBoxes();
        setBoxesEnabled(false);

        setButtonsEnabled(false);
        ButtonAdd.setDisable(false);
        ButtonEdit.setDisable(false);
        ButtonDelete.setDisable(false);

        ButtonCustomers.setDisable(false);
        ButtonTotal.setDisable(false);
        ButtonContact.setDisable(false);
        ButtonAdditional.setDisable(false);

        checkAppointmentHours();
        checkAppointmentOverlap();
        checkAppointmentUpcoming();
    }

    void clearBoxes(){
        TextFieldAppointmentID.clear();
        ChoiceBoxContactID.getItems().clear();
        ChoiceBoxCustomerID.getItems().clear();
        ChoiceBoxUserID.getItems().clear();

        ChoiceBoxContactID.setValue(null);
        ChoiceBoxCustomerID.setValue(null);
        ChoiceBoxContactID.setValue(null);

        TextFieldDescription.clear();
        TextFieldEndTime.clear();
        TextFieldLocation.clear();
        TextFieldStartTime.clear();

        TextFieldTitle.clear();
        TextFieldType.clear();
        DatePickerStartDate.setValue(null);
        DatePickerEndDate.setValue(null);
    }

    void setButtonsEnabled(boolean areEnabled){
        areEnabled = !areEnabled;
        ButtonAdd.setDisable(areEnabled);
        ButtonEdit.setDisable(areEnabled);
        ButtonSave.setDisable(areEnabled);
        ButtonCancel.setDisable(areEnabled);
        ButtonDelete.setDisable(areEnabled);
        ButtonCustomers.setDisable(areEnabled);
        ButtonTotal.setDisable(areEnabled);
        ButtonContact.setDisable(areEnabled);
        ButtonAdditional.setDisable(areEnabled);
    }

    void setBoxesEnabled(boolean areEnabled){
        areEnabled = !areEnabled;
        TextFieldAppointmentID.setDisable(areEnabled);
        TextFieldDescription.setDisable(areEnabled);
        TextFieldEndTime.setDisable(areEnabled);
        TextFieldLocation.setDisable(areEnabled);
        TextFieldStartTime.setDisable(areEnabled);
        TextFieldTitle.setDisable(areEnabled);
        TextFieldType.setDisable(areEnabled);
        ChoiceBoxUserID.setDisable(areEnabled);
        ChoiceBoxCustomerID.setDisable(areEnabled);
        ChoiceBoxContactID.setDisable(areEnabled);
        DatePickerStartDate.setDisable(areEnabled);
        DatePickerEndDate.setDisable(areEnabled);
    }

    private void setBoxes(Appointment appointment) throws SQLException {
        if(appointment == null){return;}

        populateChoiceBoxes();
        TextFieldAppointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        ChoiceBoxUserID.setValue(appointment.getUserID());
        ChoiceBoxCustomerID.setValue(appointment.getCustomerID());
        ChoiceBoxContactID.setValue(appointment.getContactID());

        TextFieldTitle.setText(appointment.getTitle());
        TextFieldDescription.setText(appointment.getDescription());
        TextFieldLocation.setText(appointment.getLocation());
        TextFieldType.setText(appointment.getType());

        TextFieldStartTime.setText(appointment.getStartSystem().toLocalTime().toString());

        DatePickerStartDate.setValue(appointment.getStartSystem().toLocalDate());
        TextFieldEndTime.setText(appointment.getEndSystem().toLocalTime().toString());
        DatePickerEndDate.setValue(appointment.getEndSystem().toLocalDate());
    }

    private Appointment getAppointmentFromSelection(){
        return TableViewAppointments.getSelectionModel().getSelectedItem();
    }

    private void populateChoiceBoxes() throws SQLException {
        ObservableList<Contact> allContacts = ContactDAO.getContacts();
        for (Contact contact: allContacts) {
            ChoiceBoxContactID.getItems().add(contact.getContactID());
        }

        ObservableList<User> allUsers = UserDAO.getUsers();
        for (User user: allUsers) {
            ChoiceBoxUserID.getItems().add(user.getUserID());
        }

        ObservableList<Customer> allCustomers = CustomerDAO.getCustomers();
        for (Customer customer: allCustomers){
            ChoiceBoxCustomerID.getItems().add(customer.getCustomerID());
        }
    }

    private void displayTableViewAppointments(int daysBack){
        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
            ObservableList<Appointment> displayAppointments = FXCollections.observableArrayList();
            for(Appointment appointment : allAppointments){
                if(appointment.getStart().isAfter(ZonedDateTime.now().minusDays(daysBack))){
                    appointment.setStart(appointment.getStartSystem());
                    appointment.setEnd(appointment.getEndSystem());
                    displayAppointments.add(appointment);
                }
            }
            TableViewAppointments.getItems().clear();
            TableViewAppointments.setItems(displayAppointments);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void sendAlert(String alertMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert!");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}

