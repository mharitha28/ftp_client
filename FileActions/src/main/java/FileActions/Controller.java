package FileActions;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
    public FPTClient ftpClient;

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

        //String address = addressText.getText();
        String user = usernameText.getText();
        String pass = passText.getText();


        // validate address & username, password


        // save login
        boolean checked = isChecked.isSelected();

        //TODO do something


        //System.out.println(address);
        System.out.println(user);
        System.out.println(pass);
        System.out.println(checked);

        if(ftpClient.Login(user, pass)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Connection Info");
                alert.setContentText("Connection has been established");

                alert.showAndWait();
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Connection Info");
            alert.setContentText("Connection has been fail");

            alert.showAndWait();
        }
    }

    public void setFtpClient(FPTClient ftpClient){
        this.ftpClient = ftpClient;
    }

}