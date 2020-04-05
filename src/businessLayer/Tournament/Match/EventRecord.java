package businessLayer.Tournament.Match;

import java.util.Date;

public class EventRecord {

    private Match match;
    private Date date;
    private String time;
    private String matchTime;
    private String description;

    /**
     *
     * @param match
     * @param date
     * @param time
     * @param matchTime
     * @param description
     */
    public EventRecord(Match match, Date date, String time, String matchTime, String description) {
        this.match = match;
        this.date = date;
        this.time = time;
        this.matchTime = matchTime;
        this.description = description;
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

    /**
     *
     * @return
     */

    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     */

    public String getMatchTime() {
        return matchTime;
    }

    /**
     *
     * @param matchTime
     */

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    /**
     *
     * @return
     */

    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */

    public void setDescription(String description) {
        this.description = description;
    }
}
