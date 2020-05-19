package dataLayer;

import businessLayer.Team.Team;
import businessLayer.Tournament.League;
import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Tournament.Season;
import businessLayer.Utilities.Complaint;
import businessLayer.Utilities.Page;
import businessLayer.userTypes.Administration.*;
import businessLayer.userTypes.Subscriber;
import javafx.util.Pair;

import java.util.*;

public class DemoDB {
    //------------System Controller------------//
    private HashMap<String, Subscriber> systemSubscribers; //name of the username, subscriber
    private HashMap<String, Team> teams; //name of the team, the team object
    private HashMap<String, LinkedList<String>> userNotifications;
    private HashMap<Integer, Complaint> systemComplaints; //complaint id, complaint object
    private HashMap<String, Subscriber> systemAdminApprovalRequests; //name of the username, subscriber (Admin/AR) to approve/disapprove
    private HashMap<String, LinkedList<String>> unconfirmedTeams; //name of the team, details on the team
    private HashMap<String, Stadium> stadiums;
    private HashMap<String, Page> pages;
    private HashMap<Match, LinkedList<String>> followersOfMatch; //every match has many followers
    private HashMap<Page, LinkedList<String>> followersOfPage; //Every page (team's page, player's page or coach's page) has many followers
    private HashMap<Match, LinkedList<String>> matchReferees; //every match has referees, holds the referees for each match
    private HashMap<Team, LinkedList<Player>> teamPlayers;
    private HashMap<Team, LinkedList<TeamManager>> teamTeamManagers;
    private HashMap<Team, LinkedList<Coach>> teamCoaches;
    private HashMap<Team, LinkedList<Stadium>> teamStadiums;
    private HashMap<Team, LinkedList<TeamOwner>> teamTeamOwners;

    //------------League Controller------------//
    private HashMap<String, League> leagues;
    private HashMap<String, AssociationRepresentative> associationRepresentatives;
    private HashMap<String, Referee> referees;

    //------------Match Controller------------//
    private HashMap<Integer, Match> matches;


    public DemoDB() {
        systemSubscribers = new HashMap<>();
        teams = new HashMap<>();
        userNotifications = new HashMap<>();
        systemComplaints = new HashMap<>();
        systemAdminApprovalRequests = new HashMap<>();
        unconfirmedTeams = new HashMap<>();
        leagues = new HashMap<>();
        associationRepresentatives = new HashMap<>();
        referees = new HashMap<>();
        stadiums = new HashMap<>();
        matches = new HashMap<>();
        pages = new HashMap<>();
        followersOfPage = new HashMap<>();
        followersOfMatch = new HashMap<>();
        matchReferees = new HashMap<>();
        teamPlayers = new HashMap<>();
        teamTeamManagers = new HashMap<>();
        teamCoaches = new HashMap<>();
        teamStadiums = new HashMap<>();
        teamTeamOwners = new HashMap<>();

    }

    //-------------------------SYSTEM CONTROLLER-------------------------//


    /**
     * demo function to search in db for subscribers
     *
     * @param subscriberName
     * @return
     */
    public boolean containsInSystemSubscribers(String subscriberName) {
        return systemSubscribers.containsKey(subscriberName);
    }

    /**
     * demo function to get a subscriber object from data base
     *
     * @param subscriberName
     * @return
     */
    public Subscriber selectSubscriberFromDB(String subscriberName) {
        return systemSubscribers.get(subscriberName);
    }

    /**
     * demo function to remove a subscriber from DB
     *
     * @param subscriberName
     * @return
     */
    public boolean removeSubscriberFromDB(String subscriberName) {
        systemSubscribers.remove(subscriberName);
        return true;
    }

    /**
     * demo function to add a subscriber to DB
     *
     * @param subscriberName
     * @param subscriber
     * @return
     */
    public boolean addSubscriberToDB(String subscriberName, Subscriber subscriber) {
        systemSubscribers.put(subscriberName, subscriber);
        return true;
    }

