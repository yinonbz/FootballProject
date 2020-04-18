package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.SystemService;

import static org.junit.jupiter.api.Assertions.*;


public class EventRecordTest {

    static DemoDB DB;
    static DataBaseValues tDB;
    static SystemService systemService;
    static Match m2;
    static Player p1;
    static Player p2;
    static Player p3;
    static MatchController matchController;
    static EventRecord eventRecord;
    static YellowCard yellowCard;
    static Substitute substitute;
    static Injury injury;


    @BeforeClass
    public static void defineValues() {
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        systemService = new SystemService();
        m2 = DB.selectMatchFromDB(2);
        p1 = (Player)DB.selectSubscriberFromDB("Son");
        p2 = (Player)DB.selectSubscriberFromDB("Scholes");
        p3 = (Player)DB.selectSubscriberFromDB("Mane");
        matchController = new MatchController();
        eventRecord = m2.getEventRecord();
        yellowCard = new YellowCard(p1,matchController);
        substitute = new Substitute(p2,p3,matchController);
        injury = new Injury(p2,matchController);
    }

    @Test
    public void UT_checkAddEvent(){
        eventRecord.addEvent("5",yellowCard);
        eventRecord.addEvent("7",substitute);
        eventRecord.addEvent("5",injury);
        eventRecord.addEvent("67",null);
        assertEquals(2,eventRecord.getGameEvents().size());
    }

    @Test
    public void UT_checkRemoveEvent(){
        eventRecord.addEvent("5",yellowCard);
        eventRecord.addEvent("7",substitute);
        eventRecord.addEvent("5",injury);
        eventRecord.removeEvent("5",0);
        assertEquals(2,eventRecord.getGameEvents().size());
    }

}