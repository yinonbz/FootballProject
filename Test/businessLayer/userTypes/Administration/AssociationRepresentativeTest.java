package businessLayer.userTypes.Administration;

import businessLayer.Utilities.Financial.FinancialMonitoring;
import org.junit.Before;
import org.junit.Test;
import serviceLayer.LeagueController;
import serviceLayer.SystemController;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssociationRepresentativeTest {

    private LeagueController leaguesControllerTest;
    private SystemController systemController;
    private AssociationRepresentative gal;
    private AssociationRepresentative dor;
    private AssociationRepresentative tali;

    @Before
    public void createTestValues() {

        leaguesControllerTest = LeagueController.LeagueController();
        systemController = SystemController.SystemController();
        gal = new AssociationRepresentative("gal5", "1111", "gal", new FinancialMonitoring("empty for now"), leaguesControllerTest, systemController);
        dor = new AssociationRepresentative("gal5", "1111", "gal", new FinancialMonitoring("empty for now"), leaguesControllerTest, systemController);
        tali = new AssociationRepresentative("gal5", "1111", "gal", new FinancialMonitoring("empty for now"), leaguesControllerTest, systemController);

    }

    @Test
    public void test_UC9_1() {
        //1. gal creates a new league successfully
        assertTrue(gal.createLeague("This is the first league created for the first test"));

        //2. dor tries to create the same league without success
        assertFalse(dor.createLeague("This is the first league created for the first test"));

        //3. tali tries to create a new league with null
        assertFalse((tali.createLeague(null)));
    }

    @Test
    public void test_UC9_2() {
        //1. gal creates a new season successfully
        //assertTrue(gal.createSeason("101","1", new Date(), new Date(), ));
    }
}
