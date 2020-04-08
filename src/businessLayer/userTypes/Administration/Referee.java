package businessLayer.userTypes.Administration;

import serviceLayer.LeagueController;
import businessLayer.userTypes.Subscriber;

public class Referee extends Subscriber {
    private String name;
    private String training;
    private LeagueController leagueController;

    /**
     * @param username
     * @param password
     * @param name
     * @param training
     * @param leaguesController
     */
    public Referee(String username, String password, String name, String training, LeagueController leaguesController) {
        super(username, password);
        this.name = name;
        this.training = training;
        this.leagueController = leaguesController;
    }

    /**
     *
     */
    public void updateEvents(){

    }

    /**
     *
     */
    public void viewMatchDetails(){

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
    public String getTraining() {
        return training;
    }

    /**
     * @return
     */
    public LeagueController getLeaguesController() {
        return leagueController;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param training
     */
    public void setTraining(String training) {
        this.training = training;
    }

    /**
     * @param leaguesController
     */
    public void setLeaguesController(LeagueController leaguesController) {
        this.leagueController = leaguesController;
    }

    @Override
    public Boolean editDetails() {
        return null;
    }
}
