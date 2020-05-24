package dataLayer;

import businessLayer.Team.Team;
import businessLayer.Tournament.*;
import businessLayer.Tournament.Match.*;
import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.SystemController;
import businessLayer.userTypes.viewers.Fan;
import dataLayer.DemoDB;
import serviceLayer.SystemService;

import java.util.*;

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
    static Team BeitarJerusalem;

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
    static TeamOwner Jacob;
    static TeamOwner piqueF;

    static TeamOwner Jimmy;
    static TeamOwner Harry;

    //team manager
    static TeamManager itay;
    static TeamManager klopp;
    static TeamManager pep;
    static TeamManager Ronaldinio;


    //referee
    static Referee Alon;
    static Referee Rayola;

    //players
    static Player Buzaglo;
    static Player Scholes;
    static Player Pickford;
    static Player Mane;
    static Player Son;
    static Player Salah;
    static Player Firmino;
    static Player Rose;
    static Player Tamash;
    static Player yosi;
    static Player Oded;
    static Player Yaniv;
    static Player Amir;
    static Player pique;
    static Player Jovani;

    //admins
    static Admin admin;
    static Admin admin2;

    //fan
    static Fan fan;
    static Fan Ben;


    //coaches
    static Coach Tomer;
    static Coach Ido;

    //leagues and seasons
    static League primerLeague;
    static Season currSeason;
    static Season season1;
    static League league1;
    static SystemController systemController;
    static Date startDate;
    static Date endDate;
    static HashMap<Integer, Team> teams;
    static SingleMatchPolicy singleMatchPolicy;
    static ClassicMatchPolicy classicMatchPolicy;
    static LeagueController leagueController;
    static RankingPolicy rankingPolicy;

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
    static Stadium Tedi;


    //policies
    static HashMap<Integer, Match> singleMatchTable;
    static HashMap<Integer, Match> classicTable;
    static MatchController matchController;
    static SystemService systemService;

    //complaints
    static Complaint c1;
    static Complaint c2;
    static Complaint c3;

    //Association-Representatives
    static private AssociationRepresentative gal;
    static private AssociationRepresentative dor;
    static private AssociationRepresentative tali;
    static private AssociationRepresentative EliLuzon;

    //Matches
    static Match m1;
    static Match m2;
    static Match m3;
    static Match m4;
    static Match m5;
    static Match m6;

    //events
    static YellowCard e1;
    static  RedCard e2;
    static  Injury e3;
    static TeamManager valverde;
    static TeamOwner gerrard;


    //season
    static Season season11;
    static League league2;
    static League league3;

    public DataBaseValues() {
        DB = new DemoDB();

        systemService = new SystemService();
        systemController = SystemController.SystemController();
        systemController.initializeSystem("admin");
        leagueController = systemController.getLeagueController();
        matchController = new MatchController(); // todo change to getter of controller


        //add Association-Representatives
        gal = new AssociationRepresentative("gal5", "1111", "Gal", new FinancialMonitoring("empty for now"), leagueController, systemController);
        gal.setApproved(true);
        dor = new AssociationRepresentative("dor12", "1111", "Dor", new FinancialMonitoring("empty for now"), leagueController, systemController);
        dor.setApproved(true);
        tali = new AssociationRepresentative("tali5", "1111", "Tali", new FinancialMonitoring("empty for now"), leagueController, systemController);
        tali.setApproved(true);
        EliLuzon = new AssociationRepresentative("EliLuzon", "abcd", "Eli", systemController);
        EliLuzon.setApproved(true);


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
        samiOfer = new Stadium("samiOfer", null, 30000, null);
        Tedi = new Stadium("Tedi", null, 30000, null);


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
        piqueF = new TeamOwner("piqueF", "111", "piqueF", systemController);

        Jimmy = new TeamOwner("Jimmy","jj","jimmy",systemController);
        Harry = new TeamOwner("Harry","jj","Harry",systemController);
        gerrard = new TeamOwner("gerrard","jj","Harry",systemController);


        DB.addSubscriberToDB("Tomer", teamOwner);
        DB.addSubscriberToDB("gerrard", gerrard);
        DB.addSubscriberToDB("AlonaBarkat", Barkat);
        DB.addSubscriberToDB("Shimon", Shimon);
        DB.addSubscriberToDB("Glazers", Glazers);
        DB.addSubscriberToDB("Nissanov", Nissanov);
        DB.addSubscriberToDB("JacobS", Jacob);
        DB.addSubscriberToDB("YaelM", YaelM);
        DB.addSubscriberToDB("Max", Max);
        DB.addSubscriberToDB("Alex", Alex);
        DB.addSubscriberToDB("Inon",Inon);
        DB.addSubscriberToDB("Jimmy",Jimmy);
        DB.addSubscriberToDB("Harry",Harry);
        DB.addSubscriberToDB("piqueF",piqueF);

        //add admins
        admin = new Admin("TomerSein", "helloWorld", "tomer",systemController);
        admin2 = new Admin ("ItaiKatz", "helloWorld", "itai",systemController);
        admin.setApproved(true);
        admin2.setApproved(true);

        DB.addSubscriberToDB("TomerSein",admin);
        DB.addSubscriberToDB("ItaiKatz",admin2);
        DB.addSubscriberToDB("Inon",Inon);

        //add player
        Buzaglo = new Player("Buzaglo", "Buzaglo123", "Buzaglo", "1900", FIELDJOB.valueOf("RB"),0, null, systemController);
        Tamash = new Player("Tamash","Tamash123","Tamash","1983",FIELDJOB.valueOf("CF"),0,null,systemController);
        Jovani = new Player("Jovani","Jovani123","Jovani","1975",FIELDJOB.valueOf("CF"),0,null,systemController);
        yosi = new Player("yosi","yosi123","yosiDekel", "1993",FIELDJOB.valueOf("CF"),0,null,systemController);
        Yaniv = new Player("Yaniv","Yaniv123","YanivKatan", "1993",FIELDJOB.valueOf("CF"),0,null,systemController);
        Oded = new Player("Oded","Oded123","OdedShahar", "1993",FIELDJOB.valueOf("CF"),0,null,systemController);
        Amir = new Player("Amir","Amir123","AmirBenHaion", "1993",FIELDJOB.valueOf("CF"),0,null,systemController);
        pique = new Player("pique","111","pique","1983",FIELDJOB.valueOf("CF"),0,null,systemController);
        pique.setTeamOwner(piqueF);
        piqueF.setOriginalObject(pique);

        DB.addSubscriberToDB("pique",pique);
        DB.addSubscriberToDB("Buzaglo",Buzaglo);
        DB.addSubscriberToDB("Tamash",Tamash);
        DB.addSubscriberToDB("Jovani",Jovani);
        DB.addSubscriberToDB("yosi",yosi);
        DB.addSubscriberToDB("Yaniv",Yaniv);
        DB.addSubscriberToDB("Oded",Oded);
        DB.addSubscriberToDB("Amir",Amir);
        DB.addSubscriberToDB("pique",pique);


        //add referee
        Alon = new Referee("Alon","Alon123456","Alon", roleRef.valueOf("MAIN"),leagueController,systemController);
        DB.addSubscriberToDB("Alon",Alon);
        Rayola = new Referee ("Rayola", "1223", "Rayola",roleRef.valueOf("MAIN"),leagueController,systemController);
        DB.addSubscriberToDB("Rayola",Rayola);

        //add coach
        //6.1
        Ido = new Coach("efronio", "111", "ido", RoleInTeam.valueOf("GENERAL"),TRAINING.valueOf("GK"), "mainCoach",0, systemController);
        Tomer = new Coach("TomerZ", "111", "tomer", RoleInTeam.valueOf("GENERAL"),TRAINING.valueOf("FITNESS"), "subCoach",0, systemController);
        DB.addSubscriberToDB("efronio",Ido);
        DB.addSubscriberToDB("TomerZ",Tomer);

        //add team manager
        itay = new TeamManager("itayK", "111", "itay",null ,100, systemController);
        itay.setPermissions(Permissions.FINANCE);
        DB.addSubscriberToDB("itayK",itay);

        klopp = new TeamManager("kloppJ", "111", "klopp",null ,100, systemController);
        klopp.setPermissions(Permissions.FINANCE);
        DB.addSubscriberToDB("kloppJ",klopp);

        pep = new TeamManager("pepG", "111", "pep",null ,100, systemController);
        pep.setPermissions(Permissions.FINANCE);
        DB.addSubscriberToDB("pepG",pep);

        //add fan
        fan = new Fan ("Gate13","aviNimni","avi",systemController);
        Ben = new Fan ("Ben","1234","ben",systemController);
        DB.addSubscriberToDB("Gate13",fan);
        DB.addSubscriberToDB("Ben",Ben);



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
        Watford = new Team ("Watford", Harry,1888);
        Southhampton = new Team ("Southhampton", teamOwner,1888);
        Arsenal = new Team ("Arsenal", teamOwner,1888);
        Chelsea = new Team ("Chelsea", teamOwner,1888);
        Chelsea.setStadium(s1);
        BeerSheva = new Team("BeerSheva", Barkat,1973);
        Natanya = new Team("Natanya", Shimon, 1973);
        Bnei_Yehuda = new Team("Bnei Yehuda", Shimon, 1899);
        MacabiHaifa = new Team("McabiHaifa", Jacob, 1913);
        HTA = new Team("HTA", Inon,1990);
        LeedsUnited = new Team("LeedsUnited", Alex, 1899);
        Sunderland = new Team("Sunderland", YaelM, 1973);
        BeitarJerusalem = new Team("BeitarJerusalem",Jacob,1960);


        //setters for teams

        //setter team-owners and teams
        Jacob.getTeams().add(MacabiHaifa);
        Jacob.getTeams().add(BeitarJerusalem);
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
        LeedsUnited.getTeamOwners().add(Alex);
        Tottenham.getTeamOwners().add(piqueF);
        Liverpool.getTeamOwners().add(gerrard);
        gerrard.getTeams().add(Liverpool);

        YaelM.getTeams().add(Sunderland);
        YaelM.getTeams().add(LeedsUnited);
        Alex.getTeams().add(LeedsUnited);
        HTA.getTeamOwners().add(Inon);
        Jimmy.getTeams().add(Arsenal);
        Arsenal.getTeamOwners().add(Jimmy);
        Arsenal.getTeamOwners().add(Alex);

        //add player
        Buzaglo = new Player("Buzaglo", "Buzaglo123", "Buzaglo", "1900", FIELDJOB.valueOf("AM"),0, null, systemController);
        Scholes = new Player("Scholes","ssss","Scholes","2222",FIELDJOB.valueOf("DM"),300,ManchesterUnited,systemController);
        Pickford = new Player("Pickford","ssss","Pickford","2222",FIELDJOB.valueOf("GK"),300,Everton,systemController);
        Son = new Player("Son","ssss","Son","2222",FIELDJOB.valueOf("S"),300,Tottenham,systemController);
        Mane = new Player("Mane","ssss","Son","2222",FIELDJOB.valueOf("S"),300,Liverpool,systemController);
        Salah = new Player("Salah","ssss","Salah","2222",FIELDJOB.valueOf("S"),400,Liverpool,systemController);
        Firmino = new Player("Firmino","ssss","Firmino","2222",FIELDJOB.valueOf("S"),400,Liverpool,systemController);
        Rose = new Player("Rose","ssss","Rose","2222",FIELDJOB.valueOf("S"),400,Tottenham,systemController);


        ManchesterUnited.getPlayers().add(Scholes);
        Everton.getPlayers().add(Pickford);
        Liverpool.getPlayers().add(Salah);
        Liverpool.getPlayers().add(Mane);
        Liverpool.getPlayers().add(Firmino);
        Tottenham.getPlayers().add(Son);
        Tottenham.getPlayers().add(Rose);



        DB.addSubscriberToDB("Buzaglo",Buzaglo);
        DB.addSubscriberToDB("Scholes",Scholes);
        DB.addSubscriberToDB("Pickford",Pickford);
        DB.addSubscriberToDB("Son",Son);
        DB.addSubscriberToDB("Mane",Mane);
        DB.addSubscriberToDB("Salah",Salah);
        DB.addSubscriberToDB("Rose",Rose);
        DB.addSubscriberToDB("Firmino",Firmino);


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
        DB.addTeamToDB("BeerSheva", BeerSheva);
        DB.addTeamToDB("HTA",HTA);
        DB.addTeamToDB("LeedsUnited",LeedsUnited);
        DB.addTeamToDB("BeitarJerusalem",BeitarJerusalem);

        //add team manager
        itay = new TeamManager("itayK", "111", "itay",null ,0, systemController);
        Ronaldinio = new TeamManager("Ronaldinio", "111", "Ronaldinio",null ,0, systemController);
        valverde = new TeamManager("valverde","111","valverde",Liverpool,100,systemController);
        DB.addSubscriberToDB("Ronaldinio",Ronaldinio);;
        DB.addSubscriberToDB("itayK",itay);
        DB.addSubscriberToDB("valverde",valverde);
        Liverpool.setTeamManager(valverde);
        gerrard.getTeamManagers().put(Liverpool,valverde);


        //complaints
        c1 = new Complaint("My system doesn't work",fan.getUsername());
        c2 = new Complaint("I don't like this team", fan.getUsername());

        DB.addComplaintToDB(0,c1);
        DB.addComplaintToDB(1,c2);


        //add Stadium todo ido move this code to this line need to be careful in merge!!!!!!
        DB.addStadiumToDB("Tedi",Tedi);
        DB.addStadiumToDB("samiOfer",samiOfer);
        DB.addStadiumToDB("s1",s1);
        DB.addStadiumToDB("s2",s2);
        DB.addStadiumToDB("s3",s3);
        DB.addStadiumToDB("s4",s4);
        DB.addStadiumToDB("s5",s5);
        DB.addStadiumToDB("s6",s6);
        DB.addStadiumToDB("s7",s7);
        DB.addStadiumToDB("s8",s8);
        DB.addStadiumToDB("Default",s9);

        //matches
        m1 = new Match(Chelsea,Liverpool,s1);
        m2 = new Match (ManchersterCity,ManchesterUnited,s2);
        m3 = new Match (ManchesterUnited,Everton,s3);
        m4 = new Match (Liverpool,Tottenham,s2);
        m5 = new Match(BeitarJerusalem,BeerSheva,Tedi);
        m6 = new Match(BeitarJerusalem,BeerSheva,Tedi);
        m5.setNumerOfFans(70);
        m6.setNumerOfFans(100);

        //m3.setDate(startDate);
        Rayola.getRefMatches().put(3,m3);
        m3.chooseMainReferee(Rayola);
        //List<Referee> RefereeList = new ArrayList<>();
        //RefereeList.add(Rayola);
        //m3.setReferees(RefereeList);
        m4.chooseMainReferee(Alon);
        Alon.getRefMatches().put(4,m4);

        e1 = new YellowCard(Son,matchController);
        e2 = new RedCard(Salah,matchController);
        e3 = new Injury(Rose,matchController);
        m4.getEventRecord().addEvent("5",e1);
        m4.getEventRecord().addEvent("6",e2);
        m4.getEventRecord().addEvent("5",e3);
        m4.setFinished(true);
        m4.chooseMainReferee(Alon);
        m3.getEventRecord().addEvent("5",e3);
        m3.chooseMainReferee(Rayola);


        DB.addMatchToDB(1,m1);
        DB.addMatchToDB(2,m2);
        DB.addMatchToDB(3,m3);
        DB.addMatchToDB(4,m4);
        DB.addMatchToDB(5,m5);
        DB.addMatchToDB(6,m6);
        Tedi.setPreviousMatches(DB.getMatches());

        startDate = new Date();
        endDate = new Date();
        //seasons
        rankingPolicy = new ARankingPolicy(3,0,1);
        league1 = new League("11");
        DB.addLeagueToDB("11" ,league1);
        league2 = new League("12");
        league2.addSeasonToLeague(2020,startDate,endDate,3,0,1,"SingleMatchPolicy");
        Season season2020 =league2.getSeasonFromLeague("2020");
        HashMap <Integer, Match> table = new HashMap<>();
        table.put(4,m4);
        season2020.setMatchesOfTheSeason(table);
        league3 = new League("13");
        league3.addSeasonToLeague(2020,startDate,endDate,3,0,1,"SingleMatchPolicy");
        DB.addLeagueToDB("13",league3);

        DB.addLeagueToDB("12" ,league2);
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
