package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamManager extends Subscriber implements OwnerEligible {

    private TeamOwner teamOwner; //fictive account for team owner permission via team manager account
    private Team team;
    private Set<String> permissions;

    /**
     * @param username
     * @param password
     * @param name
     * @param team
     */
    public TeamManager(String username, String password, String name, Team team, SystemController systemController) {
        super(username, password,name, systemController);
        this.team = team;
        this.teamOwner =null;
        permissions= new HashSet<>();
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
    public Team getTeams() {
        return team;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param team
     */
    public void setTeams(Team team) {
        team = team;
    }


    @Override
    public Boolean editDetails() {
        return null;
    }


    /**
     * this function determine if the coach is also an Owner
     * @return true if also an owner, false if only coach
     */
    @Override
    public boolean isOwner() {
        if(teamOwner ==null){
            return false;
        }
        return true;
    }

    public TeamOwner getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(TeamOwner teamOwner) {
        this.teamOwner = teamOwner;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj!=null && obj instanceof Subscriber){
            Subscriber objS = (Subscriber) obj;
            if(objS instanceof TeamManager){
                TeamManager objTM = (TeamManager) objS;
                return super.equals(objTM);
            }
        }
        return false;
    }
}
