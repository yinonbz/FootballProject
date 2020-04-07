package businessLayer.Tournament.Match;

import serviceLayer.userTypes.Administration.Player;

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
