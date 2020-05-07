package businessLayer.userTypes.Administration;

import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.LeagueController;
import businessLayer.userTypes.Subscriber;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.userTypes.SystemController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Referee extends Subscriber {
    private String training;
    private LeagueController leagueController;
    private HashMap<Integer, Match> matches;
    private MatchController matchController;

    /**
     * @param username
     * @param password
     * @param name
     * @param training
     * @param leaguesController
     */
    public Referee(String username, String password, String name, String training, LeagueController leaguesController, SystemController systemController) {
        super(username, password, name,systemController);
        this.training = training;
        this.leagueController = leaguesController;
        matches = new HashMap<>();
        this.matchController = matchController;
    }

    /**
     * @param username
     * @param password
     * @param name
     * @param training
     * @param leaguesController
     * @param matchController
     */
    public Referee(String username, String password, String name, String training, LeagueController leaguesController, SystemController systemController, MatchController matchController) {

        super(username, password,name, systemController);
        this.training = training;
        this.leagueController = leaguesController;
        matches = new HashMap<>();
        this.matchController = matchController;
    }


    /**
     * function that checks that the referee is submitted to a game
     * @param matchID
     * @return
     */
    public boolean isSubmittedToAGame (String matchID){
        if(tryParseInt(matchID)){
            int id = Integer.parseInt(matchID);
            return matches.containsKey(id);
        }
        return false;
    }

    /**
     * the function lets the referee to watch a game that he will manage or managed already
     * @param matchID the matchID the referee wants to see
     * @return a string of the game's details
     */
    public String viewMatchDetails(int matchID) {
        if(matches.containsKey(matchID)){
            Match toDisplay = matches.get(matchID);
            return toDisplay.toString();
        }
        return "";
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
    public String getTraining() {
        return training;
    }

    /**
     * @return
     */
    public LeagueController getLeaguesController() {
        return leagueController;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param training
     */
    public void setTraining(String training) {
        this.training = training;
    }

    /**
     * @param leaguesController
     */
    public void setLeaguesController(LeagueController leaguesController) {
        this.leagueController = leaguesController;
    }

    /*
    @Override
    public Boolean editDetails() {
        return null;
    }

*/

    /**
     * The function receives order from the system controller to remove the referee from every match he's registered to
     *
     * @return
     */
    public boolean removeFromAllMatches() {

        for (HashMap.Entry<Integer,Match> entry : matches.entrySet()) {
            Match e = entry.getValue();
            if(!(e.removeReferee(this))){
                return false;
            }
        }
        return true;
    }

    /**
     * getter of the matches that belongs to the referee
     * @return
     */
    public HashMap <Integer, Match> getRefMatches(){
        return matches;
    }

    public MatchController getMatchController() {
        return matchController;
    }

    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
    }

    @Override
    public String toString() {
        return "Referee";
    }

}
