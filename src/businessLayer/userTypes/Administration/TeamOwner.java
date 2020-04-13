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
     *
     * @return
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
