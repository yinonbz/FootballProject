package businessLayer.userTypes;

import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.Exceptions.MissingInputException;
import businessLayer.Exceptions.NotApprovedException;
import businessLayer.Exceptions.NotFoundInDbException;
import businessLayer.Team.Team;
import businessLayer.Team.TeamController;
import businessLayer.Tournament.*;
import businessLayer.Tournament.Match.*;
import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.Utilities.HasPage;
import businessLayer.Utilities.Page;
import businessLayer.Utilities.alertSystem.*;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.Utilities.recommendationSystem.RecommendationSystem;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.viewers.*;
import dataLayer.*;
import dataLayer.DemoDB;
import serviceLayer.SystemService;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SystemController extends Observable {

    private static SystemController single_instance = null; //singleton instance
    private DB_Inter DB; //DBs in use
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

    /**
     * The function receives a service and adds it as an observer of the system-controller
     *
     * @param service
     */
    public void addServiceObservers(SystemService service) {
        addObserver(service);
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
     *
     * @param DB
     * @return
     */
    public boolean connectToDB(DemoDB DB) {
        //this.DB = DB;
        return true;
    }


    /**
     * Getter function for the league controller
     *
     * @return
     */
    public LeagueController getLeagueController() {
        return leagueController;
    }

    /**
     * Getter function for the match controller
     *
     * @return
     */
    public MatchController getMatchController() {
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
     * @return
     */
    public TeamController getTeamController() {
        return teamController;
    }

    /**
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
     * UC-1.1
     *
     * @param userName The user name of the default temporary admin, as mentioned in the Readme file.
     * @param password The password of the default temporary admin, as mentioned in the Readme file.
     * @return true: if the temporary admin user was created successfully by the system. | false: The userName or password didn't match to the default temporary admin details.
     */
    public Boolean insertInfo(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            connectToSubscriberDB();
            temporaryAdmin = new Admin(userName, password, "tempAdmin", this);
            ((Admin) temporaryAdmin).setApproved(true);
//            DB.addSubscriberToDB("admin", temporaryAdmin); todo fix it with db

            Map<String, ArrayList<String>> adminDetails = new HashMap<>();
            ArrayList<String> approved = new ArrayList<>();
            approved.add("true");
            adminDetails.put("approved", approved);

            DB.addToDB(temporaryAdmin.getUsername(),
                    temporaryAdmin.getPassword(),
                    temporaryAdmin.name,
                    "Admin", adminDetails);
            //System.out.println("The temporary admin has been created successfully.");
            return true;
        }
        return false;
    }

    /**
     * UC-1.1 (get input from System Service)
     *
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
        //todo update db record
        return true;
    }

    public Boolean validateUserName(String userName) {
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
            if (!DB.containInDB(team.getTeamName(), null, null)) {
                Map<String, ArrayList<String>> teamDetails = new HashMap<>();

                ArrayList<String> closedByAdmin = new ArrayList<>();
                closedByAdmin.add(String.valueOf(team.getClosedByAdmin()));
                teamDetails.put("closedByAdmin", closedByAdmin);

                ArrayList<String> players = new ArrayList<>();
                for (Player p : team.getPlayers()) {
                    players.add(p.getUsername());
                }
                teamDetails.put("players", players);

                ArrayList<String> coaches = new ArrayList<>();
                for (Coach c : team.getCoaches()) {
                    coaches.add(c.getUsername());
                }
                teamDetails.put("coaches", coaches);

                ArrayList<String> teamOwners = new ArrayList<>();
                for (TeamOwner to : team.getTeamOwners()) {
                    teamOwners.add(to.getUsername());
                }
                teamDetails.put("teamOwners", teamOwners);

                if (team.getTeamManager() != null)
                    DB.addToDB(team.getTeamName(), String.valueOf(team.getEstablishedYear())
                            , String.valueOf(team.getActive()), team.getTeamManager().getUsername()
                            , teamDetails);
                else
                    DB.addToDB(team.getTeamName(), String.valueOf(team.getEstablishedYear())
                            , String.valueOf(team.getActive()), null
                            , teamDetails);
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
            if (DB.containInDB(teamName, null, null)) {
                Team chosenTeam = getTeamByName(teamName);
                //checks what is the status of the team
                if (chosenTeam.closeTeamPermanently()) {
                    setTeamActive(chosenTeam.getTeamName(), String.valueOf(chosenTeam.getActive()));
                    updateTeamStatusToUsers(chosenTeam, "The team " + chosenTeam.getTeamName() + " was closed permanently.");
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
     *
     * @param content  the content of the complaint
     * @param username the subscriber who wants to complain
     */
    public boolean addComplaint(String content, String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Fan) {
            Complaint complaint = ((Fan) subscriber).createComplaint(content);
            if (complaint != null) {
                connectToComplaintsDB();
                int id = DB.countRecords();
                complaint.setId(id);
                addComplaint(complaint);
                subscriber.addComplaint(complaint); //todo update db
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
            if (subscriberName != null) {
                if (DB.containInDB(subscriberName, null, null)) {
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
                    DB.removeFromDB(subscriberName, null, null);
                    //remove from notifications
                    /*if (DB.containsInNotificationDB(tempSubscriber.getUsername())) {
                        DB.removeNotificationFromDB(tempSubscriber.getUsername());
                    }*/ //fixme take out of comment
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
        connectToComplaintsDB();
        ArrayList<Map<String, ArrayList<String>>> allComplaints = DB.selectAllRecords(null, null);
        for (Map<String, ArrayList<String>> complaint : allComplaints) {
            String complaintID = complaint.get("complaintID").get(0);
            complaintsReturn.put(Integer.parseInt(complaintID), getComplaintByID(complaintID));
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
    public HashMap<String, Subscriber> displayAdminApprovalRequests(String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Admin) {
            //return DB.selectAllAdminApprovalRequests();fixme take out of comment
            return null;
        } else {
            return null;
        }
    }

    /**
     * the function lets the admin to respond the the comments in the system
     *
     * @param complaintID the complain's id the admin wants to respond to
     * @param username    the user that wants to respond - has to be an admin
     * @param comment     - the comment of the admin
     * @return true is he responded successfully
     * UC 8.3.2
     */
    public boolean replyComplaints(String complaintID, String username, String comment) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Admin && !comment.isEmpty()) {
            //int compID = Integer.parseInt(complaintID);
            if (DB.containInDB(complaintID, null, null)) {
                Complaint complaint = getComplaintByID(complaintID);
                //Complaint editedComplaint = ((Admin) subscriber).replyComplaints(complaint,comment);
                complaint.setAnswered(true);
                complaint.setComment(comment);
                complaint.setHandler(subscriber.getUsername());
                DB.removeFromDB(complaintID, null, null);//todo update instead
                addComplaint(complaint);
                return true;
            }
        }
        return false;
    }

    public boolean addComplaint(Complaint complaint) {
        connectToComplaintsDB();
        Map<String, ArrayList<String>> complaintDetails = new HashMap<>();
        ArrayList<String> comment = new ArrayList<>();
        comment.add(complaint.getComment());
        ArrayList<String> content = new ArrayList<>();
        content.add(complaint.getComplaintContent());
        return DB.addToDB(String.valueOf(complaint.getId()), complaint.getHandler(), complaint.getWriter()
                , String.valueOf(complaint.isAnswered()), complaintDetails);
    }

    public Complaint getComplaintByID(String complaintID) {
        connectToComplaintsDB();
        Map<String, ArrayList<String>> complaintM = DB.selectFromDB(complaintID, null, null);
        return new Complaint(Integer.parseInt(complaintID),
                complaintM.get("WriterID").get(0), complaintM.get("content").get(0)
                , complaintM.get("HandlerID").get(0), complaintM.get("comment").get(0)
                , Boolean.valueOf(complaintM.get("isAnswered").get(0)));
    }


    public boolean addAdminApprovalRequest(String userName, Subscriber admin) {
        Subscriber subscriber = getSubscriberByUserName(userName);
        if (subscriber instanceof Admin) {
            //DB.addAdminApprovalRequest(userName,admin);fixme take out of comment
            return true;
        }
        return false;

    }

    /**
     * The function adds a referee to the system and returns whether the referee was successfully added or not
     *
     * @param username
     * @param password
     * @param name
     * @param refTraining
     * @param representativeUser
     * @return true/false
     */
    public boolean addReferee(String username, String password, String name, String refTraining, String representativeUser) {

        connectToSubscriberDB();
        Subscriber representative = getSubscriberByUserName(representativeUser);
        if (username == null || password == null || name == null || refTraining == null || representative == null) {
            return false;
        }
        if (!(representative instanceof AssociationRepresentative)) {
            return false;
        }
        if (DB.containInDB(username, null, null)) {
            return false;
        }

        Referee newRef = new Referee(username, password, name, roleRef.valueOf(refTraining), leagueController, this);
        addSubscriber(newRef);
        leagueController.addRefereeToDataFromSystemController(newRef);
        return true;
    }

    public boolean addSubscriber(Subscriber sub) {
        if (sub != null) {
            connectToSubscriberDB();

            String type = null;
            Map<String, ArrayList<String>> objDetails = new HashMap<>();
            if (sub instanceof Player) {
                Player p = (Player) sub;
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

                ArrayList<String> teamOwnerID = new ArrayList<>();
                teamOwnerID.add(p.getTeamOwner() == null ? null : p.getTeamOwner().getUsername());


                objDetails.put("teamID", teamID);
                objDetails.put("birthDate", birthDate);
                objDetails.put("fieldJob", fieldJob);
                objDetails.put("salary", salary);
                objDetails.put("ownerFictive", teamOwnerID);
            }
            if (sub instanceof Coach) {
                Coach c = (Coach) sub;
                type = "Coach";
                //coach Details
                ArrayList<String> teamIDC = new ArrayList<>();
                for (Team t : c.getTeamS()) {
                    teamIDC.add(t.getTeamName());
                }

                ArrayList<String> training = new ArrayList<>();
                training.add(c.getTraining().name());

                ArrayList<String> roleInTeam = new ArrayList<>();
                roleInTeam.add(c.getRoleInTeam().name());

                ArrayList<String> salaryC = new ArrayList<>();
                salaryC.add(String.valueOf(c.getSalary()));

                ArrayList<String> teamOwnerIDC = new ArrayList<>();
                teamOwnerIDC.add(c.getTeamOwner().getUsername());

                ArrayList<String> teamJob = new ArrayList<>();
                teamJob.add(c.getRoleInTeam().name());

                objDetails.put("teams", teamIDC);
                objDetails.put("training", training);
                objDetails.put("roleInTeam", roleInTeam);
                objDetails.put("salary", salaryC);
                objDetails.put("ownerFictive", teamOwnerIDC);
                objDetails.put("teamJob", teamJob);
            }
            if (sub instanceof TeamManager) {
                type = "TeamManager";
                TeamManager TM = (TeamManager) sub;
                ArrayList<String> teamIDTM = new ArrayList<>();
                teamIDTM.add(TM.getTeam().getTeamName());
                ArrayList<String> permissions = new ArrayList<>();
                permissions.add(TM.getPermissions().name());
                ArrayList<String> salaryTM = new ArrayList<>();
                salaryTM.add(String.valueOf(TM.getSalary()));
                ArrayList<String> teamOwnerIDTM = new ArrayList<>();
                teamOwnerIDTM.add(TM.getTeamOwner() == null ? null : TM.getTeamOwner().getUsername());

                objDetails.put("teamID", teamIDTM);
                objDetails.put("permissions", permissions);
                objDetails.put("salary", salaryTM);
                objDetails.put("ownerFictive", teamOwnerIDTM);
            }
            if (sub instanceof TeamOwner) {
                type = "TeamOwner";
                TeamOwner TO = (TeamOwner) sub;
                //teamOwner Details
                ArrayList<String> teamIDTO = new ArrayList<>();
                for (Team t : TO.getTeams()) {
                    teamIDTO.add(t.getTeamName());
                }

                ArrayList<String> playerID = new ArrayList<>();
                ArrayList<String> coachID = new ArrayList<>();
                ArrayList<String> managerID = new ArrayList<>();
                if (TO.getOriginalObject() != null) {
                    if (TO.getOriginalObject() instanceof Player) {
                        Player player = (Player) TO.getOriginalObject();
                        playerID.add(player.getUsername());
                    }
                    if (TO.getOriginalObject() instanceof Coach) {
                        Coach coach = (Coach) TO.getOriginalObject();
                        coachID.add(coach.getUsername());
                    }
                    if (TO.getOriginalObject() instanceof TeamManager) {
                        TeamManager TM = (TeamManager) TO.getOriginalObject();
                        managerID.add(TM.getUsername());
                    }
                }

                ArrayList<String> assigneeID = new ArrayList<>();
                ArrayList<String> ownerTeam = new ArrayList<>();
                for (Map.Entry<Team, LinkedList<TeamOwner>> toA : TO.getTeamOwners().entrySet()) {
                    for (TeamOwner to : toA.getValue()) {
                        assigneeID.add(to.getUsername());
                        ownerTeam.add(toA.getKey().getTeamName());
                    }
                }
                ArrayList<String> teamManagerID = new ArrayList<>();
                ArrayList<String> managerTeam = new ArrayList<>();
                for (Map.Entry<Team, TeamManager> TMA : TO.getTeamManagers().entrySet()) {
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
            if (sub instanceof Admin) {
                type = "Admin";
                //Admin Details
                Admin admin = (Admin) sub;
                ArrayList<String> approvedA = new ArrayList<>();
                approvedA.add(String.valueOf(admin.isApproved()));

                objDetails.put("approved", approvedA);
            }
            if (sub instanceof AssociationRepresentative) {
                //AR Details
                type = "AR";
                AssociationRepresentative AR = (AssociationRepresentative) sub;
                ArrayList<String> approvedA = new ArrayList<>();
                approvedA.add(String.valueOf(AR.isApproved()));

                objDetails.put("approved", approvedA);
            }
            if (sub instanceof Referee) {
                //referee Details
                type = "Referee";
                Referee ref = (Referee) sub;
                ArrayList<String> trainingRef = new ArrayList<>();
                trainingRef.add(ref.getRoleRef().name());
                ArrayList<String> matches = new ArrayList<>();
                for (Map.Entry<Integer, Match> matchE : ref.getRefMatches().entrySet()) {
                    matches.add(String.valueOf(matchE.getKey()));
                }

                objDetails.put("training", trainingRef);
                objDetails.put("matches", matches);
            }
            DB.addToDB(sub.getUsername(), String.valueOf(sub.getPassword().hashCode()), sub.getName(), type, objDetails);
            return true;
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
        if (!DB.containInDB(username, null, null)) {
            return false;
        }
        Subscriber possibleRef = getSubscriberByUserName(username);
        if (!(possibleRef instanceof Referee)) {
            return false;
        }

        //leagueController.removeReferee(possibleRef);
        Referee ref = (Referee) possibleRef;
        ref.removeFromAllMatches();
        DB.removeFromDB(username, null, null);
        return true;
    }

    //-------------------TeamOwner--------------------//

    /**
     * the function takes a request for opening a new team and puts it in the data structure
     *
     * @param details  of the new team
     * @param username
     */
    public boolean addToTeamConfirmList(LinkedList<String> details, String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof TeamOwner) {
            DB.addToDB(details.get(0), details.get(1), details.get(2), null, null);
            return true;
        }
        return false;
    }

    /**
     * the function checks if the referee exists in the system
     *
     * @param username
     * @return
     */
    public boolean containsReferee(String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Referee) {
            return true;
        }
        return false;
    }

    /**
     * a functions that returns the referee from the DB
     *
     * @param username
     * @return
     */
    public Referee getRefereeFromDB(String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof Referee) {
            return (Referee) subscriber;
        }
        return null;
    }

    public ArrayList<String> getAllCoachesNames() {
        connectToSubscriberDB();
        return DB.selectAllRecords(UserTypes.COACH, null).get(0).get("coaches");

    }

    /**
     * checks if the Association Representative exists in the DB
     *
     * @param username
     * @return
     */
    public boolean containsInSystemAssociationRepresentative(String username) {
        connectToSubscriberDB();
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof AssociationRepresentative) {
            return true;
        }
        return false;
    }

    //-------------------TeamOwner--------------------//

    /**
     * the function approves the request by the AR and updates the new team in the system and in the team owner
     *
     * @param teamName the name of the team
     * @param username the subscriber who tries to confirm the request
     * @return true if it done successfully
     */
    public boolean confirmTeamByAssociationRepresentative(String teamName, String username) {
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof AssociationRepresentative) {
            connectToUnconfirmedTeamsDB();
            if (DB.containInDB(teamName, null, null)) {
                //check that a team with a same name doesn't exist
                connectToTeamDB();
                if (!DB.containInDB(teamName, null, null)) {
                    connectToUnconfirmedTeamsDB();
                    Map<String, ArrayList<String>> requestDetails = DB.selectFromDB(teamName, null, null);
                    LinkedList<String> request = new LinkedList<>();
                    request.add(0, requestDetails.get("teamID").get(0));
                    request.add(1, requestDetails.get("year").get(0));
                    request.add(2, requestDetails.get("owner").get(0));
                    //checks that the user who wrote the request exists
                    connectToSubscriberDB();
                    if (DB.containInDB(request.get(2), null, null)) {
                        Subscriber teamOwner = getSubscriberByUserName(request.get(2));
                        //checks that the user is a team owner
                        if (teamOwner instanceof TeamOwner) {
                            int year = Integer.parseInt(request.get(1));
                            Team team = new Team(teamName, (TeamOwner) teamOwner, year);
                            connectToTeamDB();
                            addTeam(team);
                            connectToUnconfirmedTeamsDB();
                            DB.removeFromDB(teamName, null, null);
                            ((TeamOwner) teamOwner).getTeams().add(team);
                            //updates the structure of the updated subscriber with the team
                            connectToSubscriberDB();
                            DB.removeFromDB(teamName, null, null);
                            addSubscriber(teamOwner);
                            //todo update the db
                            addTeamToOwner(teamOwner.getUsername(), team.getTeamName());
                            //todo update the db
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
     *
     * @param playerName
     * @return
     */
    public boolean checkUserExists(String playerName) {
        connectToSubscriberDB();
        return DB.containInDB(playerName, null, null);
    }

    /**
     * brings back a subscriber from the data base if he exists in the system
     *
     * @param username
     * @return
     */
    public Subscriber selectUserFromDB(String username) {
        if (checkUserExists(username)) {
            return getSubscriberByUserName(username);
        }
        return null;
    }

    /**
     * add a subscriber to the DB
     *
     * @param username
     * @param subscriber
     * @return
     */
    public boolean addSubscriberToDB(String username, Subscriber subscriber) {
        return addSubscriber(subscriber);
    }


    /**
     * this function find the player according to is user name and return it if the player exist in the system
     *
     * @param username the user name of the player
     * @return the player
     */
    public Player findPlayer(String username) {
        Subscriber sub = getSubscriberByUserName(username);
        if (sub instanceof Player) {
            Player p = (Player) sub;
            //if(p.isAssociated())
            return p;
        } else {
            return null;
        }
    }

    /**
     * the function checks if the DB contains the league
     *
     * @param leagueID
     * @return
     */
    public boolean containsLeague(String leagueID) {
        connectToLeagueDB();
        return DB.containInDB(leagueID, null, null);
    }

    /**
     * the function returns the league value from DB
     *
     * @param leagueID
     * @return
     */
    public League getLeagueFromDB(String leagueID) {
        connectToLeagueDB();
        Map<String, ArrayList<String>> details = DB.selectFromDB(leagueID, null, null);
        if (details != null) {
            League league = new League(leagueID);
            return league;
        }
        return null;
    }

    /**
     * add new league to the DB
     *
     * @param leagueID
     * @return
     */
    public boolean addLeagueToDB(String leagueID) {
        connectToLeagueDB();
        return DB.addToDB(leagueID, null, null, null, null);
    }


    /**
     * this function find the TeamManager according to is user name and return it if the TeamManager exist in the system
     *
     * @param assetUserName the user name of the TeamManager
     * @return the TeamManager
     */
    public TeamManager findTeamManager(String assetUserName) {
        Subscriber sub = getSubscriberByUserName(assetUserName);
        if (sub instanceof TeamManager) {
            TeamManager teamM = (TeamManager) sub;
            //if(p.isAssociated())
            return teamM;
        } else {
            return null;
        }
    }

    /**
     * this function find the Coach according to is user name and return it if the Coach exist in the system
     *
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

    public Stadium getStadiumByID(String stadiumID) {
        connectToStadiumDB();
        Map<String, ArrayList<String>> stadium = DB.selectFromDB(stadiumID, null, null);
        HashMap<String, Team> owners = new HashMap<>();
  /*      for (String str : stadium.get("teams")) {
            owners.put(str, getTeamByName(str));
        }*/

        return new Stadium(stadiumID, null
                , Integer.parseInt(stadium.get("numOfSeats").get(0))
                , owners);
    }

    public Stadium findStadium(String assetUserName) {
        connectToStadiumDB();
        if (DB.containInDB(assetUserName, null, null)) {
            return getStadiumByID(assetUserName);
        }
        return null;
    }

    /**
     * return a default stadium to the matches policies
     *
     * @return
     */
    public Stadium findDefaultStadium() {
        int totalStadiums = DB.countRecords();
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
        connectToSubscriberDB();
        Subscriber sub = null;
        if (DB.containInDB(userName, null, null)) {
            //connectToSubscriberDB();
            Map<String, ArrayList<String>> subDetails = DB.selectFromDB(userName, null, null);
            String type = subDetails.get("type").get(0);
            if (type.equalsIgnoreCase("player")) {
                sub = new Player(userName, subDetails.get("password").get(0)
                        , subDetails.get("name").get(0), subDetails.get("birthDay").get(0)
                        , FIELDJOB.valueOf(subDetails.get("fieldJob").get(0)),
                        Integer.parseInt(subDetails.get("salary").get(0))
                        , /*getTeamByName(subDetails.get("teamID").get(0))*/null, this);

            }

            if (type.equalsIgnoreCase("coach")) {
                sub = new Coach(userName, subDetails.get("password").get(0)
                        , subDetails.get("name").get(0)
                        , RoleInTeam.valueOf(subDetails.get("roleInTeam").get(0))
                        , TRAINING.valueOf(subDetails.get("training").get(0))
                        , "coach"
                        , Integer.parseInt(subDetails.get("salary").get(0))
                        , this);
                for (String str : subDetails.get("teams")) {
                    Team team = /*getTeamByName(str);*/ null;
                    ((Coach) sub).addTeam(team);
                }
            }
            if (type.equalsIgnoreCase("TEAMMANAGER")) {
                sub = new TeamManager(userName, subDetails.get("password").get(0)
                        , subDetails.get("name").get(0)
                        ,/* getTeamByName(subDetails.get("teamID").get(0))*/null
                        , Integer.parseInt(subDetails.get("salary").get(0))
                        , this);
            }

            if (type.equalsIgnoreCase("teamowner")) {
                sub = new TeamOwner(userName, subDetails.get("password").get(0)
                        , subDetails.get("name").get(0)
                        , this);
                if (subDetails.get("teams") != null) {
                    for (String str : subDetails.get("teams")) {
                        ((TeamOwner) sub).addTeam(/*getTeamByName(str)*/null);
                    }
                }
                if (subDetails.get("ownerAssigned") != null) {
                    for (int i = 0; i < subDetails.get("ownerAssigned").size(); i++) {
                        Team team = /*getTeamByName(subDetails.get("ownerTeam").get(i));*/ null;
                        TeamOwner to = (TeamOwner) getSubscriberByUserName(subDetails.get("ownerAssigned").get(0));
                        ((TeamOwner) sub).addAssignedOwner(team, to);
                    }
                }
                if (subDetails.get("managersAssigned") != null) {
                    for (int i = 0; i < subDetails.get("managersAssigned").size(); i++) {
                        Team team = /*getTeamByName(subDetails.get("managerTeam").get(i));*/ null;
                        TeamManager TM = (TeamManager) getSubscriberByUserName(subDetails.get("managersAssigned").get(0));
                        ((TeamOwner) sub).addAssignedManager(team, TM);
                    }
                }

            }
            if (type.equalsIgnoreCase("admin")) {
                sub = new Admin(userName, subDetails.get("password").get(0)
                        , subDetails.get("name").get(0)
                        , this);
            }

            if (type.equalsIgnoreCase("AR")) {
                sub = new AssociationRepresentative(userName, subDetails.get("password").get(0)
                        , subDetails.get("name").get(0)
                        , this, leagueController);
            }
            if (type.equalsIgnoreCase("referee")) {
                sub = new Referee(userName, subDetails.get("password").get(0)
                        , subDetails.get("name").get(0)
                        , roleRef.valueOf(subDetails.get("roleRef").get(0))
                        , getLeagueController()
                        , this);
                HashMap<Integer, Match> mapMatch = new HashMap<>();
                for (String str : subDetails.get("matches")) {
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
        if (DB.containInDB(teamName, null, null)) {
            Map<String, ArrayList<String>> teamDetails = DB.selectFromDB(teamName, null, null);
            HashSet<Player> players = new HashSet<>();
            HashSet<Coach> coaches = new HashSet<>();
            HashSet<Match> matches = new HashSet<>();
            HashSet<TeamOwner> teamOwners = new HashSet<>();
            HashSet<Season> seasons = new HashSet<>();
            for (String str : teamDetails.get("players")) {
                players.add((Player) getSubscriberByUserName(str));
            }
            for (String str : teamDetails.get("coaches")) {
                coaches.add((Coach) getSubscriberByUserName(str));
            }
            /*//todo need to be implemented in next iteration
            for (String str : teamDetails.get("matches")) {
                matches.add(findMatch(Integer.parseInt(str)));
            }
            */
            for (String str : teamDetails.get("ownerID")) {
                teamOwners.add((TeamOwner) getSubscriberByUserName(str));
            }
  /*          for (int i = 0; i < teamDetails.get("seasons").size(); i++) {
                seasons.add(selectSeasonFromDB(teamDetails.get("seasons").get(i),
                        teamDetails.get("leagues").get(i)));
            }*/
            Stadium stadium = findStadium(teamDetails.get("stadium").get(0));
            TeamManager TM = (TeamManager) getSubscriberByUserName(teamDetails.get("teamManagerID").get(0));
            return new Team(players, coaches, TM
                    , teamOwners, new FinancialMonitoring(null), matches, seasons
                    , stadium, teamName
                    , Integer.parseInt(teamDetails.get("establishedYear").get(0))
                    , Boolean.valueOf(teamDetails.get("isActive").get(0))
                    , Boolean.valueOf(teamDetails.get("closedByAdmin").get(0)));
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
 /*   public Boolean createRegistrationForm(Guest guest) {
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
        DB.addSubscriberToDB(userNameInput, newFan);

        return false;
    }*/

    /**
     * add stadium to stadiumDB
     *
     * @param stadium
     */
    public boolean addStadium(Stadium stadium) {
        connectToStadiumDB();
        if (stadium != null) {
            Map<String, ArrayList<String>> stadiumDetails = new HashMap<>();
            ArrayList<String> teams = new ArrayList<>();
            for (Map.Entry<String, Team> owners : stadium.getOwners().entrySet()) {
                teams.add(owners.getKey());
            }

            stadiumDetails.put("teams", teams);

            return DB.addToDB(stadium.getName(), String.valueOf(stadium.getNumberOfSeats())
                    , String.valueOf(stadium.getTicketCost()), null, stadiumDetails); //todo for IDO please check if you can use my function
        }
        return false;
    }

    // -------------------AssociationRepresentative--------------------//

    /**
     * the function adds
     *
     * @param nameStadium
     * @param numberOfSeats
     * @return
     */
    public boolean addNewStadium(String nameStadium, String numberOfSeats) {
        connectToStadiumDB();
        if (!DB.containInDB(nameStadium, null, null)) {
            int numOfSeats = Integer.parseInt(numberOfSeats);
            Stadium stadium = new Stadium(nameStadium, numOfSeats);
            return addStadium(stadium);
        }
        return false;
    }

    /**
     * UC-6.6 - enable team status by Team Owner todo-write tests
     *
     * @param teamName the name of the team from input
     * @param userName the user who wants to enable the team status
     * @return true if the team's status has been enabled.
     * false else.
     */
    public Boolean enableTeamStatus(String teamName, String userName) {
        if (userName == null || teamName == null) {
            return false;
        }
        connectToSubscriberDB();
        if (!DB.containInDB(userName, null, null)) {
            return false;
        }
        connectToTeamDB();
        if (!DB.containInDB(teamName, null, null)) {
            return false;
        }
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if (possibleTeamOwner instanceof TeamOwner) { //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner) possibleTeamOwner);
            if (teamOwner.getTeam(teamName) != null) { //check if the team owner owns the team
                return teamOwner.enableStatus(teamOwner.getTeam(teamName));
            } else {
                return false; //the team owner doesn't own the team
            }
        } else if (possibleTeamOwner instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.enableStatus(teamOwner.getTeam(teamName));
            } else
                return false;
        } else {
            return false; //the user isn't a team owner
        }
    }

    /**
     * UC-6.6 - disable team status by Team Owner todo-write tests
     *
     * @param teamName the name of the team from input
     * @param userName the user who wants to disable the team status
     * @return true if the team's status has been disabled.
     * false else.
     */
    public Boolean disableTeamStatus(String teamName, String userName) {
        if (userName == null || teamName == null) {
            throw new MissingInputException("Please select a team to close.");
        }
        connectToSubscriberDB();
        if (!DB.containInDB(userName, null, null)) {
            return false;
        }
        connectToTeamDB();
        if (!DB.containInDB(teamName, null, null)) {
            return false;
        }
        connectToSubscriberDB();
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if (possibleTeamOwner instanceof TeamOwner) { //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner) possibleTeamOwner);
            if (teamOwner.getTeam(teamName) != null) { //check if the team owner owns the team
                return teamOwner.disableStatus(teamOwner.getTeam(teamName));
            } else {
                return false; //the team owner doesn't own the team
            }
        } else if (possibleTeamOwner instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.disableStatus(teamOwner.getTeam(teamName));
            } else
                return false;
        } else {
            return false; //the user isn't a team owner
        }
    }

    /**
     * //UC-6.2
     *
     * @param teamName    the team's name of the team which the user wants to add to it's owners
     * @param newUserName the new team owner's user name
     * @param userName    the user which wants to add the user newUserName the the team owners
     * @return true if newUserName was added to the team's owners
     * false else
     */
    public Boolean appoinTeamOwnerToTeam(String teamName, String newUserName, String userName) {
        if (userName == null || teamName == null || newUserName == null) {
            return false;
        }
        connectToSubscriberDB();
        if (!DB.containInDB(userName, null, null)) {
            return false;
        }
        connectToTeamDB();
        if (!DB.containInDB(teamName, null, null)) {
            return false;
        }
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if (possibleTeamOwner instanceof TeamOwner) { //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner) possibleTeamOwner);
            if (teamOwner.enterMember(newUserName) != null) {
                return teamOwner.appointToOwner(teamOwner.enterMember(newUserName), teamName);
            } else //There is no such user with the user name of 'newUserName' in the system
                return false;
        } else if (possibleTeamOwner instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.appointToOwner(teamOwner.enterMember(newUserName), teamName);
            } else
                return false;
        } else {
            return false; //the user isn't a team owner
        }
    }

    public Boolean removeOwnerFromTeam(String userName, String teamName, String newUserName) {
        if (userName == null || teamName == null || newUserName == null) {
            return false;
        }
        connectToSubscriberDB();
        if (!DB.containInDB(userName, null, null)) {
            return false;
        }
        connectToTeamDB();
        if (!DB.containInDB(teamName, null, null)) {
            return false;
        }
        Subscriber possibleTeamOwner = getSubscriberByUserName(userName);
        if (possibleTeamOwner instanceof TeamOwner) { //check if the user is a team owner
            TeamOwner teamOwner = ((TeamOwner) possibleTeamOwner);
            if (teamOwner.enterMember(newUserName) != null) {
                return teamOwner.removeOwner(teamOwner.enterMember(newUserName), teamName);
            } else //There is no such user with the user name of 'newUserName' in the system
                return false;
        } else if (possibleTeamOwner instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) possibleTeamOwner;
            if (ownerEligible.isOwner()) {
                TeamOwner teamOwner = ownerEligible.getTeamOwner();
                return teamOwner.removeOwner(teamOwner.enterMember(newUserName), teamName);
            } else
                return false;
        } else {
            return false; //the user isn't a team owner
        }
    }


    /**
     * finds a match in the DB
     *
     * @param matchID
     * @return
     */
    public Match findMatch(int matchID) {
        connectToMatchDB();
        Map<String, ArrayList<String>> details = DB.selectFromDB(String.valueOf(matchID), null, null);
        if (details != null) {
            connectToTeamDB();
            Team home = getTeamByName(details.get("homeTeam").get(0));
            Team away = getTeamByName(details.get("awayTeam").get(0));
            League league = getLeagueFromDB(details.get("leagueID").get(0));
            connectToSeasonDB();
            Stadium stadium = getStadiumByID(details.get("stadium").get(0));
            connectToSeasonDB();
            //Season season = selectSeasonFromDB(league.getLeagueName(), details.get("seasonID").get(0));
            String scoreString = details.get("score").get(0);
            String[] arr = scoreString.split(":");
            int[] score = new int[2];
            score[0] = Integer.parseInt(arr[0]);
            score[1] = Integer.parseInt(arr[1]);
            connectToSubscriberDB();
            //Referee mainReferee = (Referee) selectUserFromDB(details.get("mainRef").get(0));
            String mainReferee = details.get("mainRef").get(0);
            ArrayList<String> refID = details.get("allRefs");
            LinkedList<String> refs = new LinkedList<>();
            if(refID != null) {
                for (String allRefID : refID) {
                    refs.add(allRefID);
                }
            }
            int numOfFans = Integer.parseInt(details.get("numberOFFans").get(0));
            connectToEventRecordDB();
            EventRecord eventRecord = selectEventRecord(matchID);
            boolean isFinished = Boolean.valueOf(details.get("isFinished").get(0));
            Match match = new Match(league, null, home, away, refs, score, null, isFinished, stadium, numOfFans, eventRecord, mainReferee);
            return match;
        }
        return null;
    }

    public EventRecord selectEventRecord(int matchID) {
        connectToEventRecordDB();
        Map<String, ArrayList<String>> details = DB.selectFromDB(String.valueOf(matchID), null, null);
        connectToMatchDB();
        ////Match match = findMatch(matchID);
        EventRecord eventRecord = new EventRecord(matchID);
        connectToEventDB();
        for (Map.Entry<String, ArrayList<String>> arr : details.entrySet()) {
            Event event = selectEvent(Integer.parseInt(arr.getValue().get(0)), arr.getValue().get(1), Integer.parseInt(arr.getValue().get(2)));
            eventRecord.addEvent(arr.getValue().get(1), event);
        }
        return eventRecord;
    }

    public Event selectEvent(int matchID, String time, int eventID) {
        connectToEventDB();
        String type = "";
        Map<String, ArrayList<String>> details = DB.selectFromDB(String.valueOf(matchID), time, String.valueOf(eventID));

        if (details.get("type").get(0).equals("goal")) {
            Player playerG = (Player) getSubscriberByUserName(details.get("playerG").get(0));
            Player playerA = (Player) getSubscriberByUserName(details.get("playerA").get(0));
            boolean isOwnGoal = Boolean.valueOf(details.get("isOwnGoal").get(0));
            return new Goal(playerG, playerA, isOwnGoal, matchController);
        } else if (details.get("type").get(0).equals("yellowcard")) {
            Player player = (Player) getSubscriberByUserName(details.get("player").get(0));
            return new YellowCard(player, matchController);
        } else if (details.get("type").get(0).equals("redcard")) {
            Player player = (Player) getSubscriberByUserName(details.get("player").get(0));
            return new RedCard(player, matchController);
        } else if (details.get("type").get(0).equals("offside")) {
            Player player = (Player) getSubscriberByUserName(details.get("player").get(0));
            return new Offside(player, matchController);
        } else if (details.get("type").get(0).equals("injury")) {
            Player player = (Player) getSubscriberByUserName(details.get("player").get(0));
            return new Injury(player, matchController);
        } else if (details.get("type").get(0).equals("foul")) {
            Player playerA = (Player) getSubscriberByUserName(details.get("playerA").get(0));
            Player playerF = (Player) getSubscriberByUserName(details.get("playerF").get(0));
            return new Foul(playerA, playerF, matchController);
        } else if (details.get("type").get(0).equals("sub")) {
            Player playerON = (Player) getSubscriberByUserName(details.get("playerON").get(0));
            Player playerOff = (Player) getSubscriberByUserName(details.get("playerOff").get(0));
            return new Substitute(playerON, playerOff, matchController);
        }
        return null;
    }

    public boolean addEvent(int matchID, String time, int eventID, Event event) {
        HashMap<String, ArrayList<String>> details = new HashMap<>();
        String type = "";
        if (event instanceof Goal) {
            type = "goal";
            details.put("playerG", new ArrayList<>(Arrays.asList(event.getFirstPlayer().getUsername())));
            details.put("playerA", new ArrayList<>(Arrays.asList(((Goal) event).getSecondPlayer().getUsername())));
            String isOwnGoal = String.valueOf(((Goal) event).isOwnGoal());
            details.put("isOwnGoal", new ArrayList<>(Arrays.asList(((isOwnGoal)))));
        } else if (event instanceof YellowCard) {
            type = "yellowcard";
            details.put("player", new ArrayList<>(Arrays.asList(event.getFirstPlayer().getUsername())));
        } else if (event instanceof RedCard) {
            type = "redcard";
            details.put("player", new ArrayList<>(Arrays.asList(event.getFirstPlayer().getUsername())));
        } else if (event instanceof Offside) {
            type = "offside";
            details.put("player", new ArrayList<>(Arrays.asList(event.getFirstPlayer().getUsername())));
        } else if (event instanceof Injury) {
            type = "injury";
            details.put("player", new ArrayList<>(Arrays.asList(event.getFirstPlayer().getUsername())));
        } else if (event instanceof Foul) {
            type = "foul";
            details.put("playerA", new ArrayList<>(Arrays.asList(event.getFirstPlayer().getUsername())));
            details.put("playerF", new ArrayList<>(Arrays.asList(((Foul) event).getSecondPlayer().getUsername())));
        } else if (event instanceof Substitute) {
            type = "sub";
            details.put("playerIn", new ArrayList<>(Arrays.asList(event.getFirstPlayer().getUsername())));
            details.put("playerOut", new ArrayList<>(Arrays.asList(((Substitute) event).getSecondPlayer().getUsername())));
        }
        connectToEventDB();
        DB.addToDB(String.valueOf(matchID), time, String.valueOf(eventID), type, details);
        return false;
    }

    /**
     * Login UC-2.3
     *
     * @param userName the User Name as the user's input
     * @param password the Password as the user's input
     * @return the user type if there is a Subscriber in the DB with the @userName and the @password
     * null - else, or one of the inputs are null
     */
    public String enterLoginDetails(String userName, String password) {

        if (userName == null || password == null || userName.equals("") || password.equals("")) {
            throw new MissingInputException("Missing Input");
            //return null;
        }

        Subscriber subscriber = selectUserFromDB(userName);

        if (subscriber == null)
            throw new NotFoundInDbException("No such user in the data base.");
        //return null;

        String s = String.valueOf(password.hashCode());
        if (subscriber.getPassword().equals(s)) {
            if (subscriber instanceof Admin) {
                Admin userCheckIfApproved = ((Admin) subscriber);
                if (userCheckIfApproved.isApproved() == false) {
                    throw new NotApprovedException("You are trying to log in as an unapproved Admin. You have to be approved first by another Admin to log in.");
                    //return null;
                }
            } else if (subscriber instanceof AssociationRepresentative) {
                AssociationRepresentative userCheckIfAprroved = ((AssociationRepresentative) subscriber);
                if (userCheckIfAprroved.isApproved() == false) {
                    throw new NotApprovedException("You are trying to log in as an unapproved AR. You have to be approved first by an Admin to log in.");
                    //return null;
                }
            }
            return subscriber.toString();
        }
        throw new NotApprovedException("Wrong password. Please try to login again.");
        //return null;
    }


    // -------------------Guest--------------------//

    /**
     * Registration for player:
     * Creates a new player in the DB
     *
     * @param userName  the user name of the subscriber
     * @param password  the password of the subscriber
     * @param name      the name of the player
     * @param birthDate the player's date of birth
     * @param fieldJob  the field job of the player
     * @param teamName  the team name of the player
     * @return true if the new player was created successfully in the DB
     * false else
     */
    public boolean enterRegisterDetails_Player(String userName, String password, String name, String birthDate, String fieldJob, String teamName) {
        if (userName == null || password == null || name == null || birthDate == null || fieldJob == null || teamName == null) {
            return false;
        }
        if (validateUserName(userName)) {
            return false;
        }
        if (checkPasswordStrength(password, userName) == false) {
            return false;
        }
        Subscriber subscriber = selectUserFromDB(userName);
        if (subscriber != null) //user name is already exists in the database
            return false;
        Team team = getTeamByName(teamName);
        if (team == null) { //no such team in the DB
            return false;
        }
        Subscriber newPlayer = new Player(userName, password, name, birthDate, FIELDJOB.valueOf(fieldJob), 0, team, this);
        addSubscriber(newPlayer);
        return true;
    }

    /**
     * Registration for Coach:
     * Creates a new coach in the DB
     *
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name     the name of the coach
     * @param training the training of the new coach
     * @param teamJob  the team job of the new coach
     * @return true if the new coach was created successfully in the DB
     * false else
     */
    public boolean enterRegisterDetails_Coach(String userName, String password, String name, String roleInTeam, String training, String teamJob) {
        if (userName == null || password == null || name == null || training == null || teamJob == null) {
            return false;
        }
        if (validateUserName(userName)) {
            return false;
        }
        if (checkPasswordStrength(password, userName) == false) {
            return false;
        }
        if (checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newCoach = new Coach(userName, password, name, RoleInTeam.valueOf(roleInTeam), TRAINING.valueOf(training), teamJob, 0, this);
        addSubscriber(newCoach);
        return true;
    }

    /**
     * Registration for Team Owner:
     * Creates a new team owner in the DB
     *
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name     the name of the team owner
     * @return true if the new team owner was created successfully in the DB
     * false else
     */
    public boolean enterRegisterDetails_TeamOwner(String userName, String password, String name) {
        if (userName == null || password == null || name == null) {
            return false;
        }
        if (validateUserName(userName)) {
            return false;
        }
        if (checkPasswordStrength(password, userName) == false) {
            return false;
        }
        if (checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newTeamOwner = new TeamOwner(userName, password, name, this);
        addSubscriber(newTeamOwner);
        return true;
    }

    /**
     * Registration for Team Manager:
     * Creates a new team manager in the DB
     *
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name     the name of the team manager
     * @param teamName the team name of the team owner
     * @return true if the new team manager was created successfully in the DB
     * false else
     */
    public boolean enterRegisterDetails_TeamManager(String userName, String password, String name, String teamName) {
        if (userName == null || password == null || name == null || teamName == null) {
            return false;
        }
        if (validateUserName(userName)) {
            return false;
        }
        if (checkPasswordStrength(password, userName) == false) {
            return false;
        }
        if (checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Team team = getTeamByName(teamName);
        if (team == null) { //no such team in the DB
            return false;
        }
        Subscriber newTeamManager = new TeamManager(userName, password, name, team, 0, this);
        addSubscriber(newTeamManager);
        return true;
    }

    /**
     * Registration for Admin:
     * Creates a new Admin in the DB
     *
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name     the name of the admin
     * @return true if the new admin was created successfully in the DB
     * false else
     */
    public boolean enterRegisterDetails_Admin(String userName, String password, String name) {
        if (userName == null || password == null || name == null) {
            return false;
        }
        if (validateUserName(userName)) {
            return false;
        }
        if (checkPasswordStrength(password, userName) == false) {
            return false;
        }
        if (checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newAdmin = new Admin(userName, password, name, this);
        addSubscriber(newAdmin);
        addAdminApprovalRequest(userName, newAdmin);
        return true;
    }

    /**
     * Registration for AR:
     * Creates a new AR in the DB
     *
     * @param userName the user name of the subscriber
     * @param password the password of the subscriber
     * @param name     the name of the AR
     * @return true if the new AR was created successfully in the DB
     * false else
     */
    public boolean enterRegisterDetails_AssociationRepresentative(String userName, String password, String name) {

        if (userName == null || password == null || name == null) {
            return false;
        }
        if (validateUserName(userName)) {
            return false;
        }
        if (checkPasswordStrength(password, userName) == false) {
            return false;
        }
        if (checkIfUserNameExistsInDB(userName)) //user name is already exists in the database
            return false;
        Subscriber newAssociationRepresentative = new AssociationRepresentative(userName, password, name, this, leagueController);
        addSubscriber(newAssociationRepresentative);
        addAdminApprovalRequest(userName, newAssociationRepresentative);
        return true;
    }

    /**
     * @param userName the user name to be checked
     * @return true if the user name exists in the DB
     * false else
     */
    private boolean checkIfUserNameExistsInDB(String userName) {
        Subscriber subscriber = selectUserFromDB(userName);

        if (subscriber != null) //user name is already exists in the database
            return true;

        return false;
    }


    /**
     * This function handles the operation of approving a new AR or Admin user by an already-approved admin.
     *
     * @param userName          the user name of the user which approves
     * @param userNameToApprove the user name of the user which is being approved
     * @param approve           = true, disapprove = false
     * @return true if the userNameToApprove was approved/disapproved by userName
     * false else
     */
    public boolean handleAdminApprovalRequest(String userName, String userNameToApprove, boolean approve) {
        Subscriber approved = selectUserFromDB(userName);
        if (!(approved instanceof Admin)) {
            return false;
        }
        Admin adminApproved = ((Admin) approved);
        return adminApproved.approveAdminRequest(userNameToApprove, approve);
    }

    public boolean removeAdminRequest(String userNameToApprove) {
        //DB.removeAdminRequest(userNameToApprove);fixme take out of comment
        return true;
    }


    /**
     * function that asks from the DB to get a Season
     *
     * @param leagueID
     * @param seasonID
     * @return
     */
    public Season selectSeasonFromDB(String leagueID, String seasonID) {
        connectToSeasonDB();
        Map<String, ArrayList<String>> details = DB.selectFromDB(leagueID, String.valueOf(seasonID), null);
        ArrayList<String> matchesString = details.get("matches");
        HashMap<Integer, Match> matches = new HashMap<>();
        connectToMatchDB();
        for (String matchID : matchesString) {
            Match match = findMatch(Integer.parseInt(matchID));
            matches.put(match.getMatchId(), match);
        }
        ArrayList<String> refString = details.get("referees");
        HashMap<String, Referee> referees = new HashMap<>();
        connectToSubscriberDB();
        for (String refereeID : refString) {
            Subscriber ref = getSubscriberByUserName(refereeID);
            referees.put(ref.getUsername(), (Referee) ref);
        }
        ArrayList<String> teamsString = details.get("teams");
        HashMap<String, Team> teams = new HashMap<>();
        connectToTeamDB();
        for (String teamID : teamsString) {
            Team team = getTeamByName(teamID);
            teams.put(teamID, team);
        }
        ArrayList<String> tableLeagueString = details.get("table");
        HashMap<Team, LinkedList<Integer>> leagueTable = new HashMap<>();
        connectToSeasonDB();
        for (int i = 0; i < tableLeagueString.size(); i = i + 5) {
            Team team = teams.get(tableLeagueString.get(i));
            LinkedList<Integer> teamDetail = new LinkedList<>();
            teamDetail.add(Integer.parseInt(tableLeagueString.get(i + 1)));
            teamDetail.add(Integer.parseInt(tableLeagueString.get(i + 2)));
            teamDetail.add(Integer.parseInt(tableLeagueString.get(i + 3)));
            teamDetail.add(Integer.parseInt(tableLeagueString.get(i + 4)));
            leagueTable.put(team, teamDetail);
        }
        ArrayList<String> rankingPolicyString = details.get("rankingPolicy");
        ARankingPolicy rankingPolicy = new ARankingPolicy(Integer.parseInt(rankingPolicyString.get(2)),
                Integer.parseInt(rankingPolicyString.get(3)), Integer.parseInt(rankingPolicyString.get(4)));

        String matchingPolicy = details.get("matchingPolicy").get(0);
        Date start = new Date();
        Date end = new Date();
        //public Season(int seasonId, Date startDate, Date endDate, League league, int win, int lose, int tie, String matchingPolicy)
        connectToLeagueDB();
        League league = getLeagueFromDB(leagueID);
        Season season = new Season(league, Integer.parseInt(seasonID), start, end, rankingPolicy, leagueTable, matches, referees, matchingPolicy,teams);
        return season;
    }


    public boolean sendRequestForTeam(String teamName, String establishedYear, String username) {
        connectToSubscriberDB();
        Subscriber subscriber = getSubscriberByUserName(username);
        if (subscriber instanceof TeamOwner) {
            if (tryParseInt(establishedYear)) {
                connectToTeamDB();
                Team team = getTeamByName(teamName);
                if (team == null) {
                    LinkedList<String> details = new LinkedList<>();
                    details.add(teamName);
                    details.add(establishedYear);
                    details.add(username);
                    connectToUnconfirmedTeamsDB();
                    return DB.addToDB(teamName, details.get(1), details.get(2), null, null);

                }
            }
        }
        return false;
    }

    protected boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * the function updates the referee ID and attach it to the season in the DB
     *
     * @param leagueID
     * @param seasonID
     * @param refereeID
     * @return
     */
    public boolean addRefereeToSeason(String leagueID, int seasonID, String refereeID) {
        HashMap<String, String> details = new HashMap<>();
        details.put("leagueID", leagueID);
        details.put("seasonID", String.valueOf(seasonID));
        details.put("refID", refereeID);
        return DB.update(SEASONENUM.REFEREE, details);
    }


    public boolean addTeamToSeasonDB(String leagueID, int seasonID, String teamID) {
        connectToSeasonDB();
        HashMap<String, String> details = new HashMap<>();
        details.put("leagueID", leagueID);
        details.put("seasonID", String.valueOf(seasonID));
        details.put("teamID", teamID);
        return DB.update(SEASONENUM.TEAM, details);
    }

    public boolean addMatchTableToSeason(String leagueID, int seasonID, LinkedList<Integer> matchNum) {
        connectToSeasonDB();
        HashMap<String, String> details = new HashMap<>();
        details.put("leagueID", leagueID);
        details.put("seasonID", String.valueOf(seasonID));
        details.put(String.valueOf(matchNum), String.valueOf(matchNum));
        return DB.update(SEASONENUM.MATCHESTABLE, details);
    }

    public boolean updateMatchTableOFSeason(String leagueID, int seasonID, String teamID, LinkedList<Integer> info) {
        connectToSeasonDB();
        HashMap<String, String> details = new HashMap<>();
        details.put("teamID", teamID);
        details.put("leagueID", leagueID);
        details.put("seasonID", String.valueOf(seasonID));
        details.put("numOfGames", String.valueOf(info.get(0)));
        details.put("goalsFor", String.valueOf(info.get(1)));
        details.put("goalAgainst", String.valueOf(info.get(2)));
        details.put("points", String.valueOf(info.get(3)));
        return DB.update(SEASONENUM.SEASONUPDATED, details);
    }


    public boolean addNewMatch(Match match, String leagueID, int seasonID) {
        connectToMatchDB();
        HashMap<String, ArrayList<String>> details = new HashMap<>();
        details.put("teamHome", new ArrayList<>(Arrays.asList(match.getHomeTeam().getTeamName())));
        details.put("teamAway", new ArrayList<>(Arrays.asList(match.getAwayTeam().getTeamName())));
        int[] scoreInt = match.getScore();
        String scoreString = scoreInt[0] + ":" + scoreInt[1];
        details.put("score", new ArrayList<>(Arrays.asList(scoreString)));
        details.put("date", new ArrayList<>(Arrays.asList(match.getDate().toString())));
        return DB.addToDB(leagueID, String.valueOf(seasonID), String.valueOf(match.getMatchId()), match.getStadium().getName(), details);
    }


    public boolean updateScore(String matchID, String score) {
        connectToMatchDB();
        HashMap<String, String> details = new HashMap<>();
        details.put("matchID", matchID);
        details.put("score", score);
        return DB.update(MATCHENUM.SCORE, details);
    }

    public boolean updateMainRefereeToMatch(String matchID, String refID) {
        connectToMatchDB();
        HashMap<String, String> details = new HashMap<>();
        details.put("refID", refID);
        details.put("matchID", matchID);
        return DB.update(MATCHENUM.MAINREFEREE, details);
    }

    public boolean updateNumOfFans(String matchID, int numOfFans) {
        connectToMatchDB();
        HashMap<String, String> details = new HashMap<>();
        details.put("numOfFans", String.valueOf(numOfFans));
        details.put("matchID", matchID);
        return DB.update(MATCHENUM.NUMBEROFFANS, details);
    }

    public boolean addRefereeToMatch(String matchID, String refID) {
        connectToMatchDB();
        HashMap<String, String> details = new HashMap<>();
        details.put("refID", refID);
        details.put("matchID", matchID);
        return DB.update(MATCHENUM.ADDREFEREE, details);
    }

    public LinkedList<String> getRefsOfMatch(int matchID) {
        //Match match = findMatch(matchID);
        connectToMatchDB();
        HashMap<String, String> args = new HashMap<>();
        args.put("matchID", String.valueOf(matchID));
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(MATCHENUM.ALLREFEREEOFGAME, args);
        LinkedList<String> refs = new LinkedList<>();
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                refs.add(temp.get(0));
            }
        }
        return refs;
    }


    public boolean addMatchTableOfSeason(HashMap<Integer, Match> matchesOfTheSeason, String leagueID, int seasonID) {
        LinkedList<Integer> matchID = new LinkedList<>();
        for (HashMap.Entry<Integer, Match> entry : matchesOfTheSeason.entrySet()) {
            Match match = entry.getValue();
            addNewMatch(match, leagueID, seasonID);
            matchID.add(match.getMatchId());
        }
        connectToSeasonDB();
        HashMap<String, String> args = new HashMap<>();
        for(int i=0;i<matchID.size();i++){
            args.put(String.valueOf(matchID.get(i)),String.valueOf(matchID.get(i)));
        }
        args.put("leagueID",leagueID);
        args.put("seasonID",String.valueOf(seasonID));
        DB.update(SEASONENUM.MATCHESTABLE, args);
        return addMatchTableToSeason(leagueID, seasonID, matchID);
    }


    public boolean updateApprovedAdmin(String isApproved, String adminID) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("adminID", adminID);
        arguments.put("isApproved", isApproved);
        return DB.update(SUBSCRIBERSUPDATES.ADMINSETAPPROVED, arguments);
    }

    public boolean setTeamToPlayer(String playerID, String teamID) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("teamID", teamID);
        arguments.put("playerID", playerID);
        return DB.update(SUBSCRIBERSUPDATES.SETTEAMTOPLAYER, arguments);
    }

    public boolean addPlayerToTeam(String playerID, String teamID) {
        connectToTeamDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("teamID", teamID);
        arguments.put("playerID", playerID);
        return DB.update(TEAMUPDATES.ADDPLAYER, arguments);
    }

    public boolean setTeamToTM(String managerID, String teamID) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("teamID", teamID);
        arguments.put("managerID", managerID);
        return DB.update(SUBSCRIBERSUPDATES.SETTEAMTOTM, arguments);
    }

    public boolean addManagerToOwner(String ownerID, String managerID, String teamID) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("ownerID", ownerID);
        arguments.put("teamID", teamID);
        arguments.put("managersAssigned", managerID);
        return DB.update(SUBSCRIBERSUPDATES.ADDMANAGERTOOWNER, arguments);
    }

    public boolean deleteManagerToOwner(String ownerID, String managerID, String teamID) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("ownerID", ownerID);
        arguments.put("teamID", teamID);
        arguments.put("managerID", managerID);
        return DB.update(SUBSCRIBERSUPDATES.DELETEMANAGERFROMOWNER, arguments);
    }

    public boolean setTMToTeam(String managerID, String teamID) {
        connectToTeamDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("teamID", teamID);
        arguments.put("managerID", managerID);
        return DB.update(TEAMUPDATES.SETTEAMMANAGER, arguments);
    }


    public boolean SetPlayerBirthdate(String playerID, String birthDate) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("birthDate", birthDate);
        arguments.put("playerID", playerID);
        return DB.update(SUBSCRIBERSUPDATES.SETPLAYERBIRTHDATE, arguments);
    }

    public boolean SetPlayerFieldJob(String playerID, String fieldJob) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("fieldJob", fieldJob);
        arguments.put("playerID", playerID);
        return DB.update(SUBSCRIBERSUPDATES.SETPLAYERFIELDJOB, arguments);
    }

    public boolean SetPlayerSalary(String playerID, String salary) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("salary", salary);
        arguments.put("playerID", playerID);
        return DB.update(SUBSCRIBERSUPDATES.SETPLAYERSALARY, arguments);
    }

    public boolean SetTMSalary(String managerID, String salary) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("salary", salary);
        arguments.put("managerID", managerID);
        return DB.update(SUBSCRIBERSUPDATES.SETTMSALARY, arguments);
    }

    public boolean SetTMPermissions(String managerID, String permissions) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("permissions", permissions);
        arguments.put("managerID", managerID);
        return DB.update(SUBSCRIBERSUPDATES.SETTMPERMISSIONS, arguments);
    }

    public boolean addTeamToOwner(String ownerID, String teamID) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("ownerID", ownerID);
        arguments.put("teamID", teamID);
        return DB.update(SUBSCRIBERSUPDATES.ADDTEAMTOOWNER, arguments);
    }


    public boolean setTeamActive(String teamID, String isActive) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("isActive", isActive);
        arguments.put("teamID", teamID);
        return DB.update(TEAMUPDATES.SETACTIVE, arguments);
    }

    /**
     * The function receives a user's username and a team's name, adds the user as a follower of the team's page and returns whether the operation was successful
     *
     * @param username
     * @param teamName
     * @return
     */
    public boolean allowUserToFollowTeam(String username, String teamName) {

        Subscriber user = selectUserFromDB(username);
        Page teamToFollow = getTeamPageByName(teamName);
        if (user == null || teamToFollow == null) {
            return false;
        }
        //return DB.addFollowerToPage(teamToFollow, username); //todo add table to db
        return false;
    }


    private void connectToSubscriberDB() {
        DB.TerminateDB();
        DB = new DBHandler();
    }

    private void connectToPageDB() {
        DB.TerminateDB();
        DB = new pageDB();
    }

    private void connectToTeamDB() {
        DB.TerminateDB();
        DB = new TeamDB();
    }

    private void connectToComplaintsDB() {
        DB.TerminateDB();
        DB = new ComplaintsDB();
    }

    private void connectToStadiumDB() {
        DB.TerminateDB();
        DB = new StadiumDB();
    }

    private void connectToUnconfirmedTeamsDB() {
        DB.TerminateDB();
        DB = new DBUnconfirmedTeams();
    }

    private void connectToMatchDB() {
        DB.TerminateDB();
        DB = new DBMatch();
    }

    private void connectToSeasonDB() {
        DB.TerminateDB();
        DB = new DBSeasons();
    }

    private void connectToLeagueDB() {
        DB.TerminateDB();
        DB = new DBLeagues();
    }

    private void connectToEventDB() {
        DB.TerminateDB();
        DB = new DBEvents();
    }

    private void connectToEventRecordDB() {
        DB.TerminateDB();
        DB = new EventRecordDB();
    }


    private LocalDate convertToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public boolean removeOwnerFromTeam(String teamID,String teamOwner){
        connectToTeamDB();
        Map<String,String> arguments = new HashMap<>();
        arguments.put("teamID",teamID);
        arguments.put("ownerID",teamOwner);

        DB.update(TEAMUPDATES.DELETEOWNER,arguments);
        return false;
    }



    public boolean removeOwner(String subscriberID,String setSubscriberID,String teamOwner,String setTeamOwner,String eligibleType){
        connectToSubscriberDB();
        Map<String,String> arguments = new HashMap<>();
        arguments.put("ownerID",teamOwner);
        arguments.put("setOwnerID",setTeamOwner);
        arguments.put("type",eligibleType);
        if(eligibleType.equalsIgnoreCase("player")){
            arguments.put("playerID",subscriberID);
            arguments.put("setPlayerID",setSubscriberID);
        }
        if(eligibleType.equalsIgnoreCase("coach")){
            arguments.put("coachID",subscriberID);
            arguments.put("setCoachID",setSubscriberID);
        }
        if(eligibleType.equalsIgnoreCase("teamManager")){
            arguments.put("managerID",subscriberID);
            arguments.put("setManagerID",setSubscriberID);
        }

        DB.update(SUBSCRIBERSUPDATES.REMOVEOWNER,arguments);
        return false;
    }

    public boolean deleteOwnerFromOwner(String teamID,String teamOwner,String assigneeID){

        connectToSubscriberDB();
        Map<String,String> arguments = new HashMap<>();
        arguments.put("teamID",teamID);
        arguments.put("ownerID",teamOwner);
        arguments.put("assigneeID",assigneeID);

        DB.update(SUBSCRIBERSUPDATES.DELETEOWNERFROMOWNER,arguments);
        return false;
    }

    public boolean setPlayerEligible(String ownerID,String playerID,String setPlayerID,String setOwnerID){

        connectToTeamDB();
        Map<String,String> arguments = new HashMap<>();
        arguments.put("ownerID",ownerID);
        arguments.put("playerID",playerID);
        arguments.put("setPlayerID",setPlayerID);
        arguments.put("setOwnerID",setOwnerID);

        DB.update(SUBSCRIBERSUPDATES.SETPLAYERELIGIBLE,arguments);
        return false;
    }

    public boolean setCoachEligible(String ownerID,String coachID,String setCoachID,String setOwnerID){

        connectToTeamDB();
        Map<String,String> arguments = new HashMap<>();
        arguments.put("ownerID",ownerID);
        arguments.put("coachID",coachID);
        arguments.put("setCoachID",setCoachID);
        arguments.put("setOwnerID",setOwnerID);

        DB.update(SUBSCRIBERSUPDATES.SETCOACHELIGIBLE,arguments);
        return false;
    }

    public boolean setTMEligible(String ownerID,String managerID,String setManagerID,String setOwnerID){

        connectToTeamDB();
        Map<String,String> arguments = new HashMap<>();
        arguments.put("ownerID",ownerID);
        arguments.put("managerID",managerID);
        arguments.put("setManagerID",setManagerID);
        arguments.put("setOwnerID",setOwnerID);

        DB.update(SUBSCRIBERSUPDATES.SETTMELIGIBLE,arguments);
        return false;
    }

    public boolean addOwnerToOwner(String ownerID,String assigneeID,String teamID){

        connectToSubscriberDB();
        Map<String,String> arguments = new HashMap<>();
        arguments.put("ownerID",ownerID);
        arguments.put("assigneeID",assigneeID);
        arguments.put("teamID",teamID);

        DB.update(SUBSCRIBERSUPDATES.ADDOWNERTOOWNER,arguments);
        return false;
    }

    /**
     * The function receives a user's username and a player's name, adds the user as a follower of the player's page and returns whether the operation was successful
     *
     * @param username
     * @param playerName
     * @return
     */
    public boolean allowUserToFollowPlayer(String username, String playerName) {

        Subscriber user = selectUserFromDB(username);
        Page playerToFollow = getPlayerPageByName(playerName);
        if (user == null || playerToFollow == null) {
            return false;
        }
        //return DB.addFollowerToPage(playerToFollow, username); //todo add table to DB
        return false;
    }

    /**
     * The function receives a user's username and a coach name, adds the user as a follower of the coach page and returns whether the operation was successful
     *
     * @param username
     * @param coachName
     * @return
     */
    public boolean allowUserToFollowCoach(String username, String coachName) {

        Subscriber user = selectUserFromDB(username);
        Page playerToFollow = getCoachPageByName(coachName);
        if (user == null || playerToFollow == null) {
            return false;
        }
        //return DB.addFollowerToPage(playerToFollow, username); //todo add table to db
        return false;
    }


    /**
     * The function receives a user's username and a match's identifier, adds the user as a follower of the match and returns whether the operation was successful
     *
     * @param username
     * @param matchID
     * @return
     */
    public boolean allowUserToFollowMatch(String username, String matchID) {

        Subscriber user = selectUserFromDB(username);
        int id = Integer.parseInt(matchID);
        Match match = findMatch(id);
        if (user != null && match != null) {
            //       return DB.addFollowerToMatch(match, username); todo add to db
        }
        return false;
    }

    /**
     * The function receives a team's name and returns the matching page to the name
     *
     * @param teamName
     * @return
     */
    public Page getTeamPageByName(String teamName) {
        if (teamName == null) {
            return null;
        }
        //  return DB.getTeamPageByName(teamName); todo add to db
        return null;
    }

    /**
     * The function receives a player's name and returns the matching page to the name
     *
     * @param playerName
     * @return
     */
    public Page getPlayerPageByName(String playerName) {
        return getSubscriberPage(playerName);
    }

    private Page getSubscriberPage(String subName) {
        if (subName == null) {
            return null;
        }
        connectToPageDB();
        Map<String, ArrayList<String>> objDetails = DB.selectFromDB(subName, null, null);

        Page page = new Page(objDetails.get("ownerID").get(0),
                objDetails.get("name").get(0),
                objDetails.get("birthDay").get(0),
                (HasPage) selectUserFromDB(objDetails.get("ownerID").get(0)));

        page.setPosts(new LinkedList<>(objDetails.get("posts")));

        return page;
    }

    /**
     * The function receives a coach name and returns the matching page to the name
     *
     * @param coachName
     * @return
     */
    public Page getCoachPageByName(String coachName) {
        return getSubscriberPage(coachName);
    }

    /**
     * The function receives a page, retrieves a list of its followers usernames and then updates each of them of the new event
     *
     * @param page
     */
    public void updatePageFollowers(Page page, String event) {
        /*
        if (page != null && event != null) {
            LinkedList<String> followers = DB.getPageFollowers(page); todo db add to db
            if (followers != null) {
                followers.add(event);
                followers.add("Page update");
                setChanged();
                notifyObservers(followers);
            }
        }
        */
    }


    /**
     * The function receives a page and the page's owner name and sends it to the DB
     *
     * @param name
     * @param page
     * @return true/false
     */
    public boolean addPageToDB(String name, Page page) {
        if (name == null || page == null) {
            return false;
        }
        connectToPageDB();
        Map<String, ArrayList<String>> objDetails = new HashMap<>();
        objDetails.put("posts", new ArrayList<>());
        for (String str : page.getPosts()) {
            objDetails.get("posts").add(str);
        }
        //todo ido put it on comma
        //DB.addToDB(name, String.valueOf(page.getPageID()), page.getbDate(), page.getName(), objDetails);
        return true;
    }


    /**
     * The function receives a match, retrieves a list of its followers usernames and updates each of them about the new event
     *
     * @param match
     * @param event
     */
    public void updateMatchToFollowers(Match match, String event) {
        /*
        if (match != null && event != null) {
            LinkedList<String> followers = DB.getMatchFollowers(match); todo add the db
            if (followers != null) {
                followers.add(event);
                followers.add("Match update");
                setChanged();
                notifyObservers(followers);
            }
        }
        */
    }

    /**
     * The function receives a match and a referee and sends them to the DB to be connected to each other
     *
     * @param match
     * @param ref
     * @return
     */
    public boolean addRefereeToMatch(Match match, Referee ref) {
        if (match != null && ref != null) {
            connectToMatchDB();
            Map<String, String> arguments = new HashMap<>();
            arguments.put("matchID", String.valueOf(match.getMatchId()));
            arguments.put("refID", ref.getName());
            return DB.update(MATCHENUM.ADDREFEREE, arguments);
        }
        return false;
    }

    /**
     * The function receives a referee's username and a match, verifies the initiation of the action is from an association representative and
     * returns whether the operation was successful or not
     *
     * @param username
     * @param matchID
     * @param refereeUsername
     * @return
     */
    public boolean addRefereeToMatchThroughRepresentative(String username, String matchID, String refereeUsername) {

        if (username == null || matchID == null || refereeUsername == null) {
            return false;
        }
        Subscriber user = getSubscriberByUserName(username);
        Subscriber userRef = getSubscriberByUserName(refereeUsername);
        int id = Integer.parseInt(matchID);
        Match match = findMatch(id);
        if (user instanceof AssociationRepresentative && userRef instanceof Referee && match != null) {
            return addRefereeToMatch(match, (Referee) userRef);
        }
        return false;
    }

    /**
     * The function receives a match, retrieves the referees of the match and notifies the observer about the changes
     *
     * @param match
     * @param event
     */
    public void updateMatchChangesToReferees(Match match, String event) {
        /*
        if (match != null && event != null) {
            LinkedList<String> followers = DB.getMatchReferees(match);
            if (followers != null) {
                followers.add(event);
                followers.add("Change in match date&place");
                setChanged();
                notifyObservers(followers);
            }
        }
        */ //todo add this function to the DB
    }


    /**
     * The function receives a coach and a team and binds them together in the database
     *
     * @param coach
     * @param team
     */
    public void addCoachToTeam(Coach coach, Team team) {

        if (coach != null && team != null) {
            connectToTeamDB();
            Map<String, String> arguments = new HashMap<>();
            arguments.put("coachID", coach.getName());
            arguments.put("teamID", team.getTeamName());
            DB.update(TEAMUPDATES.ADDCOACH, arguments);
        }
    }

    /**
     * The function receives a stadium and a team and binds them together in the database
     *
     * @param stadium
     * @param team
     */
    public void addStadiumToTeam(Stadium stadium, Team team) {

        if (stadium != null && team != null) {
            connectToTeamDB();
            Map<String, String> arguments = new HashMap<>();
            arguments.put("stadiumID", stadium.getName());
            arguments.put("teamID", team.getTeamName());
            DB.update(TEAMUPDATES.ADDSTADIUM, arguments);
        }
    }

    /**
     * The function receives a team-owner and a team and binds them together in the database
     *
     * @param owner
     * @param team
     */
    public void addOwnerToTeam(TeamOwner owner, Team team) { //todo ido alon check if you need it

        if (owner != null && team != null) {
            connectToTeamDB();
            Map<String, String> arguments = new HashMap<>();
            arguments.put("ownerID", owner.getUsername());
            arguments.put("teamID", team.getTeamName());
            DB.update(TEAMUPDATES.ADDOWNER, arguments);
        }
    }

    /**
     * The function receives a team and an event that occurred at the team, collects the names of the team's owners and
     * managers as well as the names of the admins of the system and updates each of them of the event
     *
     * @param team
     * @param event
     */
    public void updateTeamStatusToUsers(Team team, String event) {

        if (team != null && event != null) {

            //update isActive column in DB
            setTeamActive(team.getTeamName(),String.valueOf(team.getActive()));

            LinkedList<String> usersToNotify;
            ArrayList<String> teamManagers;
            ArrayList<String> teamOwners;

            connectToSubscriberDB();
            Map<String, String> arguments = new HashMap<>();
            arguments.put("teamID", team.getTeamName());
            teamManagers = DB.selectAllRecords(TEAMOBJECTS.TEAM_TEAM_MANAGERS, arguments).get(0).get("teamManagers");
            teamOwners = DB.selectAllRecords(TEAMOBJECTS.TEAM_TEAM_OWNERES, arguments).get(0).get("teamOwners");

            connectToSubscriberDB();
            usersToNotify = new LinkedList<>(DB.selectAllRecords(UserTypes.ADMIN, arguments).get(0).get("admins"));

            if (teamManagers != null) {
                for (String manager : teamManagers) {
                    usersToNotify.add(manager);
                }
            }
            if (teamOwners != null) {
                for (String owner : teamOwners) {
                    usersToNotify.add(owner);
                }
            }
            usersToNotify.add(event);
            usersToNotify.add("Team status update");
            setChanged();
            notifyObservers(usersToNotify);
        }
    }

    /**
     * The function receives a team and an owner and removes the owner from the team's data structures
     *
     * @param team
     * @param owner
     */
  /*  public void updateOwnerOfRemoval(Team team, TeamOwner owner) { //todo not implemented in the db

        if (team != null && owner != null) {
            LinkedList<String> adminToUpdate = new LinkedList<>();
            String name = DB.removeOwnerFromTeam(team, owner);
            if (name != null) {
               adminToUpdate.add(name);
                adminToUpdate.add("You have lost your rights as an owner for the team '" + team.getTeamName() + "'.");
                adminToUpdate.add("Owner privileges removal");
                setChanged();
                notifyObservers(adminToUpdate);
                //todo add notification to db?
            }
        }
    }*/

    /**
     * The function receives a username and sends it to the DB to be added into the online users data structure
     *
     * @param username
     */
    public void addOnlineUser(String username) {

        if (username != null) {
            // DB.addOnlineUser(username); //todo save it on the ram
        }
    }

    /**
     * The function receives a username and sends it to the DB to be removed from the online users data structure
     *
     * @param username
     */
    public void removeOnlineUser(String username) {
        /*
        if (username != null) {
            DB.removeOnlineUser(username); //todo save it on the ram
        }
        */
        return;
    }

    /**
     * The function receives a username and sends to the DB to check whether the user is online or not
     *
     * @param username
     * @return
     */
    public boolean isUserOnline(String username) {

        if (username != null) {
            //      return DB.isUserOnline(username); todo save it on the ram
        }
        return false;
    }


    /**
     * The function receives a username and a notification for the user and saves it within the database
     *
     * @param username
     * @param message
     */
    public void saveUserMessage(String username, String message, String title) {

        if (username != null && message != null && title != null) {
            //    DB.saveUserMessage(username, message, title); todo build a notification table
        }
    }

    /**
     * //todo javafx function
     * <p>
     * The function receives a username and returns the list of its notifications
     *
     * @param username
     * @return
     */
    public LinkedList<String> getOfflineUsersNotifications(String username) {
        /*
        if(username != null) {
            return DB.getOfflineUsersNotifications(username); //todo need to build a db
        }
        return null; //todo: might need an exception here
        */
        return null;
    }

    /**
     * @param userName Team Owner
     * @return names of the ACTIVE teams
     */
    public LinkedList<String> getActiveTeamOfTeamOwner(String userName) {
        connectToTeamDB();
        HashSet<String> allTeamsOfUser = getAllTeamsOFTeamOwner(userName);
        LinkedList<String> teamsOfOwner = new LinkedList<>();
        //here we are collecting all of the teams the owner owns
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(TEAMUPDATES.ONLYACTIVE, null);
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                if (allTeamsOfUser.contains(temp.get(0))) {
                    teamsOfOwner.add(temp.get(0));
                }
            }
        }
        return teamsOfOwner;
    }

    /**
     * @param userName Team Owner
     * @return names of the INACTIVE teams
     */
    public LinkedList<String> getInactiveTeamOfTeamOwner(String userName) {
        connectToTeamDB();
        HashSet<String> allTeamsOfUser = getAllTeamsOFTeamOwner(userName);
        LinkedList<String> teamsOfOwner = new LinkedList<>();
        //here we are collecting all of the teams the owner owns
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(TEAMUPDATES.ONLYNOTACTIVE, null);
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                if (allTeamsOfUser.contains(temp.get(0))) {
                    teamsOfOwner.add(temp.get(0));
                }
            }
        }
        return teamsOfOwner;
    }

    private HashSet<String> getAllTeamsOFTeamOwner(String userName) {
        HashMap<String, String> args = new HashMap<>();
        args.put("userName", userName);
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(TEAMUPDATES.TEAMOFOWNER, args);
        HashSet<String> teamsOfTeamOwner = new HashSet<>();
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                teamsOfTeamOwner.add(temp.get(0));
            }
        }
        return teamsOfTeamOwner;
    }

    /**
     * The function receives a team name and returns the matching team. If the name does not exist, returns close
     * names to the original
     * @param teamName
     * @return possibleNames
     */
    /*
    public LinkedList<Team> getSimilarTeams(String teamName) {
        LinkedList<Team> possibleNames;
        char firstTeamNameLetter = teamName.charAt(0);
        possibleNames = DB.getTeamsWithCloseNames(firstTeamNameLetter);
        return possibleNames;
    }
    */
    /**
     * The function receives a player name and returns the matching team. If the name does not exist, returns close
     * names to the original
     * @param playerName
     * @return possibleNames
     */
    /*
    public LinkedList<Player> getSimilarPlayers(String playerName) {
        LinkedList<Player> possibleNames;
        char firstPlayerNameLetter = playerName.charAt(0);
        possibleNames = DB.getPlayersWithCloseNames(firstPlayerNameLetter);
        return possibleNames;
    }
    */

    /**
     * @return get all the unconfirmed team names from the DB
     */
    public ArrayList<String> getAllUnconfirmedTeamsInDB() {
        connectToUnconfirmedTeamsDB();
        ArrayList<String> unconfirmedTeams = new ArrayList<>();
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(TEAMUPDATES.UNCONONFIRMED, null);
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                unconfirmedTeams.add(temp.get(0));
            }
        }
        return unconfirmedTeams;
    }

    /**
     * @return get all the team manager's user names from the DB
     */
    public ArrayList<String> getAllTeamManagers() {
        connectToSubscriberDB();
        ArrayList<String> teamManagers = new ArrayList<>();
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(UserTypes.TEAMMANAGER, null);
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                teamManagers.add(temp.get(0));
            }
        }
        return teamManagers;
    }

    /**
     * @return get all the league's names from the DB
     */
    public ArrayList<String> getAllLeaguesInDB() {
        connectToLeagueDB();
        ArrayList<String> leagues = new ArrayList<>();
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(SEASONENUM.ALLLEAGUES, null);
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                leagues.add(temp.get(0));
            }
        }
        return leagues;
    }

    /**
     * @return get all the team's names from the DB
     */
    public ArrayList<String> getAllTeamsNames() {
        connectToTeamDB();
        ArrayList<String> teams = new ArrayList<>();
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(TEAMUPDATES.ALLTEAMS, null);
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                teams.add(temp.get(0));
            }
        }
        return teams;
    }

    public boolean addNewSeasonToDB(String leagueID, int seasonID, Date start, Date end, int win, int lose, int tie, String matchingPolicy, Season season) {
        HashMap<String, ArrayList<String>> details = new HashMap<>();
        connectToSeasonDB();
        ArrayList<String> matchPolicy = new ArrayList<>();
        matchPolicy.add(matchingPolicy);
        details.put("matchingPolicy", matchPolicy);
        ArrayList<String> rank = new ArrayList<>();
        rank.add(String.valueOf(win));
        rank.add(String.valueOf(lose));
        rank.add(String.valueOf(tie));
        details.put("rankingPolicy", rank);
        DB.addToDB(leagueID, String.valueOf(seasonID), start.toString(), end.toString(), details);
        //NOTICE MATCH, REFEREE AND TABLE ARE NOT NEEDED TO BE ADDED IN THIS FUNCTION!!!
        return true;
    }


    /**
     * @param league the league we want to return it's seasons
     * @return the league's seasons
     */
    public ArrayList<String> getAllSeasonsFromLeague(String league) {
        connectToSeasonDB();
        ArrayList<String> seasons = new ArrayList<>();
        HashMap<String, String> args = new HashMap<>();
        args.put("leagueID", league);
        ArrayList<Map<String, ArrayList<String>>> details = DB.selectAllRecords(SEASONENUM.ALLSEASON, args);
        for (Map<String, ArrayList<String>> map : details) {
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> temp = entry.getValue();
                seasons.add(temp.get(0));
            }
        }
        return seasons;
    }

    public ArrayList<String> getAllRefsGameID(String userID){
        connectToMatchDB();
        ArrayList<String> matchID = new ArrayList<>();
        HashMap<String,String> args = new HashMap<>();
        args.put("refID",userID);
        ArrayList<Map<String,ArrayList<String>>> details = DB.selectAllRecords(MATCHENUM.ALLGAMEREFEREE,args);
        for(Map <String,ArrayList<String>> map : details){
            for(Map.Entry <String,ArrayList<String>> entry : map.entrySet()){
                ArrayList<String> temp = entry.getValue();
                matchID.add(temp.get(0));
            }
        }
        return matchID;
    }

    public ArrayList<String> getPlayerInMatch(int matchID){
        Match match = findMatch(matchID);
        String home = match.getHomeTeam().getTeamName();
        String away = match.getAwayTeam().getTeamName();
        ArrayList<String> playersNames = new ArrayList<>();
        for (Player p : match.getHomeTeam().getPlayers()){
            playersNames.add(p.getUsername()+"-"+home);
        }
        for (Player p : match.getAwayTeam().getPlayers()){
            playersNames.add(p.getUsername()+"-"+away);
        }
        return playersNames;
    }

    public String getDetailsOnMatch(int matchID){
        Match match = findMatch(matchID);
        return match.toString();
    }

    //todo javafx function
    public void updatePlayerBDate(String date, String user) {
        SetPlayerBirthdate(user, date);
    }

    //todo javafx function
    public void updatePlayerName(String name, String userName) {
        updateSubscriberName(name, userName);
    }

    //todo javafx function
    public void updatePlayerPost(String userName, String post) {
        updatePage(userName, post);
    }

    //todo javafx function
    public void updateCoachName(String name, String userName1) {
        updateSubscriberName(name, userName1);
    }

    //todo javafx function
    public void updateCoachPost(String userName, String post) {
        updatePage(userName, post);
    }

    private void updateSubscriberName(String name, String userName1) {
        connectToSubscriberDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("subscriberID", userName1);
        arguments.put("name", name);
        DB.update(SUBSCRIBERSUPDATES.SETSUBSCRIBERNAME, arguments);
    }

    private void updatePage(String userName, String post) {
        connectToPageDB();
        Map<String, String> arguments = new HashMap<>();
        arguments.put("userName", userName);
        arguments.put("post", post);
        DB.update(PAGEUPDATES.SUMBIT, arguments);
    }

    //todo javafx function
    public void updateRefereeName(String name, String userName) {
        updateSubscriberName(name, userName);
    }
}
