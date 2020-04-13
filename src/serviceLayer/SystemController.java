package serviceLayer;

import businessLayer.Team.Team;
import businessLayer.Tournament.League;
import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.alertSystem.*;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.Utilities.recommendationSystem.RecommendationSystem;
import businessLayer.userTypes.*;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.viewers.*;

import java.util.*;

public class SystemController {

    private static SystemController single_instance = null; //singleton instance

    private Map<Subscriber, List<String>> userNotifications;
    private AlertSystem alertSystem;
    private RecommendationSystem recommendationSystem;
    private List<Guest> onlineGuests;
    private HashMap<String, Subscriber> systemSubscribers; //name of the username, subscriber
    private List<Admin> admins; //todo check why we need this field tomer
    private LoggingSystem loggingSystem;
    private List<League> leagues;
    private HashMap<String, Team> teams; //name of the team, the team object
    private HashMap<Integer, Complaint> systemComplaints; //complaint id, complaint object
    private Admin temporaryAdmin; //instance of the temporary admin, which is initializing the system
    private LeagueController leagueController;

    private SystemController() {
        this.teams = new HashMap<>();
        systemSubscribers = new HashMap<>();
        this.systemComplaints = new HashMap<>();
        userNotifications = new HashMap<>();
        systemComplaints = new HashMap<>();
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
            temporaryAdmin = new Admin(userName, password, "tempAdmin", this);
            //System.out.println("The temporary admin has been created successfully.");
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
            //System.out.println("The password must be 6 to 32 characters long.");
            return false;
        }
        if (!password.matches(".*\\d.*") || !password.matches(".*[a-z].*")) {
            //System.out.println("The password must contain a mix of letters, numbers, and/or special characters. Passwords containing only letters or only numbers are not accepted.");
            return false;
        }
        if (password.contains("'") || password.contains("\"") || password.contains("&") || password.contains(" ")) {
            //System.out.println("Single quotes, double quotes, ampersands ( ‘  \"  & ), and spaces are not allowed.");
            return false;
        }
        if (password.contains(userName)) {
            //System.out.println("The password cannot be the same as your User Name name and should not contain any part of your user name.");
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
     * @param userNotifications
     */

    public void setUserNotifications(Map<Subscriber, List<String>> userNotifications) {
        this.userNotifications = userNotifications;
    }

    /**
     * @return
     */

    public AlertSystem getAlertSystem() {
        return alertSystem;
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

    public RecommendationSystem getRecommendationSystem() {
        return recommendationSystem;
    }

    /**
     * @param recommendationSystem
     */

    public void setRecommendationSystem(RecommendationSystem recommendationSystem) {
        this.recommendationSystem = recommendationSystem;
    }

    /**
     * @return
     */

    public List<Guest> getOnlineGuests() {
        return onlineGuests;
    }

    /**
     * @param onlineGuests
     */

    public void setOnlineGuests(List<Guest> onlineGuests) {
        this.onlineGuests = onlineGuests;
    }

    /**
     * @return
     */

    public HashMap<String, Subscriber> getSystemSubscribers() {
        return systemSubscribers;
    }

    /**
     * @param systemSubscribers
     */

    public void setSystemSubscribers(HashMap<String, Subscriber> systemSubscribers) {
        this.systemSubscribers = systemSubscribers;
    }

    /**
     * @return
     */

    public List<Admin> getAdmins() {
        return admins;
    }

    /**
     * @param admins
     */

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    /**
     * @return
     */
    public LoggingSystem getLoggingSystem() {
        return loggingSystem;
    }

    /**
     * @param loggingSystem
     */
    public void setLoggingSystem(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    /**
     * @return
     */

    public List<League> getLeagues() {
        return leagues;
    }

    /**
     * @param leagues
     */

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    /**
     * getter of system complaints
     *
     * @return the system complaints
     */
    public HashMap<Integer, Complaint> getSystemComplaints() {
        return systemComplaints;
    }

    //-------------------TEAM--------------------//

    /**
     * the function gets a team and put it into the data structure that holds all of the teams in the system
     *
     * @param team the team we want to add into the system
     */
    public void addTeam(Team team) {
        if (team != null) {
            if (!teams.containsKey(team)) {
                teams.put(team.getTeamName(), team);
            }
        }
    }


    /**
     * the function closes a team Permanently by the admin
     *
     * @param teamName the team the user wants to close
     * @param userType the user type of the user that requested to close the team
     * @return true if the status was changed to close
     * UC 8.1
     */
    public boolean closeTeamByAdmin(String teamName, Subscriber userType) {
        if (userType instanceof Admin) {
            if (teams.containsKey(teamName)) {
                Team chosenTeam = teams.get(teamName);
                //checks what is the status of the team
                if (chosenTeam.closeTeamPermanently()) {
                    teams.replace(teamName, chosenTeam);
                    return true;
                }
                //team is already closed by admin
                else {
                    return false;
                }
            }
            //team doesn't exist
            else {
                return false;
            }
        }
        //not an admin
        return false;
    }

    //-------------------Subscriber--------------------//

    /**
     * the function lets the subscriber to upload a complaint
     *
     * @param content    the content of the complaint
     * @param subscriber the subscriber who wants to complain
     *                   UC 3.4
     */
    public void addComplaint(String content, Subscriber subscriber) {
        Complaint complaint = subscriber.createComplaint(content);
        if (complaint != null) {
            complaint.setId(systemComplaints.size());
            systemComplaints.put(systemComplaints.size(), complaint);
            subscriber.addComplaint(complaint);
        }
    }

    //-------------------Admin--------------------//

    /**
     * the function removes a user from the system by the admin
     *
     * @param subscriberName the name of the user we want to delete
     * @param userType       the type of the user that tries to delete
     * @return a string that explains what was the result
     * 8.2
     */

    public String removeSubscriber(String subscriberName, Subscriber userType) {
        if (subscriberName != null && (userType instanceof Admin)) {
            if (systemSubscribers.containsKey(subscriberName)) {
                Subscriber tempSubscriber = systemSubscribers.get(subscriberName);
                if (tempSubscriber instanceof Admin) {
                    if (userType.getUsername().equals(subscriberName)) {
                        return "Admin can't remove his own user";
                    }
                } else if (tempSubscriber instanceof TeamOwner) {
                    if (((TeamOwner) tempSubscriber).isExclusiveTeamOwner()) {
                        return "Can't remove an exclusive team owner";
                    }
                }
                systemSubscribers.remove(subscriberName);
                //remove from notifications
                if (userNotifications.containsKey(tempSubscriber)) {
                    userNotifications.remove(tempSubscriber);
                }
                return "The User " + subscriberName + " was removed";
            }
        }
        return "User doesn't exist in the system";
    }

    /**
     * the function displays the complaints in the system to the admin
     *
     * @param subscriber the user who wants to see the complaints
     * @return the complaints in the system
     * UC 8.3.1
     */
    public HashMap<Integer, Complaint> displayComplaints(Subscriber subscriber) {
        if (subscriber instanceof Admin) {
            return systemComplaints;
        } else {
            return null;
        }
    }

    /**
     * the function lets the admin to respond the the comments in the system
     *
     * @param complaintID the complain's id the admin wants to respond to
     * @param subscriber  the user that wants to respond - has to be an admin
     * @param comment     - the comment of the admin
     * @return true is he responded successfully
     * UC 8.3.2
     */
    public boolean replyComplaints(int complaintID, Subscriber subscriber, String comment) {
        if (subscriber instanceof Admin && !comment.isEmpty()) {
            if (systemComplaints.containsKey(complaintID)) {
                Complaint complaint = systemComplaints.get(complaintID);
                //Complaint editedComplaint = ((Admin) subscriber).replyComplaints(complaint,comment);
                complaint.setAnswered(true);
                complaint.setComment(comment);
                complaint.setHandler(subscriber.getUsername());
                systemComplaints.remove(complaintID);
                systemComplaints.put(complaintID, complaint);
                return true;
            }
        }
        return false;
    }

    /**
     * The function adds a referee to the system and returns whether the referee was successfully added or not
     *
     * @param username
     * @param password
     * @param name
     * @param training
     * @param representative
     * @return true/false
     */
    public boolean addReferee(String username, String password, String name, String training, Subscriber representative) {

        if (username == null || password == null || name == null || training == null || representative == null) {
            return false;
        }
        if (!(representative instanceof AssociationRepresentative)) {
            return false;
        }
        if (systemSubscribers.containsKey(username)) {
            return false;
        }

        Referee newRef = new Referee(username, password, name, training, leagueController, this);
        systemSubscribers.put(username, newRef);
        return true;
    }


    /**
     * The function removes referee from the system and returns whether the removal was successful or not
     * @param username
     * @return true/false
     */
    public boolean removeReferee(String username) {

        if (username == null) {
            return false;
        }
        if (!systemSubscribers.containsKey(username)) {
            return false;
        }
        Subscriber possibleRef = systemSubscribers.get(username);
        if (!(possibleRef instanceof Referee)) {
            return false;
        }

        leagueController.removeReferee(possibleRef);
        Referee ref = (Referee)possibleRef;
        ref.removeFromAllMatches();
        systemSubscribers.remove(username);
        return true;
    }
}
