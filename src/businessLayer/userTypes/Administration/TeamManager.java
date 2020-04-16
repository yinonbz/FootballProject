package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.HashSet;
import java.util.Set;

public class TeamManager extends Subscriber implements OwnerEligible {

    private TeamOwner teamOwner; //fictive account for team owner permission via team manager account
    private Team team;
    private Permissions permissions;
    private int salary;

    /**
     * @param username
     * @param password
     * @param name
     * @param team
     */
    public TeamManager(String username, String password, String name, Team team,int salary, SystemController systemController) {
        super(username, password,name, systemController);
        this.team = team;
        this.teamOwner =null;
        this.salary = salary;
        permissions= null;
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
    public Team getTeam() {
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
    public void setTeam(Team team) {
        this.team = team;
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

    public TeamOwner getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(TeamOwner teamOwner) {
        this.teamOwner = teamOwner;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public boolean addPlayer(String playerUserName){
        if(permissions==Permissions.PLAYERORIENTED || permissions == Permissions.GENERAL){
            if (playerUserName!= null){
                Player player = systemController.findPlayer(playerUserName);
                if (player != null && player.getTeam() == null) {
                    team.addPlayer(player);
                    player.setTeam(team);
                    return true;
                }
            }
        }
        else{
            System.out.println("Invalid permissions");
        }
        return false;
    }

    public boolean deletePlayer(String playerUserName){
        if(permissions == Permissions.PLAYERORIENTED || permissions == Permissions.GENERAL) {
            if (playerUserName!= null) {
                Player player = systemController.findPlayer(playerUserName);
                if (player != null && player.getTeam() != null && player.getTeam() == team && team.containPlayer(player)) {
                    player.setTeam(null);
                    team.removePlayer(player);
                }
                return false;
            }
        }
        else{
            System.out.println("Invalid permissions");
        }
        return true;
    }

    public boolean editPlayer(String playerUser,String typeEdit, String edit){
        if(permissions == Permissions.PLAYERORIENTED || permissions == Permissions.GENERAL) {
            if (playerUser != null && typeEdit != null && edit != null) {
                Player player = team.getPlayerByUser(playerUser);
                if (player != null) {
                    if (typeEdit.equals("birthDate")) {
                        team.removePlayer(player);
                        player.setBirthDate(edit);
                        team.addPlayer(player);
                    } else if (typeEdit.equals("fieldJob")) {
                        team.removePlayer(player);
                        player.setFieldJob(edit);
                        team.addPlayer(player);
                    }
                    else if(typeEdit.equals("salary")){
                        if(isNumeric(edit)){
                            int salary = Integer.parseInt(edit);
                            team.removePlayer(player);
                            player.setSalary(salary);
                            team.addPlayer(player);
                        }
                    }
                    return true;
                }
            }
            else{
                System.out.println("Invalid permissions");
            }
        }
        return false;
    }

    public boolean addCoach(String coachUserName){
        if(permissions == Permissions.COACHORIENTED || permissions==Permissions.GENERAL){
            if(coachUserName != null){
                Coach coach = systemController.findCoach(coachUserName);
                if (coach != null && !coach.containTeam(team)) {
                    team.addCoach(coach);
                    coach.addTeam(team);
                    return true;
                }
            }
        }
        else{
            System.out.println("Invalid permissions");
        }
        return false;

    }

    public boolean deleteCoach(String coachUserName){
        if(permissions == Permissions.COACHORIENTED || permissions==Permissions.GENERAL) {
            if(coachUserName != null){
                Coach coach = systemController.findCoach(coachUserName);
                if (coach != null && coach.containTeam(team) && team.containCoach(coach)) {
                    team.removeCoach(coach);
                    coach.removeTeam(team);
                    return true;
                }
            }
        }
        else{
            System.out.println("Invalid permissions");

        }
        return false;
    }

    public boolean editCoach(String CoachUser, String typeEdit, String edit) {
        if(permissions == Permissions.COACHORIENTED || permissions==Permissions.GENERAL) {

            if (CoachUser != null && typeEdit != null && edit != null) {
                Coach coach = team.getCoachByUser(CoachUser);
                if (coach != null) {
                    if (typeEdit.equals("training")) {
                        team.removeCoach(coach);
                        coach.setTraining(edit);
                        team.addCoach(coach);
                    } else if (typeEdit.equals("teamJob")) {
                        team.removeCoach(coach);
                        coach.setTeamJob(edit);
                        team.addCoach(coach);
                    }
                    return true;
                }
            }
        }
        else{
            System.out.println("Invalid permissions");
        }
        return false;
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
