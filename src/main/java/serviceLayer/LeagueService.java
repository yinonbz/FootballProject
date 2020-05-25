package serviceLayer;

import businessLayer.Exceptions.MissingInputException;
import businessLayer.Tournament.LeagueController;
import businessLayer.userTypes.SystemController;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

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
            System.out.println(leagueID + " ," + username);
            //need to be connected ido created stab
            return leagueController.addLeagueThroughRepresentative(leagueID, username);
        }
        return false;
    }


    /**
     * The function receives username, leagueID, seasonID and dates from the interface layer and calls the creation function in the business layer
     * @param leagueID
     * @param seasonID
     * @param startingDate
     * @param endingDate
     * @param win
     * @param lose
     * @param tie
     * @param matchingPolicy
     * @param username
     * @return
     */
    public boolean addSeasonThroughRepresentative(String leagueID, int seasonID, Date startingDate, Date endingDate, int win, int lose, int tie, String matchingPolicy, String username) {

        if (leagueID != null && username != null && matchingPolicy != null) {
            if (seasonID >= 1970 && seasonID <= 2021) {
                return leagueController.addSeasonThroughRepresentative(leagueID, seasonID, startingDate, endingDate, win, lose, tie, matchingPolicy, username);
            }else{
                throw new MissingInputException("Please select a Season ID between 1970 and 2021.");
            }
        }
        throw new MissingInputException("Please complete the form.");
    }


    /**
     * The function receives a referee's username and the representative's username from the interface layer and calls the creation function in the business layer
     *
     * @param refUsername
     * @param username
     * @return
     */
    public boolean createRefereeThroughRepresentative(String refUsername, String username, String role) {

        if (refUsername != null && username != null) {
            return leagueController.createRefereeThroughRepresentative(refUsername, username, role);
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
    public boolean assignRefereeThroughRepresentative(String refUsername, String leagueName, int seasonID, String username) {
            return leagueController.assignRefereeThroughRepresentative(refUsername, leagueName, seasonID, username);
    }

    /**
     * The function receives a team's name and the representative's username from the interface layer and calls the assigning function in the business layer
     *
     * @param teamName
     * @param username
     * @return
     */
    public boolean confirmTeamRequestThroughRepresentative(String teamName, String username) {
        if (teamName != null && username != null) {
            return leagueController.confirmTeamRequestThroughRepresentative(teamName, username);
        }
        return false;
    }

    /**
     * The function receives a stadium's identifier, number of seats and the representative's username from the interface layer and calls the creating function in the business layer
     *
     * @param nameStadium
     * @param numberOfSeats
     * @return
     */
    public boolean createNewStadiumThroughRepresentative(String nameStadium, String numberOfSeats, String username) {

        if (nameStadium != null && numberOfSeats != null && username != null) {
            return leagueController.createNewStadiumThroughRepresentative(nameStadium, numberOfSeats, username);
        }
        return false;
    }

    /**
     * the function lets the AR to choose teams and add them to the season
     * @param teamsNames the teams the AR wants to add
     * @param leagueID   the league ID
     * @param seasonID   the season ID
     * @param username   the username
     * @return true if it done successfully
     */
    public boolean chooseTeamForSeason(LinkedList<String> teamsNames, String leagueID , String seasonID, String username){
        return leagueController.chooseTeamForSeason(teamsNames,leagueID,seasonID,username);
    }

    /**
     * the function lets the AR to activate the match policy of a season
     * @param leagueID
     * @param seasonID
     * @param userName
     * @return
     */
    public boolean activateMatchPolicyForSeason(String leagueID, String seasonID, String userName) {
            return leagueController.activateMatchPolicy(leagueID, seasonID, userName);

    }

    /**
     * the function lets an AR or a Referee to update a ranking table of a season
     *
     * @param leagueID the league id the season belongs to
     * @param seasonID the season id
     * @param matchID  the match we want to update on
     * @param username the requester
     * @return true is the action was completed
     */
    public boolean updateSeasonTableRank(String leagueID, String seasonID, String matchID, String username) {
        if (leagueID != null && matchID != null && username != null && seasonID != null) {
            if (tryParseInt(leagueID) && tryParseInt(seasonID) && tryParseInt(matchID)) {
                return leagueController.updateSeasonTableRank(leagueID, seasonID, matchID, username);
            }
        }
        return false;
    }

    /**
     * private function that checks that a string represents an interger
     *
     * @param value the string
     * @return true if it an integer
     */
    protected boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @return all of the unconfirmed team names from the DB
     */
    public ArrayList<String> getAllUnconfirmedTeams(){
        return systemController.getAllUnconfirmedTeamsInDB();
    }

    /**
     * @return all of the league names from DB
     */
    public ArrayList<String> getAllULeagues(){
        return systemController.getAllLeaguesInDB();
    }

    /**
     * @return all of the team names fron DB
     */
    //todo ido added
    public ArrayList<String> getAllTeamsNames(){
        return systemController.getAllTeamsNames();
    }

    /**
     * @return all of the referee names from the DB
     */
    public ArrayList<String> getAllRefereeNames(){
      //  return systemController.getAllRefereeNames(); todo not implemented in db
        return null;
    }


    /**
     * @param league the league's name to get all of it's season
     * @return the season's names of the league
     */
    public ArrayList<String> getAllSeasonsFromLeague(String league){
        return systemController.getAllSeasonsFromLeague(league);
    }

    /**
     * @param userName the offline user name
     * @return get all of the user's offline notifications
     */
    public LinkedList<String> getOfflineMessages(String userName) {
        return systemController.getOfflineUsersNotifications(userName);
    }
}
