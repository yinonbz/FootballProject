package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import businessLayer.userTypes.Administration.Referee;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Season {

    private int seasonId;
    private Date startDate;
    private Date endDate;
    private League league;
    private List<Match> matches;
    private MatchingPolicy matchingPolicy;
    private RankingPolicy rankingPolicy;
    private List<Team> teams;
    private HashMap<String, Referee> referees;

    /**
     * @param seasonId
     * @param startDate
     * @param endDate
     * @param league
     * @param matchingPolicy
     * @param rankingPolicy
     */
    public Season(int seasonId, Date startDate, Date endDate, League league, MatchingPolicy matchingPolicy, RankingPolicy rankingPolicy) {
        this.seasonId = seasonId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
        this.matchingPolicy = matchingPolicy;
        this.rankingPolicy = rankingPolicy;
    }

    /**
     * @return
     */
    public int getSeasonId() {
        return seasonId;
    }

    /**
     * @param seasonId
     */
    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    /**
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     */

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return
     */

    public League getLeague() {
        return league;
    }

    /**
     * @param league
     */

    public void setLeague(League league) {
        this.league = league;
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
     * @return
     */

    public MatchingPolicy getMatchingPolicy() {
        return matchingPolicy;
    }

    /**
     * @param matchingPolicy
     */

    public void setMatchingPolicy(MatchingPolicy matchingPolicy) {
        this.matchingPolicy = matchingPolicy;
    }

    /**
     * @return
     */

    public RankingPolicy getRankingPolicy() {
        return rankingPolicy;
    }

    /**
     * @param rankingPolicy
     */
    public void setRankingPolicy(RankingPolicy rankingPolicy) {
        this.rankingPolicy = rankingPolicy;
    }

    /**
     * @return
     */

    public List<Team> getTeams() {
        return teams;
    }

    /**
     * @param teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }


    /**
     * The function receives a referee and adds it to the data structure that holds the referees which assigned to the current season
     * @param refToAdd
     * @return true/false
     */
    public boolean addReferee(Referee refToAdd) {

        if (refToAdd == null || referees.containsKey(refToAdd.getUsername())) {
            return false;
        }
        referees.put(refToAdd.getUsername(), refToAdd);
        return true;
    }
}
