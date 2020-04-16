import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.userTypes.Administration.Player;
import businessLayer.userTypes.Administration.Referee;
import businessLayer.userTypes.Administration.TeamOwner;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.Test;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.userTypes.SystemController;

import java.sql.Ref;

import static org.junit.Assert.*;

public class TestTeamOwner {
    /*
    private TeamOwner Barkat;
    private TeamOwner Glazers;
    private TeamOwner Nissanov;
    private Referee Alon;
    private Team BeerSheva;
    private Team ManchesterUnited;
    private Team MacabiHaifa;
    private TeamOwner Jacob;
    */

    private TeamOwner Barkat;
    private TeamOwner Nissanov;
    private TeamOwner Jacob;
    private TeamOwner Glazers;
    private Player Buzaglo;
    private Referee Alon;
    private Team BeerSheva;
    private Team HTA;


    //private SystemController systemController;
    static DemoDB DB;
    static DataBaseValues tDB;


    @Before
    public void createTestValues() {
       // systemController = SystemController.SystemController();
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        Barkat = (TeamOwner) DB.selectSubscriberFromDB("AlonaBarkat");
        Nissanov = (TeamOwner) DB.selectSubscriberFromDB("Nissanov");
        Jacob = (TeamOwner) DB.selectSubscriberFromDB("JabobS");
        Glazers = (TeamOwner) DB.selectSubscriberFromDB("Glazers");
        Buzaglo = (Player) DB.selectSubscriberFromDB("Buzaglo");
        Alon = (Referee) DB.selectSubscriberFromDB("Alon");

        BeerSheva = DB.selectTeamFromDB("Beer Sheva");
        HTA = DB.selectTeamFromDB("HTA");

    }

    @Test
    public void UC6_1_1() {
        //check if add asset works correctly 6.1.1
        //new player that was not assign to a team

        assertTrue(Barkat.addAsset(123, "Player", "Buzaglo"));

        //a player that was already assign to a team
        assertFalse(Barkat.addAsset(123, "Player", "Buzaglo"));

        assertFalse(Nissanov.addAsset(456, "Player", "Buzaglo"));

        assertTrue(Barkat.addAsset(123, "Coach", "efronio"));

        assertFalse(Nissanov.addAsset(123, "Coach", "efronio"));

        assertTrue(Barkat.addAsset(123, "TeamManager", "itayK"));

        assertFalse(Nissanov.addAsset(123, "TeamManager", "itayK"));

        TeamOwner Jacob = (TeamOwner) DB.selectSubscriberFromDB("AlonaBarkat");

        assertTrue(Jacob.addAsset(789,"Stadium","samiOfer"));

        assertFalse(Jacob.addAsset(789,"Stadium","samiOfer"));

    }

    @Test
    public void UC6_1_2() {
        //check if remove asset works correctly 6.1.2
        //new player that was not assign to a team
        Barkat.addAsset(123, "Player", "Buzaglo");
        assertTrue(Barkat.deleteAsset(123, "Player", "Buzaglo"));
        assertFalse(Barkat.deleteAsset(123, "Player", "Buzaglo"));
        assertFalse(Nissanov.deleteAsset(456, "Player", "Buzaglo"));


        Barkat.addAsset(123, "Coach", "efronio");
        assertTrue(Barkat.deleteAsset(123, "Coach", "efronio"));
        assertFalse(Nissanov.deleteAsset(123, "Coach", "efronio"));

       Jacob.addAsset(789, "TeamManager", "itayK");
        assertTrue(Jacob.deleteAsset(789, "TeamManager", "itayK"));
        assertFalse(Barkat.deleteAsset(123, "TeamManager", "itayK"));

        Jacob.addAsset(789,"Stadium","samiOfer");

        assertTrue(Jacob.deleteAsset(789,"Stadium","samiOfer"));
        assertFalse(Jacob.deleteAsset(789,"Stadium","samiOfer"));
        assertFalse(Barkat.deleteAsset(123, "Stadium", "samiOfer"));

    }

    @Test
    public void UC6_1_3() {
        Barkat.addAsset(123, "Player", "Buzaglo");
        Barkat.addAsset(123, "Coach", "efronio");
        Jacob.addAsset(789, "TeamManager", "itayK");
        Jacob.addAsset(789,"Stadium","samiOfer");

        assertTrue(Barkat.editPlayer(123,"Buzaglo","birthDate","9/11"));
        assertTrue(Barkat.editPlayer(123,"Buzaglo","fieldJob","attacker"));
        Barkat.deleteAsset(123, "Player", "Buzaglo");
        assertFalse(Barkat.editPlayer(123,"Buzaglo","birthDate","9/20"));
        assertFalse(Barkat.editPlayer(123,"ido","birthDate","9/20"));

        assertTrue(Barkat.editCoach(123,"efronio","training","attacker"));
        assertTrue(Barkat.editCoach(123,"efronio","teamJob","mainCoach"));
        Barkat.deleteAsset(123, "Coach", "efronio");
        assertFalse(Barkat.editCoach(123,"efronio","teamJob","SubCoach"));

        assertTrue(Jacob.editTeamManager(789,"itayK","salary",100000));
        Jacob.deleteAsset(789,"TeamManager","itayK");
        assertFalse(Jacob.editTeamManager(789,"itayK","salary",20));

        assertTrue(Jacob.editStadium(789,"samiOfer","numberOfSeats",50));

        assertFalse(Jacob.editStadium(789,"natania","numberOfSeats",50));

    }

/*
    @Test
    public void UC8_2(){ //todo need to check about the names of the sub-functions tomer
        //1
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

    @Test
    public void UC_6_2() {
        //1 - Test enterMember(String userName)
        assertEquals(Barkat.enterMember("Glazers"), Glazers); //Try to search a subscriber
        assertNull(Barkat.enterMember("Itay")); //Search a team member which in not exist in the system.

        //2 - Test
        assertFalse(Barkat.appointToOwner(Buzaglo, "Manchester")); //Try and Fail to add to a team which you don't own.
        assertTrue(Barkat.appointToOwner(Buzaglo, "Beer Sheva")); //Try to add successfully.
        assertFalse(Barkat.appointToOwner(Glazers,"Beer Sheva")); //Try and Fail to add someone which is already a team owner.
        assertFalse(Barkat.appointToOwner(Alon,"Beer Sheva")); //Try and Fail to add someone, when you are not a Player, a Coach or a Team Manager.

    }
    @Test
    public void UC6_6() {

        //1 - test getTeam
        assertEquals(Barkat.getTeam("Beer Sheva"),BeerSheva);
        assertNull(Barkat.getTeam("NAS"));
        assertEquals(Barkat.getTeam("HTA"),HTA);
        assertNotEquals(Barkat.getTeam("HTA"),BeerSheva);

        //2 - test changeStatus - enabled to disabled
        Barkat.changeStatus(BeerSheva);
        assertFalse(BeerSheva.getActive());

        //3 - test changeStatus - disabled to enabled
        Barkat.changeStatus(BeerSheva);
        assertTrue(BeerSheva.getActive());
    }

}
