package sample.controller;

import sample.dao.JDBC;
import java.sql.Connection;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
        ZoneId timezone = ZoneId.systemDefault();
        TextCurrentZone.setText(timezone.toString());
    }

    @FXML
    void ClickLogin(ActionEvent event){
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
        }catch (SQLException throwables) { //throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, passwordError);
            alert.showAndWait();
        }
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
}
