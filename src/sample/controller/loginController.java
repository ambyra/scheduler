package sample.controller;

import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextField;
        import javafx.scene.text.Text;

public class loginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonCancel;

    @FXML
    private Button ButtonLogin;

    @FXML
    private TextField TextFieldPassword;

    @FXML
    private TextField TextFieldUser;

    @FXML
    private Text TextTimeZone;

    @FXML
    void initialize() {
    }

}
