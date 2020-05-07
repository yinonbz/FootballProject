package businessLayer.userTypes;

import businessLayer.Team.Team;
import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Administration.Admin;
import businessLayer.userTypes.Administration.AssociationRepresentative;
import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.viewers.Fan;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;

import org.junit.Test;
import businessLayer.userTypes.SystemController;
import serviceLayer.SystemService;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestSystemController {

     static Team LeedsUnited;
     static Team Sunderland;
     static TeamOwner Alex;
     static TeamOwner Max;
     static TeamOwner YaelM;
     static Admin admin;
     static Admin admin2;
     static Fan fan;
    static DemoDB DB;
    static DataBaseValues tDB;
    static SystemService systemService;


    @BeforeClass

    public static void createTestValuesForSystemController(){
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        fan = (Fan) DB.selectSubscriberFromDB("Gate13");
        admin = (Admin)DB.selectSubscriberFromDB("TomerSein");
        admin2 = (Admin)DB.selectSubscriberFromDB("ItaiKatz");
        YaelM = (TeamOwner) DB.selectSubscriberFromDB("YaelM");
        Max = (TeamOwner) DB.selectSubscriberFromDB("Max");
        Alex = (TeamOwner) DB.selectSubscriberFromDB("Alex");
        LeedsUnited = DB.selectTeamFromDB("LeedUnited");
        Sunderland = DB.selectTeamFromDB("Sunderland");
        systemService = new SystemService();
    }

    @Test
    //unit test
    public void UT_checkCloseTeam(){
        //1
        //close a team 1st time
        assertTrue(admin.closeTeam("LeedsUnited"));

        //2
        //close team that doesn't exist
        assertFalse(admin.closeTeam("MCA"));

        //3
        //close team that is already closed
        assertFalse(admin.closeTeam("Beer Sheva"));

    }

    @Test
    //unit
    public void IT_checkDeleteSubscriber(){
        //1
        //checks if we can delete a fan from the system

        assertEquals("The User Gate13 was removed",admin.deleteSubscriber("Gate13"));

        //2 checks that the user was deleted from the list
        assertFalse(DB.containsInSystemSubscribers("Gate13"));

        //3
        //checks that the admin can't delete a user that doesn't exist
        assertEquals("User doesn't exist in the system",admin.deleteSubscriber("Gate13"));

        //4
        //checks that the admin can't delete an exclusive team owner
        assertEquals("Can't remove an exclusive team owner",admin.deleteSubscriber("YaelM"));
        assertTrue(DB.containsInSystemSubscribers("YaelM"));

        //5
        //checks admin can't delete himself
        assertEquals("Admin can't remove his own user",admin.deleteSubscriber("TomerSein"));
        assertTrue(DB.containsInSystemSubscribers("TomerSein"));

    }

    @Test
    //unit
    public void IT_checkDisplayComplaints(){

        //1
        //check if the complaints are displayed
        assertEquals(2,admin.displayComplaints().size());

    }


    @Test
    //unit
    public void IT_checkReplyComments(){

        //1
        //regular test add a comment
        assertTrue(admin.replyComplaints("0",admin.getUsername(), "Solved"));

        //1.1 check the field were updated
        Complaint c1 = DB.selectComplaintFromDB(0);
        assertEquals("Solved",c1.getComment());
        assertEquals("TomerSein",c1.getHandler());

 //       System.out.println(systemController.getSystemComplaints().get(0).toString());

        //2
        //can't add an empty comment
        assertFalse(admin.replyComplaints("0",admin.getUsername(), ""));

        //3
        //can't add a comment to invalid complaint id
        assertFalse(admin.replyComplaints("3",admin.getUsername(), ""));

    }

    //Unit Test - Check singleton functionality
    @Test
    public void UT_singleton () {
        SystemController systemController1 = SystemController.SystemController();
        SystemController systemController2 = SystemController.SystemController();
        assertEquals(systemController1,systemController2);
    }

    //Unit Test - insertInfo(String userName, String password)
    @Test
    public void UT_insertInfo() {
        SystemController systemController = SystemController.SystemController();
        assertTrue(systemController.insertInfo("admin","admin"));
        assertFalse(systemController.insertInfo("admin","wrongPass"));
    }

    //Unit Test - Test changePassword(String password,String userName)
    /**
     * Check the password guidelines
     */
    @Test
    public void UT_changePassword() {
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
        assertFalse(systemController.changePassword("abcdefghijklmnopqrstuvwxyz1234567890","admin")); //too long
        assertFalse(systemController.changePassword("abcdefg","admin")); //include only letters
        assertFalse(systemController.changePassword("123456","admin"));//include only numbers
        assertTrue(systemController.changePassword("123456abcde@","admin")); //include both numbers and letters
        assertFalse(systemController.changePassword("123456abcde&","admin")); //include &,' or "
        assertFalse(systemController.changePassword("123456abcde\"","admin"));
        assertFalse(systemController.changePassword("123456abcde'","admin"));
        assertFalse(systemController.changePassword("123456 abcde'","admin")); //include space
        assertFalse(systemController.changePassword("123456admin","admin")); //include the user name
    }

    //Unit Test -initializeSystem(String Password)
    /**
     * Test wrong/right password in the initialize system process
     */
    @Test
    public void UT_initializeSystem() {

        SystemController systemController = SystemController.SystemController();
        assertTrue(systemController.initializeSystem("admin"));
        assertFalse(systemController.initializeSystem("wrongPass"));

    }

    @Test
    public void UT_TestgetSubscriberByUserName(){
        SystemController systemController = SystemController.SystemController();
        assertNull(systemController.getSubscriberByUserName("Itzik"));
    }

    @Test
    public void UT_enterUserDetails(){
        SystemController systemController = SystemController.SystemController();
        assertNull(systemController.enterLoginDetails("Itzik","abc123"));
        assertNull(systemController.enterLoginDetails(null,"abc123"));
        assertNull(systemController.enterLoginDetails("Itzik",null));
    }

    @Test
    public void UT_enterRegisterDetails_Admin(){
        SystemController systemController = SystemController.SystemController();
        assertFalse(systemController.enterRegisterDetails_Admin(null,"a","a"));
        assertFalse(systemController.enterRegisterDetails_Admin("a",null,"a"));
        assertFalse(systemController.enterRegisterDetails_Admin("a","a",null));

        assertFalse(systemController.enterRegisterDetails_Admin("TomerSein","a","a")); //already exist userName

        assertTrue(systemController.enterRegisterDetails_Admin("AlonGolo","abc123","b"));
        assertNotNull(DB.selectAdminToApproveFromDB("AlonGolo"));
        assertFalse(((Admin)DB.selectAdminToApproveFromDB("AlonGolo")).isApproved());
        assertTrue(systemController.enterRegisterDetails_Admin("Roni","abc123","b"));
        assertEquals(DB.selectAllAdminApprovalRequests().size(),2);
    }

    @Test
    public void UT_handleAdminApprovalRequest(){
        SystemController systemController = SystemController.SystemController();
        assertTrue(systemController.enterRegisterDetails_Admin("NewAdmin","abc123","b"));
        assertFalse(((Admin)systemController.selectUserFromDB("NewAdmin")).isApproved());
        assertFalse(systemController.handleAdminApprovalRequest("Buzaglo","NewAdmin",true));
        assertFalse(systemController.handleAdminApprovalRequest("TomerSein","Buzaglo",true));
        assertTrue(systemController.handleAdminApprovalRequest("TomerSein","NewAdmin",true));
        assertTrue(((Admin)systemController.selectUserFromDB("NewAdmin")).isApproved());

        assertTrue(systemController.enterRegisterDetails_AssociationRepresentative("NewAR","abc123","b"));
        assertFalse(((AssociationRepresentative)systemController.selectUserFromDB("NewAR")).isApproved());
        assertFalse(systemController.handleAdminApprovalRequest("Buzaglo","NewAR",true));;
        assertTrue(systemController.handleAdminApprovalRequest("TomerSein","NewAR",true));
        assertTrue(((AssociationRepresentative)systemController.selectUserFromDB("NewAR")).isApproved());

        assertTrue(systemController.enterRegisterDetails_AssociationRepresentative("NewAR2","abc123","b"));
        assertFalse(((AssociationRepresentative)systemController.selectUserFromDB("NewAR2")).isApproved());
        assertTrue(systemController.handleAdminApprovalRequest("TomerSein","NewAR2",false));
        assertFalse(((AssociationRepresentative)systemController.selectUserFromDB("NewAR2")).isApproved());
    }

    @Test
    public void UC_1_1_a() {
        assertTrue(systemService.insertInfo("admin","admin"));
        assertTrue(systemService.initializeSystem("admin"));
        assertTrue(systemService.changePassword("ad123456","admin"));
    }

    @Test
    public void UC_1_1_b() {
        //will be tested after the login implementation
    }


    @Test
    public void UC_2_3_a() {
        assertEquals(systemService.enterLoginDetails("Buzaglo","Buzaglo123"),"Player");
    }

    @Test
    public void UC_2_3_b(){
        assertNull(systemService.enterLoginDetails("Buzaglo",null));
    }

    @Test
    public void UC_2_3_c(){
        assertNull(systemService.enterLoginDetails("Dudidu","Dudidu123"));
    }
}