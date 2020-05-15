package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import businessLayer.userTypes.SystemController;
import dataLayer.DataBaseValues;
import serviceLayer.*;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;


public class SingleClassicMatchPolicyTest {

    static DemoDB DB;

    static SystemService systemService;
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
    static SystemController systemController;


    @BeforeClass
    public static void defineValues(){
        systemService = new SystemService();
        systemService.initializeSystem("admin");
        systemController = SystemController.SystemController();
        leagueController = systemController.getLeagueController();
        dataBaseValues = new DataBaseValues();
        //add leagues
        DB = dataBaseValues.getDB();
        primerLeague = new League("PriemerLeague");
        startDate = new Date();
        endDate = new Date ();
        currSeason = new Season(2,startDate,endDate, primerLeague, 3, 0, 1, "ClassicMatchPolicy");
        tempTeams = new HashMap<>();
        String [] teamNames = new String [] {"Arsenal","ManchesterUnited","ManchesterCity","Everton","Liverpool",
        "Wolves","Tottenham","Southhampton","NewCastle","AstonVilla","Chelsea","Watford"};
        for(int i=0;i<teamNames.length;i++){
            Team team = DB.selectTeamFromDB(teamNames[i]);
            tempTeams.put(teamNames[i],team);
        }

        singleMatchPolicy = new SingleMatchPolicy(tempTeams, primerLeague,currSeason);
        classicMatchPolicy = new ClassicMatchPolicy(tempTeams, primerLeague,currSeason);


    }

    @Test
    public void IT_checkPolicyOfSingleGame(){

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
                assertEquals(DB.selectStadiumFromDB("s3"),match.getStadium());
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
    public void IT_checkPolicyOfTwoGames(){
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
