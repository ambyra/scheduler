package sample.controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
        setLanguage();

        ZoneId timezone = ZoneId.systemDefault();
        TextCurrentZone.setText(timezone.toString());
    }

    @FXML
    void ClickLogin(ActionEvent event) {
        System.out.println("ok");
    }
    @FXML
    void ClickCancel(ActionEvent event) {
        Platform.exit();
    }

    private void setLanguage(){
        //Locale.setDefault(new Locale("Fr"));

        ResourceBundle resourceBundle = ResourceBundle.getBundle("languages", Locale.getDefault());

        TextLogin.setText((resourceBundle.getString("login")));
        TextUserName.setText((resourceBundle.getString("username")));
        TextPassword.setText((resourceBundle.getString("password")));
        TextZoneID.setText((resourceBundle.getString("zoneid")));

        ButtonLogin.setText((resourceBundle.getString("login")));
        ButtonCancel.setText((resourceBundle.getString("cancel")));
    }
}
