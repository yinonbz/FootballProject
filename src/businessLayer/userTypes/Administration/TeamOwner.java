package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class TeamOwner extends Subscriber {

    private String name;
    private HashSet<Team> teams;
    private HashSet<TeamOwner> teamOwners;
    private HashSet<TeamManager> teamManagers;
    /**
     *
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name, SystemController systemController) {
        super(username, password, systemController);
        this.name= name;
        teams = new HashSet<>();
    }

    /**
     * the function lets the team owner to send a request for opening a new team
     * @param teamName the team's name
     * @param establishedYear the established year of the team
     * @return true if the request was send successfully
     */
    public boolean sendRequestForTeam (String teamName, String establishedYear){
        if(!teamName.isEmpty() && !establishedYear.isEmpty()){
            if(tryParseInt(establishedYear)){
                if(isTheNumberAYear(establishedYear)){
                    LinkedList<String> details= new LinkedList<>();
                    details.add(teamName);
                    details.add(establishedYear);
                    details.add(getUsername());
                    return systemController.addToTeamConfirmList(details,this);
                }
            }
        }
        return false;
    }

    /**
     * private function that checks that a string represents an interger
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
     * @return true if he owns one of the teams elusively
     */
    public boolean isExclusiveTeamOwner(){
        if (teams.size()>0){
            for(Team team : teams){
                if(team.getTeamOwners().size()==1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this function add the asset to the chosen team
     * @param teamId
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean addAsset(int teamId, String assetType, String assetUserName){
        boolean isAdded = false;
        Team team = findTeam(teamId);
        SystemController systemController=  this.getSystemController();
        if(team!=null && team.getActive()){
            switch (assetType){
                case "Player":
                    Player player = systemController.findPlayer(assetUserName);
                    if(player!=null&&!player.getAssigned()) {
                        team.addPlayer(player);
                        player.setAssign(true);
                        isAdded = true;
                    }
                    break;
                case "TeamManager":
                    TeamManager teamManager = systemController.findTeamManager(assetUserName);
                    if(teamManager!=null&&!teamManager.getAssigned()){
                        team.addTeamManager(teamManager);
                        teamManager.setAssign(true);
                        isAdded = true;
                    }
                    break;

                case "Coach":
                    Coach coach = systemController.findCoach(assetUserName);
                    if(coach!=null&&!coach.getAssigned()) {
                        team.addCoach(coach);
                        coach.setAssign(true);
                        isAdded = true;
                    }
                    break;

                case "Stadium":
                    break;

            }

        }
        return isAdded;
    }
    public boolean deleteAsset(int teamId, String assetType, String assetUserName){
        boolean isDeleted = false;
        Team team = findTeam(teamId);
        SystemController systemController =  this.getSystemController();
        if(team!=null && team.getActive()){
            switch (assetType){
                case "Player":
                    Player player = systemController.findPlayer(assetUserName);
                    if(player!=null&&player.getAssigned()) {
                        player.setAssign(false);
                        team.removePlayer(player);
                        isDeleted = true;
                    }
                    break;
                case "TeamManager":
                    TeamManager teamManager = systemController.findTeamManager(assetUserName);
                    if(teamManager!=null&&teamManager.getAssigned()){
                        team.removeTeamManager(teamManager);
                        teamManager.setAssign(false);
                        isDeleted = true;
                    }
                    break;

                case "Coach":
                    Coach coach = systemController.findCoach(assetUserName);
                    if(coach!=null&&coach.getAssigned()) {
                        team.removeCoach(coach);
                        coach.setAssign(false);
                        isDeleted = true;
                    }
                    break;

                case "Stadium":
                    break;

            }

        }
        return isDeleted;
    }
    public boolean editAsset(int teamId, String assetType, String assetUserName ,String typeEdit,String edit){

        return false;
    }

    public boolean editPlayer(int teamId,String playerUser,String typeEdit,String edit){
        Team team = findTeam(teamId);
        if(playerUser!=null &&typeEdit!=null && edit!=null ){
            Player player = team.getPlayerByUser(playerUser);
            if(player!=null) {
                if (typeEdit.equals("birthDate")) {
                    team.removePlayer(player);
                    player.setBirthDate(edit);
                    team.addPlayer(player);
                    return true;
                } else if (typeEdit.equals("fieldJobs")) {
                    team.removePlayer(player);
                    player.setFieldJob(edit);
                    team.addPlayer(player);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean editCoach(int teamId,String CoachUser,String typeEdit,String edit){
        Team team = findTeam(teamId);
        if(CoachUser!=null &&typeEdit!=null && edit!=null ){
            Coach coach = team.getCoachByUser(CoachUser);
            if(coach!=null) {
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
    public boolean editTeamManager(int teamId,String teamManagerUser,String typeEdit,int edit) {
        Team team = findTeam(teamId);
        if (teamManagerUser != null && typeEdit != null) {
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
        for (Team team:teams) {
            if(team.getTeamId()==teamId){
                return team;
            }
        }
        return null;
    }

    /**
     *
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
}
