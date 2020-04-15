package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

abstract public class AMatchPolicy implements MatchingPolicy{

    protected HashMap <Integer,Team> teams;
    protected League league;
    protected Season season;
    private HashMap<Integer,Match> matchTable;

    /**
     * the constructor of the policy
     * @param teams the teams
     * @param league the league
     * @param season the season
     */
        AMatchPolicy (HashMap<Integer, Team> teams, League league, Season season){
        this.teams=teams;
        this.league=league;
        this.season=season;
        //simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
    }


    /**
     * schedule the dates to the matches in the season
     * @param matches the matches in array list
     * @param tablesMatch the matches in hash map
     */
    protected HashMap<Integer, Match> scheduleDates (ArrayList<Match> matches, HashMap<Integer,Match> tablesMatch){
        int week = 0;
        int counter=0;
        int howManyGamesLeftForTeam=1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(season.getStartDate());
        for (Match match : matches){
            cal.add(Calendar.DATE,week);
            Date date = cal.getTime();
            match.setDate(date);
            if(counter<teams.size()-howManyGamesLeftForTeam){
                week=week+7;
                counter++;
            }
            else{
                week=0;
                howManyGamesLeftForTeam++;
                counter=0;
                cal.setTime(season.getStartDate());
            }
            tablesMatch.put(match.getMatchId(),match);
        }
        this.matchTable=tablesMatch;
        return tablesMatch;
    }

    /**
     * the function activates the policy to schedule the matches in the season
     * @param teams the teams that participates in the season
     * @param leagueController the league controller
     * @return the table of the matches
     */
    public abstract HashMap <Integer, Match> activatePolicy (HashMap <Integer,Team> teams, LeagueController leagueController);


    @Override
    public String toString(){
       StringBuilder stringBuilder = new StringBuilder();
        if(matchTable!=null){
            for(Match match : matchTable.values()){
                stringBuilder.append(match.toString()+"\n");
            }
        }
        return stringBuilder.toString();
    }


}
