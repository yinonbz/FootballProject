package dataLayer;

import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;

import static org.junit.Assert.*;

public class DBUnconfirmedTeamsTest {

    DBUnconfirmedTeams DB;

    @Before
    public void startValues(){
        DB = new DBUnconfirmedTeams();
    }

    @Test
    public void addRequest(){
 //       assertTrue(DB.addRequestForTeam("TheHackers",2019,"Bradley"));
    }

    @Test
    public void removeRequest(){
//        assertTrue(DB.removeFromDB("TheHackers"));
    }

    @Test
    public void selectRequest(){
        //HashMap<String, String> details = DB.selectUnconfirmedTeamFromDB("BGU Team");
        //assertEquals(details.get("year"),"2020");
        //assertEquals(details.get("owner"),"Irwin");

    }

    
}