    /**
     * demo function to search in db for teams
     *
     * @param teamName
     * @return
     */
    public boolean containsInTeamsDB(String teamName) {
        return teams.containsKey(teamName);
    }

    /**
     * demo function to get a team object from data base
     *
     * @param teamName
     * @return
     */
    public Team selectTeamFromDB(String teamName) {
        return teams.get(teamName);
    }

    /**
     * demo function to remove a team from DB
     *
     * @param teamName
     * @return
     */
    public boolean removeTeamFromDB(String teamName) {
        teams.remove(teamName);
        return true;
    }

    /**
     * demo function to add a team to DB
     *
     * @param teamName
     * @param team
     * @return
     */
    public boolean addTeamToDB(String teamName, Team team) {
        teams.put(teamName, team);
        return true;
    }

    /**
     * demo function to search in db for notification
     *
     * @param subscriberName
     * @return
     */
    public boolean containsInNotificationDB(String subscriberName) {
        return userNotifications.containsKey(subscriberName);
    }

    /**
     * demo function to get a notification object from data base
     *
     * @param subscriberName
     * @return
     */
    public LinkedList<String> selectNotificationFromDB(String subscriberName) {
        return userNotifications.get(subscriberName);
    }

    /**
     * demo function to remove a notification from DB
     *
     * @param subscriberName
     * @return
     */
    public boolean removeNotificationFromDB(String subscriberName) {
        userNotifications.remove(subscriberName);
        return true;
    }

    /**
     * demo function to add a notification to DB
     *
     * @param subscriberName
     * @param notifications
     * @return
     */
    public boolean addNotificationToDB(String subscriberName, LinkedList<String> notifications) {
        userNotifications.put(subscriberName, notifications);
        return true;
    }

    /**
     * count the number of complaints in the DB
     *
     * @return
     */
    public int countComplaintsInDB() {
        return systemComplaints.size();
    }

    /**
     * count the number of Admin Approval Requests in the DB
     *
     * @return
     */
    public int countAdminApprovalRequestsInDB() {
        return systemAdminApprovalRequests.size();
    }

    /**
     * demo function to display all of the complaints in the system
     *
     * @return
     */
    public HashMap<Integer, Complaint> selectAllComplaints() {
        return systemComplaints;
    }

    /**
     * * demo function to display all of the admin approval requests in the system
     *
     * @return all of the admin approval requests in the DB
     */
    public HashMap<String, Subscriber> selectAllAdminApprovalRequests() {
        return systemAdminApprovalRequests;
    }

    /**
     * demo function to search in db for notification
     *
     * @param complaintID
     * @return
     */
    public boolean containsInComplaintDB(int complaintID) {
        return systemComplaints.containsKey(complaintID);
    }

    /**
     * demo function to search in db for an admin approval by username
     *
     * @param userName the subscriber's username
     * @return true if there is a request for the subscriber with the username
     */
    public boolean containsInAdminApprovalRequestsDB(String userName) {
        return systemAdminApprovalRequests.containsKey(userName);
    }

    /**
     * demo function to get a notification object from data base
     *
     * @param complaintID
     * @return
     */
    public Complaint selectComplaintFromDB(int complaintID) {
        return systemComplaints.get(complaintID);
    }

    /**
     * select an admin to approve/disapprove by username
     *
     * @param userName
     * @return the subscriber to be approve/disapprove
     */
    public Subscriber selectAdminToApproveFromDB(String userName) {
        return systemAdminApprovalRequests.get(userName);
    }

    /**
     * demo function to remove a notification from DB
     *
     * @param complaintID
     * @return
     */
    public boolean removeComplaintFromDB(int complaintID) {
        systemComplaints.remove(complaintID);
        return true;
    }

    /**
     * demo function to remove a admin approval request from DB
     *
     * @param userName
     * @return true
     */
    public boolean removeAdminRequest(String userName) {
        systemAdminApprovalRequests.remove(userName);
        return true;
    }

