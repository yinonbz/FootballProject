package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;
import serviceLayer.MatchController;

public class Goal extends Event {
    Player scorer;
    Player assist;

    /**
     * constructor
     * @param scorer
     * @param assist
     * @param matchController
     */
    public Goal(Player scorer, Player assist, MatchController matchController) {
        super(matchController,scorer);
        this.assist = assist;
    }
}
