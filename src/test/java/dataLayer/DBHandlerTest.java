package dataLayer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class DBHandlerTest {
    DBHandler db;

    @Before
    public void connectToDB() {
        db = new DBHandler();

    }

    @Test
    public void containInDB() {
        assertTrue(db.containInDB("Arthur"));
        assertFalse(db.containInDB("Arthuri"));
    }

    @Test
    public void addToDB() {
        Map<String, ArrayList<String>> map = db.selectFromDB("Arthur");
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arthur2");
        int k=0;

    }

    @Test
    public void deleteFromDB() {
        Map<String, ArrayList<String>> map = db.selectFromDB("Arthur");
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arthur2");
        int k=0;

    }

    @Test
    public void selectFromDB() {
        Map<String, ArrayList<String>> map = db.selectFromDB("Arthur");
        Map<String, ArrayList<String>> map2 = db.selectFromDB("Arthur2");
        int k=0;

    }
}