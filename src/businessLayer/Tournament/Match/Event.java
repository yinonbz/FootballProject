package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public abstract class Event {

    protected MatchController matchController;
    protected Player playerSubject;

    /**
     * the constructor of an event
     * @param matchController
     * @param playerSubject
     */
    public Event(MatchController matchController, Player playerSubject) {
        this.matchController = matchController;
        this.playerSubject=playerSubject;
    }

}
