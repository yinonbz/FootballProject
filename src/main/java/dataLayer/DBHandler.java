package dataLayer;

import businessLayer.userTypes.Subscriber;

public class DBHandler implements DB_Inter{


    public DBHandler(){
        //connect to DB and save to field in class.
    }

    /**
     * function to search in db for subscribers
     * @param objectName
     * @return
     */

    @Override
    public boolean containInDB(String objectName,String table) {
        //create sql query to search record in db using ObjectName
        //return the result
        return false;
    }

    /**
     * demo function to get a subscriber object from data base
     * @param objectName
     * @return
     */

    @Override
    public Object selectFromDB(String objectName, String table) {
        //create sql query to select record from db using ObjectName
        //saving string results from query,
        //creating new Subscriber with the details, and returning the subscriber.
        // if more details of subscriber needed, check for subscriber type and use its constructor.
        return null;
    }

    /**
     * demo function to remove a subscriber from DB
     * @param objectName
     * @return
     */
    @Override
    public boolean removeFromDB(String objectName, String table) {
        //create sql query to find record in db using ObjectName and removing it
        //return the boolean result
        return false;
    }

    /**
     * demo function to add a subscriber to DB
     * @param name subscriber name
     * @param obj the subscriber
     * @return
     */
    @Override
    public boolean addToDb(String name, Object obj, String table) {
        if(obj instanceof Subscriber){
            Subscriber sub = (Subscriber) obj;
            String username = sub.getUsername();
            String password = sub.getPassword();
            String SubName = sub.getName();
            //add record to DB using the above strings,
            // if more details needed check subscriber type and get the
            // remaining details before adding.
        }
        return false;
    }
}
