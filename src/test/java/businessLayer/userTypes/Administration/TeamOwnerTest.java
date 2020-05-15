package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.Test;
import businessLayer.userTypes.SystemController;
import serviceLayer.SystemService;
import serviceLayer.TeamService;

import static org.junit.Assert.*;

public class TeamOwnerTest {


    private TeamOwner Barkat;
    private TeamOwner Nissanov;
    private TeamOwner Jacob;
    private TeamOwner Glazers;
    private TeamOwner Inon;
    private TeamOwner Jimmy;
    private TeamOwner Alex;
    private TeamOwner teamOwner;
    private TeamOwner gerrard;

    //private TeamOwner Alex;
    private TeamOwner piqueF;
    private TeamManager klopp;
    private TeamManager pep;
    private TeamManager valverde;


    private Player Buzaglo;
    private Player Tamash;
    private Player Jovani;
    private Player Roso; //This Player will not be in the DB
    private Player yosi;
    private Player pique;

    private Referee Alon;

    private Team HTA;
    private Team BeerSheva;
    private Team Barca; //This Team will not be in the DB
    private Team Arsenal;
    private Team Liverpool;

    private Team LeedsUnited; //This Team will not be in the DB

    private DemoDB DB;
    private DataBaseValues tDB;


    private TeamService teamService;
    private SystemService systemService;
    private SystemController systemController;

