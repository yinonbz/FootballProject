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
import static dataLayer.Tables.Tables.UNCONFIRMED_TEAMS;

public class DBUnconfirmedTeams implements DB_Inter {

    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    //String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    String myUrl = "jdbc:mysql://localhost:3306/localsoccer";
    Connection connection = null;



    public DBUnconfirmedTeams(){
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
        Result<?> result = create.select().from(UNCONFIRMED_TEAMS).where(UNCONFIRMED_TEAMS.TEAMID.eq(objectName)).fetch();
        return (!result.isEmpty());
    }

    @Override
    public Object selectFromDB(String objectName) {
        return null;
    }

    public HashMap<String,String> selectUnconfirmedTeamFromDB(String teamID){
        HashMap<String,String> details = new HashMap<>();
        if(containInDB(teamID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result <?> result = create.select().from(UNCONFIRMED_TEAMS).where(UNCONFIRMED_TEAMS.TEAMID.eq(teamID)).fetch();
            String year  = result.get(0).get(UNCONFIRMED_TEAMS.ESTABLISHEDYEAR)+"";
            String teamOwner = result.get(0).get(UNCONFIRMED_TEAMS.OWNERID);
            details.put("year",year);
            details.put("owner",teamOwner);
            details.put("teamID",teamID);
            return details;
        }
        return null;
    }


    @Override
    public boolean removeFromDB(String objectName) {
        if(containInDB(objectName)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(UNCONFIRMED_TEAMS).where(UNCONFIRMED_TEAMS.TEAMID.eq(objectName)).execute();
            return true;
        }
        return false;
    }


    @Override
    public boolean addToDb(String username, String password, String name, Map<String, ArrayList<String>> objDetails) {
        return false;
    }

    public boolean addRequestForTeam(String teamID, int establishedYear, String ownerName) {
        if (!containInDB(teamID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(UNCONFIRMED_TEAMS,UNCONFIRMED_TEAMS.TEAMID,UNCONFIRMED_TEAMS.ESTABLISHEDYEAR,UNCONFIRMED_TEAMS.OWNERID).values(teamID,establishedYear,ownerName).execute();
            return true;
        }
        return false;
    }
}
