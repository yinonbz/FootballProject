package businessLayer.Team;

import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Tournament.Season;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.userTypes.Administration.Coach;
import businessLayer.userTypes.Administration.Player;
import businessLayer.userTypes.Administration.TeamManager;
import businessLayer.userTypes.Administration.TeamOwner;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Team {
    private HashSet<Player> players;
    private HashSet<Coach> coaches;
    private TeamManager teamManager;
    private HashSet<TeamOwner> teamOwners;
    private FinancialMonitoring financialMonitoring;
    private List<Match> matches;
    private List<Season> seasons;
    private Stadium stadium;
    private String teamName;
    private int teamId;
    private int establishedYear;
    private Boolean isActive;
    private Boolean closedByAdmin; //refers to UC 8.1 - can be changed only once

    /**a
     * @param players
     * @param coaches
     * @param teamManager
     * @param teamOwners
     * @param financialMonitoring
     * @param matches
     * @param seasons
     * @param stadium
     * @param teamName
     * @param teamId
     * @param establishedYear
     * @param isActive
     */
    public Team(HashSet<Player> players, HashSet coaches, TeamManager teamManager, HashSet<TeamOwner> teamOwners, FinancialMonitoring financialMonitoring, List<Match> matches, List<Season> seasons, Stadium stadium, String teamName, int teamId, int establishedYear, Boolean isActive, Boolean closedByAdmin) {
        this.players = players;
        this.coaches = coaches;
        this.teamManager = teamManager;
        this.teamOwners = teamOwners;
        this.financialMonitoring = financialMonitoring;
        this.matches = matches;
        this.seasons = seasons;
        this.stadium = stadium;
        this.teamName = teamName;
        this.teamId = teamId; //todo need to check if we still need this id
        this.establishedYear = establishedYear;
        this.isActive = isActive;
        this.closedByAdmin=closedByAdmin;
    }
    //todo need to add to this constructor the stadium, but the stadium needs to be moved or be edited
    /**
     * constructor - opening a new team in the system - will be send to approval to the association representative
     * @param teamName the name of the team
     * @param teamOwner the owner of the team
     */
    public Team (String teamName, TeamOwner teamOwner, int establishedYear){
        this.teamName=teamName;
        teamOwners= new HashSet<>();
        teamOwners.add(teamOwner);
        teamOwner.getTeams().add(this);
        this.establishedYear=establishedYear;
        isActive=true;
        closedByAdmin=false;
    }

    /**
     * the function closes a team permanently by the admin. once it done, it can't be changed
     * @return true is the status has been changed
     */
    public boolean closeTeamPermanently(){
        if(closedByAdmin){
            return false;
        }
        else{
            setActive(false);
            closedByAdmin=true;
        }
        return true;
    }

    /**
     * @return
     */
    public HashSet<Player> getPlayers() {
        return players;
    }

    /**
     * @return
     */
    public HashSet<Coach> getCoaches() {
        return coaches;
    }

    /**
     * @return
     */
    public TeamManager getTeamManager() {
        return teamManager;
    }

    /**
     * @return
     */
    public HashSet<TeamOwner> getTeamOwners() {
        return teamOwners;
    }

    /**
     * @return
     */
    public FinancialMonitoring getFinancialMonitoring() {
        return financialMonitoring;
    }


    /**
     * @return
     */
    public List<Season> getSeasons() {
        return seasons;
    }

    /**
     * @return
     */
    public Stadium getStadium() {
        return stadium;
    }

    /**
     * @return
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @return
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * @return
     */
    public int getEstablishedYear() {
        return establishedYear;
    }

    /**
     * @return
     */
    public Boolean getActive() {
        return isActive;
    }

    /**
     * @param players
     */
    public void setPlayers(HashSet<Player> players) {
        this.players = players;
    }

    /**
     * @param coaches
     */
    public void setCoaches(HashSet<Coach> coaches) {
        this.coaches = coaches;
    }

    /**
     * @param teamManager
     */
    public void setTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    /**
     * @param teamOwners
     */
    public void setTeamOwners(HashSet<TeamOwner> teamOwners) {
        this.teamOwners = teamOwners;
    }

    /**
     * @param financialMonitoring
     */
    public void setFinancialMonitoring(FinancialMonitoring financialMonitoring) {
        this.financialMonitoring = financialMonitoring;
    }


    /**
     * @param seasons
     */
    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    /**
     * @param stadium
     */
    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    /**
     * @param teamName
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @param teamId
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * @param establishedYear
     */
    public void setEstablishedYear(int establishedYear) {
        this.establishedYear = establishedYear;
    }

    /**
     * @param active
     */
    public void setActive(Boolean active) {
        isActive = active;
    }

    /**
     * @return
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * @param matches
     */
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    /**
     *
     * @return
     */
    public Boolean getClosedByAdmin() {
        return closedByAdmin;
    }


}
