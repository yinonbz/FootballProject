package businessLayer.userTypes.Administration;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import serviceLayer.SystemController;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private SystemController systemController;
    private Player messi;
    private TeamOwner teamOwner;

    @Before
    public void createTestValues() {
        this.messi = new Player("Messi", "Messi123", "Messi", "", "foward", null, systemController);
        this.teamOwner = new TeamOwner("MessiFictive", "MessiF123", "MessiF", systemController);
    }

    @Test
    void isOwner() {
        assertFalse(messi.isOwner());
        Player hanfish;
        messi.setTeamOwner(teamOwner);
        assertTrue(messi.isOwner());
    }
}