package dataLayer;

import dataLayer.Tables.*;
import dataLayer.Tables.tables.Teams;
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

import static dataLayer.Tables.Tables.SEASONS;
import static dataLayer.Tables.tables.CoachTeam.COACH_TEAM;
import static dataLayer.Tables.tables.Match.MATCH;
import static dataLayer.Tables.tables.OwnerTeams.OWNER_TEAMS;
import static dataLayer.Tables.tables.OwnersOfStadium.OWNERS_OF_STADIUM;
import static dataLayer.Tables.tables.SeasonTeams.SEASON_TEAMS;
import static dataLayer.Tables.tables.TeamPlayers.TEAM_PLAYERS;
import static dataLayer.Tables.tables.Teams.TEAMS;

public class TeamDB implements DB_Inter {

    String username;
    String password;
    String myDriver;
    String myUrl;
    Connection connection = null;

    public TeamDB(){
        this("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://132.72.65.33:3306/demodb");
    }

    public TeamDB(String username,String password, String myDriver, String myUrl){
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
    public boolean containInDB(String objectName,String arg1,String arg2) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().
                from(TEAMS)
                .where(TEAMS.NAME.eq(objectName)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String objectName,String arg1,String arg2) {
        if(containInDB(objectName,null,null)){

            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().
                    from(TEAMS)
                    .where(TEAMS.NAME.eq(objectName)).fetch();

            Map<String,ArrayList<String>> teamDetails = new HashMap<>();
            ArrayList<String> establishedYear = new ArrayList<>();
            establishedYear.add(String.valueOf(result.get(0).get(TEAMS.ESTABLISHEDYEAR)));
            teamDetails.put("establishedYear",establishedYear);
            ArrayList<String> isActive = new ArrayList<>();
            isActive.add(Boolean.toString(result.get(0).get(TEAMS.ISACTIVE)));
            teamDetails.put("isActive",isActive);
            ArrayList<String> teamManagerID= new ArrayList<>();
            teamManagerID.add(result.get(0).get(TEAMS.TEAMMANAGERID));
            teamDetails.put("teamManagerID",teamManagerID);
            ArrayList<String> closedByAdmin= new ArrayList<>();
            closedByAdmin.add(Boolean.toString(result.get(0).get(TEAMS.CLOSEDBYADMIN)));
            teamDetails.put("closedByAdmin",closedByAdmin);

            //add players
            result = create.select()
                    .from(TEAM_PLAYERS)
                    .where(TEAM_PLAYERS.TEAMID.eq(objectName)).fetch();

            ArrayList<String> players = new ArrayList<>();
            for(Record r: result){
                players.add(r.get(TEAM_PLAYERS.PLAYERID));
            }

            teamDetails.put("players",players);

            //add coaches
            result = create.select()
                    .from(COACH_TEAM)
                    .where(COACH_TEAM.TEAMID.eq(objectName)).fetch();

            ArrayList<String> coaches = new ArrayList<>();
            for(Record r: result){
                coaches.add(r.get(COACH_TEAM.COACHID));
            }

            teamDetails.put("coaches",coaches);

            //add seasons
            result = create.select()
                    .from(SEASON_TEAMS)
                    .where(SEASON_TEAMS.TEAMID.eq(objectName)).fetch();

            ArrayList<String> seasons = new ArrayList<>();
            ArrayList<String> leagues = new ArrayList<>();
            for(Record r: result){
                seasons.add(String.valueOf(r.get(SEASON_TEAMS.SEASONID)));
                leagues.add(r.get(SEASON_TEAMS.LEAGUEID));
            }
            teamDetails.put("seasons",seasons);
            teamDetails.put("leagues",leagues);

            //add owners
            result = create.select()
                    .from(OWNER_TEAMS)
                    .where(OWNER_TEAMS.TEAMID.eq(objectName)).fetch();

            ArrayList<String> ownerID = new ArrayList<>();
            for(Record r: result){
                ownerID.add(r.get(OWNER_TEAMS.OWNERID));
            }
            teamDetails.put("ownerID",ownerID);

            //add matches
            result = create.select()
                    .from(MATCH)
                    .where(MATCH.TEAMAWAYID.eq(objectName))
                    .or(MATCH.TEAMHOMEID.eq(objectName))
                    .fetch();

            ArrayList<String> matches = new ArrayList<>();
            for(Record r: result){
                matches.add(String.valueOf(r.get(MATCH.MATCHID)));
            }
            teamDetails.put("matches",matches);

            //add stadium
            result = create.select()
                    .from(OWNERS_OF_STADIUM)
                    .where(OWNERS_OF_STADIUM.TEAMID.eq(objectName))
                    .fetch();

            ArrayList<String> stadium = new ArrayList<>();
            for(Record r: result){
                stadium.add(r.get(OWNERS_OF_STADIUM.STADIUMID));
            }
            teamDetails.put("stadium",stadium);

            return teamDetails;
        }
        return null;
    }

    @Override
    public boolean removeFromDB(String objectName,String arg1,String arg2) {
        System.out.println("cannot delete team");
        return false;
    }

    @Override
    public boolean addToDB(String name, String establishedYear, String isActive, String teamManagerID, Map<String, ArrayList<String>> objDetails) {
        if(!containInDB(name,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(TEAMS
                    ,TEAMS.NAME
                    ,TEAMS.ESTABLISHEDYEAR
                    ,TEAMS.TEAMMANAGERID
                    ,TEAMS.CLOSEDBYADMIN
                    ,TEAMS.ISACTIVE)
                    .values(name
                            ,Integer.parseInt(establishedYear)
                            ,teamManagerID
                            ,Boolean.valueOf(objDetails.get("closedByAdmin").get(0))
                            ,Boolean.valueOf(isActive))
                    .execute();

            create.insertInto(OWNERS_OF_STADIUM
                    ,OWNERS_OF_STADIUM.TEAMID
                    ,OWNERS_OF_STADIUM.STADIUMID)
                    .values(name,objDetails.get("stadium").get(0))
            .execute();

            for(String str: objDetails.get("players")){
                create.insertInto(TEAM_PLAYERS
                        ,TEAM_PLAYERS.TEAMID
                        ,TEAM_PLAYERS.PLAYERID)
                        .values(name,str)
                .execute();
            }
            for(String str: objDetails.get("coaches")){
                create.insertInto(COACH_TEAM
                ,COACH_TEAM.TEAMID
                ,COACH_TEAM.COACHID)
                        .values(name,str)
                .execute();
            }
            for(String str: objDetails.get("teamOwners")){
                create.insertInto(OWNER_TEAMS
                ,OWNER_TEAMS.TEAMID
                ,OWNER_TEAMS.OWNERID)
                        .values(name,str)
                .execute();
            }
            return true;
        }


        return false;
    }

    @Override
    public int countRecords() {
        return -1;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> userType) {
        ArrayList<Map<String, ArrayList<String>>> details = new ArrayList<>();
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(userType == TEAMUPDATES.ALLTEAMS) {
            Result<?> result = create.select(TEAMS.NAME).from(TEAMS).fetch();
            for (Record record : result) {
                HashMap<String, ArrayList<String>> seasonDetails = new HashMap<>();
                ArrayList<String> temp = new ArrayList<>();
                temp.add(record.get(TEAMS.NAME));
                seasonDetails.put("teamID", temp);
                details.add(seasonDetails);
            }
        }
        else if(userType == TEAMUPDATES.TEAMOFOWNER){
            Result<?> result = create.select(OWNER_TEAMS.OWNERID,OWNER_TEAMS.TEAMID).from(OWNER_TEAMS).fetch();
            for (Record record : result) {
                HashMap<String, ArrayList<String>> teams = new HashMap<>();
                ArrayList<String> temp = new ArrayList<>();
                temp.add(record.get(OWNER_TEAMS.OWNERID));
                temp.add(record.get(OWNER_TEAMS.TEAMID));
                teams.put("teamDetails", temp);
                details.add(teams);
            }
        }
        else if(userType == TEAMUPDATES.ONLYACTIVE){

        }
        return details;
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(e==TEAMUPDATES.SETACTIVE){
            create.update(TEAMS)
                    .set(TEAMS.ISACTIVE, Boolean.valueOf(arguments.get("isActive")))
                    .where(TEAMS.NAME.eq(arguments.get("teamID")))
                    .execute();
            return true;
        }
        if(e==TEAMUPDATES.ADDPLAYER){
            create.insertInto(TEAM_PLAYERS
                    ,TEAM_PLAYERS.PLAYERID
                    ,TEAM_PLAYERS.TEAMID)
                    .values(arguments.get("playerID")
                            ,arguments.get("teamID"))
                    .onDuplicateKeyUpdate()
                    .set(TEAM_PLAYERS.PLAYERID,arguments.get("playerID"))
                    .set(TEAM_PLAYERS.TEAMID,arguments.get("teamID"))
                    .execute();
            return true;
        }
        if(e==TEAMUPDATES.SETTEAMMANAGER){
            create.update(TEAMS)
                    .set(TEAMS.TEAMMANAGERID, arguments.get("managerID"))
                    .where(TEAMS.NAME.eq(arguments.get("teamID")))
                    .execute();
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
}
