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
    String myUrl = "jdbc:mysql://132.72.65.33:3306/demodb";
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
    public boolean containInDB(String objectName,String empty1,String empty2) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(MATCH).where(MATCH.MATCHID.eq(Integer.parseInt(objectName))).fetch();
        return (!result.isEmpty());
    }


    @Override
    public Map<String, ArrayList<String>> selectFromDB(String matchID,String arg2,String arg3) {
        return selectMatchFromDB(Integer.parseInt(matchID));
    }
    private HashMap <String,ArrayList<String>> selectMatchFromDB(int matchID){
        if(containInDB(String.valueOf(matchID),null,null)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().from(MATCH).where(MATCH.MATCHID.eq(matchID)).fetch();
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
            ArrayList<String> allReferees = getRefsOfMatch(matchID);

            HashMap<String, ArrayList<String>> details = new HashMap<>();

            ArrayList<String> matchIDs = new ArrayList<>();
            matchIDs.add(id);
            ArrayList<String> homeTeam = new ArrayList<>();
            homeTeam.add(home);
            ArrayList<String> awayTeam = new ArrayList<>();
            awayTeam.add(away);
            ArrayList<String> leagueIDS = new ArrayList<>();
            leagueIDS.add(leagueID);
            ArrayList<String> seasonIDs = new ArrayList<>();
            seasonIDs.add(seasonID);
            ArrayList<String> scores = new ArrayList<>();
            scores.add(score);
            ArrayList<String> numberOFFansA = new ArrayList<>();
            numberOFFansA.add(numberOFFans);
            ArrayList<String> stadiumA = new ArrayList<>();
            stadiumA.add(stadium);
            ArrayList<String> dateA = new ArrayList<>();
            dateA.add(date);
            ArrayList<String> referees = new ArrayList<>();
            referees.add(referee);
            ArrayList<String> isFinishedA = new ArrayList<>();
            isFinishedA.add(isFinished);

            details.put("matchID", matchIDs);
            details.put("homeTeam", homeTeam);
            details.put("awayTeam", awayTeam);
            details.put("leagueID", leagueIDS);
            details.put("seasonID", seasonIDs);
            details.put("score", scores);
            details.put("numberOFFans", numberOFFansA);
            details.put("stadium", stadiumA);
            details.put("date", dateA);
            details.put("mainRef", referees);
            details.put("isFinished", isFinishedA);
            details.put("allRefs",allReferees);

            return details;
        }
        return null;
    }

    public ArrayList<String> getRefsOfMatch(int matchID){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        ArrayList<String> refNames = new ArrayList<>();
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
    public boolean removeFromDB(String objectName,String arg2,String arg3) {
        if(containInDB(objectName,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(MATCH).where(MATCH.MATCHID.eq(Integer.parseInt(objectName))).execute();
            create.delete(REFEREE_MATCHES).where(MATCH_REFEREE.MATCHID.eq(Integer.parseInt(objectName))).execute();
            return true;
        }
        return false;
    }

    @Override
    public boolean addToDB(String leagueID, String seasonID, String matchID, String stadium, Map<String, ArrayList<String>> objDetails) {
        return addMatchToDB(leagueID,Integer.parseInt(seasonID),matchID,stadium,
                objDetails.get("teamHome").get(0),objDetails.get("teamAway").get(0)
                ,objDetails.get("score").get(0),convertToDate(objDetails.get("date").get(0)));
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
    public boolean update(Enum<?> e,Map<String,String> args) {
        if(e == MATCHENUM.ADDREFEREE){
            addRefereeToMatch(args.get("matchID"),args.get("refID"));
            return true;
        }
        else if(e==MATCHENUM.SCORE){
            updateScore(args.get("matchID"),args.get("score"));
            return true;
        }
        else if(e==MATCHENUM.MAINREFEREE){
            updateMainRefereeToMatch(args.get("matchID"),args.get("refID"));
            return true;
        }
        else if(e==MATCHENUM.NUMBEROFFANS){
            updateNumOfFans(args.get("matchID"),Integer.parseInt(args.get("numOfFans")));
            return true;
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

    public boolean addMatchToDB(String leagueID, int seasonID, String matchID , String stadium,
                                String teamHome, String teamAway, String score, LocalDate date){
        if(!containInDB(matchID,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(MATCH,MATCH.MATCHID,MATCH.LEAGUEID,MATCH.SEASONID,MATCH.TEAMHOMEID,
                    MATCH.TEAMAWAYID,MATCH.STADIUMID,MATCH.SCORE).values(Integer.parseInt(matchID),leagueID,seasonID,teamHome,teamAway,stadium,score).execute();
            create.insertInto(EVENTRECORDER,EVENTRECORDER.MATCHID).values(Integer.parseInt(matchID)).execute();
            //create.insertInto(MATCH,MATCH.DATE).values(date).execute();
            return true;
        }
        return false;
    }

    public boolean updateScore (String matchID, String score){
        if(containInDB(matchID,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.update(MATCH).set(MATCH.SCORE,score).where(MATCH.MATCHID.eq(Integer.parseInt(matchID))).execute();
            return true;
        }
        return false;
    }

    public boolean updateMainRefereeToMatch(String matchID,String refID){
        if(containInDB(matchID,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.update(MATCH).set(MATCH.MAINREFEREEID,refID).where(MATCH.MATCHID.eq(Integer.parseInt(matchID))).execute();
            return true;
        }
        return false;
    }

    public boolean updateNumOfFans(String matchID,int numOfFans){
        if(containInDB(matchID,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.update(MATCH).set(MATCH.NUMBEROFFANS,numOfFans).where(MATCH.MATCHID.eq(Integer.parseInt(matchID))).execute();
            return true;
        }
        return false;
    }



    public boolean addRefereeToMatch(String matchID, String refID) {
        if (containInDB(matchID,null,null)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(MATCH_REFEREE,MATCH_REFEREE.MATCHID,MATCH_REFEREE.REFEREEID)
                    .values(Integer.parseInt(matchID),refID).execute();
            return true;
        }
        return false;
    }

    private LocalDate convertToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }


}
