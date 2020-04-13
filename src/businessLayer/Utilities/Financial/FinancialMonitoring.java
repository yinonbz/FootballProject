package businessLayer.Utilities.Financial;

import businessLayer.Team.Team;
import businessLayer.userTypes.Administration.AssociationRepresentative;

import java.util.List;

public class FinancialMonitoring {

    private String rules;
    private List<AssociationRepresentative> associationReps;
    private List<Team> teams;

    /**
     *
     * @param rules
     */
    public FinancialMonitoring(String rules) {
        this.rules = rules;
    }

    /**
     *
     * @return
     */

    public String getRules() {
        return rules;
    }

    /**
     *
     * @param rules
     */

    public void setRules(String rules) {
        this.rules = rules;
    }

    /**
     *
     * @return
     */

    public List<AssociationRepresentative> getAssociationReps() {
        return associationReps;
    }

    /**
     *
     * @param associationReps
     */

    public void setAssociationReps(List<AssociationRepresentative> associationReps) {
        this.associationReps = associationReps;
    }

    /**
     *
     * @return
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}

