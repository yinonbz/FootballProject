package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Referee;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.SystemService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMatch {

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

    public void checkMainReferee(){
        Referee Rayola = (Referee)DB.selectSubscriberFromDB("Rayola");
        Referee Alon = (Referee)DB.selectSubscriberFromDB("Alon");
        Match m1 = DB.selectMatchFromDB(1);
        m1.chooseMainReferee(Rayola);
        assertTrue(m1.isMainReferee(Rayola));
        assertFalse(m1.isMainReferee(Alon));
    }
}
