package serviceLayer;

import businessLayer.Tournament.League;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Utilities.alertSystem.AlertSystem;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.userTypes.Administration.AssociationRepresentative;
import businessLayer.userTypes.Administration.Referee;

import java.util.HashMap;
import java.util.List;

public class LeagueController {

    private List<League> leagues;
    //private businessLayer.Tournament.RankingPolicy rankingPolicy; //might be list as well, define later
    //private businessLayer.Tournament.MatchingPolicy matchingPolicy; //might be list as well, define later
    private LoggingSystem loggingSystem;
    private List<AssociationRepresentative> associationRepresentatives;
    private List<Referee> referees;
    private AlertSystem alertSystem;
    private HashMap <Integer, Stadium> stadiums;

    /**
     *
     */
    public LeagueController() {
        stadiums = new HashMap<>();
    }

    /**
     *
     * @param alerts
     * @return
     */
    public boolean sendAlerts(List<String> alerts){

        return true;
    }

    /**
     *
     * @param logs
     * @return
     */
    public boolean sendLogs(List<String> logs){

        return true;
    }

    /**
     *
     * @return
     */
    public List<League> getLeagues() {
        return leagues;
    }

    /**
     *
     * @param leagues
     */
    public void setLeagues(List<League> leagues) {
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
     *
     * @return
     */
    public LoggingSystem getLoggingSystem() {
        return loggingSystem;
    }

    /**
     *
     * @param loggingSystem
     */
    public void setLoggingSystem(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    /**
     *
     * @return
     */
    public List<AssociationRepresentative> getAssociationRepresentatives() {
        return associationRepresentatives;
    }

    /**
     *
     * @param associationRepresentatives
     */
    public void setAssociationRepresentatives(List<AssociationRepresentative> associationRepresentatives) {
        this.associationRepresentatives = associationRepresentatives;
    }

    /**
     *
     * @return
     */
    public List<Referee> getReferees() {
        return referees;
    }

    /**
     *
     * @param referees
     */
    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }

    /**
     *
     * @return
     */
    public AlertSystem getAlertSystem() {
        return alertSystem;
    }

    /**
     *
     * @param alertSystem
     */
    public void setAlertSystem(AlertSystem alertSystem) {
        this.alertSystem = alertSystem;
    }

    public HashMap<Integer, Stadium> getStadiums() {
        return stadiums;
    }

}
