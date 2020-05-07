package dataLayer;

import businessLayer.userTypes.Subscriber;
import com.sun.corba.se.spi.monitoring.StatisticMonitoredAttribute;

import java.sql.*;

public class DBHandler implements DB_Inter{

    String myDriver = "";
    String myUrl = "";
    Connection connection = null;



    public DBHandler(){
        //connect to DB and save to field in class.
        try {
            connection = DriverManager.getConnection(myUrl,"root","");
        } catch (SQLException e) {
            System.out.println("error connecting to server. connection is now null");
        }
    }

    /**
     * function to search in db for subscribers
     * @param objectName
     * @return
     */

    @Override
    public boolean containInDB(String objectName,String table,String where,String groupby) {
        //create sql query to search record in db using ObjectName
        String query = "Select" +objectName +"from" +table;

        if(where !=null || where !=""){
            query = query +where;
        }
        if(groupby!=null || groupby!=""){
            query = query +"GROUPBY"+ groupby;
        }
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println("error creting statement. statement is now null");
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("error executing query. result set in now null");
        }

        try{
            while (rs.next()){

                // available functions
                /*int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date dateCreated = rs.getDate("date_created");
                boolean isAdmin = rs.getBoolean("is_admin");
                int numPoints = rs.getInt("num_points");*/

                //found user
             if(rs.getString("userName").equalsIgnoreCase(objectName)){
                 st.close();
                 return true;
             }
            }
            st.close();
        } catch (Exception e){
            System.out.println("error iterating result set.");
        }
        //user not found
        return false;
    }

    /**
     * demo function to get a subscriber object from data base
     * @param objectName
     * @return
     */

    @Override
    public Object selectFromDB(String objectName, String table,String where,String groupby) {
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
    public boolean removeFromDB(String objectName, String tableת,String where,String groupby) {
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
    public boolean addToDb(String name, Object obj, String table,String where,String groupby) {
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
