package dataLayer;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import static dataLayer.Tables.Tables.LEAGUE;
import static dataLayer.Tables.Tables.PAGE_POST;

public class DBLeagues implements DB_Inter{

    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    //String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    String myUrl = "jdbc:mysql://localhost:3306/localsoccer";
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
    public boolean containInDB(String objectName) {
        //create sql query to search record in db using ObjectName
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(LEAGUE).where(LEAGUE.LEAGUEID.eq(objectName)).fetch();
        return (!result.isEmpty());
    }

    @Override
    public Object selectFromDB(String objectName) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result <?> result = create.select().from(LEAGUE).where(LEAGUE.LEAGUEID.eq(objectName)).fetch();
        String leagueID = result.get(0).get(LEAGUE.LEAGUEID);
        return leagueID;
    }

    @Override
    public boolean removeFromDB(String objectName) {
        if(containInDB(objectName)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(LEAGUE)
                    .where(LEAGUE.LEAGUEID.eq(objectName)).execute();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean addToDb(String username, String password, String name, Map<String, ArrayList<String>> objDetails) {
        return false;
    }


    public boolean addToDb(String leagueID){
        if(!containInDB(leagueID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(LEAGUE,LEAGUE.LEAGUEID).values(leagueID).execute();
            return true;
        }
        return false;
    }
}
