package serviceLayer;

import businessLayer.Tournament.League;
import businessLayer.Tournament.LeagueController;
import businessLayer.userTypes.Administration.AssociationRepresentative;
import businessLayer.userTypes.Administration.Referee;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.Date;

public class LeagueService {

    private SystemController systemController;
    private LeagueController leagueController;


    /**
     * Constructor
     */
    public LeagueService() {
        systemController = SystemController.SystemController();
        leagueController = systemController.getLeagueController();
    }


    //-----------------------------AssociationRepresentative------------------------

    /**
     * The function receives username and leagueID from the interface layer and calls the creation function in the business layer
     *
     * @param leagueID
     * @param username
     * @return
     */
    public boolean addLeagueThroughRepresentative(String leagueID, String username) {

        if (leagueID != null && username != null) {
            return leagueController.addLeagueThroughRepresentative(leagueID, username);
        }
        return false;
    }


    /**
     * The function receives username, leagueID, seasonID and dates from the interface layer and calls the creation function in the business layer
     *
     * @param leagueID
     * @param seasonID
     * @param startingDate
     * @param endingDate
     * @param username
     * @return
     */
    public boolean addSeasonThroughRepresentative(String leagueID, int seasonID, Date startingDate, Date endingDate, String username) {

        if (leagueID != null && username != null) {
            return leagueController.addSeasonThroughRepresentative(leagueID, seasonID, startingDate, endingDate, username);
        }
        return false;
    }


    /**
     * The function receives a referee's username and the representative's username from the interface layer and calls the creation function in the business layer
     *
     * @param refUsername
     * @param username
     * @return
     */
    public boolean createRefereeThroughRepresentative(String refUsername, String username) {

        if (refUsername != null && username != null) {
            return leagueController.createRefereeThroughRepresentative(refUsername, username);
        }
        return false;
    }


    /**
     * The function receives a referee's username and the representative's username from the interface layer and calls the removal function in the business layer
     *
     * @param refUsername
     * @param username
     * @return
     */
    public boolean removeRefereeThroughRepresentative(String refUsername, String username) {

        if (refUsername != null && username != null) {
            return leagueController.removeRefereeThroughRepresentative(refUsername, username);
        }
        return false;
    }


    /**
     * The function receives a referee's username, leagueID, seasonID and the representative's username from the interface layer and calls the assigning function in the business layer
     *
     * @param refUsername
     * @param leagueName
     * @param seasonID
     * @param username
     * @return
     */
    public boolean assignRefereeThroughRepresentation(String refUsername, String leagueName, int seasonID, String username) {

        if (refUsername != null && leagueName != null && username != null) {
            return leagueController.assignRefereeThroughRepresentation(refUsername, leagueName, seasonID, username);
        }
        return false;
    }

}
