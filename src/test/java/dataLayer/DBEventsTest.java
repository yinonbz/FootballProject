package dataLayer;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class DBEventsTest {

    DBEvents DB;

    @Before
    public void startValues(){
        DB = new DBEvents();
    }

    @Test
    public void addGoal(){
        HashMap <String, String> details = new HashMap<>();
        details.put("playerG","Lingard");
        details.put("playerA","Pogba");
        //assertTrue(DB.addEventToDB(200004,"15",333,"goal",details));
    }

    @Test
    public void addSubstitute(){
        HashMap <String, String> details = new HashMap<>();
        details.put("playerIn","Lingard");
        details.put("playerOut","Pogba");
        //assertFalse(DB.addEventToDB(200004,"10",33,"sub",details));
    }

    @Test
    public void addInjury(){
        HashMap <String, String> details = new HashMap<>();
        details.put("player","Lingard");
        //assertTrue(DB.addEventToDB(200004,"50",55,"injury",details));
    }

    @Test
    public void addRedCard(){
        HashMap <String, String> details = new HashMap<>();
        details.put("player","Lingard");
        //assertTrue(DB.addEventToDB(200004,"50",56,"redcard",details));
    }

    @Test
    public void addYellowCard(){
        HashMap <String, String> details = new HashMap<>();
        details.put("player","Lingard");
        //assertTrue(DB.addEventToDB(200004,"50",57,"yellowcard",details));
    }

    @Test
    public void addOffside(){
        HashMap <String, String> details = new HashMap<>();
        details.put("player","Lingard");
        //assertTrue(DB.addEventToDB(200004,"53",58,"offside",details));
    }

    @Test
    public void selectEvent(){
        //HashMap <String,String> details = DB.selectEventFromDB(200004,"53",58);
        //assertEquals("Lingard",details.get("player"));
    }


}