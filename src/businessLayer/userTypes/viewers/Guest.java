package businessLayer.userTypes.viewers;

import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.List;

public class Guest {
    private int guestId;
    private SystemController systemController;

    /**
     * @param guestId
     */
    public Guest(int guestId, SystemController systemController) {
        this.guestId = guestId;
        this.systemController=systemController;
    }

    /**
     * @return
     */
    public Boolean register(){
        return true;
    }

    /**
     * This function will be called from the UI controller.
     * It will start the login procedure in the business layer.
     * @return true if the login was successful
     * false else
     */
    public Boolean login(){
        if(systemController.createLoginForm(this) == null)
            return false;
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

    /**
     * @param userNameInput the input as the
     * @param passwordInput
     */
    public void enterUserDetails(String userNameInput, String passwordInput) {
        //Send to UI to fill the userNameInput and passwordInput to create a new Subscriber with GUI
    }
}