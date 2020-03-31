public class Player extends Subscriber{
    private String name;
    private String birthDate;
    private String fieldJob;

    /**
     * @param username
     * @param password
     * @param name
     * @param birthDate
     * @param fieldJob
     */
    public Player(String username, String password, String name, String birthDate, String fieldJob) {
        super(username, password);
        this.name = name;
        this.birthDate = birthDate;
        this.fieldJob = fieldJob;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @param fieldJob
     */
    public void setFieldJob(String fieldJob) {
        this.fieldJob = fieldJob;
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
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @return
     */
    public String getFieldJob() {
        return fieldJob;
    }
}
