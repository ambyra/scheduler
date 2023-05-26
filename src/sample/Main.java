package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dao.JDBC;

/**
 * @author Adam Byra
 * WGU C195
 * Submitted 5/26/2023
 */

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

    /**
     * loads login form
     * @param primaryStage
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * makes db connection
     * @param args
     */

    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
