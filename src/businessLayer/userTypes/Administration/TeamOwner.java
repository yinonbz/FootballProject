package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;

import java.util.LinkedList;
import java.util.List;

public class TeamOwner extends Subscriber {

    private String name;
    private LinkedList<Team> teams;
    /**
     *
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name) {
        super(username, password);
        this.name= name;
        teams = new LinkedList<>();
    }

    /**
     * the function checks if the team owner has a team that nobody but him ows, so he can't be deleted as a user
     * @return true if he owns one of the teams elusively
     */
    public boolean isEclusiveTeamOwner(){
        if (teams.size()>0){
            for(Team team : teams){
                if(team.getTeamOwners().size()==1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean editProperties(){
        return true;
    }

    /**
     *
     * @return
     */
    public boolean editOwners(){

        return true;
    }

    /**
     *
     * @return
     */

    public boolean editManagers(){
        return true;
    }

    /**
     *
     * @return
     */
    public boolean editTeams(){

        return true;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     */

    public void setTeams(LinkedList<Team> teams) {
        this.teams = teams;
    }

    @Override
    public Boolean editDetails() {
        return null;
    }
}
