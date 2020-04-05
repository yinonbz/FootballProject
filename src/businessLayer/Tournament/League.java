package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import serviceLayer.SystemController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class League {

    private String leagueName;
    private int numberOfLeagues;
    private Map<Team,Integer> scoreTable;
    //private Map<businessLayer.Tournament.Season,businessLayer.Tournament.MatchingPolicy> matchingPolicy; // probably not needed
    //private Map<businessLayer.Tournament.Season,businessLayer.Tournament.RankingPolicy> rankingPolicy; // probably not needed
    private SystemController systemController;
    private List<Season> seasons;
    private List<Match> matches;

    /**
     *
     * @param leagueName
     * @param numberOfLeagues
     */
    public League(String leagueName, int numberOfLeagues) {
        this.leagueName = leagueName;
        this.numberOfLeagues = numberOfLeagues;
        scoreTable = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public String getLeagueName() {
        return leagueName;
    }

    /**
     *
     * @param leagueName
     */
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    /**
     *
     * @return
     */
    public int getNumberOfLeagues() {
        return numberOfLeagues;
    }

    /**
     *
     * @param numberOfLeagues
     */

    public void setNumberOfLeagues(int numberOfLeagues) {
        this.numberOfLeagues = numberOfLeagues;
    }

    /**
     *
     * @return
     */
    public Map<Team, Integer> getScoreTable() {
        return scoreTable;
    }

    /**
     *
     * @param scoreTable
     */
    public void setScoreTable(Map<Team, Integer> scoreTable) {
        this.scoreTable = scoreTable;
    }

    /**
     *
     * @return
     */
    public SystemController getSystemController() {
        return systemController;
    }

    /**
     *
     * @param systemController
     */
    public void setSystemController(SystemController systemController) {
        this.systemController = systemController;
    }

    /**
     *
     * @return
     */
    public List<Season> getSeasons() {
        return seasons;
    }

    /**
     *
     * @param seasons
     */

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    /**
     *
     * @return
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     *
     * @param matches
     */
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
