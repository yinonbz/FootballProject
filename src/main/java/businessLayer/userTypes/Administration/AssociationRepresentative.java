package businessLayer.userTypes.Administration;

import businessLayer.Exceptions.AlreadyExistException;
import businessLayer.Tournament.LeagueController;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.Date;

public class AssociationRepresentative extends Subscriber {
    private FinancialMonitoring financialMonitoring;
    private LeagueController leagueController;
    private boolean approved; // Approved by an admin after registration

    /**
     * @param username
     * @param password
     * @param name
     * @param financialMonitoring
     * @param leaguesController
     */
    public AssociationRepresentative(String username, String password, String name, FinancialMonitoring financialMonitoring, LeagueController leaguesController, SystemController systemController) {
        super(username, password, name, systemController);
        this.name = name;
        this.financialMonitoring = financialMonitoring;
        this.leagueController = leaguesController;
        leaguesController.addAssociationRepToController(this);
        approved = true; //once Admin's functionality's ready connect to his approval
    }

    /**
     * constructor
     *
     * @param username
     * @param password
     * @param name
     * @param systemController
     */
    public AssociationRepresentative(String username, String password, String name, SystemController systemController, LeagueController leagueController) {
        super(username, password, name, systemController);
        this.systemController = systemController;
        this.leagueController = leagueController;
        approved = true; //once Admin's functionality's ready connect to his approval
    }

    /**
     * the function gets the name of the stadium and the number of seats it has
     *
     * @param nameStadium   the name of the stadium
     * @param numberOfSeats the number of seats in the stadium
     * @return true if the stadium was added successfully
     */
    public boolean createNewStadium(String nameStadium, String numberOfSeats) {
        if (!isApproved())
            return false;
        if (!nameStadium.isEmpty() && !numberOfSeats.isEmpty()) {
            if (tryParseInt(numberOfSeats)) {
                return systemController.addNewStadium(nameStadium, numberOfSeats);
            }
        }
        return false;
    }

    /**
     * The function creates a league in the system and returns a boolean representing whether it was created or not
     *
     * @param newLeagueID
     * @return true/false
     */
    public Boolean createLeague(String newLeagueID) {
        if (!isApproved())
            return false;
        if (newLeagueID == null) {
            return false;
        }
        if (leagueController.doesLeagueExist(newLeagueID)) {
            throw new AlreadyExistException("There is already a League in the system with that name. Please choose a different name.");
        }
        return leagueController.createLeague(newLeagueID);
    }

    /**
     * The function creates a season within a league and returns whether the creation was successful or not
     *
     * @param leagueName
     * @param seasonID
     * @param startingDate
     * @param win
     * @param lose
     * @param tie
     * @param matchingPolicy
     * @param endingDate
     * @return
     */
    public Boolean createSeason(String leagueName, int seasonID, Date startingDate, int win, int lose, int tie, String matchingPolicy, Date endingDate) {
        if (!isApproved())
            return false;
        if (!leagueController.doesLeagueExist(leagueName)) {
            return false;
        }
        if (matchingPolicy == null) {
            return false;
        }
        return leagueController.addSeasonToLeague(leagueName, seasonID, startingDate, endingDate, win, lose, tie, matchingPolicy);
    }

    /**
     * The function creates a new referee in the system, returns whether the creation was successful or not
     *
     * @param username
     * @return true/false
     */
    public boolean createReferee(String username, String role) {
        if (!isApproved())
            return false;
        if (username == null) {
            return false;
        }
        if (!role.equals("MAIN") && !role.equals("ASSISTANT")) {
            role = "MAIN";
        }
        return super.getSystemController().addReferee(username, "1111", "default", role, this.getUsername());
    }

    /**
     * The function removes referee from the entire system
     *
     * @param username
     * @return true/false
     */
    public boolean removeReferee(String username) {
        if (!isApproved())
            return false;
        if (username == null) {
            return false;
        }
        return super.getSystemController().removeReferee(username);
    }


    /**
     * The function receives a referee's username, a league's name and season's ID and assigns the referee to the season within the given league, returns whether was successful or not
     *
     * @param username
     * @param leagueName
     * @param seasonID
     * @return true/false
     */
    public boolean assignRefereeToSeason(String username, String leagueName, int seasonID) {
        if (!isApproved())
            return false;
        if (username == null || leagueName == null) {
            return false;
        }
        return leagueController.addRefereeToSeasonInLeague(username, leagueName, seasonID);
    }

    /**
     * @return
     */
    public Boolean assignMatchProperties() {
        return true;
    }

    /**
     * @return
     */
    public Boolean definePolicies() {
        return true;
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
    public FinancialMonitoring getFinancialMonitoring() {
        return financialMonitoring;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param financialMonitoring
     */
    public void setFinancialMonitoring(FinancialMonitoring financialMonitoring) {
        this.financialMonitoring = financialMonitoring;
    }

    /**
     * @return
     */
    public LeagueController getLeaguesController() {
        return leagueController;
    }

    /**
     * @param leaguesController
     */
    public void setLeaguesController(LeagueController leaguesController) {
        this.leagueController = leaguesController;
    }

    /*
    @Override
    public Boolean editDetails() {
        return null;
    }
    */


    /**
     * the function lets the AR to confirm new teams in the system
     *
     * @param teamName the team the AR wants to approve
     * @return true if the team was approved
     */
    public boolean confirmTeamRequest(String teamName) {
        if (!approved)
            return false;
        return systemController.confirmTeamByAssociationRepresentative(teamName,this.getUsername());
        //return false;
    }

    @Override
    public String toString() {
        return "AR";
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
