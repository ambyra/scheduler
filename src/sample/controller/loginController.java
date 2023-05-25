package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
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

    public static User getCurrentUser(){return currentUser;}

    @FXML
    public void initialize(){
        setLanguage();
        ZoneId timezone = ZoneId.systemDefault();
        TextCurrentZone.setText(timezone.toString());
    }

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
        }else{
            login();
        }
    }

    private void setLanguage(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("languages", Locale.getDefault());

        TextLogin.setText((resourceBundle.getString("login")));
        TextUserName.setText((resourceBundle.getString("username")));
        TextPassword.setText((resourceBundle.getString("password")));
        TextZoneID.setText((resourceBundle.getString("zoneid")));
        ButtonLogin.setText((resourceBundle.getString("login")));
        passwordError = resourceBundle.getString("passworderror");
    }

    private void login() throws IOException {
        //TODO logger
        System.out.println("User " + currentUser.getUserID()+ " logged in at TIME");

        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/appointment.fxml");
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void sendAlert(String alertMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert!");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}
