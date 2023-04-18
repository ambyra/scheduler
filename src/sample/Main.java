package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dao.JDBC;

import java.util.Locale;

public class Main extends Application {
    private static Stage mainStage;
    private static void setStage(Stage stage)
    {
        Main.mainStage = stage;
    }
    static public Stage getStage()
    {
        return Main.mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setStage(primaryStage);
        //DEBUG
        Parent root = FXMLLoader.load(getClass().getResource("view/appointment.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(new Scene(root));//(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
