package businessLayer.Tournament.Match;

import serviceLayer.userTypes.Administration.Player;

public class Goal extends Event {
    Player scorer;
    Player assist;

    /**
     * @param match
     */
    public Goal(Match match, Player scorer, Player assist) {
        super(match);
        this.scorer= scorer;
        this.assist = assist;
    }
}
