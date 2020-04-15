package serviceLayer;

import businessLayer.Tournament.League;
import businessLayer.Tournament.LeagueController;
import businessLayer.userTypes.Administration.AssociationRepresentative;
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
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.createLeague(leagueID);
            }
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
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.createSeason(leagueID, seasonID, startingDate, endingDate);
            }
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
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.createReferee(refUsername);
            }
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
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.removeRefree(refUsername);
            }
        }
        return false;
    }


    /**
     * The function receives a referee's username, leagueID, seasonID and the representative's username from the interface layer and calls the assigning function in the business layer
     * @param refUsername
     * @param leagueName
     * @param seasonID
     * @param username
     * @return
     */
    public boolean assignRefereeThroughRepresentation(String refUsername, String leagueName, int seasonID, String username) {

        if (refUsername != null && leagueName != null && username != null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.assignRefereeToSeason(refUsername, leagueName, seasonID);
            }
        }
        return false;
    }
}
