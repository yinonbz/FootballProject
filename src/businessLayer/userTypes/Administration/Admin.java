package businessLayer.userTypes.Administration;

import businessLayer.userTypes.Subscriber;

public class Admin extends Subscriber {
    private String name;

    /**
     * @param username
     * @param password
     * @param name
     */
    public Admin(String username, String password, String name) {
        super(username, password);
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

    @Override
    public Boolean editDetails() {
        return null;
    }
}
