package dataLayer;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.jooq.Record;

import static dataLayer.Tables.Tables.*;

public class DBMatch implements DB_Inter {

    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    //String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    String myUrl = "jdbc:mysql://localhost:3306/localsoccer";
    Connection connection = null;



    public DBMatch(){
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
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(MATCH).where(MATCH.MATCHID.eq(Integer.parseInt(objectName))).fetch();
        return (!result.isEmpty());
    }


    @Override
    public Object selectFromDB(String objectName) {
        /*
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result <?> result = create.select().from(MATCH).where(MATCH.MATCHID.eq(Integer.parseInt(objectName))).fetch();
        int match = result.get(0).get(MATCH.MATCHID);
        */
        return null;
    }
    public HashMap <String,String> selectMatchFromDB(int matchID){

        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result <?> result = create.select().from(MATCH).where(MATCH.MATCHID.eq(matchID)).fetch();
        String id = result.get(0).get(MATCH.MATCHID).toString();
        String home = result.get(0).get(MATCH.TEAMHOMEID);
        String away = result.get(0).get(MATCH.TEAMAWAYID);
        String leagueID = result.get(0).get(MATCH.LEAGUEID);
        String seasonID = result.get(0).get(MATCH.SEASONID).toString();
        String score = result.get(0).get(MATCH.SCORE);
        String numberOFFans = result.get(0).get(MATCH.NUMBEROFFANS).toString();
        String stadium = result.get(0).get(MATCH.STADIUMID);
        String date = result.get(0).get(MATCH.DATE).toString();
        String referee = result.get(0).get(MATCH.MAINREFEREEID);
        String isFinished = result.get(0).get(MATCH.ISFINISHED).toString();
        HashMap <String,String> details = new HashMap<>();
        details.put("matchID",id);
        details.put("homeTeam",home);
        details.put("awayTeam",away);
        details.put("leagueID",leagueID);
        details.put("seasonID",seasonID);
        details.put("score",score);
        details.put("numberOFFans",numberOFFans);
        details.put("stadium",stadium);
        details.put("date",date);
        details.put("refere",referee);
        details.put("isFinished",isFinished);
        return details;
    }

    public LinkedList<String> getRefsOfMatch(int matchID){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        LinkedList<String> refNames = new LinkedList<>();
        Result <?> result = create.select().from(MATCH_REFEREE).where(MATCH_REFEREE.MATCHID.eq(matchID)).fetch();
        if(result.isEmpty()){
            return null;
        }
        else{
            for(Record r : result){
                refNames.add(r.get(MATCH_REFEREE.REFEREEID));
            }
        }
        return refNames;
    }


    @Override
    public boolean removeFromDB(String objectName) {
        if(containInDB(objectName)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(MATCH).where(MATCH.MATCHID.eq(Integer.parseInt(objectName))).execute();
            create.delete(REFEREE_MATCHES).where(MATCH_REFEREE.MATCHID.eq(Integer.parseInt(objectName))).execute();
            return true;
        }
        /*
                if(containsInDB(leagueID,seasonID)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(SEASONS)
                    .where(SEASONS.LEAGUEID.eq(leagueID)).and(SEASONS.SEASONID.eq(seasonID)).execute();
            return true;
        }
        return false;
         */
        return false;
    }

    @Override
    public boolean addToDb(String username, String password, String name, Map<String, ArrayList<String>> objDetails) {
        return false;
    }

    public boolean addMatchToDB(String leagueID, int seasonID, String matchID ,String teamHome, String teamAway, String stadium, String score, Date date){
        if(!containInDB(matchID)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(MATCH,MATCH.MATCHID,MATCH.LEAGUEID,MATCH.SEASONID,MATCH.TEAMHOMEID,
                    MATCH.TEAMAWAYID,MATCH.STADIUMID,MATCH.SCORE
            ).values(Integer.parseInt(matchID),leagueID,seasonID,teamHome,teamAway,stadium,score).execute();
            create.insertInto(EVENTRECORDER,EVENTRECORDER.MATCHID).values(Integer.parseInt(matchID)).execute();
            //create.insertInto(MATCH,MATCH.DATE).values(date).execute();
            return true;
        }
        return false;
    }

    public boolean updateScore (String matchID, String score){
        if(containInDB(matchID)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.update(MATCH).set(MATCH.SCORE,score).where(MATCH.MATCHID.eq(Integer.parseInt(matchID))).execute();
            return true;
        }
        return false;
    }

    public boolean updateMainRefereeToMatch(String matchID,String refID){
        if(containInDB(matchID)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.update(MATCH).set(MATCH.MAINREFEREEID,refID).where(MATCH.MATCHID.eq(Integer.parseInt(matchID))).execute();
            return true;
        }
        return false;
    }

    public boolean updateNumOfFans(String matchID,int numOfFans){
        if(containInDB(matchID)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.update(MATCH).set(MATCH.NUMBEROFFANS,numOfFans).where(MATCH.MATCHID.eq(Integer.parseInt(matchID))).execute();
            return true;
        }
        return false;
    }



    public boolean addRefereeToMatch(String matchID, String refID) {
        if (containInDB(matchID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(MATCH_REFEREE,MATCH_REFEREE.MATCHID,MATCH_REFEREE.REFEREEID)
                    .values(Integer.parseInt(matchID),refID).execute();
            return true;
        }
        return false;
    }


}
