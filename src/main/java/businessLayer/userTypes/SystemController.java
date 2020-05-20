package businessLayer.userTypes;

import businessLayer.Team.Team;
import businessLayer.Team.TeamController;
import businessLayer.Tournament.League;
import businessLayer.Tournament.LeagueController;
import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Tournament.Season;
import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.Utilities.alertSystem.*;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.Utilities.recommendationSystem.RecommendationSystem;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.viewers.*;
import dataLayer.*;

import java.lang.reflect.Array;
import java.util.*;

public class SystemController {

    private static SystemController single_instance = null; //singleton instance
    private DB_Inter DB; //this will be our DB until the next iteration
    private AlertSystem alertSystem;
    private RecommendationSystem recommendationSystem;
    private LoggingSystem loggingSystem;
    private Subscriber temporaryAdmin; //instance of the temporary admin, which is initializing the system
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
        DB = new DBHandler();
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
        //this.DB=DB;
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
     * Getter function for the match controller
     * @return
     */
    public MatchController getMatchController(){
        return matchController;
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

    /** UC-1.1
     * @param userName The user name of the default temporary admin, as mentioned in the Readme file.
     * @param password The password of the default temporary admin, as mentioned in the Readme file.
     * @return true: if the temporary admin user was created successfully by the system. | false: The userName or password didn't match to the default temporary admin details.
     */
    public Boolean insertInfo(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            connectToSubscriberDB();
            temporaryAdmin = new Admin(userName, password, "tempAdmin", this);
            ((Admin) temporaryAdmin).setApproved(true);

            Map<String,ArrayList<String>> adminDetails = new HashMap<>();
            ArrayList<String> approved = new ArrayList<>();
            approved.add("true");
            adminDetails.put("approved",approved);

            DB.addToDB(temporaryAdmin.getUsername(),
                    temporaryAdmin.getPassword(),
                    temporaryAdmin.name,
                    "Admin",adminDetails);
            //System.out.println("The temporary admin has been created successfully.");
            return true;
        }
        return false;
    }

