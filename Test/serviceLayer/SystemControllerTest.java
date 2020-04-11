import org.junit.Test;
import serviceLayer.SystemController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class SystemControllerTest {

    //T1.1_1 - Check singleton functionality
    @Test
    public void singletonTest () {
        SystemController systemController1 = SystemController.SystemController();
        SystemController systemController2 = SystemController.SystemController();
        assertEquals(systemController1,systemController2);
    }

    //T1.1_2 - Test insertInfo(String userName, String password)
    @Test
    public void insertInfo() {
        SystemController systemController = SystemController.SystemController();
        assertTrue(systemController.insertInfo("admin","admin"));
        assertFalse(systemController.insertInfo("admin","wrongPass"));
    }

    //T1.1_3 - Test changePassword(String password,String userName)
    /**
     * Check the password guidelines
     */
    @Test
    public void changePasswordTest() {
        SystemController systemController = SystemController.SystemController();

        System.out.println("Please enter a new password while following the guidelines below:");
        System.out.println("* The password must be 6 to 32 characters long.");
        System.out.println("* The password must contain a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.");
        System.out.println("* The password is case-sensitive.");
        System.out.println("* Single quotes, double quotes, ampersands ( ‘  \"  & ), and spaces are not allowed.");
        System.out.println("* The password cannot be the same as your User Name name and should not contain any part of your user name.");
        String s = "* Do not post or share your password or send your password to others by email.";
        System.out.println(s.toUpperCase());

        assertFalse(systemController.changePassword("a2","admin")); //too short
        assertFalse(systemController.changePassword("abcdefghijklmnopqrstuvwxyz1234567890","admin")); //too short
        assertFalse(systemController.changePassword("abcdefg","admin")); //include only letters
        assertFalse(systemController.changePassword("123456","admin"));//include only numbers
        assertTrue(systemController.changePassword("123456abcde@","admin")); //include both numbers and letters
        assertFalse(systemController.changePassword("123456abcde&","admin")); //include &,' or "
        assertFalse(systemController.changePassword("123456abcde\"","admin"));
        assertFalse(systemController.changePassword("123456abcde'","admin"));
        assertFalse(systemController.changePassword("123456 abcde'","admin")); //include space
        assertFalse(systemController.changePassword("123456admin","admin")); //include the user name
    }

    //T1.1_4
    /**
     * Test wrong/right password in the initialize system process
     */
    @Test
    public void initializeSystemTest() {

        SystemController systemController = SystemController.SystemController();
        assertTrue(systemController.initializeSystem("admin"));
        assertFalse(systemController.initializeSystem("wrongPass"));

    }






}