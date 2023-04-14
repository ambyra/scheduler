package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
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
        @FXML private TableView<?> TableViewAppointments;
        @FXML private TextField TextFieldAppointmentID;
        @FXML private TextField TextFieldDescription;
        @FXML private TextField TextFieldEndTime;
        @FXML private TextField TextFieldLocation;
        @FXML private TextField TextFieldStartTime;
        @FXML private TextField TextFieldTitle;
        @FXML private TextField TextFieldType;

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
    }
}