    /**
     * demo function to add a complaint to DB
     *
     * @param id
     * @param complaint
     * @return
     */
    public boolean addComplaintToDB(int id, Complaint complaint) {
        systemComplaints.put(id, complaint);
        return true;
    }

    /**
     * demo function to add an admin approval request to DB
     *
     * @param userName
     * @param admin
     * @return true
     */
    public boolean addAdminApprovalRequest(String userName, Subscriber admin) {
        systemAdminApprovalRequests.put(userName, admin);
        return true;
    }

    /**
     * demo function to search in db for unconfirmed teams
     *
     * @param teamName
     * @return
     */
    public boolean containsInUnconfirmedTeams(String teamName) {
        return unconfirmedTeams.containsKey(teamName);
    }

    /**
     * demo function to get the details of unconfirmed teams object from data base
     *
     * @param teamName
     * @return
     */
    public LinkedList<String> selectUnconfirmedTeamsFromDB(String teamName) {
        return unconfirmedTeams.get(teamName);
    }

    /**
     * demo function to remove a unconfirmed teams from DB
     *
     * @param teamName
     * @return
     */
    public boolean removeUnconfirmedTeamsFromDB(String teamName) {
        unconfirmedTeams.remove(teamName);
        return true;
    }

    /**
     * demo function to add a unconfirmed teams to DB
     *
     * @param details
     * @param details
     * @return
     */
    public boolean addUnconfirmedTeamsToDB(String teamName, LinkedList<String> details) {
        unconfirmedTeams.put(teamName, details);
        return true;
    }

    /**
     * demo function to search in db for stadium
     *
     * @param stadiumName
     * @return
     */
    public boolean containsInSystemStadium(String stadiumName) {
        return stadiums.containsKey(stadiumName);
    }

    /**
     * demo function to get a stadium object from data base
     *
     * @param stadiumName
     * @return
     */
    public Stadium selectStadiumFromDB(String stadiumName) {
        return stadiums.get(stadiumName);
    }

    /**
     * demo function to remove a stadium from DB
     *
     * @param stadiumName
     * @return
     */
    public boolean removeStadiumFromDB(String stadiumName) {
        stadiums.remove(stadiumName);
        return true;
    }

    /**
     * demo function to add a stadium to DB
     *
     * @param stadiumName
     * @param stadium
     * @return
     */
    public boolean addStadiumToDB(String stadiumName, Stadium stadium) {
        stadiums.put(stadiumName, stadium);
        return true;
    }

    /**
     * demo function that return a random stadium from the stadiums
     *
     * @return
     */
    public Stadium selectRandomStadium() {
        Map.Entry<String, Stadium> entry = stadiums.entrySet().iterator().next();
        Stadium stadium = entry.getValue();
        return stadium;
    }

    /**
     * Returns a team's page from the DB that matches the name which was given
     *
     * @param teamName
     * @return
     */
    public Page getTeamPageByName(String teamName) {
        return pages.get(teamName);
    }


    /**
     * Returns a player's page from the DB that matches the name which was given
     *
     * @param playerName
     * @return
     */
    public Page getPlayerPageByName(String playerName) {
        return pages.get(playerName);
    }

    /**
     * Returns a the coach's page from the DB that matches the name which was given
     *
     * @param coachName
     * @return
     */
    public Page getCoachPageByName(String coachName) {
        return pages.get(coachName);
    }

    /**
     * Adding a follower to a specific page within the database
     *
     * @param page
     * @param username
     * @return
     */
    public boolean addFollowerToPage(Page page, String username) {
        if (followersOfPage.containsKey(page)) {
            if (followersOfPage.get(page).contains(username)) {
                return false;
            }
            followersOfPage.get(page).add(username); //todo: make sure it adds to the list within the hashmap, and not to a temporary list that was receieved through the 'get' function
        } else {
            LinkedList<String> temp = new LinkedList<>();
            temp.add(username);
            followersOfPage.put(page, temp);
        }
        return true;
    }


