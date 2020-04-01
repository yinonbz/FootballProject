import java.util.List;
import java.util.Map;

public class LeagueController {

    private List<League> leagues;
    //private RankingPolicy rankingPolicy; //might be list as well, define later
    //private MatchingPolicy matchingPolicy; //might be list as well, define later
    private LoggingSystem loggingSystem;
    private List<AssociationRepresentative> associationRepresentatives;
    private List<Referee> referees;
    private AlertSystem alertSystem;

    /**
     *
     */
    public LeagueController() {

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

    public RankingPolicy getRankingPolicy() {
        return rankingPolicy;
    }

    */
/**
     *
     * @param rankingPolicy
     *//*

    public void setRankingPolicy(RankingPolicy rankingPolicy) {
        this.rankingPolicy = rankingPolicy;
    }

    */
/**
     *
     * @return
     *//*

    public MatchingPolicy getMatchingPolicy() {
        return matchingPolicy;
    }

    */
/**
     *
     * @param matchingPolicy
     *//*

    public void setMatchingPolicy(MatchingPolicy matchingPolicy) {
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
}
