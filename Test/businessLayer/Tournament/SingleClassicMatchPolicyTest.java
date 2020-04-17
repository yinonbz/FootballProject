package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleClassicMatchPolicyTest {

    static DemoDB DB;

    static HashMap <Integer, Match> singleMatchTable;
    static HashMap <Integer, Match> classicTable;
    static SingleMatchPolicy singleMatchPolicy;
    static ClassicMatchPolicy classicMatchPolicy;
    static LeagueController leagueController;
    static Date startDate;
    static Date endDate;
    static League primerLeague;
    static Season currSeason;
    static DataBaseValues dataBaseValues;
    static HashMap <String, Team> tempTeams;

    @BeforeClass
    public static void defineValues(){
        dataBaseValues = new DataBaseValues();

        //add leagues
        DB = dataBaseValues.getDB();
        primerLeague = new League("PriemerLeague");
        startDate = new Date();
        endDate = new Date ();
        currSeason = new Season(2,startDate,endDate, primerLeague);
        HashMap tempTeams = DB.getTeams();
        singleMatchPolicy = new SingleMatchPolicy(tempTeams, primerLeague,currSeason);
        classicMatchPolicy = new ClassicMatchPolicy(tempTeams, primerLeague,currSeason);



    }

    @Test
    public void checkPolicyOfSingleGame(){

        //1
        // checks the correct number of games were created
        assertEquals(66,singleMatchPolicy.activatePolicy(tempTeams,leagueController).size());

        singleMatchTable = singleMatchPolicy.activatePolicy(tempTeams,leagueController);
        //System.out.println(singleMatchPolicy.toString());

        for(Match match : singleMatchTable.values()){
            //2
            //check that the home stadium of is set well
            if(match.getHomeTeam().equals(DB.selectTeamFromDB("ManchesterUnited"))){
                assertEquals(DB.selectStadiumFromDB("s8"),match.getStadium());
            }
            //3
            //check the default stadium is set well
            else if(match.getHomeTeam().equals(DB.selectTeamFromDB("Watford")) &&
                    match.getAwayTeam().equals(DB.selectTeamFromDB("Arsenal"))){
                assertEquals(DB.selectStadiumFromDB("DEFAULT"),match.getStadium());
            }
            //4
            //check that the away stadium team is being choose well
            else if(match.getHomeTeam().equals(DB.selectTeamFromDB("ManchesterCity")) &&
                    match.getAwayTeam().equals(DB.selectTeamFromDB("NewCastle"))){
                assertEquals(DB.selectStadiumFromDB("s2"),match.getStadium());
            }
        }

    }

    @Test
    public void checkPolicyOfTwoGames(){
        //1
        // checks the correct number of games were created
        assertEquals(132,classicMatchPolicy.activatePolicy(tempTeams,leagueController).size());

        classicTable = classicMatchPolicy.activatePolicy(tempTeams,leagueController);

        int counterHome = 0;
        int counterAway = 0;
        int counterStadium = 0;
        for (Match match : classicTable.values()){
            if(match.getHomeTeam().equals(DB.selectTeamFromDB("ManchesterUnited"))){
                counterHome++;
                counterStadium++;
            }
            else if(match.getAwayTeam().equals(DB.selectTeamFromDB("ManchesterUnited"))){
                counterAway++;
            }
        }
        //2
        //check that each team has the same home and away games
        assertEquals(counterHome,counterAway);
        //3
        //check that the home stadium is set the right number of times
        assertEquals(counterStadium,counterHome);

        //System.out.println(classicTable.toString());
    }


}
