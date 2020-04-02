import java.util.Date;
import java.util.List;

abstract public class Match {
    private League league;
    private Season season;
    private Team homeTeam;
    private Team awayTeam;
    private Referee referee;
    private String score;
    private String time;
    private Date date;
    private Boolean isFinished;

    /**
     * @param league
     * @param season
     * @param homeTeam
     * @param awayTeam
     * @param referee
     * @param score
     * @param time
     * @param date
     * @param isFinished
     */
    public Match(League league, Season season, Team homeTeam, Team awayTeam, Referee referee, String score, String time, Date date, Boolean isFinished) {
        this.league = league;
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referee = referee;
        this.score = score;
        this.time = time;
        this.date = date;
        this.isFinished = isFinished;
    }

    /**
     * @return
     */
    public Event notifyEvent(){
        return null;
    }

    /**
     * @return
     */
    public League getLeague() {
        return league;
    }

    /**
     * @return
     */
    public Season getSeason() {
        return season;
    }

    /**
     * @return
     */
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     * @return
     */
    public Team getAwayTeam() {
        return awayTeam;
    }

    /**
     * @return
     */
    public Referee getReferee() {
        return referee;
    }

    /**
     * @return
     */
    public String getScore() {
        return score;
    }

    /**
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return
     */
    public Boolean getFinished() {
        return isFinished;
    }
}
