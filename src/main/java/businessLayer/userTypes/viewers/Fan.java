package businessLayer.userTypes.viewers;

import businessLayer.Utilities.Complaint;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.List;

public class Fan extends Subscriber {

    /**
     * @param username
     * @param password
     * @param name
     */
    public Fan(String username, String password, String name, SystemController systemController) {
        super(username, password,name, systemController);
    }

    /**
     * the function creates the complaint and adds it to the system
     * @param content the content of the complaint
     * @return the complaint, null if the content is not valid
     */
    public Complaint createComplaint(String content){
        if(content!=null) {
            if(!content.isEmpty()) {
                Complaint complaint = new Complaint(this.username, content);
                return complaint;
            }
        }
        return null;
    }

    /**
     *
     */
    public void logout(){

    }

    /**
     * @return
     */
    public Boolean follow(){
        return true;
    }

    /**
     * @return
     */
    public Boolean getNotifications(){
        return true;
    }

    /**
     *
     */
    public void openPage(){

    }

    /**
     * @param searchInput
     * @return
     */
    public List<Object> search(String searchInput){
        return null;
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


/*
    @Override
    public Boolean editDetails() {
        return null;
    }
    */
}
