package dataLayer;

import businessLayer.Team.Team;
import businessLayer.Tournament.*;
import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.SystemController;
import businessLayer.userTypes.viewers.Fan;
import dataLayer.DemoDB;

import java.util.Date;
import java.util.HashMap;

public class DataBaseValues {

    //static public DemoDB db;

    static DemoDB DB;

    //teams
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
    static Team BeerSheva;
    static Team Natanya;
    static Team Bnei_Yehuda;
    static Team LeedsUnited;
    static Team Sunderland;
    static Team MacabiHaifa;
    static Team HTA;

    //team owner
    static TeamOwner teamOwner;
    static TeamOwner Barkat;
    static TeamOwner Shimon;
    static TeamOwner Glazers;
    static TeamOwner Nissanov;
    static TeamOwner Max;
    static TeamOwner YaelM;
    static TeamOwner Alex;
    static TeamOwner Inon;

    //team manager
    static TeamManager itay;

    //referee
    static Referee Alon;

    //players
    static Player Buzaglo;

    //admins
    static Admin admin;
    static Admin admin2;

    //fan
    static Fan fan;


    //coaches
    static Coach tomer;
    static Coach Ido;

    //leagues and seasons
    static League primerLeague;
    static Season currSeason;
    static SystemController systemController;
    static Date startDate;
    static Date endDate;
    static HashMap<Integer, Team> teams;
    static SingleMatchPolicy singleMatchPolicy;
    static ClassicMatchPolicy classicMatchPolicy;
    static LeagueController leagueController;

    //stadiums
    static Stadium s1;
    static Stadium s2;
    static Stadium s3;
    static Stadium s4;
    static Stadium s5;
    static Stadium s6;
    static Stadium s7;
    static Stadium s8;
    static Stadium s9;
    static Stadium samiOfer;

    //policies
    static HashMap<Integer, Match> singleMatchTable;
    static HashMap<Integer, Match> classicTable;
    static MatchController matchController;
    static TeamOwner Jacob;

    //complaints
    static Complaint c1;
    static Complaint c2;
    static Complaint c3;

    //Association-Representatives
    static private AssociationRepresentative gal;
    static private AssociationRepresentative dor;
    static private AssociationRepresentative tali;
    static private AssociationRepresentative EliLuzon;

