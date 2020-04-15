package dataLayer;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Stadium;
import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Subscriber;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DemoDB {
    private HashMap<String, Subscriber> systemSubscribers; //name of the username, subscriber
    private HashMap<String, Team> teams; //name of the team, the team object
    private HashMap <String, LinkedList<String>> userNotifications;
    private HashMap<Integer, Complaint> systemComplaints; //complaint id, complaint object
    private HashMap<String, LinkedList<String>> unconfirmedTeams; //name of the team, details on the team
    private HashMap<String, Stadium> stadiums;



    public DemoDB(){
        systemSubscribers = new HashMap<>();
        teams = new HashMap<>();
        userNotifications = new HashMap<>();
        systemComplaints = new HashMap<>();
        unconfirmedTeams = new HashMap<>();
    }

    /**
     * demo function to search in db for subscribers
     * @param subscriberName
     * @return
     */
    public boolean containsInSystemSubscribers(String subscriberName){
        return systemSubscribers.containsKey(subscriberName);
    }

    /**
     * demo function to get a subscriber object from data base
     * @param subscriberName
     * @return
     */
    public Subscriber selectSubscriberFromDB(String subscriberName){
        return systemSubscribers.get(subscriberName);
    }

    /**
     * demo function to remove a subscriber from DB
     * @param subscriberName
     * @return
     */
    public boolean removeSubscriberFromDB (String subscriberName){
        systemSubscribers.remove(subscriberName);
        return true;
    }

    /**
     * demo function to add a subscriber to DB
     * @param subscriberName
     * @param subscriber
     * @return
     */
    public boolean addSubscriberToDB (String subscriberName, Subscriber subscriber){
        systemSubscribers.put(subscriberName, subscriber);
        return true;
    }

    /**
     * demo function to search in db for teams
     * @param teamName
     * @return
     */
    public boolean containsInTeamsDB(String teamName){
        return teams.containsKey(teamName);
    }

    /**
     * demo function to get a team object from data base
     * @param teamName
     * @return
     */
    public Team selectTeamFromDB(String teamName){
        return teams.get(teamName);
    }

    /**
     * demo function to remove a team from DB
     * @param teamName
     * @return
     */
    public boolean removeTeamFromDB (String teamName){
        teams.remove(teamName);
        return true;
    }

    /**
     * demo function to add a team to DB
     * @param teamName
     * @param team
     * @return
     */
    public boolean addTeamToDB (String teamName, Team team){
        teams.put(teamName, team);
        return true;
    }

    /**
     * demo function to search in db for notification
     * @param subscriberName
     * @return
     */
    public boolean containsInNotificationDB(String subscriberName){
        return userNotifications.containsKey(subscriberName);
    }

    /**
     * demo function to get a notification object from data base
     * @param subscriberName
     * @return
     */
    public LinkedList<String> selectNotificationFromDB(String subscriberName){
        return userNotifications.get(subscriberName);
    }

    /**
     * demo function to remove a notification from DB
     * @param subscriberName
     * @return
     */
    public boolean removeNotificationFromDB (String subscriberName){
        userNotifications.remove(subscriberName);
        return true;
    }

    /**
     * demo function to add a notification to DB
     * @param subscriberName
     * @param notifications
     * @return
     */
    public boolean addNotificationToDB (String subscriberName, LinkedList<String> notifications){
        userNotifications.put(subscriberName, notifications);
        return true;
    }

    /**
     * count the number of complaints in the DB
     * @return
     */
    public int countComplaintsInDB (){
        return systemComplaints.size();
    }

    /**
     * demo function to display all of the complaints in the system
     * @return
     */
    public HashMap <Integer, Complaint> selectAllComplaints(){
        return systemComplaints;
    }

    /**
     * demo function to search in db for notification
     * @param complaintID
     * @return
     */
    public boolean containsInComplaintDB(int complaintID){
        return systemComplaints.containsKey(complaintID);
    }

    /**
     * demo function to get a notification object from data base
     * @param complaintID
     * @return
     */
    public Complaint selectComplaintFromDB(int complaintID){
        return systemComplaints.get(complaintID);
    }

    /**
     * demo function to remove a notification from DB
     * @param complaintID
     * @return
     */
    public boolean removeComplaintFromDB (int complaintID){
        systemComplaints.remove(complaintID);
        return true;
    }

    /**
     * demo function to add a complaint to DB
     * @param id
     * @param complaint
     * @return
     */
    public boolean addComplaintToDB(int id, Complaint complaint){
        systemComplaints.put(id,complaint);
        return true;
    }

    /**
     * demo function to search in db for unconfirmed teams
     * @param teamName
     * @return
     */
    public boolean containsInUnconfirmedTeams(String teamName){
        return unconfirmedTeams.containsKey(teamName);
    }

    /**
     * demo function to get the details of unconfirmed teams object from data base
     * @param teamName
     * @return
     */
    public LinkedList<String> selectUnconfirmedTeamsFromDB(String teamName){
        return unconfirmedTeams.get(teamName);
    }

    /**
     * demo function to remove a unconfirmed teams from DB
     * @param teamName
     * @return
     */
    public boolean removeUnconfirmedTeamsFromDB (String teamName){
        unconfirmedTeams.remove(teamName);
        return true;
    }

    /**
     * demo function to add a unconfirmed teams to DB
     * @param details
     * @param details
     * @return
     */
    public boolean addUnconfirmedTeamsToDB (String teamName, LinkedList <String> details){
        unconfirmedTeams.put(teamName, details);
        return true;
    }


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

    public void setUnconfirmedTeams(HashMap<String, LinkedList<String>> unconfirmedTeams) {
        this.unconfirmedTeams = unconfirmedTeams;
    }

    public HashMap<String, Stadium> getStadiums() {
        return stadiums;
    }

    public void setStadiums(HashMap<String, Stadium> stadiums) {
        this.stadiums = stadiums;
    }
}
