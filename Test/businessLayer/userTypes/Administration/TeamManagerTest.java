package businessLayer.userTypes.Administration;

import org.junit.Before;
import org.junit.Test;
import serviceLayer.SystemController;

import static org.junit.Assert.*;

public class TeamManagerTest {
    private SystemController systemController;
    private TeamManager teamManager;
    private TeamOwner teamOwner;

    @Before
    public void createTestValues() {
        teamManager = new TeamManager("JurgenKlopp", "Klopp123", "Klopp", null, systemController);
        teamOwner = new TeamOwner("JurgenFictive","KloppF123","KloppF",systemController);
    }

    @Test
    public void isOwner() {
        assertFalse(teamManager.isOwner());
        teamManager.setTeamOwner(teamOwner);
        assertTrue(teamManager.isOwner());
    }
}