public class Referee extends Subscriber{
    private String name;
    private String training;
    private LeaguesController leaguesController;

    /**
     * @param username
     * @param password
     * @param systemController
     * @param alertSystem
     * @param name
     * @param training
     * @param leaguesController
     */
    public Referee(String username, String password, SystemController systemController, AlertSystem alertSystem, String name, String training, LeaguesController leaguesController) {
        super(username, password, systemController, alertSystem);
        this.name = name;
        this.training = training;
        this.leaguesController = leaguesController;
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
    public LeaguesController getLeaguesController() {
        return leaguesController;
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
    public void setLeaguesController(LeaguesController leaguesController) {
        this.leaguesController = leaguesController;
    }
}
