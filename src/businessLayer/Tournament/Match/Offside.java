package businessLayer.Tournament.Match;

import serviceLayer.userTypes.Administration.Player;

public class Offside extends Event {
    Player inOffside;

    /**
     * @param match
     */
    public Offside(Match match, Player inOffside) {
        super(match);
        this.inOffside= inOffside;
    }
}
