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

import static dataLayer.Tables.Tables.LEAGUE;

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
        //create sql query to search record in db using ObjectName
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(LEAGUE).where(LEAGUE.LEAGUEID.eq(objectName)).fetch();
        return (!result.isEmpty());
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String objectName,String arg2,String arg3) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result <?> result = create.select().from(LEAGUE).where(LEAGUE.LEAGUEID.eq(objectName)).fetch();
        String leagueID = result.get(0).get(LEAGUE.LEAGUEID);

        Map<String,ArrayList<String>> objDetails = new HashMap<>();
        ArrayList<String> leagueIDs = new ArrayList<>();
        leagueIDs.add(leagueID);
        objDetails.put("leagueID",leagueIDs);
        return objDetails;
    }

    @Override
    public boolean removeFromDB(String objectName,String arg2,String arg3) {
        if(containInDB(objectName,null,null)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(LEAGUE)
                    .where(LEAGUE.LEAGUEID.eq(objectName)).execute();
            return true;
        }
        return false;
    }

    @Override
    public boolean addToDB(String leagueID, String empty1, String empty2, String empty3, Map<String, ArrayList<String>> objDetails) {
        return addToDb(leagueID);
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
