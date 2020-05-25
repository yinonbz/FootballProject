package businessLayer.userTypes.Administration;

import businessLayer.Exceptions.NotApprovedException;
import businessLayer.Tournament.Match.Match;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.rules.ExpectedException;
import serviceLayer.MatchService;
import serviceLayer.SystemService;


public class RefereeTest {

    static DemoDB DB;
    static DataBaseValues tDB;
    static MatchService matchService;

    static Match m1;
    static Match m2;



    static Referee Rayola;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void defineValues(){
        /*
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        matchService = new MatchService();
        Rayola = (Referee)  DB.selectSubscriberFromDB("Rayola");
        m1 = DB.selectMatchFromDB(1);
        m2 = DB.selectMatchFromDB(2);

    */
        SystemService testingSystemService = new SystemService();
        testingSystemService.initializeSystem("admin");
        matchService = new MatchService();

    }

    @Test
    //unit
    public void UT_checkSubmitReferee(){
        //1
        // check that a regular action of choosing referee works
        //todo: check with new DB
        //assertTrue(matchService.chooseMainReferee("Rayola","1","EliLuzon"));

        //1
        //check the update was executed
        //todo: check with new DB
        //assertTrue(m1.isMainReferee(Rayola));

        //2
        //check that no one but AR can choose referee in the meantime
        assertFalse(matchService.chooseMainReferee("Rayola","2","TomerSein"));
        DB.addMatchToDB(2,m2);

        //check the update was not executed
        assertFalse(m2.isMainReferee(Rayola));

        //3
        //check you can't submit a user to a game that is not a referee
        assertFalse(matchService.chooseMainReferee("TomerSein","1","EliLuzon"));
    }


    @Test
    public void UC10_2_a(){
        //1
        //check the referee can see a match he is submitted to
        String ans = "Match id: " + "3" + "\n" + "Home: " + "ManchesterUnited" + "\n" + "Away: "
                + "Everton" + "\n" +
                "Stadium: " + "s3" + "\n" + "Date: " + "No information" +"\n" + "Referee: "+"Rayola";

      //  assertEquals(ans,matchService.viewMatchDetails("3","Rayola","Rayola"));
    }

    @Test
    public void UC10_2_b(){
        //2
        //check the referee can't see a match he is not submitted to
        assertEquals("",matchService.viewMatchDetails("2","Rayola","Rayola"));
    }

    @Test
    public void UC10_2_c(){
        //3
        //can't watch a match that the system doesn't contain
        assertEquals("",matchService.viewMatchDetails("-2","Rayola","Rayola"));

    }

    @Test
    public void UC10_2_d(){
        //4
        //check that no one beside referee can see the match details with this function
        assertEquals("",matchService.viewMatchDetails("2","TomerSein","Rayola"));
    }


    @Test
    public void UC10_3(){



    }

    @Test
    public void UC10_3_a(){
        //1
        //checks that the referee can add an event to a game he is submitted to
        //todo: check with new DB
        assertTrue(matchService.reportFoulThroughReferee("119","Fred","Cillessen","200003","Love"));

        //2
        //checks yellow card event
        //todo: check with new DB
        //assertTrue(matchService.yellowCard("4","Salah","4","Alon"));

        //3
        //checks red card event
        //todo: check with new DB
        //assertTrue(matchService.reportOnInjury("4","Salah","4","Alon"));

        //4
        //checks injury
        //todo: check with new DB
        //assertTrue(matchService.reportOnRedCard("4","Mane","4","Alon"));

        //5
        //checks goal
        //todo: check with new DB
        //assertTrue(matchService.reportGoalThroughReferee("4","Mane","Salah", "true","4","Alon"));

        //6
        //check substitute
        //todo: check with new DB
        //assertTrue(matchService.reportOnSubstitute("4","Firmino","Mane","4","Alon"));

        //7
        //check offside
        //todo: check with new DB
        //assertTrue(matchService.reportOffside("4","Firmino","4","Alon"));

    }

