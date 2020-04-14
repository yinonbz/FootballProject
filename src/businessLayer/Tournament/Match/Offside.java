package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import serviceLayer.MatchController;

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
}
