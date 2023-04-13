package sample.controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class loginController implements Initializable {
    @FXML private Button ButtonCancel;
    @FXML private Button ButtonLogin;

    @FXML private TextField TextFieldPassword;
    @FXML private TextField TextFieldUserName;


    @FXML private Text TextLogin;
    @FXML private Text TextUserName;
    @FXML private Text TextPassword;
    @FXML private Text TextZoneID;
    @FXML private Text TextCurrentZone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourceBundle = ResourceBundle.getBundle("languages_FR");
        //resourceBundle = ResourceBundle.getBundle("languages_en_US");

        TextLogin.setText((resourceBundle.getString("login")));
        TextUserName.setText((resourceBundle.getString("username")));
        TextPassword.setText((resourceBundle.getString("password")));
        TextZoneID.setText((resourceBundle.getString("zoneid")));

        ButtonLogin.setText((resourceBundle.getString("login")));
        ButtonCancel.setText((resourceBundle.getString("cancel")));

    }
}
