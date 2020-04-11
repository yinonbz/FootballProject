package businessLayer.Team;

import businessLayer.userTypes.Administration.TeamOwner;
import org.junit.Before;

public class TestTeam {

    private Team BeerSheva;
    private TeamOwner Barkat;

    @Before

    public void createTestValues(){
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva","alona");


    }



}
