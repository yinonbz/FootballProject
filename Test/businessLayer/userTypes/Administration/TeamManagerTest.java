package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.TeamService;

import java.awt.*;

import static org.junit.Assert.*;

public class TeamManagerTest {
    private  DataBaseValues tDB;
    private DemoDB DB;
    private TeamOwner Barkat;
    private TeamManager itay;
    private Player Tamash;
    private Player Buzaglo;
    private Referee Alon;
    private Team BeerSheva;
    private TeamService teamService;
    private Coach Ido;


    @Before
    public void createTestValues() {
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        Barkat = (TeamOwner) DB.selectSubscriberFromDB("AlonaBarkat");
        itay = (TeamManager) DB.selectSubscriberFromDB("itayK");
        Buzaglo = (Player) DB.selectSubscriberFromDB("Buzaglo");
        Tamash = (Player) DB.selectSubscriberFromDB("Tamash");
        Alon = (Referee) DB.selectSubscriberFromDB("Alon");
        Ido = (Coach) DB.selectSubscriberFromDB("efronio");

        BeerSheva = DB.selectTeamFromDB("Beer Sheva");
        teamService = new TeamService();


    }

    @Test
    public void equals(){
        assertFalse(itay.equals(Barkat));
        assertFalse(itay.equals(new TeamManager(itay.getUsername(),itay.getPassword(),itay.getName(),itay.getTeam(),100,itay.getSystemController())));
        assertTrue(itay.equals(DB.selectSubscriberFromDB("itayK")));

    }

    @Test
    public void isOwner() {
        assertFalse(itay.isOwner());
        Barkat.appointToOwner(itay,"Beer Sheva");
        assertTrue(itay.isOwner());
    }

    @Test
    public void addPlayer() {
        //no permissions
        assertFalse(itay.addPlayer(Buzaglo.getUsername()));
        //player has team
        itay.setPermissions(Permissions.PLAYERORIENTED);
        Buzaglo.setTeam(BeerSheva);
        assertFalse(itay.addPlayer(Buzaglo.getUsername()));
        //player has no team and itay has wrong permissions
        itay.setPermissions(Permissions.COACHORIENTED);
        Buzaglo.setTeam(null);
        assertFalse(itay.addPlayer(Buzaglo.getUsername()));
        //everything is ok
        itay.setPermissions(Permissions.PLAYERORIENTED);
        assertTrue(itay.addPlayer(Buzaglo.getUsername()));
        //another permission allowed
        itay.setPermissions(Permissions.GENERAL);
        assertTrue(itay.addPlayer(Tamash.getUsername()));

    }

    @Test
    public void deletePlayer() {
        //no permissions
        assertFalse(itay.deletePlayer(Buzaglo.getUsername()));
        //player has no team
        itay.setPermissions(Permissions.PLAYERORIENTED);
        assertFalse(itay.deletePlayer(Buzaglo.getUsername()));
        //player has team and itay has wrong permissions
        BeerSheva.addPlayer(Buzaglo);
        Buzaglo.setTeam(BeerSheva);
        itay.setPermissions(Permissions.COACHORIENTED);
        assertFalse(itay.deletePlayer(Buzaglo.getUsername()));
        //right permissions no team
        itay.setPermissions(Permissions.PLAYERORIENTED);
        assertTrue(itay.deletePlayer(Buzaglo.getUsername()));
        //another permission allowed
        //Barkat.addAsset(BeerSheva.getTeamId(),"Player",Buzaglo.getName());
        BeerSheva.addPlayer(Buzaglo);
        Buzaglo.setTeam(BeerSheva);
        itay.setPermissions(Permissions.GENERAL);
        assertTrue(itay.deletePlayer(Buzaglo.getUsername()));

    }

    @Test
    public void editPlayer() {
        itay.setPermissions(Permissions.PLAYERORIENTED);
        //no such player
        assertFalse(itay.editPlayer(Buzaglo.getUsername(),"fieldJob","foward"));
        BeerSheva.addPlayer(Buzaglo);
        Buzaglo.setTeam(BeerSheva);
        //no permissions
        itay.setPermissions(Permissions.FINANCE);
        assertFalse(itay.editPlayer(Buzaglo.getUsername(),"fieldJob","foward"));
        //all good
        itay.setPermissions(Permissions.GENERAL);
        assertTrue(itay.editPlayer(Buzaglo.getUsername(),"fieldJob","foward"));
        assertTrue(itay.editPlayer(Buzaglo.getUsername(),"salary","200"));
        assertTrue(itay.editPlayer(Buzaglo.getUsername(),"birthDate","19042000"));

    }

    @Test
    public void addCoach() {
        //no permission
        assertFalse(itay.addCoach("efronio"));
        //Coach already in team
        Barkat.addAsset("BeerSheva","Coach","efronio");
        itay.setPermissions(Permissions.COACHORIENTED);
        assertFalse(itay.addCoach("efronio"));
        //all good
        Barkat.deleteAsset("BeerSheva","Coach","efronio");
        assertTrue(itay.addCoach("efronio"));
    }

    @Test
    public void deleteCoach() {
        //no permission
        assertFalse(itay.deleteCoach("efronio"));
        //Coach has no team
        itay.setPermissions(Permissions.COACHORIENTED);
        assertFalse(itay.deleteCoach("efronio"));
        //all good
        Barkat.addAsset("BeerSheva","Coach","efronio");
        assertTrue(itay.deleteCoach("efronio"));

    }

    @Test
    public void editCoach() {
        Barkat.addAsset("BeerSheva","Coach","efronio");
        //no permissions
        assertFalse(itay.editCoach("efronio","training","fitness"));
        //has wrong permissions
        itay.setPermissions(Permissions.PLAYERORIENTED);
        assertFalse(itay.editCoach("efronio","training","fitness"));

        //all good
        itay.setPermissions(Permissions.GENERAL);
        assertTrue(itay.editCoach("efronio","training","fitness"));
        assertTrue(itay.editCoach("efronio","teamJob","assistentManager"));




    }
}