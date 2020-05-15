package dataLayer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBHandlerTest {
    DBHandler db;

    @Before
    public void connectToDB() {
        db = new DBHandler();

    }

    @Test
    public void containInDB(){
        assertTrue(db.containInDB("Arthur"));
        assertFalse(db.containInDB("Arthuri"));
    }
}
