package FileActions;

import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Haritha M. on 8/16/17.
 * Tests whether the remote directory exists or not.
 */
public class SearchRemoteFileTest {
    FPTClient ftpClient = new FPTClient();

    @Before
    public void setUp() throws Exception {
        assertEquals(true, ftpClient.Login("localhost","username","P4ssw0rd"));
    }

    @Test
    public void remoteDirDoesNotExists()throws FTPConnectionClosedException, IOException {
        String fileNameToSearch = "txt";
        File remoteDirPath = new File("/Users/haritapalam/Documents/FTPFils");
        assertNotEquals(true,ftpClient.searchRemoteFile(remoteDirPath, fileNameToSearch));
    }

}