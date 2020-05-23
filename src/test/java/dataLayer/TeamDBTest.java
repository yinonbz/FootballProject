package dataLayer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TeamDBTest {
    TeamDB db;
    Map<String, ArrayList<String>> teamDetails;
    Map<String, ArrayList<String>> detailsPlayer;
    Map<String, ArrayList<String>> detailsCoach;
    Map<String, ArrayList<String>> detailsTO;
    DBHandler dbHandler;


    @Before
    public void connectToDB() {
       dbHandler = new DBHandler();
        //team details
        teamDetails = new HashMap<>();
        ArrayList<String> name = new ArrayList<>();
        name.add("Cruyff FC");
        ArrayList<String> establishedYear = new ArrayList<>();
        establishedYear.add("1992");
        ArrayList<String> isActive = new ArrayList<>();
        isActive.add("CM");
        ArrayList<String> teamManagerID = new ArrayList<>();
        teamManagerID.add("Zavaleta");
        ArrayList<String> closedByAdmin= new ArrayList<>();
        closedByAdmin.add("");
        ArrayList<String> stadium= new ArrayList<>();
        stadium.add("Anfield");

        //player details
        detailsPlayer = new HashMap<>();
        ArrayList<String> teamID = new ArrayList<>();
        teamID.add("Cruyff FC");
        ArrayList<String> birthDate = new ArrayList<>();
        birthDate.add("1992/02/03");
        ArrayList<String> fieldJob = new ArrayList<>();
        fieldJob.add("CM");
        ArrayList<String> salary = new ArrayList<>();
        salary.add("546");
        ArrayList<String> teamOwnerID= new ArrayList<>();
        //teamOwnerID.add("");
        detailsPlayer.put("teamID", teamID);
        detailsPlayer.put("birthDate", birthDate);
        detailsPlayer.put("fieldJob", fieldJob);
        detailsPlayer.put("salary", salary);
        detailsPlayer.put("ownerFictive", teamOwnerID);

        //coach Details
        detailsCoach = new HashMap<>();
        ArrayList<String> teamIDC = new ArrayList<>();
        teamIDC.add("Cruyff FC");
        ArrayList<String> training = new ArrayList<>();
        training.add("GK");
        ArrayList<String> salaryC = new ArrayList<>();
        salaryC.add("546");
        ArrayList<String> teamOwnerIDC= new ArrayList<>();
        //teamOwnerIDC.add("");
        ArrayList<String> teams = new ArrayList<>();
        teams.add("Cruyff FC");
        detailsCoach.put("teamID", teamIDC);
        detailsCoach.put("training", training);
        detailsCoach.put("salary", salaryC);
        detailsCoach.put("ownerFictive", teamOwnerIDC);
        detailsCoach.put("teams",teams);

//        dbHandler.addToDB("Jordi","Jordi1","jordi","player",detailsPlayer);
  //      dbHandler.addToDB("testCoach","testCoach1","testCoach","Coach",detailsCoach);

        teamDetails.put("name", name);
        teamDetails.put("establishedYear", establishedYear);
        teamDetails.put("isActive", isActive);
        teamDetails.put("teamManagerID", teamManagerID);
        teamDetails.put("closedByAdmin", closedByAdmin);
        teamDetails.put("stadium", stadium);
        teamDetails.put("players",new ArrayList<>());
        teamDetails.put("coaches",new ArrayList<>());
        teamDetails.put("teamOwners",new ArrayList<>());
        dbHandler.TerminateDB();
        db = new TeamDB("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://localhost:3306/testdb2");
    }

    @Test
    public void containInDB() {
        assertTrue(db.containInDB("Barcelona",null,null));
        assertFalse(db.containInDB("Cruyff FC2",null,null));
    }

    @Test
    public void addToDB() {
        assertTrue(db.addToDB(teamDetails.get("name").get(0)
                ,teamDetails.get("establishedYear").get(0),teamDetails.get("isActive").get(0)
                ,teamDetails.get("teamManagerID").get(0),teamDetails));
        assertTrue(db.containInDB("Cruyff FC",null,null));

    }


    @Test
    public void update() {
        Map<String,String> arguments = new HashMap<>();
        arguments.put("managerID","Zavaleta");
        arguments.put("teamID","Cruyff FC");
        assertTrue(db.update(TEAMUPDATES.SETTEAMMANAGER,arguments));
        arguments.clear();
        arguments.put("teamID","Cruyff FC");
        arguments.put("playerID","Umtiti");
        assertTrue(db.update(TEAMUPDATES.ADDPLAYER,arguments));
        arguments.clear();
        arguments.put("isActive","true");
        arguments.put("teamID","Cruyff FC");
        assertTrue(db.update(TEAMUPDATES.SETACTIVE,arguments));

    }

    @Test
    public void deleteFromDB() {
        assertFalse(db.removeFromDB("Cruyff FC",null,null));
        assertFalse(db.removeFromDB("Cruyff FC2",null,null));

    }

    @Test
    public void selectFromDB() {
        ArrayList<Map<String, ArrayList<String>>> maps = new ArrayList<>();
        Map<String, ArrayList<String>> map1 = db.selectFromDB("Barcelona",null,null);
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arthur2",null,null);
        Map<String, ArrayList<String>> map9 = db.selectFromDB("Cruyff FC",null,null);
        maps.add(map1);
        maps.add(map9);
        for(Map<String,ArrayList<String>> map: maps){
            for(Map.Entry<String,ArrayList<String>> entry: map.entrySet()){
                System.out.print(entry.getKey()+": ");
                for(String str :entry.getValue()){
                    System.out.print(str+" ,");
                }
                System.out.println();
            }
            System.out.println("*******************************");

        }
    }
}

