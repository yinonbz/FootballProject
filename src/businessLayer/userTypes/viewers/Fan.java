package businessLayer.userTypes.viewers;

import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.util.List;

public class Fan extends Subscriber {
    private String name;


    /**
     * @param username
     * @param password
     * @param name
     */
    public Fan(String username, String password, String name, SystemController systemController) {
        super(username, password,name, systemController);
        this.name = name;
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



    @Override
    public Boolean editDetails() {
        return null;
    }
}
