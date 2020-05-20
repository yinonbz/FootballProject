package businessLayer.userTypes.Administration;


import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.Exceptions.MissingInputException;
import businessLayer.Exceptions.NotApprovedException;
import businessLayer.Exceptions.NotFoundInDbException;
import businessLayer.Tournament.League;
import businessLayer.userTypes.SystemController;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import serviceLayer.LeagueService;
import serviceLayer.SystemService;


import java.util.Date;
import java.util.LinkedList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class AssociationRepresentativeTest {

    private AssociationRepresentative gal;
    private AssociationRepresentative dor;
    private AssociationRepresentative tali;
    private LeagueService testingLeagueService;
    private SystemService testingSystemService;
    private League l1;
    private SystemController systemController;

    static private DataBaseValues testingDBValues;
    static private DemoDB testingDB;

    //-------tomer's part-------------

    static TeamOwner Barkat;
    static AssociationRepresentative EliLuzon;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void createTestValues() {

        testingSystemService = new SystemService();
        testingSystemService.initializeSystem("admin");
        testingLeagueService = new LeagueService();
        testingDBValues = new DataBaseValues();
        testingDB = testingDBValues.getDB();
        gal = (AssociationRepresentative) testingDB.selectSubscriberFromDB("gal5");
        dor = (AssociationRepresentative) testingDB.selectSubscriberFromDB("dor12");
        tali = (AssociationRepresentative) testingDB.selectSubscriberFromDB("tali5");
        systemController = SystemController.SystemController();
        Barkat = (TeamOwner) testingDB.selectSubscriberFromDB("AlonaBarkat");
        systemController.sendRequestForTeam("HapoelBeerSheva1", "1888","Tomer");
        LinkedList <String> temp = new LinkedList<>();
        temp.add("HapoelBeerSheva1");
        temp.add("1888");
        temp.add("Tomer");
        testingDB.addUnconfirmedTeamsToDB("HapoelBeerSheva",temp);

        //Barkat.sendRequestForTeam("HapoelBeerSheva", "1888");
        EliLuzon = (AssociationRepresentative) testingDB.selectSubscriberFromDB("EliLuzon");

        l1 = testingDB.selectLeagueFromDB("11");

    }

    @Test
    public void test_UC9_1() {
        //1. gal creates a new league successfully
        //assertTrue(gal.createLeague("This is the first league created for the first test"));
        assertTrue(testingLeagueService.addLeagueThroughRepresentative("This is the first league created for the first test", "gal5"));

        //2. dor tries to create the same league without success
        expectedException.expect(AlreadyExistException.class);
        assertFalse(testingLeagueService.addLeagueThroughRepresentative("This is the first league created for the first test", "dor12"));

        //3. tali tries to create a new league with null
        assertFalse(testingLeagueService.addLeagueThroughRepresentative(null, "tali5"));
    }

    @Test
    public void test_UC9_2() {
        testingLeagueService.addLeagueThroughRepresentative("101", "gal5");

        //1. gal creates a new season successfully
        assertTrue(testingLeagueService.addSeasonThroughRepresentative("101", 2000, new Date(), new Date(), 5, 1, 3, "ClassicMatchPolicy", "gal5"));

        //2. dor tries to create the same season without success
        expectedException.expect(AlreadyExistException.class);
        testingLeagueService.addSeasonThroughRepresentative("101", 2000, new Date(), new Date(), 5, 1, 3, "ClassicMatchPolicy", "dor12");

        //3. tali tries to create a season where the starting date is after the ending date
        assertFalse(testingLeagueService.addSeasonThroughRepresentative("102", 2005, new Date(2000, 1, 11), new Date(2000, 1, 10), 5, 1, 3, "ClassicMatchPolicy", "tali5"));
    }

    @Test
    public void test_UC9_3() {
        testingLeagueService.addLeagueThroughRepresentative("101", "gal5");
        testingLeagueService.addSeasonThroughRepresentative("101", 1, new Date(), new Date(), 5, 1, 3, "ClassicMatchPolicy", "gal5");

        //1. gal creates a new referee successfully
        assertTrue(testingLeagueService.createRefereeThroughRepresentative("Bob", "gal5"));

        //2. dor tries to create the same referee without success
        assertFalse(testingLeagueService.createRefereeThroughRepresentative("Bob", "dor12"));

        //3. tali tries to create a referee with a null username field
        assertFalse(testingLeagueService.createRefereeThroughRepresentative(null, "tali5"));

        //4. gal removes a referee successfully
        assertTrue(testingLeagueService.removeRefereeThroughRepresentative("Bob", "gal5"));

        //5. dor tries to remove a referee that doesn't exist in the data-base
        assertFalse(testingLeagueService.removeRefereeThroughRepresentative("Bob", "dor12"));

        //6. tali tries to remove a referee with null username field
        assertFalse(testingLeagueService.removeRefereeThroughRepresentative(null, "tali5"));

        //7. gal tries to remove a username that isn't a referee
        //todo: need to add SystemController class functions to add more types of subscribers to create this test
    }

    @Test
    public void test_UC9_4() {
        testingLeagueService.addLeagueThroughRepresentative("101", "gal5");
        testingLeagueService.addSeasonThroughRepresentative("101", 2000, new Date(), new Date(), 5, 1, 3, "ClassicMatchPolicy", "gal5");
        testingLeagueService.addSeasonThroughRepresentative("101", 2001, new Date(), new Date(), 5, 1, 3, "ClassicMatchPolicy", "gal5");
        testingLeagueService.createRefereeThroughRepresentative("Bob", "gal5");
        testingLeagueService.createRefereeThroughRepresentative("Alice", "gal5");

        //1. gal assigns bob to season 1
        assertTrue(testingLeagueService.assignRefereeThroughRepresentative("Bob", "101", 2000, "gal5"));

        //2. dor tries to assign same referee to season 1 unsuccessfully
        assertFalse(testingLeagueService.assignRefereeThroughRepresentative("Bob", "101", 2000, "dor12"));

        //3. gal assigns Bob to season number 2
        assertTrue(testingLeagueService.assignRefereeThroughRepresentative("Bob", "101", 2001, "gal5"));

        //4. gal assigns Alice to season 1
        assertTrue(testingLeagueService.assignRefereeThroughRepresentative("Alice", "101", 2000, "gal5"));

        //5. tali assigns Bob to null league
        expectedException.expect(MissingInputException.class);
        testingLeagueService.assignRefereeThroughRepresentative("Bob", null, 2001, "tali5");

        //6. tali assigns Bob to a non-existing season
        assertFalse(testingLeagueService.assignRefereeThroughRepresentative("Bob", "101", 2007, "tali5"));

        //7. tali assigns Bob to a non-existing league
        assertFalse(testingLeagueService.assignRefereeThroughRepresentative("Bob", "102", 2000, "tali5"));
    }

    @Test
    public void UC9_5_a(){
        Date d1 = new Date();
        Date d2 = new Date();
        assertTrue(testingLeagueService.addSeasonThroughRepresentative("11",2020,d1,d2,3,0,1, "ClassicMatchPolicy","gal5"));
    }

    @Test
    public void UC9_5_b(){
        Date d1 = new Date();
        Date d2 = new Date();
        expectedException.expect(MissingInputException.class);
        testingLeagueService.addSeasonThroughRepresentative("11",2021,d1,d2,3,0,1, "ClassicMatchPolicy","gal15");
    }

    @Test
    public void UC9_5_c(){
        Date d1 = new Date();
        Date d2 = new Date();
        expectedException.expect(MissingInputException.class);
        testingLeagueService.addSeasonThroughRepresentative("11",2020,d1,d2,3,0,1, "ClassicMatchPolicy","Alon");
    }

    @Test
    public void UC9_6_a(){
        Date d1 = new Date();
        Date d2 = new Date();
        assertTrue(testingLeagueService.addSeasonThroughRepresentative("11",2020,d1,d2,3,0,1, "SingleMatchPolicy","gal5"));
        LinkedList<String> teamsName = new LinkedList<>();
        teamsName.add("ManchesterUnited");
        teamsName.add("Everton");
        teamsName.add("Liverpool");
        teamsName.add("Chelsea");
        testingLeagueService.chooseTeamForSeason(teamsName,"13","2020","gal5");
        expectedException.expect(NotApprovedException.class);
        testingLeagueService.activateMatchPolicyForSeason("13","2020","gal5");
    }

    @Test
    public void UC9_6_b(){
        Date d1 = new Date();
        Date d2 = new Date();
        assertFalse(testingLeagueService.addSeasonThroughRepresentative("11",2020,d1,d2,3,0,1, "Hello","gal5"));

        expectedException.expect(NotApprovedException.class);
        testingLeagueService.activateMatchPolicyForSeason("12","2020","gal5");
    }

    @Test
    public void UT_checkAddTeams(){
        LinkedList<String> teamsName = new LinkedList<>();
        teamsName.add("ManchesterUnited");
        teamsName.add("Everton");
        teamsName.add("Liverpool");
        teamsName.add("Chelsea");
        assertTrue(testingLeagueService.chooseTeamForSeason(teamsName,"12","2020","gal5"));
    }




    @Test
    public void checkTeamConfirmation() {
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
    public void checkAddStadium() {

        //1
        //check that a regular stadium is being updated
        assertTrue(EliLuzon.createNewStadium("S1", "200"));

        //2
        //check the stadium was added
        assertTrue(testingDB.getStadiums().containsKey("S1"));

        //3
        //see we can't add the same stadium again
        assertFalse(EliLuzon.createNewStadium("S1", "200"));

        //4
        //see wa can't add a stadium with corrupt value
        assertFalse(EliLuzon.createNewStadium("", "200"));

        //5
        assertFalse(EliLuzon.createNewStadium("S3", ""));
    }
}
