import java.io.*;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

class FileActions {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("127.0.0.1"); //LOCAL HOST
            System.out.println("Enter user name: ");
            String username = scan.nextLine();
            System.out.println("Enter password: ");
            String password = scan.nextLine();
            boolean login = ftpClient.login(username, password);
            if (login) {
                System.out.println("Connection established...");
                System.out.println("Status: "+ftpClient.getStatus());


                // logout the user, returned true if logout successfully
                boolean logout = ftpClient.logout();
                if (logout) {
                    System.out.println("Connection close...");
                }
            } else {
                System.out.println("Connection fail...");
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    System.exit(1);
    }
}