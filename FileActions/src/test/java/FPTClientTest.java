import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by MyMac on 7/18/17.
 */
public class FPTClientTest {
    @Test
    public void login() throws Exception {
        String username = "abc";
        String password = "123";
        FPTClient object = new FPTClient();
        assertEquals(false, object.Login(username, password));
    }
}