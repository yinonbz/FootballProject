package dataLayer;

import org.apache.maven.surefire.shade.api.org.apache.maven.shared.utils.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dataLayer.Tables.tables.OwnersOfStadium.OWNERS_OF_STADIUM;
import static dataLayer.Tables.tables.Stadium.STADIUM;

public class StadiumDB implements DB_Inter {

    String username;
    String password;
    String myDriver;
    String myUrl;
    Connection connection = null;

    public StadiumDB(){
        this("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://localhost:3306/localsoccer");
    }

    public StadiumDB(String username,String password, String myDriver, String myUrl){
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

    @Override
    public boolean containInDB(String objectName,String arg2,String arg3) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(STADIUM)
                .where(STADIUM.STADIUMID.eq(objectName)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String objectName,String arg2,String arg3) {
        //get specified stadium
        if(containInDB(objectName,null,null)){
            Map<String, ArrayList<String>> stadium = new HashMap<>();
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().
                    from(STADIUM)
                    .where(STADIUM.STADIUMID.eq(objectName)).fetch();

            ArrayList<String> stadiumID = new ArrayList<>();
            stadiumID.add(objectName);
            stadium.put("stadiumID",stadiumID);

            ArrayList<String> numOfSeats = new ArrayList<>();
            numOfSeats.add(String.valueOf(result.get(0).get(STADIUM.NUMOFSEATS)));
            stadium.put("numOfSeats",numOfSeats);

            ArrayList<String> ticketCost = new ArrayList<>();
            ticketCost.add(String.valueOf(result.get(0).get(STADIUM.TICKETCOST)));
            stadium.put("ticketCost",ticketCost);

            result = create.select().
                    from(OWNERS_OF_STADIUM)
                    .where(OWNERS_OF_STADIUM.STADIUMID.eq(objectName)).fetch();

            ArrayList<String> teamID = new ArrayList<>();
            for(Record r: result){
                teamID.add(String.valueOf(r.get(OWNERS_OF_STADIUM.TEAMID)));
            }
            stadium.put("teamID",teamID);

            return stadium;
        }
        //get random stadium
        if(StringUtils.isNumeric(objectName)){
            int i = Integer.parseInt(objectName);
            Map<String, ArrayList<String>> stadium = new HashMap<>();
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().
                    from(STADIUM).fetch();

            ArrayList<String> stadiumID = new ArrayList<>();
            stadiumID.add(result.get(i).get(STADIUM.STADIUMID));
            stadium.put("stadiumID",stadiumID);

            ArrayList<String> numOfSeats = new ArrayList<>();
            numOfSeats.add(String.valueOf(result.get(i).get(STADIUM.NUMOFSEATS)));
            stadium.put("numOfSeats",numOfSeats);

            ArrayList<String> ticketCost = new ArrayList<>();
            ticketCost.add(String.valueOf(result.get(i).get(STADIUM.TICKETCOST)));
            stadium.put("ticketCost",ticketCost);

            result = create.select().
                    from(OWNERS_OF_STADIUM)
                    .where(OWNERS_OF_STADIUM.STADIUMID.eq(stadiumID.get(0))).fetch();

            ArrayList<String> teamID = new ArrayList<>();
            for(Record r: result){
                teamID.add(String.valueOf(r.get(OWNERS_OF_STADIUM.TEAMID)));
            }
            stadium.put("teamID",teamID);

            return stadium;
        }
        return null;
    }

    @Override
    public boolean removeFromDB(String objectName,String arg2,String arg3) {
        if(containInDB(objectName,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(STADIUM)
                    .where(STADIUM.STADIUMID.eq(objectName)).execute();
            return true;
        }
        return false;
    }

    @Override
    public boolean addToDB(String stadiumID, String numOfSeats, String ticketCost, String empty, Map<String, ArrayList<String>> objDetails) {
        if(!containInDB(stadiumID,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(STADIUM
                    ,STADIUM.STADIUMID
                    ,STADIUM.NUMOFSEATS
                    ,STADIUM.TICKETCOST)
                    .values(stadiumID
                            ,Integer.parseInt(numOfSeats)
                            ,Integer.parseInt(ticketCost))
                    .execute();

            for(String str: objDetails.get("teams")){
                create.insertInto(OWNERS_OF_STADIUM
                        ,OWNERS_OF_STADIUM.STADIUMID
                        ,OWNERS_OF_STADIUM.TEAMID)
                        .values(stadiumID
                                ,str)
                        .execute();
            }
            return true;
        }
        return false;
    }

    @Override
    public int countRecords() {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        return create.selectCount().from(STADIUM).fetchOne(0,int.class);
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> userType) {
        System.out.println("can't get all stadium from the system");
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
}
