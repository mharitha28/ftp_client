package ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test; //JUnit4 from Intellij IDE
/**
 * Created by chsherpa on 7/22/17.
 * Unit tests for the {@link getFile} class.
 */
public class getFileTest
{
  @Test
  public void allTests()
  {
    getFileInvalidPath();
    getFileInvalidFileName();
    getFileInvalidFTPClient();
    getFileSingleFileTest();
    getFileMultipleFilesTest();
    listFilesOnCurrDirectory();
    invalidIsDirectoryTest();
  }

  @Test
  public void getFileInvalidPath()
  {
    String pth = new String("");
    getFile one = new getFile();
    Assert.assertFalse(one.isDirectoryOnLocal(pth));
  }

  @Test
  public void getFileInvalidFileName()
  {
    String invalFileName = new String ("0xdofwje");
    getFile one = new getFile();
    Assert.assertFalse( one.checkFile( invalFileName ) );
  }

  @Test
  public void getFileInvalidFTPClient()
  {
    getFile one = new getFile();
    Assert.assertEquals(false, one.checkFTPConnection());
  }

  @Test
  public void getFileSingleFileTest()
  {getFile one = new getFile();
    String sourcePath = new String(".");
    String destPath = new String(".");
    String single = new String("SingleTest.txt");
    FTPClient test = new FTPClient();
    Assert.assertFalse( one.SingleFile( sourcePath, destPath, single, test ) );
  }

  @Test
  public void getFileMultipleFilesTest()
  {
    getFile one = new getFile();
    String sourcePath = new String(".");
    String destPath = new String(".");
    String [] multiple = new String[10];
    FTPClient test = new FTPClient();

    Assert.assertFalse( one.multipleFiles( sourcePath, destPath, multiple, test ) );
  }

  @Test
  public void listFilesOnCurrDirectory()
  {
    getFile one = new getFile();
    Assert.assertTrue( one.listFilesOnCurrentDirectory() );
  }

  @Test
  public void invalidIsDirectoryTest()
  {
    getFile one = new getFile();
    Assert.assertFalse( one.isDirectoryOnLocal("") );
    Assert.assertFalse( one.isDirectoryOnLocal("eosjt") );
  }
}

