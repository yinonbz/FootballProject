package dataLayer;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static dataLayer.Tables.Tables.LEAGUE;
import static dataLayer.Tables.Tables.MATCH_REFEREE;

public class DBLeagues implements DB_Inter{

    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    //String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    String myUrl = "jdbc:mysql://132.72.65.33:3306/demodb";
    Connection connection = null;



    public DBLeagues(){
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
        try {
            //create sql query to search record in db using ObjectName
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().from(LEAGUE).where(LEAGUE.LEAGUEID.eq(objectName)).fetch();
            return (!result.isEmpty());
        } catch (DataAccessException e) {
            System.out.println("error searching league in DB");
            return false;
        }
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String objectName,String arg2,String arg3) {
        try {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result <?> result = create.select().from(LEAGUE).where(LEAGUE.LEAGUEID.eq(objectName)).fetch();
            String leagueID = result.get(0).get(LEAGUE.LEAGUEID);

            Map<String,ArrayList<String>> objDetails = new HashMap<>();
            ArrayList<String> leagueIDs = new ArrayList<>();
            leagueIDs.add(leagueID);
            objDetails.put("leagueID",leagueIDs);
            return objDetails;
        } catch (DataAccessException e) {
            System.out.println("error getting league from DB");
            return new HashMap<>();
        } catch (IllegalArgumentException e) {
            System.out.println("error getting league from DB");
            return new HashMap<>();
        }
    }

    @Override
    public boolean removeFromDB(String objectName,String arg2,String arg3) {
        try {
            if(containInDB(objectName,null,null)) {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                create.delete(LEAGUE)
                        .where(LEAGUE.LEAGUEID.eq(objectName)).execute();
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            System.out.println("error removing league from DB");
            return false;
        }
    }

    @Override
    public boolean addToDB(String leagueID, String empty1, String empty2, String empty3, Map<String, ArrayList<String>> objDetails) {
        try {
            return addToDb(leagueID);
        } catch (Exception e) {
            System.out.println("error adding league to DB");
            return false;
        }
    }

    @Override
    public int countRecords() {
        return 0;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> e,Map<String,String> arguments) {
        if(e==SEASONENUM.ALLLEAGUES){
            try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select(LEAGUE.LEAGUEID).from(LEAGUE).fetch();
                ArrayList<Map<String, ArrayList<String>>> details = new ArrayList<>();
                for (Record record : result) {
                    HashMap<String, ArrayList<String>> seasonDetails = new HashMap<>();
                    ArrayList<String> temp = new ArrayList<>();
                    String leagueID = record.get(LEAGUE.LEAGUEID);
                    temp.add(leagueID);
                    seasonDetails.put(leagueID, temp);
                    details.add(seasonDetails);
                }
                return details;
            } catch (DataAccessException e1) {
                System.out.println("error retriving all leagues");
                return new ArrayList<>();
            } catch (IllegalArgumentException e1) {
                System.out.println("error retriving all leagues");
                return new ArrayList<>();
            }
        }
        return null;
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {
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


    public boolean addToDb(String leagueID){
        if(!containInDB(leagueID,null,null)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(LEAGUE,LEAGUE.LEAGUEID).values(leagueID).execute();
            return true;
        }
        return false;
    }
}
