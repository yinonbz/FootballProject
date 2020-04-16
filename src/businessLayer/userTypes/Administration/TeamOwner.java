package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.*;


public class TeamOwner extends Subscriber {

    private OwnerEligible originalObject; // points to player/Coach/Manager of current fictive team owner
    private HashSet<Team> teams;
    private HashMap<Team,TeamOwner> teamOwners; // owners assigned by current owner
    private HashMap<Team,TeamManager> teamManagers; // managers assigned by current owner

    public static int newTeamOwnerCounter = 0;

    /**
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name, SystemController systemController) {
        super(username, password,name, systemController);
        teams = new HashSet<>();
        originalObject = null;
        this.teamManagers = new HashMap<>();
        this.teamOwners = new HashMap<>();
    }

    /**
     * the function lets the team owner to send a request for opening a new team
     *
     * @param teamName        the team's name
     * @param establishedYear the established year of the team
     * @return true if the request was send successfully
     */
    public boolean sendRequestForTeam(String teamName, String establishedYear) {
        if (!teamName.isEmpty() && !establishedYear.isEmpty()) {
            if (tryParseInt(establishedYear)) {
                if (isTheNumberAYear(establishedYear)) {
                    LinkedList<String> details = new LinkedList<>();
                    details.add(teamName);
                    details.add(establishedYear);
                    details.add(getUsername());
                    return systemController.addToTeamConfirmList(details, this.getUsername());
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
     * this function add the asset to the chosen team
     *
     * @param teamId
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean addAsset(int teamId, String assetType, String assetUserName) {
        boolean isAdded = false;
        Team team = findTeam(teamId);
        SystemController systemController = this.getSystemController();
        if (team != null && team.getActive()) {
            switch (assetType) {
                case "Player":
                    Player player = systemController.findPlayer(assetUserName);
                    if (player != null && player.getTeam() == null) {
                        team.addPlayer(player);
                        player.setTeam(team);
                        isAdded = true;
                    }
                    break;
                case "TeamManager":
                    TeamManager teamManager = systemController.findTeamManager(assetUserName);
                    if (teamManager != null && teamManager.getTeam()==null) {//ido change !teamManager.getTeam().equals(team)
                        team.addTeamManager(teamManager);
                        teamManager.setTeam(team);
                        this.teamManagers.put(team,teamManager);
                        isAdded = true;
                    }
                    break;

                case "Coach":
                    Coach coach = systemController.findCoach(assetUserName);
                    if (coach != null && !coach.containTeam(team)) {
                        team.addCoach(coach);
                        coach.addTeam(team);
                        isAdded = true;
                    }
                    break;

                case "Stadium":
                    Stadium stadium = systemController.findStadium(assetUserName);
                    if (stadium != null && stadium.containTeam(team) == false && team.getStadium() == null) {
                        team.setStadium(stadium);
                        stadium.addTeam(team);
                        isAdded = true;
                    }
                    break;

            }

        }
        return isAdded;
    }

    public boolean deleteAsset(int teamId, String assetType, String assetUserName) {
        boolean isDeleted = false;
        Team team = findTeam(teamId);
        SystemController systemController = this.getSystemController();
        if (team != null && team.getActive()) {
            switch (assetType) {
                case "Player":
                    Player player = systemController.findPlayer(assetUserName);
                    if (player != null && player.getTeam() != null && player.getTeam() == team && team.containPlayer(player)) {
                        player.setTeam(null);
                        team.removePlayer(player);
                        isDeleted = true;
                    }
                    break;
                case "TeamManager":
                    TeamManager teamManager = systemController.findTeamManager(assetUserName);
                    if (teamManager != null && teamManagers.containsKey(team) && teamManagers.get(team).equals(teamManager)&&teamManager.getTeam().equals(team)) {
                        team.removeTeamManager(teamManager);
                        teamManager.setTeam(null);
                        this.teamManagers.remove(team);
                        isDeleted = true;
                    }
                    break;

                case "Coach":
                    Coach coach = systemController.findCoach(assetUserName);
                    if (coach != null && coach.containTeam(team) && team.containCoach(coach)) {
                        team.removeCoach(coach);
                        coach.removeTeam(team);
                        isDeleted = true;
                    }
                    break;

                case "Stadium":
                    Stadium stadium = systemController.findStadium(assetUserName);
                    if (stadium != null && stadium.containTeam(team) == true && team.getStadium() != null) {
                        team.setStadium(stadium);
                        stadium.removeTeam(team);
                        team.setStadium(null);
                        isDeleted = true;
                    }
                    break;
            }
        }
        return isDeleted;
    }


    public boolean editPlayer(int teamId, String playerUser, String typeEdit, String edit) {
        Team team = findTeam(teamId);
        if (playerUser != null && typeEdit != null && edit != null) {
            Player player = team.getPlayerByUser(playerUser);
            if (player != null) {
                if (typeEdit.equals("birthDate")) {
                    team.removePlayer(player);
                    player.setBirthDate(edit);
                    team.addPlayer(player);
                    return true;
                } else if (typeEdit.equals("fieldJob")) {
                    team.removePlayer(player);
                    player.setFieldJob(edit);
                    team.addPlayer(player);
                    return true;
                }
                else if(typeEdit.equals("salary")){
                    team.removePlayer(player);
                   // int salary = tryParseInt()

                  //  player.setSalary();
                }
            }
        }
        return false;
    }

    public boolean editCoach(int teamId, String CoachUser, String typeEdit, String edit) {
        Team team = findTeam(teamId);
        if (CoachUser != null && typeEdit != null && edit != null) {
            Coach coach = team.getCoachByUser(CoachUser);
            if (coach != null) {
                if (typeEdit.equals("training")) {
                    team.removeCoach(coach);
                    coach.setTraining(edit);
                    team.addCoach(coach);
                    return true;
                } else if (typeEdit.equals("teamJob")) {
                    team.removeCoach(coach);
                    coach.setTeamJob(edit);
                    team.addCoach(coach);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean editTeamManager(int teamId, String teamManagerUser, String typeEdit, int edit) {
        Team team = findTeam(teamId);
        if (teamManagerUser != null && typeEdit != null && team != null) {
            TeamManager teamManager = team.getTeamManager();
            if (teamManager != null) {
                if (typeEdit.equals("salary")) {
                    team.removeTeamManager(teamManager);
                    teamManager.setSalary(edit);
                    team.addTeamManager(teamManager);
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean editStadium(int teamId, String editStadiumName, String typeEdit, int edit) {
        Team team = findTeam(teamId);
        if (editStadiumName != null && typeEdit != null && team != null) {
            Stadium stadium = team.getStadium();
            if(stadium!=null&&stadium.getName().equals(editStadiumName)){
                if(typeEdit.equals("numberOfSeats")){
                    stadium.setNumberOfSeats(edit);
                    team.setStadium(stadium);
                    return true;
                }
            }

        }
        return false;
    }

    private Team findTeam(int teamId) {
        for (Team team : teams) {
            if (team.getTeamId() == teamId) {
                return team;
            }
        }
        return null;
    }

    /**
     * UC 6.6
     *
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
            //System.out.println("The team '" + team.getTeamName() + "' has been enabled and is now active.");
        } else {
            team.setActive(false);
            //System.out.println("The team '" + team.getTeamName() + "' has been disabled and is now not active.");
        }
    }

    /**
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

    public boolean addManager(String username, Permissions permission,Team team,int salary){
        //check if user exists in out system
        Subscriber subscriber=null;
        if(systemController.checkUserExists(username)){
            subscriber = systemController.selectUserFromDB(username);
        }

        //verify user exists in the system, user is not the team manager,user is not one of the team owners, owner indeed owns the team/
        if(subscriber==null||team.getTeamManager().equals(subscriber)|| team.getTeamOwners().contains(subscriber)|| !(this.teams.contains(team))){
            return false;
            //todo check if we should print something based on the error given
        }

        if(team.getTeamManager() ==null){

            //covert Subsriber to teamManger

            TeamManager newTeamManger = new TeamManager(subscriber.getUsername(),
                    subscriber.getPassword(),subscriber.getName(),team,salary,this.getSystemController());

            systemController.addSubscriberToDB(username,newTeamManger);
            subscriber= newTeamManger;

            //assign to team manager field in the team objects
            team.setTeamManager((TeamManager)subscriber);

            //grant permissions to the new team manager
            newTeamManger.setPermissions(grantPermissions(permission));

            //link to assigning owner
            teamManagers.put(team,newTeamManger);

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
        if(systemController.checkUserExists(username)){
            subscriber = systemController.selectUserFromDB(username);
        }

        //verify user exists in the system, user is not the team manager,user is not one of the team owners, owner indeed owns the team/
        if(subscriber==null|| !(this.teams.contains(team))||teamManagers.containsValue(subscriber) || !(subscriber instanceof TeamManager) || !(team.getTeamManager().equals(subscriber))){
            return false;
            //todo check if we should print something based on the error given
        }

        //fire manager from team and delete links
        team.setTeamManager(null);
        TeamManager tm = (TeamManager) subscriber;
        tm.setTeam(null);

        //cancel permissions
        tm.setPermissions(new HashSet<>());

        //delete assignment from owner
        teamManagers.remove(team);

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

    /**
     * @return true if fictive (ex: player is also a team owner = fictive)
     * false else
     */
    public boolean isFictive(){
        if(originalObject==null){
            return false;
        }
        return true;
    }


    /**
     * //UC-6.2
     * @param userName the user name of the user that the Team Owner wants to appoint to
     * @return
     */
    public Subscriber enterMember(String userName){

        return systemController.getSubscriberByUserName(userName);
    }


    /**
     * //UC-6.2
     * @param subscriber the new subscriber that the user wants to add to the team's owners
     * @param teamName the team name that the user wants to add a new team owner
     * @return true if the new team owner has added successfully.
     *          false if: the subscriber is already a team owner, or the subscriber isn't a Player, a Coach or a Team Manager.
     */
    public Boolean appointToOwner(Subscriber subscriber, String teamName){

        if(subscriber instanceof OwnerEligible || subscriber instanceof TeamOwner){
            if(!(subscriber instanceof TeamOwner) && ((OwnerEligible) subscriber).isOwner() == false){
                if(getTeams().contains(systemController.getTeamByName(teamName))) { //if the user is the team owner of the team with the name 'teamName'
                    String newUserName = subscriber.getUsername();
                    updateFictiveOwner(newUserName,subscriber,teamName);
                    return true;
                }
                else{
                    //System.out.println("You cannot add to a team which you do not own.");
                    return false;
                }
            }
            else{
                //System.out.println("The user " + subscriber.getUsername() + " is already an owner of a team.");
                return false;
            }
        }
        else{
            //System.out.println("Team owner must be a Player, a Coach or a Team Manager.");
            return false;
        }
    }

    /**
     * //UC - 6.2
     * @param newUserName the userName for the new fictive user.
     * @param subscriber the subscriber to add as a new team owner.
     * @param teamName the team name to add a new team owner.
     */
    private void updateFictiveOwner(String newUserName, Subscriber subscriber, String teamName) {
        while (subscriber.getSystemController().checkUserExists(newUserName)) { //generate new fictive user name
            newUserName = newUserName + newTeamOwnerCounter++;
        }
        TeamOwner newTeamOwner = new TeamOwner(newUserName, subscriber.getPassword(), "fictive", subscriber.getSystemController());
        if(subscriber instanceof Player){
            Player player = (Player)subscriber;
            player.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(player);

        }
        else if(subscriber instanceof Coach){
            Coach coach = (Coach)subscriber;
            coach.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(coach);
        }
        else if(subscriber instanceof TeamManager){
            TeamManager teamManager = (TeamManager)subscriber;
            teamManager.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(teamManager);
        }

        Team team = getSystemController().getTeamByName(teamName);
        subscriber.getSystemController().getTeamByName(teamName).getTeamOwners().add(newTeamOwner); //add the new team owner to the team's team owners list
        newTeamOwner.getTeams().add(getSystemController().getTeamByName(teamName));
        teamOwners.put(team,newTeamOwner);
        //todo - add complaints to newTeamOwner? if not, complaints needs to be added manually to the newTeamOwner from the original object
        //System.out.println("The user " + subscriber.getUsername() + " has been added to the Team '" + teamName + "' owners list successfully.");
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
