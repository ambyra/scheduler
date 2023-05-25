package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.*;
import sample.model.TypeTotal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class reportAdditionalController {
    @FXML private TableColumn<?, ?> TableColumnDatabase;
    @FXML private TableColumn<?, ?> TableColumnEntries;
    @FXML private TableView<TypeTotal> TableViewDatabase;

    @FXML void initialize() throws SQLException {
        TableColumnDatabase.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumnEntries.setCellValueFactory(new PropertyValueFactory<>("total"));

        countDBEntries();
    }

    private void countDBEntries() throws SQLException {
        int appointmentSize = AppointmentDAO.getAppointments().size();
        int contactSize = ContactDAO.getContacts().size();
        int countrySize = CountryDAO.getCountries().size();
        int customerSize = CustomerDAO.getCustomers().size();
        int divisionSize = DivisionDAO.getDivisions().size();
        int userSize = UserDAO.getUsers().size();

        ObservableList<TypeTotal> dbEntries = FXCollections.observableArrayList();

        dbEntries.add(new TypeTotal("Appointments", appointmentSize));
        dbEntries.add(new TypeTotal("Contacts", contactSize));
        dbEntries.add(new TypeTotal("Countries", countrySize));
        dbEntries.add(new TypeTotal("Customers", customerSize));
        dbEntries.add(new TypeTotal("Divisions", divisionSize));
        dbEntries.add(new TypeTotal("Users", userSize));

        TableViewDatabase.setItems(dbEntries);
    }

    @FXML
    void ClickReturn() throws IOException {
        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/appointment.fxml");
        assert resource != null;
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
