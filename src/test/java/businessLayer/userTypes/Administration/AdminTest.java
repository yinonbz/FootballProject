package businessLayer.userTypes.Administration;

import businessLayer.Exceptions.NotFoundInDbException;
import businessLayer.Team.Team;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.rules.ExpectedException;
import serviceLayer.SystemService;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class AdminTest {

    static DemoDB  DB;
    static DataBaseValues tDB;
    static SystemService systemService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void defineValues(){
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        systemService = new SystemService();
    }

    @Test
    public void UC8_1_a(){
        //todo: check with new DB
        //expectedException.expect(NotFoundInDbException.class);
        //systemService.closeTeamByAdmin("Chelsea","TomerSein");
        //1
        //checks if we can close the team successfully
        Team Chelsea = DB.selectTeamFromDB("Chelsea");

        //check the field has changed
        //todo: check with new DB
        //assertTrue(Chelsea.getClosedByAdmin());
    }

    @Test
    public void UC8_1_b(){
        //3
        //checks if we can close a team that is already closed
        Team Chelsae = DB.selectTeamFromDB("Chelsea");
        Chelsae.closeTeamPermanently();
        assertFalse(systemService.closeTeamByAdmin("Chelsea","TomerSein"));
    }

    @Test
    public void UC8_1_c(){
        //2
        //checks if we can close a team that doesn't exists
        assertFalse(systemService.closeTeamByAdmin("","TomerSein"));
    }


    @Test
    public void UC8_2_a(){
        //1
        //check if removing a regular user is possible
        //todo: check with new DB
        //Assert.assertEquals("The User Ben was removed",systemService.removeSubscriber("Ben","TomerSein"));
    }

    @Test
    public void UC8_2_b(){
        //2
        //check if it is possible to remove an exclusive team owner from the system
        //todo: check with new DB
        //Assert.assertEquals("Can't remove an exclusive team owner",systemService.removeSubscriber("Harry","TomerSein"));
    }

    @Test
    public void UC8_2_c(){
        //3
        //check if admin can delete himself
        //todo: check with new DB
        //Assert.assertEquals("Admin can't remove his own user",systemService.removeSubscriber("TomerSein","TomerSein"));
    }


    @Test
    public void UC8_2_d(){
        //4
        //check if you can delete user that doesn't exists
        //todo: check with new DB
        //Assert.assertEquals("User doesn't exist in the system",systemService.removeSubscriber("dddddd","TomerSein"));

    }



    @Test
    public void UC8_3_1_a(){
        //1
        //check the number of complaints is the right number
        //todo: check with new DB
        //assertEquals(2,systemService.displayComplaints("TomerSein").size());
    }

    @Test
    public void UC8_3_1_b(){
        //2
        //check no body else can see the complaints beside the admin
        assertNull(systemService.displayComplaints("Gate13"));
    }

    @Test
    public void UC8_3_2_a(){
        //1
        //reply to a complaint
        //todo: check with new DB
        //assertTrue(systemService.replyComplaints("0","TomerSein","Solved"));
    }

    @Test
    public void UC8_3_2_b(){
        //2
        //reply a comment that doesn't exist
        assertFalse(systemService.replyComplaints("4","TomerSein","Solved"));

        //todo ido added but their is a problem delete this comment
        //assertFalse(systemService.replyComplaints("tomer","TomerSein","Solved"));
    }

    @Test
    public void UC8_3_2_c(){
        //3
        //reply a comment with not valid comment
        assertFalse(systemService.replyComplaints("0","TomerSein",""));
    }

    @Test
    public void UC8_3_2_d(){
        //4
        //reply a comment not with admin
        assertFalse(systemService.replyComplaints("0","Gate13","Solved"));
    }

    //TODO IDO ADDED TESTS TO MUTATION

    @Test
    public void IT_setAndGetTest(){
        Admin admin = (Admin) DB.selectSubscriberFromDB("TomerSein");
        admin.setName("TomerKatz");
        assertEquals(admin.getName(),"TomerKatz");
    }
}

