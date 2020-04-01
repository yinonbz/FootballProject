import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class LoggingSystem {

    private Map<String, String> logs; // map of <controller the log sent from, the log itself>
    private SystemController systemController;
    private LeagueController leagueController;

    /**
     *
     * @param logs
     * @param systemController
     * @param leagueController
     */
    public LoggingSystem(Map<String, String> logs, SystemController systemController, LeagueController leagueController) {
        this.logs = new HashMap<>();
        this.systemController = systemController;
        this.leagueController = leagueController;
    }

    /**for security checks
     *
     * @return
     */
    public boolean sendLogs(List<String> logs){

        return true;
    }

    /**
     *
     */
    public void updateLogs(){

    }

    /**
     *
     * @return
     */
    public Map<String, String> getLogs() {
        return logs;
    }

    /**
     *
     * @param logs
     */
    public void setLogs(Map<String, String> logs) {
        this.logs = logs;
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
}
