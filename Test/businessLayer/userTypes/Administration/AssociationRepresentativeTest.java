package businessLayer.userTypes.Administration;


import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.Test;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssociationRepresentativeTest {

    private AssociationRepresentative gal;
    private AssociationRepresentative dor;
    private AssociationRepresentative tali;

    static private DataBaseValues testingDBValues;
    static private DemoDB testingDB;

    //-------tomer's part-------------

    static TeamOwner Barkat;
    static AssociationRepresentative EliLuzon;


    @Before
    public void createTestValues() {

        testingDBValues = new DataBaseValues();
        testingDB = testingDBValues.getDB();
        gal = (AssociationRepresentative)testingDB.selectSubscriberFromDB("gal5");
        dor = (AssociationRepresentative)testingDB.selectSubscriberFromDB("dor12");
        tali = (AssociationRepresentative)testingDB.selectSubscriberFromDB("tali5");

        Barkat = (TeamOwner)testingDB.selectSubscriberFromDB("AlonaBarkat");
        Barkat.sendRequestForTeam("HapoelBeerSheva","1888");
        EliLuzon = (AssociationRepresentative)testingDB.selectSubscriberFromDB("EliLuzon");



    }

    @Test
    public void test_UC9_1() {
        //1. gal creates a new league successfully
        assertTrue(gal.createLeague("This is the first league created for the first test"));

        //2. dor tries to create the same league without success
        assertFalse(dor.createLeague("This is the first league created for the first test"));

        //3. tali tries to create a new league with null
        assertFalse((tali.createLeague(null)));
    }

    @Test
    public void test_UC9_2() {
        gal.createLeague("101");

        //1. gal creates a new season successfully
        assertTrue(gal.createSeason("101", 1, new Date(), new Date()));

        //2. dor tries to create the same season without success
        assertFalse(dor.createSeason("101", 1, new Date(), new Date()));

        //3. tali tries to create a season where the starting date is after the ending date
        assertFalse(tali.createSeason("102", 1, new Date(2000, 1, 11), new Date(2000, 1, 10)));
    }

    @Test
    public void test_UC9_3() {
        gal.createLeague("101");
        gal.createSeason("101", 1, new Date(), new Date());

        //1. gal creates a new referee successfully
        assertTrue(gal.createReferee("Bob"));

        //2. dor tries to create the same referee without success
        assertFalse(dor.createReferee("Bob"));

        //3. tali tries to create a referee with a null username field
        assertFalse(tali.createReferee(null));

        //4. gal removes a referee successfully
        assertTrue(gal.removeRefree("Bob"));

        //5. dor tries to remove a referee that doesn't exist in the data-base
        assertFalse(dor.removeRefree("Bob"));

        //6. tali tries to remove a referee with null username field
        assertFalse(tali.removeRefree(null));

        //7. gal tries to remove a username that isn't a referee
        //todo: need to add SystemController class functions to add more types of subscribers to create this test
    }

    @Test
    public void test_UC9_4() {
        gal.createLeague("101");
        gal.createSeason("101", 1, new Date(), new Date());
        gal.createSeason("101", 2, new Date(), new Date());
        gal.createReferee("Bob");
        gal.createReferee("Alice");

        //1. gal assigns bob to season 1
        assertTrue(gal.assignRefereeToSeason("Bob","101",1));

        //2. dor tries to assign same referee to season 1 unsuccessfully
        assertFalse(dor.assignRefereeToSeason("Bob","101",1));

        //3. gal assigns Bob to season number 2
        assertTrue(gal.assignRefereeToSeason("Bob","101",2));

        //4. gal assigns Alice to season 1
        assertTrue(gal.assignRefereeToSeason("Alice", "101", 1));

        //5. tali assigns Bob to null league
        assertFalse(tali.assignRefereeToSeason("Bob", null, 1));

        //6. tali assigns Bob to a non-existing season
        assertFalse(tali.assignRefereeToSeason("Bob", "101", 3));

        //7. tali assigns Bob to a non-existing league
        assertFalse(tali.assignRefereeToSeason("Bob", "102", 1));
    }

    @Test
    public void checkTeamConfirmation(){
        //1
        //check if a regular confirmation
        assertTrue(EliLuzon.confirmTeamRequest("HapoelBeerSheva"));

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
        assertTrue(testingDB.getStadiums().containsKey("S1"));

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
