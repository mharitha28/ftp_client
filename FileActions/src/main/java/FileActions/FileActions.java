package FileActions;
import java.io.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Scanner;



public class FileActions extends Application{
    private static Scanner scan;
    private static FPTClient fptClient;
    @FXML
    public TextField addressText, usernameText;

    @FXML
    public PasswordField passText;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        fptClient = new FPTClient();
        launch(args);

        System.out.println("Enter user name: ");
        String username = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();

        boolean result = fptClient.Login(username, password);
        if(result){
            System.out.println("Connection exits succesfully.");
        } else{
            System.out.println("Connection fails.");
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ftpclient.fxml"));
        Parent root = (Parent) loader.load();

        Controller controller = loader.<Controller>getController();
        controller.setFtpClient(fptClient);

        primaryStage.setTitle("FTP Client");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
        Button button = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        button.setText("Rename File");
        button2.setText("Delete File");
        button3.setText("Logout File");
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        layout.getChildren().add(button2);
        layout.getChildren().add(button3);

        Scene scene = new Scene(layout,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    //System.exit(1);
}

class FPTClient{
    private FTPClient ftpClient;

    public FPTClient(){
        ftpClient = new FTPClient();
    }

    public boolean Login(String username, String password) {
        try {
            ftpClient.connect("ftp.swfwmd.state.fl.us",21);
            boolean login = ftpClient.login(username, password);
            if (login) {
                System.out.println("Connection established...");
                System.out.println("Status: " + ftpClient.getStatus());
                return true;
            } else {
                return false;
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean Logout() {
        try {
            boolean logout = ftpClient.logout();
            if (logout) {
                System.out.println("Succesfully logged out.");
                return true;
            } else {
                System.out.println("Logout was unsuccessful.");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect(); // disconnect on logout
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean Rename(String oldName, String newName){
        try {
            boolean rename = ftpClient.rename(oldName,newName);
            if(rename) {
                System.out.println(oldName + " has been renamed to " + newName);
                return true;
            } else {
                System.out.println("Failed to rename: " + oldName);
                return false;
            }
        } catch (SocketException ex){
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean DeleteFile(String fileName) {
        try {
            boolean deleted = ftpClient.deleteFile(fileName);
            if (deleted) {
                System.out.println("The file was deleted successfully.");
                return true;
            } else {
                System.out.println("Could not delete the file.");
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean DeleteDirectory(String directory) {
        try {
            boolean deleted = ftpClient.removeDirectory(directory);
            if(deleted) {
                System.out.println("The directory was deleted successfully!");
                return true;
            } else {
                System.out.println("Could not delete the directory!");
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}


