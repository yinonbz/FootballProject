import businessLayer.Team.Team;
import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Administration.Admin;
import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.viewers.Fan;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import serviceLayer.SystemController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class TestSystemController {

    public static SystemController systemController;
    public static Team ManchesterUnited;
    public static Team BeerSheva;
    public static TeamOwner Glazers;
    public static TeamOwner Nissanov;
    public static TeamOwner Barkat;
    public static Admin admin;
    public static Admin admin2;
    public static Fan fan;


    @BeforeClass
    public static void createTestValuesForSystemController(){
        systemController = SystemController.SystemController();
        admin = new Admin("TomerSein", "helloWorld", "tomer",systemController);
        admin2 = new Admin ("ItaiKatz", "helloWorld", "itai",systemController);
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva","alona",systemController);
        Nissanov = new TeamOwner("Nissanov", "telAviv","nissanov",systemController);
        Glazers = new TeamOwner("Glazers", "manchesterU","glazer",systemController);
        fan = new Fan ("Gate13","aviNimni","avi",systemController);
        systemController.getSystemSubscribers().put("AlonaBarkat",Barkat);
        systemController.getSystemSubscribers().put("Nissanov",Nissanov);
        systemController.getSystemSubscribers().put("Glazers",Glazers);
        systemController.getSystemSubscribers().put("Gate13",fan);
        systemController.getSystemSubscribers().put("TomerSein",admin);
        systemController.getSystemSubscribers().put("ItaiKatz",admin2);
        ManchesterUnited = new Team("Manchester United",Glazers,1899);
        ManchesterUnited.getTeamOwners().add(Barkat); //todo will be changed later to a normal function depends on 6.1
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        Barkat.getTeams().add(BeerSheva); //todo will be changed later to a normal function depends on 6.1
        Barkat.getTeams().add(ManchesterUnited);
        Glazers.getTeams().add(ManchesterUnited);
        systemController.addTeam(ManchesterUnited);
        systemController.addTeam(BeerSheva);
        systemController.addComplaint("My system doesn't work",fan);
        systemController.addComplaint("I don't like this team",fan);
        systemController.addComplaint("",fan);

    }

    @Test
    public void UC8_1(){
        //1
        //close a team 1st time
        assertTrue(admin.closeTeam("Beer Sheva"));

        //2
        //close team that doesn't exist
        assertFalse(admin.closeTeam("HTA"));

        //3
        //close team that is already closed
        assertFalse(admin.closeTeam("Beer Sheva"));

    }

    @Test
    public void UC8_2(){
        //1
        //checks if we can delete a fan from the system

        assertEquals("The User Gate13 was removed",admin.deleteSubscriber("Gate13"));

        //2 checks that the user was deleted from the list
        assertFalse(systemController.getSystemSubscribers().containsKey("Gate13"));

        //3
        //checks that the admin can't delete a user that doesn't exist
        assertEquals("User doesn't exist in the system",admin.deleteSubscriber("Gate13"));

        //4
        //checks that the admin can't delete an exclusive team owner
        assertEquals("Can't remove an exclusive team owner",admin.deleteSubscriber("AlonaBarkat"));
        assertTrue(systemController.getSystemSubscribers().containsKey("AlonaBarkat"));

        //5
        //checks admin can't delete himself
        assertEquals("Admin can't remove his own user",admin.deleteSubscriber("TomerSein"));
        assertTrue(systemController.getSystemSubscribers().containsKey("TomerSein"));

    }

    @Test
    public void UC8_3_1(){

        //1
        //check if the complaints are displayed
        assertEquals(2,admin.displayComplaints().size());

    }


    @Test
    public void UC8_3_2(){

        //1
        //regular test add a comment
        assertTrue(admin.replyComplaints(0,admin, "Solved"));
 //       System.out.println(systemController.getSystemComplaints().get(0).toString());

        //2
        //can't add an empty comment
        assertFalse(admin.replyComplaints(0,admin, ""));

        //3
        //can't add a comment to invalid complaint id
        assertFalse(admin.replyComplaints(3,admin, ""));

    }

    //T1.1_1 - Check singleton functionality
    @Test
    public void singletonTest () {
        SystemController systemController1 = SystemController.SystemController();
        SystemController systemController2 = SystemController.SystemController();
        assertEquals(systemController1,systemController2);
    }

    //T1.1_2 - Test insertInfo(String userName, String password)
    @Before
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

        /*
        System.out.println("Please enter a new password while following the guidelines below:");
        System.out.println("* The password must be 6 to 32 characters long.");
        System.out.println("* The password must contain a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.");
        System.out.println("* The password is case-sensitive.");
        System.out.println("* Single quotes, double quotes, ampersands ( ‘  \"  & ), and spaces are not allowed.");
        System.out.println("* The password cannot be the same as your User Name name and should not contain any part of your user name.");
        */
        String s = "* Do not post or share your password or send your password to others by email.";
        /*
        System.out.println(s.toUpperCase());
        */
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