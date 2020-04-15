package serviceLayer;

import businessLayer.Tournament.League;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Tournament.MatchingPolicy;
import businessLayer.Tournament.RankingPolicy;
import businessLayer.Utilities.alertSystem.AlertSystem;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.userTypes.Administration.AssociationRepresentative;
import businessLayer.userTypes.Administration.Referee;
import businessLayer.userTypes.Subscriber;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LeagueController {

    private HashMap<String, League> leagues;
    //private businessLayer.Tournament.RankingPolicy rankingPolicy; //might be list as well, define later
    //private businessLayer.Tournament.MatchingPolicy matchingPolicy; //might be list as well, define later
    private LoggingSystem loggingSystem;
    private List<AssociationRepresentative> associationRepresentatives;
    private HashMap<String, Referee> referees;
    private AlertSystem alertSystem;
    private SystemController systemController;

    public LeagueController() {

        leagues = new HashMap<>();
        referees = new HashMap<>();
        associationRepresentatives = new ArrayList<>();
        //systemController = SystemController.SystemController();
    }


    /**
     * returns the data structure that holds all of the stadiums in the system
     *
     * @return the stadiums in the system
     */
    public HashMap<String, Stadium> getStadiums() {
        return systemController.getStadiums();
    }

    /**
     * @param alerts
     * @return
     */
    public boolean sendAlerts(List<String> alerts) {

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
    public HashMap<String, League> getLeagues() {
        return leagues;
    }

    /**
     * @param leagues
     */
    public void setLeagues(HashMap<String, League> leagues) {
        this.leagues = leagues;
    }
    /*

     */
/**
 *
 * @return
 *//*

    public businessLayer.Tournament.RankingPolicy getRankingPolicy() {
        return rankingPolicy;
    }

    */
/**
 *
 * @param rankingPolicy
 *//*

    public void setRankingPolicy(businessLayer.Tournament.RankingPolicy rankingPolicy) {
        this.rankingPolicy = rankingPolicy;
    }

    */
/**
 *
 * @return
 *//*

    public businessLayer.Tournament.MatchingPolicy getMatchingPolicy() {
        return matchingPolicy;
    }

    */
/**
 *
 * @param matchingPolicy
 *//*

    public void setMatchingPolicy(businessLayer.Tournament.MatchingPolicy matchingPolicy) {
        this.matchingPolicy = matchingPolicy;
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
    public List<AssociationRepresentative> getAssociationRepresentatives() {
        return associationRepresentatives;
    }

    /**
     * @param associationRepresentatives
     */
    public void setAssociationRepresentatives(List<AssociationRepresentative> associationRepresentatives) {
        this.associationRepresentatives = associationRepresentatives;
    }

    /**
     * @return
     */
    public HashMap<String, Referee> getReferees() {
        return referees;
    }

    /**
     * @param referees
     */
    public void setReferees(HashMap<String, Referee> referees) {
        this.referees = referees;
    }

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
     * The function returns whether a league with the same ID exists
     *
     * @param leagueID
     * @return true/false
     */
    public boolean doesLeagueExist(String leagueID) {
        return leagues.containsKey(leagueID);
    }

    /**
     * The function creates a league and returns whether the league was created successfully or not
     *
     * @param leagueID
     * @return true/false
     */
    public boolean createLeague(String leagueID) {

        if (leagueID == null) {
            return false;
        }
        League newLeague = new League(leagueID);
        leagues.put(leagueID, newLeague);
        if (!leagues.containsKey(leagueID)) {
            return false;
        }
        return true;
    }

    /**
     * The function creates a season within league and returns whether the season was created successfully or not
     *
     * @param leagueID
     * @param seasonID
     * @param startingDate
     * @return
     */
    public boolean addSeasonToLeague(String leagueID, int seasonID, Date startingDate, Date endingDate) {

        League leagueToAdd = leagues.get(leagueID);
        if (leagueToAdd == null) {
            return false;
        }
        return leagueToAdd.addSeasonToLeague(seasonID, startingDate, endingDate);
        //todo: check if when you pull out a complex object from a hashmap, the changes you do to it are registered in the hashmap
    }

    /**
     * The function receives referee from the system controller, removes it from its data structures and returns whether the removal was successful or not
     *
     * @param referee
     * @return true/false
     */
    public boolean removeReferee(Subscriber referee) {
        if (!(referee instanceof Referee)) {
            return false;
        }
        String refName = referee.getUsername();
        if (referees.containsKey(refName)) {
            referees.remove(refName);
            return true;
        }
        return false;
    }


    /**
     * The function assigns a referee from the system to a season within a specific league, returns whether the assignment was successful or not
     *
     * @param refUserName
     * @param leagueName
     * @param seasonID
     * @return true/false
     */
    public boolean addRefereeToSeasonInLeague(String refUserName, String leagueName, int seasonID) {

        if (refUserName == null || leagueName == null) {
            return false;
        }
        if (leagues.containsKey(leagueName) && referees.containsKey(refUserName)) {
            League addingToLeague = leagues.get(leagueName);
            Referee refToAssign = referees.get(refUserName);
            return addingToLeague.addRefereeToSeason(refToAssign, seasonID);
        }
        return false;
    }


    /**
     * The function adds an association representative to the data structure in the league controller
     *
     * @param associationRep
     */
    public void addAssociationRepToController(AssociationRepresentative associationRep) {
        if (associationRep != null) {
            if (!associationRepresentatives.contains(associationRep)) {
                associationRepresentatives.add(associationRep);
            }
        }
    }


    /**
     * The function adds referee to the league controller data structures by receiving it from the system controller
     * @param referee
     */
    public void addRefereeToDataFromSystemController(Referee referee) {

        if (referee != null && !referees.containsKey(referee.getUsername())) {
            referees.put(referee.getUsername(), referee);
        }
    }
}
