package serviceLayer;

import businessLayer.Tournament.League;
import businessLayer.Utilities.alertSystem.*;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.Utilities.recommendationSystem.RecommendationSystem;
import businessLayer.userTypes.*;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.viewers.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SystemController {

    private static SystemController single_instance=null; //singleton instance

    private Map<Subscriber,List<String>> userNotifications;
    private AlertSystem alertSystem;
    private RecommendationSystem recommendationSystem;
    private List<Guest> onlineGuests;
    private List<Subscriber> systemSubscribers;
    private List<Admin> admins;
    private LoggingSystem loggingSystem;
    private List<League> leagues;
    private Admin temporaryAdmin;

    private SystemController() {

    }

    public static SystemController SystemController()
    {
        if (single_instance == null)
        {
            single_instance = new SystemController();
        }
        return single_instance;
    }

    /**
     *
     * @param subscriber
     * @param notification
     * @return
     */
    public boolean sendNotification(Subscriber subscriber, String notification){

        return true;
    }

    /**
     *
     * @param logs
     * @return
     */

    public boolean sendLogs(List<String> logs){

        return true;
    }

    /**
     *
     * @param followers
     * @param Alerts
     * @return
     */

    public boolean sendAlert(List<Subscriber> followers, List<String> Alerts){

        return true;
    }

    /**
     * @param userName The user name of the default temporary admin, as mentioned in the Readme file.
     * @param password The password of the default temporary admin, as mentioned in the Readme file.
     * @return true: if the temporary admin user was created successfully by the system. | false: The userName or password didn't match to the default temporary admin details.
     */
    public Boolean insertInfo(String userName, String password){
        if(userName.equals("admin") && password.equals("admin")) {
            temporaryAdmin = new Admin(userName,password,"tempAdmin");
            System.out.println("The temporary admin has been created successfully.");
            return true;
        }
        return false;
    }

    public Boolean initializeSystem(String password,String userName){
        if(password.equals("admin")){
            System.out.println("Please enter a new password while following the guidelines below:");
            System.out.println("* The password must be 6 to 32 characters long.");
            System.out.println("* The password must contain a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.");
            System.out.println("* The password is case-sensitive.");
            System.out.println("* Single quotes, double quotes, ampersands ( ‘  \"  & ), and spaces are not allowed.");
            System.out.println("* The password cannot be the same as your User Name name and should not contain any part of your user name.");
            String s = "* Do not post or share your password or send your password to others by email.";
            System.out.println(s.toUpperCase());
            while(!checkPasswordStrength(password,userName)){
                Scanner myObj = new Scanner(System.in);
                password = myObj.nextLine();
            }
            System.out.println("Password has been reset successfully.");
            return true;
        }
        return false;
    }

    /**
     * @param password the password from the user's input
     * @param userName the userName from the user's input (to check that the password does not contain the user name)
     * @return
     * true if:
     *     * The password must be 6 to 32 characters long.
     *     * The password must contain a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.
     *     * The password is case-sensitive.
     *     * Single quotes, double quotes, ampersands ( ‘  \"  & ), and spaces are not allowed.
     *     * The password cannot be the same as your User Name name and should not contain any part of your user name.
     * false else.
     */
    public boolean checkPasswordStrength(String password, String userName) {
        if(password.length() < 6 || password.length() > 32){
            System.out.println("The password must be 6 to 32 characters long.");
            return false;
        }
        if(!password.matches(".*\\d.*") || !password.matches(".*[a-z].*")){
            System.out.println("The password must contain a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.");
            return false;
        }
        if(password.contains("'") || password.contains("\"") || password.contains("&")){
            System.out.println("Single quotes, double quotes, ampersands ( ‘  \"  & ), and spaces are not allowed.");
            return false;
        }
        if(password.contains(userName)){
            System.out.println("The password cannot be the same as your User Name name and should not contain any part of your user name.");
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public Map<Subscriber, String> getUserNotifications() {
        return null;
    }

    /**
     *
     * @param userNotifications
     */

    public void setUserNotifications(Map<Subscriber, List<String>> userNotifications) {
        this.userNotifications = userNotifications;
    }

    /**
     *
     * @return
     */

    public AlertSystem getAlertSystem() {
        return alertSystem;
    }

    /**
     *
     * @param alertSystem
     */

    public void setAlertSystem(AlertSystem alertSystem) {
        this.alertSystem = alertSystem;
    }

    /**
     *
     * @return
     */

    public RecommendationSystem getRecommendationSystem() {
        return recommendationSystem;
    }

    /**
     *
     * @param recommendationSystem
     */

    public void setRecommendationSystem(RecommendationSystem recommendationSystem) {
        this.recommendationSystem = recommendationSystem;
    }

    /**
     *
     * @return
     */

    public List<Guest> getOnlineGuests() {
        return onlineGuests;
    }

    /**
     *
     * @param onlineGuests
     */

    public void setOnlineGuests(List<Guest> onlineGuests) {
        this.onlineGuests = onlineGuests;
    }

    /**
     *
     * @return
     */

    public List<Subscriber> getSystemSubscribers() {
        return systemSubscribers;
    }

    /**
     *
     * @param systemSubscribers
     */

    public void setSystemSubscribers(List<Subscriber> systemSubscribers) {
        this.systemSubscribers = systemSubscribers;
    }

    /**
     *
     * @return
     */

    public List<Admin> getAdmins() {
        return admins;
    }

    /**
     *
     * @param admins
     */

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    /**
     *
     * @return
     */
    public LoggingSystem getLoggingSystem() {
        return loggingSystem;
    }

    /**
     *
     * @param loggingSystem
     */
    public void setLoggingSystem(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    /**
     *
     * @return
     */

    public List<League> getLeagues() {
        return leagues;
    }

    /**
     *
     * @param leagues
     */

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

}
