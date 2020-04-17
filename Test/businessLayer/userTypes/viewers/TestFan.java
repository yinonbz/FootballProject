package businessLayer.userTypes.viewers;

import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import serviceLayer.SystemService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFan {

    static SystemService systemService;
    static DemoDB DB;
    static DataBaseValues tDB;

    @BeforeClass
    static public void defineValues(){
        systemService = new SystemService();
        tDB = new DataBaseValues();
        DB = tDB.getDB();
    }

    @Test
    public void UC3_4(){
        //1
        //check that adding a complaint works
        assertTrue(systemService.addComplaint("Hello I don't like the GUI", "Gate13"));

        //2
        //check that you can't add an empty complaint
        assertFalse(systemService.addComplaint("", "Gate13"));

        //3
        //check that no subscriber can add a complaint
        assertFalse(systemService.addComplaint("", "Rayola"));

    }
}
