package FileActions;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Haritha M. on 8/8/17.
 * Tests whether the files given by user exists or not.\
 **/

public class UploadFilesToServerTest {
    int replyCode;
    FPTClient ftpClient = new FPTClient();
    List<File> listLocalFiles = null;


    @Before
    public void setUp() throws Exception {
        assertEquals(true, ftpClient.Login("localhost","username","P4ssw0rd"));
    }

    @Test
    public void localFileExistsAndUploaded()throws FTPConnectionClosedException, IOException{
        String remoteFilePath="/Users/haritapalam/Documents/FTPFiles_Server/";
        File localFilePath=new File("/Users/haritapalam/Documents/FTPFiles/ftpclient2.txt");
        listLocalFiles = new ArrayList<File>();
        listLocalFiles.add(localFilePath);

        assertEquals(true,localFilePath.exists());

        replyCode=ftpClient.UploadFilesToFTPServer(remoteFilePath, listLocalFiles);
        assertEquals(226, replyCode);

    }

    @Test
    public void localFileExistsInServer()throws FTPConnectionClosedException, IOException{

        File localFilePath=new File("/Users/haritapalam/Documents/FTPFiles/ftpclient1.txt");
        listLocalFiles = new ArrayList<File>();
        listLocalFiles.add(localFilePath);
        assertEquals(true,localFilePath.exists());

        String remoteFilePath="/Users/haritapalam/Documents/FTPFiles_Server";
        //assertEquals(true,ftpClient.changeWorkingDirectory(remoteFilePath));
        replyCode=ftpClient.UploadFilesToFTPServer(remoteFilePath, listLocalFiles);
        assertNotEquals(226, replyCode);

    }

    @Test
    public void localDirDoesNotExists()throws FTPConnectionClosedException, IOException{
        String remoteFilePath= "/Users/haritapalam/Documents/FTPFiles_Server";
        File localFilePath=new File("/Users/haritapalam/Documents/FTPFils/ftpclient1.txt");
        listLocalFiles = new ArrayList<File>();
        listLocalFiles.add(localFilePath);
        assertNotEquals(true,localFilePath.exists());

        replyCode=ftpClient.UploadFilesToFTPServer(remoteFilePath, listLocalFiles);
        assertNotEquals(226, replyCode);
    }

    @Test
    public void serverDirDoesNotExists()throws FTPConnectionClosedException, IOException{
        String remoteFilePath="/Users/haritapalam/Documents/FTPFies_Server";
        File localFilePath=new File("/Users/haritapalam/Documents/FTPFiles/ftpclient1.txt");
        listLocalFiles = new ArrayList<File>();
        listLocalFiles.add(localFilePath);

        replyCode=ftpClient.UploadFilesToFTPServer(remoteFilePath, listLocalFiles);
        assertNotEquals(226, replyCode);
    }
}