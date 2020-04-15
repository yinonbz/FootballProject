package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import businessLayer.userTypes.SystemController;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAssociationRepresentative {

    static TeamOwner Barkat;
    static SystemController systemController;
    static AssociationRepresentative EliLuzon;
    static DataBaseValues tDB;
    static DemoDB DB;

    @BeforeClass
    public static void createTestValues(){
        systemController = SystemController.SystemController();
        Barkat.sendRequestForTeam("ManchesterUnited","1888");
        EliLuzon = new AssociationRepresentative("EliLuzon", "abcd", "Eli", systemController);
        DB = new DemoDB();
        DB = tDB.getDB();

    }

    @Test
    public void checkTeamConfirmation(){
        //1
        //check if a regular confirmation
        assertTrue(EliLuzon.confirmTeamRequest("ManchesterUnited"));

        //2
        //check if a team that already exists get false
        assertFalse(EliLuzon.confirmTeamRequest("Beer Sheva"));

        //3
        //check that a team that doesn't exist get false
        assertFalse(EliLuzon.confirmTeamRequest(""));

        //4
        //check that a team that doesn't exist get false
        assertFalse(EliLuzon.confirmTeamRequest("HTA"));
    }

    @Test
    public void checkAddStadium(){

        //1
        //check that a regular stadium is being updated
        assertTrue(EliLuzon.createNewStadium("S1","200"));

        //2
        //check the stadium was added
        assertTrue(DB.getStadiums().containsKey("S1"));

        //3
        //see we can't add the same stadium again
        assertFalse(EliLuzon.createNewStadium("S1","200"));

        //4
        //see wa can't add a stadium with corrupt value
        assertFalse(EliLuzon.createNewStadium("","200"));

        //5
        assertFalse(EliLuzon.createNewStadium("S3",""));
    }


}
