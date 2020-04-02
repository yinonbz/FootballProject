import java.util.List;

public class TeamManager extends Subscriber{
    private String name;
    private List<Team> teams;

    /**
     * @param username
     * @param password
     * @param systemController
     * @param alertSystem
     * @param name
     * @param teams
     */
    public TeamManager(String username, String password, SystemController systemController, AlertSystem alertSystem, String name, List<Team> teams) {
        super(username, password, systemController, alertSystem);
        this.name = name;
        this.teams = teams;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }


}
