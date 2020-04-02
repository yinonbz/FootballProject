import java.util.List;

public class Guest {
    private int guestId;
    private SystemController systemController;

    /**
     * @param guestId
     */
    public Guest(int guestId) {
        this.guestId = guestId;
    }

    /**
     * @return
     */
    public Boolean register(){
        return true;
    }

    /**
     * @return
     */
    public Boolean login(){
        return true;
    }

    /**
     * @param searchInput
     * @return
     */
    public List<Object> search(String searchInput){
        return null;
    }

    /**
     *
     */
    public void openPage(){

    }

    /**
     * @return
     */
    public int getGuestId() {
        return guestId;
    }

    /**
     * @param guestId
     */
    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    /**
     * @return
     */
    public SystemController getSystemController() {
        return systemController;
    }

    /**
     * @param systemController
     */
    public void setSystemController(SystemController systemController) {
        this.systemController = systemController;
    }
}
