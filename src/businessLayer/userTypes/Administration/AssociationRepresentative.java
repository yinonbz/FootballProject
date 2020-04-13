package businessLayer.userTypes.Administration;

import businessLayer.Tournament.MatchingPolicy;
import businessLayer.Tournament.RankingPolicy;
import serviceLayer.LeagueController;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.Date;

public class AssociationRepresentative extends Subscriber {

    private String name;
    private FinancialMonitoring financialMonitoring;
    private LeagueController leagueController;

    /**
     * @param username
     * @param password
     * @param name
     * @param financialMonitoring
     * @param leaguesController
     */
    public AssociationRepresentative(String username, String password, String name, FinancialMonitoring financialMonitoring, LeagueController leaguesController, SystemController systemController) {

        super(username, password, systemController);
        this.name = name;
        this.financialMonitoring = financialMonitoring;
        this.leagueController = leaguesController;
        leaguesController.addAssociationRepToController(this);

    }

    /**
     * The function creates a league in the system and returns a boolean representing whether it was created or not
     *
     * @param newLeagueID
     * @return true/false
     */
    public Boolean createLeague(String newLeagueID) {

        if (newLeagueID == null) {
            return false;
        }
        if (leagueController.doesLeagueExist(newLeagueID)) {
            return false;
        }
        return leagueController.createLeague(newLeagueID);
    }

    /**
     * The function creates a season within a league and returns whether the creation was successful or not
     *
     * @param leagueName
     * @param seasonID
     * @param startingDate
     * @param endingDate
     * @param matchPolicy
     * @param rankingPolicy
     * @return true/false
     */
    public Boolean createSeason(String leagueName, int seasonID, Date startingDate, Date endingDate, MatchingPolicy matchPolicy, RankingPolicy rankingPolicy) {

        if (!leagueController.doesLeagueExist(leagueName)) {
            return false;
        }
        return leagueController.addSeasonToLeague(leagueName, seasonID, startingDate, endingDate, matchPolicy, rankingPolicy);
    }

    /**
     * The function creates a new referee in the system, returns whether the creation was successful or not
     *
     * @param username
     * @return true/false
     */
    public boolean createReferee(String username) {
        if (username == null) {
            return false;
        }
        return super.getSystemController().addReferee(username, "1111", "default", "basic", this);
    }

    /**
     * The function removes referee from the entire system
     *
     * @param username
     * @return true/false
     */
    public boolean removeRefree(String username) {

        if (username == null) {
            return false;
        }
        return super.getSystemController().removeReferee(username);
    }


    /**
     * The function receives a referee's username, a league's name and season's ID and assigns the referee to the season within the given league, returns whether was successful or not
     *
     * @param username
     * @param leagueName
     * @param seasonID
     * @return true/false
     */
    public boolean assignRefereeToSeason(String username, String leagueName, int seasonID) {

        if (username == null || leagueName == null) {
            return false;
        }
        return leagueController.addRefereeToSeasonInLeague(username, leagueName, seasonID);
    }

    /**
     * @return
     */
    public Boolean assignMatchProperties() {
        return true;
    }

    /**
     * @return
     */
    public Boolean definePolicies() {
        return true;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public FinancialMonitoring getFinancialMonitoring() {
        return financialMonitoring;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param financialMonitoring
     */
    public void setFinancialMonitoring(FinancialMonitoring financialMonitoring) {
        this.financialMonitoring = financialMonitoring;
    }

    /**
     * @return
     */
    public LeagueController getLeaguesController() {
        return leagueController;
    }

    /**
     * @param leaguesController
     */
    public void setLeaguesController(LeagueController leaguesController) {
        this.leagueController = leaguesController;
    }

    @Override
    public Boolean editDetails() {
        return null;
    }
}
