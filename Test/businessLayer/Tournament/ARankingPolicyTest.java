package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ARankingPolicyTest {

    private DataBaseValues tDB;
    private DemoDB DB;
    private HashMap<Team, LinkedList<Integer>> rankTable;
    private Season s;
    private ARankingPolicy aRankingPolicy;
    Match m1;
    Match m2;
    Match m3;
    Match m4;
    Team Chelsea;
    Team Liverpool;
    Team ManchesterUnited;
    Team Tottenham;
    Team ManchesterCity;
    Team Everton;

    @Before
    public void createTestValues() {
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        //rankTable = new HashMap<>();
        Chelsea = DB.selectTeamFromDB("Chelsea");
        Liverpool = DB.selectTeamFromDB("Liverpool");
        ManchesterUnited = DB.selectTeamFromDB("ManchesterUnited");
        Tottenham = DB.selectTeamFromDB("Tottenham");
        ManchesterCity = DB.selectTeamFromDB("ManchesterCity");
        Everton = DB.selectTeamFromDB("Everton");
        HashMap <String, Team> teams = new HashMap<>();
        teams.put("Chelsea",Chelsea);
        teams.put("Liverpool",Liverpool);
        teams.put("ManchesterUnited",ManchesterUnited);
        teams.put("Tottenham",Tottenham);
        teams.put("ManchesterCity",ManchesterCity);
        teams.put("Everton",Everton);
        Date d1 = new Date();
        Date d2 = new Date();
        League l1 = new League ("1st League");
        s = new Season (2020,d1,d2,l1);
        s.setTeams(teams);
        int [] score1 = new int [] {1,2};
        int [] score2 = new int [] {0,0};
        int [] score3 = new int [] {2,1};
        int [] score4 = new int [] {3,0};

        m1 = DB.selectMatchFromDB(1);
        m2 = DB.selectMatchFromDB(2);
        m3 = DB.selectMatchFromDB(3);
        m4 = DB.selectMatchFromDB(4);
        m1.setScore(score1);
        m2.setScore(score2);
        m3.setScore(score3);
        m4.setScore(score4);
        aRankingPolicy = new ARankingPolicy(3,0,1);
        s.setRankingPolicy(aRankingPolicy);
        s.initializeTable();
        rankTable = s.getLeagueTable();

    }

    @Test
    public void IT_checkMatchRank(){

        /*
        m1 = new Match(Chelsea,Liverpool,s1);
        m2 = new Match (ManchersterCity,ManchesterUnited,s2);
        m3 = new Match (ManchesterUnited,Everton,s3);
        m4 = new Match (Liverpool,Tottenham,s2);
        int [] score1 = new int [] {1,2};
        int [] score2 = new int [] {0,0};
        int [] score3 = new int [] {2,1};
        int [] score4 = new int [] {3,0};
     */
        aRankingPolicy.updateRank(m1, rankTable);
        LinkedList<Integer> infoC = rankTable.get(Chelsea);
        assertEquals(1,infoC.get(0)); // num of games
        assertEquals(1,infoC.get(1)); // num of goals F
        assertEquals(2,infoC.get(2)); //num for goals A
        assertEquals(0,infoC.get(3)); //num of points
        LinkedList <Integer> infoL = rankTable.get(Liverpool);
        assertEquals(1,infoL.get(0)); // num of games
        assertEquals(2,infoL.get(1)); // num of goals F
        assertEquals(1,infoL.get(2)); //num for goals A
        assertEquals(3,infoL.get(3)); //num of points
        aRankingPolicy.updateRank(m2, rankTable);
        LinkedList<Integer> infoMU = rankTable.get(ManchesterUnited);
        assertEquals(1,infoMU.get(0)); // num of games
        assertEquals(0,infoMU.get(1)); // num of goals F
        assertEquals(0,infoMU.get(2)); //num for goals A
        assertEquals(1,infoMU.get(3)); //num of points
        aRankingPolicy.updateRank(m3,rankTable);
        infoMU = rankTable.get(ManchesterUnited);
        assertEquals(2,infoMU.get(0)); // num of games
        assertEquals(2,infoMU.get(1)); // num of goals F
        assertEquals(1,infoMU.get(2)); //num for goals A
        assertEquals(4,infoMU.get(3)); //num of points
    }
}