package dataLayer;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jooq.Record;

import static dataLayer.Tables.Tables.*;
import static dataLayer.Tables.Tables.LEAGUE;

public class EventRecordDB implements DB_Inter {
    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    //String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    String myUrl = "jdbc:mysql://132.72.65.33:3306/demodb";
    Connection connection = null;

    public EventRecordDB(){
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

    @Override
    public boolean containInDB(String objectName, String arg2, String arg3) {
        return false;
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String matchID, String arg2, String arg3) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(EVENTSRECORDER_EVENTS).where(EVENTSRECORDER_EVENTS.MATCHID.eq(Integer.parseInt(matchID))).fetch();
        HashMap<String,ArrayList<String>> details = new HashMap<>();
        int counter=0;
        for (Record r :result){
            ArrayList<String> eventDetails = new ArrayList<>();
            eventDetails.add(r.get(EVENTSRECORDER_EVENTS.MATCHID).toString());
            eventDetails.add(r.get(EVENTSRECORDER_EVENTS.TIME));
            eventDetails.add(r.get(EVENTSRECORDER_EVENTS.EVENTID).toString());
            details.put(String.valueOf(counter),eventDetails);
            counter++;
        }
        return details;
    }

    @Override
    public boolean removeFromDB(String objectName, String arg2, String arg3) {
        return false;
    }

    @Override
    public boolean addToDB(String str1, String str2, String str3, String str4, Map<String, ArrayList<String>> objDetails) {
        return false;
    }

    @Override
    public int countRecords() {
        return 0;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> e,Map<String,String> arguments) {
        if(e == MATCHENUM.ALLMATCHES){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().from(EVENTSRECORDER_EVENTS).
                    where(EVENTSRECORDER_EVENTS.MATCHID.eq(Integer.parseInt(arguments.get("matchID")))).fetch();
            ArrayList<Map<String, ArrayList<String>>> details = new ArrayList<>();
            for (Record record : result) {
                HashMap<String, ArrayList<String>> seasonDetails = new HashMap<>();
                ArrayList<String> temp = new ArrayList<>();
                int eventID = record.get(EVENTSRECORDER_EVENTS.EVENTID);
                String time = record.get(EVENTSRECORDER_EVENTS.TIME);
                String type = record.get(EVENTSRECORDER_EVENTS.TYPE);
                temp.add(String.valueOf(eventID));
                temp.add(time);
                temp.add(type);
                seasonDetails.put("matchData", temp);
                details.add(seasonDetails);
            }
            return details;
        }
        return null;
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {
        return false;
    }

    @Override
    public boolean TerminateDB() {
        return false;
    }
}
