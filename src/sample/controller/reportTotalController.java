package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.AppointmentDAO;
import sample.model.Appointment;
import sample.model.MonthTotal;
import sample.model.TypeTotal;


public class reportTotalController implements Initializable {

    @FXML private TableView<MonthTotal> TableViewMonth;
    @FXML private TableColumn<?, ?> TableColumnMonth;
    @FXML private TableColumn<?, ?> TableColumnMonthTotal;

    @FXML private TableView<TypeTotal> TableViewType;
    @FXML private TableColumn<?, ?> TableColumnType;
    @FXML private TableColumn<?, ?> TableColumnTypeTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumnMonth.setCellValueFactory(new PropertyValueFactory<>("yearMonthString"));
        TableColumnMonthTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumnTypeTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            countMonths();
            countTypes();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    void countMonths() throws SQLException {
        ObservableList<MonthTotal> monthTotals = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        if(appointments == null || appointments.isEmpty()){return;}

        List<YearMonth> allYearMonths = new ArrayList<>();
        for(Appointment appointment :appointments){
            ZonedDateTime start = appointment.getStartUTC();
            allYearMonths.add(YearMonth.of(start.getYear(), start.getMonth()));
        }

        Map<YearMonth, Long> monthCounts =
            allYearMonths
                .stream()
                .collect(
                    Collectors.groupingBy(
                        YearMonth :: from ,
                        Collectors.counting()
                    )
                );

        //lambda
        monthCounts.forEach((k, v) -> monthTotals.add(new MonthTotal(k, v.intValue())));
        TableViewMonth.setItems(monthTotals);
    }

    void countTypes(){
        //todo: implement
        ObservableList<TypeTotal> typeTotals = FXCollections.observableArrayList();
        typeTotals.add(new TypeTotal("example"));
        TableViewType.setItems(typeTotals);
    }

    @FXML
    void ClickReturn(ActionEvent event) throws IOException {
        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/appointment.fxml");
        assert resource != null;
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
