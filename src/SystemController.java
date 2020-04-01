import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Map;

public class SystemController {

    private Map<Subscriber,List<String>> userNotifications;
    private AlertSystem alertSystem;
    private RecommendationSystem recommendationSystem;
    private List<Guest> onlineGuests;
    private List<Subscriber> systemSubscribers;
    private List<Admin> admins;
    private LoggingSystem loggingSystem;
    private List<League> leagues;

    public SystemController() {

    }

    /**
     *
     * @param subscriber
     * @param notification
     * @return
     */
    public boolean sendNotification(Subscriber subscriber, String notification){

        return true;
    }

    /**
     *
     * @param logs
     * @return
     */

    public boolean sendLogs(List<String> logs){

        return true;
    }

    /**
     *
     * @param followers
     * @param Alerts
     * @return
     */

    public boolean sendAlert(List<Subscriber> followers, List<String> Alerts){

        return true;
    }


    /**
     *
     * @return
     */
    public Map<Subscriber, String> getUserNotifications() {
        return null;
    }

    /**
     *
     * @param userNotifications
     */

    public void setUserNotifications(Map<Subscriber, List<String>> userNotifications) {
        this.userNotifications = userNotifications;
    }

    /**
     *
     * @return
     */

    public AlertSystem getAlertSystem() {
        return alertSystem;
    }

    /**
     *
     * @param alertSystem
     */

    public void setAlertSystem(AlertSystem alertSystem) {
        this.alertSystem = alertSystem;
    }

    /**
     *
     * @return
     */

    public RecommendationSystem getRecommendationSystem() {
        return recommendationSystem;
    }

    /**
     *
     * @param recommendationSystem
     */

    public void setRecommendationSystem(RecommendationSystem recommendationSystem) {
        this.recommendationSystem = recommendationSystem;
    }

    /**
     *
     * @return
     */

    public List<Guest> getOnlineGuests() {
        return onlineGuests;
    }

    /**
     *
     * @param onlineGuests
     */

    public void setOnlineGuests(List<Guest> onlineGuests) {
        this.onlineGuests = onlineGuests;
    }

    /**
     *
     * @return
     */

    public List<Subscriber> getSystemSubscribers() {
        return systemSubscribers;
    }

    /**
     *
     * @param systemSubscribers
     */

    public void setSystemSubscribers(List<Subscriber> systemSubscribers) {
        this.systemSubscribers = systemSubscribers;
    }

    /**
     *
     * @return
     */

    public List<Admin> getAdmins() {
        return admins;
    }

    /**
     *
     * @param admins
     */

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    /**
     *
     * @return
     */
    public LoggingSystem getLoggingSystem() {
        return loggingSystem;
    }

    /**
     *
     * @param loggingSystem
     */
    public void setLoggingSystem(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    /**
     *
     * @return
     */

    public List<League> getLeagues() {
        return leagues;
    }

    /**
     *
     * @param leagues
     */

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

}
