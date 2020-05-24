package businessLayer.userTypes.Administration;

import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.Exceptions.NotFoundInDbException;
import businessLayer.Team.Team;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import businessLayer.userTypes.SystemController;
import org.junit.rules.ExpectedException;
import serviceLayer.SystemService;
import serviceLayer.TeamService;

import static org.junit.Assert.*;

public class TeamOwnerTest {




    private TeamService teamService;
    private SystemService systemService;
    private SystemController systemController;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void UT_createTestValues() {
       // systemController = SystemController.SystemController();
       /* tDB = new DataBaseValues();
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
*/
        teamService = new TeamService();
        systemService = new SystemService();
        systemController = SystemController.SystemController();

    }
    @Test
    public void UC6_1_1_a() {
        //adding a player to the team successfully
        //todo: check with new DB
        //assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Player", "yosi"));
        //adding a coach to the team successfully
        //todo: check with new DB
        //assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));
        //adding a teamManager to the team successfully
        //todo: check with new DB
        //assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //adding a stadium to the team successfully
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));


    }
    @Test
    public void UC6_1_1_b() {
        //adding a player to the team
        //todo: check with new DB
        //assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Player", "yosi"));
        //a player that was already assign to the team
        assertFalse(teamService.addAsset("AlonaBarkat","BeerSheva", "Player", "yosi"));
        //a team that doesnt exist at the teamOwner
        assertFalse(teamService.addAsset("Nissanov","ManchesterUnited", "Player", "Buzaglo"));

        //adding a coach to the team
        //todo: check with new DB
        //assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));
        //failed to a coach that was already assign to the team
        assertFalse(teamService.addAsset("Nissanov","BeerSheva", "Coach", "efronio"));

        //adding a teamOwner to the team
        //todo: check with new DB
        //assertTrue(teamService.addAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //failed to a teamOwner that was already assign to the team
        assertFalse(teamService.addAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //failed to a teamOwner to a team that was already assign to a different team
        assertFalse(teamService.addAsset("Nissanov","BeerSheva", "TeamManager", "itayK"));

        //adding a stadium to the team
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
        //failed to a teamOwner to a team that was already assign to a different team
        assertFalse(teamService.addAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
    }

    @Test
    public void UC6_1_2_a() {
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Coach", "efronio");
        //todo: check with new DB
        //Jacob.addAsset("McabiHaifa", "TeamManager", "itayK");
        //todo: check with new DB
        //Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        //edit a player from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "birthDate", "9/11"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "fieldJob", "DM"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "salary", "10000"));

        //edit a Coach from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "training", "GK"));
        //todo: check with new DB
        //assertTrue(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "teamJob", "mainCoach"));
        //todo: check with new DB
        //assertTrue(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "salary", "10000"));

        //edit a TeamManager from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.editTeamManager("JacobS","McabiHaifa", "itayK", "salary", 100000));

        //edit a Stadium from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.editStadium("JacobS","McabiHaifa", "samiOfer", "numberOfSeats", 50));

    }
    @Test
    public void UC6_1_2_b() {
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Coach", "efronio");
        //todo: check with new DB
        //Jacob.addAsset("McabiHaifa", "TeamManager", "itayK");
        //todo: check with new DB
        //Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        //fail to edit Asset that was deleted

        //todo: check with new DB
        //Barkat.deleteAsset("BeerSheva", "Player", "Buzaglo");
        assertFalse(teamService.editPlayer("AlonaBarkat","BeerSheva", "Buzaglo", "birthDate", "9/20"));
        assertFalse(teamService.editPlayer("AlonaBarkat","BeerSheva", "ido", "birthDate", "9/20"));

        //todo: check with new DB
        //Barkat.deleteAsset("BeerSheva", "Coach", "efronio");
        assertFalse(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "teamJob", "SubCoach"));
        assertFalse(teamService.editCoach("AlonaBarkat","BeerSheva", "efronio", "training", "attacker"));

        //todo: check with new DB
        //Jacob.deleteAsset("McabiHaifa", "TeamManager", "itayK");
        assertFalse(teamService.editTeamManager("JacobS","McabiHaifa", "itayK", "salary", 20));

        //todo: check with new DB
        //Jacob.deleteAsset("McabiHaifa", "Stadium", "samiOfer");
        assertFalse(teamService.editStadium("JacobS","McabiHaifa", "samiOfer", "numberOfSeats", 50));
        //a stadium that doesnt exist in the system
        assertFalse(teamService.editStadium("JacobS","McabiHaifa", "natania", "numberOfSeats", 50));
    }

    @Test
    public void UC6_1_3_a() {
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Coach", "efronio");
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "TeamManager", "itayK");
        //todo: check with new DB
        //Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        //delete a player from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Player", "Buzaglo"));

        //delete a Coach from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));

        //delete a TeamManager from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));

        //delete a Stadium from the team successfully
        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
    }
    @Test
    public void UC6_1_3_b() {
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "Coach", "efronio");
        //todo: check with new DB
        //Barkat.addAsset("BeerSheva", "TeamManager", "itayK");
        //todo: check with new DB
        //Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Player", "Buzaglo"));
        //fail to delete a player that was already deleted
        assertFalse(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Player", "Buzaglo"));
        //fail to delete a player that doesnt exist in the team
        assertFalse(teamService.deleteAsset("Nissanov","ManchesterUnited", "Player", "Buzaglo"));

        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Coach", "efronio"));
        //fail to delete a Coach that was already deleted
        assertFalse(teamService.deleteAsset("Nissanov","BeerSheva", "Coach", "efronio"));


        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //fail to delete a TeamManager that was already deleted
        assertFalse(teamService.deleteAsset("AlonaBarkat","BeerSheva", "TeamManager", "itayK"));
        //fail to delete a TeamManager that doesnt exist in the team
        assertFalse(teamService.deleteAsset("JacobS","McabiHaifa", "TeamManager", "itayK"));


        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
        //fail to delete a Stadium that was already deleted
        assertFalse(teamService.deleteAsset("JacobS","McabiHaifa", "Stadium", "samiOfer"));
        //fail to delete a Stadium that doesnt exist in the team
        assertFalse(teamService.deleteAsset("AlonaBarkat","BeerSheva", "Stadium", "samiOfer"));
    }

    // a flow of the uc 6_1
    @Test
    public void UC6_1() {
        //test if the team owner successfully able to add a asset to his team
        //todo: check with new DB
        //assertTrue(teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));
        //test if the team owner unable to add an asset that already exist in his team
        assertFalse(teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));

        //test if the team owner unable to add an asset that not exist in the System
        assertFalse(teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "ido"));

        //test if the team owner successfully able to delete a asset from his team
        //todo: check with new DB
        //assertTrue(teamService.deleteAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));
        //test if the team owner unable to delete a asset from his team that doesnt exist anymore
        assertFalse(teamService.deleteAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo"));

        //test if the team owner successfully able to edit a detail of a asset
        teamService.addAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo");
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "birthDate", "11/9/93"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "fieldJob", "DM"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "salary", "20000"));
        //test if the
        assertFalse(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "training", "20000"));

        teamService.deleteAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo");
        //test if team owner try to edit a team player that doesnt exist
        assertFalse(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "birthDate", "11/9/93"));
    }

    @Test
    public void UC_6_2_a() {
        //Test 1 - add Successfully
        //todo: check with new DB
        //assertTrue(teamService.appoinTeamOwnerToTeam("BeerSheva","Tamash","AlonaBarkat"));
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
        //todo: check with new DB
        //Barkat.appointToOwner(Tamash, "BeerSheva");
        //todo: check with new DB
        //assertTrue(teamService.removeOwner("AlonaBarkat","BeerSheva","Tamash"));
    }

    @Test
    public void UC_6_3_b(){
        assertFalse(teamService.removeOwner("AlonaBarkat","BeerSheva","Roso"));
        assertFalse(teamService.removeOwner("AlonaBarkat","BeerSheva","Yosi"));
    }

    @Test
    public void UC6_4_a(){
        //all good
        //todo: check with new DB
        //assertTrue(teamService.addManager("Inon","kloppJ","GENERAL","HTA","100"));
        //check if add succeeded
        //todo: check with new DB
        //expectedException.expect(AlreadyExistException.class);
        //assertFalse(teamService.addManager("Inon","kloppJ","GENERAL","HTA","100"));

    }

    @Test
    public void UC6_4_b(){
        //all good
        //todo: check with new DB
        //assertTrue(teamService.addManager("Inon","kloppJ","GENERAL","HTA","100"));
        //add same manager again and adding manager to occupied team
        //todo: check with new DB
        //expectedException.expect(AlreadyExistException.class);
        //teamService.addManager("Inon","kloppJ","GENERAL","HTA","100");
    }

    @Test
    public void UC6_4_c(){
        //wrong username
        //todo: check with new DB
        //assertFalse(teamService.addManager("Inon","kloppJU","GENERAL","HTA","100"));
    }

    @Test
    public void UC6_4_d(){
        //try assign a teamManager
        //todo: check with new DB
        //assertFalse(teamService.addManager("Inon","AlonaBarkat","GENERAL","HTA","100"));
    }

    @Test
    public void UC6_5_a(){
        //all good
        //todo: check with new DB
        //assertTrue(teamService.fireManager("gerrard","valverde","Liverpool"));

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
        //todo: check with new DB
        //assertTrue(teamService.fireManager("gerrard","valverde","Liverpool"));

        //try fire the same manager again
        assertFalse(teamService.fireManager("gerrard","valverde","Liverpool"));
    }


    @Test
    //UNIT TEST
    public void UT_checkExclusiveTeamOwner(){
        //1 - UNIT
        //check if Alona who has 2 teams is exclusive

        //todo: check with new DB
        //assertFalse(Jimmy.isExclusiveTeamOwner());

        //2
        //check if Alona is now Exclusive
        //todo: check with new DB
        //Arsenal.getTeamOwners().remove(Alex);
        //todo: check with new DB
        //Arsenal.getTeamOwners().remove(teamOwner);
        //todo: check with new DB
        //Alex.getTeams().remove(Arsenal);

        //todo: check with new DB
        //assertTrue(Jimmy.isExclusiveTeamOwner());

        //3
        //check what happens without any teams
        //todo: check with new DB
        //BeerSheva.getTeamOwners().remove(Nissanov);
        //todo: check with new DB
        //Nissanov.getTeams().remove(BeerSheva);
        //todo: check with new DB
        //assertFalse(Nissanov.isExclusiveTeamOwner());
    }



    @Test
    public void UT_isFictive() {

        //todo: check with new DB
        //assertFalse(Nissanov.isFictive());
        //todo: check with new DB
        //Nissanov.setOriginalObject(Buzaglo);
        //todo: check with new DB
        //assertTrue(Nissanov.isFictive());
    }

    @Test
    public void UT_checkTeamRequest() {
        //1
        //check if we get true on a normal request

        assertTrue(systemService.sendRequestForTeam("natania","1948","AlexTO"));

        //2
        //check if we get a false on a not valid year
        assertFalse(systemService.sendRequestForTeam("natania","","AlexTO"));

        //3
        //check if we get a false on not valid name
        assertFalse(systemService.sendRequestForTeam("","1948","AlexTO"));

    }

    /**
     * Unit Test - enterMember(String userName))
     */
    @Test
    public void UT_enterMember() {
        //todo: check with new DB
        //assertEquals(Barkat.enterMember("Glazers"), Glazers); //Try to search a subscriber
        //todo: check with new DB
        //assertNull(Barkat.enterMember("Itay")); //Search a team member which in not exist in the system.

    }

    /**
     * Unit Test - enterMember(String teamName))
     */
    @Test
    public void UT_appointToOwner() {
        //todo: check with new DB
        //assertFalse(Barkat.appointToOwner(Buzaglo, "Manchester")); //Try and Fail to add to a team which you don't own.
        //todo: check with new DB
        //assertTrue(Barkat.appointToOwner(Buzaglo, "BeerSheva")); //Try to add successfully.
        //todo: check with new DB
        //assertFalse(Barkat.appointToOwner(Glazers, "BeerSheva")); //Try and Fail to add someone which is already a team owner.
        //todo: check with new DB
        //assertFalse(Barkat.appointToOwner(Alon, "BeerSheva")); //Try and Fail to add someone, when you are not a Player, a Coach or a Team Manager.
    }


    /**
     * Unit Test - getTeam(String teamName)
     */
    @Test
    public void UT_getTeam() {
        //todo: check with new DB
        //assertEquals(Inon.getTeam("BeerSheva"), BeerSheva);
        //todo: check with new DB
        //assertNull(Inon.getTeam("NAS"));
        //todo: check with new DB
        //assertEquals(Inon.getTeam("HTA"), HTA);
        //todo: check with new DB
        //assertNotEquals(Inon.getTeam("HTA"), BeerSheva);
    }

    /**
     * Unit Test - changeStatus(Team team)
     */
    @Test
    public void UT_changeStatus() {
        //enabled to disabled
        expectedException.expect(NotFoundInDbException.class);
        //todo: check with new DB
        //Inon.disableStatus(BeerSheva);
        //todo: check with new DB
        //assertFalse(BeerSheva.getActive());

        //disabled to enabled
        //todo: check with new DB
        //Inon.enableStatus(BeerSheva);
        //todo: check with new DB
        //assertTrue(BeerSheva.getActive());
    }

    @Test
    public void UC6_6_1_a() {
        //Test - 1 - Disable successfully
        expectedException.expect(NotFoundInDbException.class);
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
        expectedException.expect(NotFoundInDbException.class);
        teamService.disableTeamStatus("ManchesterUnited", "Glazers");
        assertFalse(teamService.disableTeamStatus("ManchesterUnited", "Glazers"));
    }

    @Test
    public void UC6_6_2_a() {
        //Test - 4 - Enable successfully
        expectedException.expect(NotFoundInDbException.class);
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
        //todo: check with new DB
        //TeamManager itay= (TeamManager) DB.selectSubscriberFromDB("itayK");
        //BeerSheva.setTeamManager(itay);
        //itay.setTeam(BeerSheva);
        //try assign manager to a team that not belong to me

        //todo: check with new DB
        //expectedException.expect(AlreadyExistException.class);
        //Alex.addManager("pepG", Permissions.GENERAL, BeerSheva, 200);
        //manager already has team
        //todo: check with new DB
        //assertFalse(Alex.addManager("itayK", Permissions.GENERAL, LeedsUnited, 200));
        //all good
        //todo: check with new DB
        //assertTrue(Alex.addManager("pepG", Permissions.GENERAL, LeedsUnited, 1000));
        //add again same manager
        //todo: check with new DB
        //assertFalse(Alex.addManager("pepG", Permissions.GENERAL, LeedsUnited, 1000));

    }

    @Test
    public void UT_fireManager() {

        //try fire manager from team that not belong to me
        //todo: check with new DB
        //assertFalse(Alex.fireManager("itayK", BeerSheva));
        //not my manager
        //todo: check with new DB
        //assertFalse(Alex.fireManager("itayK", LeedsUnited));
        //try delete pep withut assigning him via Alex
        //LeedsUnited.setTeamManager(pep);
        //pep.setTeam(LeedsUnited);
        //todo: check with new DB
        //assertFalse(Alex.fireManager("pepG", LeedsUnited));
        //all good
        //Alex.getTeamManagers().put(LeedsUnited, pep);
        //todo: check with new DB
        //assertTrue(Alex.fireManager("pepG", LeedsUnited));
        //manager has no team
        //todo: check with new DB
        //assertFalse(Alex.fireManager("pepG", LeedsUnited));
        //add again same manager

    }

    @Test
    public void UT_getOriginalObject() {
        //todo: check with new DB
        //assertEquals(piqueF.getOriginalObject(),DB.selectSubscriberFromDB("pique"));
    }

    @Test
    public void UT_setOriginalObject() {
        //piqueF.setOriginalObject(null);
        //todo: check with new DB
        //assertEquals(piqueF.getOriginalObject(), null);
    }

    @Test
    public void UT_equals() {
        //todo: check with new DB
        //assertTrue(piqueF.equals(DB.selectSubscriberFromDB("piqueF")));
        //todo: check with new DB
        //assertTrue(piqueF.equals((TeamOwner) DB.selectSubscriberFromDB("piqueF")));
        //todo: check with new DB
        //assertFalse(piqueF.equals(DB.selectSubscriberFromDB("pepG")));
        //todo: check with new DB
        //assertFalse(piqueF.equals(DB.selectSubscriberFromDB("Alex")));
        //todo: check with new DB
        //assertFalse(piqueF.equals(null));

    }

   @Test
   public void UT_testRemoveOwner(){
    /*   Barkat.appointToOwner(Tamash, "BeerSheva");
       assertTrue(Tamash.getTeamOwner().getTeams().contains(BeerSheva));
       assertTrue(Barkat.removeOwner(Tamash,"BeerSheva"));
*/

       //todo: check with new DB
  /*     Barkat.appointToOwner(Tamash, "BeerSheva");
       Tamash.getTeamOwner().appointToOwner(Jovani,"BeerSheva");
       assertTrue(Tamash.getTeamOwner().getTeams().contains(BeerSheva));
       assertTrue(Jovani.getTeamOwner().getTeams().contains(BeerSheva));
       assertTrue(Barkat.removeOwner(Tamash,"BeerSheva"));
       assertNull(Tamash.getTeamOwner());
       assertNull(Jovani.getTeamOwner());*/
   }

    @Test
    public void UC6_7_a() {
        //add all pf the asset and set their salary
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "TeamManager", "Ronaldinio"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Amir"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Oded"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Yaniv"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Coach", "TomerZ"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Amir", "salary", "10000"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Oded", "salary", "12000"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Yaniv", "salary", "13000"));
        //todo: check with new DB
        //assertTrue(teamService.editCoach("JacobS", "McabiHaifa", "TomerZ", "salary", "15500"));
        //todo: check with new DB
        //assertTrue(teamService.editTeamManager("JacobS", "McabiHaifa", "Ronaldinio", "salary", 20000));
        //check all of the salary off all the asset and sum tham
        //todo: check with new DB
        //assertEquals(teamService.reportExpanse("JacobS", "McabiHaifa"), 70500);

        //todo: check with new DB
        //assertTrue(Jacob.addAsset("BeitarJerusalem", "Stadium", "Tedi"));

        //todo: check with new DB
        //assertEquals(teamService.reportIncome("JacobS","BeitarJerusalem"),6375);


    }
    @Test
    public void UC6_7_b() {
        //add all pf the asset and set their salary
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "TeamManager", "Ronaldinio"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Amir"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Oded"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Player", "Yaniv"));
        //todo: check with new DB
        //assertTrue(teamService.addAsset("JacobS", "McabiHaifa", "Coach", "TomerZ"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Amir", "salary", "10000"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Oded", "salary", "12000"));
        //todo: check with new DB
        //assertTrue(teamService.editPlayer("JacobS", "McabiHaifa", "Yaniv", "salary", "13000"));
        //todo: check with new DB
        //assertTrue(teamService.editCoach("JacobS", "McabiHaifa", "TomerZ", "salary", "15500"));
        //todo: check with new DB
        //assertTrue(teamService.editTeamManager("JacobS", "McabiHaifa", "Ronaldinio", "salary", 20000));
        //check all of the salary off all the asset and sum tham
        //assertEquals(teamService.reportExpanse("JacobS", "McabiHaifa"), 70500);
        //check if a teamOwner enter a wrong team
        //todo: check with new DB
        //assertEquals(teamService.reportExpanse("JacobS", "BeerSheva"), -1);

        //todo: check with new DB
        //assertTrue(Jacob.addAsset("BeitarJerusalem", "Stadium", "Tedi"));

        //assertEquals(teamService.reportIncome("JacobS","BeitarJerusalem"),6375);

        //todo: check with new DB
        //assertEquals(teamService.reportIncome("JacobS", "BeerSheva"), -1);

        //assertTrue(Jacob.deleteAsset("BeitarJerusalem", "Stadium", "Tedi"));
    }

    @Test
    public void updatePage(){
        //todo: check with new DB
        //assertTrue(Jacob.addUpdate("McabiHaifa","that is my new team"));
        //todo: check with new DB
        //assertFalse(Jacob.addUpdate("McabiHaifa",""));
        //todo: check with new DB
        //assertFalse(Jacob.addUpdate("McabiHaifa",null));
        //todo: check with new DB
        //assertFalse(Jacob.addUpdate("BeerSheva","that is my new team"));

    }


}
