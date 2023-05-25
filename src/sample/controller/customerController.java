package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.AppointmentDAO;
import sample.dao.CustomerDAO;
import sample.dao.DivisionDAO;
import sample.dao.UserDAO;
import sample.model.Customer;
import sample.model.Division;
import sample.model.User;

import java.io.IOException;
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

    @FXML private ComboBox<String> ComboBoxCountry;
    @FXML private ComboBox<String> ComboBoxFirstLevelDivision;

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

    void displayTableViewCustomers(){
        ObservableList<Customer> allCustomers = CustomerDAO.getCustomers();
        TableViewCustomers.setItems(allCustomers);
    }

    void setColumnsTableViewCustomers(){
        TableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        TableColumnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumnPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        TableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumnStateProvince.setCellValueFactory(new PropertyValueFactory<>("division"));
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

        ComboBoxCountry.getSelectionModel().select("");
        ComboBoxFirstLevelDivision.getSelectionModel().select("");
    }

    void setBoxes(Customer customer){
        TextFieldCustomerId.setText(String.valueOf(customer.getCustomerID()));
        TextFieldName.setText(customer.getCustomerName());
        TextFieldPhone.setText(customer.getPhone());
        TextFieldAddress.setText(customer.getAddress());
        TextFieldPostalCode.setText(customer.getPostalCode());
    }

    void setComboBoxCountry(Division division){
        setComboBoxCountry();
        int countryIndex = division.getCountryID() - 1;
        ComboBoxCountry.getSelectionModel().select(countryIndex);
    }

    void setComboBoxCountry(){
        ObservableList<String> countries = FXCollections.observableArrayList();
        countries.add("United States");
        countries.add("United Kingdom");
        countries.add("Canada");
        ComboBoxCountry.setItems(countries);
    }

    void setComboBoxDivision(Division division) throws SQLException {
        if(division == null){return;}
        setComboBoxDivision();
        ComboBoxFirstLevelDivision.getSelectionModel().select(division.getDivision());
    }

    void setComboBoxDivision() throws SQLException {
        int countryIndex = ComboBoxCountry.getSelectionModel().getSelectedIndex() + 1;
        ObservableList<String> divisionNames = DivisionDAO.getDivisionsFromCountryID(countryIndex);
        ComboBoxFirstLevelDivision.setItems(divisionNames);
    }

    @FXML
    void ActionCountry() throws SQLException {
        setComboBoxDivision();
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

        User currentUser = loginController.getCurrentUser();
        if(currentUser == null){
            sendAlert("No user logged in");
            return null;
        }

        ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("UTC"));

        assert currentUser != null;
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
            String divisionName = ComboBoxFirstLevelDivision.getValue();
            Division division = DivisionDAO.getDivision(divisionName);
            if(division == null){
                sendAlert("State/Province not found");
            }
            divisionId = division.getDivisionID();
            if(divisionId == 0){
                sendAlert("State/Province not found");
                return null;
            }
        }
        catch(NullPointerException e){
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
    void ClickAdd(){
        clearBoxes();
        setBoxesEnabled(true);
        setComboBoxCountry();

        TableViewCustomers.setDisable(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);

        int newCustomerID = CustomerDAO.newCustomerID();
        TextFieldCustomerId.setText(String.valueOf(newCustomerID));
    }

    @FXML
    void ClickEdit() throws SQLException {
        Customer selectedCustomer = getCustomerFromSelection();
        if(selectedCustomer == null){return;}

        TableViewCustomers.setDisable(true);
        clearBoxes();
        setBoxesEnabled(true);
        setButtonsEnabled(false);
        ButtonSave.setDisable(false);
        ButtonCancel.setDisable(false);
        setBoxes(selectedCustomer);

        Division division = DivisionDAO.getDivision(selectedCustomer.getDivisionID());
        if(division != null){
            setComboBoxCountry(division);
            setComboBoxDivision(division);
        }
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
    void ClickDelete() throws SQLException {
        Customer selectedCustomer = getCustomerFromSelection();
        if(selectedCustomer == null){
            sendAlert("No customer selected");
        }else{
            AppointmentDAO.deleteAppointments(selectedCustomer);
            CustomerDAO.deleteCustomer(getCustomerFromSelection());
            sendAlert("Customer ID#" + selectedCustomer.getCustomerID() +
                    " and associated appointments deleted.");
        }
        selectState();
    }

    @FXML
    void ClickAppointments() throws IOException {
        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/appointment.fxml");
        assert resource != null;
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
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
        ButtonAppointments.setDisable(false);
    }

    private void sendAlert(String alertMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert!");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}