package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class RedCard extends Event {
    Player against;

    /**
     * constructor
     * @param against
     * @param matchController
     */
    public RedCard(Player against, MatchController matchController) {
        super(matchController,against);
        this.against= against;
    }

    @Override
    public String toString(){
        return "Red card: "+playerSubject.getName();
    }
}
