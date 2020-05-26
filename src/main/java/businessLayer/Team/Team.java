package businessLayer.Team;

import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Tournament.Season;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.Utilities.Page;
import businessLayer.Utilities.HasPage;
import businessLayer.userTypes.Administration.Coach;
import businessLayer.userTypes.Administration.Player;
import businessLayer.userTypes.Administration.TeamManager;
import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.SystemController;
import serviceLayer.SystemService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Team implements HasPage {
    private HashSet<Player> players;
    private HashSet<Coach> coaches;
    private TeamManager teamManager;
    private HashSet<TeamOwner> teamOwners;
    private FinancialMonitoring financialMonitoring;
    private HashSet<Match> matches;
    private HashSet<Season> seasons;
    private Stadium stadium;
    private String teamName;
    private int establishedYear;
    private Boolean isActive;
    private Boolean closedByAdmin; //refers to UC 8.1 - can be changed only once
    private Page teamPage;

    /**
     * a
     *
     * @param players
     * @param coaches
     * @param teamManagers
     * @param teamOwners
     * @param financialMonitoring
     * @param matches
     * @param seasons
     * @param stadium
     * @param teamName
     * @param establishedYear
     * @param isActive
     */
    public Team(HashSet<Player> players, HashSet coaches, TeamManager teamManagers, HashSet<TeamOwner> teamOwners, FinancialMonitoring financialMonitoring, HashSet<Match> matches, HashSet<Season> seasons, Stadium stadium, String teamName, int establishedYear, Boolean isActive, Boolean closedByAdmin) {
        this.players = players;
        this.coaches = coaches;
        this.teamManager = teamManagers;
        //teamManager.getTeams().add(this);
        this.teamOwners = teamOwners;
        Iterator<TeamOwner> it = teamOwners.iterator();
        while (it.hasNext()) {
            TeamOwner teamOwner = it.next();
            //teamOwner.getTeams().add(this);
        }
        this.financialMonitoring = financialMonitoring;
        this.matches = matches;
        this.seasons = seasons;
        this.stadium = stadium;
        this.teamName = teamName;
        this.establishedYear = establishedYear;
        this.isActive = isActive;
        this.closedByAdmin = closedByAdmin;
        teamPage = new Page(new ArrayList<>(teamOwners), teamName, establishedYear, this, teamName);
/*        SystemController systemController = SystemController.SystemController();
        for(TeamOwner teamOwner: teamOwners) {
            systemController.addPageToDB(teamOwner.getUsername(), teamPage);
        }*/


    }
    //todo need to add to this constructor the stadium, but the stadium needs to be moved or be edited

    /**
     * constructor - opening a new team in the system - will be send to approval to the association representative
     *
     * @param teamName  the name of the team
     * @param teamOwner the owner of the team
     */
    public Team(String teamName, TeamOwner teamOwner, int establishedYear) {
        this.teamName = teamName;
        teamOwners = new HashSet<>();
        teamOwners.add(teamOwner);
        teamOwner.getTeams().add(this);
        this.establishedYear = establishedYear;
        isActive = true;
        closedByAdmin = false;
        stadium = null;
        this.players = new HashSet<>();
        this.coaches = new HashSet<>();
        this.seasons = new HashSet<>();
        this.matches = new HashSet<>();
        this.teamManager = null;
        teamPage = new Page(new ArrayList<>(teamOwners), teamName, establishedYear, this, teamName);


    }

    /**
     * the function closes a team permanently by the admin. once it done, it can't be changed
     *
     * @return true is the status has been changed
     */
    public boolean closeTeamPermanently() {
        if (closedByAdmin) {
            return false;
        } else {
            setActive(false);
            closedByAdmin = true;
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
    public HashSet<Season> getSeasons() {
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
    public void setSeasons(HashSet<Season> seasons) {
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
    public HashSet<Match> getMatches() {
        return matches;
    }

    /**
     * @param matches
     */
    public void setMatches(HashSet<Match> matches) {
        this.matches = matches;
    }

    /**
     * @return
     */
    public Boolean getClosedByAdmin() {
        return closedByAdmin;
    }

    /**
     * ido added
     * this function add a player to the team
     *
     * @param p the player to be added
     */
    public void addPlayer(Player p) {
        this.players.add(p);
    }

    /**
     * ido add
     * this function add a teamManager to the team
     *
     * @param teamManager the teamManager to be added
     */

    public void addTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    public void addCoach(Coach coach) {
        this.coaches.add(coach);
    }

    public void removePlayer(Player player) {
        if (players.contains(player)) {
            players.remove(player);
        }
    }

    public void removeTeamManager(TeamManager teamManager) {
        this.teamManager = null;
    }


    public void removeCoach(Coach coach) {
        if (coaches.contains(coach)) {
            coaches.remove(coach);
        }
    }

    public Player getPlayerByUser(String playerUser) {
        for (Player player : players) {
            if (player.getUsername().equals(playerUser)) {
                return player;
            }
        }
        return null;
    }

    public Coach getCoachByUser(String coachUser) {
        for (Coach coach : coaches) {
            if (coach.getUsername().equals(coachUser)) {
                return coach;
            }
        }
        return null;
    }

    /*
    public TeamManager getManegerByUser(String teamManagerUser) {
        for (TeamManager teamManager:teamManagers) {
            if(teamManager.getUsername().equals(teamManagerUser)){
                return teamManager;
            }
        }
        return null;
    }*/

    public Boolean containPlayer(Player player) {
        return players.contains(player);
    }

    public boolean containCoach(Coach coach) {
        return this.coaches.contains(coach);
    }


    public int calculateExpanse() {
        int sum = 0;
        for (Player player : players) {
            sum = sum + player.getSalary();
        }
        for (Coach coach : coaches) {
            sum = sum + coach.getSalary();
        }
        if (teamManager != null) {
            sum = sum + teamManager.getSalary();
        }
        return sum;
    }

    public int calculateIncome() {
        int sum = 0;
        this.stadium.getNumberOfSeats();
        return stadium.calculateIncome(this);
    }

    public boolean updatePage(String update) {
        return teamPage.update(update);
    }
}
