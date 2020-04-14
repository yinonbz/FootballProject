package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.userTypes.Subscriber;
import com.sun.deploy.util.StringUtils;
import serviceLayer.SystemController;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
*********************FOR IDO**********************************
 */

public class TeamOwner extends Subscriber {

    private OwnerEligible originalObject;
    private Set<TeamOwner> assignedByMe;
    private String name;
    private HashSet<Team> teams;
    private HashSet<TeamOwner> teamOwners;
    private HashSet<TeamManager> teamManagers;

    public static int newTeamOwnerCounter = 0;
    /**
     *
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name, SystemController systemController) {
        super(username, password, systemController);
        this.name = name;
        teams = new HashSet<>();
        assignedByMe = new HashSet<>();
        originalObject = null;
        this.teamManagers = new HashSet<>();
        this.teamOwners = new HashSet<>();
        this.assignedByMe = new HashSet<>();
    }

    /**
     * the function lets the team owner to send a request for opening a new team
     *
     * @param teamName        the team's name
     * @param establishedYear the established year of the team
     * @return true if the request was send successfully
     */
    public boolean sendRequestForTeam(String teamName, String establishedYear) {
        if (!teamName.isEmpty() && !establishedYear.isEmpty()) {
            if (tryParseInt(establishedYear)) {
                if (isTheNumberAYear(establishedYear)) {
                    LinkedList<String> details = new LinkedList<>();
                    details.add(teamName);
                    details.add(establishedYear);
                    details.add(getUsername());
                    return systemController.addToTeamConfirmList(details, this);
                }
            }
        }
        return false;
    }

    /**
     * private function that checks that a string represents an interger
     *
     * @param value the string
     * @return true if it an integer
     */
    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * this function check a string that represent a NUMBER and checks if it can be a year
     *
     * @param value the string that represents a number
     * @return true if it can be a year
     */
    private boolean isTheNumberAYear(String value) {
        if (tryParseInt(value)) {
            int tempNumber = Integer.parseInt(value);
            if (tempNumber >= 1800 && tempNumber <= 2020) {
                return true;
            }
        }
        return false;
    }

