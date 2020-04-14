package businessLayer.userTypes.Administration;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import serviceLayer.SystemController;

import static org.junit.jupiter.api.Assertions.*;

class TeamManagerTest {
    private SystemController systemController;
    private TeamManager teamManager;
    private TeamOwner teamOwner;

    @Before
    public void createTestValues() {
        teamManager = new TeamManager("JurgenKlopp", "Klopp123", "Klopp", null, systemController);
        teamOwner = new TeamOwner("JurgenFictive","KloppF123","KloppF",systemController);
    }

    @Test
    void isOwner() {
        assertFalse(teamManager.isOwner());
        teamManager.setTeamOwner(teamOwner);
        assertTrue(teamManager.isOwner());
    }
}