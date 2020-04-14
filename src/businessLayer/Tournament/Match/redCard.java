package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import serviceLayer.MatchController;

public class redCard extends Event {
    Player against;

    /**
     * constructor
     * @param against
     * @param matchController
     */
    public redCard(Player against, MatchController matchController) {
        super(matchController,against);
        this.against= against;
    }
}
