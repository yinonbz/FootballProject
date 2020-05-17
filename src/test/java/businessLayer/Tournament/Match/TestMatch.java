package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Referee;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import serviceLayer.MatchService;
import serviceLayer.SystemService;

public class TestMatch {

    static DemoDB DB;
    static DataBaseValues tDB;
    static SystemService systemService;
    static MatchService matchService;

    @BeforeClass
    public static void defineValues(){
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        systemService = new SystemService();
        matchService = new MatchService();
    }

    @Test

    public void UT_checkMainReferee(){
        Referee Rayola = (Referee)DB.selectSubscriberFromDB("Rayola");
        Referee Alon = (Referee)DB.selectSubscriberFromDB("Alon");
        Match m1 = DB.selectMatchFromDB(1);
        m1.chooseMainReferee(Rayola);
        assertTrue(m1.isMainReferee(Rayola));
        assertFalse(m1.isMainReferee(Alon));
    }

    @Test

    public void UT_checkMatchScoreUpdate(){

        matchService.reportGoalThroughReferee("7", "Firmino", "Mane","false", "4","Alon");
        matchService.reportGoalThroughReferee("11", "Son", "Rose","false", "4","Alon");
        Match match = DB.selectMatchFromDB(4);
        int [] score = match.getScore();
        assertEquals(1,score[0]);
        assertEquals(1,score[1]);
        matchService.reportGoalThroughReferee("7", "Firmino", "Mane","true", "4","Alon");
        score = match.getScore();
        assertEquals(2,score[1]);
        matchService.reportGoalThroughReferee("11", "Son", "Rose","true", "4","Alon");
        score = match.getScore();
        assertEquals(2,score[0]);
    }
}
