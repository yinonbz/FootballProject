public class Admin extends Subscriber {
    private String name;

    /**
     * @param name
     */
    public Admin(String name,String username, String password) {
        super(username,password);
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
