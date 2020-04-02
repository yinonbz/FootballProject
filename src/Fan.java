public class Fan extends Subscriber {
    private String name;


    /**
     * @param username
     * @param password
     * @param systemController
     * @param alertSystem
     * @param name
     */
    public Fan(String username, String password, SystemController systemController, AlertSystem alertSystem, String name) {
        super(username, password, systemController, alertSystem);
        this.name = name;
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
