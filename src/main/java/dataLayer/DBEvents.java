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

import static dataLayer.Tables.Tables.*;

public class DBEvents implements DB_Inter {


    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    //String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    String myUrl = "jdbc:mysql://localhost:3306/localsoccer";
    Connection connection = null;



    public DBEvents(){
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
    public boolean containInDB(String objectName,String time,String eventID) {
        return eventInDB(Integer.parseInt(objectName),time,Integer.parseInt(eventID));
    }

    public boolean eventInDB(int matchID, String time, int eventID){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(EVENTSRECORDER_EVENTS).where(EVENTSRECORDER_EVENTS.MATCHID.eq(matchID)).and(
                EVENTSRECORDER_EVENTS.TIME.eq(time).and(EVENTSRECORDER_EVENTS.EVENTID.eq(eventID))).fetch();
        return (!result.isEmpty());
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String eventID,String matchID,String time) {
        return selectEventFromDB(Integer.parseInt(matchID),time,Integer.parseInt(eventID));
    }

    public HashMap <String,ArrayList<String>> selectEventFromDB(int matchID, String time, int eventID){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result <?> result = create.select().from(EVENTSRECORDER_EVENTS).where(EVENTSRECORDER_EVENTS.MATCHID.eq(matchID)).
                and(EVENTSRECORDER_EVENTS.TIME.eq(time).and(EVENTSRECORDER_EVENTS.EVENTID.eq(eventID))).fetch();
        String type = result.get(0).get(EVENTSRECORDER_EVENTS.TYPE);
        HashMap <String,ArrayList<String>> details = new HashMap<>();
        ArrayList<String> typeA = new ArrayList<>();
        typeA.add(type);
        ArrayList<String> matchIDA = new ArrayList<>();
        matchIDA.add(String.valueOf(matchID));
        ArrayList<String> timeA = new ArrayList<>();
        timeA.add(time);
        ArrayList<String> eventIDA = new ArrayList<>();
        eventIDA.add(String.valueOf(eventID));

        details.put("matchID",matchIDA);
        details.put("time",timeA);
        details.put("eventID",eventIDA);
        details.put("type",typeA);

        if(type.equals("yellowcard")){
            result = create.select().from(YELLOWCARD).where(YELLOWCARD.MATCHID.eq(matchID).and(YELLOWCARD.EVENTID.eq(eventID)
                    .and(YELLOWCARD.TIME.eq(time)))).fetch();
            ArrayList<String> player = new ArrayList<>();
            player.add(result.get(0).get(YELLOWCARD.PLAYERAGAINSTID));
            details.put("player",player);
        }
        else if(type.equals("redcard")){
            result = create.select().from(REDCARD).where(REDCARD.MATCHID.eq(matchID).and(REDCARD.EVENTID.eq(eventID)
                    .and(REDCARD.TIME.eq(time)))).fetch();
            ArrayList<String> player = new ArrayList<>();
            player.add(result.get(0).get(REDCARD.PLAYERAGAINSTID));
            details.put("player",player);
        }
        else if(type.equals("sub")){
            result = create.select().from(SUBSTITUTE).where(SUBSTITUTE.MATCHID.eq(matchID).and(SUBSTITUTE.EVENTID.eq(eventID)
                    .and(SUBSTITUTE.TIME.eq(time)))).fetch();
            ArrayList<String> playerON = new ArrayList<>();
            playerON.add(result.get(0).get(SUBSTITUTE.PLAYERINID));
            ArrayList<String> playerOff = new ArrayList<>();
            playerOff.add(result.get(0).get(SUBSTITUTE.PLAYEROUTID));
            details.put("playerON",playerON);
            details.put("playerOff",playerOff);
        }
        else if(type.equals("injury")){
            result = create.select().from(INJURY).where(INJURY.MATCHID.eq(matchID).and(INJURY.EVENTID.eq(eventID)
                    .and(INJURY.TIME.eq(time)))).fetch();
            ArrayList<String> player = new ArrayList<>();
            player.add(result.get(0).get(INJURY.PLAYERINJUREDID));
            details.put("player",player);
        }
        else if(type.equals("goal")){
            result = create.select().from(GOAL).where(GOAL.MATCHID.eq(matchID).and(GOAL.EVENTID.eq(eventID)
                    .and(GOAL.TIME.eq(time)))).fetch();
            ArrayList<String> playerG = new ArrayList<>();
            playerG.add(result.get(0).get(GOAL.PLAYERGOALID));
            ArrayList<String> playerA = new ArrayList<>();
            playerA.add(result.get(0).get(GOAL.PLAYERASSISTID));
            ArrayList<String> isOwnGoal = new ArrayList<>();
            isOwnGoal.add(String.valueOf(result.get(0).get(GOAL.ISOWNGOAL)));
            details.put("playerG",playerG);
            details.put("playerA",playerA);
            details.put("isOwnGoal",isOwnGoal);
        }
        else if(type.equals("offside")){
            result = create.select().from(OFFSIDE).where(OFFSIDE.MATCHID.eq(matchID).and(OFFSIDE.EVENTID.eq(eventID)
                    .and(OFFSIDE.TIME.eq(time)))).fetch();
            ArrayList<String> player = new ArrayList<>();
            player.add(result.get(0).get(GOAL.PLAYERGOALID));
            details.put("player",player);
        }
        return details;
    }



    @Override
    public boolean removeFromDB(String matchID, String time,String eventID) {
        return removeEventFromDB(Integer.parseInt(matchID),time,Integer.parseInt(eventID));
    }

    @Override
    public boolean addToDB(String matchID, String time, String eventID, String type, Map<String, ArrayList<String>> objDetails) {
        return addEventToDB(Integer.parseInt(matchID),time,Integer.parseInt(eventID),type,objDetails);
    }

    @Override
    public int countRecords() {
        return 0;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> e) {
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

    public boolean removeEventFromDB(int matchID, String time, int eventID){
        if(eventInDB(matchID,time,eventID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(EVENTSRECORDER_EVENTS).where(EVENTSRECORDER_EVENTS.MATCHID.eq(matchID)).
                    and(EVENTSRECORDER_EVENTS.TIME.eq(time).and(EVENTSRECORDER_EVENTS.EVENTID.eq(eventID))).execute();
            return true;
        }
        return false;
    }

    public boolean addEventToDB(int matchID, String time, int eventID, String type, Map <String,ArrayList<String>> details){
        if(!eventInDB(matchID,time,eventID)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(EVENTSRECORDER_EVENTS,EVENTSRECORDER_EVENTS.MATCHID,EVENTSRECORDER_EVENTS.TIME,EVENTSRECORDER_EVENTS.EVENTID,
                    EVENTSRECORDER_EVENTS.TYPE).values(matchID,time,eventID,type).execute();
            if(type.equals("yellowcard")){
                create.insertInto(YELLOWCARD,YELLOWCARD.MATCHID,YELLOWCARD.TIME,YELLOWCARD.EVENTID,
                        YELLOWCARD.PLAYERAGAINSTID).values(matchID,time,eventID,details.get("player").get(0)).execute();
            }
            else if(type.equals("redcard")){
                create.insertInto(REDCARD,REDCARD.MATCHID,REDCARD.TIME,REDCARD.EVENTID,
                        REDCARD.PLAYERAGAINSTID).values(matchID,time,eventID,details.get("player").get(0)).execute();
            }
            else if(type.equals("sub")){
                create.insertInto(SUBSTITUTE,SUBSTITUTE.MATCHID,SUBSTITUTE.TIME,SUBSTITUTE.EVENTID,
                        SUBSTITUTE.PLAYERINID,SUBSTITUTE.PLAYEROUTID).values(matchID,time,eventID,details.get("playerIn").get(0),details.get("playerOut").get(0)).execute();
            }
            else if(type.equals("injury")){
                create.insertInto(INJURY,INJURY.MATCHID,INJURY.TIME,INJURY.EVENTID,
                        INJURY.PLAYERINJUREDID).values(matchID,time,eventID,details.get("player").get(0)).execute();
            }
            else if(type.equals("goal")){
                create.insertInto(GOAL,GOAL.MATCHID,GOAL.TIME,GOAL.EVENTID,
                        GOAL.PLAYERGOALID,GOAL.PLAYERASSISTID).values(matchID,time,eventID,details.get("playerG").get(0),details.get("playerA").get(0)).execute();
                //todo the is own goals does trouble
            }
            else if(type.equals("offside")){
                create.insertInto(OFFSIDE,OFFSIDE.MATCHID,OFFSIDE.TIME,OFFSIDE.EVENTID,
                        OFFSIDE.PLAYEROFFSIDEID).values(matchID,time,eventID,details.get("player").get(0)).execute();
            }
            return true;
        }
        return false;
    }

}
