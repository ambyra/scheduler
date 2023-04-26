package sample.controller;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.dao.AppointmentDAO;
import sample.dao.AutoTableView;
import sample.model.Appointment;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class appointmentController implements Initializable {
    @FXML private ChoiceBox<Integer> ChoiceBoxContactID;
    @FXML private ChoiceBox<Integer> ChoiceBoxCustomerID;
    @FXML private ChoiceBox<Integer> ChoiceBoxUserID;

    @FXML private DatePicker DatePickerEndDate;
    @FXML private DatePicker DatePickerStartDate;

    @FXML private ToggleGroup RadioGroupAppointments;

    @FXML private TableView<ObservableList> TableViewAppointments;

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
        selectState();
        populateTableView();
        try {populateChoiceBoxes(); }
        catch (SQLException sqlException) { sqlException.printStackTrace();}

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                TextFieldLocalDateTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                TextFieldLocalTimeZone.setText(ZoneId.systemDefault().toString());

                TextFieldUTCDateTime.setText(ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                TextFieldUTCDateTime.setText(ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            }
        };
        timer.start();
    }

    @FXML
    void ClickAdd (ActionEvent event){
        addState();
    }

    @FXML
    void ClickEdit(ActionEvent event) throws SQLException {
        editState();
        setBoxes(getAppointmentFromSelection());
    }

    @FXML
    void ClickSave (ActionEvent event){
        selectState();
        //TODO: NEXT: dao not tested
    }

    @FXML
    void ClickCancel (ActionEvent event){
        selectState();
    }

    @FXML
    void ClickDelete (ActionEvent event){
        selectState();
    }

    void selectState(){
        TableViewAppointments.setDisable(false);
        TextFieldAppointmentID.setDisable(true);
        clearBoxes();
        disableButtons();
        disableBoxes();
        ButtonAdd.setDisable(false);
        ButtonEdit.setDisable(false);
        ButtonDelete.setDisable(false);
    }
    void addState(){
        TableViewAppointments.setDisable(true);
        disableButtons();
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

    private void setBoxes(Appointment appointment){
        TextFieldAppointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        TextFieldTitle.setText(appointment.getTitle());
        TextFieldDescription.setText(appointment.getDescription());
        TextFieldLocation.setText(appointment.getLocation());
        ChoiceBoxContactID.setValue(appointment.getContactID());
        TextFieldType.setText(appointment.getType());
        //TODO: convert UTC to local time
        TextFieldStartTime.setText(appointment.getStart().toLocalTime().toString());
        DatePickerStartDate.setValue(appointment.getStart().toLocalDate());
        TextFieldEndTime.setText(appointment.getEnd().toLocalTime().toString());
        DatePickerEndDate.setValue(appointment.getEnd().toLocalDate());
        ChoiceBoxCustomerID.setValue(appointment.getCustomerID());
        ChoiceBoxUserID.setValue(appointment.getUserID());
    }

    private Appointment getAppointmentFromSelection() throws SQLException {
        Appointment selectedAppointment = null;

        ObservableList selectedItem = TableViewAppointments.getSelectionModel().getSelectedItem();
        if (selectedItem == null){return null;}
        String selectedID = selectedItem.get(0).toString();
        int selectedAppointmentID = Integer.parseInt(selectedID);

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();

        for (Appointment appointment: allAppointments) {
            if (selectedAppointmentID == appointment.getAppointmentID()) {
                selectedAppointment = appointment;
            }
        }
        if (selectedAppointment == null){
            System.out.println("no appointment found");
            return null;
        }
        return selectedAppointment;
    }

    private void populateChoiceBoxes() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointments();
        for (Appointment appointment: allAppointments) {
            ChoiceBoxContactID.getItems().add(appointment.getContactID());
            ChoiceBoxUserID.getItems().add(appointment.getUserID());
            ChoiceBoxCustomerID.getItems().add(appointment.getCustomerID());
        }
    }

    private void populateTableView(){
        AutoTableView autoTableAppointments = new AutoTableView(TableViewAppointments);
        String query =
                "select appointment_id, title, description, location, contact_id, type, start, end, customer_id, user_id" +
                        " from client_schedule.appointments";
        autoTableAppointments.loadTableFromDB(query);
        TableViewAppointments.getSelectionModel().selectFirst();
    }

}

