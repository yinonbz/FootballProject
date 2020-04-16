package businessLayer.userTypes;

import businessLayer.Team.Team;
import businessLayer.Team.TeamController;
import businessLayer.Tournament.League;
import businessLayer.Tournament.LeagueController;
import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.alertSystem.*;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.Utilities.recommendationSystem.RecommendationSystem;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.viewers.*;
import dataLayer.DemoDB;
import businessLayer.Tournament.Match.Match;

import java.util.*;

public class SystemController {

    private static SystemController single_instance = null; //singleton instance
    private DemoDB DB; //this will be our DB until the next iteration
    private AlertSystem alertSystem;
    private RecommendationSystem recommendationSystem;
    private LoggingSystem loggingSystem;
    private Admin temporaryAdmin; //instance of the temporary admin, which is initializing the system
    private LeagueController leagueController;
    private TeamController teamController;
    private MatchController matchController;

    //----------------OLD DATA STRUCTURES THAT ARE LOCATED IN THE DB-----------------------//
    //private HashMap<String, Team> teams; //name of the team, the team object
    //private HashMap<String, Subscriber> systemSubscribers; //name of the username, subscriber
    //private Map<Subscriber, List<String>> userNotifications;
    //private HashMap<Integer, Complaint> systemComplaints; //complaint id, complaint object
    //private HashMap<String, LinkedList<String>> unconfirmedTeams;
    //private HashMap<String, Stadium> stadiums;


    //----------------OLD DATA STRUCTURES THAT ARE NOT USED-------------------------------//

    //private List<Guest> onlineGuests;
    //private List<Admin> admins;
    //private List<League> leagues;


    private SystemController() {
         DB = new DemoDB();
    }

    public void setLeagueController(LeagueController leagueController) {
        this.leagueController = leagueController;
    }

    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
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
     * this function connects to the DB
     * @param DB
     * @return
     */
    public boolean connectToDB(DemoDB DB){
        this.DB=DB;
        return true;
    }


