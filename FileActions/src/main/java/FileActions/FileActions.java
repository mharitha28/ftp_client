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

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 450));
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

    public boolean ChangePermissions(String fileName, String permissions) {
        /*
            Changes the permissions of the specified file. Expects a string
            of permission levels for the owner, the group and the world, e.g. '755'
            or another set of digits that work with the chmod command.

            https://www.linux.org/threads/file-permissions-chmod.4124/

            Requires the user to be in the directory where the file is located, or to provide relative path 
            to the file as filename.
         */
        try {
            boolean permissions_changed = ftpClient.sendSiteCommand("chmod " + permissions + " " + fileName);
            if (permissions_changed) {
                System.out.println("The file permissions were changed successfully to: " + permissions);
                return true;
            } else {
                System.out.println("Could not change permissions on the file.");
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}


