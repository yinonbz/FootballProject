package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import serviceLayer.MatchController;

public class Substitue extends Event {
    Player in;
    Player out;

    /**
     * constructor
     * @param in
     * @param out
     * @param matchController
     */
    public Substitue(Player in, Player out, MatchController matchController) {
        super(matchController,in);
        this.in= in;
        this.out = out;
    }
}
