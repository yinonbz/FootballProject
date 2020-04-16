package businessLayer.userTypes.Administration;

import org.junit.*;
import businessLayer.userTypes.SystemController;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class CoachTest {

    private SystemController systemController;
    private Coach henry;
    private TeamOwner teamOwner;

    @Before
    public void createTestValues() {
        henry = new Coach("Henry", "Henry123", "Henry", "manager", "assistent manager",0,systemController);
        teamOwner = new TeamOwner("HenryFictive", "HenryF123", "HenryF", systemController);
    }

    @Test
    public void isOwner(){
        assertFalse(henry.isOwner());
        henry.setTeamOwner(teamOwner);
        assertTrue(henry.isOwner());
    }

}