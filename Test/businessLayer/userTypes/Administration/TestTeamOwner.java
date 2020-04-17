package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Stadium;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.userTypes.SystemController;
import serviceLayer.TeamService;

import static org.junit.Assert.*;

public class TestTeamOwner {


    static TeamOwner Barkat;
    static TeamOwner Nissanov;
    static TeamOwner Jacob;
    static TeamOwner Glazers;
    static TeamOwner Inon;


    static Player Buzaglo;
    static Player Tamash;
    static Player Roso; //This Player will not be in the DB
    static Player yosi;

    static Referee Alon;

    static Team HTA;
    static Team BeerSheva;
    static Team Barca; //This Team will not be in the DB


    //private SystemController systemController;
    static DemoDB DB;
    static DataBaseValues tDB;


    static TeamService teamService;


    @BeforeClass
    public static void createTestValues() {
        // systemController = SystemController.SystemController();
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        Barkat = (TeamOwner) DB.selectSubscriberFromDB("AlonaBarkat");
        Nissanov = (TeamOwner) DB.selectSubscriberFromDB("Nissanov");
        Jacob = (TeamOwner) DB.selectSubscriberFromDB("JacobS");
        Glazers = (TeamOwner) DB.selectSubscriberFromDB("Glazers");
        Inon = (TeamOwner) DB.selectSubscriberFromDB("Inon");
        Buzaglo = (Player) DB.selectSubscriberFromDB("Buzaglo");
        Tamash = (Player) DB.selectSubscriberFromDB("Tamash");
        yosi = (Player) DB.selectSubscriberFromDB("yosi");
        Alon = (Referee) DB.selectSubscriberFromDB("Alon");

        BeerSheva = DB.selectTeamFromDB("BeerSheva");
        HTA = DB.selectTeamFromDB("HTA");
        teamService = new TeamService();

    }

