package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;


public class Controller   {

    @FXML
    public Button connectButton;

    @FXML
    public TextField addressText, usernameText;

    @FXML
    public PasswordField passText;

    @FXML
    public CheckBox isChecked;

    // location and resources will be automatically injected by the FXML loader
    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public Controller(){}

    @FXML
    private void initialize() {

    }

    @FXML
    public void handleButtonAction(ActionEvent event) {

    }
}
