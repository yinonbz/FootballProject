package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class Offside extends Event {
    Player inOffside;

    /**
     * constructor
     * @param inOffside
     * @param matchController
     */
    public Offside(Player inOffside, MatchController matchController) {
        super(matchController,inOffside);
        this.inOffside= inOffside;
    }

    @Override
    public String toString(){
        return "Offside By: "+playerSubject.getName();
    }

    @Override
    Player getSecondPlayer() {
        return null;
    }
}
