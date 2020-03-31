import java.util.Map;

public class AssociationRepresentative extends Subscriber{
    private String name;
    private FinancialMonitoring financialMonitoring;

    /**
     * @param username
     * @param password
     * @param name
     * @param financialMonitoring
     */
    public AssociationRepresentative(String username, String password, String name, FinancialMonitoring financialMonitoring) {
        super(username, password);
        this.name = name;
        this.financialMonitoring = financialMonitoring;
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
}
