package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.SystemController;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAssociationRepresentative {

    static TeamOwner Barkat;
    static Team BeerSheva;
    static SystemController systemController;
    static AssociationRepresentative EliLuzon;

    @BeforeClass
    public static void createTestValues(){
        systemController = SystemController.SystemController();
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva","alona",systemController);
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        Barkat.sendRequestForTeam("ManchesterUnited","1888");
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        systemController.addTeam(BeerSheva);
        EliLuzon = new AssociationRepresentative("EliLuzon", "abcd", "Eli", systemController);
        systemController.getSystemSubscribers().put("AlonaBarkat",Barkat);
    }

    @Test
    public void checkTeamConfirmation(){
        //1
        //check if a regular confirmation
        assertTrue(EliLuzon.confirmTeamRequest("ManchesterUnited"));

        //2
        //check if a team that already exists get false
        assertFalse(EliLuzon.confirmTeamRequest("Beer Sheva"));

        //3
        //check that a team that doesn't exist get false
        assertFalse(EliLuzon.confirmTeamRequest(""));

        //4
        //check that a team that doesn't exist get false
        assertFalse(EliLuzon.confirmTeamRequest("HTA"));
    }

    @Test
    public void checkAddStadium(){

        //1
        //check that a regular stadium is being updated
        assertTrue(EliLuzon.createNewStadium("S1","200"));

        //2
        //check the stadium was added
        assertTrue(systemController.getStadiums().containsKey("S1"));

        //3
        //see we can't add the same stadium again
        assertFalse(EliLuzon.createNewStadium("S1","200"));

        //4
        //see wa can't add a stadium with corrupt value
        assertFalse(EliLuzon.createNewStadium("","200"));

        //5
        assertFalse(EliLuzon.createNewStadium("S3",""));
    }


}
