package dataLayer;

import dataLayer.Tables.tables.Notifications;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.swing.text.StyledEditorKit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dataLayer.Tables.tables.MatchFollowers.MATCH_FOLLOWERS;
import static dataLayer.Tables.tables.Notifications.NOTIFICATIONS;
import static dataLayer.Tables.tables.PageFollowers.PAGE_FOLLOWERS;

public class notificationDB implements DB_Inter {
    String username;
    String password;
    String myDriver;
    String myUrl;
    Connection connection = null;

    public notificationDB(){
        this("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://132.72.65.33:3306/demodb");
    }

    public notificationDB(String username,String password, String myDriver, String myUrl){
        this.username = username;
        this.password = password;
        this.myDriver = myDriver;
        this.myUrl = myUrl;

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


    //check if user has notifications in DB
    @Override
    public boolean containInDB(String objectName, String arg2, String arg3) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(NOTIFICATIONS)
                .where(NOTIFICATIONS.SUBSCRIBERID.eq(objectName)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String objectName, String arg2, String arg3) {

        System.out.println("can't select specific notification");
        return null;

    }

    @Override
    public boolean removeFromDB(String objectName, String arg2, String arg3) {
        return false;
    }

    @Override
    public boolean addToDB(String subscriberID, String notification, String seen, String str4, Map<String, ArrayList<String>> objDetails) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        try{
            create.insertInto(
                    NOTIFICATIONS,
                    NOTIFICATIONS.SUBSCRIBERID,
                    NOTIFICATIONS.NOTIFICATION,
                    NOTIFICATIONS.SEEN)
                    .values(subscriberID,notification,Boolean.valueOf(seen))
                    .execute();

            return true;
        }catch (Exception e){
            System.out.println("cannot add notification to DB");
            return false;
        }
    }

    @Override
    public int countRecords() {
        return 0;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> e, Map<String, String> arguments) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(e==NOTIFICATIONENUMS.GETUSERNOTIFICATIONS){
            ArrayList<Map<String, ArrayList<String>>> allNotifications = new ArrayList<>();
            try{
                Result<?> result = create.select().
                        from(NOTIFICATIONS)
                        .where(NOTIFICATIONS.SUBSCRIBERID.eq(arguments.get("SubscriberID")))
                        .and(NOTIFICATIONS.SEEN.eq(Boolean.valueOf("false")))
                        .fetch();
                allNotifications.add(new HashMap<>());
                allNotifications.get(0).put("notifications",new ArrayList<>());
                for(Record r: result){
                    allNotifications.get(0).get("notifications").add(r.get(NOTIFICATIONS.NOTIFICATION));

                }
                return allNotifications;
            }catch (Exception exception){
                System.out.println("cannot get notifications of user from DB");
                return allNotifications;
            }
        }
        if(e==NOTIFICATIONENUMS.GETMATCHFOLLOWERS){
            ArrayList<Map<String, ArrayList<String>>> matchFollowers = new ArrayList<>();
            try{
                Result<?> result = create.select().
                        from(MATCH_FOLLOWERS)
                        .where(MATCH_FOLLOWERS.MATCHID.eq(Integer.parseInt(arguments.get("matchID"))))
                        .fetch();
                matchFollowers.add(new HashMap<>());
                matchFollowers.get(0).put("followers",new ArrayList<>());
                for(Record r: result){
                    matchFollowers.get(0).get("followers").add(r.get(MATCH_FOLLOWERS.FOLLOWERID));
                }
                return matchFollowers;
            }catch (Exception exception){
                System.out.println("cannot get match followers from DB");
                return matchFollowers;
            }
        }
        if(e==NOTIFICATIONENUMS.GETPAGEFOLLOWES){
            ArrayList<Map<String, ArrayList<String>>> pageFollowers = new ArrayList<>();
            try{
                Result<?> result = create.select().
                        from(PAGE_FOLLOWERS)
                        .where(PAGE_FOLLOWERS.PAGEID.eq(Integer.parseInt(arguments.get("pageID"))))
                        .fetch();
                pageFollowers.add(new HashMap<>());
                pageFollowers.get(0).put("followers",new ArrayList<>());
                for(Record r: result){
                    pageFollowers.get(0).get("followers").add(r.get(PAGE_FOLLOWERS.FOLLOWERID));
                }
                return pageFollowers;
            }catch (Exception exception){
                System.out.println("cannot get match followers from DB");
                return pageFollowers;
            }
        }
        System.out.println("invalid notification operation");
        return null;
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(e==NOTIFICATIONUPDATES.ADDMATCHFOLLOWER){
            create.insertInto(MATCH_FOLLOWERS,
                    MATCH_FOLLOWERS.MATCHID,
                    MATCH_FOLLOWERS.FOLLOWERID)
                    .values(Integer.parseInt(arguments.get("matchID")),
                            arguments.get("followerID")).execute();
        }
        if(e==NOTIFICATIONUPDATES.ADDPAGEFOLLOWER){
            create.insertInto(PAGE_FOLLOWERS,
                    PAGE_FOLLOWERS.PAGEID,
                    PAGE_FOLLOWERS.FOLLOWERID)
                    .values(Integer.parseInt(arguments.get("pageID")),
                            arguments.get("followerID")).execute();
        }
        return false;
    }

    @Override
    public boolean TerminateDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }
}