    public DataBaseValues() {
        DB = new DemoDB();

        systemController = SystemController.SystemController();
        leagueController = systemController.getLeagueController();
        matchController = new MatchController(systemController);


        //add Association-Representatives
        gal = new AssociationRepresentative("gal5", "1111", "Gal", new FinancialMonitoring("empty for now"), leagueController, systemController);
        dor = new AssociationRepresentative("dor12", "1111", "Dor", new FinancialMonitoring("empty for now"), leagueController, systemController);
        tali = new AssociationRepresentative("tali5", "1111", "Tali", new FinancialMonitoring("empty for now"), leagueController, systemController);
        EliLuzon = new AssociationRepresentative("EliLuzon", "abcd", "Eli", systemController);


        DB.addSubscriberToDB("gal5", gal);
        DB.addSubscriberToDB("dor12", dor);
        DB.addSubscriberToDB("tali5", tali);
        DB.addSubscriberToDB("EliLuzon", EliLuzon);

        //add stadiums

        s1 = new Stadium("s1", 200);
        s2 = new Stadium("s2", 300);
        s3 = new Stadium("s3", 400);
        s4 = new Stadium("s4", 600);
        s5 = new Stadium("s5", 700);
        s6 = new Stadium("s6", 800);
        s7 = new Stadium("s7", 900);
        s8 = new Stadium("s8", 1000);
        s9 = new Stadium("Default", 500);
        samiOfer = new Stadium("samiOfer", null, null, 30000, null);


        DB.addStadiumToDB("s1", s1);
        DB.addStadiumToDB("s2", s2);
        DB.addStadiumToDB("s3", s3);
        DB.addStadiumToDB("s4", s4);
        DB.addStadiumToDB("s5", s5);
        DB.addStadiumToDB("s6", s6);
        DB.addStadiumToDB("s7", s7);
        DB.addStadiumToDB("s8", s8);
        DB.addStadiumToDB("Default", s9);


        //add team owners
        teamOwner = new TeamOwner("Tomer", "helloWorld", "tomer", systemController);
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva", "alona", systemController);
        Shimon = new TeamOwner("Shimon", "beerSheva", "alona", systemController);
        Glazers = new TeamOwner("Glazers", "manchesterU", "glazer", systemController);
        Nissanov = new TeamOwner("Nissanov", "telAviv", "nissanov", systemController);
        Jacob = new TeamOwner("JacobS", "JacobS123", "Jacob", systemController);
        Inon = new TeamOwner("Inon","Inon123456", "Inon",systemController);
        YaelM = new TeamOwner("YaelM", "Sunderland", "alona", systemController);
        Max = new TeamOwner("Max", "telAviv", "Max", systemController);
        Alex = new TeamOwner("Alex", "manchesterU", "glazer", systemController);


        DB.addSubscriberToDB("Tomer", teamOwner);
        DB.addSubscriberToDB("AlonaBarkat", Barkat);
        DB.addSubscriberToDB("Shimon", Shimon);
        DB.addSubscriberToDB("Glazers", Glazers);
        DB.addSubscriberToDB("Nissanov", Nissanov);
        DB.addSubscriberToDB("JacobS", Jacob);
        DB.addSubscriberToDB("YaelM", YaelM);
        DB.addSubscriberToDB("Max", Max);
        DB.addSubscriberToDB("Alex", Alex);
        DB.addSubscriberToDB("Inon",Inon);

        //add admins
        admin = new Admin("TomerSein", "helloWorld", "tomer",systemController);
        admin2 = new Admin ("ItaiKatz", "helloWorld", "itai",systemController);

        DB.addSubscriberToDB("TomerSein",admin);
        DB.addSubscriberToDB("ItaiKatz",admin2);

        //add player
        Buzaglo = new Player("Buzaglo", "Buzaglo123", "Buzaglo", "1900", "midfield", null, systemController);

        DB.addSubscriberToDB("Buzaglo",Buzaglo);

        //add referee
        Alon = new Referee("Alon","Alon123456","Alon","main",null,systemController,matchController);
        DB.addSubscriberToDB("Alon",Alon);

        //add coach
        //6.1
        Ido = new Coach("efronio", "111", "ido", "attack", "mainCoach", systemController);
        tomer = new Coach("TomerZ", "111", "tomer", "defence", "subCoach", systemController);
        DB.addSubscriberToDB("efronio",Ido);
        DB.addSubscriberToDB("TomerZ",Ido);

        //add team manager
        itay = new TeamManager("itayK", "111", "itay",BeerSheva ,100, systemController);
        DB.addSubscriberToDB("itayK",itay);

        //add fan
        fan = new Fan ("Gate13","aviNimni","avi",systemController);
        DB.addSubscriberToDB("Gate13",fan);



        //add teams
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
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        Natanya = new Team("Natanya", Shimon, 1973);
        Bnei_Yehuda = new Team("Bnei Yehuda", Shimon, 1899);
        MacabiHaifa = new Team("McabiHaifa", Jacob, 1913);
        HTA = new Team("HTA", Inon,1990);
        LeedsUnited = new Team("LeedsUnited", Alex, 1899);
        Sunderland = new Team("Sunderland", YaelM, 1973);


        //setters for teams
        BeerSheva.setTeamId(123); //todo delete and change this fields
        ManchesterUnited.setTeamId(456);
        MacabiHaifa.setTeamId(789);

        //setter team-owners and teams
        Jacob.getTeams().add(MacabiHaifa);
        MacabiHaifa.getTeamOwners().add(Jacob);
        Barkat.getTeams().add(BeerSheva);
        Barkat.getTeams().add(ManchesterUnited);
        Inon.getTeams().add(HTA);
        Inon.getTeams().add(BeerSheva);
        Glazers.getTeams().add(ManchesterUnited);
        ManchesterUnited.getTeamOwners().add(Barkat);
        ManchesterUnited.getTeamOwners().add(Glazers);
        BeerSheva.getTeamOwners().add(Barkat);
        BeerSheva.getTeamOwners().add(Nissanov);
        LeedsUnited.getTeamOwners().add(YaelM);
        YaelM.getTeams().add(Sunderland);
        YaelM.getTeams().add(LeedsUnited);
        Alex.getTeams().add(LeedsUnited);
        HTA.getTeamOwners().add(Inon);

        //teams
        DB.addTeamToDB("ManchesterUnited",ManchesterUnited);
        DB.addTeamToDB("ManchesterCity",ManchersterCity);
        DB.addTeamToDB("NewCastle",NewCastle);
        DB.addTeamToDB("Tottenham",Tottenham);
        DB.addTeamToDB("AstonVilla",AstonVilla);
        DB.addTeamToDB("Liverpool",Liverpool);
        DB.addTeamToDB("Wolves",Wolves);
        DB.addTeamToDB("Everton",Everton);
        DB.addTeamToDB("Watford",Watford);
        DB.addTeamToDB("Southhampton",Southhampton);
        DB.addTeamToDB("Arsenal",Arsenal);
        DB.addTeamToDB("Chelsea",Chelsea);
        DB.addTeamToDB("Natanya",Natanya);
        DB.addTeamToDB("Bnei Yehuda",Bnei_Yehuda);
        DB.addTeamToDB("McabiHaifa",MacabiHaifa);
        DB.addTeamToDB("Beer Sheva", BeerSheva);
        DB.addTeamToDB("HTA",HTA);

        //complaints
        c1 = new Complaint("My system doesn't work",fan.getUsername());
        c2 = new Complaint("I don't like this team", fan.getUsername());

        DB.addComplaintToDB(0,c1);
        DB.addComplaintToDB(1,c2);

        systemController.connectToDB(DB);
    }

    /**
     * getter for a ready DB
     * @return
     */
    public DemoDB getDB (){
        return DB;
    }

}
