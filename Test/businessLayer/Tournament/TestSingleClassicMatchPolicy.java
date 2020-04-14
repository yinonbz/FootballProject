package businessLayer.Tournament;


import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.userTypes.Administration.TeamOwner;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.LeagueController;
import serviceLayer.SystemController;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSingleClassicMatchPolicy {

    static Team ManchesterUnited;
     static Team ManchersterCity;
     static Team NewCastle;
     static Team Tottenham;
     static Team AstonVilla;
     static Team Liverpool;
     static Team Wolves;
     static Team Everton;
     static Team Watford;
     static Team Southhampton;
     static Team Arsenal;
     static Team Chelsea;
     static TeamOwner teamOwner;
     static League primerLeague;
     static Season currSeason;
     static SystemController systemController;
     static Date startDate;
     static Date endDate;
     static HashMap <Integer,Team> teams;
     static SingleMatchPolicy singleMatchPolicy;
     static ClassicMatchPolicy classicMatchPolicy;
     static LeagueController leagueController;
     static Stadium s1;
     static Stadium s2;
     static Stadium s3;
     static Stadium s4;
     static Stadium s5;
     static Stadium s6;
     static Stadium s7;
     static Stadium s8;
     static Stadium s9;
     static HashMap <Integer, Match> singleMatchTable;
     static HashMap <Integer, Match> classicTable;


    @BeforeClass
    public static void defineValues(){
        systemController = SystemController.SystemController();
        leagueController = new LeagueController(systemController);
        s1 = new Stadium("s1",200);
        s2 = new Stadium("s2",300);
        s3 = new Stadium("s3",400);
        s4 = new Stadium("s4",600);
        s5 = new Stadium("s5",700);
        s6 = new Stadium("s6",800);
        s7 = new Stadium("s7",900);
        s8 = new Stadium("s8",1000);
        teamOwner = new TeamOwner("Tomer","helloWorld","tomer",systemController);
        ManchesterUnited = new Team("ManchesterUnited",teamOwner,1888);
        ManchesterUnited.setStadium(s8);
        ManchersterCity = new Team("ManchesterCity",teamOwner,1888);
        NewCastle = new Team ("NewCastle", teamOwner,1888);
        NewCastle.setStadium(s2);
        Tottenham = new Team ("Tottenham", teamOwner,1888);
        Tottenham.setStadium(s3);
        AstonVilla = new Team ("AstonVilla", teamOwner,1888);
        AstonVilla.setStadium(s4);
        Liverpool = new Team ("Liverpool", teamOwner,1888);
        Liverpool.setStadium(s5);
        Wolves = new Team ("Wolves", teamOwner,1888);
        Wolves.setStadium(s6);
        Everton = new Team ("Everton", teamOwner,1888);
        Everton.setStadium(s7);
        Watford = new Team ("Watford", teamOwner,1888);
        Southhampton = new Team ("Southhampton", teamOwner,1888);
        Arsenal = new Team ("Arsenal", teamOwner,1888);
        Chelsea = new Team ("Chelsea", teamOwner,1888);
        Chelsea.setStadium(s1);
        primerLeague = new League ("PriemerLeague",1);
        s9 = new Stadium("Default",500);
        //systemController.getStadiums().put(0,s9);
        //leagueController.getStadiums().put(0,s9);
        startDate = new Date();
        endDate = new Date ();
        currSeason = new Season (2,startDate,endDate, primerLeague);
        teams = new HashMap<>();
        teams.put(0,ManchesterUnited);
        teams.put(1,ManchersterCity);
        teams.put(2,NewCastle);
        teams.put(3,Tottenham);
        teams.put(4,AstonVilla);
        teams.put(5,Liverpool);
        teams.put(6,Wolves);
        teams.put(7,Everton);
        teams.put(8,Watford);
        teams.put(9,Southhampton);
        teams.put(10,Arsenal);
        teams.put(11,Chelsea);
        singleMatchPolicy = new SingleMatchPolicy(teams, primerLeague,currSeason);
        classicMatchPolicy = new ClassicMatchPolicy(teams, primerLeague,currSeason);

    }

    @Test
    public void checkPolicyOfSingleGame(){

        //1
        // checks the correct number of games were created
        assertEquals(66,singleMatchPolicy.activatePolicy(teams,leagueController).size());

        singleMatchTable = singleMatchPolicy.activatePolicy(teams,leagueController);
        //System.out.println(singleMatchPolicy.toString());

        for(Match match : singleMatchTable.values()){
            //2
            //check that the home stadium of is set well
            if(match.getHomeTeam().equals(ManchesterUnited)){
                assertEquals(s8,match.getStadium());
            }
            //3
            //check the default stadium is set well
            else if(match.getHomeTeam().equals(Watford) && match.getAwayTeam().equals(Arsenal)){
                assertEquals(s9,match.getStadium());
            }
            //4
            //check that the away stadium team is being choose well
            else if(match.getHomeTeam().equals(ManchersterCity) && match.getAwayTeam().equals(NewCastle)){
                assertEquals(s2,match.getStadium());
            }
        }

    }

    @Test
    public void checkPolicyOfTwoGames(){
        //1
        // checks the correct number of games were created
        assertEquals(132,classicMatchPolicy.activatePolicy(teams,leagueController).size());

        classicTable = classicMatchPolicy.activatePolicy(teams,leagueController);

        int counterHome = 0;
        int counterAway = 0;
        int counterStadium = 0;
        for (Match match : classicTable.values()){
            if(match.getHomeTeam().equals(ManchesterUnited)){
                counterHome++;
                counterStadium++;
            }
            else if(match.getAwayTeam().equals(ManchesterUnited)){
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
