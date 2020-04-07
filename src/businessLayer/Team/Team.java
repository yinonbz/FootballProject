package businessLayer.Team;

import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Tournament.Season;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import serviceLayer.userTypes.Administration.Coach;
import serviceLayer.userTypes.Administration.Player;
import serviceLayer.userTypes.Administration.TeamManager;
import serviceLayer.userTypes.Administration.TeamOwner;

import java.util.List;

public class Team {
    private List<Player> players;
    private List<Coach> coaches;
    private TeamManager teamManager;
    private List<TeamOwner> teamOwners;
    private FinancialMonitoring financialMonitoring;
    private List<Match> matches;
    private List<Season> seasons;
    private Stadium stadium;
    private String teamName;
    private int teamId;
    private int establishedYear;
    private Boolean isActive;

    /**
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
    public Team(List<Player> players, List<Coach> coaches, TeamManager teamManager, List<TeamOwner> teamOwners, FinancialMonitoring financialMonitoring, List<Match> matches, List<Season> seasons, Stadium stadium, String teamName, int teamId, int establishedYear, Boolean isActive) {
        this.players = players;
        this.coaches = coaches;
        this.teamManager = teamManager;
        this.teamOwners = teamOwners;
        this.financialMonitoring = financialMonitoring;
        this.matches = matches;
        this.seasons = seasons;
        this.stadium = stadium;
        this.teamName = teamName;
        this.teamId = teamId;
        this.establishedYear = establishedYear;
        this.isActive = isActive;
    }

    /**
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return
     */
    public List<Coach> getCoaches() {
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
    public List<TeamOwner> getTeamOwners() {
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
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * @param coaches
     */
    public void setCoaches(List<Coach> coaches) {
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
    public void setTeamOwners(List<TeamOwner> teamOwners) {
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
}
