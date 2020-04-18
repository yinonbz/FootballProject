package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.SystemService;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdminTest {

    static DemoDB DB;
    static DataBaseValues tDB;
    static SystemService systemService;

    @BeforeClass
    public static void defineValues(){
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        systemService = new SystemService();
    }

    @Test
    public void UC_8_1_a(){
        assertTrue(systemService.closeTeamByAdmin("Chelsea","TomerSein"));
        //1
        //checks if we can close the team successfully
        Team Chelsea = DB.selectTeamFromDB("Chelsea");

        //check the field has changed
        assertTrue(Chelsea.getClosedByAdmin());
    }

    @Test
    public void UC_8_1_b(){
        //3
        //checks if we can close a team that is already closed
        Team Chelsae = DB.selectTeamFromDB("Chelsea");
        Chelsae.closeTeamPermanently();
        assertFalse(systemService.closeTeamByAdmin("Chelsea","TomerSein"));
    }

    @Test
    public void UC_8_1_c(){
        //2
        //checks if we can close a team that doesn't exists
        assertFalse(systemService.closeTeamByAdmin("","TomerSein"));
    }


    @Test
    public void UC_8_2_a(){
        //1
        //check if removing a regular user is possible
        Assert.assertEquals("The User Ben was removed",systemService.removeSubscriber("Ben","TomerSein"));
    }

    @Test
    public void UC_8_2_b(){
        //2
        //check if it is possible to remove an exclusive team owner from the system
        Assert.assertEquals("Can't remove an exclusive team owner",systemService.removeSubscriber("Harry","TomerSein"));
    }

    @Test
    public void UC_8_2_c(){
        //3
        //check if admin can delete himself
        Assert.assertEquals("Admin can't remove his own user",systemService.removeSubscriber("TomerSein","TomerSein"));
    }


    @Test
    public void UC_8_2_d(){
        //4
        //check if you can delete user that doesn't exists
        Assert.assertEquals("User doesn't exist in the system",systemService.removeSubscriber("dddddd","TomerSein"));

    }



    @Test
    public void UC_8_3_1_a(){
        //1
        //check the number of complaints is the right number
        assertEquals(2,systemService.displayComplaints("TomerSein").size());
    }

    @Test
    public void UC_8_3_1_b(){
        //2
        //check no body else can see the complaints beside the admin
        assertNull(systemService.displayComplaints("Gate13"));
    }

    @Test
    public void UC_8_3_2_a(){
        //1
        //reply to a complaint
        assertTrue(systemService.replyComplaints("0","TomerSein","Solved"));
    }

    @Test
    public void UC_8_3_2_b(){
        //2
        //reply a comment that doesn't exist
        assertFalse(systemService.replyComplaints("4","TomerSein","Solved"));

        //todo ido added but their is a problem delete this comment
        //assertFalse(systemService.replyComplaints("tomer","TomerSein","Solved"));
    }

    @Test
    public void UC_8_3_2_c(){
        //3
        //reply a comment with not valid comment
        assertFalse(systemService.replyComplaints("0","TomerSein",""));
    }

    @Test
    public void UC_8_3_2_d(){
        //4
        //reply a comment not with admin
        assertFalse(systemService.replyComplaints("0","Gate13","Solved"));
    }

    //TODO IDO ADDED TESTS TO MUTATION

    @Test
    public void setAndGetTest(){
        Admin admin = (Admin) DB.selectSubscriberFromDB("TomerSein");
        admin.setName("TomerKatz");
        assertEquals(admin.getName(),"TomerKatz");
    }
}

