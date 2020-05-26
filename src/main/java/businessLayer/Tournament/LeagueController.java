package businessLayer.Tournament;

import businessLayer.Exceptions.MissingInputException;
import businessLayer.Exceptions.NotApprovedException;
import businessLayer.Exceptions.NotFoundInDbException;
import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Utilities.alertSystem.AlertSystem;
import businessLayer.Utilities.logSystem.LoggingSystem;
import businessLayer.userTypes.Administration.AssociationRepresentative;
import businessLayer.userTypes.Administration.Referee;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.*;

public class LeagueController {

    //----------------OLD DATA STRUCTURES THAT ARE LOCATED IN THE DB-----------------------//
    //private HashMap<String, League> leagues;
    //private businessLayer.Tournament.RankingPolicy rankingPolicy; //might be list as well, define later
    //private businessLayer.Tournament.MatchingPolicy matchingPolicy; //might be list as well, define later
    //private List<AssociationRepresentative> associationRepresentatives;
    //private HashMap<String, Referee> referees;

    private LoggingSystem loggingSystem;
    private AlertSystem alertSystem;
    private SystemController systemController;
    //private DemoDB DB;

    public LeagueController() {

    }

    public void setSystemController(SystemController systemController) {
        this.systemController = systemController;
    }

    /**
     * get a random stadium from the DB
     *
     * @return
     */
    public Stadium getRandomStadium() {
        return systemController.findDefaultStadium();
    }

    /**
     * @param alerts
     * @return
     */
    /*
    public boolean sendAlerts(List<String> alerts) {

        return true;
    }
    */

    /**
     * @param logs
     * @return
     */
    public boolean sendLogs(List<String> logs) {

        return true;
    }


/**
 *
 * @return
 *//*

    public businessLayer.Tournament.RankingPolicy getRankingPolicy() {
        return rankingPolicy;
    }

    */
/**
 *
 * @param rankingPolicy
 *//*

    public void setRankingPolicy(businessLayer.Tournament.RankingPolicy rankingPolicy) {
        this.rankingPolicy = rankingPolicy;
    }

    */
/**
 *
 * @return
 *//*

    public businessLayer.Tournament.MatchingPolicy getMatchingPolicy() {
        return matchingPolicy;
    }

    */
/**
 *
 * @param matchingPolicy
 *//*

    public void setMatchingPolicy(businessLayer.Tournament.MatchingPolicy matchingPolicy) {
        this.matchingPolicy = matchingPolicy;
    }
*/

    /**
     * @return
     */
    public LoggingSystem getLoggingSystem() {
        return loggingSystem;
    }

    /**
     * @param loggingSystem
     */
    public void setLoggingSystem(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }


    /**
     * @return
     */
    public AlertSystem getAlertSystem() {
        return alertSystem;
    }

    /**
     * @param alertSystem
     */
    public void setAlertSystem(AlertSystem alertSystem) {
        this.alertSystem = alertSystem;
    }

    /**
     * The function returns whether a league with the same ID exists
     *
     * @param leagueID
     * @return true/false
     */
    public boolean doesLeagueExist(String leagueID) {
        return systemController.containsLeague(leagueID);
    }

    /**
     * The function creates a league and returns whether the league was created successfully or not
     *
     * @param leagueID
     * @return true/false
     */
    public boolean createLeague(String leagueID) {

        if (leagueID == null) {
            return false;
        }
        //League newLeague = new League(leagueID);
        systemController.addLeagueToDB(leagueID);
        if (!systemController.containsLeague(leagueID)) {
            return false;
        }
        return true;
    }

