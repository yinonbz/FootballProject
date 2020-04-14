package businessLayer.userTypes.Administration;

import javafx.scene.layout.Pane;
import serviceLayer.LeagueController;
import businessLayer.userTypes.Subscriber;
import serviceLayer.MatchController;
import serviceLayer.SystemController;

public class Referee extends Subscriber {
    private String name;
    private String training;
    private LeagueController leagueController;
    private MatchController matchController;

    /**
     * @param username
     * @param password
     * @param name
     * @param training
     * @param leaguesController
     * @param matchController
     */
    public Referee(String username, String password, String name, String training, LeagueController leaguesController, SystemController systemController, MatchController matchController) {
        super(username, password,systemController);
        this.name = name;
        this.training = training;
        this.leagueController = leaguesController;
        this.matchController = matchController;
    }

    /**
     *
     * @param time
     * @param playerAgainst
     * @param playOn
     * @return
     */
    public boolean reportFoul(String time, String playerAgainst , String playOn, String matchID){
        if (!playerAgainst.isEmpty() && !playOn.isEmpty() && tryParseInt(time) && tryParseInt(matchID)){
            int timeEvent = Integer.parseInt(time);
            if(timeEvent>0 && timeEvent<121){
                return matchController.reportOnFoul(time,playerAgainst,playOn,matchID);
            }
        }
        return false;
    }

    /**
     * the function reports on a goal and add it to the event recorder
     * @param time
     * @param PlayerGoal
     * @param playerAssist
     * @return
     */
    public boolean reportGoal(String time, String PlayerGoal, String playerAssist, String matchID){
        if (!PlayerGoal.isEmpty() && !playerAssist.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportGoal(time, PlayerGoal, playerAssist,matchID);
            }
        }
        return false;
    }

    /**
     * the function reports on an injury and add it to the event recorder
     * @param time
     * @param PlayerInjury
     * @return
     */
    public boolean reportOnInjury(String time, String PlayerInjury, String matchID){
        if (!PlayerInjury.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnInjury(time, PlayerInjury,matchID);
            }
        }
        return false;
    }


    /**
     * the function reports on an offside and add it to the event recorder
     * @param time
     * @param playerOfSide
     * @return
     */
    public boolean reportOffside(String time, String playerOfSide, String matchID){
        if (!playerOfSide.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnOffside(time, playerOfSide,matchID);
            }
        }
        return false;
    }


    /**
     * the function reports on a red card and add it to the event recorder
     * @param time
     * @param PlayerAgainst
     * @return
     */
    public boolean reportOnRedCard(String time, String PlayerAgainst, String matchID){
        if (!PlayerAgainst.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnRedCard(time, PlayerAgainst,matchID);
            }
        }
        return false;
    }

    /**
     * the function reports on a Substitute and add it to the event recorder
     * @param time
     * @param PlayerOn
     * @param playerOff
     * @return
     */
    public boolean reportOnSubstitute(String time, String PlayerOn, String playerOff, String matchID){
        if (!PlayerOn.isEmpty() && !playerOff.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnSubstitute(time, PlayerOn, playerOff,matchID);
            }
        }
        return false;

    }

    /**
     * the function reports on a yellow card and add it to the event recorder
     * @param time
     * @param PlayerAgainst
     * @return
     */
    public boolean yellowCard (String time, String PlayerAgainst, String matchID){
        if (!PlayerAgainst.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnYellowCard(time, PlayerAgainst,matchID);
            }
        }
        return false;
    }

    /**
     *
     */
    public void updateEvents(){

    }

    /**
     *
     */
    public void viewMatchDetails(){

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

    @Override
    public Boolean editDetails() {
        return null;
    }

    public MatchController getMatchController() {
        return matchController;
    }

    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
    }
}