    @Test
    public void UC10_3_b(){

        //1
        //checks that the referee can't add an event to a game that he is not submitted to
        assertFalse(matchService.reportFoulThroughReferee("2","Mane","Son","4","Rayola"));
    }

    @Test
    public void UC10_3_c(){
        //1
        //checks that the referee can't add an event to a game that he is not submitted to
        assertFalse(matchService.reportFoulThroughReferee("-3","Mane","Son","4","Rayola"));
    }


    @Test
    public void UC10_3_d(){
        //1
        //checks that the referee can't add a foul on two team members from the same team
        assertFalse(matchService.reportFoulThroughReferee("4","Salah","Mane","4","Alon"));

        //2
        //checks that the referee can't add a foul on two team members from the same team
        assertFalse(matchService.reportFoulThroughReferee("5","Salah","Mane","4","Alon"));

        //3
        //checks a player from other team can't assist in a goal
        assertFalse(matchService.reportGoalThroughReferee("4","Mane","Rose", "T","4","Alon"));

        //4
        //check two players from dif teams can't be substitute
        assertFalse(matchService.reportOnSubstitute("4","Firmino","Rose","4","Alon"));

        //5

        //checks a player from other team can't assist in a goal
        assertFalse(matchService.reportGoalThroughReferee("4","Mane","Rose", "G","4","Alon"));
    }


    @Test
    public void UC10_4_a(){
        //1
        //check that the main referee can remove an event from a game
        //todo: check with new DB
        //assertTrue(matchService.removeEventByMainReferee("5","4","Alon","0"));

        //2
        //check that the main referee can't remove an event from a game that doesn't exists
        assertFalse(matchService.removeEventByMainReferee("1","4","Alon","1"));

    }

    @Test
    public void UC10_4_b(){
        //3
        //check that whoever is not a main referee can't change the game after it finishes
        //todo: check with new DB
        //expectedException.expect(NotApprovedException.class);
        //matchService.removeEventByMainReferee("4","4","Rayola","1");

        //4
        //try to remove an event before the game finished
        assertFalse(matchService.removeEventByMainReferee("5","3","Rayola","1"));
    }


    @Test
    public void IT_checkFalseSubnit(){
        assertFalse(Rayola.isSubmittedToAGame("TOMER"));
    }
    @Test
    public void checkViewMatchDetails(){
       // assertEquals(Rayola.viewMatchDetails(3),"Match id: 3\n" + "Home: ManchesterUnited\n" + "Away: Everton\n" + "Stadium: s3\n" + "Date: No information\n" + "Referee: Rayola");
    }
    @Test
    public void IT_checkGetName(){
        Rayola.setName("RayolaR");
        assertEquals(Rayola.getName(),"RayolaR");
        Rayola.setName("Rayola");
    }
    /*
    @Test
    public void IT_checkSetTraining(){
        Rayola.setTraining("nothing");
        assertEquals(Rayola.getTraining(),"nothing");
    }
    */
    @Test
    public void IT_checkLeaguesController(){
        Rayola.setLeaguesController(null);
        assertEquals(Rayola.getLeaguesController(),null);
    }
    @Test
    public void IT_checkMatchController(){
        Rayola.setMatchController(null);
        assertEquals(Rayola.getMatchController(),null);
    }
    /**not working correctly
    @Test
    public void checkRemoveFromAllMatches(){
        Match m3 = DB.selectMatchFromDB(3);
        List<Referee> refereeList = new ArrayList<>();
        refereeList.add(Rayola);
        m3.setReferees(refereeList);
        assertTrue(Rayola.removeFromAllMatches());
        Rayola.setName("ido");
        assertFalse(Rayola.removeFromAllMatches());
        Rayola.setName("Rayola");
    }
    */
    @Test
    public void updateDetails(){
        Rayola.setName("RayolaL");
        assertEquals(Rayola.getName(),"RayolaL");
    }
}