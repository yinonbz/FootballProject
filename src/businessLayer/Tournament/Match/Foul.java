package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class Foul extends Event {

    Player inFavor;


    /**
     * constructor
     * @param against
     * @param inFavor
     * @param matchController
     */
    public Foul(Player against , Player inFavor, MatchController matchController) {
        super(matchController,against);
        this.inFavor=inFavor;
    }
}
