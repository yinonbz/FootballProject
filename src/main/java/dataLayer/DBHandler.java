package dataLayer;

import dataLayer.Tables.enums.*;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static dataLayer.Tables.Tables.*;
import static dataLayer.Tables.tables.Subscribers.SUBSCRIBERS;

public class DBHandler implements DB_Inter{

    String username;
    String password;
    String myDriver;
    String myUrl;
    Connection connection = null;

    public DBHandler(){
        this("root","Messi1Ronaldo2",
                "org.mariadb.jdbc.Driver","jdbc:mysql://132.72.65.33:3306/demodb");
    }

    public DBHandler(String username,String password, String myDriver, String myUrl){
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

    /**
     * function to search in db for subscribers
     * @param objectName
     * @return
     */

    @Override
    public boolean containInDB(String objectName,String empty1,String empty2) {
        //create sql query to search record in db using ObjectName
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        try {
            Result<?> result = create.select().
                    from(SUBSCRIBERS)
                    .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName)).fetch();
            if (result.isEmpty()) {
                return false;
            }
            return true;
        }
        catch(Exception e){
            System.out.println("syntax error in handler contain function");
            return false;
        }
    }

    /**
     *  function to get a subscriber object from data base
     * @param objectName
     * @return
     */

    @Override
    public Map<String,ArrayList<String>> selectFromDB(String objectName,String arg2,String arg3) {
        //create sql query to select record from db using ObjectName

        try {
            if (containInDB(objectName, null, null)) {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select().
                        from(SUBSCRIBERS)
                        .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName))
                        .fetch();

                Map<String, ArrayList<String>> objDetails = new HashMap<>();
                String username = result.get(0).get(SUBSCRIBERS.SUBSCRIBERID);
                String password = result.get(0).get(SUBSCRIBERS.PASSWORD);
                String name = result.get(0).get(SUBSCRIBERS.NAME);
                String type = result.get(0).get(SUBSCRIBERS.TYPE);

                objDetails.put("username", new ArrayList<>());
                objDetails.get("username").add(username);
                objDetails.put("password", new ArrayList<>());
                objDetails.get("password").add(password);
                objDetails.put("name", new ArrayList<>());
                objDetails.get("name").add(name);
                objDetails.put("type", new ArrayList<>());
                objDetails.get("type").add(type);

                try {
                    if (type.equalsIgnoreCase("player")) {
                        result = create.select().from(PLAYERS).where(PLAYERS.PLAYERID.eq(objectName)).fetch();
                        String teamID = result.get(0).get(PLAYERS.TEAMID);
                        String birthDay = result.get(0).get(PLAYERS.BIRTHDATE).toString();
                        String fieldJob = result.get(0).get(PLAYERS.FIELDJOB).name();
                        int salary = result.get(0).get(PLAYERS.SALARY);
                        String teamOwnerID = result.get(0).get(PLAYERS.TEAMOWNERID_FICTIVE);

                        objDetails.put("teamID", new ArrayList<>());
                        objDetails.get("teamID").add(teamID);
                        objDetails.put("birthDay", new ArrayList<>());
                        objDetails.get("birthDay").add(birthDay);
                        objDetails.put("fieldJob", new ArrayList<>());
                        objDetails.get("fieldJob").add(fieldJob);
                        objDetails.put("salary", new ArrayList<>());
                        objDetails.get("salary").add(String.valueOf(salary));
                        objDetails.put("teamOwnerID", new ArrayList<>());
                        objDetails.get("teamOwnerID").add(teamOwnerID);
                    }
                    if (type.equalsIgnoreCase("coach")) {
                        result = create.select().from(COACHES
                                .join(COACH_TEAM).on(COACHES.COACHID.eq(COACH_TEAM.COACHID)))
                                .where(COACHES.COACHID.eq(objectName)).fetch();
                        ArrayList<String> teams = new ArrayList<>();
                        String roleInTeam = result.get(0).get(COACHES.ROLEINTEAM).name();
                        String training = result.get(0).get(COACHES.TRAINING).name();
                        int salary = result.get(0).get(COACHES.SALARY);
                        String teamOwnerID = result.get(0).get(COACHES.TEAMOWNERID_FICTIVE);

                        for (Record r : result) {
                            teams.add(r.get(COACH_TEAM.TEAMID));
                        }

                        objDetails.put("roleInTeam", new ArrayList<>());
                        objDetails.get("roleInTeam").add(roleInTeam);
                        objDetails.put("teams", teams);
                        objDetails.put("training", new ArrayList<>());
                        objDetails.get("training").add(training);
                        objDetails.put("salary", new ArrayList<>());
                        objDetails.get("salary").add(String.valueOf(salary));
                        objDetails.put("teamOwnerID", new ArrayList<>());
                        objDetails.get("teamOwnerID").add(teamOwnerID);
                    }
                    if (type.equalsIgnoreCase("teammanager")) {
                        result = create.select().from(TEAMMANAGERS).where(TEAMMANAGERS.MANAGERID.eq(objectName)).fetch();
                        String teamID = result.get(0).get(TEAMMANAGERS.TEAMID);
                        String permissions = result.get(0).get(TEAMMANAGERS.PERMISSIONS).name();
                        int salary = result.get(0).get(TEAMMANAGERS.SALARY);
                        String teamOwnerID = result.get(0).get(TEAMMANAGERS.TEAMOWNERID_FICTIVE);

                        objDetails.put("teamID", new ArrayList<>());
                        objDetails.get("teamID").add(teamID);
                        objDetails.put("permissions", new ArrayList<>());
                        objDetails.get("permissions").add(permissions);
                        objDetails.put("salary", new ArrayList<>());
                        objDetails.get("salary").add(String.valueOf(salary));
                        objDetails.put("teamOwnerID", new ArrayList<>());
                        objDetails.get("teamOwnerID").add(teamOwnerID);
                    }
                    if (type.equalsIgnoreCase("teamowner")) {
                        //query
                        result = create.select().
                                from(TEAMOWNER_OWNERELIGIBLE.
                                        join(OWNER_TEAMS).on(TEAMOWNER_OWNERELIGIBLE.OWNERID.eq(OWNER_TEAMS.OWNERID))).
                                where(TEAMOWNER_OWNERELIGIBLE.OWNERID.eq(objectName)).
                                fetch();

                        //data extraction

                        //String teamOwnerID = result.get(0).get(TEAMOWNER_OWNERELIGIBLE.OWNERID);
                        String eligible = Stream.of(result.get(0).get(TEAMOWNER_OWNERELIGIBLE.PLAYERID), result.get(0).get(TEAMOWNER_OWNERELIGIBLE.COACHID), result.get(0).get(TEAMOWNER_OWNERELIGIBLE.MANAGERID)).filter(Objects::nonNull).findFirst().orElse(null);
                        ArrayList<String> teams = new ArrayList<>();

                        for (Record r : result) {
                            teams.add(r.get(OWNER_TEAMS.TEAMID));
                        }

                        ArrayList<String> managersAssignedByMe = new ArrayList<>();
                        ArrayList<String> managerTeam = new ArrayList<>();
                        Result<?> result2 = create.select().
                                from(OWNER_MANAGER_ASSIGNINGS).
                                where(OWNER_MANAGER_ASSIGNINGS.OWNERID.eq(objectName))
                                .fetch();
                        for (Record r : result2) {
                            managersAssignedByMe.add(r.get(OWNER_MANAGER_ASSIGNINGS.TEAMMANAGERID));
                            managerTeam.add(r.get(OWNER_MANAGER_ASSIGNINGS.TEAMID));
                        }
                        Result<?> result3 = create.select().
                                from(OWNER_OWNER_ASSIGNINGS).
                                where(OWNER_OWNER_ASSIGNINGS.OWNERID.eq(objectName))
                                .fetch();

                        ArrayList<String> ownerAssignedByMe = new ArrayList<>();
                        ArrayList<String> ownerTeam = new ArrayList<>();
                        for (Record r : result3) {
                            ownerAssignedByMe.add(r.get(OWNER_OWNER_ASSIGNINGS.ASSIGNEEID));
                            ownerTeam.add(r.get(OWNER_OWNER_ASSIGNINGS.TEAMID));
                        }
                        objDetails.put("teams", teams);
                        objDetails.put("ownerAssigned", ownerAssignedByMe);
                        objDetails.put("ownerTeam", ownerTeam);
                        objDetails.put("managersAssigned", managersAssignedByMe);
                        objDetails.put("managerTeam", managerTeam);
                        objDetails.put("eligible", new ArrayList<>());
                        objDetails.get("eligible").add(eligible);
                    }
                    if (type.equalsIgnoreCase("admin")) {
                        result = create.select().
                                from(ADMINS).where(ADMINS.ADMINID.eq(objectName)).fetch();
                        boolean approved = result.get(0).get(ADMINS.APPROVED);

                        objDetails.put("approved", new ArrayList<>());
                        objDetails.get("approved").add(Boolean.toString(approved));

                    }
                    if (type.equalsIgnoreCase("ar")) {
                        result = create.select().
                                from(ARS).where(ARS.AR_ID.eq(objectName)).fetch();
                        boolean approved = result.get(0).get(ARS.APPROVED);

                        objDetails.put("approved", new ArrayList<>());
                        objDetails.get("approved").add(Boolean.toString(approved));
                    }
                    if (type.equalsIgnoreCase("referee")) {
                        result = create.select().
                                from(REFEREES)
                                //.join().on(REFEREES.REFEREEID.eq(REFEREE_MATCHES.REFEREEID)))
                                .where(REFEREES.REFEREEID.eq(objectName)).fetch();
                        Result<?> result2 = create.select()
                                .from(REFEREE_MATCHES)
                                .where(REFEREE_MATCHES.REFEREEID.eq(objectName))
                                .fetch();
                        String roleRef = result.get(0).get(REFEREES.ROLEREF).name();
                        ArrayList<String> matches = new ArrayList<>();
                        for (Record r : result2) {
                            matches.add(String.valueOf(r.get(REFEREE_MATCHES.MATCHID)));
                        }

                        objDetails.put("matches", matches);
                        objDetails.put("roleRef", new ArrayList<>());
                        objDetails.get("roleRef").add(roleRef);
                    }
                    return objDetails;
                } catch (Exception e) {
                    System.out.println("user is subscriber only");
                    return objDetails;
                }
            }
        }
        catch(Exception e){
            System.out.println("user has not found");
        }
        return null;
    }

    /**
     * demo function to remove a subscriber from DB
     * @param objectName
     * @return
     */
    @Override
    public boolean removeFromDB(String objectName,String arg2,String arg3) {
        try {
            if (containInDB(objectName, null, null)) {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                create.delete(SUBSCRIBERS)
                        .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName)).execute();
                return true;
            }
            return false;
        }
        catch(Exception e){
            System.out.println("error removing subscriber from DB");
            return false;
        }
    }

    /**
     *function to add a subscriber to DB
     * @param username subscriber username
     * @param password subscriber password
     * @param name subscriber name
     * @param type subscriber type
     * @param objDetails userType details (player,coach etc...)
     * @return added successfully or not
     */
    @Override
    public boolean addToDB(String username,String password,String name,String type, Map<String,ArrayList<String>> objDetails) {
        //check if user in db already
        try {
            if (!containInDB(username, null, null)) {
                //get subscriber from db
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                try {
                    create.insertInto(SUBSCRIBERS
                            , SUBSCRIBERS.SUBSCRIBERID
                            , SUBSCRIBERS.PASSWORD
                            , SUBSCRIBERS.NAME
                            , SUBSCRIBERS.TYPE)
                            .values(username
                                    , String.valueOf(password.hashCode())
                                    , name
                                    , type)
                            .execute();
                }
                catch(Exception e){
                    System.out.println("error adding subscriber type to DB");
                }
                try {
                    //get player from db
                    if (type.equalsIgnoreCase("player")) {

                        create.insertInto(PLAYERS
                                , PLAYERS.PLAYERID
                                , PLAYERS.TEAMID
                                , PLAYERS.FIELDJOB
                                , PLAYERS.BIRTHDATE
                                , PLAYERS.SALARY
                                , PLAYERS.TEAMOWNERID_FICTIVE)
                                .values(username
                                        , objDetails.get("teamID").get(0)
                                        , PlayersFieldjob.valueOf(objDetails.get("fieldJob").get(0))
                                        , convertToDate(objDetails.get("birthDate").get(0))
                                        , Integer.parseInt(objDetails.get("salary").get(0))
                                        , objDetails.get("ownerFictive").isEmpty() ?
                                                null : objDetails.get("ownerFictive").get(0))
                                .execute();
                        return true;
                    }
                    if (type.equalsIgnoreCase("coach")) {
                        create.insertInto(COACHES
                                , COACHES.COACHID
                                , COACHES.ROLEINTEAM
                                , COACHES.TRAINING
                                , COACHES.SALARY
                                , COACHES.TEAMOWNERID_FICTIVE)
                                .values(username
                                        , CoachesRoleinteam.valueOf(objDetails.get("roleInTeam").get(0))
                                        , CoachesTraining.valueOf(objDetails.get("training").get(0))
                                        , Integer.parseInt(objDetails.get("salary").get(0))
                                        , objDetails.get("ownerFictive").isEmpty() ?
                                                null : objDetails.get("ownerFictive").get(0))
                                .execute();

                        for (String str : objDetails.get("teams")) {
                            create.insertInto(COACH_TEAM
                                    , COACH_TEAM.COACHID
                                    , COACH_TEAM.TEAMID)
                                    .values(username, str)
                                    .execute();
                        }
                        return true;
                    }
                    if (type.equalsIgnoreCase("teammanager")) {
                        create.insertInto(TEAMMANAGERS
                                , TEAMMANAGERS.MANAGERID
                                , TEAMMANAGERS.TEAMID
                                , TEAMMANAGERS.PERMISSIONS
                                , TEAMMANAGERS.SALARY
                                , TEAMMANAGERS.TEAMOWNERID_FICTIVE)
                                .values(username
                                        , objDetails.get("teamID").get(0)
                                        , TeammanagersPermissions.valueOf(objDetails.get("permissions").get(0))
                                        , Integer.parseInt(objDetails.get("salary").get(0))
                                        , objDetails.get("ownerFictive").isEmpty() ?
                                                null : objDetails.get("ownerFictive").get(0))
                                .execute();
                        return true;
                    }
                    if (type.equalsIgnoreCase("teamowner")) {
                        create.insertInto(TEAMOWNER_OWNERELIGIBLE
                                , TEAMOWNER_OWNERELIGIBLE.OWNERID
                                , TEAMOWNER_OWNERELIGIBLE.PLAYERID
                                , TEAMOWNER_OWNERELIGIBLE.COACHID
                                , TEAMOWNER_OWNERELIGIBLE.MANAGERID)
                                .values(username
                                        , objDetails.get("playerID").isEmpty() ?
                                                null : objDetails.get("playerID").get(0)
                                        , objDetails.get("coachID").isEmpty() ?
                                                null : objDetails.get("coachID").get(0)
                                        , objDetails.get("managerID").isEmpty() ?
                                                null : objDetails.get("managerID").get(0))
                                .execute();

                        for (String str : objDetails.get("teams")) {
                            create.insertInto(OWNER_TEAMS
                                    , OWNER_TEAMS.OWNERID
                                    , OWNER_TEAMS.TEAMID)
                                    .values(username, str)
                                    .execute();
                        }
                        for (int i = 0; i < objDetails.get("ownersAssigned").size(); i++) {
                            create.insertInto(OWNER_OWNER_ASSIGNINGS
                                    , OWNER_OWNER_ASSIGNINGS.OWNERID
                                    , OWNER_OWNER_ASSIGNINGS.ASSIGNEEID
                                    , OWNER_OWNER_ASSIGNINGS.TEAMID)
                                    .values(username
                                            , objDetails.get("ownersAssigned").get(i)
                                            , objDetails.get("ownerTeam").get(i))
                                    .execute();
                        }

                        for (int i = 0; i < objDetails.get("managersAssigned").size(); i++) {
                            create.insertInto(OWNER_MANAGER_ASSIGNINGS
                                    , OWNER_MANAGER_ASSIGNINGS.OWNERID
                                    , OWNER_MANAGER_ASSIGNINGS.TEAMMANAGERID
                                    , OWNER_MANAGER_ASSIGNINGS.TEAMID)
                                    .values(username
                                            , objDetails.get("managersAssigned").get(i)
                                            , objDetails.get("managerTeam").get(i))
                                    .execute();
                        }
                    }
                    if (type.equalsIgnoreCase("admin")) {
                        create.insertInto(ADMINS
                                , ADMINS.ADMINID
                                , ADMINS.APPROVED)
                                .values(username,
                                        Boolean.valueOf(objDetails.get("approved").get(0)))
                                .execute();
                    }
                    if (type.equalsIgnoreCase("ar")) {
                        create.insertInto(ARS
                                , ARS.AR_ID
                                , ARS.APPROVED)
                                .values(username,
                                        Boolean.valueOf(objDetails.get("approved").get(0)))
                                .execute();

                    }
                    if (type.equalsIgnoreCase("referee")) {
                        create.insertInto(REFEREES
                                , REFEREES.REFEREEID
                                , REFEREES.ROLEREF).
                                values(username, RefereesRoleref.valueOf(objDetails.get("roleRef").get(0)))
                                .execute();

                        for (String str : objDetails.get("matches")) {
                            create.insertInto(REFEREE_MATCHES
                                    , REFEREE_MATCHES.REFEREEID
                                    , REFEREE_MATCHES.MATCHID).
                                    values(username, Integer.parseInt(str))
                                    .execute();
                        }
                    }
                    return true;
                }
                catch(Exception e){
                    System.out.println("error adding additional type to subscriber in DB");
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            System.out.println("error adding subscriber to DB");
            return false;
        }
    }

    @Override
    public int countRecords() {
        return -1;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> userType,Map<String,String> arguments) {
        if (userType == UserTypes.SUBSCRIBER) {
            try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select(SUBSCRIBERS.SUBSCRIBERID).
                        from(SUBSCRIBERS)
                        .fetch();
                ArrayList<Map<String, ArrayList<String>>> allSubscribers = new ArrayList<>();
                allSubscribers.add(new HashMap<>());
                allSubscribers.get(0).put("subscribers", new ArrayList<>());
                for (Record r : result) {
                    allSubscribers.get(0).get("subscribers").add(r.get(SUBSCRIBERS.SUBSCRIBERID));

                }
                return allSubscribers;
            }
            catch(Exception e){
                System.out.println("error selecting all subscribers");
                return null;
            }
        }
        if (userType == UserTypes.COACH) {
            try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select(COACHES.COACHID).
                        from(COACHES)
                        .fetch();
                ArrayList<Map<String, ArrayList<String>>> allCoaches = new ArrayList<>();
                allCoaches.add(new HashMap<>());
                allCoaches.get(0).put("coaches", new ArrayList<>());
                for (Record r : result) {
                    allCoaches.get(0).get("coaches").add(r.get(COACHES.COACHID));

                }
                return allCoaches;
            }
            catch(Exception e){
                System.out.println("error selecting all coaches");
                return null;
            }
        }
        if (userType == UserTypes.PLAYER) {
            try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select(PLAYERS.PLAYERID).
                        from(PLAYERS)
                        .fetch();
                ArrayList<Map<String, ArrayList<String>>> allPlayers = new ArrayList<>();
                allPlayers.add(new HashMap<>());
                allPlayers.get(0).put("players", new ArrayList<>());
                for (Record r : result) {
                    allPlayers.get(0).get("players").add(r.get(PLAYERS.PLAYERID));

                }
                return allPlayers;
            }
            catch(Exception e){
                System.out.println("error selecting all players");
                return null;
            }
        }
        if (userType == UserTypes.ADMIN) {
            try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select(ADMINS.ADMINID).
                        from(ADMINS)
                        .fetch();
                ArrayList<Map<String, ArrayList<String>>> allAdmins = new ArrayList<>();
                allAdmins.add(new HashMap<>());
                allAdmins.get(0).put("admins", new ArrayList<>());
                for (Record r : result) {
                    allAdmins.get(0).get("admins").add(r.get(ADMINS.ADMINID));

                }
                return allAdmins;
            }
            catch(Exception e){
                System.out.println("error selecting all admins");
                return null;
            }
        }
        if (userType == UserTypes.REFEREE) {
            try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select(REFEREES.REFEREEID).
                        from(REFEREES)
                        .fetch();
                ArrayList<Map<String, ArrayList<String>>> allReferees = new ArrayList<>();
                allReferees.add(new HashMap<>());
                allReferees.get(0).put("referees", new ArrayList<>());
                for (Record r : result) {
                    allReferees.get(0).get("referees").add(r.get(REFEREES.REFEREEID));

                }
                return allReferees;
            }
            catch(Exception e){
                System.out.println("error selecting all referees");
                return null;
            }
        }
        if(userType == UserTypes.TEAMMANAGER){
            try {
                return selectAllTeamManagers();
            }
            catch(Exception e){
                System.out.println("error selecting all team managers");
                return null;
            }
        }
        if(userType == SUBSCRIBERSUPDATES.ALLREFS){
            return selectAllRefs();
        }
        else{
            System.out.println("invalid select from subscriberDB");
        }
        return null;
    }

    private ArrayList<Map<String, ArrayList<String>>> selectAllRefs(){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select(REFEREES.REFEREEID).from(REFEREES).fetch();
        ArrayList<Map<String, ArrayList<String>>> details = new ArrayList<>();
        for (Record record : result) {
            HashMap<String, ArrayList<String>> seasonDetails = new HashMap<>();
            ArrayList<String> temp = new ArrayList<>();
            String refID = record.get(REFEREES.REFEREEID);
            //String leagueID = record.get(SEASONS.LEAGUEID);
            temp.add(refID);
            seasonDetails.put(refID, temp);
            details.add(seasonDetails);
        }
        return details;
    }

    private ArrayList<Map<String, ArrayList<String>>> selectAllTeamManagers(){
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        try {
            Result<?> result = create.select(TEAMMANAGERS.MANAGERID).from(TEAMMANAGERS).fetch();
            ArrayList<Map<String, ArrayList<String>>> details = new ArrayList<>();
            for (Record record : result) {
                HashMap<String, ArrayList<String>> seasonDetails = new HashMap<>();
                ArrayList<String> temp = new ArrayList<>();
                temp.add(record.get(TEAMMANAGERS.MANAGERID));
                seasonDetails.put("managerID", temp);
                details.add(seasonDetails);
            }
            return details;
        }
        catch(Exception e){
            System.out.println("error selecting all team managers");
            return null;
        }
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {

        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(e == SUBSCRIBERSUPDATES.ADMINSETAPPROVED){
            try {
                create.update(ADMINS)
                        .set(ADMINS.APPROVED, Boolean.valueOf(arguments.get("setApproved")))
                        .where(ADMINS.ADMINID.eq(arguments.get("adminID")))
                        .execute();
                return true;
            }
            catch(Exception exptn){
                System.out.println("error updating admin set approved");
                return false;
            }
        }
        if(e == SUBSCRIBERSUPDATES.ARSETAPPROVED){
            try {
                create.update(ARS)
                        .set(ARS.APPROVED, Boolean.valueOf(arguments.get("setApproved")))
                        .where(ARS.AR_ID.eq(arguments.get("ARID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error updating AR set approved");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETTEAMTOPLAYER){
            try {
                create.update(PLAYERS)
                        .set(PLAYERS.TEAMID, arguments.get("teamID"))
                        .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting team to player");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETTEAMTOTM){
            try {
                create.update(TEAMMANAGERS)
                        .set(TEAMMANAGERS.TEAMID, arguments.get("teamID"))
                        .where(TEAMMANAGERS.MANAGERID.eq(arguments.get("managerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting team to team manaager");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETPLAYERBIRTHDATE){
            try {
                create.update(PLAYERS)
                        .set(PLAYERS.BIRTHDATE, convertToDate(arguments.get("birthDate")))
                        .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting birth date to player");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETSUBSCRIBERNAME){
            try {
                create.update(SUBSCRIBERS)
                        .set(SUBSCRIBERS.NAME, arguments.get("name"))
                        .where(SUBSCRIBERS.SUBSCRIBERID.eq(arguments.get("subscriberID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting name to subscriber");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETPLAYERFIELDJOB){
            try {
                create.update(PLAYERS)
                        .set(PLAYERS.FIELDJOB, PlayersFieldjob.valueOf(arguments.get("fieldJob")))
                        .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting fieldjob to player");
                return false;
            } catch (IllegalArgumentException e1) {
                System.out.println("error setting fieldjob to player. bad enum expression");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETPLAYERSALARY){
            try {
                create.update(PLAYERS)
                        .set(PLAYERS.SALARY, Integer.parseInt(arguments.get("salary")))
                        .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting salary to player");
                return false;
            } catch (NumberFormatException e1) {
                System.out.println("error setting salary to player. bad salary value.");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETTMPERMISSIONS){
            try {
                create.update(TEAMMANAGERS)
                        .set(TEAMMANAGERS.PERMISSIONS, TeammanagersPermissions.valueOf(arguments.get("permissions")))
                        .where(TEAMMANAGERS.MANAGERID.eq(arguments.get("managerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting permissions to team manager");
                return false;
            } catch (IllegalArgumentException e1) {
                System.out.println("error setting permissions to team manager. bad enum expression.");
                return false;
            }
        }
        if(e== SUBSCRIBERSUPDATES.SETTMSALARY){
            try {
                create.update(TEAMMANAGERS)
                        .set(TEAMMANAGERS.SALARY, Integer.parseInt(arguments.get("salary")))
                        .where(TEAMMANAGERS.MANAGERID.eq(arguments.get("managerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting salary to team manager");
                return false;
            } catch (NumberFormatException e1) {
                System.out.println("error setting salary to team manager. bad salary argument");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.ADDMANAGERTOOWNER){
            try {
                create.insertInto(OWNER_MANAGER_ASSIGNINGS
                        ,OWNER_MANAGER_ASSIGNINGS.OWNERID
                        ,OWNER_MANAGER_ASSIGNINGS.TEAMMANAGERID
                        ,OWNER_MANAGER_ASSIGNINGS.TEAMID)
                        .values(arguments.get("ownerID")
                                ,arguments.get("managersAssigned")
                                ,arguments.get("teamID"))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error adding manager to owner");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.ADDOWNERTOOWNER){
            try {
                create.insertInto(OWNER_OWNER_ASSIGNINGS
                        ,OWNER_OWNER_ASSIGNINGS.OWNERID
                        ,OWNER_OWNER_ASSIGNINGS.ASSIGNEEID
                        ,OWNER_OWNER_ASSIGNINGS.TEAMID)
                        .values(arguments.get("ownerID")
                                ,arguments.get("assigneeID")
                                ,arguments.get("teamID"))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error adding owner to owner");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.DELETEMANAGERFROMOWNER){
            try {
                create.delete(OWNER_MANAGER_ASSIGNINGS)
                        .where(OWNER_MANAGER_ASSIGNINGS.OWNERID.eq(arguments.get("ownerID"))
                                .and(OWNER_MANAGER_ASSIGNINGS.TEAMMANAGERID.eq(arguments.get("managerID")))
                                        .and(OWNER_MANAGER_ASSIGNINGS.TEAMID.eq(arguments.get("teamID"))))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error delete manager from owner");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.DELETEOWNERFROMOWNER){
            try {
                create.delete(OWNER_OWNER_ASSIGNINGS)
                        .where(OWNER_OWNER_ASSIGNINGS.OWNERID.eq(arguments.get("ownerID"))
                        .and(OWNER_OWNER_ASSIGNINGS.ASSIGNEEID.eq(arguments.get("assigneeID")))
                        .and(OWNER_OWNER_ASSIGNINGS.TEAMID.eq(arguments.get("teamID"))))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error delete owner from owner");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.SETPLAYERELIGIBLE){
            try {
                create.update(TEAMOWNER_OWNERELIGIBLE)
                        .set(TEAMOWNER_OWNERELIGIBLE.PLAYERID, arguments.get("setPlayerID"))
                        .where(TEAMOWNER_OWNERELIGIBLE.OWNERID.eq(arguments.get("ownerID")))
                        .execute();

                create.update(PLAYERS)
                        .set(PLAYERS.TEAMOWNERID_FICTIVE, arguments.get("setOwnerID"))
                        .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting player eligible");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.SETCOACHELIGIBLE){
            try {
                create.update(TEAMOWNER_OWNERELIGIBLE)
                        .set(TEAMOWNER_OWNERELIGIBLE.COACHID, arguments.get("setCoachID"))
                        .where(TEAMOWNER_OWNERELIGIBLE.OWNERID.eq(arguments.get("ownerID")))
                        .execute();

                create.update(COACHES)
                        .set(COACHES.TEAMOWNERID_FICTIVE, arguments.get("setOwnerID"))
                        .where(COACHES.COACHID.eq(arguments.get("coachID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting coach eligible");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.SETTMELIGIBLE){
            try {
                create.update(TEAMOWNER_OWNERELIGIBLE)
                        .set(TEAMOWNER_OWNERELIGIBLE.MANAGERID, arguments.get("setManagerID"))
                        .where(TEAMOWNER_OWNERELIGIBLE.OWNERID.eq(arguments.get("ownerID")))
                        .execute();

                create.update(TEAMMANAGERS)
                        .set(TEAMMANAGERS.TEAMOWNERID_FICTIVE, arguments.get("setOwnerID"))
                        .where(TEAMMANAGERS.MANAGERID.eq(arguments.get("managerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting TM eligible");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.ADDTEAMTOOWNER){
            try {
                create.insertInto(OWNER_TEAMS
                        ,OWNER_TEAMS.OWNERID
                        ,OWNER_TEAMS.TEAMID)
                        .values(arguments.get("ownerID")
                                ,arguments.get("teamID"))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error adding team to owner");
                return false;
            }
        }
        if(e==SUBSCRIBERSUPDATES.SETSUBSCRIBERPASSWORD){
            try {
                create.update(SUBSCRIBERS)
                        .set(SUBSCRIBERS.PASSWORD, arguments.get("password"))
                        .where(SUBSCRIBERS.SUBSCRIBERID.eq(arguments.get("subscriberID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error setting subscriber password");
                return false;
            }
        }

        if(e==SUBSCRIBERSUPDATES.REMOVEOWNER){
            try {
                if(arguments.get("type").equalsIgnoreCase("player")){
                    update(SUBSCRIBERSUPDATES.SETPLAYERELIGIBLE,arguments);
                }
                if(arguments.get("type").equalsIgnoreCase("coach")){
                    update(SUBSCRIBERSUPDATES.SETCOACHELIGIBLE,arguments);
                }
                if(arguments.get("type").equalsIgnoreCase("teamManager")){
                    update(SUBSCRIBERSUPDATES.SETTMELIGIBLE,arguments);
                }
                create.delete(TEAMOWNER_OWNERELIGIBLE)
                        .where(TEAMOWNER_OWNERELIGIBLE.OWNERID.eq(arguments.get("ownerID")))
                        .execute();
                return true;
            } catch (DataAccessException e1) {
                System.out.println("error removing owner");
                return false;
            }
        }

        return false;
    }

    public boolean TerminateDB() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }

    private LocalDate convertToDate(String date){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            //convert String to LocalDate
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (Exception e) {
            System.out.println("error converting date");
            return null;
        }
    }
}
