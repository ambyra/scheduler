package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import sample.dao.AppointmentDAO;
import sample.model.Appointment;
import sample.model.MonthTotal;
import sample.model.TypeTotal;

public class reportTotalController{

    @FXML private TableView<MonthTotal> TableViewMonth;
    @FXML private TableColumn<?, ?> TableColumnMonth;
    @FXML private TableColumn<?, ?> TableColumnMonthTotal;

    @FXML private TableView<TypeTotal> TableViewType;
    @FXML private TableColumn<?, ?> TableColumnType;
    @FXML private TableColumn<?, ?> TableColumnTypeTotal;

    @FXML
    public void initialize() throws SQLException {
        TableColumnMonth.setCellValueFactory(new PropertyValueFactory<>("yearMonthString"));
        TableColumnMonthTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumnTypeTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        countMonths();
        countTypes();
    }

    void countMonths() throws SQLException {
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
        ObservableList<MonthTotal> monthTotals = FXCollections.observableArrayList();
        monthCounts.forEach((k, v) -> monthTotals.add(new MonthTotal(k, v.intValue())));
        TableViewMonth.setItems(monthTotals);
    }

    void countTypes() throws SQLException {
        //todo: implement
        ObservableList<Appointment> appointments = AppointmentDAO.getAppointments();
        if(appointments == null || appointments.isEmpty()){return;}

        List<String> allTypes = new ArrayList<>();
        for(Appointment appointment :appointments){
            allTypes.add(appointment.getType());
        }
        //lambda
        Map<String, Long> typeCounts =
            allTypes
                .stream()
                .collect(
                    Collectors.groupingBy(
                            s -> s,
                            Collectors.counting()
                    )
                );
        //lambda
        ObservableList<TypeTotal> typeTotals = FXCollections.observableArrayList();
        typeCounts.forEach((k, v) -> typeTotals.add(new TypeTotal(k, v.intValue())));
        TableViewType.setItems(typeTotals);
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
