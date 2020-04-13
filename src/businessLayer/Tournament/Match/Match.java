package businessLayer.Tournament.Match;

import businessLayer.Team.Team;
import businessLayer.Tournament.League;
import businessLayer.Tournament.Season;
import businessLayer.userTypes.Administration.Referee;

import javax.security.auth.Refreshable;
import java.util.Date;
import java.util.List;

public class Match {
    private League league;
    private Season season;
    private Team homeTeam;
    private Team awayTeam;
    private List<Referee> referees;
    private String score;
    private String time;
    private Date date;
    private Boolean isFinished;

    /**
     * @param league
     * @param season
     * @param homeTeam
     * @param awayTeam
     * @param referees
     * @param score
     * @param time
     * @param date
     * @param isFinished
     */
    public Match(League league, Season season, Team homeTeam, Team awayTeam, List<Referee> referees, String score, String time, Date date, Boolean isFinished) {
        this.league = league;
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referees = referees;
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
    public List<Referee> getReferee() {
        return referees;
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

    /**
     * @param league
     */
    public void setLeague(League league) {
        this.league = league;
    }

    /**
     * @param season
     */
    public void setSeason(Season season) {
        this.season = season;
    }

    /**
     * @param homeTeam
     */
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * @param awayTeam
     */
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    /**
     * @param referees
     */
    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }

    /**
     * @param score
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @param finished
     */
    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    /**
     * The function removes the received referee from the list of referees in the match, and returns whether the removal was successful or not
     * @param ref
     * @return true/false
     */
    public boolean removeReferee(Referee ref){

        for(Referee e : referees){
            if(e.getUsername().equals(ref.getUsername())){
                referees.remove(e);
                return true;
            }
        }
        return false;
    }
}
