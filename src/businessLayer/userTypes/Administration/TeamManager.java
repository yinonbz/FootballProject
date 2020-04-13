package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.List;

public class TeamManager extends Subscriber {
    private String name;
    private List<Team> teams;
    private Boolean assign;

    /**
     * @param username
     * @param password
     * @param name
     * @param teams
     */
    public TeamManager(String username, String password, String name, List<Team> teams, SystemController systemController) {
        super(username, password, systemController);
        this.name = name;
        this.teams = teams;
        this.assign = false;
    }

    public void setAssign(Boolean assign) {
        this.assign = assign;
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

    public Boolean getAssigned(){
        return this.assign;
    }
}