    /**
     * The function creates a season within league and returns whether the season was created successfully or not
     * @param leagueID
     * @param seasonID
     * @param startingDate
     * @param endingDate
     * @param win
     * @param lose
     * @param tie
     * @param matchingPolicy
     * @return
     */
    public boolean addSeasonToLeague(String leagueID, int seasonID, Date startingDate, Date endingDate, int win, int lose, int tie, String matchingPolicy) {

        League leagueToAdd = systemController.getLeagueFromDB(leagueID);
        if (leagueToAdd == null) {
            return false;
        }
        if(matchingPolicy == null || (!matchingPolicy.equals("SingleMatchPolicy") && !matchingPolicy.equals("ClassicMatchPolicy"))) {
            return false;
        }
        return leagueToAdd.addSeasonToLeague(seasonID, startingDate, endingDate, win, lose, tie, matchingPolicy);
        //todo: check if when you pull out a complex object from a hashmap, the changes you do to it are registered in the hashmap
    }

    /**
     * The function receives referee from the system controller, removes it from its data structures and returns whether the removal was successful or not
     *
     * @param referee
     * @return true/false
     */
    public boolean removeReferee(Subscriber referee) {
        if (!(referee instanceof Referee)) {
            return false;
        }
        String refName = referee.getUsername();
        if (systemController.containsReferee(refName)) {
            systemController.removeReferee(refName);
            return true;
        }
        return false;
    }


    /**
     * The function assigns a referee from the system to a season within a specific league, returns whether the assignment was successful or not
     *
     * @param refUserName
     * @param leagueName
     * @param seasonID
     * @return true/false
     */
    public boolean addRefereeToSeasonInLeague(String refUserName, String leagueName, int seasonID) {

        if (refUserName == null || leagueName == null) {
            return false;
        }
        if (systemController.containsLeague(leagueName) && systemController.containsReferee(refUserName)) {
            League addingToLeague = systemController.getLeagueFromDB(leagueName);
            Referee refToAssign = systemController.getRefereeFromDB(refUserName);
            //if(addingToLeague.addRefereeToSeason(refToAssign, seasonID)){
                return systemController.addRefereeToSeason(leagueName,seasonID,refUserName);
            //}
        }
        return false;
    }


    /**
     * The function adds an association representative to the data structure in the league controller
     *
     * @param associationRep
     */
    public void addAssociationRepToController(AssociationRepresentative associationRep) {
        if (associationRep != null) {
            if (!systemController.containsInSystemAssociationRepresentative(associationRep.getUsername())) {
                systemController.addSubscriber(associationRep);
            }
        }
    }


    /**
     * The function adds referee to the league controller data structures by receiving it from the system controller
     *
     * @param referee
     */
    public void addRefereeToDataFromSystemController(Referee referee) {

        if (referee != null && !systemController.containsReferee(referee.getUsername())) {
            systemController.addSubscriber(referee);
        }
    }


