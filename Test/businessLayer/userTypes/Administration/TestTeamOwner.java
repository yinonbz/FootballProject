package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import org.junit.Before;
import org.junit.Test;
import serviceLayer.SystemController;

import static org.junit.Assert.*;

public class TestTeamOwner {
    private Player Buzaglo;
    private TeamOwner Barkat;
    private TeamOwner Glazers;
    private TeamOwner Nissanov;
    private Team BeerSheva;
    private Team ManchesterUnited;
    private SystemController systemController;


    @Before
    public void createTestValues(){
        Buzaglo = new Player("Buzaglo","Buzaglo123","Buzaglo","","midfield",null,systemController);
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva","alona",systemController);
        Glazers = new TeamOwner("Glazers", "manchesterU","glazer",systemController);
        Nissanov = new TeamOwner("Nissanov", "telAviv","nissanov",systemController);
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        ManchesterUnited = new Team("Manchester United",Barkat,1899);
        Barkat.getTeams().add(BeerSheva); //todo change it later to a normal function UC 6.1
        Barkat.getTeams().add(ManchesterUnited);
        Glazers.getTeams().add(ManchesterUnited);
        ManchesterUnited.getTeamOwners().add(Barkat);
        ManchesterUnited.getTeamOwners().add(Glazers);
        BeerSheva.getTeamOwners().add(Barkat);
        BeerSheva.getTeamOwners().add(Nissanov);

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
    public void isFictive(){

        assertFalse(Nissanov.isFictive());
        Nissanov.setOriginalObject(Buzaglo);
        assertTrue(Nissanov.isFictive());

    }


}
