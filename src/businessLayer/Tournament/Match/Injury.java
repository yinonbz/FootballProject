package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class Injury extends Event {
    Player injured;

    /**
     * @param match
     */
    public Injury(Match match, Player injured) {
        super(match);
        this.injured= injured;
    }
}