    @Before
    public void UT_createTestValues() {
       // systemController = SystemController.SystemController();
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        Barkat = (TeamOwner) DB.selectSubscriberFromDB("AlonaBarkat");
        Nissanov = (TeamOwner) DB.selectSubscriberFromDB("Nissanov");
        Jacob = (TeamOwner) DB.selectSubscriberFromDB("JacobS");
        Glazers = (TeamOwner) DB.selectSubscriberFromDB("Glazers");
        Inon = (TeamOwner) DB.selectSubscriberFromDB("Inon");
        piqueF = (TeamOwner) DB.selectSubscriberFromDB("piqueF");
        Buzaglo = (Player) DB.selectSubscriberFromDB("Buzaglo");
        Tamash = (Player) DB.selectSubscriberFromDB("Tamash");
        Jovani = (Player) DB.selectSubscriberFromDB("Jovani");
        yosi = (Player) DB.selectSubscriberFromDB("yosi");
        pique = (Player) DB.selectSubscriberFromDB("pique");
        Alon = (Referee) DB.selectSubscriberFromDB("Alon");
        Jimmy = (TeamOwner) DB.selectSubscriberFromDB("Jimmy");
        Alex = (TeamOwner) DB.selectSubscriberFromDB("Alex");
        teamOwner = (TeamOwner) DB.selectSubscriberFromDB("Tomer");
        gerrard = (TeamOwner) DB.selectSubscriberFromDB("gerrard");

        BeerSheva = DB.selectTeamFromDB("BeerSheva");
        pep = (TeamManager) DB.selectSubscriberFromDB("pepG");
        LeedsUnited = DB.selectTeamFromDB("LeedsUnited");
        Alex = (TeamOwner) DB.selectSubscriberFromDB("Alex");

        BeerSheva = DB.selectTeamFromDB("BeerSheva");
        HTA = DB.selectTeamFromDB("HTA");
        klopp= (TeamManager)DB.selectSubscriberFromDB("kloppJ");
        valverde=(TeamManager)DB.selectSubscriberFromDB("valverde");

        Arsenal = DB.selectTeamFromDB("Arsenal");
        Liverpool = DB.selectTeamFromDB("Liverpool");

        teamService = new TeamService();
        systemService = new SystemService();
        systemController = SystemController.SystemController();

    }
    @Test
    public void UC6_1_1_a() {
        //adding a player to the team successfully
        assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Player", "yosi"));
        //adding a coach to the team successfully
        assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));
        //adding a teamManager to the team successfully
        assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //adding a stadium to the team successfully
        assertTrue(teamService.addAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));


    }
    @Test
    public void UC6_1_1_b() {
        //adding a player to the team
        assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Player", "yosi"));
        //a player that was already assign to the team
        assertFalse(teamService.addAsset("AlonaBarkat","BeerSheva", "Player", "yosi"));
        //a team that doesnt exist at the teamOwner
        assertFalse(teamService.addAsset("Nissanov","ManchesterUnited", "Player", "Buzaglo"));

        //adding a coach to the team
        assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));
        //failed to a coach that was already assign to the team
        assertFalse(teamService.addAsset("Nissanov","BeerSheva", "Coach", "efronio"));

        //adding a teamOwner to the team
        assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //failed to a teamOwner that was already assign to the team
        assertFalse(teamService.addAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //failed to a teamOwner to a team that was already assign to a different team
        assertFalse(teamService.addAsset("Nissanov","BeerSheva", "TeamManager", "itayK"));

        //adding a stadium to the team
        assertTrue(teamService.addAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
        //failed to a teamOwner to a team that was already assign to a different team
        assertFalse(teamService.addAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
    }

    @Test
    public void UC6_1_2_a() {
        Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        Barkat.addAsset("BeerSheva", "Coach", "efronio");
        Jacob.addAsset("McabiHaifa", "TeamManager", "itayK");
        Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        //edit a player from the team successfully
        assertTrue(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "birthDate", "9/11"));
        assertTrue(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "fieldJob", "DM"));
        assertTrue(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "salary", "10000"));

        //edit a Coach from the team successfully
        assertTrue(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "training", "GK"));
        assertTrue(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "teamJob", "mainCoach"));
        assertTrue(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "salary", "10000"));

        //edit a TeamManager from the team successfully
        assertTrue(teamService.editTeamManager("JacobS","McabiHaifa", "itayK", "salary", 100000));

        //edit a Stadium from the team successfully
        assertTrue(teamService.editStadium("JacobS","McabiHaifa", "samiOfer", "numberOfSeats", 50));

    }
    @Test
    public void UC6_1_2_b() {
        Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        Barkat.addAsset("BeerSheva", "Coach", "efronio");
        Jacob.addAsset("McabiHaifa", "TeamManager", "itayK");
        Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        //fail to edit Asset that was deleted

        Barkat.deleteAsset("BeerSheva", "Player", "Buzaglo");
        assertFalse(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "birthDate", "9/20"));
        assertFalse(teamService.editPlayer("AlonaBarkat","BeerSheva", "ido", "birthDate", "9/20"));

        Barkat.deleteAsset("BeerSheva", "Coach", "efronio");
        assertFalse(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "teamJob", "SubCoach"));
        assertFalse(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "training", "attacker"));

        Jacob.deleteAsset("McabiHaifa", "TeamManager", "itayK");
        assertFalse(teamService.editTeamManager("JacobS","McabiHaifa", "itayK", "salary", 20));

        Jacob.deleteAsset("McabiHaifa", "Stadium", "samiOfer");
        assertFalse(teamService.editStadium("JacobS","McabiHaifa", "samiOfer", "numberOfSeats", 50));
        //a stadium that doesnt exist in the system
        assertFalse(teamService.editStadium("JacobS","McabiHaifa", "natania", "numberOfSeats", 50));
    }

    @Test
    public void UC6_1_3_a() {
        Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        Barkat.addAsset("BeerSheva", "Coach", "efronio");
        Barkat.addAsset("BeerSheva", "TeamManager", "itayK");
        Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        //delete a player from the team successfully
        assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Player", "Buzaglo"));

        //delete a Coach from the team successfully
        assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));

        //delete a TeamManager from the team successfully
        assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));

        //delete a Stadium from the team successfully
        assertTrue(teamService.deleteAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
    }
    @Test
    public void UC6_1_3_b() {
        Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        Barkat.addAsset("BeerSheva", "Coach", "efronio");
        Barkat.addAsset("BeerSheva", "TeamManager", "itayK");
        Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Player", "Buzaglo"));
        //fail to delete a player that was already deleted
        assertFalse(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Player", "Buzaglo"));
        //fail to delete a player that doesnt exist in the team
        assertFalse(teamService.deleteAsset("Nissanov","ManchesterUnited", "Player", "Buzaglo"));

        assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));
        //fail to delete a Coach that was already deleted
        assertFalse(teamService.deleteAsset("Nissanov","BeerSheva", "Coach", "efronio"));


        assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //fail to delete a TeamManager that was already deleted
        assertFalse(teamService.deleteAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //fail to delete a TeamManager that doesnt exist in the team
        assertFalse(teamService.deleteAsset("JacobS","McabiHaifa", "TeamManager", "itayK"));


        assertTrue(teamService.deleteAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
        //fail to delete a Stadium that was already deleted
        assertFalse(teamService.deleteAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
        //fail to delete a Stadium that doesnt exist in the team
        assertFalse(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Stadium", "samiOfer"));
    }

    // a flow of the uc 6_1
    @Test
    public void UC6_1() {
        //test if the team owner successfully able to add a asset to his team
        assertTrue(teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));
        //test if the team owner unable to add an asset that already exist in his team
        assertFalse(teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));

        //test if the team owner unable to add an asset that not exist in the System
        assertFalse(teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "ido"));

        //test if the team owner successfully able to delete a asset from his team
        assertTrue(teamService.deleteAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));
        //test if the team owner unable to delete a asset from his team that doesnt exist anymore
        assertFalse(teamService.deleteAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));

        //test if the team owner successfully able to edit a detail of a asset
        teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo");
        assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "birthDate", "11/9/93"));
        assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "fieldJob", "DM"));
        assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "salary", "20000"));
        //test if the
        assertFalse(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "training", "20000"));

        teamService.deleteAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo");
        //test if team owner try to edit a team player that doesnt exist
        assertFalse(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "birthDate", "11/9/93"));
    }

    @Test
    public void UC_6_2_a() {
        //Test 1 - add Successfully
        assertTrue(teamService.appoinTeamOwnerToTeam("BeerSheva","Tamash","AlonaBarkat"));
    }

    @Test
    public void UC_6_2_b() {
        //Test - 2 - Try to add a Player which does not exists in the DB
        assertFalse(teamService.appoinTeamOwnerToTeam("BeerSheva","Roso","AlonaBarkat"));
    }

    @Test
    public void UC_6_2_c(){
        //Test - 3 -Try and Fail to add someone which is already a team owner.
        assertFalse(teamService.appoinTeamOwnerToTeam("BeerSheva","Glazers","AlonaBarkat"));
    }


    @Test
    public void UC_6_3_a(){
        Barkat.appointToOwner(Tamash, "BeerSheva");
        assertTrue(teamService.removeOwner("AlonaBarkat","BeerSheva","Tamash"));
    }

    @Test
    public void UC_6_3_b(){
        assertFalse(teamService.removeOwner("AlonaBarkat","BeerSheva","Roso"));
        assertFalse(teamService.removeOwner("AlonaBarkat","BeerSheva","Yosi"));
    }

    @Test
    public void UC6_4_a(){
        //all good
        assertTrue(teamService.addManager("Inon","kloppJ","GENERAL","HTA","100"));
        //check if add succeeded
        assertFalse(teamService.addManager("Inon","kloppJ","GENERAL","HTA","100"));

    }

    @Test
    public void UC6_4_b(){
        //all good
        assertTrue(teamService.addManager("Inon","kloppJ","GENERAL","HTA","100"));
        //add same manager again and adding manager to occupied team
        assertFalse(teamService.addManager("Inon","kloppJ","GENERAL","HTA","100"));
    }

    @Test
    public void UC6_4_c(){
        //wrong username
        assertFalse(teamService.addManager("Inon","kloppJU","GENERAL","HTA","100"));
    }

    @Test
    public void UC6_4_d(){
        //try assign a teamManager
        assertFalse(teamService.addManager("Inon","AlonaBarkat","GENERAL","HTA","100"));
    }

    @Test
    public void UC6_5_a(){
        //all good
        assertTrue(teamService.fireManager("gerrard","valverde","Liverpool"));

        //check
        assertFalse(teamService.fireManager("gerrard","valverde","Liverpool"));

    }

    @Test
    public void UC6_5_b(){
        //fire teamowner
        assertFalse(teamService.fireManager("gerrard","AlonaBarkat","Liverpool"));
        //not manager of the team
        assertFalse(teamService.fireManager("gerrard","kloppJ","Liverpool"));
        //wrong username
        assertFalse(teamService.fireManager("gerrard","kloppJU","Liverpool"));
    }

    @Test
    public void UC6_5_c(){
        //all good
        assertTrue(teamService.fireManager("gerrard","valverde","Liverpool"));

        //try fire the same manager again
        assertFalse(teamService.fireManager("gerrard","valverde","Liverpool"));
    }


    @Test
    //UNIT TEST
    public void UT_checkExclusiveTeamOwner(){
        //1 - UNIT
        //check if Alona who has 2 teams is exclusive

        assertFalse(Jimmy.isExclusiveTeamOwner());

        //2
        //check if Alona is now Exclusive
        Arsenal.getTeamOwners().remove(Alex);
        Arsenal.getTeamOwners().remove(teamOwner);
        Alex.getTeams().remove(Arsenal);

        assertTrue(Jimmy.isExclusiveTeamOwner());

        //3
        //check what happens without any teams
        BeerSheva.getTeamOwners().remove(Nissanov);
        Nissanov.getTeams().remove(BeerSheva);
        assertFalse(Nissanov.isExclusiveTeamOwner());
    }



    @Test
    public void UT_isFictive() {

        assertFalse(Nissanov.isFictive());
        Nissanov.setOriginalObject(Buzaglo);
        assertTrue(Nissanov.isFictive());
    }

    @Test
    public void UT_checkTeamRequest() {
        //1
        //check if we get true on a normal request

        assertTrue(systemService.sendRequestForTeam("natania","1948","Tomer"));

        //2
        //check if we get a false on a not valid year
        assertFalse(systemService.sendRequestForTeam("natania","","Tomer"));

        //3
        //check if we get a false on not valid name
        assertFalse(systemService.sendRequestForTeam("","1948","Tomer"));

    }

    /**
     * Unit Test - enterMember(String userName))
     */
    @Test
    public void UT_enterMember() {
        assertEquals(Barkat.enterMember("Glazers"), Glazers); //Try to search a subscriber
        assertNull(Barkat.enterMember("Itay")); //Search a team member which in not exist in the system.

    }

    /**
     * Unit Test - enterMember(String teamName))
     */
    @Test
    public void UT_appointToOwner() {
        assertFalse(Barkat.appointToOwner(Buzaglo, "Manchester")); //Try and Fail to add to a team which you don't own.
        assertTrue(Barkat.appointToOwner(Buzaglo, "BeerSheva")); //Try to add successfully.
        assertFalse(Barkat.appointToOwner(Glazers, "BeerSheva")); //Try and Fail to add someone which is already a team owner.
        assertFalse(Barkat.appointToOwner(Alon, "BeerSheva")); //Try and Fail to add someone, when you are not a Player, a Coach or a Team Manager.
    }


    /**
     * Unit Test - getTeam(String teamName)
     */
    @Test
    public void UT_getTeam() {
        assertEquals(Inon.getTeam("BeerSheva"), BeerSheva);
        assertNull(Inon.getTeam("NAS"));
        assertEquals(Inon.getTeam("HTA"), HTA);
        assertNotEquals(Inon.getTeam("HTA"), BeerSheva);
    }

    /**
     * Unit Test - changeStatus(Team team)
     */
    @Test
    public void UT_changeStatus() {
        //enabled to disabled
        Inon.disableStatus(BeerSheva);
        assertFalse(BeerSheva.getActive());

        //disabled to enabled
        Inon.enableStatus(BeerSheva);
        assertTrue(BeerSheva.getActive());
    }

    @Test
    public void UC6_6_1_a() {
        //Test - 1 - Disable successfully
        assertTrue(teamService.disableTeamStatus("ManchesterUnited", "Glazers"));
    }

    @Test
    public void UC6_6_1_b() {
        //Test - 2 - Try to disable a Team status which does not exists in the DB
        assertFalse(teamService.disableTeamStatus("Barca", "Glazers"));
    }

    @Test
    public void UC6_6_1_c() {
        //Test - 3 - Try to disable an already disabled team
        teamService.disableTeamStatus("ManchesterUnited", "Glazers");
        assertFalse(teamService.disableTeamStatus("ManchesterUnited", "Glazers"));
    }

    @Test
    public void UC6_6_2_a() {
        //Test - 4 - Enable successfully
        teamService.disableTeamStatus("ManchesterUnited","Glazers");
        assertTrue(teamService.enableTeamStatus("ManchesterUnited", "Glazers"));
    }

    @Test
    public void UC6_6_2_b() {
        //Test - 5 - Try to enable a Team status which does not exists in the DB
        assertFalse(teamService.enableTeamStatus("Barca", "Glazers"));
    }

    @Test
    public void UC6_6_2_c() {
        //Test - 6 - Try to enable an already disabled team
        assertFalse(teamService.enableTeamStatus("ManchesterUnited", "Glazers"));
    }

    @Test
    public void UT_addManager() {
        TeamManager itay= (TeamManager) DB.selectSubscriberFromDB("itayK");
        BeerSheva.setTeamManager(itay);
        itay.setTeam(BeerSheva);
        //try assign manager to a team that not belong to me
        assertFalse(Alex.addManager("pepG", Permissions.GENERAL, BeerSheva, 200));
        //manager already has team
        assertFalse(Alex.addManager("itayK", Permissions.GENERAL, LeedsUnited, 200));
        //all good
        assertTrue(Alex.addManager("pepG", Permissions.GENERAL, LeedsUnited, 1000));
        //add again same manager
        assertFalse(Alex.addManager("pepG", Permissions.GENERAL, LeedsUnited, 1000));

    }

    @Test
    public void UT_fireManager() {

        //try fire manager from team that not belong to me
        assertFalse(Alex.fireManager("itayK", BeerSheva));
        //not my manager
        assertFalse(Alex.fireManager("itayK", LeedsUnited));
        //try delete pep withut assigning him via Alex
        LeedsUnited.setTeamManager(pep);
        pep.setTeam(LeedsUnited);
        assertFalse(Alex.fireManager("pepG", LeedsUnited));
        //all good
        Alex.getTeamManagers().put(LeedsUnited, pep);
        assertTrue(Alex.fireManager("pepG", LeedsUnited));
        //manager has no team
        assertFalse(Alex.fireManager("pepG", LeedsUnited));
        //add again same manager

    }

    @Test
    public void UT_getOriginalObject() {
        assertEquals(piqueF.getOriginalObject(),DB.selectSubscriberFromDB("pique"));
    }

    @Test
    public void UT_setOriginalObject() {
        piqueF.setOriginalObject(null);
        assertEquals(piqueF.getOriginalObject(), null);
    }

    @Test
    public void UT_equals() {
        assertTrue(piqueF.equals(DB.selectSubscriberFromDB("piqueF")));
        assertTrue(piqueF.equals((TeamOwner) DB.selectSubscriberFromDB("piqueF")));
        assertFalse(piqueF.equals(DB.selectSubscriberFromDB("pepG")));
        assertFalse(piqueF.equals(DB.selectSubscriberFromDB("Alex")));
        assertFalse(piqueF.equals(null));
    }

   @Test
   public void UT_testRemoveOwner(){
    /*   Barkat.appointToOwner(Tamash, "BeerSheva");
       assertTrue(Tamash.getTeamOwner().getTeams().contains(BeerSheva));
       assertTrue(Barkat.removeOwner(Tamash,"BeerSheva"));
*/
       Barkat.appointToOwner(Tamash, "BeerSheva");
       Tamash.getTeamOwner().appointToOwner(Jovani,"BeerSheva");
       assertTrue(Tamash.getTeamOwner().getTeams().contains(BeerSheva));
       assertTrue(Jovani.getTeamOwner().getTeams().contains(BeerSheva));
       assertTrue(Barkat.removeOwner(Tamash,"BeerSheva"));
       assertNull(Tamash.getTeamOwner());
       assertNull(Jovani.getTeamOwner());
   }

    @Test
    public void UC6_7_a() {
        //add all pf the asset and set their salary
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "TeamManager", "Ronaldinio"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Amir"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Oded"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Yaniv"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Coach", "TomerZ"));
        assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Amir", "salary", "10000"));
        assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Oded", "salary", "12000"));
        assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Yaniv", "salary", "13000"));
        assertTrue(teamService.editCoach("JacobS", "McabiHaifa", "TomerZ", "salary", "15500"));
        assertTrue(teamService.editTeamManager("JacobS", "McabiHaifa", "Ronaldinio", "salary", 20000));
        //check all of the salary off all the asset and sum tham
        assertEquals(teamService.reportExpanse("JacobS", "McabiHaifa"), 70500);

        assertTrue(Jacob.addAsset("BeitarJerusalem", "Stadium", "Tedi"));

        assertEquals(teamService.reportIncome("JacobS","BeitarJerusalem"),6375);


    }
    @Test
    public void UC6_7_b() {
        //add all pf the asset and set their salary
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "TeamManager", "Ronaldinio"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Amir"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Oded"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Yaniv"));
        assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Coach", "TomerZ"));
        assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Amir", "salary", "10000"));
        assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Oded", "salary", "12000"));
        assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Yaniv", "salary", "13000"));
        assertTrue(teamService.editCoach("JacobS", "McabiHaifa", "TomerZ", "salary", "15500"));
        assertTrue(teamService.editTeamManager("JacobS", "McabiHaifa", "Ronaldinio", "salary", 20000));
        //check all of the salary off all the asset and sum tham
        //assertEquals(teamService.reportExpanse("JacobS", "McabiHaifa"), 70500);
        //check if a teamOwner enter a wrong team
        assertEquals(teamService.reportExpanse("JacobS", "BeerSheva"), -1);

        assertTrue(Jacob.addAsset("BeitarJerusalem", "Stadium", "Tedi"));

        //assertEquals(teamService.reportIncome("JacobS","BeitarJerusalem"),6375);

        assertEquals(teamService.reportIncome("JacobS", "BeerSheva"), -1);

        //assertTrue(Jacob.deleteAsset("BeitarJerusalem", "Stadium", "Tedi"));
    }

    @Test
    public void updatePage(){
        assertTrue(Jacob.addUpdate("McabiHaifa","that is my new team"));
        assertFalse(Jacob.addUpdate("McabiHaifa",""));
        assertFalse(Jacob.addUpdate("McabiHaifa",null));
        assertFalse(Jacob.addUpdate("BeerSheva","that is my new team"));

    }


}
