package dataLayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class DBSeasonsTest {
    DBSeasons DB;

    @Before
    public void prepareDB(){
    DB = new DBSeasons();
        String LeagueID = "La Liga";
        int seasonID = 2018;
        Date d1 = new Date();
        Date d2 = new Date();
        HashMap<String, LinkedList<String>> details = new HashMap<>();
        LinkedList<String> rank = new LinkedList<>();
        rank.add("3");
        rank.add("0");
        rank.add("1");
        LinkedList <String> match = new LinkedList<>();
        match.add("ClassicalMatchPolicy");
        details.put("rankingPolicy",rank);
        details.put("matchingPolicy",match);
        //assertTrue(DB.addToDb(LeagueID,seasonID,details,d1,d2));
    }

    @After
    public void deleteValues(){
        DB.removeFromDB("La Liga",2018);
    }

    @Test
    public void containsSeason(){
        assertTrue(DB.containsInDB("Champions",2019));
        assertFalse(DB.containsInDB("Champions",2000));
    }


    @Test
    public void addRefereeToSeason(){
        assertTrue(DB.addRefereeInSeason("La Liga",2018,"Nelson"));
    }

    @Test
    public void addTeamInSeason(){
        assertTrue(DB.addTeamInSeason("La Liga",2018,"Barcelona"));
        assertTrue(DB.addTeamInSeason("La Liga", 2018, "Real Madrid"));
    }

    @Test
    public void addMatchToSeason(){
        LinkedList<Integer>  games = new LinkedList<>();
        games.add(200004);
        games.add(200005);
        assertTrue(DB.addMatchTableToSeason("La Liga",2018,games));
    }

    @Test
    public void updateSeasonMatchTable(){
        LinkedList<Integer> infoBarca = new LinkedList<>();
        infoBarca.add(1);
        infoBarca.add(2);
        infoBarca.add(1);
        infoBarca.add(3);
        assertTrue(DB.updateSeasonTable("La Liga",2018,"Barcelona",infoBarca));
        LinkedList<Integer> infoReal = new LinkedList<>();
        infoReal.add(1);
        infoReal.add(1);
        infoReal.add(2);
        infoReal.add(0);
        assertTrue(DB.updateSeasonTable("La Liga",2018,"Real Madrid",infoReal));
    }

}