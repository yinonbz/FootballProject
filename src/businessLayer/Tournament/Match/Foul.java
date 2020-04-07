package businessLayer.Tournament.Match;

import serviceLayer.userTypes.Administration.Player;

public class Foul extends Event {
    Player against;
    Player inFavor;

    /**
     * @param match
     */
    public Foul(Match match, Player against , Player inFavor) {
        super(match);
        this.against= against;
        this.inFavor = inFavor;
    }
}
