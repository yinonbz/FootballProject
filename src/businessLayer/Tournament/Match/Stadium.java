package businessLayer.Tournament.Match;

import businessLayer.Team.Team;

import java.util.HashMap;

public class Stadium {
    private String name;
    private Match currentMatch;
    private HashMap <Integer,Match> previousMatches;
    private int numberOfSeats;
    private HashMap <String, Team> owners;
    private int ticketCost;

    //private Team currentPlayingTeam1;
    //private Team currentPlayingTeam2;


    /**
     * the constructor of stadium
     * @param name
     * @param currentMatch
     * @param previousMatches
     * @param numberOfSeats
     * @param owners
     */
    public Stadium(String name, Match currentMatch, HashMap <Integer,Match> previousMatches, int numberOfSeats, HashMap <String, Team> owners ) {
        this.name = name;
        this.currentMatch = currentMatch;
        this.previousMatches=previousMatches;
        this.numberOfSeats=numberOfSeats;
        this.owners=owners;
        if(this.owners ==null){
            this.owners = new HashMap<>();
        }
        this.ticketCost = 75;//default price todo check with tomer
    }



    /**
     * the function creates a new stadium in the system
     * @param name the name of the stadium
     * @param numberOfSeats the number of seats the stadium has
     */
    public Stadium (String name, int numberOfSeats){
        this.name = name;
        currentMatch = null;
        previousMatches = new HashMap<>();
        this.numberOfSeats = numberOfSeats;
        this.owners = new HashMap<>();
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
    public Match getCurrentMatch() {
        return currentMatch;
    }


    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param currentMatch
     */
    public void setCurrentMatch(Match currentMatch) {
        this.currentMatch = currentMatch;
    }

    public HashMap<Integer, Match> getPreviousMatches() {
        return previousMatches;
    }

    public void setPreviousMatches(HashMap<Integer, Match> previousMatches) {
        this.previousMatches = previousMatches;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public HashMap<String, Team> getOwners() {
        return owners;
    }

    public void setOwners(HashMap<String, Team> owners) {
        this.owners = owners;
    }

    /**
     * this function check if a team owns the stadium
     * @param team the team that need to be check if in a owner map
     * @return true if false if a team contain in the map
     */
    public boolean containTeam(Team team) {
        if(owners.containsKey(team.getTeamName())){
            return true;
        }
        return false;
    }

    /**
     * remove the team from the owners map
     * @param team that need to be removed
     */
    public void removeTeam(Team team) {
        this.owners.remove(team.getTeamName());
    }

    /**
     * add team to owners map
     * @param team that need to be added
     */
    public void addTeam(Team team) {
        this.owners.put(team.getTeamName(),team);
    }

    /**
     * this function get the cost of the ticket
     * @return the cost of the ticket
     */
    public int getTicketCost() {
        return ticketCost;
    }

    /**
     * this function is a setter that set the cost of the ticket
     * @param ticketCost the ticket cost
     */
    public void setTicketCost(int ticketCost) {
        this.ticketCost = ticketCost;
    }

    /**
     * this function calculate the income of the stadium according to the tea, that played and own the stadium
     * @param team the team that own the stadium
     * @return the income of the team
     */
    public int calculateIncome(Team team) {
        int sum=0;
        if(owners.containsKey(team.getTeamName())){
            for (Integer i:previousMatches.keySet()) {
                Match match = previousMatches.get(i);
                if(match.getHomeTeam().getTeamName().equals(team.getTeamName())){
                    sum = sum+(match.getNumerOfFans()/2)*ticketCost;
                }
            }
            return sum;
        }
        return -1;
    }
}
