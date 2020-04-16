package serviceLayer;

import businessLayer.Tournament.LeagueController;
import businessLayer.Tournament.Match.MatchController;
import businessLayer.userTypes.SystemController;

public class MatchService {
    private SystemController systemController;
    private LeagueController leagueController;
    private MatchController matchController;

    public MatchService(){
        systemController = SystemController.SystemController();
        leagueController = systemController.getLeagueController();
        //todo add matchController as singelton?
    }


    //-------------------------Referee-------------------------------

    /**
     * The function receives the details to assign a foul and a referee's username from the interface layer and calls the reporting method in the business layer
     * @param time
     * @param playerAgainst
     * @param playOn
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportFoulThroughReferee(String time, String playerAgainst, String playOn, String matchID, String username) {
        if (!playerAgainst.isEmpty() && !playOn.isEmpty() && tryParseInt(time) && tryParseInt(matchID)){
            int timeEvent = Integer.parseInt(time);
            if(timeEvent>0 && timeEvent<121){
                return matchController.reportOnFoul(time,playerAgainst,playOn,matchID,username);
            }
        }
        return false;
    }

    /**
     * The function receives the details to assign a goal and a referee's username from the interface layer and calls the reporting method in the business layer
     * @param time
     * @param PlayerGoal
     * @param playerAssist
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportGoalThroughReferee(String time, String PlayerGoal, String playerAssist, String matchID, String username){

        if (!PlayerGoal.isEmpty() && !playerAssist.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportGoal(time, PlayerGoal, playerAssist,matchID,username);
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
    public boolean reportOnInjury(String time, String PlayerInjury, String matchID,String username){
        if (!PlayerInjury.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnInjury(time, PlayerInjury,matchID,username);
            }
        }
        return false;
    }

    /**
     * the function reports on an offside and add it to the event recorder
     * @param time
     * @param playerOfSide
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOffside(String time, String playerOfSide, String matchID,String username){
        if (!playerOfSide.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnOffside(time, playerOfSide,matchID,username);
            }
        }
        return false;
    }

    /**
     * the function reports on a red card and add it to the event recorder
     * @param time
     * @param PlayerAgainst
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnRedCard(String time, String PlayerAgainst, String matchID,String username){
        if (!PlayerAgainst.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnRedCard(time, PlayerAgainst,matchID,username);
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
    public boolean reportOnSubstitute(String time, String PlayerOn, String playerOff, String matchID,String username){
        if (!PlayerOn.isEmpty() && !playerOff.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnSubstitute(time, PlayerOn, playerOff,matchID,username);
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
    public boolean yellowCard (String time, String PlayerAgainst, String matchID ,String username){
        if (!PlayerAgainst.isEmpty() && tryParseInt(time) && tryParseInt(matchID)) {
            int timeEvent = Integer.parseInt(time);
            if (timeEvent > 0 && timeEvent < 121) {
                return matchController.reportOnYellowCard(time, PlayerAgainst,matchID,username);
            }
        }
        return false;
    }

    /**
     * the function lets a referee to view the details on a game he manages
     * @param matchID the match id the referee chose
     * @param usernameRequested the username the user wants to check
     * @param username the username of the user
     * @return the string of the details of the match
     * UC 10.2
     */
    public String viewMatchDetails(String matchID, String usernameRequested ,String username){
        if(tryParseInt(matchID)){
            int id = Integer.parseInt(matchID);
            return matchController.displayMatchDetails(id,usernameRequested, username);
        }
        return "";
    }

    /**
     * the function lets a main referee to edit events after a game
     * @param matchID
     * @param usernameRequested
     * @return
     * UC 10.4
     */
    //int matchID, String usernameRequested, String timeOfEvent, String eventId)
    public boolean removeEventByMainReferee (String time, String matchID, String usernameRequested, String eventID){
        if(tryParseInt(matchID) && tryParseInt(eventID) && tryParseInt(time)){
            int id = Integer.parseInt(matchID);
            int evID = Integer.parseInt(eventID);
            return matchController.removeEventByMainReferee(id,usernameRequested,time,evID);
        }
        return false;
    }

    /**
     * private function that checks that a string represents an interger
     * @param value the string
     * @return true if it an integer
     */
    protected boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
