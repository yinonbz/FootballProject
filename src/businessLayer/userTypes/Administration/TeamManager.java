package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;

import java.util.List;

public class TeamManager extends Subscriber {
    private String name;
    private List<Team> teams;

    /**
     * @param username
     * @param password
     * @param name
     * @param teams
     */
    public TeamManager(String username, String password, String name, List<Team> teams) {
        super(username, password);
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


    @Override
    public Boolean editDetails() {
        return null;
    }
}
