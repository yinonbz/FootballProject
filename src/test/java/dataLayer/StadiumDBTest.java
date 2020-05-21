package dataLayer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StadiumDBTest {
    StadiumDB db;
    Map<String, ArrayList<String>> stadiumDetails;

    @Before
    public void StadiumDBTest(){
        db = new StadiumDB();
        //complaint details

        stadiumDetails = new HashMap<>();
        ArrayList<String> stadiumID = new ArrayList<>();
        stadiumID.add("Arad stadium");
        stadiumDetails.put("stadiumID",stadiumID);

        ArrayList<String> numOfSeats = new ArrayList<>();
        numOfSeats.add("12345");
        stadiumDetails.put("numOfSeats",numOfSeats);

        ArrayList<String> ticketCost = new ArrayList<>();
        ticketCost.add("123");
        stadiumDetails.put("ticketCost",ticketCost);

        ArrayList<String> teams = new ArrayList<>();
        stadiumDetails.put("teams",teams);

    }

    @Test
    public void containInDB(){
        assertTrue(db.containInDB("Cruyff Stadium",null,null));
        assertFalse(db.containInDB("Arad stadium",null,null));
    }

    @Test
    public void addToDB() {

        assertTrue(db.addToDB("Arad Stadium","12345","123",null,stadiumDetails));
        assertTrue(db.addToDB("Cruyff Stadium","12345","123",null,stadiumDetails));

    }

    @Test
    public void deleteFromDB() {
        assertTrue(db.removeFromDB("Arad Stadium",null,null));
        assertTrue(db.removeFromDB("Cruyff Stadium",null,null));
        assertFalse(db.removeFromDB("Arad Stadium",null,null));

    }

    @Test
    public void selectFromDB() {
        ArrayList<Map<String, ArrayList<String>>> maps = new ArrayList<>();
        Map<String, ArrayList<String>> map1 = db.selectFromDB("Cruyff Stadium",null,null);
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arad Stadium",null,null);
        Map<String, ArrayList<String>> map3 = db.selectFromDB("Old Trafford",null,null);
        Map<String, ArrayList<String>> map4 = db.selectFromDB("0",null,null);
        maps.add(map1);
        maps.add(map3);
        maps.add(map4);
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
