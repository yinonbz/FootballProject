package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public abstract class Event {

    protected MatchController matchController;
    protected Player playerSubject;
    protected int id;

    /**
     * the constructor of an event
     * @param matchController
     * @param playerSubject
     */
    public Event(MatchController matchController, Player playerSubject) {
        this.matchController = matchController;
        this.playerSubject=playerSubject;
    }

    /**
     * setter of id if we want to identify two events that occurred in the same time
     * @param id
     */
    public void setID(int id){
        this.id=id;
    }

    /**
     * the first player
     * @return
     */
    public Player getFirstPlayer(){
        return playerSubject;
    }

    abstract Player getSecondPlayer();

}