    /**
     * Getter function for the league controller
     * @return
     */
    public LeagueController getLeagueController() {
        return leagueController;
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
     *
     * @return
     */
    public TeamController getTeamController() {
        return teamController;
    }

    /**
     *
     * @param teamController
     */
    public void setTeamController(TeamController teamController) {
        this.teamController = teamController;
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
    /*
    public void setUserNotifications(Map<Subscriber, List<String>> userNotifications) {
        this.userNotifications = userNotifications;
    }
    */
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

    /*
    public List<Guest> getOnlineGuests() {
        return onlineGuests;
    }
    */
    /**
     * @param onlineGuests
     */

    /*
    public void setOnlineGuests(List<Guest> onlineGuests) {
        this.onlineGuests = onlineGuests;
    }
    /*
    /**
     * @return
     */

    /*
    public HashMap<String, Subscriber> getSystemSubscribers() {
        return systemSubscribers;
    }
    */


    /**
     * @param systemSubscribers
     */
    /*
    public void setSystemSubscribers(HashMap<String, Subscriber> systemSubscribers) {
        this.systemSubscribers = systemSubscribers;
    }
    */

    /**
     * @return
     */
    /*
    public List<Admin> getAdmins() {
        return admins;
    }
    /*
    /**
     * @param admins
     */
    /*
    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
    */
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
    /*
    public List<League> getLeagues() {
        return leagues;
    }
    */
    /**
     * @param leagues
     */
    /*
    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
    */
    /**
     * getter of system complaints
     *
     * @return the system complaints
     */
    /*
    public HashMap<Integer, Complaint> getSystemComplaints() {
        return systemComplaints;
    }
    */

    //-------------------TEAM--------------------//

    /**
     * the function gets a team and put it into the data structure that holds all of the teams in the system
     *
     * @param team the team we want to add into the system
     */
    public void addTeam(Team team) {
        if (team != null) {
            if (!DB.containsInTeamsDB(team.getTeamName())) {
                DB.addTeamToDB(team.getTeamName(), team);
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
    public boolean closeTeamByAdmin(String teamName, String userType) {
        Subscriber subscriber = getSubscriberByUserName(userType);
        if (subscriber instanceof Admin) {
            if (DB.containsInTeamsDB(teamName)) {
                Team chosenTeam = DB.selectTeamFromDB(teamName);
                //checks what is the status of the team
                if (chosenTeam.closeTeamPermanently()) {
                    DB.addTeamToDB(teamName, chosenTeam);
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
     *  @param content    the content of the complaint
     * @param username the subscriber who wants to complain
     */
    public void addComplaint(String content, String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if(subscriber!=null){
            Complaint complaint = subscriber.createComplaint(content);
            if (complaint != null) {
                int id = DB.countComplaintsInDB();
            complaint.setId(id);
                DB.addComplaintToDB(id, complaint);
                subscriber.addComplaint(complaint);
            }
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

    public String removeSubscriber(String subscriberName, String userType) {
        Subscriber subscriber = getSubscriberByUserName(userType);
        if (subscriberName != null && (subscriber instanceof Admin)) {
            if (DB.containsInSystemSubscribers(subscriberName)) {
                Subscriber tempSubscriber = DB.selectSubscriberFromDB(subscriberName);
                if (tempSubscriber instanceof Admin) {
                    if (subscriber.getUsername().equals(subscriberName)) {
                        return "Admin can't remove his own user";
                    }
                } else if (tempSubscriber instanceof TeamOwner) {
                    if (((TeamOwner) tempSubscriber).isExclusiveTeamOwner()) {
                        return "Can't remove an exclusive team owner";
                    }
                }
                DB.removeSubscriberFromDB(subscriberName);
                //remove from notifications
                if (DB.containsInNotificationDB(tempSubscriber.getUsername())) {
                    DB.removeNotificationFromDB(tempSubscriber.getUsername());
                }
                return "The User " + subscriberName + " was removed";
            }
        }
        return "User doesn't exist in the system";
    }

    /**
     * the function displays the complaints in the system to the admin
     *
     * @param username the user who wants to see the complaints
     * @return the complaints in the system
     * UC 8.3.1
     */
    public HashMap<Integer, Complaint> displayComplaints(String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Admin) {
            return DB.selectAllComplaints();
        } else {
            return null;
        }
    }

    /**
     * the function lets the admin to respond the the comments in the system
     *
     * @param complaintID the complain's id the admin wants to respond to
     * @param username  the user that wants to respond - has to be an admin
     * @param comment     - the comment of the admin
     * @return true is he responded successfully
     * UC 8.3.2
     */
    public boolean replyComplaints(String complaintID, String username, String comment) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Admin && !comment.isEmpty()) {
                int compID = Integer.parseInt(complaintID);
            if (DB.containsInComplaintDB(compID)) {
                Complaint complaint = DB.selectComplaintFromDB(compID);
                //Complaint editedComplaint = ((Admin) subscriber).replyComplaints(complaint,comment);
                complaint.setAnswered(true);
                complaint.setComment(comment);
                complaint.setHandler(subscriber.getUsername());
                DB.removeComplaintFromDB(compID);
                DB.addComplaintToDB(compID, complaint);
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
     * @param representativeUser
     * @return true/false
     */
    public boolean addReferee(String username, String password, String name, String training, String representativeUser) {

        Subscriber representative = getSubscriberByUserName(representativeUser);
        if (username == null || password == null || name == null || training == null || representative == null) {
            return false;
        }
        if (!(representative instanceof AssociationRepresentative)) {
            return false;
        }
        if (DB.containsInSystemSubscribers(username)) {
            return false;
        }

        Referee newRef = new Referee(username, password, name, training, leagueController, this);
        DB.addSubscriberToDB(username, newRef);
        leagueController.addRefereeToDataFromSystemController(newRef);
        return true;
    }


    /**
     * The function removes referee from the system and returns whether the removal was successful or not
     *
     * @param username
     * @return true/false
     */
    public boolean removeReferee(String username) {

        if (username == null) {
            return false;
        }
        if (!DB.containsInSystemSubscribers(username)) {
            return false;
        }
        Subscriber possibleRef = DB.selectSubscriberFromDB(username);
        if (!(possibleRef instanceof Referee)) {
            return false;
        }

        leagueController.removeReferee(possibleRef);
        Referee ref = (Referee) possibleRef;
        ref.removeFromAllMatches();
        DB.removeSubscriberFromDB(username);
        return true;
    }

    //-------------------TeamOwner--------------------//

    /**
     * the function takes a request for opening a new team and puts it in the data structure
     *
     * @param details of the new team
     * @param username
     */
    public boolean addToTeamConfirmList(LinkedList<String> details, String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof TeamOwner) {
            DB.addUnconfirmedTeamsToDB(details.getFirst(), details);
            return true;
        }
        return false;
    }

    /**
     * the function checks if the referee exists in the system
     * @param username
     * @return
     */
    public boolean containsReferee(String username){
        Subscriber subscriber = getSubscriberByUserName(username);
        if(subscriber instanceof Referee){
            return true;
        }
        return false;
    }

    /**
     * a functions that returns the referee from the DB
     * @param username
     * @return
     */
    public Referee getRefereeFromDB(String username){
        Subscriber subscriber = getSubscriberByUserName(username);
        if(subscriber instanceof Referee){
            return (Referee)subscriber;
        }
        return null;
    }

    /**
     * checks if the Association Representative exists in the DB
     * @param username
     * @return
     */
    public boolean containsInSystemAssociationRepresentative(String username){
        Subscriber subscriber = DB.selectSubscriberFromDB(username);
        if(subscriber instanceof AssociationRepresentative){
            return true;
        }
        return false;
    }

    //-------------------TeamOwner--------------------//

    /**
     * the function approves the request by the AR and updates the new team in the system and in the team owner
     *
     * @param teamName   the name of the team
     * @param username the subscriber who tries to confirm the request
     * @return true if it done successfully
     */
    public boolean confirmTeamByAssociationRepresntative(String teamName, String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof AssociationRepresentative) {
            if (DB.containsInUnconfirmedTeams(teamName)) {
                //check that a team with a same name doesn't exist
                if (!DB.containsInTeamsDB(teamName)) {
                    LinkedList<String> request = DB.selectUnconfirmedTeamsFromDB(teamName);
                    //checks that the user who wrote the request exists
                    if (DB.containsInSystemSubscribers(request.get(2))) {
                        Subscriber teamOwner = DB.selectSubscriberFromDB(request.get(2));
                        //checks that the user is a team owner
                        if (teamOwner instanceof TeamOwner) {
                            int year = Integer.parseInt(request.get(1));
                            Team team = new Team(teamName, (TeamOwner) teamOwner, year);
                            DB.addTeamToDB(teamName, team);
                            DB.removeSubscriberFromDB(teamName);
                            ((TeamOwner) teamOwner).getTeams().add(team);
                            //updates the structure of the updated subscriber with the team
                            DB.removeSubscriberFromDB(teamOwner.getUsername());
                            DB.addSubscriberToDB(teamOwner.getUsername(), teamOwner);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * the function checks if a player exists in the DB
     * @param playerName
     * @return
     */
    public boolean checkUserExists(String playerName){
        return DB.containsInSystemSubscribers(playerName);
    }

    /**
     * brings back a subscriber from the data base if he exists in the system
     * @param username
     * @return
     */
    public Subscriber selectUserFromDB(String username){
        if(checkUserExists(username)){
            return DB.selectSubscriberFromDB(username);
        }
        return null;
    }

    /**
     * add a subscriber to the DB
     * @param username
     * @param subscriber
     * @return
     */
    public boolean addSubscriberToDB (String username, Subscriber subscriber){
        return DB.addSubscriberToDB(username,subscriber);
    }


    /**
     * this function find the player according to is user name and return it if the player exist in the system
     * @param username the user name of the player
     * @return the player
     */
    public Player findPlayer(String username) {
        Subscriber sub = DB.selectSubscriberFromDB(username);
        if(sub instanceof Player){
            Player p = (Player) sub;
            //if(p.isAssociated())
            return p;
        } else{
            return null;
        }
    }

    /**
     * the function checks if the DB contains the league
     * @param leagueID
     * @return
     */
    public boolean containsLeague(String leagueID){
        return DB.containsInSystemLeague(leagueID);
    }

    /**
     * the function returns the league value from DB
     * @param leagueID
     * @return
     */
    public League getLeagueFromDB(String leagueID){
        return DB.selectLeagueFromDB(leagueID);
    }

    /**
     * add new league to the DB
     * @param leagueID
     * @param league
     * @return
     */
    public boolean addLeagueToDB(String leagueID, League league){
        return DB.addLeagueToDB(leagueID,league);
    }



    /**
     * this function find the TeamManager according to is user name and return it if the TeamManager exist in the system
     * @param assetUserName the user name of the TeamManager
     * @return the TeamManager
     */
    public TeamManager findTeamManager(String assetUserName) {
        Subscriber sub = DB.selectSubscriberFromDB(assetUserName);
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
        Subscriber sub = DB.selectSubscriberFromDB(assetUserName);
        if (sub instanceof Coach) {
            Coach coach = (Coach) sub;
            return coach;
        } else {
            return null;
        }
    }

    public Stadium findStadium(String assetUserName) {
        if(DB.containsInSystemStadium(assetUserName)){
            return DB.selectStadiumFromDB(assetUserName);
        }
        return null;
    }

    /**
     * return a default stadium to the matches policies
     * @return
     */
    public Stadium findDefaultStadium(){
        return DB.selectRandomStadium();

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
        if (DB.containsInSystemSubscribers(userNameInput)) {
            if (DB.selectSubscriberFromDB(userNameInput).getPassword().equals(passwordInput)) {
                return DB.selectSubscriberFromDB(userNameInput);
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
        if (DB.containsInSystemSubscribers(userName)) {
            return DB.selectSubscriberFromDB(userName);
        }
        return null;
    }

    /**
     * @param teamName the team name that the user searches it's team instance
     * @return the team's instance with the team name, if existed in the system
     * *      NULL if there is no team in the system with the input team name
     */
    public Team getTeamByName(String teamName) {
        if (DB.containsInTeamsDB(teamName)) {
            return DB.selectTeamFromDB(teamName);
        }
        return null;
    }

    /*
    public HashMap<String, Team> getTeams() {
        return teams;
    }
    */

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
        DB.addSubscriberToDB(userNameInput, newFan);

        return newFan;
    }

    /**
     * FUNCTION OF IDO, MAYBE NEEDS TO BE REMOVED
     * @param stadium
     */
    public void addStadium(Stadium stadium){
        if(stadium!=null){
            DB.addStadiumToDB(stadium.getName(),stadium); //todo for IDO please check if you can use my function
        }
    }

    // -------------------AssociationRepresentative--------------------//

    /**
     * the function adds
     * @param nameStadium
     * @param numberOfSeats
     * @return
     */
    public boolean addNewStadium(String nameStadium, String numberOfSeats){
        if (!DB.containsInSystemStadium(nameStadium)){
            int numOfSeats = Integer.parseInt(numberOfSeats);
            Stadium stadium = new Stadium(nameStadium,numOfSeats);
            DB.addStadiumToDB(nameStadium,stadium);
            return true;
        }
        return false;
    }

    // -------------------Referee--------------------//

    /**
     * finds a match in the DB
      * @param matchID
     * @return
     */
    public Match findMatch(int matchID){
            return DB.selectMatchFromDB(matchID);
    }
}
