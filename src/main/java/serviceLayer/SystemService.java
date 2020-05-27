package serviceLayer;

import businessLayer.Team.Team;
import businessLayer.Exceptions.NotFoundInDbException;
import businessLayer.Team.TeamController;
import businessLayer.Tournament.LeagueController;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Administration.Player;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;
import presentationLayer.ControllerInterface;

import java.util.*;

public class SystemService extends Observable implements Observer {
    private SystemController systemController; //business layer system controller.
    private LeagueController leagueController;
    private TeamController teamController;
    private MatchController matchController;
    private ArrayList<String> onlineUsers;

    /**
     * initalizing system and controllers as singeltons
     */
    public SystemService() {
        onlineUsers = new ArrayList<>();
        this.systemController = SystemController.SystemController();
        systemController.addServiceObservers(this);
    }

    public void addObserverForService(ControllerInterface controller){
        addObserver(controller);
    }

    /**
     * this function gets request from admin to watch complaint from the presentation layer
     * and generate the arguments needed in the business layer to operate.
     * @param username
     * @return
     * UC 8.3.1
     */
    public HashMap<Integer, Complaint> displayComplaints(String username) {
        return systemController.displayComplaints(username);
    }

    public HashMap<String, Subscriber> displayAdminApprovalRequests(String username) {
        return systemController.displayAdminApprovalRequests(username);
    }


    /**
     * this function gets the arguments inserted to the presentation layer by the user in the initialization of system
     * @param userName The user name of the default temporary admin, as mentioned in the Readme file.
     * @param password The password of the default temporary admin, as mentioned in the Readme file.
     * @return true: if the temporary admin user was created successfully by the system. | false: The userName or password didn't match to the default temporary admin details.
     */
    public Boolean insertInfo(String userName, String password) {
        return systemController.insertInfo(userName, password);

    }

    /**
     * UC 1.1 - Initialize System
     * @param password the password of the temporary admin
     * @return true if the system has initialized successfully
     *          false else
     */
    public Boolean initializeSystem(String password) {
        return systemController.initializeSystem(password);
    }

    /**
     * @param newPassword The new password of the user
     * @param userName the user's user name
     * @return true if the passsword has been changed
     *          false else
     */
    public Boolean changePassword(String newPassword, String userName) {
        return systemController.changePassword(newPassword, userName);
    }

    /**
     * the function get closing team request from presentation layer and closes a team Permanently by the admin
     *
     * @param teamName the team the user wants to close
     * @param username the user type of the user that requested to close the team
     * @return true if the status was changed to close
     * UC 8.1
     */
    public boolean closeTeamByAdmin(String teamName, String username) {
        return systemController.closeTeamByAdmin(teamName, username);
    }



    /**
     * the function lets the subscriber to upload a complaint via the presentation layer, and execute the command.
     *  @param content    the content of the complaint
     * @param username the subscriber who wants to complain
     * UC 3.4
     */

    public boolean addComplaint(String content, String username) {
        return systemController.addComplaint(content, username);
    }


    /**
     * the functiong gets from the UI a request to removes a user from the system by the admin
     *and execute it in business layer
     * @param subscriberName the name of the user we want to delete
     * @param userType       the type of the user that tries to delete
     * @return a string that explains what was the result
     * 8.2
     */
    public String removeSubscriber(String subscriberName, String userType) {
        return systemController.removeSubscriber(subscriberName, userType);
    }

    /**
     * the function gets request to reply a complaint from presentation layer,
     * and lets the admin to respond to the comments in the system
     *
     * @param complaintID the complain's id the admin wants to respond to
     * @param username  the user that wants to respond - has to be an admin
     * @param comment     - the comment of the admin
     * @return true is he responded successfully
     * UC 8.3.2
     */
    public boolean replyComplaints(String complaintID, String username, String comment) {
        return systemController.replyComplaints(complaintID, username, comment);
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
        return systemController.addReferee(username,password,name,training,representativeUser);
    }

    /**
     * The function foward the arguments of removing a referee operation, from the UI of the admin to the business layer.
     *
     * @param username
     * @return true/false
     */
    public boolean removeReferee(String username) {
        return systemController.removeReferee(username);
    }

    /**
     * the function takes a request for opening a new team and puts it in the data structure
     * @param details of the new team
     *Not a UC - A function of TEAM OWNER
     */
    public boolean requestForNewTeam(LinkedList<String> details, String username) {
        return systemController.addToTeamConfirmList(details,username);
    }

    /**
     * the function approves the request by the AR and updates the new team in the system and in the team owner
     *
     * @param teamName   the name of the team
     * @param username the subscriber who tries to confirm the request
     * @return true if it done successfully
     * Not a UC - A function of Association Represntative
     */
    public boolean confirmTeamByAssociationRepresntative(String teamName, String username) {
        return systemController.confirmTeamByAssociationRepresentative(teamName,username);
    }


