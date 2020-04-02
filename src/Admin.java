public class Admin extends Subscriber {
    private String name;

    /**
     * @param username
     * @param password
     * @param systemController
     * @param alertSystem
     * @param name
     */
    public Admin(String username, String password, SystemController systemController, AlertSystem alertSystem, String name) {
        super(username, password, systemController, alertSystem);
        this.name = name;
    }

    /**
     * @param subscriberId
     * @return
     */
    public Boolean deleteSubscriber(String subscriberId){
        return true;
    }

    /**
     * @param teamId
     * @return
     */
    public  Boolean closeTeam(int teamId){
        return true;
    }

    /**
     *
     */
    public void observeLogs(){

    }

    /**
     * @return
     */
    public Boolean executeRecommendation(){
        return true;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
