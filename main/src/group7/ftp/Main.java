package group7.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class Main {

    /**
     * Method takes in four args for the source and destination of the file name args;
     * Looks it up on the client side and returns true or false for success
     * If found, instant write to destination path
     * All input param are assumed to be valid ( i.e. sourcePath exists, destPath exists,
     * FTP Client is logged on with proper credentials )
     *
     * Source: http://www.codejava.net/java-se/networking/ftp/java-ftp-file-download-tutorial-and-example
     *
     * @param sourcePath    Path where the file you're looking for comes from
     * @param destPath      Path where you want the file to go on your local machine
     * @param File          Name of the File you're looking for
     * @return              Boolean value; true equals success
     */
    public static boolean getFile( String sourcePath, String destPath,
                                   String File, FTPClient ftpClient){
        String remoteFile1 = new String( sourcePath+"/"+File);
        String downloadPath = new String( destPath+"/"+File);
        File downloadFile1 = new File(downloadPath);
        try{
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();
            if (success) {
                System.out.println("File has been downloaded successfully.");
                return true;
            }
        }
        catch(IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Multiple version of the getFile method
     * @param sourcePath    Path where the file you're looking for comes from
     * @param destPath      Path where you want the file to go on your local machine
     * @param Files         Name of the File you're looking for
     * @param ftpClient     Boolean value; true equals success
     * @return              Boolean value; true equals success
     */
    public static boolean getMultipleFiles( String sourcePath, String destPath,
                                            String[] Files, FTPClient ftpClient ){
        boolean success = false;
        for( int i = 0; i < Files.length; i++){
            success = getFile(sourcePath, destPath, Files[i], ftpClient);
        }
        return success;
    }

    /**
     * A method to check whether the passed in path is a directory on the local
     * machine. Returns false if it is not
     *
     * @param Path          A valid path to a directory
     * @return  Boolean     Value True is success
     */
    public static boolean isDirectoryOnLocal( String Path ){
        File dir = new File(Path);
        return dir.isDirectory();
    }

    /**
     * Call this method to get the list of the files on the current
     * directory
     *
     * @return  Always returns true
     */
    public static boolean listFilesOnCurrentDirectory(){
        File currDir = new File(".");

        if( isDirectoryOnLocal( currDir.toString() ) == false ) return false;

        File[] filesList = currDir.listFiles();
        for(int i = 0; i < filesList.length; i++ ){
            System.out.println( filesList[i].getName() );
        }
        return true;
    }

    public static void main( String[] args ){
        System.out.printf("Hello, world!");
        String sourcePath = new String();
        String destPath = new String();
        String file = new String();
        FTPClient ftpClient = new FTPClient();

        try{
            isDirectoryOnLocal(destPath);
        }
        catch(IllegalArgumentException ex){
            System.out.println("\n"+destPath+" is not a directory");
        }

        //Temporary Testing
        if( getFile(sourcePath, destPath, file, ftpClient) )
            System.out.println( "GetFile Works");

        //Temporary Testing
        String[] files = new String[5];
        if( getMultipleFiles(sourcePath, destPath, files, ftpClient) )
            System.out.println("GetMultiple Files Works!");

        listFilesOnCurrentDirectory();

        System.exit(1);
    }
}
