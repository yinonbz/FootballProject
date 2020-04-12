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
        super(username, password,systemController);
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
