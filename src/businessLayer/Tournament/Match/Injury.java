package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import serviceLayer.MatchController;

public class Injury extends Event {

    Player injured;

    /**
     * constructor
     * @param match
     * @param injured
     * @param matchController
     */
    public Injury(Match match, Player injured, MatchController matchController) {
        super(matchController,injured);
        this.injured= injured;
    }
}
