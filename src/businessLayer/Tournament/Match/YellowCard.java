package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class YellowCard extends Event {

    Player against;

    /**
     * constructor
     * @param against
     * @param matchController
     */
    public YellowCard(Player against, MatchController matchController) {
        super(matchController, against);
        this.against= against;
    }

    @Override
    public String toString(){
        return "Yellow card: "+playerSubject.getName();
    }

    @Override
    Player getSecondPlayer() {
        return null;
    }
}
