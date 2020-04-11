package serviceLayer;

import businessLayer.Tournament.League;
import businessLayer.Utilities.alertSystem.*;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.Utilities.recommendationSystem.RecommendationSystem;
import businessLayer.userTypes.*;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.viewers.*;

import java.util.*;

public class SystemController {

    private static SystemController single_instance = null; //singleton instance

    private Map<Subscriber, List<String>> userNotifications; //map of subscribers against thier notification list (strings)
    private AlertSystem alertSystem; //instance of the alert system
    private RecommendationSystem recommendationSystem; //instance of the recommendation system
    private List<Guest> onlineGuests; //list of the current online guests
    private List<Subscriber> systemSubscribers; //list of the overall system subscribers
    private List<Admin> admins; //list of the overall admins in the system
    private LoggingSystem loggingSystem; //instance of the logging system
    private List<League> leagues; //list of the overall leagues in the system
    private Admin temporaryAdmin; //instance of the temporary admin, which is initializing the system

    private SystemController() {

    }

    /**
     * @return single instance of System control for Singleton purposes.
     */
    public static SystemController SystemController() {
        if (single_instance == null) {
            single_instance = new SystemController();
        }
        return single_instance;
    }

    /**
     * @param subscriber
     * @param notification
     * @return
     */
    public boolean sendNotification(Subscriber subscriber, String notification) {

        return true;
    }

    /**
     * @param logs
     * @return
     */

    public boolean sendLogs(List<String> logs) {

        return true;
    }

    /**
     * @param followers
     * @param Alerts
     * @return
     */
    public boolean sendAlert(List<Subscriber> followers, List<String> Alerts) {

        return true;
    }

    /**
     * @param userName The user name of the default temporary admin, as mentioned in the Readme file.
     * @param password The password of the default temporary admin, as mentioned in the Readme file.
     * @return true: if the temporary admin user was created successfully by the system. | false: The userName or password didn't match to the default temporary admin details.
     */
    public Boolean insertInfo(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            temporaryAdmin = new Admin(userName, password, "tempAdmin");
            System.out.println("The temporary admin has been created successfully.");
            return true;
        }
        return false;
    }

    /**
     * @param password temporary admin password.
     * @return true if the temporary admin entered the sufficient password to initialize the system.
     * false else.
     */
    public Boolean initializeSystem(String password) {
        if (password.equals("admin")) {
            return true;
        }
        return false;
    }

    /**
     * @param newPassword the user's new password.
     * @param userName    the user name.
     * @return true if the password has changed.
     * false else.
     */
    public Boolean changePassword(String newPassword, String userName) {
        if (checkPasswordStrength(newPassword, userName) == false)
            return false;
        temporaryAdmin.setPassword(newPassword);
        return true;
    }

    /**
     * @param password the password from the user's input
     * @param userName the userName from the user's input (to check that the password does not contain the user name)
     * @return true if:
     * * The password  is 6 to 32 characters long.
     * * The password contains a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.
     * * The password is case-sensitive.
     * * Password doesn't contain single quotes, double quotes, ampersands ( ‘  \"  & ). Spaces are not allowed either.
     * * The password doesn't contain the user name.
     * false else.
     */
    public boolean checkPasswordStrength(String password, String userName) {
        if (password.length() < 6 || password.length() > 32) {
            System.out.println("The password must be 6 to 32 characters long.");
            return false;
        }
        if (!password.matches(".*\\d.*") || !password.matches(".*[a-z].*")) {
            System.out.println("The password must contain a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.");
            return false;
        }
        if (password.contains("'") || password.contains("\"") || password.contains("&") || password.contains(" ")) {
            System.out.println("Single quotes, double quotes, ampersands ( ‘  \"  & ), and spaces are not allowed.");
            return false;
        }
        if (password.contains(userName)) {
            System.out.println("The password cannot be the same as your User Name name and should not contain any part of your user name.");
            return false;
        }
        return true;
    }

    /**
     * @return map of subscribers against notification
     */
    public Map<Subscriber, String> getUserNotifications() {
        return null;
    }

    /**
     * @param userNotifications //new map of subscribers against the notifications
     */

    public void setUserNotifications(Map<Subscriber, List<String>> userNotifications) {
        this.userNotifications = userNotifications;
    }

    /**
     * @return alert system instance
     */

    public AlertSystem getAlertSystem() {
        return alertSystem;
    }

    /**
     * @param alertSystem new system alert instance
     */

    public void setAlertSystem(AlertSystem alertSystem) {
        this.alertSystem = alertSystem;
    }

    /**
     * @return recommended system instance
     */

    public RecommendationSystem getRecommendationSystem() {
        return recommendationSystem;
    }

    /**
     * @param recommendationSystem new recommended system instance
     */
    public void setRecommendationSystem(RecommendationSystem recommendationSystem) {
        this.recommendationSystem = recommendationSystem;
    }

    /**
     * @return list of the current online guests
     */
    public List<Guest> getOnlineGuests() {
        return onlineGuests;
    }

    /**
     * @param onlineGuests new list of current online guests
     */

    public void setOnlineGuests(List<Guest> onlineGuests) {
        this.onlineGuests = onlineGuests;
    }

    /**
     * @returnlist list of the overall system subscribers
     */

    public List<Subscriber> getSystemSubscribers() {
        return systemSubscribers;
    }

    /**
     * @param systemSubscribers new list of the overall system subscribers
     */

    public void setSystemSubscribers(List<Subscriber> systemSubscribers) {
        this.systemSubscribers = systemSubscribers;
    }

    /**
     * @return list of the overall admins in the system
     */

    public List<Admin> getAdmins() {
        return admins;
    }

    /**
     * @param admins new list of the overall admins in the system
     */

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    /**
     * @return logging system instance
     */
    public LoggingSystem getLoggingSystem() {
        return loggingSystem;
    }

    /**
     * @param loggingSystem new logging system instance
     */
    public void setLoggingSystem(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    /**
     * @return list of the overall leagues in the system
     */

    public List<League> getLeagues() {
        return leagues;
    }

    /**
     * @param leagues new list of the overall leagues in the system
     */

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

}