    /** UC-1.1 (get input from System Service)
     * @param password temporary admin password.
     * @return true if the temporary admin entered the sufficient password to initialize the system.
     * false else.
     */
    public Boolean initializeSystem(String password) {
        if (password.equals("admin")) {
            leagueController = new LeagueController();
            teamController = new TeamController();
            matchController = new MatchController();

            setLeagueController(leagueController);
            setTeamController(teamController);
            setMatchController(matchController);

            leagueController.setSystemController(this);

            matchController.setSystemController(this);

            teamController.setSystemController(this);
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

    public Boolean validateUserName(String userName){
        return userName.matches("/^[a-z0-9]+$/i");
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
            connectToTeamDB();
            if (!DB.containInDB(team.getTeamName())) {
                Map<String, ArrayList<String>> teamDetails = new HashMap<>();

                ArrayList<String> closedByAdmin =new ArrayList<>();
                closedByAdmin.add(String.valueOf(team.getClosedByAdmin()));
                teamDetails.put("closedByAdmin", closedByAdmin);

                ArrayList<String> players =new ArrayList<>();
                for(Player p : team.getPlayers()){
                    players.add(p.getUsername());
                }
                teamDetails.put("players",players);

                ArrayList<String> coaches =new ArrayList<>();
                for(Coach c: team.getCoaches()){
                    coaches.add(c.getUsername());
                }
                teamDetails.put("coaches",coaches);

                ArrayList<String> teamOwners =new ArrayList<>();
                for(TeamOwner to: team.getTeamOwners()){
                    teamOwners.add(to.getUsername());
                }
                teamDetails.put("teamOwners",teamOwners);

                DB.addToDB(team.getTeamName(),String.valueOf(team.getEstablishedYear())
                        ,String.valueOf(team.getActive()),team.getTeamManager().getUsername()
                        ,teamDetails);
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
            connectToTeamDB();
            if (DB.containInDB(teamName)) {
                Team chosenTeam = getTeamByName(teamName);
                //checks what is the status of the team
                if (chosenTeam.closeTeamPermanently()) {
                    //DB.addTeamToDB(teamName, chosenTeam); todo change to update isClosedByAdmin
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

    //-------------------Fan--------------------//

    /**
     * the function lets the subscriber to upload a complaint
     *  @param content    the content of the complaint
     * @param username the subscriber who wants to complain
     */
    public boolean addComplaint(String content, String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if(subscriber instanceof Fan){
            Complaint complaint = ((Fan) subscriber).createComplaint(content);
            if (complaint != null) {
                connectToComplaintsDB();
                int id = DB.countRecords();
                complaint.setId(id);
                addComplaint(complaint);
                subscriber.addComplaint(complaint);
                return true;
            }
        }
        return false;
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
        connectToSubscriberDB();
        Subscriber subscriber = getSubscriberByUserName(userType);
        if ((subscriber instanceof Admin)) {
            if(subscriberName != null) {
                if (DB.containInDB(subscriberName)) {
                    Subscriber tempSubscriber = selectUserFromDB(subscriberName);
                    if (tempSubscriber instanceof Admin) {
                        if (subscriber.getUsername().equals(subscriberName)) {
                            return "Admin can't remove his own user";
                        }
                    } else if (tempSubscriber instanceof TeamOwner) {
                        if (((TeamOwner) tempSubscriber).isExclusiveTeamOwner()) {
                            return "Can't remove an exclusive team owner";
                        }
                    }
                    DB.removeFromDB(subscriberName);
                    //remove from notifications
                    //if (DB.containsInNotificationDB(tempSubscriber.getUsername())) {
                      //  DB.removeNotificationFromDB(tempSubscriber.getUsername());
                    //}
                    return "The User " + subscriberName + " was removed";
                }
                return "User doesn't exist in the system";
            }
        }
        return "Access denied";
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
        HashMap<Integer, Complaint> complaintsReturn = new HashMap<>();
        ArrayList<Map<String, ArrayList<String>>> allComplaints = DB.selectAllRecords(null);
        for(Map<String, ArrayList<String>> complaint: allComplaints){
            String complaintID = complaint.get("complaintID").get(0);
            complaintsReturn.put(Integer.parseInt(complaintID),getComplaintByID(complaintID));
        }
            if (subscriber instanceof Admin) {
                return complaintsReturn;
        } else {
            return null;
        }
    }

    /**
     * the function displays the admin approval requests in the system to the admin
     *
     * @param username the user who wants to see the admin approval requests
     * @return the admin approval requests in the system
     */
    /*public HashMap<String, Subscriber> displayAdminApprovalRequests(String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Admin) {
            return DB.selectAllAdminApprovalRequests();
        } else {
            return null;
        }
    }*/

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
            //int compID = Integer.parseInt(complaintID);
            if (DB.containInDB(complaintID)) {
                Complaint complaint = getComplaintByID(complaintID);
                //Complaint editedComplaint = ((Admin) subscriber).replyComplaints(complaint,comment);
                complaint.setAnswered(true);
                complaint.setComment(comment);
                complaint.setHandler(subscriber.getUsername());
                DB.removeFromDB(complaintID);
                addComplaint(complaint);
                return true;
            }
        }
        return false;
    }

    public boolean addComplaint(Complaint complaint){
        connectToComplaintsDB();
        Map<String, ArrayList<String>> complaintDetails = new HashMap<>();
        ArrayList<String> comment = new ArrayList<>();
        comment.add(complaint.getComment());
        ArrayList<String> content = new ArrayList<>();
        content.add(complaint.getComplaintContent());
        return DB.addToDB(String.valueOf(complaint.getId()),complaint.getHandler(),complaint.getWriter()
        ,String.valueOf(complaint.isAnswered()),complaintDetails);
    }

    public Complaint getComplaintByID(String complaintID){
        connectToComplaintsDB();
        Map<String, ArrayList<String>> complaintM = DB.selectFromDB(complaintID);
        return new Complaint(Integer.parseInt(complaintID),
                complaintM.get("WriterID").get(0),complaintM.get("content").get(0)
                ,complaintM.get("HandlerID").get(0),complaintM.get("comment").get(0)
                ,Boolean.valueOf(complaintM.get("isAnswered").get(0)));
    }


    /*public boolean addAdminApprovalRequest(String userName, Subscriber admin) {
        Subscriber subscriber = getSubscriberByUserName(userName);
        if (subscriber instanceof Admin){
            DB.addAdminApprovalRequest(userName,admin);
            return true;
        }
        return false;
    }*/

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

        connectToSubscriberDB();
        Subscriber representative = getSubscriberByUserName(representativeUser);
        if (username == null || password == null || name == null || training == null || representative == null) {
            return false;
        }
        if (!(representative instanceof AssociationRepresentative)) {
            return false;
        }
        if (DB.containInDB(username)) {
            return false;
        }

        Referee newRef = new Referee(username, password, name, training, leagueController, this);
        addSubscriber(newRef);
        leagueController.addRefereeToDataFromSystemController(newRef);
        return true;
    }

    public boolean addSubscriber(Subscriber sub){
        if(sub!=null){
            connectToSubscriberDB();

            String type=null;
            Map <String,ArrayList<String>> objDetails = new HashMap<>();
            if(sub instanceof Player){
                Player p = (Player)sub;
                type = "Player";
                //Player details
                ArrayList<String> teamID = new ArrayList<>();
                teamID.add((p).getTeam().getTeamName());

                ArrayList<String> birthDate = new ArrayList<>();
                birthDate.add((p).getBirthDate());

                ArrayList<String> fieldJob = new ArrayList<>();
                fieldJob.add((p).getFieldJob().name());

                ArrayList<String> salary = new ArrayList<>();
                salary.add(String.valueOf((p).getSalary()));

                ArrayList<String> teamOwnerID= new ArrayList<>();
                teamOwnerID.add(p.getTeamOwner() ==null? null:p.getTeamOwner().getUsername());


                objDetails.put("teamID", teamID);
                objDetails.put("birthDate", birthDate);
                objDetails.put("fieldJob", fieldJob);
                objDetails.put("salary", salary);
                objDetails.put("ownerFictive", teamOwnerID);
                if(sub instanceof Coach){
                    Coach c = (Coach)sub;
                    type = "Coach";
                    //coach Details
                    ArrayList<String> teamIDC = new ArrayList<>();
                    for(Team t: c.getTeamS()){
                        teamIDC.add(t.getTeamName());
                    }

                    ArrayList<String> training = new ArrayList<>();
                    training.add(c.getTraining().name());

                    ArrayList<String> salaryC = new ArrayList<>();
                    salaryC.add(String.valueOf(c.getSalary()));

                    ArrayList<String> teamOwnerIDC= new ArrayList<>();
                    teamOwnerIDC.add(c.getTeamOwner().getUsername());

                    ArrayList<String> teamJob = new ArrayList<>();
                    teamJob.add(c.getTeamJob());

                    objDetails.put("teams", teamIDC);
                    objDetails.put("training", training);
                    objDetails.put("salary", salaryC);
                    objDetails.put("ownerFictive", teamOwnerIDC);
                    objDetails.put("teamJob",teamJob);
                }
                if(sub instanceof TeamManager){
                    type = "TeamManager";
                    TeamManager TM = (TeamManager)sub;
                    ArrayList<String> teamIDTM = new ArrayList<>();
                    teamIDTM.add(TM.getTeam().getTeamName());
                    ArrayList<String> permissions = new ArrayList<>();
                    permissions.add(TM.getPermissions().name());
                    ArrayList<String> salaryTM = new ArrayList<>();
                    salaryTM.add(String.valueOf(TM.getSalary()));
                    ArrayList<String> teamOwnerIDTM= new ArrayList<>();
                    teamOwnerIDTM.add(TM.getTeamOwner() ==null? null:TM.getTeamOwner().getUsername());

                    objDetails.put("teamID", teamIDTM);
                    objDetails.put("permissions", permissions);
                    objDetails.put("salary", salaryTM);
                    objDetails.put("ownerFictive", teamOwnerIDTM);
                }
                if(sub instanceof TeamOwner){
                    type= "TeamOwner";
                    TeamOwner TO = (TeamOwner)sub;
                    //teamOwner Details
                    ArrayList<String> teamIDTO = new ArrayList<>();
                    for(Team t: TO.getTeams()){
                        teamIDTO.add(t.getTeamName());
                    }

                    ArrayList<String> playerID = new ArrayList<>();
                    ArrayList<String> coachID = new ArrayList<>();
                    ArrayList<String> managerID = new ArrayList<>();
                    if(TO.getOriginalObject() != null){
                        if(TO.getOriginalObject() instanceof Player){
                            Player player = (Player)TO.getOriginalObject();
                            playerID.add(player.getUsername());
                        }
                        if(TO.getOriginalObject() instanceof Coach){
                            Coach coach = (Coach)TO.getOriginalObject();
                            coachID.add(coach.getUsername());
                        }
                        if(TO.getOriginalObject() instanceof TeamManager){
                            TeamManager TM = (TeamManager)TO.getOriginalObject();
                            managerID.add(TM.getUsername());
                        }
                    }

                    ArrayList<String> assigneeID = new ArrayList<>();
                    ArrayList<String> ownerTeam = new ArrayList<>();
                    for(Map.Entry<Team,LinkedList<TeamOwner>> toA: TO.getTeamOwners().entrySet()){
                        for(TeamOwner to: toA.getValue()){
                            assigneeID.add(to.getUsername());
                            ownerTeam.add(toA.getKey().getTeamName());
                        }
                    }
                    ArrayList<String> teamManagerID= new ArrayList<>();
                    ArrayList<String> managerTeam= new ArrayList<>();
                    for(Map.Entry<Team,TeamManager> TMA: TO.getTeamManagers().entrySet()){
                        teamManagerID.add(TMA.getValue().getUsername());
                        managerTeam.add(TMA.getKey().getTeamName());
                    }

                    objDetails.put("teams", teamIDTO);
                    objDetails.put("playerID", playerID);
                    objDetails.put("coachID", coachID);
                    objDetails.put("managerID", managerID);
                    objDetails.put("ownersAssigned", assigneeID);
                    objDetails.put("ownerTeam", ownerTeam);
                    objDetails.put("managersAssigned", teamManagerID);
                    objDetails.put("managerTeam", managerTeam);
                }
                if(sub instanceof Admin){
                    type = "Admin";
                    //Admin Details
                    Admin admin = (Admin)sub;
                    ArrayList<String> approvedA = new ArrayList<>();
                    approvedA.add(String.valueOf(admin.isApproved()));

                    objDetails.put("approved", approvedA);
                }
                if(sub instanceof AssociationRepresentative){
                    //AR Details
                    type = "AR";
                    AssociationRepresentative AR = (AssociationRepresentative)sub;
                    ArrayList<String> approvedA = new ArrayList<>();
                    approvedA.add(String.valueOf(AR.isApproved()));

                    objDetails.put("approved", approvedA);
                }
                if(sub instanceof Referee){
                    //referee Details
                    type = "Referee";
                    Referee ref = (Referee)sub;
                    ArrayList<String> trainingRef = new ArrayList<>();
                    trainingRef.add(ref.getTraining());
                    ArrayList<String> matches = new ArrayList<>();
                    for(Map.Entry<Integer,Match> matchE: ref.getRefMatches().entrySet()){
                        matches.add(String.valueOf(matchE.getKey()));
                    }

                    objDetails.put("training", trainingRef);
                    objDetails.put("matches", matches);
                }
            }
            DB.addToDB(sub.getUsername(),String.valueOf(sub.getPassword().hashCode()),sub.getName(),type,objDetails);

        }
        return false;
    }


    /**
     * The function removes referee from the system and returns whether the removal was successful or not
     *
     * @param username
     * @return true/false
     */
    public boolean removeReferee(String username) {

        connectToSubscriberDB();
        if (username == null) {
            return false;
        }
        if (!DB.containInDB(username)) {
            return false;
        }
        Subscriber possibleRef = getSubscriberByUserName(username);
        if (!(possibleRef instanceof Referee)) {
            return false;
        }

        //leagueController.removeReferee(possibleRef);
        Referee ref = (Referee) possibleRef;
        ref.removeFromAllMatches();
        DB.removeFromDB(username);
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
            //DB.addUnconfirmedTeamsToDB(details.getFirst(), details);
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


    public ArrayList<String> getAllRefereeNames() {
        connectToSubscriberDB();
        return DB.selectAllRecords(UserTypes.REFEREE).get(0).get("referees");

    }

    public ArrayList<String> getAllCoachesNames() {
        connectToSubscriberDB();
        return DB.selectAllRecords(UserTypes.COACH).get(0).get("coaches");

    }

    /**
     * checks if the Association Representative exists in the DB
     * @param username
     * @return
     */
    public boolean containsInSystemAssociationRepresentative(String username){
        connectToSubscriberDB();
        Subscriber subscriber = getSubscriberByUserName(username);
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
   /* public boolean confirmTeamByAssociationRepresntative(String teamName, String username) {
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
    }*/

    /**
     * the function checks if a player exists in the DB
     * @param playerName
     * @return
     */
    public boolean checkUserExists(String playerName){
        connectToSubscriberDB();
        return DB.containInDB(playerName);
    }

    /**
     * brings back a subscriber from the data base if he exists in the system
     * @param username
     * @return
     */
    public Subscriber selectUserFromDB(String username){
        if(checkUserExists(username)){
            return getSubscriberByUserName(username);
        }
        return null;
    }

    /**
     * this function find the player according to is user name and return it if the player exist in the system
     * @param username the user name of the player
     * @return the player
     */
    public Player findPlayer(String username) {
        Subscriber sub = getSubscriberByUserName(username);
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
    /*public boolean containsLeague(String leagueID){
        return DB.containsInSystemLeague(leagueID);
    }*/

    /**
     * the function returns the league value from DB
     * @param leagueID
     * @return
     */
   /* public League getLeagueFromDB(String leagueID){
        return DB.selectLeagueFromDB(leagueID);
    }*/

    /**
     * add new league to the DB
     * @param leagueID
     * @param league
     * @return
     */
   /* public boolean addLeagueToDB(String leagueID, League league){
        return DB.addLeagueToDB(leagueID,league);
    }*/



    /**
     * this function find the TeamManager according to is user name and return it if the TeamManager exist in the system
     * @param assetUserName the user name of the TeamManager
     * @return the TeamManager
     */
    public TeamManager findTeamManager(String assetUserName) {
        Subscriber sub = getSubscriberByUserName(assetUserName);
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
        Subscriber sub = getSubscriberByUserName(assetUserName);
        if (sub instanceof Coach) {
            Coach coach = (Coach) sub;
            return coach;
        } else {
            return null;
        }
    }

    public Stadium getStadiumByID(String stadiumID){
        connectToStadiumDB();
        Map<String, ArrayList<String>> stadium = DB.selectFromDB(stadiumID);
        HashMap<String,Team> owners = new HashMap<>();
        for(String str: stadium.get("teams")){
            owners.put(str,getTeamByName(str));
        }

        return new Stadium(stadiumID,null,null
                ,Integer.parseInt(stadium.get("numOfSeats").get(0))
                ,owners);
    }

    public Stadium findStadium(String assetUserName) {
        connectToStadiumDB();
        if(DB.containInDB(assetUserName)){
            return getStadiumByID(assetUserName);
        }
        return null;
    }

    /**
     * return a default stadium to the matches policies
     * @return
     */
    public Stadium findDefaultStadium(){
        int totalStadiums= DB.countRecords();
        Random rand = new Random();
        int random = rand.nextInt(totalStadiums);
        return getStadiumByID(String.valueOf(random));
    }

    /** todo-Next Iteration
     * This function will create variables for the user to enter for the login procedure, and will send them (via enterUserDetails(userNameInput, passwordInput) to be filled by the guest in the UI/GUI.
     *
     * @param guest The guest which started the login procedure.
     * @return the instance of Subscriber from systemSubscribers, if the login details were correct.
     * NULL if the login form wasn't filled properly, or one of the user details wasn't correct.
     */
/*    public Subscriber createLoginForm(Guest guest) {
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
    }*/

    /**
     * @param userName the user name that the user searches it's user instance
     * @return the user's instance with the user name, if existed in the system
     * NULL if there is no user in the system with the input user name
     */
    public Subscriber getSubscriberByUserName(String userName) {
        Subscriber sub = null;
        if (DB.containInDB(userName)) {
            connectToSubscriberDB();
            Map<String,ArrayList<String>> subDetails = DB.selectFromDB(userName);
            String type = subDetails.get("type").get(0);
            if(type.equalsIgnoreCase("player")){
                sub = new Player(userName,subDetails.get("password").get(0)
                        ,subDetails.get("name").get(0),subDetails.get("birthDate").get(0)
                        ,FIELDJOB.valueOf(subDetails.get("fieldJob").get(0)),
                        Integer.parseInt(subDetails.get("salary").get(0))
                        ,getTeamByName(subDetails.get("teamID").get(0)),this);
            }

            if(type.equalsIgnoreCase("coach")){
                sub = new Coach(userName,subDetails.get("password").get(0)
                        ,subDetails.get("name").get(0)
                        ,TRAINING.valueOf(subDetails.get("training").get(0))
                        ,"coach"
                        ,Integer.parseInt(subDetails.get("salary").get(0))
                        ,this);
                for(String str: subDetails.get("teams")){
                    Team team = getTeamByName(str);
                    ((Coach) sub).addTeam(team);
                }
            }
            if(type.equalsIgnoreCase("TEAMMANAGER")){
                sub = new TeamManager(userName,subDetails.get("password").get(0)
                        ,subDetails.get("name").get(0)
                        ,getTeamByName(subDetails.get("teamID").get(0))
                        ,Integer.parseInt(subDetails.get("salary").get(0))
                        ,this);
            }
            if(type.equalsIgnoreCase("teamowner")){
                sub = new TeamOwner(userName,subDetails.get("password").get(0)
                        ,subDetails.get("name").get(0)
                        ,this);
                for(String str: subDetails.get("teams")){
                    ((TeamOwner) sub).addTeam(getTeamByName(str));
                }
                for(int i=0;i<subDetails.get("ownerAssigned").size() ;i++){
                    Team team = getTeamByName(subDetails.get("ownerTeam").get(i));
                    TeamOwner to = (TeamOwner)getSubscriberByUserName(subDetails.get("ownerAssigned").get(0));
                    ((TeamOwner)sub).addAssignedOwner(team,to);
                }
                for(int i=0;i<subDetails.get("managersAssigned").size() ;i++){
                    Team team = getTeamByName(subDetails.get("managerTeam").get(i));
                    TeamManager TM = (TeamManager)getSubscriberByUserName(subDetails.get("managersAssigned").get(0));
                    ((TeamOwner)sub).addAssignedManager(team,TM);
                }
            }
            if(type.equalsIgnoreCase("admin")){
                sub = new Admin(userName,subDetails.get("password").get(0)
                        ,subDetails.get("name").get(0)
                        ,this);
            }

            if(type.equalsIgnoreCase("AR")){
                sub = new AssociationRepresentative(userName,subDetails.get("password").get(0)
                        ,subDetails.get("name").get(0)
                        ,this);
            }
            if(type.equalsIgnoreCase("referee")){
                sub = new Referee(userName,subDetails.get("password").get(0)
                        ,subDetails.get("name").get(0)
                        ,subDetails.get("training").get(0)
                        ,getLeagueController()
                        ,this);
                for(String str: subDetails.get("matches")){
                    ((Referee) sub).addMatch(findMatch(Integer.parseInt(str)));
                }
            }
            return sub;
        }
        return null;
    }

    /**
     * @param teamName the team name that the user searches it's team instance
     * @return the team's instance with the team name, if existed in the system
     * *      NULL if there is no team in the system with the input team name
     */
    public Team getTeamByName(String teamName) {
        connectToTeamDB();
        if (DB.containInDB(teamName)) {
            Map<String,ArrayList<String>> teamDetails = DB.selectFromDB(teamName);
            HashSet<Player> players = new HashSet<>();
            HashSet<Coach> coaches = new HashSet<>();
            HashSet<Match> matches = new HashSet<>();
            HashSet<TeamOwner> teamOwners = new HashSet<>();
            HashSet<Season> seasons = new HashSet<>();
            for(String str: teamDetails.get("players")){
                players.add((Player)getSubscriberByUserName(str));
            }
            for(String str: teamDetails.get("coach")){
                coaches.add((Coach)getSubscriberByUserName(str));
            }
            for(String str: teamDetails.get("matches")){
                matches.add(findMatch(Integer.parseInt(str)));
            }
            for(String str: teamDetails.get("teamOwners")){
                teamOwners.add((TeamOwner) getSubscriberByUserName(str));
            }
            for(int i=0; i<teamDetails.get("seasons").size();i++ ){
                seasons.add(selectSeasonFromDB(teamDetails.get("seasons").get(i),
                        teamDetails.get("leagues").get(i)));
            }

            Stadium stadium = findStadium(teamDetails.get("stadium").get(0));
            TeamManager TM = (TeamManager)getSubscriberByUserName(teamDetails.get("teamManagerID").get(0));
            return new Team(players,coaches,TM
                    ,teamOwners, new FinancialMonitoring(null),matches,seasons
                    ,stadium,teamName
                    ,Integer.parseInt(teamDetails.get("establishedYear").get(0))
                    ,Boolean.valueOf(teamDetails.get("isActive").get(0))
                    ,Boolean.valueOf(teamDetails.get("closedByAdmin").get(0)));
        }
        return null;
    }

    /*
    public HashMap<String, Team> getTeams() {
        return teams;
    }
    */

    /**
     * UC 2.2 todo - Next Iteration
     * @param guest the guest who requests a registration form from the system
     * @return true if the system created a registration form
     *          false else
     */
    /*public Boolean createRegistrationForm(Guest guest) {
        String userNameInput = null;
        String passwordInput = null;
        guest.enterUserDetails(userNameInput, passwordInput);
        if (userNameInput == null || userNameInput.length() == 0 || passwordInput == null || passwordInput.length() == 0) {
            //System.out.println("Not all fields were filled by the user.");
            return false;
        }
        if (checkPasswordStrength(passwordInput, userNameInput) == false) {
            return false;
        }

        String firstName = null;
        String lastName = null;

        while (!(firstName.matches("^[a-zA-Z]+$")) || !(lastName.matches("^[a-zA-Z]+$"))) //check if only letters
            guest.enterUserRealName(firstName, lastName);

        Subscriber newFan = new Fan(userNameInput, passwordInput, firstName + " " + lastName, this);
        addSubscriber(newFan);

        return false;
    }*/

    /**
     * add stadium to stadiumDB
     * @param stadium
     */
    public boolean addStadium(Stadium stadium){
        connectToStadiumDB();
        if(stadium!=null){
            Map<String,ArrayList<String>> stadiumDetails = new HashMap<>();
            ArrayList<String> teams = new ArrayList<>();
            for(Map.Entry<String,Team> owners:stadium.getOwners().entrySet()){
                teams.add(owners.getKey());
            }

            stadiumDetails.put("teams",teams);

            return DB.addToDB(stadium.getName(),String.valueOf(stadium.getNumberOfSeats())
                    ,String.valueOf(stadium.getTicketCost()),null,stadiumDetails); //todo for IDO please check if you can use my function
        }
        return false;
    }

    // -------------------AssociationRepresentative--------------------//

    /**
     * the function adds
     * @param nameStadium
     * @param numberOfSeats
     * @return
     */
    public boolean addNewStadium(String nameStadium, String numberOfSeats){
        connectToStadiumDB();
        if (!DB.containInDB(nameStadium)){
            int numOfSeats = Integer.parseInt(numberOfSeats);
            Stadium stadium = new Stadium(nameStadium,numOfSeats);
            return addStadium(stadium);
        }
        return false;
    }

    /** UC-6.6 - enable team status by Team Owner todo-write tests
     * @param teamName the name of the team from input
     * @param userName the user who wants to enable the team status
     * @return true if the team's status has been enabled.
     *          false else.
     */
    public Boolean enableTeamStatus(String teamName, String userName) {
        if (userName == null || teamName == null) {
            return false;
        }
        if (!DB.containInDB(userName) || !DB.containInDB(teamName)) {
            return false;
        }
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if(possibleTeamOwner instanceof TeamOwner){ //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner)possibleTeamOwner);
            if(teamOwner.getTeam(teamName) != null){ //check if the team owner owns the team
                return teamOwner.enableStatus(teamOwner.getTeam(teamName));
            }
            else {
                return false; //the team owner doesn't own the team
            }
        }
        else if(possibleTeamOwner instanceof OwnerEligible){
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.enableStatus(teamOwner.getTeam(teamName));
            }
            else
                return false;
        }
        else{
            return false; //the user isn't a team owner
        }
    }

    /** UC-6.6 - disable team status by Team Owner todo-write tests
     * @param teamName the name of the team from input
     * @param userName the user who wants to disable the team status
     * @return true if the team's status has been disabled.
     *          false else.
     */
    public Boolean disableTeamStatus(String teamName, String userName) {
        if (userName == null || teamName == null) {
            return false;
        }
        connectToSubscriberDB();
        if (!DB.containInDB(userName)) {
            return false;
        }
        connectToTeamDB();
        if(!DB.containInDB(teamName)){
            return false;
        }
        connectToSubscriberDB();
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if(possibleTeamOwner instanceof TeamOwner){ //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner)possibleTeamOwner);
            if(teamOwner.getTeam(teamName) != null){ //check if the team owner owns the team
                return teamOwner.disableStatus(teamOwner.getTeam(teamName));
            }
            else {
                return false; //the team owner doesn't own the team
            }
        }
        else if(possibleTeamOwner instanceof OwnerEligible){
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.disableStatus(teamOwner.getTeam(teamName));
            }
            else
                return false;
        }
        else{
            return false; //the user isn't a team owner
        }
    }

    /**
     * //UC-6.2
     * @param teamName the team's name of the team which the user wants to add to it's owners
     * @param newUserName the new team owner's user name
     * @param userName the user which wants to add the user newUserName the the team owners
     * @return true if newUserName was added to the team's owners
     *          false else
     */
    public Boolean appoinTeamOwnerToTeam(String teamName, String newUserName, String userName) {
        if (userName == null || teamName == null || newUserName == null) {
            return false;
        }
        connectToSubscriberDB();
        if (!DB.containInDB(userName)) {
            return false;
        }
        connectToTeamDB();
        if(!DB.containInDB(teamName)){
            return false;
        }
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if(possibleTeamOwner instanceof TeamOwner) { //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner)possibleTeamOwner);
            if(teamOwner.enterMember(newUserName) != null) {
                return teamOwner.appointToOwner(teamOwner.enterMember(newUserName), teamName);
            }
            else //There is no such user with the user name of 'newUserName' in the system
                return false;
        }
        else if(possibleTeamOwner instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.appointToOwner(teamOwner.enterMember(newUserName), teamName);
            } else
                return false;
        }
        else{
            return false; //the user isn't a team owner
        }
    }

    public Boolean removeOwnerFromTeam(String userName, String teamName, String newUserName){
        if (userName == null || teamName == null || newUserName == null) {
            return false;
        }
        connectToSubscriberDB();
        if (!DB.containInDB(userName)) {
            return false;
        }
        connectToTeamDB();
        if(!DB.containInDB(teamName)){
            return false;
        }
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if(possibleTeamOwner instanceof TeamOwner) { //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner)possibleTeamOwner);
            if(teamOwner.enterMember(newUserName) != null) {
                return teamOwner.removeOwner(teamOwner.enterMember(newUserName), teamName);
            }
            else //There is no such user with the user name of 'newUserName' in the system
                return false;
        }
        else if(possibleTeamOwner instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.removeOwner(teamOwner.enterMember(newUserName), teamName);
            } else
                return false;
        }
        else{
            return false; //the user isn't a team owner
        }
    }


    /**
     * finds a match in the DB
     * @param matchID
     * @return
     */
    public Match findMatch(int matchID){
        //return DB.selectMatchFromDB(matchID);
        return null;
    }

    /**
     * Login UC-2.3
     * @param userName the User Name as the user's input
     * @param password the Password as the user's input
     * @return the user type if there is a Subscriber in the DB with the @userName and the @password
     *         null - else, or one of the inputs are null
     */
    public String enterLoginDetails(String userName, String password) {

        if(userName == null || password == null){
            return null;
        }

        Subscriber subscriber = selectUserFromDB(userName);

        if(subscriber==null)
            return null;

        if(subscriber.getPassword().equals(password)) {
            if(subscriber instanceof Admin){
                Admin userCheckIfAprroved = ((Admin)subscriber);
                if(userCheckIfAprroved.isApproved() == false){
                    return null;
                }
            }
            else if(subscriber instanceof AssociationRepresentative){
                AssociationRepresentative userCheckIfAprroved = ((AssociationRepresentative)subscriber);
                if(userCheckIfAprroved.isApproved() == false){
                    return null;
                }
            }
            return subscriber.toString();
        }
        return null;
    }


    // -------------------Guest--------------------//

    /**
     * Registration for player:
     * Creates a new player in the DB
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name the name of the player
     * @param birthDate the player's date of birth
     * @param fieldJob the field job of the player
     * @param teamName the team name of the player
     * @return true if the new player was created successfully in the DB
     *          false else
     */
    public boolean enterRegisterDetails_Player(String userName, String password, String name, String birthDate, String fieldJob, String teamName) {
        if(userName == null || password == null || name == null || birthDate == null || fieldJob == null || teamName == null){
            return false;
        }

        if(validateUserName(userName)){
            return false;
        }

        if(checkPasswordStrength(password,userName) == false){
            return false;
        }



        Subscriber subscriber = selectUserFromDB(userName);

        if(subscriber!=null) //user name is already exists in the database
            return false;

        Team team = getTeamByName(teamName);
        if(team == null){ //no such team in the DB
            return false;
        }
        Subscriber newPlayer = new Player(userName,password,name,birthDate,FIELDJOB.valueOf(fieldJob),0,team,this);
        addSubscriber(newPlayer);
        return true;
    }

    /**
     * Registration for Coach:
     * Creates a new coach in the DB
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name the name of the coach
     * @param training the training of the new coach
     * @param teamJob the team job of the new coach
     * @return true if the new coach was created successfully in the DB
     *         false else
     */
    public boolean enterRegisterDetails_Coach(String userName, String password, String name, String training, String teamJob){
        if(userName == null || password == null || name == null || training==null|| teamJob==null){
            return false;
        }
        if(validateUserName(userName)){
            return false;
        }
        if(checkPasswordStrength(password,userName) == false){
            return false;
        }
        if(checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newCoach = new Coach(userName,password,name,TRAINING.valueOf(training),teamJob,0,this);
        addSubscriber(newCoach);
        return true;
    }

    /**
     * Registration for Team Owner:
     * Creates a new team owner in the DB
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name the name of the team owner
     * @return true if the new team owner was created successfully in the DB
     *         false else
     */
    public boolean enterRegisterDetails_TeamOwner(String userName, String password, String name){
        if(userName == null || password == null || name == null){
            return false;
        }
        if(validateUserName(userName)){
            return false;
        }
        if(checkPasswordStrength(password,userName) == false){
            return false;
        }
        if(checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newTeamOwner = new TeamOwner(userName,password,name,this);
        addSubscriber(newTeamOwner);
        return true;
    }

    /**
     * Registration for Team Manager:
     * Creates a new team manager in the DB
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name the name of the team manager
     * @param teamName the team name of the team owner
     * @return true if the new team manager was created successfully in the DB
     *         false else
     */
    public boolean enterRegisterDetails_TeamManager(String userName, String password, String name, String teamName){
        if(userName == null || password == null || name == null || teamName == null){
            return false;
        }
        if(validateUserName(userName)){
            return false;
        }
        if(checkPasswordStrength(password,userName) == false){
            return false;
        }
        if(checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Team team = getTeamByName(teamName);
        if(team == null){ //no such team in the DB
            return false;
        }
        Subscriber newTeamManager = new TeamManager(userName,password,name,team,0,this);
        addSubscriber(newTeamManager);
        return true;
    }

    /**
     * Registration for Admin:
     * Creates a new Admin in the DB
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name the name of the admin
     * @return true if the new admin was created successfully in the DB
     *         false else
     */
    public boolean enterRegisterDetails_Admin(String userName, String password, String name) {
        if(userName == null || password == null || name == null){
            return false;
        }
        if(validateUserName(userName)){
            return false;
        }
        if(checkPasswordStrength(password,userName) == false){
            return false;
        }
        if(checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newAdmin = new Admin(userName,password,name,this);
        addSubscriber(newAdmin);
        //addAdminApprovalRequest(userName,newAdmin);
        return true;
    }

    /**
     * Registration for AR:
     * Creates a new AR in the DB
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name the name of the AR
     * @return true if the new AR was created successfully in the DB
     *         false else
     *
     */
    public boolean enterRegisterDetails_AssociationRepresentative(String userName, String password, String name) {

        if(userName == null || password == null || name == null){
            return false;
        }
        if(validateUserName(userName)){
            return false;
        }
        if(checkPasswordStrength(password,userName) == false){
            return false;
        }
        if(checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newAssociationRepresentative = new AssociationRepresentative(userName,password,name,this);
        addSubscriber(newAssociationRepresentative);
        //addAdminApprovalRequest(userName,newAssociationRepresentative);
        return true;
    }

    /**
     * @param userName the user name to be checked
     * @return true if the user name exists in the DB
     *         false else
     */
    private boolean checkIfUserNameExistsInDB(String userName){
        Subscriber subscriber = selectUserFromDB(userName);

        if(subscriber!=null) //user name is already exists in the database
            return true;

        return false;
    }


    /**
     * This function handles the operation of approving a new AR or Admin user by an already-approved admin.
     * @param userName the user name of the user which approves
     * @param userNameToApprove the user name of the user which is being approved
     * @param approve = true, disapprove = false
     * @return true if the userNameToApprove was approved/disapproved by userName
     *         false else
     */
    public boolean handleAdminApprovalRequest(String userName, String userNameToApprove, boolean approve) {
        Subscriber approver = selectUserFromDB(userName);
        if(!(approver instanceof Admin)){
            return false;
        }
        Admin adminApprover = ((Admin)approver);
        return adminApprover.approveAdminRequest(userNameToApprove,approve);
    }

    public boolean removeAdminRequest(String userNameToApprove) {
        //DB.removeAdminRequest(userNameToApprove);
        return true;
    }


    /**
     * function that asks from the DB to get a Season
     * @param leagueID
     * @param seasonID
     * @return
     */
    public Season selectSeasonFromDB(String leagueID, String seasonID){
        //return DB.selectSeasonFromDB(leagueID,seasonID);
        return null;
    }

    private void connectToSubscriberDB(){
        DB.TerminateDB();
        DB = new DBHandler();
    }
    private void connectToTeamDB(){
        DB.TerminateDB();
        DB = new TeamDB();
    }
    private void connectToComplaintsDB(){
        DB.TerminateDB();
        DB = new ComplaintsDB();
    }
    private void connectToStadiumDB(){
        DB.TerminateDB();
        DB = new StadiumDB();
    }
}