    /**
     * Adding a follower to a specific match within the database
     *
     * @param match
     * @param username
     * @return
     */
    public boolean addFollowerToMatch(Match match, String username) {
        if (followersOfMatch.containsKey(match)) {
            if (!followersOfMatch.get(match).contains(username)) {
                followersOfMatch.get(match).add(username); //todo: make sure it adds to the list within the hashmap, and not to a temporary list that was receieved through the 'get' function
            } else {
                return false;
            }
        } else {
            LinkedList<String> temp = new LinkedList<>();
            temp.add(username);
            followersOfMatch.put(match, temp);
        }
        return true;
    }

    /**
     * The function receives a page and returns the list of its followers usernames
     *
     * @param page
     * @return
     */
    public LinkedList<String> getPageFollowers(Page page) {
        return followersOfPage.get(page);
    }

    /**
     * The function receives a match and returns the list of its followers useranmes
     *
     * @param match
     * @return
     */
    public LinkedList<String> getMatchFollowers(Match match) {
        return followersOfMatch.get(match);
    }

    public LinkedList<String> getMatchReferees(Match match) {
        return matchReferees.get(match);
    }

    /**
     * The function receives a page and its owner's name and adds it to the DB
     *
     * @param name
     * @param page
     */
    public void addPageToDB(String name, Page page) {
        pages.put(name, page);
    }

    /**
     * The function receives a player and a team and adds the player to the team's players data structure
     *
     * @param player
     * @param team
     */
    public void addPlayerToTeam(Player player, Team team) {
        if (teamPlayers.containsKey(team)) {
            if (!teamPlayers.get(team).contains(player)) {
                teamPlayers.get(team).add(player);
            }
        } else {
            LinkedList<Player> temp = new LinkedList<>();
            temp.add(player);
            teamPlayers.put(team, temp);
        }
    }

    /**
     * The function receives a team-manager and a team and adds the team-manager to the team's managers data structure
     *
     * @param teamManager
     * @param team
     */
    public void addTeamManagerToTeam(TeamManager teamManager, Team team) {
        if (teamTeamManagers.containsKey(team)) {
            if (!teamTeamManagers.get(team).contains(teamManager)) {
                teamTeamManagers.get(team).add(teamManager);
            }
        } else {
            LinkedList<TeamManager> temp = new LinkedList<>();
            temp.add(teamManager);
            teamTeamManagers.put(team, temp);
        }
    }

    /**
     * The function receives a coach and a team and adds the coach to the team's coaches data structure
     *
     * @param coach
     * @param team
     */
    public void addCoachToTeam(Coach coach, Team team) {
        if (teamCoaches.containsKey(team)) {
            if (!teamCoaches.get(team).contains(coach)) {
                teamCoaches.get(team).add(coach);
            }
        } else {
            LinkedList<Coach> temp = new LinkedList<>();
            temp.add(coach);
            teamCoaches.put(team, temp);
        }
    }


    /**
     * The function receives a coach and a team and adds the coach to the team's stadiums data structure
     *
     * @param stadium
     * @param team
     */
    public void addStadiumToTeam(Stadium stadium, Team team) {
        if (teamStadiums.containsKey(team)) {
            if (!teamStadiums.get(team).contains(stadium)) {
                teamStadiums.get(team).add(stadium);
            }
        } else {
            LinkedList<Stadium> temp = new LinkedList<>();
            temp.add(stadium);
            teamStadiums.put(team, temp);
        }
    }

    /**
     * The function receives a team-owner and a team and adds the team-owner to the team's owners data structure
     *
     * @param owner
     * @param team
     */
    public void addOwnerToTeam(TeamOwner owner, Team team) {
        if (teamTeamOwners.containsKey(team)) {
            if (!teamTeamOwners.get(team).contains(owner)) {
                teamTeamOwners.get(team).add(owner);
            }
        } else {
            LinkedList<TeamOwner> temp = new LinkedList<>();
            temp.add(owner);
            teamTeamOwners.put(team, temp);
        }
    }

