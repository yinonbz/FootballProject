package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.SystemController;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTeamOwner {
    private Player Buzaglo;
    private TeamOwner Barkat;
    private TeamOwner Glazers;
    private TeamOwner Nissanov;
    private Team BeerSheva;
    private Team ManchesterUnited;
    private SystemController systemController;
    private Team MacabiHaifa;
    private TeamOwner Jacob;


    @Before
    public void createTestValues() {
        systemController = SystemController.SystemController();
        Buzaglo = new Player("Buzaglo", "Buzaglo123", "Buzaglo", "", "midfield", null, systemController);
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva", "alona", systemController);
        Glazers = new TeamOwner("Glazers", "manchesterU", "glazer", systemController);
        Nissanov = new TeamOwner("Nissanov", "telAviv", "nissanov", systemController);
        BeerSheva = new Team("Beer Sheva", Barkat, 1973);
        ManchesterUnited = new Team("Manchester United", Barkat, 1899);
        Jacob = new TeamOwner("JacobS", "JacobS123", "Jacob", systemController);
        MacabiHaifa = new Team("McabiHaifa", Jacob, 1913);
        BeerSheva.setTeamId(123);
        ManchesterUnited.setTeamId(456);
        MacabiHaifa.setTeamId(789);
        Jacob.getTeams().add(MacabiHaifa);
        MacabiHaifa.getTeamOwners().add(Jacob);
        Barkat.getTeams().add(BeerSheva); //todo change it later to a normal function UC 6.1
        Barkat.getTeams().add(ManchesterUnited);
        Glazers.getTeams().add(ManchesterUnited);
        ManchesterUnited.getTeamOwners().add(Barkat);
        ManchesterUnited.getTeamOwners().add(Glazers);
        BeerSheva.getTeamOwners().add(Barkat);
        BeerSheva.getTeamOwners().add(Nissanov);
        /**
         * this data is for 6.1
         */
        HashMap<String, Subscriber> sysSub = new HashMap<>();
        Coach Ido = new Coach("efronio", "111", "ido", "attack", "mainCoach", systemController);
        Coach tomer = new Coach("TomerZ", "111", "tomer", "defence", "subCoach", systemController);
        TeamManager itay = new TeamManager("itayK", "111", "itay", 100, null, systemController);

        sysSub.put("Buzaglo", Buzaglo);
        sysSub.put("efronio", Ido);
        sysSub.put("TomerZ", tomer);
        sysSub.put("itayK", itay);
        systemController.setSystemSubscribers(sysSub);
        /******************************/
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
    }

    @Test
    public void UC6_1_3() {
        Barkat.addAsset(123, "Player", "Buzaglo");
        Barkat.addAsset(123, "Coach", "efronio");
        Jacob.addAsset(789, "TeamManager", "itayK");

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

    }

    @Test
    public void UC8_2() { //todo need to check about the names of the sub-functions tomer
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


    @Test
    public void deleteAsset() {
    }

    @Test
    public void editPlayer() {
    }

    @Test
    public void editCoach() {
    }

    @Test
    public void editTeamManager() {
    }


}
