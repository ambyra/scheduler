package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dao.AppointmentDAO;
import sample.dao.CustomerDAO;
import sample.model.Appointment;
import sample.model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class customerController implements Initializable {
    @FXML private Button ButtonAdd;
    @FXML private Button ButtonEdit;
    @FXML private Button ButtonSave;
    @FXML private Button ButtonCancel;
    @FXML private Button ButtonDelete;
    @FXML private Button ButtonAppointments;

    @FXML private ComboBox<?> ComboBoxCountry;
    @FXML private ComboBox<?> ComboBoxFirstLevelDivision;

    @FXML private TableView<Customer> TableViewCustomers;
    @FXML private TableColumn<?, ?> TableColumnAddress;
    @FXML private TableColumn<?, ?> TableColumnCustomerId;
    @FXML private TableColumn<?, ?> TableColumnName;
    @FXML private TableColumn<?, ?> TableColumnPhone;
    @FXML private TableColumn<?, ?> TableColumnPostalCode;
    @FXML private TableColumn<?, ?> TableColumnStateProvince;

    @FXML private TextField TextFieldCustomerId;
    @FXML private TextField TextFieldName;
    @FXML private TextField TextFieldAddress;
    @FXML private TextField TextFieldPhone;
    @FXML private TextField TextFieldPostalCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColumnsTableViewCustomers();
        try {
            selectState();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    void displayTableViewCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = CustomerDAO.getCustomers();
        TableViewCustomers.getItems().clear();
        TableViewCustomers.setItems(allCustomers);
    }

    void setColumnsTableViewCustomers(){
        TableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        TableColumnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumnPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        TableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumnStateProvince.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    void setBoxesEnabled(boolean areEnabled){
        areEnabled = !areEnabled;

        TextFieldCustomerId.setDisable(areEnabled);
        TextFieldName.setDisable(areEnabled);
        TextFieldAddress.setDisable(areEnabled);
        TextFieldPostalCode.setDisable(areEnabled);
        TextFieldPhone.setDisable(areEnabled);

        ComboBoxCountry.setDisable(areEnabled);
        ComboBoxFirstLevelDivision.setDisable(areEnabled);
    }

    void setButtonsEnabled(boolean areEnabled){
        areEnabled = !areEnabled;
        ButtonAdd.setDisable(areEnabled);
        ButtonEdit.setDisable(areEnabled);
        ButtonSave.setDisable(areEnabled);
        ButtonCancel.setDisable(areEnabled);
        ButtonDelete.setDisable(areEnabled);
        ButtonAppointments.setDisable(areEnabled);
    }

    void clearBoxes(){
        TextFieldCustomerId.clear();
        TextFieldName.clear();
        TextFieldAddress.clear();
        TextFieldPostalCode.clear();
        TextFieldPhone.clear();

        ComboBoxCountry.getItems().clear();
        ComboBoxFirstLevelDivision.getItems().clear();
    }

    void setComboBoxes(){

    }

    @FXML
    void ClickAdd(ActionEvent event) throws SQLException {
        clearBoxes();
        setBoxesEnabled(true);
        //TODO: populateChoiceBoxes();

        TableViewCustomers.setDisable(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);

        int newCustomerID = CustomerDAO.newCustomerID();
        TextFieldCustomerId.setText(String.valueOf(newCustomerID));

    }

    @FXML
    void ClickEdit(ActionEvent event) {
        //TODO: Customer selectedCustomer = getCustomerFromSelection();
        //if(selectedCustomer == null){return;}

        TableViewCustomers.setDisable(true);
        clearBoxes();
        setBoxesEnabled(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);
        //setBoxes(selectedCustomer);

    }

    @FXML
    void ClickSave(ActionEvent event) throws SQLException {
        //TODO: Customer customer = parseInput();
//        if(customer != null){
//            CustomerDAO.updateCustomer(customer);
//            //exit state
//            selectState();
//        }
    }


    @FXML
    void ClickCancel(ActionEvent event) throws SQLException {
        selectState();
    }

    @FXML
    void ClickDelete(ActionEvent event) {
        //TODO: CustomerDAO.delete(getCustomerFromSelection());
    }


    @FXML
    void ClickAppointments(ActionEvent event) {
    }

    void selectState() throws SQLException {
        displayTableViewCustomers();
        TableViewCustomers.setDisable(false);

        clearBoxes();
        setBoxesEnabled(false);

        setButtonsEnabled(false);
        ButtonAdd.setDisable(false);
        ButtonEdit.setDisable(false);
        ButtonDelete.setDisable(false);
    }

    void editState(){
       //TODO: Customer selectedCustomer = getCustomerFromSelection();
       // if(selectedCustomer == null){return;}

        TableViewCustomers.setDisable(true);
        clearBoxes();
        setBoxesEnabled(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);
        //setBoxes(selectedCustomer);
    }

}