    /**
     * The function returns the team's team-owners
     *
     * @param team
     * @return
     */
    public LinkedList<TeamOwner> getTeamTeamOwners(Team team) {
        return teamTeamOwners.get(team);
    }

    /**
     * The function returns the team's team-owners
     *
     * @param team
     * @return
     */
    public LinkedList<TeamManager> getTeamTeamManagers(Team team) {
        return teamTeamManagers.get(team);
    }

    /**
     * The function returns the admins user-names of the system
     *
     * @return
     */
    public LinkedList<String> getAdminsSubscribers() {
        LinkedList<String> admins = new LinkedList<>();
        for (Map.Entry entry : systemSubscribers.entrySet()) {
            if (entry.getValue() instanceof Admin) {
                admins.add((String) entry.getKey());
            }
        }
        return admins;
    }

    /**
     * The function receives a team and an owner, verifies the owner is an owner of the team, then removes him
     * and returns his username
     *
     * @param team
     * @param owner
     * @return
     */
    public String removeOwnerFromTeam(Team team, TeamOwner owner) {

        if (teamTeamOwners.containsKey(team)) {
            LinkedList<TeamOwner> owners = teamTeamOwners.get(team);
            for (TeamOwner t : owners) {
                if (t.getUsername().equals(owner.getUsername())) {
                    String result = t.getUsername();
                    owners.remove(t);
                    teamTeamOwners.put(team, owners);
                    return result;
                }
            }
        }
        return null;
    }

    //-------------------------LEAGUE CONTROLLER-------------------------//

    /**
     * demo function to search in db for League
     *
     * @param leagueID
     * @return
     */
    public boolean containsInSystemLeague(String leagueID) {
        return leagues.containsKey(leagueID);
    }

    /**
     * demo function to get a League object from data base
     *
     * @param leagueID
     * @return
     */
    public League selectLeagueFromDB(String leagueID) {
        return leagues.get(leagueID);
    }

    /**
     * demo function to remove a League from DB
     *
     * @param leagueID
     * @return
     */
    public boolean removeLeagueFromDB(String leagueID) {
        leagues.remove(leagueID);
        return true;
    }

    /**
     * demo function to add a League to DB
     *
     * @param leagueID
     * @param league
     * @return
     */
    public boolean addLeagueToDB(String leagueID, League league) {
        leagues.put(leagueID, league);
        return true;
    }

    /**
     * demo function to search in db for League
     *
     * @param userName
     * @return
     */
    public boolean containsInSystemAssociationRepresentative(String userName) {
        return associationRepresentatives.containsKey(userName);
    }

    /**
     * demo function to get a League object from data base
     *
     * @param userName
     * @return
     */
    public AssociationRepresentative AssociationRepresentativeLeagueFromDB(String userName) {
        return associationRepresentatives.get(userName);
    }

    /**
     * demo function to remove a League from DB
     *
     * @param userName
     * @return
     */
    public boolean removeAssociationRepresentativeFromDB(String userName) {
        associationRepresentatives.remove(userName);
        return true;
    }

    /**
     * demo function to add a League to DB
     *
     * @param userName
     * @param ar
     * @return
     */
    public boolean addAssociationRepresentativeToDB(String userName, AssociationRepresentative ar) {
        associationRepresentatives.put(userName, ar);
        return true;
    }

    /**
     * demo function to search in db for League
     *
     * @param username
     * @return
     */
    public boolean containsInSystemReferee(String username) {
        return referees.containsKey(username);
    }

    /**
     * demo function to get a League object from data base
     *
     * @param username
     * @return
     */
    public Referee selectRefereeFromDB(String username) {
        return referees.get(username);
    }

    /**
     * demo function to remove a League from DB
     *
     * @param username
     * @return
     */
    public boolean removeRefereeFromDB(String username) {
        referees.remove(username);
        return true;
    }

