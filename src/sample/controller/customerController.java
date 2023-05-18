package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dao.CustomerDAO;
import sample.dao.UserDAO;
import sample.model.Customer;
import sample.model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class customerController implements Initializable {
    @FXML private Button ButtonAdd;
    @FXML private Button ButtonEdit;
    @FXML private Button ButtonSave;
    @FXML private Button ButtonCancel;
    @FXML private Button ButtonDelete;
    @FXML private Button ButtonAppointments;

    @FXML private ComboBox<Integer> ComboBoxCountry;
    @FXML private ComboBox<Integer> ComboBoxFirstLevelDivision;

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

    void setBoxes(Customer customer){
        TextFieldCustomerId.setText(String.valueOf(customer.getCustomerID()));
        TextFieldName.setText(customer.getCustomerName());
        TextFieldPhone.setText(customer.getPhone());
        //TODO ComboBoxCountry.set();
        //TODO ComboBoxFirstLevelDivision.set();
        TextFieldAddress.setText(customer.getAddress());
        TextFieldPostalCode.setText(customer.getPostalCode());
    }

    private Customer getCustomerFromBoxes() throws SQLException {
        int customerId;
        try{
            customerId = Integer.parseInt(TextFieldCustomerId.getText());
        }catch(NullPointerException e){
            sendAlert("ID Field cannot be empty");
            return null;
        }

        String customerName = TextFieldName.getText();
        if(customerName.isEmpty()){
            sendAlert("Customer name cannot be empty");
            return null;
        }

        String address = TextFieldAddress.getText();
        if(address.isEmpty()){
            sendAlert("Address cannot be empty");
            return null;
        }

        String postalCode = TextFieldPostalCode.getText();
        if(postalCode.isEmpty()){
            sendAlert("Postal code cannot be empty");
            return null;
        }

        String phone = TextFieldPhone.getText();
        if(phone.isEmpty()){
            sendAlert("Phone number cannot be empty");
            return null;
        }

        User currentUser = UserDAO.getCurrentUser();

        ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("UTC"));

        String createdBy = currentUser.getUserName();

        Customer oldCustomer = CustomerDAO.getCustomer(customerId);
        if(oldCustomer != null){
            createDate  = oldCustomer.getCreateDate();
            createdBy = oldCustomer.getCreatedBy();
        }

        ZonedDateTime lastUpdate = ZonedDateTime.now(ZoneId.of("UTC"));

        String lastUpdatedBy = currentUser.getLastUpdatedBy();

        int divisionId;
        try{
            divisionId = ComboBoxFirstLevelDivision.getValue();
        }catch(NullPointerException e){
            sendAlert("State/Province cannot be empty");
            return null;
        }

        return new Customer(customerId, customerName, address, postalCode, phone,
                createDate,createdBy, lastUpdate, lastUpdatedBy, divisionId);
    }

    private Customer getCustomerFromSelection(){
        return TableViewCustomers.getSelectionModel().getSelectedItem();
    }

    @FXML
    void ClickAdd() throws SQLException {
        clearBoxes();
        setBoxesEnabled(true);
        //TODO: setComboBoxOptions();

        TableViewCustomers.setDisable(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);

        int newCustomerID = CustomerDAO.newCustomerID();
        TextFieldCustomerId.setText(String.valueOf(newCustomerID));
    }

    @FXML
    void ClickEdit() {
        Customer selectedCustomer = getCustomerFromSelection();
        if(selectedCustomer == null){return;}

        TableViewCustomers.setDisable(true);
        clearBoxes();
        setBoxesEnabled(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);
        setBoxes(selectedCustomer);
    }

    @FXML
    void ClickSave() throws SQLException {
        Customer customer = getCustomerFromBoxes();
        if(customer != null){
            CustomerDAO.updateCustomer(customer);
            selectState();
        }
    }

    @FXML
    void ClickCancel() throws SQLException {
        selectState();
    }

    @FXML
    void ClickDelete() {
        //TODO: CustomerDAO.delete(getCustomerFromSelection());
    }

    @FXML
    void ClickAppointments() {
        //TODO: load appointment form
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

    private void sendAlert(String alertMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert!");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

}