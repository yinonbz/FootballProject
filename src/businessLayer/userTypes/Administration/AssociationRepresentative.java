package businessLayer.userTypes.Administration;

import serviceLayer.LeagueController;
import businessLayer.Utilities.Financial.FinancialMonitoring;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.HashSet;

public class AssociationRepresentative extends Subscriber {
    private FinancialMonitoring financialMonitoring;
    private LeagueController leagueController;

    /**
     * @param username
     * @param password
     * @param name
     * @param financialMonitoring
     * @param leaguesController
     */
    public AssociationRepresentative(String username, String password, String name, FinancialMonitoring financialMonitoring, LeagueController leaguesController, SystemController systemController) {
        super(username, password,name,systemController);
        this.name = name;
        this.financialMonitoring = financialMonitoring;
        this.leagueController = leagueController;
    }

    /**
     * constructor
     * @param username
     * @param password
     * @param name
     * @param systemController
     */
    public AssociationRepresentative (String username, String password, String name, SystemController systemController) {
        super(username, password,name, systemController);
    }

    /**
     * @param leagueInfo
     * @return
     */
    public Boolean createLeague(String leagueInfo){
        return true;
    }

    /**
     * @param leagueName
     * @param year
     * @return
     */
    public Boolean createSeason(String leagueName, String year){
        return true;
    }

    /**
     * @return
     */
    public Boolean editReferees(){
        return true;
    }

    /**
     * @return
     */
    public Boolean assignMatchProperties(){
        return true;
    }

    /**
     * @return
     */
    public Boolean definePolicies(){
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

    @Override
    public Boolean editDetails() {
        return null;
    }


    /**
     * the function lets the AR to confirm new teams in the system
     * @param teamName the team the AR wants to approve
     * @return true if the team was approved
     */
    public boolean confirmTeamRequest(String teamName){
        return systemController.confirmTeamByAssociationRepresntative(teamName,this);
    }



}
