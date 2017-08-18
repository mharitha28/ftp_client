package FileActions;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Haritha M. on 8/8/17.
 * Tests whether the directories given by user exists or not.
 */
public class CopyDirectoryToServerTest {
    int replyCode;
    FPTClient ftpClient = new FPTClient();
    List<File> listLocalFiles = null;

    @Before
    public void setUp() throws Exception {
        assertEquals(true, ftpClient.Login("localhost","username","P4ssw0rd"));
    }

    @Test
    public void localDirDoesNotExists()throws FTPConnectionClosedException, IOException {

        String remoteDirPath="/Users/haritapalam/Documents/FTPFiles_Server";
        File localDirPath=new File("/Users/haritapalam/Documents/FTPFils");
        assertNotEquals(true,localDirPath.exists());

        replyCode=ftpClient.CopyDirectoryToFTPServer(localDirPath, remoteDirPath);
        assertNotEquals(226, replyCode);
    }

    @Test
    public void noFilesInLocalDir()throws FTPConnectionClosedException, IOException {

        File localDirPath=new File("/Users/haritapalam/Documents/FTPFileDirTest");
        assertEquals(true,localDirPath.exists());

        String remoteDirPath="/Users/haritapalam/Documents/FTPFiles_Server";

        replyCode=ftpClient.CopyDirectoryToFTPServer(localDirPath, remoteDirPath);
        assertNotEquals(226, replyCode);
    }

    @Test
    public void serverDirDoesNotExists()throws FTPConnectionClosedException, IOException{

        File localDirPath=new File("/Users/haritapalam/Documents/FTPFiles");
        assertEquals(true,localDirPath.exists());

        String remoteDirPath="/Users/haritapalam/Documents/FTPFies_Server";

        replyCode=ftpClient.CopyDirectoryToFTPServer(localDirPath, remoteDirPath);
        assertNotEquals(226, replyCode);
    }

    @Test
    public void dirWithSameNameExistsAtServer()throws FTPConnectionClosedException, IOException{

        File localDirPath=new File("/Users/haritapalam/Documents/PSU/Sum'17/Agile/ftp_client/ftp_client/FileActions");
        assertEquals(true,localDirPath.exists());

        String remoteDirPath="/Users/haritapalam/Documents/FTPFiles_Server";

        replyCode=ftpClient.CopyDirectoryToFTPServer(localDirPath, remoteDirPath);
        assertNotEquals(226, replyCode);
    }
}