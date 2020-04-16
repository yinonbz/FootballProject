package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

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

    @Override
    public String toString(){
        return "Goal By: "+playerSubject.getName()+" "+ "assisted: "+" "+assist.getName();
    }
}
