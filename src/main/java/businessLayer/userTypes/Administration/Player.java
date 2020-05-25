package businessLayer.userTypes.Administration;

import businessLayer.Team.Team;
//import businessLayer.Utilities.Page;
import businessLayer.Utilities.Page;
import businessLayer.Utilities.HasPage;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

public class Player extends Subscriber implements OwnerEligible, HasPage {

    private TeamOwner teamOwner;
    private String birthDate;
    private FIELDJOB fieldJob;
    private Team team;
    private int salary;
    private Page playerPage;

    /**
     *
     * @param username
     * @param password
     * @param name
     * @param birthDate
     * @param fieldJob
     * @param salary
     * @param team
     * @param systemController
     */
    public Player(String username, String password, String name, String birthDate, FIELDJOB fieldJob ,int salary, Team team, SystemController systemController) {
        super(username, password,name,systemController);
        this.birthDate = birthDate;
        this.fieldJob = fieldJob;
        this.team = team;
        this.teamOwner =null;
        this.salary= salary;
        playerPage = new Page(username,name,birthDate, this);
        systemController.addPageToDB(username,playerPage);
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * @param fieldJob
     */
    public void setFieldJob(FIELDJOB fieldJob) {
        this.fieldJob = fieldJob;
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
    public String getBirthDate() {
        return birthDate;
    }

    public FIELDJOB getFieldJob() {
        return fieldJob;
    }

    @Override
    public String getType() {
        return "Player";
    }

    /**
     * @return
     */
    public Team getTeam() {
        return team;
    }
/*
    @Override
    public Boolean editDetails() {
        return null;
    }
*/

    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * this function determine if the Player is also an Owner
     * @return true if also an owner, false if only player
     */
    @Override
    public boolean isOwner() {
        if(teamOwner ==null){
            return false;
        }
        return true;
    }

    /**
     * get team owner
     * @return team owner
     */

    public TeamOwner getTeamOwner() {
        return teamOwner;
    }

    /**
     * set team owner
     * @param teamOwner team owner to be set
     */

    public void setTeamOwner(TeamOwner teamOwner) {
        this.teamOwner = teamOwner;
    }

    @Override
    public String toString() {
        return "Player";
    }

    public boolean updatePage(String update){
        return playerPage.update(update);
    }


}
