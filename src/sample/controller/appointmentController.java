package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.dao.AutoTableView;

import java.net.URL;
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
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

    @FXML private Button ButtonAdd;
    @FXML private Button ButtonEdit;
    @FXML private Button ButtonSave;
    @FXML private Button ButtonCancel;
    @FXML private Button ButtonDelete;


    private ObservableList<ObservableList> data;

    void selectState(){
        TableViewAppointments.setDisable(false);
        clearBoxes();
        disableButtons();
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
    }

    void disableButtons(){
        ButtonAdd.setDisable(true);
        ButtonEdit.setDisable(true);
        ButtonSave.setDisable(true);
        ButtonCancel.setDisable(true);
        ButtonDelete.setDisable(true);
    }

    @FXML
    void ClickAdd (ActionEvent event){
        addState();
    }

    @FXML
    void ClickDelete (ActionEvent event){
        selectState();
    }

    @FXML
    void ClickCancel (ActionEvent event){
        selectState();
    }

    @FXML
    void ClickSave (ActionEvent event){
        selectState();
    }

    @FXML
    void ClickEdit(ActionEvent event) {
        editState();
        ObservableList<Object> selectedItem= TableViewAppointments.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        if (selectedItem == null){return;}

        TextFieldAppointmentID.setText((String) selectedItem.get(0));
        TextFieldTitle.setText((String) selectedItem.get(1));
        TextFieldDescription.setText((String) selectedItem.get(2));
        TextFieldLocation.setText((String) selectedItem.get(3));
        TextFieldType.setText((String) selectedItem.get(5));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectState();

        AutoTableView autoTableAppointments = new AutoTableView(TableViewAppointments);
        String query =
            "select appointment_id, title, description, location, contact_id, type, start, end, customer_id, user_id" +
            " from client_schedule.appointments";
        autoTableAppointments.loadTableFromDB(query);

        TableViewAppointments.getSelectionModel().selectFirst();

    }
}
