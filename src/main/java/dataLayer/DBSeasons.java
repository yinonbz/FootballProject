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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static dataLayer.Tables.Tables.*;

public class DBSeasons implements DB_Inter {
    String username = "root";
    String password = "Messi1Ronaldo2";
    String myDriver = "org.mariadb.jdbc.Driver";
    //String myUrl = "jdbc:mysql://132.72.65.33:3306/SoccerProject";
    String myUrl = "jdbc:mysql://132.72.65.33:3306/demodb";
    Connection connection = null;
    HashMap<String, String> monthsForFormat;


    public DBSeasons(){
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
        monthsForFormat = new HashMap<>();
        monthsForFormat.put("Jan", "01");
        monthsForFormat.put("Feb", "02");
        monthsForFormat.put("Mar", "03");
        monthsForFormat.put("Apr", "04");
        monthsForFormat.put("May", "05");
        monthsForFormat.put("Jun", "06");
        monthsForFormat.put("Jul", "07");
        monthsForFormat.put("Aug", "08");
        monthsForFormat.put("Sep", "09");
        monthsForFormat.put("Oct", "10");
        monthsForFormat.put("Nov", "11");
        monthsForFormat.put("Dec", "12");


    }

    @Override
    public boolean containInDB(String leagueID, String seasonID, String empty1) {
        try {
            return containsInDB(leagueID, Integer.parseInt(seasonID));
        } catch (Exception e) {
            System.out.println("error searching season in DB");
            return false;
        }
    }

    @Override
    public Map<String, ArrayList<String>> selectFromDB(String leagueID, String seasonID, String arg3) {
        try {
            return selectFromDB(leagueID, Integer.parseInt(seasonID));
        } catch (Exception e) {
            System.out.println("error selecting season in DB");
            return new HashMap<>();
        }
    }

    public boolean containsInDB(String leagueID, int seasonID) {
        DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
        Result<?> result = create.select().from(SEASONS).where(SEASONS.LEAGUEID.eq(leagueID).
                and(SEASONS.SEASONID.eq(seasonID))).fetch();
        return (!result.isEmpty());
    }


    @Override
    public boolean removeFromDB(String leagueID, String seasonID, String arg3) {
        try {
            return removeFromDB(leagueID, Integer.parseInt(seasonID));
        } catch (Exception e) {
            System.out.println("error removing season in DB");
            return false;
        }
    }

    @Override
    public boolean addToDB(String leagueID, String seasonID, String startDate, String endDate, Map<String, ArrayList<String>> objDetails) {
        try {
            return addSeasonToDB(leagueID, Integer.parseInt(seasonID), convertToDate(startDate), convertToDate(endDate), objDetails);
        } catch (Exception e) {
            System.out.println("error adding season in DB");
            return false;
        }
    }

    @Override
    public int countRecords() {
        return 0;
    }

    @Override
    public ArrayList<Map<String, ArrayList<String>>> selectAllRecords(Enum<?> e, Map<String, String> arguments) {
        if (e == SEASONENUM.ALLSEASON) {
            try {
                    return selectAllSeasonOfLeague(SEASONENUM.ALLSEASON,arguments);
                } catch (Exception e1) {
                    System.out.println("error getting all seasons in DB");
                    return new ArrayList<>();
                }
            }
            else if(e==SEASONENUM.REFSOFSEASON){
                try {
                    getAllRefsOfSeason(arguments.get("leagueID"),arguments.get("seasonID"));
                } catch (Exception e1) {
                    System.out.println("error getting all referees of seasons in DB");
                    return new ArrayList<>();
                }
        }
        return null;
    }

    private ArrayList<Map<String, ArrayList<String>>> getAllRefsOfSeason(String leagueID, String seasonID) {
        try {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select(SEASON_REFEREE.REFEREEID).from(SEASON_REFEREE).
                        where(SEASON_REFEREE.LEAGUEID.eq(leagueID)).and(SEASON_REFEREE.SEASONID.eq(Integer.parseInt(seasonID))).
                        fetch();
                ArrayList<Map<String, ArrayList<String>>> details = new ArrayList<>();
                for (Record record : result) {
                    HashMap<String, ArrayList<String>> seasonDetails = new HashMap<>();
                    ArrayList<String> temp = new ArrayList<>();
                    String refID = record.get(SEASON_REFEREE.LEAGUEID);
                    temp.add(refID);
                    seasonDetails.put(refID, temp);
                    details.add(seasonDetails);
                }
                return details;
            } catch (DataAccessException e) {
                System.out.println("error getting all referees of seasons in DB");
                return new ArrayList<>();
            } catch (IllegalArgumentException e) {
                System.out.println("error getting all referees of seasons in DB");
                return new ArrayList<>();
            }
        }