    //-----------------------------AssociationRepresentative: Link to Service Layer------------------------

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
            if(startingDate.after(endingDate)){
                throw new MissingInputException("Starting Date must be before the Ending Date.");
            }
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.createSeason(leagueID, seasonID, startingDate, win, lose, tie, matchingPolicy, endingDate);
            }
        }
        throw new MissingInputException("Please complete the form.");

        //return false;
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
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.createReferee(refUsername, role);
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
                return userRep.removeReferee(refUsername);
            }
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

        if (refUsername != null && leagueName != null && username != null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.assignRefereeToSeason(refUsername, leagueName, seasonID);
            }
        }
        throw new MissingInputException("Please complete this form to add a Referee to a Season.");
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
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.confirmTeamRequest(teamName);
            }
        }
        return false;
    }


    /**
     * The function receives a stadium's identifier, number of seats and the representative's username from the interface layer and calls the creating function in the business layer
     * @param nameStadium
     * @param numberOfSeats
     * @param username
     * @return
     */
    public boolean createNewStadiumThroughRepresentative(String nameStadium, String numberOfSeats, String username) {

        if (nameStadium != null && numberOfSeats != null && username != null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative) {
                AssociationRepresentative userRep = (AssociationRepresentative) user;
                return userRep.createNewStadium(nameStadium, numberOfSeats);
            }
        }
        return false;
    }

    /**
     * add the teams to the season in order to activate the season match policy in the future
     * @param teamsNames all of the teams the user wants to add
     * @param leagueID the league id
     * @param seasonID the season
     * @param userName the requester
     * @return true if at least one team was added
     */
    public boolean chooseTeamForSeason(LinkedList<String> teamsNames, String leagueID, String seasonID, String userName) {
        if (seasonID != null && userName != null && leagueID!=null) {
            Subscriber user = systemController.getSubscriberByUserName(userName);
            if(user instanceof AssociationRepresentative){
                Season season = systemController.selectSeasonFromDB(leagueID,seasonID);
                if(season!=null){
                    for(String teamName : teamsNames){
                        Team team = systemController.getTeamByName(teamName);
                        if(team!=null){
                            season.addTeamToSeason(team);
                            systemController.addTeamToSeasonDB(leagueID,Integer.parseInt(seasonID),team.getTeamName()); //todo DB
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        throw new MissingInputException("Missing Input");
    }

    /**
     * the function activates the match policy of the season
     * @param leagueID
     * @param seasonID
     * @param userName
     * @return
     */
    public boolean activateMatchPolicy(String leagueID, String seasonID, String userName){
        if (seasonID != null && userName != null && leagueID!=null) {
            Subscriber user = systemController.getSubscriberByUserName(userName);
            if (user instanceof AssociationRepresentative) {
                Season season = systemController.selectSeasonFromDB(leagueID, seasonID);
                if (season != null) {
                    if (season.getTeams() != null) {
                        if (season.getTeams().size() > 1) {
                            if(season.checkIfRefereeIsAssignedToSeason()) {
                                boolean success = season.activateMatchPolicy(this);
                                HashMap <Integer,Match> matches = season.getMatchesOfTheSeason();
                                //Integer [] keys = (Integer [] )matches.keySet().toArray();
                                LinkedList<Integer> keys = new LinkedList<>();
                                keys.addAll(matches.keySet());
                               systemController.addMatchTableToSeason(leagueID, Integer.parseInt(seasonID), keys); //todo DB
                               return success;
                            }
                            else{
                                throw new NotApprovedException("The Season must have at least one Referee before activation. Please add Referees for the Season.");
                            }
                        }
                        else{
                            throw new NotApprovedException("The Season must have at least 2 Teams before activation. Please add more teams for the Season.");
                        }
                    }
                }
            }
            return false;
        }
        throw new MissingInputException("Please select a League and a Season to activate.");
    }

    /**
     * the function lets the AR or the Referee to update the ranking table of a season
     * @param leagueID the league id the season belongs to
     * @param seasonID the season id
     * @param matchID the match we want to update on
     * @param username the requester
     * @return true is the action was completed
     */
    public boolean updateSeasonTableRank(String leagueID, String seasonID, String matchID, String username) {
        if (seasonID != null && username != null && leagueID!=null) {
            Subscriber user = systemController.getSubscriberByUserName(username);
            if (user instanceof AssociationRepresentative || user instanceof Referee) {
                Season season = systemController.selectSeasonFromDB(leagueID, seasonID);
                if (season != null) {
                        Match match = systemController.findMatch(Integer.parseInt(matchID));
                            if(match!=null){
                                if(season.seasonContainsMatch(Integer.parseInt(matchID))){
                                    if(season.updateMatchTableRank(match)){
                                        HashMap<Team,LinkedList<Integer>> table = season.getLeagueTable();
                                        LinkedList<Integer> homeValues = table.get(match.getHomeTeam());
                                        LinkedList<Integer> awayValues = table.get(match.getAwayTeam());
                                        systemController.updateMatchTableOFSeason(leagueID,Integer.parseInt(seasonID),
                                                match.getHomeTeam().getTeamName(),homeValues);
                                        systemController.updateMatchTableOFSeason(leagueID,Integer.parseInt(seasonID),
                                                match.getAwayTeam().getTeamName(),awayValues);
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
        return false;
    }

    /**
     * the function add the matches of the season to the DB
     * @param matchesOfTheSeason
     * @return
     */
    public boolean updateMatchTableInDB(HashMap <Integer, Match> matchesOfTheSeason, String leagueID, int seasonID){
        return systemController.addMatchTableOfSeason(matchesOfTheSeason, leagueID, seasonID);
    }
}