package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.*;


public class TeamOwner extends Subscriber {

    private OwnerEligible originalObject; // points to player/Coach/Manager of current fictive team owner
    private HashSet<Team> teams;
    private HashMap<Team, TeamOwner> teamOwners; // owners assigned by current owner
    private HashMap<Team, TeamManager> teamManagers; // managers assigned by current owner

    public static int newTeamOwnerCounter = 0;

    /**this is the constructor of team owner
     * @param username
     * @param password
     * @param name
     */
    public TeamOwner(String username, String password, String name, SystemController systemController) {
        super(username, password, name, systemController);
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
     * @param teamName the team name
     * @param assetType the asset type
     * @param assetUserName the user name of the asset
     * @return true or false if the asset was added
     */
    public boolean addAsset(String teamName, String assetType, String assetUserName) {
        boolean isAdded = false;
        Team team = findTeam(teamName);
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
                    if (teamManager != null && teamManager.getTeam() == null) {//ido change !teamManager.getTeam().equals(team)
                        team.addTeamManager(teamManager);
                        teamManager.setTeam(team);
                        this.teamManagers.put(team, teamManager);
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

    /**
     * this function return true or false if the team owner can delete a asset from the team
     * @param teamName the team name
     * @param assetType the asset type
     * @param assetUserName the asset user name
     * @return true or false if the asset was remove from the team
     */
    public boolean deleteAsset(String teamName, String assetType, String assetUserName) {
        boolean isDeleted = false;
        Team team = findTeam(teamName);
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
                    if (teamManager != null && teamManagers.containsKey(team) && teamManagers.get(team).equals(teamManager) && teamManager.getTeam().equals(team)) {
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

    /**
     * this function return true or false if a team owner manage to edit a player information
     * @param teamName the team name
     * @param playerUser the player user name
     * @param typeEdit the info type to be edit
     * @param edit what the edit info
     * @return true or false if the team owner manage to edit the player info
     */
    public boolean editPlayer(String teamName, String playerUser, String typeEdit, String edit) {
        Team team = findTeam(teamName);
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
                } else if (typeEdit.equals("salary")) {
                    if (isNumeric(edit)) {
                        int salary = Integer.parseInt(edit);
                        team.removePlayer(player);
                        player.setSalary(salary);
                        team.addPlayer(player);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * this function return true or false if a team owner manage to edit a coach information
     * @param teamName the team name
     * @param CoachUser the coach user name
     * @param typeEdit the info type to be edit
     * @param edit what the edit info
     * @return true or false if the team owner manage to edit the coach info
     */
    public boolean editCoach(String teamName, String CoachUser, String typeEdit, String edit) {
        Team team = findTeam(teamName);
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
                } else if (typeEdit.equals("salary")) {
                    if (isNumeric(edit)) {
                        int salary = Integer.parseInt(edit);
                        team.removeCoach(coach);
                        coach.setSalary(salary);
                        team.addCoach(coach);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * this function return true or false if a team owner manage to edit a teamManager information
     * @param teamName the team name
     * @param teamManagerUser the teamManager user name
     * @param typeEdit the info type to be edit
     * @param edit what the edit info
     * @return true or false if the team owner manage to edit the teamManager info
     */
    public boolean editTeamManager(String teamName, String teamManagerUser, String typeEdit, int edit) {
        Team team = findTeam(teamName);
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

    /**
     * this function return true or false if a team owner manage to edit a Stadium information
     * @param teamName the team name
     * @param editStadiumName the Stadium user name
     * @param typeEdit the info type to be edit
     * @param edit what the edit info
     * @return true or false if the team owner manage to edit the Stadium info
     */
    public Boolean editStadium(String teamName, String editStadiumName, String typeEdit, int edit) {
        Team team = findTeam(teamName);
        if (editStadiumName != null && typeEdit != null && team != null) {
            Stadium stadium = team.getStadium();
            if (stadium != null && stadium.getName().equals(editStadiumName)) {
                if (typeEdit.equals("numberOfSeats")) {
                    stadium.setNumberOfSeats(edit);
                    team.setStadium(stadium);
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * this function find the team that the owner own according to the team name and return the certain team
     * @param teamName the team name
     * @return the team according to the name
     */
    private Team findTeam(String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
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
     * @param team team team to be enabled.
     * @return true if the team has been enabled
     * false if already enabled
     */
    public Boolean enableStatus(Team team) {
        if (!team.getActive()) {
            team.setActive(true);
            return true;
            //System.out.println("The team '" + team.getTeamName() + "' has been enabled and is now active.");
        }
        //System.out.println("The team '" + team.getTeamName() + "' has already been enabled.");
        return false;
    }


    /**
     * @param team team to be disabled.
     * @return true if the team has been disabled
     * false if already disabled
     */
    public Boolean disableStatus(Team team) {
        if (team.getActive()) {
            team.setActive(false);
            return true;
            //System.out.println("The team '" + team.getTeamName() + "' has been disabled and is now not-active.");
        }
        //System.out.println("The team '" + team.getTeamName() + "' has already been disabled.");
        return false;
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
     * @param username
     * @param permission
     * @param team
     * @param salary
     * @return
     */
    public boolean addManager(String username, Permissions permission,Team team,int salary){
        //check if user exists in out system
        Subscriber subscriber=null;
        if(systemController.checkUserExists(username)){
            subscriber = systemController.selectUserFromDB(username);
        }
        //verify user exists in the system, user is a team manager,user is not one of the team owners, owner indeed owns the team and team has no manager
        if(subscriber!=null && team!=null && subscriber instanceof TeamManager){
            TeamManager teamManager = (TeamManager)subscriber;
            if(team.getTeamManager() ==null && teamManager.getTeam()==null){
                if(!team.getTeamOwners().contains(subscriber) && (this.teams.contains(team))){
                    //covert Subsriber to teamManger


                    //assign to team manager field in the team objects
                    teamManager.setTeam(team);
                    team.setTeamManager(teamManager);

                    //grant permissions to the new team manager
                    teamManager.setPermissions(permission);

                    //link to assigning owner
                    teamManagers.put(team,teamManager);

                    return true;
                }
            }
            else if((team.getTeamManager()!=null)){
                System.out.println("please fire current Manager before appointing a new one");
            }
        }
        return false;

        /*//verify user exists in the system, user is not the team manager,user is not one of the team owners, owner indeed owns the team/
        if(team==null ||subscriber==null||team.getTeamManager()!=null || team.getTeamOwners().contains(subscriber)|| !(this.teams.contains(team))){
            return false;
            //todo check if we should print something based on the error given
        }

        if(subscriber instanceof TeamManager && team.getTeamManager() ==null || ){

            //covert Subsriber to teamManger

            TeamManager teamManager = (TeamManager)subscriber;

            //assign to team manager field in the team objects
            teamManager.setTeam(team);
            team.setTeamManager(teamManager);

            //grant permissions to the new team manager
            teamManager.setPermissions(permission);

            //link to assigning owner
            teamManagers.put(team,teamManager);

            return true;
        }

        System.out.println("please fire current Manager before appointing a new one");
        return false;*/

    }
    /*
     *//**todo check if we can delete this function
     * this function checks what permissions should be given to the team manager based on enum argument
     * @param permission enum argument for permission category
     * @return set of strings that indicating the permissions which should be given to team manager.
     *//*
    private HashSet<Permissions> grantPermissions(Permissions permission) {

        if(permission == Permissions.COACHORIENTED){
            return new HashSet<String>(Arrays.asList(Permissions.COACHORIENTED));
        }
        if(permission == Permissions.PLAYERORIENTED){
            return new HashSet<String>(Arrays.asList("watchFinance", "reportFinance", "addPlayer", "firePlayer"));
        }
        if(permission == Permissions.FINANCE){
            return new HashSet<String>(Arrays.asList("watchFinance", "reportFinance"));
        }
        return new HashSet<String>(Arrays.asList("watchFinance", "reportFinance", "addCoach", "fireCoach","addPlayer","firePlayer"));
    }*/

    /**
     * @param username
     * @param team
     * @return
     */
    public boolean fireManager(String username, Team team) {

        //check if user exists in out system
        Subscriber subscriber = null;
        if (systemController.checkUserExists(username)) {
            subscriber = systemController.selectUserFromDB(username);
        }

        if(subscriber!=null && team!=null){
            if(this.teams.contains(team) && teamManagers.containsValue(subscriber) ){
                if(subscriber instanceof TeamManager && team.getTeamManager().equals(subscriber)){
                    //fire manager from team and delete links
                    team.setTeamManager(null);
                    TeamManager tm = (TeamManager) subscriber;
                    tm.setTeam(null);

                    //cancel permissions
                    tm.setPermissions(null);

                    //delete assignment from owner
                    teamManagers.remove(team);

                    return true;
                }
            }
        }
/*
        //verify user exists in the system, user is not the team manager,user is not one of the team owners, owner indeed owns the team/
        if(subscriber==null|| !(this.teams.contains(team))||!teamManagers.containsValue(subscriber) || !(subscriber instanceof TeamManager) || !(team.getTeamManager().equals(subscriber))){
            return false;
            //todo check if we should print something based on the error given
        }

        //fire manager from team and delete links
        team.setTeamManager(null);
        TeamManager tm = (TeamManager) subscriber;
        tm.setTeam(null);

        //cancel permissions
        tm.setPermissions(null);

        //delete assignment from owner
        teamManagers.remove(team);

        return true;*/
        return false;
    }
    /***todo check if this function is implemented by someone?
    public boolean editTeams() {

        return true;
    }
    */
    /**
     * a getter of teams of a team owner
     *
     * @return the data structure of the teams of the team owner
     */
    public HashSet<Team> getTeams() {
        return teams;
    }

    /**
     * @param teams
     */
    /**
     * this function set the team hash set
     * @param teams
     */
    public void setTeams(HashSet<Team> teams) {
        this.teams = teams;
    }

    /*
    @Override
    public Boolean editDetails() {
        return null;
    }
    */

    /**
     * @return true if fictive (ex: player is also a team owner = fictive)
     * false else
     */
    public boolean isFictive() {
        if (originalObject == null) {
            return false;
        }
        return true;
    }


    /**
     * //UC-6.2
     *
     * @param userName the user name of the user that the Team Owner wants to appoint to
     * @return
     */
    public Subscriber enterMember(String userName) {

        return systemController.getSubscriberByUserName(userName);
    }


    /**
     * //UC-6.2
     *
     * @param subscriber the new subscriber that the user wants to add to the team's owners
     * @param teamName   the team name that the user wants to add a new team owner
     * @return true if the new team owner has added successfully.
     * false if: the subscriber is already a team owner, or the subscriber isn't a Player, a Coach or a Team Manager.
     */
    public Boolean appointToOwner(Subscriber subscriber, String teamName) {
        if (subscriber == null)
            return false; //subscriber doesn't exist in the DB
        if (subscriber instanceof OwnerEligible || subscriber instanceof TeamOwner) {
            if (!(subscriber instanceof TeamOwner) && ((OwnerEligible) subscriber).isOwner() == false) {
                if (getTeams().contains(systemController.getTeamByName(teamName))) { //if the user is the team owner of the team with the name 'teamName'
                    String newUserName = subscriber.getUsername();
                    updateFictiveOwner(newUserName, subscriber, teamName);
                    return true;
                } else {
                    //System.out.println("You cannot add to a team which you do not own.");
                    return false;
                }
            } else {
                //System.out.println("The user " + subscriber.getUsername() + " is already an owner of a team.");
                return false;
            }
        } else {
            //System.out.println("Team owner must be a Player, a Coach or a Team Manager.");
            return false;
        }
    }

    /**
     * //UC - 6.2
     *
     * @param newUserName the userName for the new fictive user.
     * @param subscriber  the subscriber to add as a new team owner.
     * @param teamName    the team name to add a new team owner.
     */
    private void updateFictiveOwner(String newUserName, Subscriber subscriber, String teamName) {
        while (subscriber.getSystemController().checkUserExists(newUserName)) { //generate new fictive user name
            newUserName = newUserName + newTeamOwnerCounter++;
        }
        TeamOwner newTeamOwner = new TeamOwner(newUserName, subscriber.getPassword(), "fictive", subscriber.getSystemController());
        if (subscriber instanceof Player) {
            Player player = (Player) subscriber;
            player.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(player);
        } else if (subscriber instanceof Coach) {
            Coach coach = (Coach) subscriber;
            coach.setTeamOwner(newTeamOwner);
            newTeamOwner.setOriginalObject(coach);
        } else if (subscriber instanceof TeamManager) {
            TeamManager teamManager = (TeamManager) subscriber;
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

    /**
     *
     * @param teamName
     * @return
     */
    public int reportIncome(String teamName) {
        if(teamName!=null) {
            Team team = findTeam(teamName);
            if (team != null) {
                return team.calculateIncome();
            }
        }
        return -1;
    }

    /**
     *
     * @param teamName
     * @return
     */
    public int reportExpanse(String teamName) {
        if(teamName!=null) {
            Team team = findTeam(teamName);
            if (team != null) {
                return team.calculateExpanse();
            }
        }
        return -1;
    }

    /**
     *
     * @param teamName
     * @return
     */
    public int calculateFinancial(String teamName) {
        return reportIncome(teamName) - reportExpanse(teamName);
    }

    /**
     *
     * @return
     */
    public OwnerEligible getOriginalObject() {
        return originalObject;
    }

    /**
     *
     * @param originalObject
     */
    public void setOriginalObject(OwnerEligible originalObject) {
        this.originalObject = originalObject;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Subscriber) {
            Subscriber objS = (Subscriber) obj;
            if (objS instanceof TeamOwner) {
                TeamOwner objTO = (TeamOwner) objS;
                return super.equals(objTO);
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public HashMap<Team, TeamManager> getTeamManagers() {
        return teamManagers;
    }
}
