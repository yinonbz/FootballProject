package businessLayer.userTypes.viewers;

import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import serviceLayer.SystemService;

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
    public void UC_3_4_a(){
        //1
        //check that adding a complaint works
        //todo: check with new DB
        //assertTrue(systemService.addComplaint("Hello I don't like the GUI", "Gate13"));

    }

    @Test
    public void UC_3_4_b(){
        //2
        //check that no subscriber can add a complaint
        assertFalse(systemService.addComplaint("Hello", "Rayola"));
    }

    @Test
    public void UC_3_4_c(){
        //3
        //check that you can't add an empty complaint
        assertFalse(systemService.addComplaint("", "Gate13"));
    }

}
