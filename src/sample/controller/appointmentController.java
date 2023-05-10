package sample.controller;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dao.AppointmentDAO;
import sample.dao.UserDAO;
import sample.helper.TimeZoneConversions;
import sample.model.Appointment;
import sample.model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
    @FXML private TextField TextFieldLocalDateTime;
    @FXML private TextField TextFieldLocalTimeZone;
    @FXML private TextField TextFieldUTCDateTime;
    @FXML private TextField TextFieldESTDateTime;

    @FXML private Button ButtonAdd;
    @FXML private Button ButtonEdit;
    @FXML private Button ButtonSave;
    @FXML private Button ButtonCancel;
    @FXML private Button ButtonDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //entry state
        try {
            selectState();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        startRadioGroupAppointmentsListener();

    }

    private void startRadioGroupAppointmentsListener(){
        //TODO: view appointment schedules by month and week using a TableView
        RadioGroupAppointments.selectedToggleProperty().
                addListener((observable, oldValue, newValue) -> {
                    if(newValue.equals(RadioButtonAllAppointments)){
                        System.out.println("All Appointments");
                    }
                    if(newValue.equals(RadioButtonMonth)){
                        System.out.println("Month");
                    }
                    if(newValue.equals(RadioButtonWeek)){
                        System.out.println("Week");
                        displayTableViewAppointmentsWeek();
                    }
                });

    }



    private void checkAppointments() throws SQLException {
        //TODO: check for scheduling an appointment outside of business hours,
        // defined as 8:00 a.m. to 10:00 p.m. EST, including weekends

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();

        for(Appointment appointment: allAppointments){
            ZonedDateTime appointmentStart = appointment.getStartEST();
            ZonedDateTime appointmentEnd = appointment.getEndEST();

            int appointmentStartHour = appointmentStart.getHour();
            int appointmentEndHour = appointmentEnd.getHour();
            System.out.println("ID " +appointment.getAppointmentID() +
                    " " +appointmentStartHour  + " - " +
                    appointmentEndHour);

            if(appointmentStartHour < 8 || appointmentEndHour > 22){
                sendAlert("Appointment #" +
                        appointment.getAppointmentID() +
                        " is outside of business hours, " +
                        "8:00 a.m. to 10:00 p.m. EST, including weekends");
            }
        }

        //TODO: check for scheduling overlapping appointments for customers

        //TODO: provide an alert when there is an appointment within 15 minutes
        // of the user’s log-in. A custom message should be displayed in the user interface
        // and include the appointment ID, date, and time.
        // If the user does not have any appointments within 15 minutes of logging in,
        // display a custom message in the user interface indicating there are no upcoming appointments.
    }

    void setTimers(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                String format = "yyyy-MM-dd HH:mm:ss";

                TextFieldLocalDateTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)));
                TextFieldLocalTimeZone.setText(ZoneId.systemDefault().toString());

                TextFieldUTCDateTime.setText(ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern(format)));
                TextFieldESTDateTime.setText(ZonedDateTime.now(ZoneId.of("US/Eastern")).format(DateTimeFormatter.ofPattern(format)));
            }
        };
        timer.start();
    }

    @FXML
    void ClickAdd (ActionEvent event) throws SQLException {
        addState();
        int newAppointmentID = AppointmentDAO.newAppointmentID();
        TextFieldAppointmentID.setText(String.valueOf(newAppointmentID));
    }

    @FXML
    void ClickEdit(ActionEvent event) throws SQLException {
        editState();
        setBoxes(getAppointmentFromSelection());
    }

    @FXML
    void ClickSave (ActionEvent event) throws SQLException {
        Appointment appointment = parseInput();
        AppointmentDAO.updateAppointment(appointment);
        //exitstate
        if(appointment != null){
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
        }

        String title = TextFieldTitle.getText();
        if(title.isEmpty()){sendAlert("Title cannot be empty"); return null;}

        String description = TextFieldDescription.getText();
        if(description.isEmpty()){sendAlert("Description cannot be empty"); return null;}

        String location = TextFieldLocation.getText();
        if(location.isEmpty()){sendAlert("Location cannot be empty"); return null;}

        String type = TextFieldType.getText();
        if(type.length()==0){sendAlert("Type cannot be empty"); return null;}

        LocalDate startDate = DatePickerStartDate.getValue();
        LocalTime startTime = parseTime(TextFieldStartTime.getText());
        if (startTime==null){
            sendAlert("Enter a valid start time in the format of HH:mm");
            return null;
        }
        LocalDateTime startDateTimeSystem = LocalDateTime.of(startDate,startTime);
        LocalDateTime startDateTimeUTC = TimeZoneConversions.SystemToUTC(startDateTimeSystem);

        LocalDate endDate = DatePickerEndDate.getValue();
        LocalTime endTime = parseTime(TextFieldEndTime.getText());
        if (endTime==null){
            sendAlert("Enter a valid end time in the format of HH:mm");
            return null;
        }
        LocalDateTime endDateTimeSystem = LocalDateTime.of(endDate,endTime);
        LocalDateTime endDateTimeUTC = TimeZoneConversions.SystemToUTC(endDateTimeSystem);

        User currentUser = UserDAO.getCurrentUser();

        LocalDateTime createDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();
        String createdBy = currentUser.getUserName();

        //check if appointment already exists
        Appointment oldAppointment = AppointmentDAO.getAppointment(appointmentid);
        if(oldAppointment != null){
            createDate  = oldAppointment.getCreateDate();
            createdBy = oldAppointment.getCreatedBy();
        }

        LocalDateTime lastUpdate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();
        String lastUpdatedBy = currentUser.getUserName();

        Appointment appointment = new Appointment(appointmentid, title, description, location, type,
                startDateTimeUTC, endDateTimeUTC, createDate, createdBy, lastUpdate, lastUpdatedBy,
                customerid, userid, contactid);

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
    void ClickCancel (ActionEvent event) throws SQLException {
        selectState();
    }

    @FXML
    void ClickDelete (ActionEvent event) throws SQLException {
        Appointment appointment = getAppointmentFromSelection();
        AppointmentDAO.deleteAppointment(appointment);
        selectState();
    }

    void selectState() throws SQLException {
        TableViewAppointments.setDisable(false);
        TextFieldAppointmentID.setDisable(true);

        clearBoxes();
        disableBoxes();
        setTimers();

        disableButtons();
        ButtonAdd.setDisable(false);
        ButtonEdit.setDisable(false);
        ButtonDelete.setDisable(false);

        populateAppointmentsTableView();
        try {populateChoiceBoxes(); }
        catch (SQLException sqlException) { sqlException.printStackTrace();}
        checkAppointments();

    }
    void addState(){
        TableViewAppointments.setDisable(true);
        disableButtons();
        enableBoxes();
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);
    }
    void editState(){
        TableViewAppointments.setDisable(true);
        enableBoxes();
        disableButtons();
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);
    }
    void clearBoxes(){
        TextFieldAppointmentID.clear();
        TextFieldDescription.clear();
        TextFieldEndTime.clear();
        TextFieldLocation.clear();
        TextFieldStartTime.clear();
        TextFieldTitle.clear();
        TextFieldType.clear();
        DatePickerStartDate.setValue(null);
        DatePickerEndDate.setValue(null);
    }

    void disableButtons(){
        ButtonAdd.setDisable(true);
        ButtonEdit.setDisable(true);
        ButtonSave.setDisable(true);
        ButtonCancel.setDisable(true);
        ButtonDelete.setDisable(true);
    }

    void disableBoxes(){
        TextFieldAppointmentID.setDisable(true);
        TextFieldDescription.setDisable(true);
        TextFieldEndTime.setDisable(true);
        TextFieldLocation.setDisable(true);
        TextFieldStartTime.setDisable(true);
        TextFieldTitle.setDisable(true);
        TextFieldType.setDisable(true);
        ChoiceBoxUserID.setDisable(true);
        ChoiceBoxCustomerID.setDisable(true);
        ChoiceBoxContactID.setDisable(true);
        DatePickerStartDate.setDisable(true);
        DatePickerEndDate.setDisable(true);
    }

    void enableBoxes(){
        TextFieldAppointmentID.setDisable(false);
        TextFieldDescription.setDisable(false);
        TextFieldEndTime.setDisable(false);
        TextFieldLocation.setDisable(false);
        TextFieldStartTime.setDisable(false);
        TextFieldTitle.setDisable(false);
        TextFieldType.setDisable(false);
        ChoiceBoxUserID.setDisable(false);
        ChoiceBoxCustomerID.setDisable(false);
        ChoiceBoxContactID.setDisable(false);
        DatePickerStartDate.setDisable(false);
        DatePickerEndDate.setDisable(false);
    }

    private void setBoxes(Appointment appointment) throws SQLException {
        TextFieldAppointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        TextFieldTitle.setText(appointment.getTitle());
        TextFieldDescription.setText(appointment.getDescription());
        TextFieldLocation.setText(appointment.getLocation());
        TextFieldType.setText(appointment.getType());

        TextFieldStartTime.setText(appointment.getStart().toLocalTime().toString());
        DatePickerStartDate.setValue(appointment.getStart().toLocalDate());
        TextFieldEndTime.setText(appointment.getEnd().toLocalTime().toString());
        DatePickerEndDate.setValue(appointment.getEnd().toLocalDate());

        populateChoiceBoxes();
    }

    private Appointment getAppointmentFromSelection() throws SQLException {
        return TableViewAppointments.getSelectionModel().getSelectedItem();
    }

    private void populateChoiceBoxes() throws SQLException {
        ChoiceBoxContactID.getItems().clear();
        ChoiceBoxCustomerID.getItems().clear();
        ChoiceBoxUserID.getItems().clear();

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        for (Appointment appointment: allAppointments) {
            //TODO: get these from contactDAO
            ChoiceBoxContactID.getItems().add(appointment.getContactID());
            //TODO: get these from userDAO
            ChoiceBoxUserID.getItems().add(appointment.getUserID());
            //TODO: get these from customerdao
            ChoiceBoxCustomerID.getItems().add(appointment.getCustomerID());
        }
    }

    private void populateAppointmentsTableView(){
        TableColumnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        TableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumnContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        TableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumnStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        TableColumnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        TableColumnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        TableColumnUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
            TableViewAppointments.setItems(allAppointments);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        ObservableList<Appointment> tableViewAppointments = TableViewAppointments.getItems();
        for(Appointment appointment : tableViewAppointments){
            appointment.setStart(appointment.getStartSystem());
            appointment.setEnd(appointment.getEndSystem());
        }
    }

    private void displayTableViewAppointmentsAll(){
        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
            TableViewAppointments.setItems(allAppointments);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        ObservableList<Appointment> tableViewAppointments = TableViewAppointments.getItems();
        for(Appointment appointment : tableViewAppointments){
            appointment.setStart(appointment.getStartSystem());
            appointment.setEnd(appointment.getEndSystem());
        }
    }
    private void displayTableViewAppointmentsMonth(){
        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
            TableViewAppointments.setItems(allAppointments);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        ObservableList<Appointment> tableViewAppointments = TableViewAppointments.getItems();
        for(Appointment appointment : tableViewAppointments){
            appointment.setStart(appointment.getStartSystem());
            appointment.setEnd(appointment.getEndSystem());
        }
    }
    private void displayTableViewAppointmentsWeek(){
        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
            ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
            for(Appointment appointment : allAppointments){
                if(appointment.getStart().isAfter(LocalDateTime.now().minusDays(7))){
                    weekAppointments.add(appointment);
                }

            }
            TableViewAppointments.getItems().clear();
            TableViewAppointments.setItems(weekAppointments);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        ObservableList<Appointment> tableViewAppointments = TableViewAppointments.getItems();
        for(Appointment appointment : tableViewAppointments){
            appointment.setStart(appointment.getStartSystem());
            appointment.setEnd(appointment.getEndSystem());
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

