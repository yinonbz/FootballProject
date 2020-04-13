package businessLayer.userTypes.Administration;

import org.junit.Before;
import org.junit.Test;
import serviceLayer.SystemController;

import static org.junit.Assert.*;

public class PlayerTest {
    private SystemController systemController;
    private Player messi;
    private TeamOwner teamOwner;

    @Before
    public void createTestValues() {
        messi = new Player("Messi", "Messi123", "Messi", "", "foward", null, systemController);
        teamOwner = new TeamOwner("MessiFictive", "MessiF123", "MessiF", systemController);
    }

    @Test
    public void isOwner(){
        assertFalse(messi.isOwner());
        messi.setTeamOwner(teamOwner);
        assertTrue(messi.isOwner());
    }
}