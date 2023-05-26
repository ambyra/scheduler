package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import sample.dao.UserDAO;
import sample.model.User;

public class loginController{
    @FXML private Button ButtonLogin;
    @FXML private PasswordField PasswordFieldPassword;
    @FXML private TextField TextFieldUserName;

    @FXML private Text TextLogin;
    @FXML private Text TextUserName;
    @FXML private Text TextPassword;
    @FXML private Text TextZoneID;
    @FXML private Text TextCurrentZone;

    private static User currentUser;
    private String passwordError;

    private PrintWriter printWriter;

    public static User getCurrentUser(){return currentUser;}

    /**
     * initialize login form
     * @throws IOException
     */

    @FXML
    public void initialize() throws IOException {
        setLanguage();

        ZoneId timezone = ZoneId.systemDefault();
        TextCurrentZone.setText(timezone.toString());

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        printWriter = new PrintWriter(fileWriter);
        Main.getStage().setOnHiding( event -> {printWriter.close();} );
    }

    /**
     * check username and password
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void ClickLogin() throws SQLException, IOException {
        String user = TextFieldUserName.getText();
        String pass = PasswordFieldPassword.getText();

        if(user.isEmpty() || pass.isEmpty()){
            sendAlert(passwordError);
            return;
        }

        currentUser = UserDAO.login(user, pass);
        if(currentUser == null){
            sendAlert(passwordError);
            logMessage("[Username: " + user + "] failed login attempt");
        }else{
            logMessage("[Username: " + user + "] successful login");
            printWriter.close();
            login();
        }
    }

    /**
     * set form language
     */

    private void setLanguage(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("languages", Locale.getDefault());

        TextLogin.setText((resourceBundle.getString("login")));
        TextUserName.setText((resourceBundle.getString("username")));
        TextPassword.setText((resourceBundle.getString("password")));
        TextZoneID.setText((resourceBundle.getString("zoneid")));
        ButtonLogin.setText((resourceBundle.getString("login")));
        passwordError = resourceBundle.getString("passworderror");
    }

    /**
     * load appointments upon successful login
     * @throws IOException
     */

    private void login() throws IOException {
        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/appointment.fxml");
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * helper function
     * @param alertMessage
     */

    private void sendAlert(String alertMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert!");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    /**
     * log form activity
     * @param message
     */

    private void logMessage(String message){
        //user log-in attempts, dates, and time stamps
        String timestamp= "[" + Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()) + "] ";
        printWriter.print(timestamp + message + "\n");
    }
}
