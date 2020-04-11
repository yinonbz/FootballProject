package businessLayer.userTypes.Administration;
import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Subscriber;
import serviceLayer.SystemController;

import java.util.HashMap;

public class Admin extends Subscriber {
    private String name;
    private SystemController systemController;

    /**
     * @param username
     * @param password
     * @param name
     */
    public Admin(String username, String password, String name, SystemController systemController) {
        super(username, password);
        this.name = name;
        this.systemController=systemController;
    }

    /**
     * displays the complaints in the system
     * @return a hash map of the complaints
     */
    public HashMap<Integer, Complaint> displayComplaints(){
        return systemController.displayComplaints(this);
    }

    /**
     * the public function that the admin can reply on comments
     * @param complaintID the complaint ID the admin is choosing
     * @param subscriber the subscriber type
     * @param comment the comment of the admin
     * @return true if the complaint was added
     * UC 8.3.2
     */
    public boolean replyComplaints(int complaintID,Subscriber subscriber, String comment){
        return systemController.replyComplaints(complaintID, subscriber, comment);
    }

    /**
     * the function adds the comment to the complaint
     * @param complaint the complaint the admin wants to answer
     * @param comment the comment of the admin
     * @return the complaint
     * UC 8.3.2
     */
    /*
    private Complaint replyComplaints(Complaint complaint, String comment){
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
    */

    /**
     * the function delete a user from the system
     * @param subscriberUserName the user name we want to delete
     * @return the string of the result
     */
    public String deleteSubscriber(String subscriberUserName){
        return systemController.removeSubscriber(subscriberUserName, this);
    }

    /**
     * the function closes teams permanently
     * @param teamName the team the admin wants to close
     * @return true if it done successfully
     */
    public Boolean closeTeam(String teamName){
        return systemController.closeTeamByAdmin(teamName, this);
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