    /**
     * the function checks if the team owner has a team that nobody but him ows, so he can't be deleted as a user
     *
     * @return true if he owns one of the teams elusively
     */
    public boolean isExclusiveTeamOwner() {
        if (teams.size() > 0) {
            for (Team team : teams) {
                if (team.getTeamOwners().size() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this function add the asset to the chosen team
     *
     * @param teamId
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean addAsset(int teamId, String assetType, String assetUserName) {
        boolean isAdded = false;
        Team team = findTeam(teamId);
        SystemController systemController = this.getSystemController();
        if (team != null && team.getActive()) {
            switch (assetType) {
                case "Player":
                    Player player = systemController.findPlayer(assetUserName);
                    if (player != null && !player.getAssigned()) {
                        team.addPlayer(player);
                        player.setTeam(team);
                        player.setAssign(true);
                        isAdded = true;
                    }
                    break;
                case "TeamManager":
                    TeamManager teamManager = systemController.findTeamManager(assetUserName);
                    if (teamManager != null && !teamManager.getAssigned()) {
                        team.addTeamManager(teamManager);

                        this.teamManagers.add(teamManager);
                        teamManager.setAssign(true);
                        isAdded = true;
                    }
                    break;

                case "Coach":
                    Coach coach = systemController.findCoach(assetUserName);
                    if (coach != null&&!coach.containTeam(team)) {
                        team.addCoach(coach);
                        coach.addTeam(team);
                        isAdded = true;
                    }
                    break;

                case "Stadium":
                    Stadium stadium = systemController.findStadium(assetUserName);
                    if (stadium != null && stadium.containTeam(team) == false&& team.getStadium()==null) {
                        team.setStadium(stadium);
                        stadium.addTeam(team);
                        isAdded = true;
                    }
                    break;

            }

        }
        return isAdded;
    }

    public boolean deleteAsset(int teamId, String assetType, String assetUserName) {
        boolean isDeleted = false;
        Team team = findTeam(teamId);
        SystemController systemController = this.getSystemController();
        if (team != null && team.getActive()) {
            switch (assetType) {
                case "Player":
                    Player player = systemController.findPlayer(assetUserName);
                    if (player != null && player.getAssigned() && team.containPlayer(player)) {
                        player.setAssign(false);
                        team.removePlayer(player);
                        isDeleted = true;
                    }
                    break;
                case "TeamManager":
                    TeamManager teamManager = systemController.findTeamManager(assetUserName);
                    if (teamManager != null && teamManager.getAssigned() && teamManagers.contains(teamManager)) {
                        team.removeTeamManager(teamManager);
                        teamManager.setAssign(false);
                        isDeleted = true;
                    }
                    break;

                case "Coach":
                    Coach coach = systemController.findCoach(assetUserName);
                    if (coach != null && coach.containTeam(team) && team.containCoach(coach)) {
                        team.removeCoach(coach);
                        coach.addTeam(team);
                        isDeleted = true;
                    }
                    break;

                case "Stadium":
                    Stadium stadium = systemController.findStadium(assetUserName);
                    if (stadium != null && stadium.containTeam(team) == true && team.getStadium() != null) {
                        team.setStadium(stadium);
                        stadium.removeTeam(team);
                        team.setStadium(null);
                        isDeleted = true;
                    }
                    break;
            }
        }
        return isDeleted;
    }


    public boolean editPlayer(int teamId, String playerUser, String typeEdit, String edit) {
        Team team = findTeam(teamId);
        if (playerUser != null && typeEdit != null && edit != null) {
            Player player = team.getPlayerByUser(playerUser);
            if (player != null) {
                if (typeEdit.equals("birthDate")) {
                    team.removePlayer(player);
                    player.setBirthDate(edit);
                    team.addPlayer(player);
                    return true;
                } else if (typeEdit.equals("fieldJob")) {
                    team.removePlayer(player);
                    player.setFieldJob(edit);
                    team.addPlayer(player);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean editCoach(int teamId, String CoachUser, String typeEdit, String edit) {
        Team team = findTeam(teamId);
        if (CoachUser != null && typeEdit != null && edit != null) {
            Coach coach = team.getCoachByUser(CoachUser);
            if (coach != null) {
                if (typeEdit.equals("training")) {
                    team.removeCoach(coach);
                    coach.setTraining(edit);
                    team.addCoach(coach);
                    return true;
                } else if (typeEdit.equals("teamJob")) {
                    team.removeCoach(coach);
                    coach.setTeamJob(edit);
                    team.addCoach(coach);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean editTeamManager(int teamId, String teamManagerUser, String typeEdit, int edit) {
        Team team = findTeam(teamId);
        if (teamManagerUser != null && typeEdit != null && team != null) {
            TeamManager teamManager = team.getManegerByUser(teamManagerUser);
            if (teamManager != null) {
                if (typeEdit.equals("salary")) {
                    team.removeTeamManager(teamManager);
                    teamManager.setSalary(edit);
                    team.addTeamManager(teamManager);
                    return true;
                }
            }
        }
        return false;
    }

    private Team findTeam(int teamId) {
        for (Team team : teams) {
            if (team.getTeamId() == teamId) {
                return team;
            }
        }
        return null;
    }

    /**
     * @return
     */
    public boolean editProperties(){
        return true;
    }

    /**
     *
     * @return
     */
    public boolean editOwners(){

        return true;
    }

    /**
     *
     * @return
     */

    public boolean editManagers(){
        return true;
    }

    /**
     *
     * @return
     */
    public boolean editTeams(){

        return true;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * a getter of teams of a team owner
     * @return the data structure of the teams of the team owner
     */
    public HashSet<Team> getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     */

    public void setTeams(HashSet<Team> teams) {
        this.teams = teams;
    }

    @Override
    public Boolean editDetails() {
        return null;
    }

    /**
     * @return true if fictive (ex: player is also a team owner = fictive)
     * false else
     */
    public boolean isFictive() {
        if (originalObject == null) {
            return false;
        }
        return true;
    }


    /**
     * //UC-6.2
     * @param userName the user name of the user that the Team Owner wants to appoint to
     * @return
     */
    public Subscriber enterMember(String userName){

        return systemController.getSubscriberByUserName(userName);
    }


    /**
     * //UC-6.2
     * @param subscriber the new subscriber that the user wants to add to the team's owners
     * @param teamName the team name that the user wants to add a new team owner
     * @return true if the new team owner has added successfully.
     *          false if: the subscriber is already a team owner, or the subscriber isn't a Player, a Coach or a Team Manager.
     */
    public Boolean appointToOwner(Subscriber subscriber, String teamName){

        if(subscriber instanceof OwnerEligible || subscriber instanceof TeamOwner){
            if(!(subscriber instanceof TeamOwner) && ((OwnerEligible) subscriber).isOwner() == false){
                if(getTeams().contains(systemController.getTeamByName(teamName))) { //if the user is the team owner of the team with the name 'teamName'
                    String newUserName = subscriber.getUsername();
                    updateFictiveOwner(newUserName,subscriber,teamName);
                    return true;
                }
                else{
                    //System.out.println("You cannot add to a team which you do not own.");
                    return false;
                }
            }
            else{
                //System.out.println("The user " + subscriber.getUsername() + " is already an owner of a team.");
                return false;
            }
        }
        else{
            //System.out.println("Team owner must be a Player, a Coach or a Team Manager.");
            return false;
        }
    }

    /**
     * //UC - 6.2
     * @param newUserName the userName for the new fictive user.
     * @param subscriber the subscriber to add as a new team owner.
     * @param teamName the team name to add a new team owner.
     */
    private void updateFictiveOwner(String newUserName, Subscriber subscriber, String teamName) {
        while (subscriber.getSystemController().getSystemSubscribers().containsKey(newUserName)) { //generate new fictive user name
            newUserName = newUserName + newTeamOwnerCounter++;
        }
        TeamOwner newTeamOwner = new TeamOwner(newUserName, subscriber.getPassword(), "fictive", subscriber.getSystemController());
        if(subscriber instanceof Player){
            Player player = (Player)subscriber;
            player.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(player);

        }
        else if(subscriber instanceof Coach){
            Coach coach = (Coach)subscriber;
            coach.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(coach);
        }
        else if(subscriber instanceof TeamManager){
            TeamManager teamManager = (TeamManager)subscriber;
            teamManager.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(teamManager);
        }

        subscriber.getSystemController().getTeamByName(teamName).getTeamOwners().add(newTeamOwner); //add the new team owner to the team's team owners list
        newTeamOwner.getTeams().add(getSystemController().getTeamByName(teamName));
        assignedByMe.add(newTeamOwner);
        //todo - add complaints to newTeamOwner? if not, complaints needs to be added manually to the newTeamOwner from the original object
        //System.out.println("The user " + subscriber.getUsername() + " has been added to the Team '" + teamName + "' owners list successfully.");
    }

    protected OwnerEligible getOriginalObject() {
        return originalObject;
    }

    public void setOriginalObject(OwnerEligible originalObject) {
        this.originalObject = originalObject;
    }
    /**
     * UC 6.6
     * @param teamName the name of the team to be returned.
     * @return instance of the team with the name of 'teamName'.
     * null if there is no such team in the system.
     */
    public Team getTeam(String teamName) {
        Iterator<Team> it = teams.iterator();
        while (it.hasNext()) {
            Team teamCheck = it.next();
            if (teamCheck.getTeamName().equals(teamName)) {
                return teamCheck;
            }
        }
        //System.out.println("There is no team with the name '" + teamName + "' in the system.");
        return null;
    }

    /**
     * @param team team to be enabled/disabled.
     */
    public void changeStatus(Team team) {
        if (!team.getActive()) {
            team.setActive(true);
            //System.out.println("The team '" + team.getTeamName() + "' has been enabled and is now active.");
        }
        else{
            team.setActive(false);
            //System.out.println("The team '" + team.getTeamName() + "' has been disabled and is now not active.");
        }
    }

}
