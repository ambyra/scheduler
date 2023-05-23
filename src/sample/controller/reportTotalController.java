package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.YearMonth;
import java.util.ResourceBundle;
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

class MonthTotal{
    private YearMonth yearMonth;
    private int total;

    public YearMonth getYearMonth() {return yearMonth;}
    public int getTotal(){return total;}
    public void setTotal(int total){this.total = total;}

    public MonthTotal(YearMonth yearMonth){
        this.yearMonth = yearMonth;
        this.total = 0;
    }
}
class TypeTotal{
    private String type;
    private int total;

    public String getType() {return type;}
    public int getTotal(){return total;}
    public void setTotal(int total){this.total = total;}

    public TypeTotal(String type){
        this.type = type;
        this.total = 0;
    }
}

public class reportTotalController implements Initializable {

    @FXML private TableView<MonthTotal> TableViewMonth;
    @FXML private TableColumn<?, ?> TableColumnMonth;
    @FXML private TableColumn<?, ?> TableColumnMonthTotal;

    @FXML private TableView<TypeTotal> TableViewType;
    @FXML private TableColumn<?, ?> TableColumnType;
    @FXML private TableColumn<?, ?> TableColumnTypeTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumnMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        TableColumnMonthTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumnTypeTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

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
