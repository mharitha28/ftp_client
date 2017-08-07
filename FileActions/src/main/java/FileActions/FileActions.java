package FileActions;
import java.io.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Scanner;



public class FileActions extends Application{
    private static Scanner scan;
    Button button;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        FPTClient object = new FPTClient();
        launch(args);

        System.out.println("Enter user name: ");
        String username = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();

        boolean result = object.Login(username, password);
        if(result){
            System.out.println("Connection exits succesfully.");
        } else{
            System.out.println("Connection fails.");
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/fxml/ftpclient.fxml"));
        Parent root = (Parent) loader1.load();

        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        //Users/MyMac/Desktop/CS410Agile/ftp_client/FileActions/src/main/resources/sample.fxml

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }
    //System.exit(1);
}

class FPTClient{
    private FTPClient ftpClient;

    FPTClient(){
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
}


