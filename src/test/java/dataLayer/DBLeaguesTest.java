package dataLayer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class DBLeaguesTest {

    DBLeagues DB;

    @Before
    public void startDB(){
        DB = new DBLeagues();
        DB.addToDb("Ligue1"); //works

    }

    @Test
    public void containsLeagueInDB(){
        //assertFalse(DB.containInDB("Ligue11"));
        //assertTrue(DB.containInDB("Champions"));
    }


    @Test
    public void selectFromDB(){
        Map<String, ArrayList<String>> leagueID = DB.selectFromDB("Itai's_League",null,null);
        int k=0;

    }

    @Test
    public void deleteFromDB(){
       // assertTrue(DB.removeFromDB("Ligue1"));
    }

}