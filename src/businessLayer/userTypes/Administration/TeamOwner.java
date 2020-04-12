package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TeamOwner extends Subscriber {

    private String name;
    private HashSet<Team> teams;

    /**
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name, SystemController systemController) {
        super(username, password, systemController);
        this.name = name;
        teams = new HashSet<>();
    }

    /**
     * the function checks if the team owner has a team that nobody but him ows, so he can't be deleted as a user
     *
     * @return true if he owns one of the teams elusively
     */
    public boolean isExclusiveTeamOwner() {
        if (teams.size() > 0) {
            for (Team team : teams) {
                if (team.getTeamOwners().size() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * UC 6.6
     * @param teamName the name of the team to be returned.
     * @return instance of the team with the name of 'teamName'.
     * null if there is no such team in the system.
     */
    public Team getTeam(String teamName) {
        Iterator<Team> it = teams.iterator();
        while (it.hasNext()) {
            Team teamCheck = it.next();
            if (teamCheck.getTeamName().equals(teamName)) {
                return teamCheck;
            }
        }
        //System.out.println("There is no team with the name '" + teamName + "' in the system.");
        return null;
    }

    /**
     * @param team team to be enabled/disabled.
     */
    public void changeStatus(Team team) {
        if (!team.getActive()) {
            team.setActive(true);
            System.out.println("The team '" + team + "' has been enabled and is now active.");
        }
        else{
            team.setActive(false);
            System.out.println("The team '" + team + "' has been disabled and is now not active.");
        }
    }

    /**
     * @return
     */
    public boolean editProperties() {
        return true;
    }

    /**
     * @return
     */
    public boolean editOwners() {

        return true;
    }

    /**
     * @return
     */

    public boolean editManagers() {
        return true;
    }

    /**
     * @return
     */
    public boolean editTeams() {

        return true;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public HashSet<Team> getTeams() {
        return teams;
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
}
