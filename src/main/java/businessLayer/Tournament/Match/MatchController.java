package businessLayer.Tournament.Match;

import businessLayer.Exceptions.NotApprovedException;
import businessLayer.userTypes.Administration.AssociationRepresentative;
import businessLayer.userTypes.Administration.Player;
import businessLayer.userTypes.Administration.Referee;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class MatchController {

    private SystemController systemController;


    /**
     * constructor
     */
    public MatchController() {
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
               // if(!checkSameTeam(playerA,playerO)) {
                    Foul foul = new Foul(playerA, playerO, this);
                    return handleEvent(foul, time, matchID);
          //      }
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
    public boolean reportGoal(String time, String PlayerGoal, String playerAssist, String isOwnGoal, String matchID,String username){
        if(checkPermissionOfReferee(username,matchID)){
            Player playerG = getPlayerFromDB(PlayerGoal);
            Player playerA= getPlayerFromDB(playerAssist);
            boolean isOwnG;
            if(isOwnGoal.equals("T")){
                isOwnG = true;
            }
            else{
                isOwnG=false;
            }
            if (playerG != null && playerA != null) {
               // todo ido put this because it is implemented in the gui layer
                //if(checkSameTeam(playerG,playerA)) {
                    Goal goal = new Goal(playerG, playerA, isOwnG, this);
                    return handleEvent(goal, time, matchID);
               // }

            }
            else if(playerG != null){
                Goal goal = new Goal(playerG, isOwnG, this);
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
            if (playerO != null && playerOf != null && !playerO.equals(playerOf)) {
               // if(checkSameTeam(playerO,playerOf)) {//todo ido implemented this on the gui layer
                    Substitute sub = new Substitute(playerO, playerOf, this);
                    return handleEvent(sub, time, matchID);
               // }
            }
        }
        return false;
    }

    /**
     * the function checks that the substitute is legal
     * @param p1
     * @param p2
     * @return
     */
    private boolean checkSameTeam(Player p1, Player p2){
        if(p1.getTeam().equals(p2.getTeam())){
            return true;
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
            //Player player1 = event.getFirstPlayer();
            //Player player2 = event.getSecondPlayer();
            //here we will check events that have two player, we will check each one of the player participates in the game
            /*
            if(player2!=null){
                if(!checkPlayerParticipates(player2,match)){
                    return false;
                }
            }
            */
            //we will always check that the player that is involved actually plays in one of the teams
           // if(checkPlayerParticipates(player1,match)) {
                EventRecord eventRecord = match.getEventRecord();
                eventRecord.addEvent(time, event);
                systemController.addEvent(Integer.parseInt(matchID),time,event.getId(),event);
                systemController.updateMatchToFollowers(match, event.toString());
                return true;
           // }
        }
        return false;
    }

    /**
     * private function that checks the player plays in the game
     * @param player
     * @param match
     * @return
     */
    private Boolean checkPlayerParticipates(Player player, Match match){
        if(!match.getAwayTeam().containPlayer(player) && !match.getHomeTeam().containPlayer(player)){
            return false;
        }
        return true;
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
    public boolean chooseMainReferee (String username, int matchID, String requester){
        Subscriber subscriber = systemController.getSubscriberByUserName(username);
        Subscriber AR = systemController.getSubscriberByUserName(requester);
        if(subscriber instanceof Referee && AR instanceof AssociationRepresentative){
            //int id = Integer.parseInt(matchID);
            Match match = systemController.findMatch(matchID);
            if(match!=null){
                match.chooseMainReferee((Referee)subscriber);
                ((Referee) subscriber).getRefMatches().put(matchID,match);
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
        if(match!=null) {
            if (match.getFinished()) {
                Subscriber subscriber = systemController.getSubscriberByUserName(usernameRequested);
                if (subscriber instanceof Referee) {
                    if (match.isMainReferee((Referee) subscriber)) {
                        //return match.getEventRecord().removeEvent(timeOfEvent, eventId);
                        systemController.removeEventFromMatch(matchID,timeOfEvent,eventId);
                    }
                    throw new NotApprovedException("This Referee is not a Main Referee.");
                }
            }
        }
        return false;
    }

    /**
     * The function verifies the user is an AR and that the match exists, updates the match's date and returns whether
     * the operation was successful or not
     *
     * @param username
     * @param matchID
     * @param date
     * @return
     */
    public boolean setDateForMatch(String username, String matchID, Date date) {

        if (username == null || matchID == null) {
            return false;
        }
        Subscriber user = systemController.getSubscriberByUserName(username);
        int id = Integer.parseInt(matchID);
        Match match = systemController.findMatch(id);
        if (user instanceof AssociationRepresentative && match != null) {
            String eventToUpdate = match.defineDate(date);
            systemController.updateMatchChangesToReferees(match, eventToUpdate);
            return true;
        }
        return false;
    }

    /**
     * The function verifies the user is an AR, that the match exists and that the stadium exists, updates the match's
     * location and returns whether the operation was successful or not
     * @param username
     * @param matchID
     * @param stadiumName
     * @return
     */
    public boolean setStadiumForMatch(String username, String matchID, String stadiumName) {

        if (username != null && matchID != null && stadiumName != null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            int id = Integer.parseInt(matchID);
            Match match = systemController.findMatch(id);
            Stadium stadium = systemController.findStadium(stadiumName);
            if (user instanceof AssociationRepresentative && stadium != null && match != null) {
                String eventToUpdate = match.defineStadium(stadium);
                systemController.updateMatchChangesToReferees(match, eventToUpdate);
                return true;
            }
        }
        return false;
    }

    //todo ido added this function need to connect to DB!!
    public ArrayList<String> getRefereeMatch(String rUserName) {
        if (systemController.containsReferee(rUserName)) {
            return systemController.getAllRefsGameID(rUserName);
        } else {
            return null;
        }
    }

    public String getDetailsOnMatch(int match) {
        return systemController.getDetailsOnMatch(match);
    }

    public ArrayList<String> getAllPlayerOfMatch(int matchId) {
        return systemController.getPlayerInMatch(matchId);
    }
}
