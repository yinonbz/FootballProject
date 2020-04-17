package businessLayer.Tournament.Match;

import businessLayer.userTypes.Administration.Player;

public class Substitute extends Event {
    Player in;
    Player out;

    /**
     * constructor
     * @param in
     * @param out
     * @param matchController
     */
    public Substitute(Player in, Player out, MatchController matchController) {
        super(matchController,in);
        this.in= in;
        this.out = out;
    }

    @Override
    public String toString(){
        return "Substitute On: "+playerSubject.getName()+" "+ "Off: "+" "+out.getName();
    }

    @Override
    Player getSecondPlayer() {
        return out;
    }
}
