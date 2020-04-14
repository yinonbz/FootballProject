package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.HashSet;
import java.util.List;

public class TeamManager extends Subscriber implements OwnerEligible {

    private TeamOwner teamOwner;
    private String name;
    private HashSet<Team> teams;
    private int salary;

    /**
     * @param username
     * @param password
     * @param name
     * @param teams
     */
    public TeamManager(String username, String password, String name,int salary, HashSet<Team> teams, SystemController systemController) {
        super(username, password, systemController);
        this.name = name;
        this.teams = teams;
        this.salary = salary;
        this.teamOwner =null;
        if(this.teams==null){
            this.teams = new HashSet<>();
        }
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
    public HashSet<Team> getTeams() {
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
    public void setTeams(HashSet<Team> teams) {
        this.teams = teams;
    }


    @Override
    public Boolean editDetails() {
        return null;
    }



    public void setSalary(int edit) {
        this.salary = edit;
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

    protected TeamOwner getTeamOwner() {
        return teamOwner;
    }

    protected void setTeamOwner(TeamOwner teamOwner) {
        this.teamOwner = teamOwner;
    }

    public boolean containTeam(Team team) {
        return this.teams.contains(team);
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }
}