    /**
     * Login UC-2.3
     * @param userName the User Name as the user's input
     * @param password the Password as the user's input
     * @return the user type if the user logged in successfully
     *         null else
     */
    public String enterLoginDetails(String userName, String password){
        return systemController.enterLoginDetails(userName,password);
    }

    /**
     * Registration procedure for Admin
     * @param userName
     * @param password
     * @param name the real name of the new admin
     * @return true if the admin was successfully created in the DB
     */
    public boolean enterRegisterDetails_Admin(String userName, String password, String name){
        return systemController.enterRegisterDetails_Admin(userName,password,name);
    }

    /**
     * Registration procedure for Association Representative
     * @param userName
     * @param password
     * @param name the real name of the new admin
     * @return true if the Association Representative was successfully created in the DB
     *         false else
     */
    public boolean enterRegisterDetails_AssociationRepresentative(String userName, String password, String name){
        return systemController.enterRegisterDetails_AssociationRepresentative(userName,password,name);
    }


    /**
     * Registration procedure for Player
     * @param userName
     * @param password
     * @param name the real name of the new player
     * @param birthDate the date of birth of the player
     * @param fieldJob the field job of the new player
     * @param teamName the name of the team which the player plays in
     * @return true true if the Player was successfully created in the DB
     *         false else
     */
    public boolean enterRegisterDetails_Player(String userName, String password, String name, String birthDate, String fieldJob, String teamName){
        return systemController.enterRegisterDetails_Player(userName,password, name, birthDate,fieldJob,teamName);
    }

    /**
     * Registration procedure for Coach
     * @param userName
     * @param password
     * @param name the real name of the coach
     * @param training the training of the coach
     * @param teamJob the team job of the coach
     * @return true if the Coach was successfully created in the DB
     *         false else
     */
    public boolean enterRegisterDetails_Coach(String userName, String password, String name, String roleInTeam,String training, String teamJob){
        return systemController.enterRegisterDetails_Coach(userName,password,name,roleInTeam,training, teamJob);
    }

    /**
     * Registration procedure for Team Owner
     * @param userName
     * @param password
     * @param name the real name of the Team Owner
     * @return true if the Team Owner was successfully created in the DB
     *         false else
     */
    public boolean enterRegisterDetails_TeamOwner(String userName, String password, String name){
        return systemController.enterRegisterDetails_TeamOwner(userName,password,name);
    }

    /**
     * Registration procedure for Team Manager
     * @param userName
     * @param password
     * @param name the real name of the Team Owner
     * @param teamName the name of the team which the team-manager manages
     * @returntrue if the Team Manager was successfully created in the DB
     *            false else
     */
    public boolean enterRegisterDetails_TeamManager(String userName, String password, String name, String teamName){
        return systemController.enterRegisterDetails_TeamManager(userName,password,name,teamName);
    }


    /**
     * This function handles the operation of approving a new AR or Admin user by an already-approved admin.
     * @param userName the user name of the user which approves
     * @param userNameToApprove the user name of the user which is being approved
     * @param approve approve = true, disapprove = false
     * @return true if the userNameToApprove was approved/disapproved by userName
     *         false else
     */
    public boolean handleAdminApprovalRequest(String userName, String userNameToApprove, boolean approve){
        return systemController.handleAdminApprovalRequest(userName, userNameToApprove, approve);
    }

    public boolean sendRequestForTeam(String teamName, String establishedYear, String username){
        if(teamName!=null && establishedYear!=null && username!=null && !teamName.isEmpty()){
            return systemController.sendRequestForTeam(teamName, establishedYear, username);
        }
        return false;
    }

    /**
     * The function receives a user's username and a team's name and passes them for the
     * Systemcontroller to allow the fan to follow the taem's page
     *
     * @param username
     * @param teamName
     * @return
     */
    public boolean userRequestToFollowTeam(String username, String teamName) {

        if (username != null && teamName != null) {
            return systemController.allowUserToFollowTeam(username, teamName);
        }
        return false;
    }

    /**
     * The function receives a user's username and a player's name and passes them for the
     * Systemcontroller to allow the fan to follow the taem's page
     *
     * @param username
     * @param playerName
     * @return
     */
    public boolean userRequestToFollowPlayer(String username, String playerName) {

        if (username != null && playerName != null) {
            return systemController.allowUserToFollowPlayer(username, playerName);
        }
        return false;
    }

