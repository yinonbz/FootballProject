package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
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
    public void UC8_1_a(){
        assertTrue(systemService.closeTeamByAdmin("Chelsea","TomerSein"));
        //1
        //checks if we can close the team successfully
        Team Chelsea = DB.selectTeamFromDB("Chelsea");

        //check the field has changed
        assertTrue(Chelsea.getClosedByAdmin());
    }

    @Test
    public void UC8_1_b(){
        //3
        //checks if we can close a team that is already closed
        Team Chealse = DB.selectTeamFromDB("Chelsea");
        Chealse.closeTeamPermanently();
        assertFalse(systemService.closeTeamByAdmin("Chelsea","TomerSein"));
    }

    @Test
    public void UC8_1_c(){
        //2
        //checks if we can close a team that doesn't exists
        assertFalse(systemService.closeTeamByAdmin("","TomerSein"));
    }

    @Test
    public void UC8_3_1(){
        //1
        //check the number of complaints is the right number
        assertEquals(2,systemService.displayComplaints("TomerSein").size());

        //2
        //check no body else can see the complaints beside the admin
        assertNull(systemService.displayComplaints("Gate13"));
    }

    @Test
    public void UC8_3_2(){
        //1
        //reply to a complaint
        assertTrue(systemService.replyComplaints("0","TomerSein","Solved"));

        //2
        //reply a comment with not valid comment
        assertFalse(systemService.replyComplaints("0","TomerSein",""));

        //3
        //reply a comment not with admin
        assertFalse(systemService.replyComplaints("0","Gate13","Solved"));

        //4
        //reply a comment that doesn't exist
        assertFalse(systemService.replyComplaints("4","TomerSein","Solved"));
    }



}

