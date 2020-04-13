package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TeamOwner extends Subscriber {

    private OwnerEligible originalObject;
    private Set<TeamOwner> assignedByMe;
    private String name;
    private HashSet<Team> teams;
    /**
     *
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name, SystemController systemController) {
        super(username, password, systemController);
        this.name= name;
        teams = new HashSet<>();
        assignedByMe = new HashSet<>();
        originalObject = null;
    }

    /**
     * the function checks if the team owner has a team that nobody but him ows, so he can't be deleted as a user
     * @return true if he owns one of the teams elusively
     */
    public boolean isExclusiveTeamOwner(){
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
    public HashSet<Team> getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     */

    public void setTeams(HashSet<Team> teams) {
        this.teams = teams;
    }

    @Override
    public Boolean editDetails() {
        return null;
    }

    public boolean isFictive(){
        if(originalObject==null){
            return false;
        }
        return true;
    }

    protected OwnerEligible getOriginalObject() {
        return originalObject;
    }

    protected void setOriginalObject(OwnerEligible originalObject) {
        this.originalObject = originalObject;
    }
}
