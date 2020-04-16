package businessLayer.userTypes.Administration;

import dataLayer.DataBaseValues;
import dataLayer.DemoDB;
import org.junit.BeforeClass;
import serviceLayer.MatchService;


class RefereeTest {

    static DemoDB DB;
    static DataBaseValues tDB;
    static MatchService matchService;

    @BeforeClass
    public static void defineValues(){
        tDB = new DataBaseValues();
        DB = tDB.getDB();
        matchService = new MatchService();
    }





}