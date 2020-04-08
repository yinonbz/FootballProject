package businessLayer.userTypes;

import businessLayer.Utilities.alertSystem.AlertSystem;
import serviceLayer.SystemController;

public abstract class Subscriber {
    private String username;
    private String password;
    private SystemController systemController;
    private AlertSystem alertSystem;

    /**
     * @param username
     * @param password
     */
    public Subscriber(String username, String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * @return
     */
    abstract public Boolean editDetails();

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

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
     * @param alertSystem
     */
    public void setAlertSystem(AlertSystem alertSystem) {
        this.alertSystem = alertSystem;
    }

    /**
     * @return
     */
    public AlertSystem getAlertSystem() {
        return alertSystem;
    }

}
