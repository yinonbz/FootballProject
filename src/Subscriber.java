public abstract class Subscriber {
    private String username;
    private String password;

    /**
     * @param username
     * @param password
     */
    public Subscriber(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
     * @return
     */
    private Boolean editDetails(){
        return true;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}