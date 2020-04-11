package serviceLayer;
import businessLayer.Team.Team;
import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Administration.Admin;
import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.viewers.Fan;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestSystemController {

    private SystemController systemController;
    private Team ManchesterUnited;
    private Team BeerSheva;
    private TeamOwner Glazers;
    private TeamOwner Nissanov;
    private TeamOwner Barkat;
    private Admin admin;
    private Admin admin2;
    private Fan fan;


    @Before
    public void createTestValues(){
        systemController = new SystemController();
        admin = new Admin("TomerSein", "helloWorld", "tomer",systemController);
        admin2 = new Admin ("ItaiKatz", "helloWorld", "itai",systemController);
        Barkat = new TeamOwner("AlonaBarkat", "beerSheva","alona");
        Nissanov = new TeamOwner("Nissanov", "telAviv","nissanov");
        Glazers = new TeamOwner("Glazers", "manchesterU","glazer");
        fan = new Fan ("Gate13","aviNimni","avi");
        systemController.getSystemSubscribers().put("AlonaBarkat",Barkat);
        systemController.getSystemSubscribers().put("Nissanov",Nissanov);
        systemController.getSystemSubscribers().put("Glazers",Glazers);
        systemController.getSystemSubscribers().put("Gate13",fan);
        systemController.getSystemSubscribers().put("TomerSein",admin);
        systemController.getSystemSubscribers().put("ItaiKatz",admin2);
        ManchesterUnited = new Team("Manchester United",Glazers,1899);
        ManchesterUnited.getTeamOwners().add(Barkat); //todo will be changed later to a normal function depends on 6.1
        BeerSheva = new Team("Beer Sheva", Barkat,1973);
        Barkat.getTeams().add(BeerSheva); //todo will be changed later to a normal function depends on 6.1
        Barkat.getTeams().add(ManchesterUnited);
        Glazers.getTeams().add(ManchesterUnited);
        systemController.addTeam(ManchesterUnited);
        systemController.addTeam(BeerSheva);
        systemController.addComplaint("My system doesn't work",fan);
        systemController.addComplaint("I don't like this team",fan);
        systemController.addComplaint("",fan);



    }

    @Test
    public void UC8_1(){
        //1
        //close a team 1st time
        assertTrue(admin.closeTeam("Beer Sheva"));

        //2
        //close team that doesn't exist
        assertFalse(admin.closeTeam("HTA"));

        //3
        //close team that is already closed
        assertFalse(admin.closeTeam("Beer Sheva"));

    }

    @Test
    public void UC8_2(){
        //1
        //checks if we can delete a fan from the system

        assertEquals("The User Gate13 was removed",admin.deleteSubscriber("Gate13"));

        //2 checks that the user was deleted from the list
        assertFalse(systemController.getSystemSubscribers().containsKey("Gate13"));

        //3
        //checks that the admin can't delete a user that doesn't exist
        assertEquals("User doesn't exist in the system",admin.deleteSubscriber("Gate13"));

        //4
        //checks that the admin can't delete an exclusive team owner
        assertEquals("Can't remove an exclusive team owner",admin.deleteSubscriber("AlonaBarkat"));
        assertTrue(systemController.getSystemSubscribers().containsKey("AlonaBarkat"));

        //5
        //checks admin can't delete himself
        assertEquals("Admin can't remove his own user",admin.deleteSubscriber("TomerSein"));
        assertTrue(systemController.getSystemSubscribers().containsKey("TomerSein"));

    }

    @Test
    public void UC8_3_1(){

        //1
        //check if the complaints are displayed
        assertEquals(2,admin.displayComplaints().size());

    }

    @Test
    public void UC8_3_2(){

        //1
        //regular test add a comment
        assertTrue(admin.replyComplaints(0,admin, "Solved"));
 //       System.out.println(systemController.getSystemComplaints().get(0).toString());

        //2
        //can't add an empty comment
        assertFalse(admin.replyComplaints(0,admin, ""));

        //3
        //can't add a comment to invalid complaint id
        assertFalse(admin.replyComplaints(3,admin, ""));

    }

}