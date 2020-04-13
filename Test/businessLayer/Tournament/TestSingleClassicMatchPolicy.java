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
        ManchersterCity.setStadium(s1);
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
    public void checkPolicy(){

        //1
        // checks the correct number of games were created
        assertEquals(66,singleMatchPolicy.activatePolicy(teams,leagueController).size());

        //2
        //check the team that has no stadium plays in the default stadium


        //3
        //check the the team that has only away stadium plays in the away stadium
    }


}
