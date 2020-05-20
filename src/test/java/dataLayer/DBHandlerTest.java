package dataLayer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DBHandlerTest {
    DBHandler db;
    Map<String,ArrayList<String>> detailsPlayer;
    Map<String,ArrayList<String>> detailsCoach;
    Map<String,ArrayList<String>> detailsTM;

    @Before
    public void connectToDB() {
        db = new DBHandler("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://localhost:3306/testdb2");

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
        teamOwnerID.add("");
        detailsPlayer.put("teamID", teamID);
        detailsPlayer.put("birthDate", birthDate);
        detailsPlayer.put("fieldJob", fieldJob);
        detailsPlayer.put("salary", salary);
        detailsPlayer.put("ownerFictive", teamOwnerID);

        //coach Details
        detailsCoach = new HashMap<>();
        ArrayList<String> teamIDC = new ArrayList<>();
        teamIDC.add("Barcelona");
        ArrayList<String> training = new ArrayList<>();
        training.add("GK");
        ArrayList<String> salaryC = new ArrayList<>();
        salaryC.add("546");
        ArrayList<String> teamOwnerIDC= new ArrayList<>();
        teamOwnerIDC.add("");
        ArrayList<String> teams = new ArrayList<>();
        teams.add("Barcelona");
        teams.add("Ajax");
        detailsCoach.put("teamID", teamID);
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
        teamOwnerIDTM.add("");

        detailsTM.put("teamID", teamIDTM);
        detailsTM.put("permissions", permissions);
        detailsTM.put("salary", salaryTM);
        detailsTM.put("ownerFictive", teamOwnerIDTM);

        //teamOwner Details
        //detailsTO = new HashMap<>();
        ArrayList<String> teamIDTO = new ArrayList<>();
        teamIDTO.add("Barcelona");
       // ArrayList<String> permissions = new ArrayList<>();
        permissions.add("GENERAL");
    //    ArrayList<String> salaryTM = new ArrayList<>();
        salaryTM.add("546");
       // ArrayList<String> teamOwnerIDTM= new ArrayList<>();
        teamOwnerIDTM.add("");

        detailsTM.put("teamID", teamIDTM);
        detailsTM.put("permissions", permissions);
        detailsTM.put("salary", salaryTM);
        detailsTM.put("ownerFictive", teamOwnerIDTM);
    }

    @Test
    public void containInDB() {
        assertTrue(db.containInDB("Arthur"));
        assertFalse(db.containInDB("Arthuri"));
    }

    @Test
    public void addToDB() {

        //assertTrue(db.addToDb("ArthurMelo",String.valueOf("Arthur".hashCode()),"Melo","Player",detailsPlayer));
        //assertTrue(db.addToDb("Valverde",String.valueOf("Valverde1".hashCode()),"ValverdeOut","Coach",detailsCoach));
   //     assertTrue(db.addToDb("Pep",String.valueOf("Pep1".hashCode()),"Guardiola","TeamManager",detailsTM));


    }

    @Test
    public void deleteFromDB() {
        Map<String, ArrayList<String>> map = db.selectFromDB("Arthur");
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arthur2");
        int k=0;

    }

    @Test
    public void selectFromDB() {
        ArrayList<Map<String, ArrayList<String>>> maps = new ArrayList<>();
        Map<String, ArrayList<String>> map1 = db.selectFromDB("Arthur");
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arthur2");
        Map<String, ArrayList<String>> map3 = db.selectFromDB("adminA");
        Map<String, ArrayList<String>> map4 = db.selectFromDB("Akinola");
        Map<String, ArrayList<String>> map5 = db.selectFromDB("Altman");
        Map<String, ArrayList<String>> map6 = db.selectFromDB("Bradley");
        Map<String, ArrayList<String>> map7 = db.selectFromDB("Cohen");
        Map<String, ArrayList<String>> map8 = db.selectFromDB("Dabbur");
        Map<String, ArrayList<String>> map9 = db.selectFromDB("Hackett");
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