    /**
     * demo function to add a League to DB
     *
     * @param username
     * @param ref
     * @return
     */
    public boolean addRefereeToDB(String username, Referee ref) {
        referees.put(username, ref);
        return true;
    }

    /**
     * The function receives a match and a username of a referee, connects them to each other within the DB and returns whether the operation was successful
     *
     * @param match
     * @param username
     * @return
     */
    public boolean addRefereeToMatch(Match match, String username) {
        Subscriber user = selectSubscriberFromDB(username);
        if (!(user instanceof Referee)) {
            return false;
        }
        if (matches.containsKey(match.getMatchId()) && matchReferees.containsKey(match)) {
            if (matchReferees.get(match).contains(username)) {
                return false;
            }
            matchReferees.get(match).add(username);
            return true;
        } else if (matches.containsKey(match.getMatchId())) {
            LinkedList<String> temp = new LinkedList<>();
            temp.add(username);
            matchReferees.put(match, temp);
            return true;
        }
        return false;
    }

    //-------------------------MATCH CONTROLLER-------------------------//

    /**
     * demo function to search in db for Match
     *
     * @param matchID
     * @return
     */
    public boolean containsInSystemMatch(int matchID) {
        return matches.containsKey(matchID);
    }

    /**
     * demo function to get a Match object from data base
     *
     * @param matchID
     * @return
     */
    public Match selectMatchFromDB(int matchID) {
        return matches.get(matchID);
    }

    /**
     * demo function to remove a Match from DB
     *
     * @param matchID
     * @return
     */
    public boolean removeMatchFromDB(int matchID) {
        matches.remove(matchID);
        return true;
    }

    /**
     * demo function to add a Match to DB
     *
     * @param matchID
     * @param match
     * @return
     */
    public boolean addMatchToDB(int matchID, Match match) {
        matches.put(matchID, match);
        return true;
    }

    //Season

    /**
     * demo function to find a season in a league
     *
     * @param leagueID
     * @param seasonID
     * @return
     */
    public boolean containsInSystemSeason(String leagueID, String seasonID) {
        League league = selectLeagueFromDB(leagueID);
        return league.containsSeason(seasonID);
    }

    /**
     * demo function to get a season from a league
     *
     * @param leagueID
     * @param seasonID
     * @return
     */
    public Season selectSeasonFromDB(String leagueID, String seasonID) {
        League league = selectLeagueFromDB(leagueID);
        Season s = league.getSeasonFromLeague(seasonID);
        return s;
    }


    //-------------------------GETTERS AND SETTERS-------------------------//

    public HashMap<String, Subscriber> getSystemSubscribers() {
        return systemSubscribers;
    }

    public void setSystemSubscribers(HashMap<String, Subscriber> systemSubscribers) {
        this.systemSubscribers = systemSubscribers;
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    public void setTeams(HashMap<String, Team> teams) {
        this.teams = teams;
    }

    public HashMap<String, LinkedList<String>> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(HashMap<String, LinkedList<String>> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public HashMap<Integer, Complaint> getSystemComplaints() {
        return systemComplaints;
    }

    public void setSystemComplaints(HashMap<Integer, Complaint> systemComplaints) {
        this.systemComplaints = systemComplaints;
    }


    public HashMap<String, LinkedList<String>> getUnconfirmedTeams() {
        return unconfirmedTeams;
    }

    public void setUnconfirmedTeams(HashMap<String, LinkedList<String>> unconfirmedTeams) { this.unconfirmedTeams = unconfirmedTeams; }

    public HashMap<String, Stadium> getStadiums() {
        return stadiums;
    }

    public void setStadiums(HashMap<String, Stadium> stadiums) {
        this.stadiums = stadiums;
    }

    public HashMap<Integer, Match> getMatches() {
        return matches;
    }

    public HashMap<String, League> getLeagues() { return leagues; }
}
