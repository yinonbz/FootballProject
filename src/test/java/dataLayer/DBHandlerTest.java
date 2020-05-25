package dataLayer;

import businessLayer.userTypes.Administration.FIELDJOB;
import businessLayer.userTypes.Administration.Permissions;
import businessLayer.userTypes.Administration.RoleInTeam;
import businessLayer.userTypes.Administration.roleRef;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DBHandlerTest {
    DBHandler db;
    Map<String,ArrayList<String>> detailsPlayer;
    Map<String,ArrayList<String>> detailsCoach;
    Map<String,ArrayList<String>> detailsTM;
    Map<String,ArrayList<String>> detailsTO;
    Map<String,ArrayList<String>> detailsAdmin;
    Map<String,ArrayList<String>> detailsAR;
    Map<String,ArrayList<String>> detailsRef;

    @Before
    public void DBHandlerTest() {
        db = new DBHandler("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://132.72.65.33:3306/demodb");

        //playerDetails
        detailsPlayer = new HashMap<>();
        ArrayList<String> teamID = new ArrayList<>();
        teamID.add("Barcelona");
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
        ArrayList<String> roleInTeam = new ArrayList<>();
        roleInTeam.add(RoleInTeam.TACTICAL.name());
        ArrayList<String> training = new ArrayList<>();
        training.add("GK");
        ArrayList<String> salaryC = new ArrayList<>();
        salaryC.add("546");
        ArrayList<String> teamOwnerIDC= new ArrayList<>();
        //teamOwnerIDC.add("");
        ArrayList<String> teams = new ArrayList<>();
        teams.add("Barcelona");
        teams.add("Ajax");
        detailsCoach.put("roleInTeam", roleInTeam);
        detailsCoach.put("training", training);
        detailsCoach.put("salary", salaryC);
        detailsCoach.put("ownerFictive", teamOwnerIDC);
        detailsCoach.put("teams",teams);

        //teamManager Details
        detailsTM = new HashMap<>();
        ArrayList<String> teamIDTM = new ArrayList<>();
        teamIDTM.add("Barcelona");
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("GENERAL");
        ArrayList<String> salaryTM = new ArrayList<>();
        salaryTM.add("546");
        ArrayList<String> teamOwnerIDTM= new ArrayList<>();
        //teamOwnerIDTM.add("");

        detailsTM.put("teamID", teamIDTM);
        detailsTM.put("permissions", permissions);
        detailsTM.put("salary", salaryTM);
        detailsTM.put("ownerFictive", teamOwnerIDTM);

        //teamOwner Details
        detailsTO = new HashMap<>();
        ArrayList<String> teamIDTO = new ArrayList<>();
        teamIDTO.add("Barcelona");
        teamIDTO.add("Ajax");
        ArrayList<String> playerID = new ArrayList<>();
        ArrayList<String> coachID = new ArrayList<>();
        ArrayList<String> managerID = new ArrayList<>();
        //playerID.add("GENERAL");
        ArrayList<String> assigneeID = new ArrayList<>();
        //salaryTM.add("546");
        ArrayList<String> teamManagerID= new ArrayList<>();
        //teamOwnerIDTM.add("");

        detailsTO.put("teams", teamIDTO);
        detailsTO.put("playerID", playerID);
        detailsTO.put("coachID", coachID);
        detailsTO.put("managerID", managerID);
        detailsTO.put("ownersAssigned", assigneeID);
        detailsTO.put("managersAssigned", teamManagerID);

        //Admin Details
        detailsAdmin = new HashMap<>();
        ArrayList<String> approvedA = new ArrayList<>();
        approvedA.add("1");

        detailsAdmin.put("approved", approvedA);

        //AR Details
        detailsAR = new HashMap<>();
        ArrayList<String> approvedAR = new ArrayList<>();
        approvedAR.add("1");

        detailsAR.put("approved", approvedAR);

        //referee Details
        detailsRef = new HashMap<>();
        ArrayList<String> roleReferee = new ArrayList<>();
        roleReferee.add(roleRef.ASSISTANT.name());

        ArrayList<String> matches = new ArrayList<>();
        matches.add("200003");
        matches.add("200004");

        detailsRef.put("roleRef", roleReferee);
        detailsRef.put("matches", matches);

    }

    @Test
    public void containInDB() {
        assertTrue(db.containInDB("Arthur",null,null));
        assertFalse(db.containInDB("Arthuri",null,null));
    }

    @Test
    public void addToDB() {

        //add player
        //assertTrue(db.addToDB("ArthurMelo",String.valueOf("Arthur".hashCode()),"Melo","Player",detailsPlayer));
        //add coach
        //assertTrue(db.addToDB("Valverde",String.valueOf("Valverde1".hashCode()),"ValverdeOut","Coach",detailsCoach));
        //add manager
//        assertTrue(db.addToDB("Pep",String.valueOf("Pep1".hashCode()),"Guardiola","TeamManager",detailsTM));
        //addOwner
//        assertTrue(db.addToDB("Maldini",String.valueOf("Maldini1".hashCode()),"Maldini","TeamOwner",detailsTO));
        //addAdmin
//        assertTrue(db.addToDB("AdminInon",String.valueOf("AdminInon1".hashCode()),"AdminInon","Admin",detailsAdmin));
        //addAR
//        assertTrue(db.addToDB("testAR",String.valueOf("testAR1".hashCode()),"testAR","AR",detailsAR));
        //addRef
        assertTrue(db.addToDB("testRef",String.valueOf("testRef1".hashCode()),"testRef","Referee",detailsRef));



    }

    @Test
    public void deleteFromDB() {
        assertTrue(db.removeFromDB("testRef",null,null));
        assertFalse(db.removeFromDB("testRef",null,null));

    }

    @Test
    public void update() {
        /*
        Map<String,String> arguments = new HashMap<>();
        arguments.put("setApproved","true");
        arguments.put("adminID","AdminInon");
        assertTrue(db.update(SUBSCRIBERSUPDATES.ADMINSETAPPROVED,arguments));
        arguments.clear();

        arguments.put("setApproved","true");
        arguments.put("ARID","testAR");
        assertTrue(db.update(SUBSCRIBERSUPDATES.ARSETAPPROVED,arguments));
        arguments.clear();

        arguments.put("teamID","Cruyff FC");
        arguments.put("playerID","Umtiti");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETTEAMTOPLAYER,arguments));
        arguments.clear();

        arguments.put("teamID","Cruyff FC");
        arguments.put("managerID","Zavaleta");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETTEAMTOTM,arguments));
        arguments.clear();

        arguments.put("birthDate","1992/08/28");
        arguments.put("playerID","Umtiti");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETPLAYERBIRTHDATE,arguments));
        arguments.clear();

        arguments.put("name","Samuel Umtiti");
        arguments.put("subscriberID","Umtiti");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETSUBSCRIBERNAME,arguments));
        arguments.clear();

        arguments.put("fieldJob", FIELDJOB.DM.name());
        arguments.put("playerID","Umtiti");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETPLAYERFIELDJOB,arguments));
        arguments.clear();

        arguments.put("salary", "600");
        arguments.put("playerID","Umtiti");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETPLAYERSALARY,arguments));
        arguments.clear();

        arguments.put("salary", "600");
        arguments.put("managerID","Zavaleta");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETTMSALARY,arguments));
        arguments.clear();

        arguments.put("permissions", Permissions.COACHORIENTED.name());
        arguments.put("managerID","Zavaleta");
        assertTrue(db.update(SUBSCRIBERSUPDATES.SETTMPERMISSIONS,arguments));
        arguments.clear();

        arguments.put("ownerID", "Morrow");
        arguments.put("teamID", "Cruyff FC");
        arguments.put("managersAssigned","Zavaleta");
        assertTrue(db.update(SUBSCRIBERSUPDATES.ADDMANAGERTOOWNER,arguments));
        arguments.clear();

        arguments.put("ownerID", "Morrow");
        arguments.put("teamID", "Cruyff FC");
        arguments.put("managerID","Zavaleta");
        assertTrue(db.update(SUBSCRIBERSUPDATES.DELETEMANAGERFROMOWNER,arguments));
        arguments.clear();

        arguments.put("ownerID", "Morrow");
        arguments.put("teamID", "Cruyff FC");
        arguments.put("assigneeID","Ben-Harush");
        assertTrue(db.update(SUBSCRIBERSUPDATES.ADDOWNERTOOWNER,arguments));
        arguments.clear();

        arguments.put("ownerID", "Morrow");
        arguments.put("teamID", "Cruyff FC");
        arguments.put("assigneeID","Ben-Harush");
        assertTrue(db.update(SUBSCRIBERSUPDATES.DELETEOWNERFROMOWNER,arguments));
        arguments.clear();

        arguments.put("teamID", "Cruyff FC");
        arguments.put("ownerID","Ben-Harush");
        assertTrue(db.update(SUBSCRIBERSUPDATES.ADDTEAMTOOWNER,arguments));
        arguments.clear();

*/
        //change passwords to hash
       /* ArrayList<Map<String, ArrayList<String>>> subscribers = db.selectAllRecords(UserTypes.SUBSCRIBER);
        for(String str: subscribers.get(0).get("subscribers")){
            String pass=null;
            try{
                Map<String, ArrayList<String>> sub = db.selectFromDB(str,null,null);
                pass = String.valueOf(sub.get("password"));
                Map<String,String> arguments = new HashMap<>();
                String hashPass = String.valueOf(pass.hashCode());
                arguments.put("password",hashPass);
                arguments.put("subscriberID",str);
                db.update(SUBSCRIBERSUPDATES.SETSUBSCRIBERPASSWORD,arguments);
            }
            catch(Exception e){
                System.out.println(str);
                e.printStackTrace();
            }

        }*/
    }

    @Test
    public void selectAllRecords() {
        //retreive all referees
        ArrayList<Map<String, ArrayList<String>>> allRecords = db.selectAllRecords(UserTypes.REFEREE,null);
        for(Map<String,ArrayList<String>> map: allRecords){
            for(Map.Entry<String,ArrayList<String>> entry: map.entrySet()){
                System.out.print(entry.getKey()+": ");
                for(String str :entry.getValue()){
                    System.out.print(str+", ");
                }
                System.out.println();
            }
            System.out.println("*******************************");

        }

    }

    @Test
    public void selectFromDB() {
        ArrayList<Map<String, ArrayList<String>>> maps = new ArrayList<>();
        Map<String, ArrayList<String>> map1 = db.selectFromDB("Arthur",null,null);
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arthur2",null,null);
        Map<String, ArrayList<String>> map3 = db.selectFromDB("adminA",null,null);
        Map<String, ArrayList<String>> map4 = db.selectFromDB("Akinola",null,null);
        Map<String, ArrayList<String>> map5 = db.selectFromDB("Altman",null,null);
        Map<String, ArrayList<String>> map6 = db.selectFromDB("Bradley",null,null);
        Map<String, ArrayList<String>> map7 = db.selectFromDB("Cohen",null,null);
        Map<String, ArrayList<String>> map8 = db.selectFromDB("Dabbur",null,null);
        Map<String, ArrayList<String>> map9 = db.selectFromDB("Hackett",null,null);
        maps.add(map1);
        maps.add(map3);
        maps.add(map4);
        maps.add(map5);
        maps.add(map6);
        maps.add(map7);
        maps.add(map8);
        maps.add(map9);
        for(Map<String,ArrayList<String>> map: maps){
            for(Map.Entry<String,ArrayList<String>> entry: map.entrySet()){
                System.out.print(entry.getKey()+": ");
                for(String str :entry.getValue()){
                    System.out.print(str);
                }
                System.out.println();
            }
            System.out.println("*******************************");

        }
    }
}