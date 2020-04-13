package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class Substitue extends Event {
    Player in;
    Player out;

    /**
     * @param match
     */
    public Substitue(Match match, Player in, Player out) {
        super(match);
        this.in= in;
        this.out = out;
    }
}
