package businessLayer.userTypes.Administration;

import businessLayer.Utilities.Complaint;
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
     * the function adds the comment to the complaint
     * @param complaint the complaint the admin wants to answer
     * @param comment the comment of the admin
     * @return the complaint
     * UC 8.3.2
     */
    public Complaint replyComplaints(Complaint complaint, String comment){
        if (complaint!=null && !comment.isEmpty()){
            complaint.setAnswered(true);
            complaint.setComment(comment);
            complaint.setHandler(this.getUsername());
            return complaint;
        }
        else{
            return null;
        }
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
