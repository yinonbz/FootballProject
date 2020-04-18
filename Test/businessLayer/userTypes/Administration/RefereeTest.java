package businessLayer.userTypes.Administration;

import businessLayer.Tournament.Match.Match;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.MatchService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class RefereeTest {

    static DemoDB DB;
    static DataBaseValues tDB;
    static MatchService matchService;

    static Match m1;
    static Match m2;



    static Referee Rayola;


    @BeforeClass
    public static void defineValues(){
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        matchService = new MatchService();
        Rayola = (Referee)  DB.selectSubscriberFromDB("Rayola");
        m1 = DB.selectMatchFromDB(1);
        m2 = DB.selectMatchFromDB(2);



    }

    @Test
    //unit
    public void UT_checkSubmitReferee(){
        //1
        // check that a regular action of choosing referee works
        assertTrue(matchService.chooseMainReferee("Rayola","1","EliLuzon"));

        //1
        //check the update was executed
        assertTrue(m1.isMainReferee(Rayola));

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
        assertTrue(matchService.reportFoulThroughReferee("3","Pickford","Scholes","3","Rayola"));

        //2
        //checks yellow card event
        assertTrue(matchService.yellowCard("4","Salah","4","Alon"));

        //3
        //checks red card event
        assertTrue(matchService.reportOnInjury("4","Salah","4","Alon"));

        //4
        //checks injury
        assertTrue(matchService.reportOnRedCard("4","Mane","4","Alon"));

        //5
        //checks goal
        assertTrue(matchService.reportGoalThroughReferee("4","Mane","Salah","4","Alon"));

        //6
        //check substitute
        assertTrue(matchService.reportOnSubstitute("4","Firmino","Mane","4","Alon"));

        //7
        //check offside
        assertTrue(matchService.reportOffside("4","Firmino","4","Alon"));

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
        assertFalse(matchService.reportGoalThroughReferee("4","Mane","Rose","4","Alon"));

        //4
        //check two players from dif teams can't be substitute
        assertFalse(matchService.reportOnSubstitute("4","Firmino","Rose","4","Alon"));
    }


    @Test
    public void UC10_4_a(){
        //1
        //check that the main referee can remove an event from a game
        assertTrue(matchService.removeEventByMainReferee("5","4","Alon","0"));

        //2
        //check that the main referee can't remove an event from a game that doesn't exists
        assertFalse(matchService.removeEventByMainReferee("1","4","Alon","1"));

    }

    @Test
    public void UC10_4_b(){
        //3
        //check that whoever is not a main referee can't change the game after it finishes
        assertFalse(matchService.removeEventByMainReferee("4","4","Rayola","1"));

        //4
        //try to remove an event before the game finished
        assertFalse(matchService.removeEventByMainReferee("5","3","Rayola","1"));
    }


    //todo ido mutation test
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
    @Test
    public void IT_checkSetTraining(){
        Rayola.setTraining("nothing");
        assertEquals(Rayola.getTraining(),"nothing");
    }
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
}