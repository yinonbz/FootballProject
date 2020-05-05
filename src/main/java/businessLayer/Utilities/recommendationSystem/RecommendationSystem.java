package businessLayer.Utilities.recommendationSystem;

import businessLayer.userTypes.SystemController;

import java.util.List;

public class RecommendationSystem {

    private SystemController systemController;

    public RecommendationSystem(SystemController systemController) {

    }

    /** return list of recommended content
     *
     * @return
     */
    public List<String> recommend(){

        return null;
    }

    /**
     *
     * @return
     */

    public SystemController getSystemController() {
        return systemController;
    }

    /**
     *
     * @param systemController
     */
    public void setSystemController(SystemController systemController) {
        this.systemController = systemController;
    }
}
