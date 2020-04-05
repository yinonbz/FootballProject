package businessLayer.Tournament.Match;

public abstract class Event {

    private Match match;

    /**
     *
     * @param match
     */
    public Event(Match match) {
        this.match = match;
    }

    /**
     *
     * @return
     */

    public Match getMatch() {
        return match;
    }

    /**
     *
     * @param match
     */

    public void setMatch(Match match) {
        this.match = match;
    }
}
