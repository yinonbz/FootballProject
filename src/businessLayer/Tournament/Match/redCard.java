package businessLayer.Tournament.Match;

import serviceLayer.userTypes.Administration.Player;

public class redCard extends Event {
    Player against;

    /**
     * @param match
     */
    public redCard(Match match, Player against) {
        super(match);
        this.against= against;
    }
}
