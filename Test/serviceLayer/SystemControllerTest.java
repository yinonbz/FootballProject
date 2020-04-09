import org.junit.Test;
import serviceLayer.SystemController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class SystemControllerTest {

    //T1 - Check singleton functionality
    //T2 - Test insertInfo(userName,password)
    //T3 - Test checkPasswordStrengh(String password,String userName)

/*    System.out.println("Welcome user. you are about to initialize the system.");
        System.out.println("Please enter the default username and the password for the temporary admin user:");*/


    /**
     * Test wrong pass in the initialize system process
     */
    @org.junit.Test
    public void initializeSystem() {

        SystemController systemController = SystemController.SystemController();
        //System.out.println("Hello temporary admin, please enter you password:");
        assertFalse(systemController.initializeSystem("wrongPass","admin"));

    }

    /**
     * Check the password guidelines
     */
    @Test
    public void checkPasswordStrength() {
        SystemController systemController = SystemController.SystemController();
        assertFalse(systemController.checkPasswordStrength("a2","admin")); //too short
        assertFalse(systemController.checkPasswordStrength("abcdefghijklmnopqrstuvwxyz1234567890","admin")); //too short
        assertFalse(systemController.checkPasswordStrength("abcdefg","admin")); //include only letters
        assertFalse(systemController.checkPasswordStrength("123456","admin"));//include only numbers
        assertTrue(systemController.checkPasswordStrength("123456abcde@","admin")); //include both numbers and letters
        assertFalse(systemController.checkPasswordStrength("123456abcde&","admin")); //include &,' or "
        assertFalse(systemController.checkPasswordStrength("123456abcde\"","admin"));
        assertFalse(systemController.checkPasswordStrength("123456abcde'","admin"));
        assertFalse(systemController.checkPasswordStrength("123456admin","admin"));
    }
}