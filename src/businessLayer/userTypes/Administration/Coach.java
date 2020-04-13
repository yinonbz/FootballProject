package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

public class Coach extends Subscriber {

    private String name;
    private String training;
    private String teamJob;
    private Team team;
    private Boolean assign;

    /**
     *
     * @param username
     * @param password
     * @param name
     * @param training
     * @param teamJob
     */
    public Coach(String username, String password, String name,String training, String teamJob, SystemController systemController) {
        super(username, password, systemController);
        this.name=name;
        this.training=training;
        this.teamJob=teamJob;
        assign = false;
    }

    public void setAssign(Boolean assign) {
        this.assign = assign;
    }

    /**
     *
     * @return
     */
    public boolean post(String post){

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

    /**
     *
     * @return
     */

    public Team getTeam() {
        return team;
    }

    /**
     *
     * @param team
     */

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public Boolean editDetails() {
        return null;
    }

    public Boolean getAssigned(){
        return this.assign;
    }
}
