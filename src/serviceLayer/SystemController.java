package serviceLayer;

import businessLayer.Team.Team;
import businessLayer.Tournament.League;
import businessLayer.Tournament.Match.Stadium;
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
    private HashMap<String, LinkedList<String>> unconfirmedTeams;
    private HashMap<String, Stadium> stadiums;


    private SystemController() {
        this.teams = new HashMap<>();
        systemSubscribers = new HashMap<>();
        this.systemComplaints = new HashMap<>();
        userNotifications = new HashMap<>();
        systemComplaints = new HashMap<>();
        unconfirmedTeams = new HashMap<>();
        stadiums = new HashMap<>();
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

    //-------------------TeamOwner--------------------//

    /**
     * the function takes a request for opening a new team and puts it in the data structure
     *
     * @param details of the new team
     */
    public boolean addToTeamConfirmList(LinkedList<String> details, Subscriber subscriber) {
        if (subscriber instanceof TeamOwner) {
            unconfirmedTeams.put(details.getFirst(), details);
            return true;
        }
        return false;
    }

    //-------------------TeamOwner--------------------//

    /**
     * the function approves the request by the AR and updates the new team in the system and in the team owner
     *
     * @param teamName   the name of the team
     * @param subscriber the subscriber who tries to confirm the request
     * @return true if it done successfully
     */
    public boolean confirmTeamByAssociationRepresntative(String teamName, Subscriber subscriber) {
        if (subscriber instanceof AssociationRepresentative) {
            if (unconfirmedTeams.containsKey(teamName)) {
                //check that a team with a same name doesn't exist
                if (!teams.containsKey(teamName)) {
                    LinkedList<String> request = unconfirmedTeams.get(teamName);
                    //checks that the user who wrote the request exists
                    if (systemSubscribers.containsKey(request.get(2))) {
                        Subscriber teamOwner = systemSubscribers.get(request.get(2));
                        //checks that the user is a team owner
                        if (teamOwner instanceof TeamOwner) {
                            int year = Integer.parseInt(request.get(1));
                            Team team = new Team(teamName, (TeamOwner) teamOwner, year);
                            teams.put(teamName, team);
                            unconfirmedTeams.remove(teamName);
                            ((TeamOwner) teamOwner).getTeams().add(team);
                            //updates the structure of the updated subscriber with the team
                            systemSubscribers.remove(teamOwner.getUsername());
                            systemSubscribers.put(teamOwner.getUsername(), teamOwner);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This function will create variables for the user to enter for the login procedure, and will send them (via enterUserDetails(userNameInput, passwordInput) to be filled by the guest in the UI/GUI.
     *
     * @param guest The guest which started the login procedure.
     * @return the instance of Subscriber from systemSubscribers, if the login details were correct.
     * NULL if the login form wasn't filled properly, or one of the user details wasn't correct.
     */
    public Subscriber createLoginForm(Guest guest) {
        String userNameInput = null;
        String passwordInput = null;
        guest.enterUserDetails(userNameInput, passwordInput);
        if (userNameInput == null || userNameInput.length() == 0 || passwordInput == null || passwordInput.length() == 0) {
            System.out.println("Not all fields were filled by the user.");
            return null;
        }
        if (systemSubscribers.containsKey(userNameInput)) {
            if (systemSubscribers.get(userNameInput).getPassword().equals(passwordInput)) {
                return systemSubscribers.get(userNameInput);
            }
        }
        System.out.println("Wrong Password or User Name.");
        return null;
    }

    /**
     * @param userName the user name that the user searches it's user instance
     * @return the user's instance with the user name, if existed in the system
     * NULL if there is no user in the system with the input user name
     */
    public Subscriber getSubscriberByUserName(String userName) {
        if (getSystemSubscribers().containsKey(userName)) {
            return getSystemSubscribers().get(userName);
        }
        return null;
    }

    /**
     * @param teamName the team name that the user searches it's team instance
     * @return the team's instance with the team name, if existed in the system
     * *      NULL if there is no team in the system with the input team name
     */
    public Team getTeamByName(String teamName) {
        if (teams.containsKey(teamName)) {
            return teams.get(teamName);
        }
        return null;
    }

    public HashMap<String, Stadium> getStadiums() {
        return stadiums;
    }

    public void setStadiums(HashMap<String, Stadium> stadiums) {
        this.stadiums = stadiums;
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    public Subscriber createRegistrationForm(Guest guest) {
        String userNameInput = null;
        String passwordInput = null;
        guest.enterUserDetails(userNameInput, passwordInput);
        if (userNameInput == null || userNameInput.length() == 0 || passwordInput == null || passwordInput.length() == 0) {
            //System.out.println("Not all fields were filled by the user.");
            return null;
        }
        if (checkPasswordStrength(passwordInput, userNameInput) == false) {
            return null;
        }

        String firstName = null;
        String lastName = null;

        while (!(firstName.matches("^[a-zA-Z]+$")) || !(lastName.matches("^[a-zA-Z]+$"))) //check if only letters
            guest.enterUserRealName(firstName, lastName);

        Subscriber newFan = new Fan(userNameInput, passwordInput, firstName + " " + lastName, this);
        getSystemSubscribers().put(userNameInput, newFan);

        return newFan;
    }

    /**
     * this function find the player according to is user name and return it if the player exist in the system
     * @param username the user name of the player
     * @return the player
     */
    //todo add to player method isAssociated()
    public Player findPlayer(String username) {
        Subscriber sub = systemSubscribers.get(username);
        if(sub instanceof Player){
            Player p = (Player) sub;
            //if(p.isAssociated())
            return p;
        } else{
            return null;
        }
    }

    /**
     * this function find the TeamManager according to is user name and return it if the TeamManager exist in the system
     * @param assetUserName the user name of the TeamManager
     * @return the TeamManager
     */
    public TeamManager findTeamManager(String assetUserName) {
        Subscriber sub = systemSubscribers.get(assetUserName);
        if(sub instanceof TeamManager){
            TeamManager teamM = (TeamManager) sub;
            //if(p.isAssociated())
            return teamM;
        } else{
            return null;
        }
    }

    /**
     * this function find the Coach according to is user name and return it if the Coach exist in the system
     * @param assetUserName the user name of the Coach
     * @return the Coach
     */
    public Coach findCoach(String assetUserName) {
        Subscriber sub = systemSubscribers.get(assetUserName);
        if (sub instanceof Coach) {
            Coach coach = (Coach) sub;
            return coach;
        } else {
            return null;
        }
    }

    public Stadium findStadium(String assetUserName) {
        if(stadiums.containsKey(assetUserName)){
            return stadiums.get(assetUserName);
        }
        return null;
    }
    public void addStadium(Stadium stadium){
        if(stadium!=null){
            stadiums.put(stadium.getName(),stadium);
        }
    }
}
