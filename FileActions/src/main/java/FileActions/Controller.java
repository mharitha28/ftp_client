package FileActions;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import org.apache.commons.net.ftp.FTPFile;

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
    public void handleButtonAction(ActionEvent event) throws Exception {

        String address = addressText.getText();
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

        if(ftpClient.Login(address, user, pass)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Connection Info");
            alert.setContentText("Connection has been established");
            alert.showAndWait();



            Stage stage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ftpclientMainPage.fxml"));
            stage = (Stage) connectButton.getScene().getWindow();


            ControllerMainPage controller = loader.<ControllerMainPage>getController();

            TreeItem<String> rootItem = new TreeItem<String> ("FTP Server");
            rootItem.setExpanded(true);
            String path = "";
            FTPFile [] files = ftpClient.listFilesOnCurrentDirectory(path);
            for (FTPFile file : files){
                if(file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")){
                    System.out.println(file.getName());
                    path = path + "/" + file.getName();
                    findFiles(file, path, rootItem);
                }
                else{
                    if(!file.getName().equals(".") && !file.getName().equals("..")) {
                        TreeItem<String> item = new TreeItem<String>(file.getName());
                        rootItem.getChildren().add(item);
                    }
                }
            }

            TreeView<String> tree = new TreeView<String> (rootItem);
            StackPane root = new StackPane();

            root.getChildren().add(tree);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

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

    private void findFiles(FTPFile file, String path, TreeItem<String> parent) {
        TreeItem<String> rootItem = new TreeItem<String> (file.getName());
        rootItem.setExpanded(true);
        FTPFile [] FTPfiles = ftpClient.listFilesOnCurrentDirectory(path);
        for (FTPFile FTPfile : FTPfiles){
            if(FTPfile.isDirectory()&& !FTPfile.getName().equals(".") && !FTPfile.getName().equals("..")){
                path = path + "/" + FTPfile.getName();
                findFiles(FTPfile, path, rootItem);
            }
            else{
                if(!FTPfile.getName().equals(".") && !FTPfile.getName().equals("..")) {
                    TreeItem<String> item = new TreeItem<String>(FTPfile.getName());
                    rootItem.getChildren().add(item);
                }
            }
        }
        parent.getChildren().add(rootItem);

    }
}