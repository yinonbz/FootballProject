package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import businessLayer.userTypes.Administration.Referee;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;



public class MatchController {

    private SystemController systemController;


    /**
     * constructor
     */
    public MatchController (){
        //this.systemController=systemController;
    }

    public void setSystemController(SystemController systemController) {
        this.systemController = systemController;
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
    public boolean reportOnFoul(String time, String playerAgainst, String playerOn, String matchID ,String username){
        if(checkPermissionOfReferee(username,matchID)){
            Player playerA = getPlayerFromDB(playerAgainst);
            Player playerO= getPlayerFromDB(playerOn);
            if (playerA != null && playerO != null) {
                Foul foul = new Foul(playerA,playerO,this);
                return handleEvent(foul, time, matchID);
            }
        }
        return false;
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
        if(checkPermissionOfReferee(username,matchID)){
            Player playerG = getPlayerFromDB(PlayerGoal);
            Player playerA= getPlayerFromDB(playerAssist);
            if (playerG != null && playerA != null) {
                Goal goal = new Goal(playerG,playerA,this);
                return handleEvent(goal, time, matchID);
            }
        }
        return false;
    }

    /**
     * the function reports on an injury and add it to the event recorder
     * @param time
     * @param playerInjury
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnInjury(String time, String playerInjury, String matchID ,String username){
        if(checkPermissionOfReferee(username,matchID)){
            Player player = getPlayerFromDB(playerInjury);
            if (player != null) {
                Injury injury = new Injury(player,this);
                return handleEvent(injury, time, matchID);
            }
        }
        return false;
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
        if(checkPermissionOfReferee(username,matchID)){
            Player player = getPlayerFromDB(playerOffSide);
            if (player != null) {
                Offside offside = new Offside(player,this);
                return handleEvent(offside, time, matchID);
            }
        }
        return false;
    }

    /**
     * the function reports on a red card and add it to the event recorder
     * @param time
     * @param playerAgainst
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnRedCard(String time, String playerAgainst,String matchID,String username){
        if(checkPermissionOfReferee(username,matchID)){
            Player player = getPlayerFromDB(playerAgainst);
            if (player != null) {
                RedCard redCard = new RedCard(player,this);
                return handleEvent(redCard, time, matchID);
            }
        }
        return false;
    }

    /**
     * the function reports on a Substitute and add it to the event recorder
     * @param time
     * @param playerOn
     * @param playerOff
     * @param matchID
     * @param username
     * @return
     */
    public boolean reportOnSubstitute(String time, String playerOn, String playerOff, String matchID, String username){
        if(checkPermissionOfReferee(username,matchID)){
            Player playerO = getPlayerFromDB(playerOn);
            Player playerOf= getPlayerFromDB(playerOff);
            if (playerO != null && playerOf != null) {
                Substitue sub = new Substitue(playerO,playerOf,this);
                return handleEvent(sub, time, matchID);
            }
        }
        return false;
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
        if(checkPermissionOfReferee(username,matchID)){
            Player player = getPlayerFromDB(PlayerAgainst);
            if (player != null) {
                YellowCard yellowCard = new YellowCard(player,this);
                return handleEvent(yellowCard, time, matchID);
            }
        }
        return false;
    }

    /**
     * the function checks the referee can add events to a game he is submitted to
     * @param username
     * @param matchID
     * @return
     */
    private boolean checkPermissionOfReferee(String username, String matchID){
        Subscriber subscriber = systemController.getSubscriberByUserName(username);
        if(subscriber instanceof Referee){
            if(((Referee) subscriber).isSubmittedToAGame(matchID)){
                return true;
            }
        }
        return false;
    }

    /*
    private boolean handleEvent(Event event, String time, String matchID, String username) {
        if (matchID != null && username != null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof Referee) {
                Referee userReferee = (Referee) user;
                if(userReferee.isSubmittedToAGame(matchID)){

                }

                return true;
            }
        }
        return false;
    }
    */

    /**
     * the function that add an event to the event recorder
     * @param event
     * @param time
     * @param matchID
     * @return
     */
    private boolean handleEvent(Event event, String time, String matchID) {
        int id = Integer.parseInt(matchID);
        Match match = systemController.findMatch(id);
        if(match!=null){
            EventRecord eventRecord = match.getEventRecord();
            eventRecord.addEvent(time,event);
        }
        return false;
    }

    /**
     * function that get a player from the DB in order to record an event
     * @param playerName
     * @return
     */
    private Player getPlayerFromDB(String playerName){
        Subscriber subscriber = systemController.getSubscriberByUserName(playerName);
        if(subscriber instanceof Player){
            return (Player) subscriber;
        }
        else{
            return null;
        }
    }

    /**
     * the function lets the referee to watch a details of a match that he manages
     * @param matchID
     * @param usernameRequested
     * @param username
     * @return string of the match details
     */
    public String displayMatchDetails(int matchID, String usernameRequested, String username){
        Subscriber subscriber = systemController.getSubscriberByUserName(username);
        if(subscriber instanceof Referee){
            if(subscriber.getUsername().equals(usernameRequested)){
                return ((Referee) subscriber).viewMatchDetails(matchID);
            }
        }
        return "";
    }

    /**
     * the function chooses a main referee to a game
     * @param username the username of the referee
     * @param matchID the match id we want to choose
     * @return
     */
    public boolean chooseMainReferee (String username, String matchID){
        Subscriber subscriber = systemController.getSubscriberByUserName(username);
        if(subscriber instanceof Referee){
            int id = Integer.parseInt(matchID);
            Match match = systemController.findMatch(id);
            if(match!=null){
                match.chooseMainReferee((Referee)subscriber);
                return true;
            }
        }
        return false;
    }

    /**
     * remove event by the main referee of the game
     * @param matchID
     * @param usernameRequested
     * @param timeOfEvent
     * @param eventId
     * @return
     */
    public boolean removeEventByMainReferee(int matchID, String usernameRequested, String timeOfEvent, int eventId){
        Match match = systemController.findMatch(matchID);
        if(match!=null){
            Subscriber subscriber = systemController.getSubscriberByUserName(usernameRequested);
            if(subscriber instanceof Referee){
                if(match.isMainReferee((Referee)subscriber)){
                    return match.getEventRecord().removeEvent(timeOfEvent,eventId);
                }
            }
        }
        return false;
    }


}
