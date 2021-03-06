package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import businessLayer.Tournament.Match.Stadium;

import java.util.ArrayList;
import java.util.HashMap;


public class SingleMatchPolicy extends AMatchPolicy {


    /**
     * the constructor of the policy
     *
     * @param teams  the teams
     * @param league the league
     * @param season the season
     */
    public SingleMatchPolicy(HashMap<String, Team> teams, League league, Season season) {
        super(teams, league, season);
    }

    /**
     * Constructor
     * @param league
     * @param season
     */
    public SingleMatchPolicy(League league, Season season) {
        super(league, season);
    }


    /**
     * the function creates a match table that contains one game between two teams in the entire season
     *
     * @param teams            the teams that participates in the current season
     * @param leagueController the controller that holds the entire stadiums
     * @return the match table
     */
    public HashMap<Integer, Match> activatePolicy(HashMap<String, Team> teams, LeagueController leagueController) {
        HashMap<Integer, Match> gamesTable = new HashMap<>();
        ArrayList<Match> tempMatchTable = new ArrayList<>(); //we wil save here the games before we schedule a date for them
        if (teams != null) {
            ArrayList<Team> listOfTeams = new ArrayList<>(teams.values());
            for (int i = 0; i < listOfTeams.size(); i++) {
                for (int j = i + 1; j < listOfTeams.size(); j++) {
                    Team home = listOfTeams.get(i);
                    Team away = listOfTeams.get(j);
                    Match match;
                    if (!home.equals(away)) {
                        if (home.getStadium() != null) {
                            match = new Match(home, away, home.getStadium());
                        } else {
                            if (away.getStadium() != null) {
                                match = new Match(home, away, away.getStadium());
                            } else {
                                //if no one of the teams has a stadium we will choose one from the controller
                                Stadium stadium = leagueController.getRandomStadium();
                                match = new Match(home, away, stadium);
                            }
                        }
                        tempMatchTable.add(match);
                        //home.getMatches().add(match); //todo need to call the system controller
                        //away.getMatches().add(match); //todo need to call the system controller
                    }
                }
            }
            //after we finish to schedule each game, we want to schedule a date for them
            gamesTable = scheduleDates(tempMatchTable, gamesTable);
        }
        return gamesTable;
    }
}
