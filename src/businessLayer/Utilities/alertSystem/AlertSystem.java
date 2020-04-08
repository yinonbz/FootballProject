package businessLayer.Utilities.alertSystem;

import serviceLayer.LeagueController;
import serviceLayer.SystemController;
import businessLayer.userTypes.Subscriber;

import java.util.List;
import java.util.Map;

public class AlertSystem {

    private SystemController systemController;
    private List<Subscriber> subscriberList;
    private LeagueController leagueController;
    private Map<Subscriber, List<Subscriber>> alerts;

    public AlertSystem() {
    }

    /**
     *
     * @param notifications
     * @return
     */
    public boolean sendNotifications(List<String> notifications){

        return true;
    }


    /**
     *
     * @return
     */
    public SystemController getSystemController() {
        return systemController;
    }

    /**
     *
     * @param systemController
     */

    public void setSystemController(SystemController systemController) {
        this.systemController = systemController;
    }

    /**
     *
     * @return
     */
    public List<Subscriber> getSubscriberList() {
        return subscriberList;
    }

    /**
     *
     * @param subscriberList
     */
    public void setSubscriberList(List<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }

    /**
     *
     * @return
     */
    public LeagueController getLeagueController() {
        return leagueController;
    }

    /**
     *
     * @param leagueController
     */
    public void setLeagueController(LeagueController leagueController) {
        this.leagueController = leagueController;
    }

    /**
     *
     * @return
     */
    public Map<Subscriber, List<Subscriber>> getAlerts() {
        return alerts;
    }

    /**
     *
     * @param alerts
     */
    public void setAlerts(Map<Subscriber, List<Subscriber>> alerts) {
        this.alerts = alerts;
    }
}
