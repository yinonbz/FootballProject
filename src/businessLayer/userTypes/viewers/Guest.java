package businessLayer.userTypes.viewers;

import businessLayer.userTypes.SystemController;

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

    /** UC - 2.1
     *   * This function will be called from the UI controller.
     *   * It will start the registration procedure in the business layer.
     * @returntrue if the registration was successful
     *  false else
     */
    public Boolean register(){
        if(systemController.createRegistrationForm(this) == null)
            return false;
        return true;
    }

    /** UC - 2.2
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
     * This function will mediate between the UI login/registration form and the System controller. it will get the user's input from the GUI login/registration form and will place the input in the parameters (String userNameInput, String passwordInput).
     * @param userNameInput the userName input as entered at the GUI
     * @param passwordInput the password input as entered at the GUI
     */
    public void enterUserDetails(String userNameInput, String passwordInput) {
        //Send to UI to fill the userNameInput and passwordInput to create a new Subscriber with GUI
    }

    /**
     * This function will mediate between the UI registration form and the System controller. it will get the user's input from the GUI registration form and will place the input in the parameter (String name)
     * @param firstName the user's real first name input as entered at the GUI
     * @param lastName the user's real first last input as entered at the GUI
     */
    public void enterUserRealName(String firstName, String lastName) {
        //Send to UI to fill the user's real name to create a new Subscriber with GUI
    }
}