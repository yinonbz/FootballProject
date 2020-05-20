package dataLayer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ComplaintsDBTest {
    ComplaintsDB db;
    Map<String, ArrayList<String>> complaintDetails;

    @Before
    public void ComplaintsDBTest(){
        db = new ComplaintsDB();
        //complaint details

        complaintDetails = new HashMap<>();
        ArrayList<String> HandlerID = new ArrayList<>();
        HandlerID.add("adminA");
        ArrayList<String> WriterID = new ArrayList<>();
        WriterID.add("Zahavi");
        ArrayList<String> comment = new ArrayList<>();
        comment.add("delete cookies and cache and try again");
        ArrayList<String> content = new ArrayList<>();
        content.add("can't logout of system");
        ArrayList<String> isAnswered= new ArrayList<>();
        isAnswered.add("0");
        complaintDetails.put("HandlerID", HandlerID);
        complaintDetails.put("WriterID", WriterID);
        complaintDetails.put("comment", comment);
        complaintDetails.put("content", content);
        complaintDetails.put("isAnswered", isAnswered);

    }

    @Test
    public void containInDB() {
        assertTrue(db.containInDB("1"));
        assertFalse(db.containInDB("3"));
    }

    @Test
    public void addToDB() {

        assertTrue(db.addToDB("3","adminA","Zahavi","0",complaintDetails));
        assertFalse(db.addToDB("3","adminA","Zahavi","0",complaintDetails));

    }

    @Test
    public void deleteFromDB() {
        assertTrue(db.removeFromDB("3"));
        assertFalse(db.removeFromDB("3"));

    }

    @Test
    public void selectFromDB() {
        ArrayList<Map<String, ArrayList<String>>> maps = new ArrayList<>();
        Map<String, ArrayList<String>> map1 = db.selectFromDB("1");
        Map<String, ArrayList<String>> map2 = db.selectFromDB("3");
        Map<String, ArrayList<String>> map3 = db.selectFromDB("2");
        maps.add(map1);
        maps.add(map3);
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

    @Test
    public void countRecords(){
        assertEquals(2,db.countRecords());
        assertTrue(db.countRecords() ==3);
    }

    @Test
    public void getAllRecords(){
        ArrayList<Map<String, ArrayList<String>>> allRecords = db.selectAllRecords(null);
        for(Map<String,ArrayList<String>> map: allRecords){
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