    /**
     * The function receives a user's username and a player's name and passes them to the
     * Systemcontroller to allow the fan to follow the team's page
     *
     * @param username
     * @param coachName
     * @return
     */
    public boolean userRequestToFollowCoach(String username, String coachName) {

        if (username != null && coachName != null) {
            return systemController.allowUserToFollowCoach(username, coachName);
        }
        return false;
    }

    public ArrayList<String> getAllMatchesInDB(){
        return systemController.getAllMatchesInDB();
    }

    /**
     * The function receives a user's username and a match's identifier and passes them to the
     * Systemcontroller to allow a user to follow the match
     *
     * @param username
     * @param matchID
     * @return
     */
    public boolean userRequestToFollowMatch(String username, String matchID) {

        if (username != null && matchID != null) {
            systemController.allowUserToFollowMatch(username, matchID);
        }
        return false;
    }

    /**
     * The function receives a team name and returns the matching team or teams with close names to the one given
     * @param teamName
     */
    /*
    public void findTeamWithName(String teamName) { //todo: fix the returning value once we decide how to present at GUI

        Team team = systemController.getTeamByName(teamName);
        if (team != null) {
            //here we return the user the team he was searching for
        } else {
            LinkedList<Team> similarTeamNames = systemController.getSimilarTeams(teamName);
            if (similarTeamNames.size() == 0) {
                //here we let the user know there were no teams with similar names to the one he entered
            } else {
                //here we return the names that were retrieved in a way to present at the GUI
            }
        }
    }
    */

    /**
     * The function receives a player name and returns the matching player or players with close names to the one given
     * @param playerName
     */
    /*
    public void findPlayerWithName(String playerName) { //todo: fix the returning value once we decide how to present at GUI

        Subscriber user = systemController.getSubscriberByUserName(playerName);
        if (user instanceof Player) {
            //here we return the user the player he was searching for
        } else {
            LinkedList<Player> similarPlayerNames = systemController.getSimilarPlayers(playerName);
            if (similarPlayerNames.size() == 0) {
                //here we let the user know there were no players with similar names to the one he entered
            } else {
                //here we return the names that were retrieved in a way to present at the GUI
            }
        }
    }
    */
    /**
     * @param o
     * @param arg the notifications
     *            this function updates the presentation layer for new notifications
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SystemController && arg instanceof LinkedList) {
            LinkedList<String> users = (LinkedList) arg;
            String title = users.removeLast(); //title for the message in the interface
            String event = users.removeLast(); //holds the message to present to the user's interface
            for (String user : users) {
                if (onlineUsers.contains(user)) {
                    LinkedList<String> notification = new LinkedList<>();
                    notification.add(title);
                    notification.add(event);
                    setChanged();
                    notifyObservers(notification);
                } else {
                    systemController.saveUserMessage(user, event, title);
                }
            }
        }
    }

    public ArrayList<String> getAllCoachesNames(){
        return systemController.getAllCoachesNames();
    }

    /**
     * @param userName the user's username to add to the online users in DB (when logging in)
     */
    public void addToUsersOnline(String userName) {
        if (!onlineUsers.contains(userName))
            onlineUsers.add(userName);
    }

    /**
     * remove user from the online users DB (when logging out)
     *
     * @param userName the user's username to remove form the online users
     */
    public void removeFromUsersOnline(String userName) {
        if (onlineUsers.contains(userName))
            onlineUsers.remove(userName);
    }

    public ArrayList<String> getAllPlayers(){
        return systemController.getAllPlayers();
    }

    /**
     * @return get all of the system subscribers (online AND offline)
     */
    public ArrayList<String> getSystemSubscribers() {
        //return systemController.getSystemSubscribers(); //todo need to check why compilation
        return null;
    }
    //todo ido add this function
    public void updatePlayerBDate(String date, String user){
        systemController.updatePlayerBDate(date,user);
    }
    //todo ido add this function
    public void updatePlayerName(String name, String userName) {
        systemController.updatePlayerName(name,userName);
    }
    //todo ido add this function
    public void updatePlayerPost(String userName, String post) {
        systemController.updatePlayerPost(userName,post);

    }
    //todo ido add this function
    public void updateCoachName(String name, String userName) {
        systemController.updateCoachName(name,userName);

    }
    //todo ido add this function
    public void updateCoachPost(String userName, String post) {
        systemController.updateCoachPost(userName,post);

    }
    //todo ido add this function
    public void updateRefereeName(String name, String userName) {
        systemController.updateRefereeName(name,userName);
    }
    //todo ido add this function
    public ArrayList<String> getEventByMatch(String matchId) {
        return systemController.getEventByMatch(matchId);
    }

    public ArrayList<String> allEventFromMatch(Integer matchId) {
        return systemController.allEventFromMatch(matchId);
    }
}