    @Test
    public void UC6_1_1() {
        //check if add asset works correctly 6.1.1
        //new player that was not assign to a team

        assertTrue(Barkat.addAsset("BeerSheva", "Player", "yosi"));

        //a player that was already assign to a team
        assertFalse(Barkat.addAsset("BeerSheva", "Player", "yosi"));

        assertFalse(Nissanov.addAsset("ManchesterUnited", "Player", "Buzaglo"));

        assertTrue(Barkat.addAsset("BeerSheva", "Coach", "efronio"));

        assertFalse(Nissanov.addAsset("BeerSheva", "Coach", "efronio"));

        assertTrue(Barkat.addAsset("BeerSheva", "TeamManager", "itayK"));

        assertFalse(Nissanov.addAsset("BeerSheva", "TeamManager", "itayK"));

        //  assertTrue(Barkat.deleteAsset(123, "TeamManager", "itayK"));

        assertTrue(Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer"));

        assertFalse(Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer"));

    }

    @Test
    public void UC6_1_2() {
        //check if remove asset works correctly 6.1.2
        //new player that was not assign to a team
        Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        assertTrue(Barkat.deleteAsset("BeerSheva", "Player", "Buzaglo"));
        assertFalse(Barkat.deleteAsset("BeerSheva", "Player", "Buzaglo"));
        assertFalse(Nissanov.deleteAsset("ManchesterUnited", "Player", "Buzaglo"));


        Barkat.addAsset("BeerSheva", "Coach", "efronio");
        assertTrue(Barkat.deleteAsset("BeerSheva", "Coach", "efronio"));
        assertFalse(Nissanov.deleteAsset("BeerSheva", "Coach", "efronio"));

        Barkat.addAsset("BeerSheva", "TeamManager", "itayK");
        assertTrue(Barkat.deleteAsset("BeerSheva", "TeamManager", "itayK"));
        assertFalse(Barkat.deleteAsset("BeerSheva", "TeamManager", "itayK"));
        assertFalse(Jacob.deleteAsset("McabiHaifa", "TeamManager", "itayK"));

        Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        assertTrue(Jacob.deleteAsset("McabiHaifa", "Stadium", "samiOfer"));
        assertFalse(Jacob.deleteAsset("McabiHaifa", "Stadium", "samiOfer"));
        assertFalse(Barkat.deleteAsset("BeerSheva", "Stadium", "samiOfer"));

    }

    @Test
    public void UC6_1_3() {
        //check if the edit asset works correctly 6.1.3
        Barkat.addAsset("BeerSheva", "Player", "Buzaglo");
        Barkat.addAsset("BeerSheva", "Coach", "efronio");
        Jacob.addAsset("McabiHaifa", "TeamManager", "itayK");
        Jacob.addAsset("McabiHaifa", "Stadium", "samiOfer");

        assertTrue(Barkat.editPlayer("BeerSheva", "Buzaglo", "birthDate", "9/11"));
        assertTrue(Barkat.editPlayer("BeerSheva", "Buzaglo", "fieldJob", "attacker"));
        Barkat.deleteAsset("BeerSheva", "Player", "Buzaglo");
        assertFalse(Barkat.editPlayer("BeerSheva", "Buzaglo", "birthDate", "9/20"));
        assertFalse(Barkat.editPlayer("BeerSheva", "ido", "birthDate", "9/20"));

        assertTrue(Barkat.editCoach("BeerSheva", "efronio", "training", "attacker"));
        assertTrue(Barkat.editCoach("BeerSheva", "efronio", "teamJob", "mainCoach"));
        Barkat.deleteAsset("BeerSheva", "Coach", "efronio");
        assertFalse(Barkat.editCoach("BeerSheva", "efronio", "teamJob", "SubCoach"));

        assertTrue(Jacob.editTeamManager("McabiHaifa", "itayK", "salary", 100000));
        Jacob.deleteAsset("McabiHaifa", "TeamManager", "itayK");
        assertFalse(Jacob.editTeamManager("McabiHaifa", "itayK", "salary", 20));

        assertTrue(Jacob.editStadium("McabiHaifa", "samiOfer", "numberOfSeats", 50));

        assertFalse(Jacob.editStadium("McabiHaifa", "natania", "numberOfSeats", 50));
    }

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
        assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "fieldJob", "attacker"));
        assertTrue(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "salary", "20000"));
        //test if the
        assertFalse(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "training", "20000"));

        teamService.deleteAsset("AlonaBarkat", "BeerSheva", "Player", "Buzaglo");
        //test if team owner try to edit a team player that doesnt exist
        assertFalse(teamService.editPlayer("AlonaBarkat", "BeerSheva", "Buzaglo", "birthDate", "11/9/93"));

    }


    @Test
    public void UC8_2() {
        //1 - UNIT
        //check if Alona who has 2 teams is exclusive

        assertFalse(Barkat.isExclusiveTeamOwner());

        //2
        //check if Alona is now Exclusive
        BeerSheva.getTeamOwners().remove(Nissanov);
        Nissanov.getTeams().remove(BeerSheva);

        assertTrue(Barkat.isExclusiveTeamOwner());

        //3
        //check what happens without any teams
        BeerSheva.getTeamOwners().remove(Nissanov);
        Nissanov.getTeams().remove(BeerSheva);
        assertFalse(Nissanov.isExclusiveTeamOwner());
    }

    /*
    @Test
    public void isFictive() {

        assertFalse(Nissanov.isFictive());
        Nissanov.setOriginalObject(Buzaglo);
        assertTrue(Nissanov.isFictive());
    }

    @Test
    public void checkTeamRequest() {
        //1
        //check if we get true on a normal request
        assertTrue(Barkat.sendRequestForTeam("TheSharks", "2003"));

        //2
        //check if we get a false on a not valid year
        assertFalse(Barkat.sendRequestForTeam("TheSharks", "0"));

        //3
        //check if we get a false on not valid name
        assertFalse(Barkat.sendRequestForTeam("", "2004"));

    }

*/

    /**
     * Unit Test - enterMember(String userName))
     */
    @Test
    public void enterMemberUT() {
        assertEquals(Barkat.enterMember("Glazers"), Glazers); //Try to search a subscriber
        assertNull(Barkat.enterMember("Itay")); //Search a team member which in not exist in the system.

    }

    /**
     * Unit Test - enterMember(String teamName))
     */
    @Test
    public void appointToOwnerUT() {
        assertFalse(Barkat.appointToOwner(Buzaglo, "Manchester")); //Try and Fail to add to a team which you don't own.
        assertTrue(Barkat.appointToOwner(Buzaglo, "BeerSheva")); //Try to add successfully.
        assertFalse(Barkat.appointToOwner(Glazers, "BeerSheva")); //Try and Fail to add someone which is already a team owner.
        assertFalse(Barkat.appointToOwner(Alon, "BeerSheva")); //Try and Fail to add someone, when you are not a Player, a Coach or a Team Manager.
    }

    @Test
    public void UC_6_2() {
        //Test 1 - add Successfully
        assertTrue(Barkat.appointToOwner(Tamash, "BeerSheva"));

        //Test - 2 - Try to add a Player which does not exists in the DB
        assertFalse(Barkat.appointToOwner(Roso, "BeerSheva"));

        //Test - 3 -Try and Fail to add someone which is already a team owner.
        assertFalse(Barkat.appointToOwner(Glazers, "BeerSheva"));

    }

    /**
     * Unit Test - getTeam(String teamName)
     */
    @Test
    public void getTeamUT() {
        assertEquals(Inon.getTeam("BeerSheva"), BeerSheva);
        assertNull(Inon.getTeam("NAS"));
        assertEquals(Inon.getTeam("HTA"), HTA);
        assertNotEquals(Inon.getTeam("HTA"), BeerSheva);
    }

    /**
     * Unit Test - changeStatus(Team team)
     */
    @Test
    public void changeStatusUT() {
        //enabled to disabled
        Inon.disableStatus(BeerSheva);
        assertFalse(BeerSheva.getActive());

        //disabled to enabled
        Inon.enableStatus(BeerSheva);
        assertTrue(BeerSheva.getActive());
    }

    @Test
    public void UC6_6() {
        //Test - 1 - Disable successfully
        assertTrue(teamService.disableTeamStatus("ManchesterUnited", "Glazers"));

        //Test - 2 - Try to disable a Team status which does not exists in the DB
        assertFalse(teamService.disableTeamStatus("Barca", "Glazers"));

        //Test - 3 - Try to disable an already disabled team
        assertFalse(teamService.disableTeamStatus("ManchesterUnited", "Glazers"));

        //Test - 4 - Enable successfully
        assertTrue(teamService.enableTeamStatus("ManchesterUnited", "Glazers"));

        //Test - 5 - Try to enable a Team status which does not exists in the DB
        assertFalse(teamService.enableTeamStatus("Barca", "Glazers"));

        //Test - 6 - Try to enable an already disabled team
        assertFalse(teamService.enableTeamStatus("ManchesterUnited", "Glazers"));
    }

    @Test
    public void UC6_7(){
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
        assertEquals(teamService.reportExpanse("JacobS","McabiHaifa"),70500);
        //check if a teamOwner enter a wrong team
        assertEquals(teamService.reportExpanse("JacobS","BeerSheva"),-1);

        assertTrue(Jacob.addAsset("BeitarJerusalem", "Stadium", "Tedi"));
        assertEquals(teamService.reportIncome("JacobS","BeitarJerusalem"),6375);
        assertEquals(teamService.reportIncome("JacobS","BeerSheva"),-1);

        //assertTrue(Jacob.deleteAsset("BeitarJerusalem", "Stadium", "Tedi"));



    }
}