        private ArrayList<Map<String, ArrayList<String>>> selectAllSeasonOfLeague (Enum < ? > e ,Map<String,String> arguments){
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            String leagueID = arguments.get("leagueID");
            Result<?> result = create.select(SEASONS.SEASONID).from(SEASONS).where(SEASONS.LEAGUEID.eq(leagueID)).fetch();
            ArrayList<Map<String, ArrayList<String>>> details = new ArrayList<>();
            for (Record record : result) {
                HashMap<String, ArrayList<String>> seasonDetails = new HashMap<>();
                ArrayList<String> temp = new ArrayList<>();
                String seasonID = record.get(SEASONS.SEASONID).toString();
                //String leagueID = record.get(SEASONS.LEAGUEID);
                temp.add(seasonID);
                seasonDetails.put(seasonID, temp);
                details.add(seasonDetails);
            }
            return details;
        }

    @Override
    public boolean update(Enum<?> e, Map<String, String> args) {
        if (e == SEASONENUM.REFEREE) {
            try {
                    addRefereeInSeason(args.get("leagueID"), Integer.parseInt(args.get("seasonID")), args.get("refID"));
                    return true;
                } catch (Exception e1) {
                    System.out.println("error adding referees to season");
                    return false;
                }
            } else if (e == SEASONENUM.MATCHESTABLE) {
                try {
                    LinkedList<Integer> matchID = new LinkedList<>();
                    for (String key : args.keySet()) {
                        if (tryParseInt(key)) {
                            matchID.add(Integer.parseInt(key));
                        }
                    }
                    addMatchTableToSeason(args.get("leagueID"), Integer.parseInt(args.get("seasonID")), matchID);
                    return true;
                } catch (Exception e1) {
                    System.out.println("error adding match table to season");
                    return false;
                }
            } else if (e == SEASONENUM.SEASONUPDATED) {
                try {
                    LinkedList<Integer> details = new LinkedList<>();
                    details.add(Integer.parseInt(args.get("numOfGames")));
                    details.add(Integer.parseInt(args.get("goalsFor")));
                    details.add(Integer.parseInt(args.get("goalAgainst")));
                    details.add(Integer.parseInt(args.get("points")));
                    updateSeasonTable(args.get("leagueID"), Integer.parseInt(args.get("seasonID")), args.get("teamID"), details);
                    return true;
                } catch (Exception e1) {
                    System.out.println("error update season table");
                    return false;
                }
            } else if (e == SEASONENUM.TEAM) {
                try {
                    addTeamInSeason(args.get("leagueID"),Integer.parseInt(args.get("seasonID")), args.get("teamID"));
                    return true;
                } catch (Exception e1) {
                    System.out.println("error adding team to season");
                    return false;
                }
        }
        return false;
    }

