package businessLayer.Tournament.Match;

import businessLayer.Team.Team;
import businessLayer.Tournament.League;
import businessLayer.Tournament.Season;
import businessLayer.userTypes.Administration.Referee;

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
    private int matchId;
    private Stadium stadium;
    private int numberOfFans;
    private static int index=1;
    private EventRecord eventRecord;
    private Referee mainReferee;
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
     * @param numberOfFans
     */
    public Match(League league, Season season, Team homeTeam, Team awayTeam, List<Referee> referees, String score, String time, Date date, Boolean isFinished, Stadium stadium, int numberOfFans, EventRecord eventRecord, Referee mainReferee) {
        this.league = league;
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referees = referees;
        this.score = score;
        this.time = time;
        this.date = date;
        this.isFinished = isFinished;
        this.matchId=index;
        this.stadium=stadium;
        this.numberOfFans=numberOfFans;
        this.eventRecord=eventRecord;
        index++;

    }

    /**
     * constructor for match policy
     * @param home the home team
     * @param away the away team
     * @param stadium the stadium
     */
    public Match (Team home, Team away, Stadium stadium){
        this.homeTeam=home;
        this.awayTeam=away;
        this.stadium=stadium;
        this.matchId=index;
        eventRecord = new EventRecord(this);
        this.isFinished=false;
        index++;
    }

    /**
     * function that let to choose a main referee to a game
     * @param mainReferee
     * @return
     */
    public boolean chooseMainReferee(Referee mainReferee){
        if(mainReferee!=null) {
            this.mainReferee = mainReferee;
            return true;
        }
        return false;
    }

    /**
     * function that checks that a referee is the same main referee
     * @param otherReferee
     * @return
     */
    public boolean isMainReferee(Referee otherReferee){
        if(mainReferee!=null && otherReferee!=null){
            if(mainReferee.equals(otherReferee)){
                return true;
            }
        }
        return false;
    }

    /**
     * opens an event recorder for the game
     */
    public void startTheGame(){
        eventRecord = new EventRecord(this);
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
     *
     * @return
     */
    public int getMatchId() {
        return matchId;
    }

    /**
     *
     * @param matchId
     */
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public int getNumerOfFans() {
        return numberOfFans;
    }

    public void setNumerOfFans(int numberOfFans) {
        this.numberOfFans = numberOfFans;
    }

    @Override
    public String toString(){
        String ref="No information";
        String dateS = "No information";
        if(mainReferee!=null){
            ref=mainReferee.getName();
        }
        if(date!=null){
            dateS=date.toString();
        }
        return "Match id: " + matchId + "\n" + "Home: " + homeTeam.getTeamName() + "\n" + "Away: "
                + awayTeam.getTeamName() + "\n" +
                "Stadium: " + stadium.getName() + "\n" + "Date: " + dateS +"\n" + "Referee: "+ref;
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

    public EventRecord getEventRecord() {
        return eventRecord;
    }
}
