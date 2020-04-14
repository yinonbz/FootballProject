package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import serviceLayer.MatchController;

public class yellowCard extends Event {

    Player against;

    /**
     * constructor
     * @param against
     * @param matchController
     */
    public yellowCard(Player against, MatchController matchController) {
        super(matchController, against);
        this.against= against;
    }
}
