package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
*********************FOR IDO**********************************
 */

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
     * the function lets the team owner to send a request for opening a new team
     * @param teamName the team's name
     * @param establishedYear the established year of the team
     * @return true if the request was send successfully
     */
    public boolean sendRequestForTeam (String teamName, String establishedYear){
        if(!teamName.isEmpty() && !establishedYear.isEmpty()){
            if(tryParseInt(establishedYear)){
                if(isTheNumberAYear(establishedYear)){
                    LinkedList<String> details= new LinkedList<>();
                    details.add(teamName);
                    details.add(establishedYear);
                    details.add(getUsername());
                    return systemController.addToTeamConfirmList(details,this);
                }
            }
        }
        return false;
    }



    /**
     * this function check a string that represent a NUMBER and checks if it can be a year
     *
     * @param value the string that represents a number
     * @return true if it can be a year
     */
    private boolean isTheNumberAYear(String value) {
        if (tryParseInt(value)) {
            int tempNumber = Integer.parseInt(value);
            if (tempNumber >= 1800 && tempNumber <= 2020) {
                return true;
            }
        }
        return false;
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
     * a getter of teams of a team owner
     * @return the data structure of the teams of the team owner
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
