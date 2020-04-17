package businessLayer.userTypes;

import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.alertSystem.AlertSystem;

import java.util.HashMap;

public abstract class Subscriber {
    protected String name;
    protected String username;
    protected String password;
    protected SystemController systemController;
    protected AlertSystem alertSystem;
    protected HashMap <Integer, Complaint> complaints;

    /**
     * @param username
     * @param password
     */
    public Subscriber(String username, String password,String name, SystemController systemController) {
        this.name = name;
        this.username = username;
        this.password = password;
        complaints = new HashMap<>();
        this.systemController=systemController;
    }


    /**
     * add the complaint to the user's private complaints box
     * @param complaint of the user
     */
    public void addComplaint(Complaint complaint){
        this.complaints.put(complaint.getId(),complaint);
    }

    /**
     * the function replaces the complaint without the comment to the complain with the comment
     * @param complaint the new complaint
     */
    public void setComplaint (Complaint complaint){
        if (complaint!=null){
            if(complaints.containsKey(complaint.getId())){
                complaints.remove(complaint.getId());
                complaints.put(complaint.getId(),complaint);
            }
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * private function that checks that a string represents an interger
     * @param value the string
     * @return true if it an integer
     */
    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    protected boolean isNumeric(String str){
        if(str == null || str.length() ==0){
            return false;
        }
        for(char c : str.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return  true;
    }
}
