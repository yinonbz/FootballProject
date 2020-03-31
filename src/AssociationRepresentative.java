import java.util.Map;

public class AssociationRepresentative extends Subscriber{
    private String name;
    private FinancialMonitoring financialMonitoring;
    private LeaguesController leaguesController;

    /**
     * @param username
     * @param password
     * @param name
     * @param financialMonitoring
     * @param leaguesController
     */
    public AssociationRepresentative(String username, String password, String name, FinancialMonitoring financialMonitoring, LeaguesController leaguesController) {
        super(username, password);
        this.name = name;
        this.financialMonitoring = financialMonitoring;
        this.leaguesController = leaguesController;
    }

    /**
     * @param leagueInfo
     * @return
     */
    public Boolean createLeague(String leagueInfo){
        return true;
    }

    /**
     * @param s1
     * @param s2
     * @return
     */
    public Boolean createSeason(String s1, String s2){
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
}
