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
        Subscriber subscriber = systemController.getSubscriberByUserName(username); // find subscriber who request the team opening
        if(subscriber == null){
            // user not found. return null to presentation layer and print error there.
            return null;
        }
        return systemController.displayComplaints(subscriber);
    }





    }
