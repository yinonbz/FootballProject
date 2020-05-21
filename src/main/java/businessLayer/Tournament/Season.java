package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import businessLayer.userTypes.Administration.Referee;

import java.util.*;

public class Season {

    private int seasonId;
    private Date startDate;
    private Date endDate;
    private League league;
    //private List<Match> matches;
    private MatchingPolicy matchingPolicy;
    private ARankingPolicy rankingPolicy;
    private HashMap<String, Referee> referees;
    private HashMap<String, Team> teams;
    private HashMap<Team, LinkedList<Integer>> leagueTable;
    private HashMap <Integer, Match> matchesOfTheSeason;

    /**
     * @param seasonId
     * @param startDate
     * @param endDate
     * @param league
     * @param matchingPolicy
     * @param rankingPolicy
     */
    public Season(int seasonId, Date startDate, Date endDate, League league, MatchingPolicy matchingPolicy, ARankingPolicy rankingPolicy, HashMap<Team, LinkedList<Integer>> leagueTable, HashMap <Integer, Match> matchesOfTheSeason) {
        this.seasonId = seasonId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
        this.matchingPolicy = matchingPolicy;
        this.rankingPolicy = rankingPolicy;
        referees = new HashMap<>();
        teams = new HashMap<>();
        this.matchesOfTheSeason=matchesOfTheSeason;
        //matches = new ArrayList<>();
        initializeTable(); // here we initialize the table
    }

    public Season (League league, int seasonID, Date startDate, Date endDate, ARankingPolicy aRankingPolicy, HashMap<Team, LinkedList<Integer>> leagueTable,
                   HashMap<Integer,Match> matchesOfTheSeason, HashMap<String,Referee> referees, String matchingPolicy) {
        this.league=league;
        this.seasonId=seasonID;
        this.startDate=startDate;
        this.endDate=endDate;
        this.rankingPolicy=aRankingPolicy;
        this.referees=referees;
        this.leagueTable=leagueTable;
        this.matchesOfTheSeason=matchesOfTheSeason;
        if (matchingPolicy.equals("ClassicMatchPolicy")) {
            this.matchingPolicy = new ClassicMatchPolicy(league, this);
        } else if (matchingPolicy.equals("SingleMatchPolicy")) {
            this.matchingPolicy = new SingleMatchPolicy(league, this);
        }
    }

    /**
     * constructor of a new season without a policy
     *
     * @param seasonId
     * @param startDate
     * @param endDate
     * @param league
     */
    public Season(int seasonId, Date startDate, Date endDate, League league, int win, int lose, int tie, String matchingPolicy) {
        this.seasonId = seasonId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
        referees = new HashMap<>();
        teams = new HashMap<>();
        //matches = new ArrayList<>();
        leagueTable = new HashMap<>();
        this.rankingPolicy = new ARankingPolicy(win, lose, tie);
        if (matchingPolicy.equals("ClassicMatchPolicy")) {
            this.matchingPolicy = new ClassicMatchPolicy(league, this);
        } else if (matchingPolicy.equals("SingleMatchPolicy")) {
            this.matchingPolicy = new SingleMatchPolicy(league, this);
        } else {
            //todo: add an error saying not legal policy name (or check it before reaching this far)
        }
    }


    /**
     * initializes the table of the league
     */
    public void initializeTable() {
        LinkedList<Integer> temp = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            temp.add(0);
        }
        for (HashMap.Entry<String, Team> team : teams.entrySet()) {
            Team tempTeam = team.getValue();
            leagueTable.put(tempTeam, temp);
        }
    }

    /**
     * @return
     */
    /*
    public int getSeasonId() {
        return seasonId;
    }
    */

    /**
     * @param seasonId
     */
    /*
    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }
    */

    /**
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    /*
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    */

    /**
     * @return
     */
    /*
    public Date getEndDate() {
        return endDate;
    }
    */

    /**
     * @param endDate
     */

    /*
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    */

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
    /*
    public List<Match> getMatches() {
        return matches;
    }
    */
    /**
     * @param matches
     */
    /*
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
    /*
    /**
     * @return
     */
    /*
    public MatchingPolicy getMatchingPolicy() {
        return matchingPolicy;
    }
    */
    /**
     * @param matchingPolicy
     */
/*
    public void setMatchingPolicy(MatchingPolicy matchingPolicy) {
        this.matchingPolicy = matchingPolicy;
    }
*/
    /**
     * @return
     */
/*
    public RankingPolicy getRankingPolicy() {
        return rankingPolicy;
    }
*/

    /**
     * @param rankingPolicy
     */

    public void setRankingPolicy(ARankingPolicy rankingPolicy) {
        this.rankingPolicy = rankingPolicy;
    }


    /**
     * @return
     */

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    /**
     * @param teams
     */
    public void setTeams(HashMap<String, Team> teams) {
        this.teams = teams;
    }

    /**
     * getter of league table
     *
     * @return
     */
    public HashMap<Team, LinkedList<Integer>> getLeagueTable() {
        return leagueTable;
    }


    /**
     * The function receives a referee and adds it to the data structure that holds the referees which assigned to the current season
     *
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

    /**
     * a function that adds a team to a season
     * @param team
     * @return
     */
    public boolean addTeamToSeason(Team team){
        if(team!=null){
            teams.put(team.getTeamName(),team);

            return true;
        }
        return false;
    }

    /**
     * the function activates the match policy and build the match table
     * @param leagueController
     */
    public boolean activateMatchPolicy(LeagueController leagueController){
        //in order to use the match policy only once
        if(matchesOfTheSeason==null) {
            this.matchesOfTheSeason = matchingPolicy.activatePolicy(teams, leagueController);
            leagueController.updateMatchTableInDB(matchesOfTheSeason,league.getLeagueName(),seasonId);
            if (matchesOfTheSeason != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the season contains a match
     * @param marchID
     * @return
     */
    public boolean seasonContainsMatch(int marchID){
        return matchesOfTheSeason.containsKey(marchID);
    }

    /**
     * the function updates the table of the league
     * @param match
     * @return
     */
    public boolean updateMatchTableRank(Match match){
        return rankingPolicy.updateRank(match,leagueTable);
    }

    /**
     * a setter if tge matches of the season
     */
    public void  setMatchesOfTheSeason(HashMap <Integer, Match> matchesOfTheSeason){
        this.matchesOfTheSeason=matchesOfTheSeason;
    }

    /**
     * getter of the match table of a season
     * @return
     */
    public HashMap <Integer,Match> getMatchesOfTheSeason(){
        return matchesOfTheSeason;
    }

}
