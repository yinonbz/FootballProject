package dataLayer;

import dataLayer.Tables.tables.*;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.*;

import java.sql.*;

import static dataLayer.Tables.tables.Subscribers.SUBSCRIBERS;

public class DBHandler implements DB_Inter{

    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    Connection connection = null;



    public DBHandler(){
        //connect to DB and save to field in class.
        try {
            Class.forName(myDriver);
            connection = DriverManager.getConnection(myUrl,username,password);
            System.out.println("Successful connection to server db ");
        } catch (SQLException e) {
            System.out.println("error connecting to server. connection is now null");
        } catch (ClassNotFoundException e) {
            System.out.println("error connecting to driver");
        }
    }

    /**
     * function to search in db for subscribers
     * @param objectName
     * @return
     */

    @Override
    public boolean containInDB(String objectName) {
        //create sql query to search record in db using ObjectName
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(SUBSCRIBERS)
                .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;

    }

    /**
     * demo function to get a subscriber object from data base
     * @param objectName
     * @return
     */

    @Override
    public Object selectFromDB(String objectName) {
        //create sql query to select record from db using ObjectName

        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(SUBSCRIBERS)
                .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName))
                .fetch();
        //String type= result.get(0).get()
        for(Record r: result){
            //return r
        }
        return true;

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
    public boolean removeFromDB(String objectName, String table,String where,String groupby) {
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

    public boolean TerminateDB(String objectName) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }
}
