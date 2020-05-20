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
    public boolean containInDB(String objectName) {
        return false;
    }

    public boolean eventInDB(int matchID, String time, int eventID){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(EVENTSRECORDER_EVENTS).where(EVENTSRECORDER_EVENTS.MATCHID.eq(matchID)).and(
                EVENTSRECORDER_EVENTS.TIME.eq(time).and(EVENTSRECORDER_EVENTS.EVENTID.eq(eventID))).fetch();
        return (!result.isEmpty());
    }

    @Override
    public Object selectFromDB(String objectName) {
        return null;
    }

    public HashMap <String,String> selectEventFromDB(int matchID, String time, int eventID){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result <?> result = create.select().from(EVENTSRECORDER_EVENTS).where(EVENTSRECORDER_EVENTS.MATCHID.eq(matchID)).
                and(EVENTSRECORDER_EVENTS.TIME.eq(time).and(EVENTSRECORDER_EVENTS.EVENTID.eq(eventID))).fetch();
        String type = result.get(0).get(EVENTSRECORDER_EVENTS.TYPE);
        HashMap <String,String> details = new HashMap<>();
        details.put("matchID",matchID+"");
        details.put("time",time);
        details.put("eventID",eventID+"");
        details.put("type",type);

        if(type.equals("yellowcard")){
            result = create.select().from(YELLOWCARD).where(YELLOWCARD.MATCHID.eq(matchID).and(YELLOWCARD.EVENTID.eq(eventID)
                    .and(YELLOWCARD.TIME.eq(time)))).fetch();
            details.put("player",result.get(0).get(YELLOWCARD.PLAYERAGAINSTID));
        }
        else if(type.equals("redcard")){
            result = create.select().from(REDCARD).where(REDCARD.MATCHID.eq(matchID).and(REDCARD.EVENTID.eq(eventID)
                    .and(REDCARD.TIME.eq(time)))).fetch();
            details.put("player",result.get(0).get(REDCARD.PLAYERAGAINSTID));
        }
        else if(type.equals("sub")){
            result = create.select().from(SUBSTITUTE).where(SUBSTITUTE.MATCHID.eq(matchID).and(SUBSTITUTE.EVENTID.eq(eventID)
                    .and(SUBSTITUTE.TIME.eq(time)))).fetch();
            details.put("playerON",result.get(0).get(SUBSTITUTE.PLAYERINID));
            details.put("playerOff",result.get(0).get(SUBSTITUTE.PLAYEROUTID));
        }
        else if(type.equals("injury")){
            result = create.select().from(INJURY).where(INJURY.MATCHID.eq(matchID).and(INJURY.EVENTID.eq(eventID)
                    .and(INJURY.TIME.eq(time)))).fetch();
            details.put("player",result.get(0).get(INJURY.PLAYERINJUREDID));
        }
        else if(type.equals("goal")){
            result = create.select().from(GOAL).where(GOAL.MATCHID.eq(matchID).and(GOAL.EVENTID.eq(eventID)
                    .and(GOAL.TIME.eq(time)))).fetch();
            details.put("playerG",result.get(0).get(GOAL.PLAYERGOALID));
            details.put("playerA",result.get(0).get(GOAL.PLAYERASSISTID));
            details.put("isOwnGoal",result.get(0).get(GOAL.ISOWNGOAL).toString());
        }
        else if(type.equals("offside")){
            result = create.select().from(OFFSIDE).where(OFFSIDE.MATCHID.eq(matchID).and(OFFSIDE.EVENTID.eq(eventID)
                    .and(OFFSIDE.TIME.eq(time)))).fetch();
            details.put("player",result.get(0).get(OFFSIDE.PLAYEROFFSIDEID));
        }
        return details;
    }



    @Override
    public boolean removeFromDB(String objectName) {
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



    @Override
    public boolean addToDb(String username, String password, String name, Map<String, ArrayList<String>> objDetails) {
        return false;
    }

    public boolean addEventToDB(int matchID, String time, int eventID, String type, HashMap <String,String> details){
        if(!eventInDB(matchID,time,eventID)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(EVENTSRECORDER_EVENTS,EVENTSRECORDER_EVENTS.MATCHID,EVENTSRECORDER_EVENTS.TIME,EVENTSRECORDER_EVENTS.EVENTID,
                    EVENTSRECORDER_EVENTS.TYPE).values(matchID,time,eventID,type).execute();
            if(type.equals("yellowcard")){
                create.insertInto(YELLOWCARD,YELLOWCARD.MATCHID,YELLOWCARD.TIME,YELLOWCARD.EVENTID,
                        YELLOWCARD.PLAYERAGAINSTID).values(matchID,time,eventID,details.get("player")).execute();
            }
            else if(type.equals("redcard")){
                create.insertInto(REDCARD,REDCARD.MATCHID,REDCARD.TIME,REDCARD.EVENTID,
                        REDCARD.PLAYERAGAINSTID).values(matchID,time,eventID,details.get("player")).execute();
            }
            else if(type.equals("sub")){
                create.insertInto(SUBSTITUTE,SUBSTITUTE.MATCHID,SUBSTITUTE.TIME,SUBSTITUTE.EVENTID,
                        SUBSTITUTE.PLAYERINID,SUBSTITUTE.PLAYEROUTID).values(matchID,time,eventID,details.get("playerIn"),details.get("playerOut")).execute();
            }
            else if(type.equals("injury")){
                create.insertInto(INJURY,INJURY.MATCHID,INJURY.TIME,INJURY.EVENTID,
                        INJURY.PLAYERINJUREDID).values(matchID,time,eventID,details.get("player")).execute();
            }
            else if(type.equals("goal")){
                create.insertInto(GOAL,GOAL.MATCHID,GOAL.TIME,GOAL.EVENTID,
                        GOAL.PLAYERGOALID,GOAL.PLAYERASSISTID).values(matchID,time,eventID,details.get("playerG"),details.get("playerA")).execute();
                //todo the is own goals does trouble
            }
            else if(type.equals("offside")){
                create.insertInto(OFFSIDE,OFFSIDE.MATCHID,OFFSIDE.TIME,OFFSIDE.EVENTID,
                        OFFSIDE.PLAYEROFFSIDEID).values(matchID,time,eventID,details.get("player")).execute();
            }
            return true;
        }
        return false;
    }

}
