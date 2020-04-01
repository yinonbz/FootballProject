public class Event {

    private EventType event;
    private Match match;

    /**
     *
     * @param event
     * @param match
     */
    public Event(EventType event, Match match) {
        this.event = event;
        this.match = match;
    }

    /**
     *
     * @return
     */
    public EventType getEvent() {
        return event;
    }

    /**
     *
     * @param event
     */
    public void setEvent(EventType event) {
        this.event = event;
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
