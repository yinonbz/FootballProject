package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class Injury extends Event {

    Player injured;

    /**
     * constructor
     * @param injured
     * @param matchController
     */
    public Injury(Player injured, MatchController matchController) {
        super(matchController,injured);
        this.injured= injured;
    }

    @Override
    public String toString(){
        return "Injured: "+playerSubject.getName();
    }
}
