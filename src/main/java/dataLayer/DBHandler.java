package dataLayer;

import dataLayer.Tables.enums.*;
import org.jooq.*;
import org.jooq.Record;
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
        Result<?> result = create.select().
                from(SUBSCRIBERS)
                .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName)).fetch();
        if (result.isEmpty()) {
            return false;
        }
        return true;

    }

    /**
     *  function to get a subscriber object from data base
     * @param objectName
     * @return
     */

    @Override
    public Map<String,ArrayList<String>> selectFromDB(String objectName,String arg2,String arg3) {
        //create sql query to select record from db using ObjectName
        if(containInDB(objectName,null,null)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select().
                    from(SUBSCRIBERS)
                    .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName))
                    .fetch();

            Map<String, ArrayList<String>> objDetails = new HashMap<>();
            String username = result.get(0).get(SUBSCRIBERS.SUBSCRIBERID);
            //String password = result.get(0).get(SUBSCRIBERS.PASSWORD); //todo create constructor withoud password to subscriber
            String name = result.get(0).get(SUBSCRIBERS.NAME);
            String type = result.get(0).get(SUBSCRIBERS.TYPE);

            objDetails.put("username", new ArrayList<>());
            objDetails.get("username").add(username);
            objDetails.put("name", new ArrayList<>());
            objDetails.get("name").add(name);
            objDetails.put("type", new ArrayList<>());
            objDetails.get("type").add(type);

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
        if(containInDB(objectName,null,null)){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.delete(SUBSCRIBERS)
                    .where(SUBSCRIBERS.SUBSCRIBERID.eq(objectName)).execute();
            return true;
        }
        return false;
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
        if(!containInDB(username,null,null)){
            //get subscriber from db
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(SUBSCRIBERS
                    ,SUBSCRIBERS.SUBSCRIBERID
                    ,SUBSCRIBERS.PASSWORD
                    ,SUBSCRIBERS.NAME
                    ,SUBSCRIBERS.TYPE)
                    .values(username
                            ,String.valueOf(password.hashCode())
                            ,name
                            ,type)
                    .execute();
            //get player from db
            if (type.equalsIgnoreCase("player")) {

                create.insertInto(PLAYERS
                        ,PLAYERS.PLAYERID
                        ,PLAYERS.TEAMID
                        ,PLAYERS.FIELDJOB
                        ,PLAYERS.BIRTHDATE
                        ,PLAYERS.SALARY
                        ,PLAYERS.TEAMOWNERID_FICTIVE)
                        .values(username
                                ,objDetails.get("teamID").get(0)
                                ,PlayersFieldjob.valueOf(objDetails.get("fieldJob").get(0))
                                ,convertToDate(objDetails.get("birthDate").get(0))
                                ,Integer.parseInt(objDetails.get("salary").get(0))
                                ,objDetails.get("ownerFictive").isEmpty()?
                                        null :  objDetails.get("ownerFictive").get(0))
                        .execute();
                return true;
            }
            if (type.equalsIgnoreCase("coach")) {
                create.insertInto(COACHES
                        ,COACHES.COACHID
                        ,COACHES.ROLEINTEAM
                        ,COACHES.TRAINING
                        ,COACHES.SALARY
                        ,COACHES.TEAMOWNERID_FICTIVE)
                        .values(username
                                ,CoachesRoleinteam.valueOf(objDetails.get("roleInTeam").get(0))
                                ,CoachesTraining.valueOf(objDetails.get("training").get(0))
                                ,Integer.parseInt(objDetails.get("salary").get(0))
                                ,objDetails.get("ownerFictive").isEmpty()?
                                        null : objDetails.get("ownerFictive").get(0))
                        .execute();

                for (String str : objDetails.get("teams")) {
                    create.insertInto(COACH_TEAM
                                    ,COACH_TEAM.COACHID
                                    ,COACH_TEAM.TEAMID)
                    .values(username,str)
                    .execute();
                }
                return true;
            }
            if (type.equalsIgnoreCase("teammanager")) {
                create.insertInto(TEAMMANAGERS
                        ,TEAMMANAGERS.MANAGERID
                        ,TEAMMANAGERS.TEAMID
                        ,TEAMMANAGERS.PERMISSIONS
                        ,TEAMMANAGERS.SALARY
                        ,TEAMMANAGERS.TEAMOWNERID_FICTIVE)
                        .values(username
                                ,objDetails.get("teamID").get(0)
                                ,TeammanagersPermissions.valueOf(objDetails.get("permissions").get(0))
                                ,Integer.parseInt(objDetails.get("salary").get(0))
                                ,objDetails.get("ownerFictive").isEmpty()?
                                        null : objDetails.get("ownerFictive").get(0))
                        .execute();
                return true;
            }
            if (type.equalsIgnoreCase("teamowner")) {
                create.insertInto(TEAMOWNER_OWNERELIGIBLE
                        ,TEAMOWNER_OWNERELIGIBLE.OWNERID
                        ,TEAMOWNER_OWNERELIGIBLE.PLAYERID
                        ,TEAMOWNER_OWNERELIGIBLE.COACHID
                        ,TEAMOWNER_OWNERELIGIBLE.MANAGERID)
                        .values(username
                                ,objDetails.get("playerID").isEmpty()?
                                        null : objDetails.get("playerID").get(0)
                                ,objDetails.get("coachID").isEmpty()?
                                        null : objDetails.get("coachID").get(0)
                                ,objDetails.get("managerID").isEmpty()?
                                        null : objDetails.get("managerID").get(0))
                        .execute();

                for (String str : objDetails.get("teams")) {
                    create.insertInto(OWNER_TEAMS
                            ,OWNER_TEAMS.OWNERID
                            ,OWNER_TEAMS.TEAMID)
                            .values(username,str)
                    .execute();
                }
                for (int i=0;i<objDetails.get("ownersAssigned").size();i++) {
                    create.insertInto(OWNER_OWNER_ASSIGNINGS
                            ,OWNER_OWNER_ASSIGNINGS.OWNERID
                            ,OWNER_OWNER_ASSIGNINGS.ASSIGNEEID
                            ,OWNER_OWNER_ASSIGNINGS.TEAMID)
                            .values(username
                                    ,objDetails.get("ownersAssigned").get(i)
                                    ,objDetails.get("ownerTeam").get(i))
                    .execute();
                }

                for (int i=0;i<objDetails.get("managersAssigned").size();i++) {
                    create.insertInto(OWNER_MANAGER_ASSIGNINGS
                            ,OWNER_MANAGER_ASSIGNINGS.OWNERID
                            ,OWNER_MANAGER_ASSIGNINGS.TEAMMANAGERID
                            ,OWNER_MANAGER_ASSIGNINGS.TEAMID)
                            .values(username
                                    ,objDetails.get("managersAssigned").get(i)
                                    ,objDetails.get("managerTeam").get(i))
                    .execute();
                }
            }
            if (type.equalsIgnoreCase("admin")) {
                create.insertInto(ADMINS
                        ,ADMINS.ADMINID
                        ,ADMINS.APPROVED)
                        .values(username,
                                Boolean.valueOf(objDetails.get("approved").get(0)))
                .execute();
            }
            if (type.equalsIgnoreCase("ar")) {
                create.insertInto(ARS
                        ,ARS.AR_ID
                        ,ARS.APPROVED)
                        .values(username,
                                Boolean.valueOf(objDetails.get("approved").get(0)))
                        .execute();

            }
            if (type.equalsIgnoreCase("referee")) {
                create.insertInto(REFEREES
                        ,REFEREES.REFEREEID
                        ,REFEREES.ROLEREF).
                        values(username, RefereesRoleref.valueOf(objDetails.get("roleRef").get(0)))
                .execute();

                for(String str: objDetails.get("matches")){
                    create.insertInto(REFEREE_MATCHES
                            ,REFEREE_MATCHES.REFEREEID
                            ,REFEREE_MATCHES.MATCHID).
                            values(username, Integer.parseInt(str))
                    .execute();
                }
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
        if(userType ==UserTypes.COACH){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select(COACHES.COACHID).
                    from(COACHES)
                    .fetch();
            ArrayList<Map<String,ArrayList<String>>> allCoaches = new ArrayList<>();
            allCoaches.add(new HashMap<>());
            allCoaches.get(0).put("coaches",new ArrayList<>());
            for(Record r: result){
                allCoaches.get(0).get("coaches").add(r.get(COACHES.COACHID));

            }
            return allCoaches;
        }
        if(userType ==UserTypes.REFEREE){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            Result<?> result = create.select(REFEREES.REFEREEID).
                    from(REFEREES)
                    .fetch();
            ArrayList<Map<String,ArrayList<String>>> allReferees = new ArrayList<>();
            allReferees.add(new HashMap<>());
            allReferees.get(0).put("referees",new ArrayList<>());
            for(Record r: result){
                allReferees.get(0).get("referees").add(r.get(REFEREES.REFEREEID));

            }
            return allReferees;
        }
        else{
            System.out.println("invalid select from subscriberDB");
        }
        return null;
    }

    @Override
    public boolean update(Enum<?> e, Map<String, String> arguments) {

        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        if(e == SUBSCRIBERSUPDATES.ADMINSETAPPROVED){
            create.update(ADMINS)
                    .set(ADMINS.APPROVED, Boolean.valueOf(arguments.get("setApproved")))
                    .where(ADMINS.ADMINID.eq(arguments.get("adminID")))
                    .execute();
            return true;
        }
        if(e == SUBSCRIBERSUPDATES.ARSETAPPROVED){
            create.update(ARS)
                    .set(ARS.APPROVED, Boolean.valueOf(arguments.get("setApproved")))
                    .where(ARS.AR_ID.eq(arguments.get("ARID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETTEAMTOPLAYER){
            create.update(PLAYERS)
                    .set(PLAYERS.TEAMID, arguments.get("teamID"))
                    .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETTEAMTOTM){
            create.update(TEAMMANAGERS)
                    .set(TEAMMANAGERS.TEAMID, arguments.get("teamID"))
                    .where(TEAMMANAGERS.MANAGERID.eq(arguments.get("managerID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETPLAYERBIRTHDATE){
            create.update(PLAYERS)
                    .set(PLAYERS.BIRTHDATE, convertToDate(arguments.get("birthDate")))
                    .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETSUBSCRIBERNAME){
            create.update(SUBSCRIBERS)
                    .set(SUBSCRIBERS.NAME, arguments.get("name"))
                    .where(SUBSCRIBERS.SUBSCRIBERID.eq(arguments.get("subscriberID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETPLAYERFIELDJOB){
            create.update(PLAYERS)
                    .set(PLAYERS.FIELDJOB, PlayersFieldjob.valueOf(arguments.get("fieldJob")))
                    .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETPLAYERSALARY){
            create.update(PLAYERS)
                    .set(PLAYERS.SALARY, Integer.parseInt(arguments.get("salary")))
                    .where(PLAYERS.PLAYERID.eq(arguments.get("playerID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETTMPERMISSIONS){
            create.update(TEAMMANAGERS)
                    .set(TEAMMANAGERS.PERMISSIONS, TeammanagersPermissions.valueOf(arguments.get("permissions")))
                    .where(TEAMMANAGERS.MANAGERID.eq(arguments.get("managerID")))
                    .execute();
            return true;
        }
        if(e== SUBSCRIBERSUPDATES.SETTMSALARY){
            create.update(TEAMMANAGERS)
                    .set(TEAMMANAGERS.SALARY, Integer.parseInt(arguments.get("salary")))
                    .where(TEAMMANAGERS.MANAGERID.eq(arguments.get("managerID")))
                    .execute();
            return true;
        }
        if(e==SUBSCRIBERSUPDATES.ADDMANAGERTOOWNER){
            create.insertInto(OWNER_MANAGER_ASSIGNINGS
                    ,OWNER_MANAGER_ASSIGNINGS.OWNERID
                    ,OWNER_MANAGER_ASSIGNINGS.TEAMMANAGERID
                    ,OWNER_MANAGER_ASSIGNINGS.TEAMID)
                    .values(arguments.get("ownerID")
                            ,arguments.get("managersAssigned")
                            ,arguments.get("teamID"))
                    .execute();
            return true;
        }
        if(e==SUBSCRIBERSUPDATES.ADDOWNERTOOWNER){
            create.insertInto(OWNER_OWNER_ASSIGNINGS
                    ,OWNER_OWNER_ASSIGNINGS.OWNERID
                    ,OWNER_OWNER_ASSIGNINGS.ASSIGNEEID
                    ,OWNER_OWNER_ASSIGNINGS.TEAMID)
                    .values(arguments.get("ownerID")
                            ,arguments.get("assigneeID")
                            ,arguments.get("teamID"))
                    .execute();
            return true;
        }
        if(e==SUBSCRIBERSUPDATES.DELETEMANAGERFROMOWNER){
            create.delete(OWNER_MANAGER_ASSIGNINGS)
                    .where(OWNER_MANAGER_ASSIGNINGS.OWNERID.eq(arguments.get("ownerID"))
                            .and(OWNER_MANAGER_ASSIGNINGS.TEAMMANAGERID.eq(arguments.get("managerID")))
                                    .and(OWNER_MANAGER_ASSIGNINGS.TEAMID.eq(arguments.get("teamID"))))
                    .execute();
            return true;
        }
        if(e==SUBSCRIBERSUPDATES.DELETEOWNERFROMOWNER){
            create.delete(OWNER_OWNER_ASSIGNINGS)
                    .where(OWNER_OWNER_ASSIGNINGS.OWNERID.eq(arguments.get("ownerID"))
                    .and(OWNER_OWNER_ASSIGNINGS.ASSIGNEEID.eq(arguments.get("assigneeID")))
                    .and(OWNER_OWNER_ASSIGNINGS.TEAMID.eq(arguments.get("teamID"))))
                    .execute();
            return true;
        }
        if(e==SUBSCRIBERSUPDATES.ADDTEAMTOOWNER){
            create.insertInto(OWNER_TEAMS
                    ,OWNER_TEAMS.OWNERID
                    ,OWNER_TEAMS.TEAMID)
                    .values(arguments.get("ownerID")
                            ,arguments.get("teamID"))
                    .execute();
            return true;
        }
        return false;
    }

    public boolean TerminateDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }

    private LocalDate convertToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
}
