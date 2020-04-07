package businessLayer.Tournament.Match;

import serviceLayer.userTypes.Administration.Player;

public class yellowCard extends Event {
    Player against;

    /**
     * @param match
     */
    public yellowCard(Match match, Player against) {
        super(match);
        this.against= against;
    }
}
