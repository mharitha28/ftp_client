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
 * Tests whether the local directory exists or not.
 */
public class SearchLocalFileTest {
    FPTClient ftpClient = new FPTClient();

    @Before
    public void setUp() throws Exception {
        assertEquals(true, ftpClient.Login("localhost","haritapalam","goddu karam"));
    }

    @Test
    public void localDirDoesNotExists()throws FTPConnectionClosedException, IOException {
        String fileNameToSearch = "txt";
        File localDirPath = new File("/Users/haritapalam/Documents/FTPFils");
        assertNotEquals(true,ftpClient.searchLocalFile(localDirPath, fileNameToSearch));
    }

}