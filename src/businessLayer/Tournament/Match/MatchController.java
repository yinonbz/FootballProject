package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Referee;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.HashMap;

public class MatchController {

    private SystemController systemController;
    private HashMap<Integer, Match> matches;


    /**
     * constructor
     * @param systemController
     */
    public MatchController (SystemController systemController){
        this.systemController=systemController;
    }

    /**
     * the function reports on a foul and add it to the event recorder
     * @param time
     * @param playerAgainst
     * @param playerOn
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnFoul(String time, String playerAgainst, String playerOn, String matchID,String username){

        return handleEventTwoVar(time, playerAgainst, playerOn, matchID, username);
    }

    /**
     * the function reports on a goal and add it to the event recorder
     * @param time
     * @param PlayerGoal
     * @param playerAssist
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportGoal(String time, String PlayerGoal, String playerAssist, String matchID,String username){
        return handleEventTwoVar(time,PlayerGoal,playerAssist,matchID,username);
    }

    /**
     * the function reports on an injury and add it to the event recorder
     * @param time
     * @param playerInjury
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnInjury(String time, String playerInjury, String matchID,String username){

        return handleEventOneVar(time, playerInjury, matchID, username);
    }

    /**
     * the function reports on an offside and add it to the event recorder
     * @param time
     * @param playerOffSide
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnOffside(String time, String playerOffSide, String matchID,String username){

        return handleEventOneVar(time, playerOffSide, matchID, username);
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

        return handleEventOneVar(time, PlayerAgainst, matchID, username);
    }

    /**
     * the function reports on a Substitute and add it to the event recorder
     * @param time
     * @param PlayerOn
     * @param playerOff
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnSubstitute(String time, String PlayerOn, String playerOff, String matchID, String username){

        return handleEventTwoVar(time, PlayerOn, playerOff, matchID, username);
    }

    /**
     * the function reports on a yellow card and add it to the event recorder
     * @param time
     * @param PlayerAgainst
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnYellowCard(String time, String PlayerAgainst, String matchID, String username){

        return handleEventOneVar(time, PlayerAgainst, matchID, username);
    }

    private boolean handleEventOneVar(String time, String playerOffSide, String matchID, String username) {
        if (time != null && playerOffSide != null && matchID != null && username != null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof Referee) {
                Referee userReferee = (Referee) user;
                //todo add to match database of events

                return true;
            }
        }
        return false;
    }

    private boolean handleEventTwoVar(String time, String PlayerOn, String playerOff, String matchID, String username) {
        if (time != null && PlayerOn != null && playerOff != null  && matchID != null && username != null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof Referee) {
                Referee userReferee = (Referee) user;
                //todo add to match database of events

                return true;
            }
        }
        return false;
    }

}
