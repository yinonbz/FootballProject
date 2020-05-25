package businessLayer.Tournament.Match;

import businessLayer.Team.Team;
import businessLayer.userTypes.SystemController;

import java.util.HashMap;
import java.util.LinkedList;

public class EventRecord {
    private int matchId;
    private Match match;
    private HashMap<String, LinkedList <Event>> gameEvents;


    /**
     * constructor
     * @param match the match that the events belongs to
     */
    public EventRecord (Match match){
        this.match=match;
        gameEvents = new HashMap<>();
    }
    /**
     * constructor
     * @param matchId the match that the events belongs to
     */
    public EventRecord (int matchId){
        this.matchId=matchId;
        gameEvents = new HashMap<>();
    }
    /**
     * adding a new event to the event recorder
     * @param time the time that the event have occurred
     * @param event the event
     */
    public void addEvent (String time, Event event){
        //if the game has already an event in the current minute
        if(event!=null) {
            if (gameEvents.containsKey(time)) {
                LinkedList<Event> temp = gameEvents.get(time);
                event.setID(temp.size());
                temp.add(event);
                gameEvents.remove(time);
                gameEvents.put(time, temp);
            } else {
                LinkedList<Event> newList = new LinkedList<>();
                event.setID(0);
                newList.add(event);
                gameEvents.put(time, newList);
            }
            //here we will update the score of the game
            if(event instanceof Goal){
                if(this.match==null){
                    //SystemController systemController = SystemController.SystemController();
                    //this.match = systemController.findMatch(matchId);
                }
                /*
                Team home = match.getHomeTeam();
                int numGoalsHome = match.getScore()[0];
                int numGoalsAway = match.getScore()[1];
                if(home.containPlayer(event.getFirstPlayer()) && !((Goal) event).isOwnGoal){
                    int [] newScore = {numGoalsHome+1,numGoalsAway};
                    match.setScore(newScore);
                }
                else if(home.containPlayer(event.getFirstPlayer()) && ((Goal) event).isOwnGoal){
                    int [] newScore = {numGoalsHome,numGoalsAway+1};
                    match.setScore(newScore);
                }
                else if(((Goal) event).isOwnGoal){
                    int [] newScore = {numGoalsHome+1,numGoalsAway};
                    match.setScore(newScore);
                }
                else{
                    int [] newScore = {numGoalsHome,numGoalsAway+1};
                    match.setScore(newScore);
                }
                */
            }
        }
    }

    /**
     * lets the referee to remove an event from the recorder
     * @param time the time of the event
     * @param eventID
     * @return true if the event was removed successfully
     */
    public boolean removeEvent (String time, int eventID){
        if(gameEvents.containsKey(time)){
            LinkedList<Event> temp = gameEvents.get(time);
            for(Event event : temp){
                if(event.id == eventID){
                    temp.remove(event);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * the function swaps an old event with a new event
     * @param time the time of the event
     * @param oldEvent the old event we want to remove
     * @param newEvent the new event we want to add
     * @return true if the change was done successfully
     */
    /*
    public boolean editEvent (String time, Event oldEvent, Event newEvent){
        if(removeEvent(time,oldEvent)){
            addEvent(time,newEvent);
            return true;
        }
        return false;
    }
    */

    /**
     *
     * @return
     */
    public Match getMatch() {
        return match;
    }

    /**
     *
     * @param match
     */

    public void setMatch(Match match) {
        this.match = match;
    }


    /**
     * a getter for the game events of the match
     * @return
     */
    public HashMap<String, LinkedList<Event>> getGameEvents() {
        return gameEvents;
    }

    @Override
    public String toString(){
        String events = "";
        if(gameEvents!=null){
            for(LinkedList<Event> listEvents : gameEvents.values()){
                if(listEvents.size()>1){
                    for(Event event : listEvents){
                        events=events+event.toString()+"\n";
                    }
                }
            }
        }
        return events;
    }

}
