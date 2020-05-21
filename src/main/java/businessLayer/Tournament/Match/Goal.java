package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class Goal extends Event {

    Player assist;
    boolean isOwnGoal;

    /**
     * constructor
     * @param scorer
     * @param assist
     * @param matchController
     */
    public Goal(Player scorer, Player assist, boolean isOwnGoal, MatchController matchController) {
        super(matchController,scorer);
        this.assist = assist;
        this.isOwnGoal = isOwnGoal;
    }

    /**
     * constructor for a goal without assist
     * @param scorer
     * @param matchController
     */
    public Goal (Player scorer,  boolean isOwnGoal, MatchController matchController ){
        super(matchController,scorer);
        this.assist = null;
        this.isOwnGoal = isOwnGoal;
    }

    public boolean isOwnGoal(){
        return isOwnGoal;
    }

    @Override
    public String toString(){
        return "Goal By: "+playerSubject.getName()+" "+ "assisted: "+" "+assist.getName();
    }

    @Override
    public Player getSecondPlayer() {
        return assist;
    }
}
