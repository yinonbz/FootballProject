package businessLayer.userTypes.Administration;

import businessLayer.Tournament.Match.Match;
import serviceLayer.LeagueController;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.ArrayList;
import java.util.List;

public class Referee extends Subscriber {
    private String name;
    private String training;
    private LeagueController leagueController;
    private List<Match> matches;


    /**
     * @param username
     * @param password
     * @param name
     * @param training
     * @param leaguesController
     */
    public Referee(String username, String password, String name, String training, LeagueController leaguesController, SystemController systemController) {

        super(username, password, systemController);
        this.name = name;
        this.training = training;
        this.leagueController = leaguesController;
        matches = new ArrayList<>();
    }

    /**
     *
     */
    public void updateEvents() {

    }

    /**
     *
     */
    public void viewMatchDetails() {

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


    /**
     * The function receives order from the system controller to remove the referee from every match he's registered to
     *
     * @return
     */
    public boolean removeFromAllMatches() {

        for (Match e : matches) {
            if(!(e.removeReferee(this))){
                return false;
            }
        }
        return true;
    }
}
