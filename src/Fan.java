public class Fan extends Subscriber {
    private String name;


    /**
     * @param username
     * @param password
     * @param name
     */
    public Fan(String username, String password, String name) {
        super(username, password);
        this.name = name;
    }

    /**
     *
     */
    public void logout(){

    }

    public Boolean follow(){
        return true;
    }

    public Boolean getNotifications(){
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
