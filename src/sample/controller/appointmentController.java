package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.dao.AutoTableView;
import sample.dao.JDBC;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
    @FXML private Button ButtonAdd;
    @FXML private Button ButtonDelete;
    @FXML private Button ButtonUpdate;
    @FXML private ChoiceBox<?> ChoiceBoxContact;
    @FXML private ChoiceBox<?> ChoiceBoxCustomerID;
    @FXML private ChoiceBox<?> ChoiceBoxUserID;
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

    private ObservableList<ObservableList> data;

    @FXML
    void ClickAdd (ActionEvent event){
    }

    @FXML
    void ClickDelete (ActionEvent event){
    }

    @FXML
    void ClickUpdate (ActionEvent event){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AutoTableView autoTableAppointments = new AutoTableView(TableViewAppointments);
        String query =
            "select appointment_id, title, description, location, contact_id, type, start, end, customer_id, user_id" +
            " from client_schedule.appointments";
        autoTableAppointments.loadTableFromDB(query);
    }
}