    @Override
    public boolean TerminateDB() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("error closing connection of DB");
            return false;
        }

        return true;
    }

        public HashMap<String, ArrayList<String>> selectFromDB (String leagueID,int seasonID){
            HashMap<String, ArrayList<String>> allDetails = new HashMap<>();
            if (containsInDB(leagueID, seasonID)) {
                ArrayList<String> matches = new ArrayList<>();
                ArrayList<String> referee = new ArrayList<>();
                ArrayList<String> teams = new ArrayList<>();
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                Result<?> result = create.select().from(SEASON_MATCHES).where(SEASON_MATCHES.LEAGUEID.eq(leagueID).
                        and(SEASON_MATCHES.SEASONID.eq(seasonID))).fetch();
                for (Record r : result) {
                    matches.add(r.get(SEASON_MATCHES.MATCHID).toString());
                }
                allDetails.put("matches", matches);
                result = create.select().from(SEASON_REFEREE).where(SEASON_REFEREE.LEAGUEID.eq(leagueID).
                        and(SEASON_REFEREE.SEASONID.eq(seasonID))).fetch();
                for (Record r : result) {
                    referee.add(r.get(SEASON_REFEREE.REFEREEID));
                }
                allDetails.put("referees", referee);
                result = create.select().from(SEASON_TEAMS).where(SEASON_TEAMS.LEAGUEID.eq(leagueID).
                        and(SEASON_TEAMS.SEASONID.eq(seasonID))).fetch();
                for (Record r : result) {
                    teams.add(r.get(SEASON_TEAMS.TEAMID));
                }
                allDetails.put("teams", teams);
                result = create.select().from(SEASON_TABLELEAGUE).where(SEASON_TABLELEAGUE.LEAGUEID.eq(leagueID).
                        and(SEASON_TABLELEAGUE.SEASONID.eq(seasonID))).fetch();
                ArrayList<String> table = new ArrayList<>();
                for (Record r : result) {
                    table.add(r.get(SEASON_TABLELEAGUE.TEAMID));
                    table.add(r.get(SEASON_TABLELEAGUE.NUMOFGAMES).toString());
                    table.add(r.get(SEASON_TABLELEAGUE.GOALSFOR).toString());
                    table.add(r.get(SEASON_TABLELEAGUE.GOALSAGAINST).toString());
                    table.add(r.get(SEASON_TABLELEAGUE.POINTS).toString());
                }
                allDetails.put("table", table);
                ArrayList<String> rankingPolicy = new ArrayList<>();
                result = create.select().from(RANKINGPOLICY).where(RANKINGPOLICY.LEAGUEID.eq(leagueID).
                        and(RANKINGPOLICY.SEASONID.eq(seasonID))).fetch();
                for (Record r : result) {
                    rankingPolicy.add(r.get(RANKINGPOLICY.LEAGUEID));
                    rankingPolicy.add(r.get(RANKINGPOLICY.SEASONID).toString());
                    rankingPolicy.add(r.get(RANKINGPOLICY.WIN).toString());
                    rankingPolicy.add(r.get(RANKINGPOLICY.LOSE).toString());
                    rankingPolicy.add(r.get(RANKINGPOLICY.TIE).toString());
                }
                allDetails.put("rankingPolicy", rankingPolicy);
                result = create.select().from(MATCHING_POLICY).where(MATCHING_POLICY.LEAGUEID.eq(leagueID).
                        and(MATCHING_POLICY.SEASONID.eq(seasonID))).fetch();
                ArrayList<String> matchingPolicy = new ArrayList<>();
                for (Record r : result) {
                    matchingPolicy.add(r.get(MATCHING_POLICY.TYPE));
                }
                allDetails.put("matchingPolicy", matchingPolicy);
            }
            return allDetails;
        }


        public boolean removeFromDB (String leagueID,int seasonID){
            if (containsInDB(leagueID, seasonID)) {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                create.delete(SEASONS)
                        .where(SEASONS.LEAGUEID.eq(leagueID)).and(SEASONS.SEASONID.eq(seasonID)).execute();
                return true;
            }
            return false;
        }

        public boolean addSeasonToDB (String leagueID,int seasonID, LocalDate start, LocalDate
        end, Map < String, ArrayList < String >> details){
            if (!containsInDB(leagueID, seasonID)) {
                DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
                create.insertInto(SEASONS,
                        SEASONS.LEAGUEID,
                        SEASONS.SEASONID,
                        SEASONS.STARTDATE,
                        SEASONS.ENDDATE).values(leagueID, seasonID,start,end).execute();
                //create.insertInto(SEASONS,SEASONS.SEASONID).values(seasonID).execute();
                /*create.insertInto(SEASONS, SEASONS.STARTDATE)
                        .values(start)
                        .execute();
                create.insertInto(SEASONS, SEASONS.ENDDATE)
                        .values(end)
                        .execute();*/
            ArrayList<String> teams = details.get("teams");
            if (teams != null) {
                for (String team : teams) {
                    create.insertInto(SEASON_TEAMS, SEASON_TEAMS.LEAGUEID, SEASON_TEAMS.SEASONID, SEASON_TEAMS.TEAMID).values(leagueID, seasonID, team).execute();
                }
            }
            ArrayList<String> referees = details.get("referees");
            if (referees != null) {
                for (String referee : referees) {
                    create.insertInto(SEASON_REFEREE, SEASON_REFEREE.LEAGUEID, SEASON_REFEREE.SEASONID, SEASON_REFEREE.REFEREEID).values(leagueID, seasonID, referee).execute();
                }
            }
            ArrayList<String> matches = details.get("matches");
            if (matches != null) {
                for (String match : matches) {
                    int matchInt = Integer.parseInt(match);
                    create.insertInto(SEASON_MATCHES, SEASON_MATCHES.LEAGUEID, SEASON_MATCHES.SEASONID, SEASON_MATCHES.MATCHID).values(leagueID, seasonID, matchInt).execute();
                }
            }
            ArrayList<String> tables = details.get("table");
            if (tables != null) {
                for (int i = 0; i < tables.size(); i = i + 5) {
                    String team = tables.get(i);
                    int numOFGames = Integer.parseInt(tables.get(i + 1));
                    int goalsFor = Integer.parseInt(tables.get(i + 2));
                    int goalsAgainst = Integer.parseInt(tables.get(i + 3));
                    int points = Integer.parseInt(tables.get(i + 4));
                    create.insertInto(SEASON_TABLELEAGUE, SEASON_TABLELEAGUE.LEAGUEID, SEASON_TABLELEAGUE.SEASONID, SEASON_TABLELEAGUE.TEAMID,
                            SEASON_TABLELEAGUE.NUMOFGAMES, SEASON_TABLELEAGUE.GOALSFOR, SEASON_TABLELEAGUE.GOALSAGAINST, SEASON_TABLELEAGUE.POINTS)
                            .values(leagueID, seasonID, team, numOFGames, goalsFor, goalsAgainst, points).execute();
                }
            }
            ArrayList<String> rankingPolicy = details.get("rankingPolicy");
            int win = Integer.parseInt(rankingPolicy.get(0));
            int lose = Integer.parseInt(rankingPolicy.get(1));
            int tie = Integer.parseInt(rankingPolicy.get(2));
            create.insertInto(RANKINGPOLICY, RANKINGPOLICY.LEAGUEID, RANKINGPOLICY.SEASONID, RANKINGPOLICY.WIN,
                    RANKINGPOLICY.LOSE, RANKINGPOLICY.TIE).values(leagueID, seasonID, win, lose, tie).execute();

            ArrayList<String> matchingPolicy = details.get("matchingPolicy");
            if (matchingPolicy != null) {
                String type = matchingPolicy.get(0);
                create.insertInto(MATCHING_POLICY, MATCHING_POLICY.LEAGUEID, MATCHING_POLICY.SEASONID, MATCHING_POLICY.TYPE).values(leagueID, seasonID, type).execute();
            }
            return true;
        }
        return false;
    }

    public boolean addRefereeInSeason(String leagueID, int seasonID, String referee) {
        if (containsInDB(leagueID, seasonID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(SEASON_REFEREE, SEASON_REFEREE.LEAGUEID, SEASON_REFEREE.SEASONID,
                    SEASON_REFEREE.REFEREEID).values(leagueID, seasonID, referee).execute();
            return true;
        }
        return false;
    }

    public boolean addTeamInSeason(String leagueID, int seasonID, String teamID) {
        if (containsInDB(leagueID, seasonID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.insertInto(SEASON_TEAMS, SEASON_TEAMS.LEAGUEID, SEASON_TEAMS.SEASONID,
                    SEASON_TEAMS.TEAMID).values(leagueID, seasonID, teamID).execute();
            create.insertInto(SEASON_TABLELEAGUE, SEASON_TABLELEAGUE.LEAGUEID, SEASON_TABLELEAGUE.SEASONID, SEASON_TABLELEAGUE.TEAMID, SEASON_TABLELEAGUE.NUMOFGAMES,
                    SEASON_TABLELEAGUE.GOALSFOR, SEASON_TABLELEAGUE.GOALSAGAINST, SEASON_TABLELEAGUE.POINTS).values(leagueID, seasonID, teamID, 0, 0, 0, 0).execute();
            return true;
        }
        return false;
    }

    public boolean addMatchTableToSeason(String leagueID, int seasonID, LinkedList<Integer> matchNum) {
        if (containsInDB(leagueID, seasonID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            if (matchNum != null) {
                for (int i = 0; i < matchNum.size(); i++) {
                    create.insertInto(SEASON_MATCHES, SEASON_MATCHES.LEAGUEID, SEASON_MATCHES.SEASONID,
                            SEASON_MATCHES.MATCHID).values(leagueID, seasonID, matchNum.get(i)).execute();
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateSeasonTable(String leagueID, int seasonID, String teamID, LinkedList<Integer> info) {
        if (containsInDB(leagueID, seasonID)) {
            DSLContext create = DSL.using(connection, SQLDialect.MARIADB);
            create.update(SEASON_TABLELEAGUE).set(SEASON_TABLELEAGUE.NUMOFGAMES, info.get(0)).
                    set(SEASON_TABLELEAGUE.GOALSFOR, info.get(1))
                    .set(SEASON_TABLELEAGUE.GOALSAGAINST, info.get(2)).set(SEASON_TABLELEAGUE.POINTS, info.get(3))
                    .where(SEASON_TABLELEAGUE.LEAGUEID.eq(leagueID).and(SEASON_TABLELEAGUE.SEASONID.eq(seasonID).and
                            (SEASON_TABLELEAGUE.TEAMID.eq(teamID)))).execute();
            return true;
        }
        return false;
    }


    private LocalDate convertToDate (String date){
        String [] arr = date.split(" ");
        String temp = arr[5]+"-"+monthsForFormat.get(arr[1])+"-"+arr[2] + " " + arr[3].substring(0,5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(temp, formatter);
        return localDate;
    }

    protected boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

