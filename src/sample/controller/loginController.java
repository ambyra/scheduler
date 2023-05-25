package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;
import sample.dao.JDBC;

import java.io.IOException;
import java.sql.Connection;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;

public class loginController implements Initializable {
    @FXML private Button ButtonLogin;

    @FXML private PasswordField PasswordFieldPassword;
    @FXML private TextField TextFieldUserName;

    @FXML private Text TextLogin;
    @FXML private Text TextUserName;
    @FXML private Text TextPassword;
    @FXML private Text TextZoneID;
    @FXML private Text TextCurrentZone;

    private String passwordError;

    private static int CurrentUserID = 0;

    public static int getCurrentUserID() {
        return CurrentUserID;
    }

    public static void debugSetCurrentUserID(){
        CurrentUserID = 1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
        ZoneId timezone = ZoneId.systemDefault();
        TextCurrentZone.setText(timezone.toString());
    }

    @FXML
    void ClickLogin(){
        String user = TextFieldUserName.getText();
        String pass = PasswordFieldPassword.getText();

        String sqlquery = "select * from users where User_Name = '" + user +
                "' and Password = '" + pass + "'" ; //System.out.println(sqlquery);

        Connection connection = JDBC.getConnection();
        int userid = -1;
        try{
            JDBC.makePreparedStatement(sqlquery, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            rs.next();
            userid= rs.getInt("User_ID");
            System.out.println("User ID: " + userid);
            login(userid);
        }catch (SQLException | IOException throwables) { //throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, passwordError);
            alert.showAndWait();
        }

        CurrentUserID = userid;
    }

    private void setLanguage(){
        //Locale.setDefault(new Locale("Fr"));

        ResourceBundle resourceBundle = ResourceBundle.getBundle("languages", Locale.getDefault());

        TextLogin.setText((resourceBundle.getString("login")));
        TextUserName.setText((resourceBundle.getString("username")));
        TextPassword.setText((resourceBundle.getString("password")));
        TextZoneID.setText((resourceBundle.getString("zoneid")));

        ButtonLogin.setText((resourceBundle.getString("login")));

        passwordError = resourceBundle.getString("passworderror");
    }

    private void login(int userid) throws IOException {
        //TODO logger
        System.out.println("User " + userid + " logged in at TIME");

        Stage stage = Main.getStage();
        URL resource = getClass().getResource("/sample/view/appointment.fxml");
        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
