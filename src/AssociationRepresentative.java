import java.util.Map;

public class AssociationRepresentative extends Subscriber{
    private String name;
    private FinancialMonitoring financialMonitoring;
    private LeagueController leagueController;

    /**
     * @param username
     * @param password
     * @param name
     * @param financialMonitoring
     * @param leaguesController
     */
    public AssociationRepresentative(String username, String password, String name, FinancialMonitoring financialMonitoring, LeagueController leaguesController) {
        super(username, password);
        this.name = name;
        this.financialMonitoring = financialMonitoring;
        this.leagueController = leagueController;
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
}
