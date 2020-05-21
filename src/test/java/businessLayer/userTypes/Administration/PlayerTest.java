package businessLayer.userTypes.Administration;

import org.junit.Before;
import org.junit.Test;
import businessLayer.userTypes.SystemController;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class PlayerTest {
    private SystemController systemController;
    private Player messi;
    private TeamOwner teamOwner;

    @Before
    public void createTestValues() {
        messi = new Player("Messi", "Messi123", "Messi", "", FIELDJOB.valueOf("CF"), 0,null, systemController);
        teamOwner = new TeamOwner("MessiFictive", "MessiF123", "MessiF", systemController);
    }

    @Test
    public void isOwner(){
        assertFalse(messi.isOwner());
        messi.setTeamOwner(teamOwner);
        assertTrue(messi.isOwner());
    }

    @Test
    public void updatePage(){
        assertTrue(messi.updatePage("today i played against machbi haifa"));
        assertFalse(messi.updatePage(""));
        assertFalse(messi.updatePage(null));
    }
    @Test
    public void updateDetails(){
        messi.setName("leoMessi");
        assertEquals(messi.getName(),"leoMessi");
        messi.setBirthDate("11/9/93");
        assertEquals(messi.getBirthDate(),"11/9/93");
    }
}