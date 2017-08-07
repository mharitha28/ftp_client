package ftp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.*;
import java.net.SocketException;

/**
 * Created by chsherpa on 7/22/17.
 */
public class getFile
{
  private boolean Login(String username, String password)
  {
    FTPClient ftp = new FTPClient();
    try
    {
      ftp.connect("ftp.swfwmd.state.fl.us",21);
      boolean login = ftp.login(username, password);
      if (login)
      {
        System.out.println("Connection established...");
        System.out.println("Status: " + ftp.getStatus());

        // logout the user, returned true if logout successfully
        boolean logout = ftp.logout();
        if (logout)
        {
          return true;
        }
      }
      else
      {
        return false;
      }
    }
    catch (SocketException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        ftp.disconnect();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return false;
  }

  public getFile()
  { }

  public boolean SingleFile( String sourcePath, String destPath, String fileName, FTPClient ftp )
  {
    if( isDirectoryOnLocal(sourcePath) && isDirectoryOnLocal(destPath) && checkFile(fileName) && checkFTPConnection() )
    {
      return getFile(sourcePath, destPath, fileName, ftp );
    }
    System.out.println("\nSomething went wrong in getting the Single File! Try again.");
    return false;
  }

  public boolean multipleFiles( String sourcePath, String destPath, String[] files, FTPClient ftp )
  {
    boolean sourceCheck = isDirectoryOnLocal(sourcePath);
    boolean destCheck = isDirectoryOnLocal(destPath);

    if( isDirectoryOnLocal(sourcePath) && isDirectoryOnLocal(destPath) && checkFile(files[0]) && checkFTPConnection() )
    {
      return getMultipleFiles(sourcePath, destPath, files, ftp);
    }
    System.out.println("\nSomething went wrong in getting the Multiple Files! Try again.");
    return false;
  }

  /**
   * A method to check whether the passed in path is a directory on the local
   * machine. Returns false if it is not
   *
   * @param Path          A valid path to a directory
   * @return  Boolean     Value True is success
   */
  public boolean isDirectoryOnLocal( String Path )
  {
    if( Path.isEmpty() )
    {
      System.out.println("File Path is empty");
      return false;
    }
    if( Path == null )
    {
      System.out.println("File Path is null");
      return false;
    }

    File dir = new File(Path);

    if( dir.isDirectory() )
    {
      System.out.println("\n" + Path + "is directory");
      return true;
    }
    System.out.println("\n" + Path + "is not a directory");
    return false;
  }

  /**
   * Call this method to get the list of the files on the current
   * directory
   *
   * @return  boolean value of True/False; True being success value
   */
  public boolean listFilesOnCurrentDirectory(){
      File currDir = new File(".");

      if( isDirectoryOnLocal( currDir.toString() ) == false ) return false;

      File[] filesList = currDir.listFiles();
      for(int i = 0; i < filesList.length; i++ ){
          System.out.println( filesList[i].getName() );
      }
      return true;
  }

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
  * @param fileName          Name of the File you're looking for
  * @return              Boolean value; true equals success
  */
  private boolean getFile( String sourcePath, String destPath, String fileName, FTPClient client)
  {
    String remoteFile1 = new String( sourcePath+"/"+fileName);
    String downloadPath = new String( destPath+"/"+fileName);
    File downloadFile1 = new File(downloadPath);
    try{
      OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
      System.out.println("\nRetrieving File");
      boolean success = client.retrieveFile(remoteFile1, outputStream1);
      outputStream1.close();
      if (success) {
        System.out.println("File has been downloaded successfully.");
        return true;
      }
    }
    catch(IOException ex) {
      System.out.println("Error: " + ex.getMessage());
      System.out.println();
    }
    return false;
  }

  /**
   * Multiple version of the getFile method
   * @param sourcePath    Path where the file you're looking for comes from
   * @param destPath      Path where you want the file to go on your local machine
   * @param files         Name of the File you're looking for
   * @param ftp     Boolean value; true equals success
   * @return              Boolean value; true equals success
   */
  private boolean getMultipleFiles( String sourcePath, String destPath, String[] files, FTPClient ftp )
  {
      boolean success = false;
      for( int i = 0; i < files.length; i++){
          success = getFile(sourcePath, destPath, files[i], ftp);
      }
      return success;
  }

  public boolean checkFile(String fileName)
  {
    if( fileName.isEmpty() )
    {
      System.out.println("File name is empty");
      return false;
    }
    if( fileName == null )
    {
      System.out.println("File name is null and invalid.");
      return false;
    }

    File fil = new File(fileName);
    if( fil.isFile() )
    {
      System.out.println("\n" + fileName + "is a File");
      return true;
    }
    System.out.println("\n" + fileName  + "is not a File");
    return false;
  }

  public boolean checkFTPConnection()
  {
    String user = new String();
    String pwd = new String();
    return Login(user,pwd);
  }
}
