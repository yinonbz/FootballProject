package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.awt.*;
import java.util.*;
import java.util.List;

/*
*********************FOR IDO**********************************
 */

public class TeamOwner extends Subscriber {

    private OwnerEligible originalObject;
    private Set<TeamOwner> assignedByMe;
    private Map<Team,TeamManager> managersAssignedByMe;
    private HashSet<Team> teams;
    /**
     *
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name, SystemController systemController) {
        super(username, password,name, systemController);
        teams = new HashSet<>();
        assignedByMe = new HashSet<>();
        originalObject = null;
        managersAssignedByMe= new HashMap<>();
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
     * private function that checks that a string represents an interger
     * @param value the string
     * @return true if it an integer
     */
    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

    public boolean addManager(String username, Permissions permission,Team team){
        //check if user exists in out system
        Subscriber subscriber=null;
        if(systemController.getSystemSubscribers().containsKey(username)){
            subscriber = systemController.getSystemSubscribers().get(username);
        }

        //verify user exists in the system, user is not the team manager,user is not one of the team owners, owner indeed owns the team/
        if(subscriber==null||team.getTeamManager().equals(subscriber)|| team.getTeamOwners().contains(subscriber)|| !(this.teams.contains(team))){
            return false;
            //todo check if we should print something based on the error given
        }

        if(team.getTeamManager() ==null){

            //covert Subsriber to teamManger

            TeamManager newTeamManger = new TeamManager(subscriber.getUsername(),
                    subscriber.getPassword(),subscriber.getName(),team,this.getSystemController());

            systemController.getSystemSubscribers().put(username,newTeamManger);
            subscriber= newTeamManger;

            //assign to team manager field in the team objects
            team.setTeamManager((TeamManager)subscriber);

            //grant permissions to the new team manager
            newTeamManger.setPermissions(grantPermissions(permission));

            //link to assigning owner
            managersAssignedByMe.put(team,newTeamManger);

            return true;
        }

        System.out.println("please fire current Manager before appointing a new one");
        return false;

    }

    /**
     * this function checks what permissions should be given to the team manager based on enum argument
     * @param permission enum argument for permission category
     * @return set of strings that indicating the permissions which should be given to team manager.
     */
    private HashSet<String> grantPermissions(Permissions permission) {

        if(permission == Permissions.COACHORIENTED){
            return new HashSet<String>(Arrays.asList("watchFinance", "reportFinance", "addCoach", "fireCoach"));
        }
        if(permission == Permissions.PLAYERORIENTED){
            return new HashSet<String>(Arrays.asList("watchFinance", "reportFinance", "addPlayer", "firePlayer"));
        }
        if(permission == Permissions.FINANCE){
            return new HashSet<String>(Arrays.asList("watchFinance", "reportFinance"));
        }
        return new HashSet<String>(Arrays.asList("watchFinance", "reportFinance", "addCoach", "fireCoach","addPlayer","firePlayer"));
    }

    /**
     *
     * @return
     */

    public boolean fireManager(String username,Team team){

//check if user exists in out system
        Subscriber subscriber=null;
        if(systemController.getSystemSubscribers().containsKey(username)){
            subscriber = systemController.getSystemSubscribers().get(username);
        }

        //verify user exists in the system, user is not the team manager,user is not one of the team owners, owner indeed owns the team/
        if(subscriber==null|| !(this.teams.contains(team))||managersAssignedByMe.containsValue(subscriber) || !(subscriber instanceof TeamManager) || !(team.getTeamManager().equals(subscriber))){
            return false;
            //todo check if we should print something based on the error given
        }

        //fire manager from team and delete links
        team.setTeamManager(null);
        TeamManager tm = (TeamManager) subscriber;
        tm.setTeams(null);

        //cancel permissions
        tm.setPermissions(new HashSet<>());

        //delete assignment from owner
        managersAssignedByMe.remove(team);

        return true;
    }

    private void fireManagerFromTeam(Subscriber subscriber) {


    }


    /**
     *
     * @return
     */
    public boolean editTeams(){

        return true;
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

    public OwnerEligible getOriginalObject() {
        return originalObject;
    }

    public void setOriginalObject(OwnerEligible originalObject) {
        this.originalObject = originalObject;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj!=null && obj instanceof Subscriber){
            Subscriber objS = (Subscriber) obj;
            if(objS instanceof TeamOwner){
                TeamOwner objTO = (TeamOwner) objS;
                return super.equals(objTO);
            }
        }
        return false;
    }
}
