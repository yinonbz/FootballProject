package serviceLayer;

import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.HashMap;

public class SystemService {
    private SystemController systemController; //business layer system controller.

    public SystemService(SystemController systemController){
        this.systemController = systemController;
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
     * @param userName The user name of the default temporary admin, as mentioned in the Readme file.
     * @param password The password of the default temporary admin, as mentioned in the Readme file.
     * @return true: if the temporary admin user was created successfully by the system. | false: The userName or password didn't match to the default temporary admin details.
     */
    public Boolean insertInfo(String userName, String password) {
        return insertInfo(userName,password);
    }

    /**
     * the function closes a team Permanently by the admin
     *
     * @param teamName the team the user wants to close
     * @param username the user type of the user that requested to close the team
     * @return true if the status was changed to close
     * UC 8.1
     */
    public boolean closeTeamByAdmin(String teamName, String username) {
        return closeTeamByAdmin(teamName,username);
    }



}