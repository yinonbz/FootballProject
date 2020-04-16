package serviceLayer;

import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.LinkedList;

public class SystemService {
    private SystemController systemController; //business layer system controller.

    public SystemService(){
        this.systemController = SystemController.SystemController();
    }

    /**
     * this function gets request from admin to watch complaint from the presentation layer
     * and generate the arguments needed in the business layer to operate.
     * @param username
     * @return
     */
    public HashMap<Integer, Complaint> displayComplaints(String username) {
        return systemController.displayComplaints(username);
    }

    /**
     * this function gets the arguments inserted to the presentation layer by the user in the initialization of system
     * @param userName The user name of the default temporary admin, as mentioned in the Readme file.
     * @param password The password of the default temporary admin, as mentioned in the Readme file.
     * @return true: if the temporary admin user was created successfully by the system. | false: The userName or password didn't match to the default temporary admin details.
     */
    public Boolean insertInfo(String userName, String password) {
        return systemController.insertInfo(userName,password);

    }

    /**
     * UC 1.1 - Initialize System
     * @param password the password of the temporary admin
     * @return true if the system has initialized successfully
     *          false else
     */
    public Boolean initializeSystem(String password){
        return systemController.initializeSystem(password);
    }

    /**
     * @param newPassword The new password of the user
     * @param userName the user's user name
     * @return true if the passsword has been changed
     *          false else
     */
    public Boolean changePassword(String newPassword, String userName){
        return systemController.changePassword(newPassword, userName);
    }

    public Boolean appoinTeamOwnerToTeam(String teamName, String newUserName, String userName){
        return systemController.appoinTeamOwnerToTeam(teamName, newUserName,userName);
    }

    /**
     * the function get closing team request from presentation layer and closes a team Permanently by the admin
     *
     * @param teamName the team the user wants to close
     * @param username the user type of the user that requested to close the team
     * @return true if the status was changed to close
     * UC 8.1
     */
    public boolean closeTeamByAdmin(String teamName, String username) {
        return systemController.closeTeamByAdmin(teamName,username);
    }



    /**
     * the function lets the subscriber to upload a complaint via the presentation layer, and execute the command.
     *  @param content    the content of the complaint
     * @param username the subscriber who wants to complain
     */

    public void addComplaint(String content, String username) {
        systemController.addComplaint(content,username);
    }

    /**
     * the functiong gets from the UI a request to removes a user from the system by the admin
     *and execute it in business layer
     * @param subscriberName the name of the user we want to delete
     * @param userType       the type of the user that tries to delete
     * @return a string that explains what was the result
     * 8.2
     */
    public String removeSubscriber(String subscriberName, String userType) {
        return systemController.removeSubscriber(subscriberName,userType);
    }

    /**
     * the function gets request to reply a complaint from presentation layer,
     * and lets the admin to respond to the comments in the system
     *
     * @param complaintID the complain's id the admin wants to respond to
     * @param username  the user that wants to respond - has to be an admin
     * @param comment     - the comment of the admin
     * @return true is he responded successfully
     * UC 8.3.2
     */
    public boolean replyComplaints(int complaintID, String username, String comment) {
        return systemController.replyComplaints(complaintID,username,comment);
    }



    /**
     * The function adds a referee to the system and returns whether the referee was successfully added or not
     *
     * @param username
     * @param password
     * @param name
     * @param training
     * @param representativeUser
     * @return true/false
     */
    public boolean addReferee(String username, String password, String name, String training, String representativeUser) {
        return systemController.addReferee(username,password,name,training,representativeUser);
    }

    /**
     * The function foward the arguments of removing a referee operation, from the UI of the admin to the business layer.
     *
     * @param username
     * @return true/false
     */
    public boolean removeReferee(String username) {
        return systemController.removeReferee(username);
    }

    /**
     * the function takes a request for opening a new team and puts it in the data structure
     *
     * @param details of the new team
     */
    public boolean addToTeamConfirmList(LinkedList<String> details, String username) {
        return systemController.addToTeamConfirmList(details,username);
    }

    /**
     * the function approves the request by the AR and updates the new team in the system and in the team owner
     *
     * @param teamName   the name of the team
     * @param username the subscriber who tries to confirm the request
     * @return true if it done successfully
     */
    public boolean confirmTeamByAssociationRepresntative(String teamName, String username) {
        return systemController.confirmTeamByAssociationRepresntative(teamName,username);
    }

}