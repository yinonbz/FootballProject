package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.Utilities.Page;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.HashSet;

public class Coach extends Subscriber implements OwnerEligible {

    private TeamOwner teamOwner;
    private String training;
    private String teamJob;
    private int salary;
    private HashSet<Team> teams;
    private Page coachPage;
    /**
     *
     * @param username
     * @param password
     * @param name
     * @param training
     * @param teamJob
     * @param salary
     * @param systemController
     */
    public Coach(String username, String password, String name,String training, String teamJob,int salary, SystemController systemController) {
        super(username, password,name, systemController);
        this.training=training;
        this.teamJob=teamJob;
        this.teamOwner =null;
        this.salary = salary;
        this.teams = new HashSet<>();
    }

    /**
     *
     * @return
     */
    public int getSalary() {
        return salary;
    }

    /**
     *
     * @param salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     *
     * @return
     */
    /*todo not implemented
    public boolean post(String post){

        return true;
    }
    */
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

    @Override
    public String getType() {
        return "Coach";
    }

    /**
     *
     * @return
     */



    public String getTraining() {
        return training;
    }

    /**
     *
     * @param training
     */

    public void setTraining(String training) {
        this.training = training;
    }

    /**
     *
     * @return
     */

    public String getTeamJob() {
        return teamJob;
    }

    /**
     *
     * @param teamJob
     */

    public void setTeamJob(String teamJob) {
        this.teamJob = teamJob;
    }

    public HashSet<Team> getTeamS() {
        return teams;
    }

    public void setTeamS(HashSet<Team> teamS) {
        this.teams = teamS;
    }

    /*
    @Override
    public Boolean editDetails() {
        return null;
    }
    */

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
