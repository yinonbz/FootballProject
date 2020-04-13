

import businessLayer.Team.Team;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.Administration.Player;
import businessLayer.userTypes.Administration.TeamOwner;
import org.junit.Before;
import businessLayer.userTypes.Administration.TeamOwner;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.SystemController;
import sun.swing.BakedArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class TestTeamOwner {
    private Player Buzaglo;
    private TeamOwner Barkat;
    private TeamOwner Glazers;
    private TeamOwner Nissanov;
    private Referee Alon;
    private Team BeerSheva;
    private Team ManchesterUnited;
    private SystemController systemController;


    @Before
    public void createTestValues(){
        systemController = SystemController.SystemController();
        Buzaglo = new Player("Buzaglo","Buzaglo123","Buzaglo","1900","midfield",null,systemController);
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva","alona",systemController);
        Glazers = new TeamOwner("Glazers", "manchesterU","glazer",systemController);
        Nissanov = new TeamOwner("Nissanov", "telAviv","nissanov",systemController);
        Alon = new Referee("Alon","Alon123456","Alon","main",null,systemController);
        systemController.getSystemSubscribers().put(Buzaglo.getUsername(),Buzaglo);
        systemController.getSystemSubscribers().put(Barkat.getUsername(),Barkat);
        systemController.getSystemSubscribers().put(Glazers.getUsername(),Glazers);
        systemController.getSystemSubscribers().put(Nissanov.getUsername(),Nissanov);
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        ManchesterUnited = new Team("Manchester United",Barkat,1899);
        Barkat.getTeams().add(BeerSheva); //todo change it later to a normal function UC 6.1
        Barkat.getTeams().add(ManchesterUnited);
        Glazers.getTeams().add(ManchesterUnited);
        ManchesterUnited.getTeamOwners().add(Barkat);
        ManchesterUnited.getTeamOwners().add(Glazers);
        BeerSheva.getTeamOwners().add(Barkat);
        BeerSheva.getTeamOwners().add(Nissanov);
        systemController.getTeams().put(BeerSheva.getTeamName(),BeerSheva);
        systemController.getTeams().put(ManchesterUnited.getTeamName(),ManchesterUnited);
    }

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
    public void UC6_6() {
        TeamOwner teamOwner = new TeamOwner("teamOwner1","to123456","Alon",SystemController.SystemController());
        Team team1 = new Team("Beer Sheva", teamOwner,1993);
        Team team2 = new Team("HTA", teamOwner,1990);

        //1 - test getTeam
        assertEquals(teamOwner.getTeam("Beer Sheva"),team1);
        assertNull(teamOwner.getTeam("NAS"));
        assertEquals(teamOwner.getTeam("HTA"),team2);
        assertNotEquals(teamOwner.getTeam("HTA"),team1);

        //2 - test changeStatus - enabled to disabled
        teamOwner.changeStatus(team1);
        assertFalse(team1.getActive());

        //3 - test changeStatus - disabled to enabled
        teamOwner.changeStatus(team1);
        assertTrue(team1.getActive());
    }

    @Test
    public void checkTeamRequest() {
        //1
        //check if we get true on a normal request
        assertTrue(Barkat.sendRequestForTeam("TheSharks", "2003"));
    }

    @Test
    public void isFictive(){

        assertFalse(Nissanov.isFictive());
        Nissanov.setOriginalObject(Buzaglo);
        assertTrue(Nissanov.isFictive());
        //2
        //check if we get a false on a not valid year
        assertFalse(Barkat.sendRequestForTeam("TheSharks","0"));

        //3
        //check if we get a false on not valid name
        assertFalse(Barkat.sendRequestForTeam("","2004"));

    }

    @Test
    public void UC_6_2() {
        //1 - Test enterMember(String userName)
        assertEquals(Barkat.enterMember("Glazers"), Glazers);
        assertNull(Barkat.enterMember("Itay"));

        //2 - Test
        assertFalse(Barkat.appointToOwner(Buzaglo, "Manchester"));
        assertTrue(Barkat.appointToOwner(Buzaglo, "Beer Sheva"));
        assertFalse(Barkat.appointToOwner(Glazers,"Beer Sheva"));
        assertFalse(Barkat.appointToOwner(Alon,"Beer Sheva"));

    }
}
