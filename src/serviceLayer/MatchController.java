package serviceLayer;

import businessLayer.Tournament.Match.Match;

import java.util.HashMap;

public class MatchController {

    private SystemController systemController;
    private HashMap<Integer, Match> matches;

    /**
     * the function reports on a foul and add it to the event recorder
     * @param time
     * @param PlayerAgainst
     * @param playerOn
     * @return
     */
    public boolean reportOnFoul(String time, String PlayerAgainst, String playerOn, String matchID){

        return true;
    }

    /**
     * the function reports on a goal and add it to the event recorder
     * @param time
     * @param PlayerGoal
     * @param playerAssist
     * @return
     */
    public boolean reportGoal(String time, String PlayerGoal, String playerAssist, String matchID){

        return true;
    }

    /**
     * the function reports on an injury and add it to the event recorder
     * @param time
     * @param PlayerInjury
     * @return
     */
    public boolean reportOnInjury(String time, String PlayerInjury, String matchID){

        return true;
    }

    /**
     * the function reports on an offside and add it to the event recorder
     * @param time
     * @param playerOfSide
     * @return
     */
    public boolean reportOnOffside(String time, String playerOfSide, String matchID){

        return true;
    }

    /**
     * the function reports on a red card and add it to the event recorder
     * @param time
     * @param PlayerAgainst
     * @return
     */
    public boolean reportOnRedCard(String time, String PlayerAgainst, String matchID){

        return true;
    }

    /**
     * the function reports on a Substitute and add it to the event recorder
     * @param time
     * @param PlayerOn
     * @param playerOff
     * @return
     */
    public boolean reportOnSubstitute(String time, String PlayerOn, String playerOff, String matchID){

        return true;
    }

    /**
     * the function reports on a yellow card and add it to the event recorder
     * @param time
     * @param PlayerAgainst
     * @return
     */
    public boolean reportOnYellowCard(String time, String PlayerAgainst, String matchID){

        return true;
    }





}
