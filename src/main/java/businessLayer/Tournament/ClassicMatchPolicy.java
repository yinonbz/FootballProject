package businessLayer.Tournament;


import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassicMatchPolicy extends AMatchPolicy {

    /**
     * the constructor of the policy
     *
     * @param teams  the teams
     * @param league the league
     * @param season the season
     */
    public ClassicMatchPolicy(HashMap<String, Team> teams, League league, Season season) {
        super(teams, league, season);
    }

    /**
     * Constructor
     * @param league
     * @param season
     */
    public ClassicMatchPolicy(League league, Season season) {
        super(league, season);
    }

    /**
     * the function creates a table that has two games between two teams, each one of them is being hosted by the other team
     *
     * @param teams            the teams that participates in the season
     * @param leagueController the league controller
     * @return
     */
    public HashMap<Integer, Match> activatePolicy(HashMap<String, Team> teams, LeagueController leagueController) {
        HashMap<Integer, Match> gamesTable = new HashMap<>();
        ArrayList<Match> tempMatchTable = new ArrayList<>(); //we wil save here the games before we schedule a date for them
        if (teams != null) {
            ArrayList<Team> listOfTeams = new ArrayList<>(teams.values());
            for (int i = 0; i < listOfTeams.size(); i++) {
                for (int j = 0; j < listOfTeams.size(); j++) {
                    Team home = listOfTeams.get(i);
                    Team away = listOfTeams.get(j);
                    if (!home.equals(away)) {
                        Match match;
                        if (home.getStadium() != null) {
                            match = new Match(home, away, home.getStadium());
                        } else {
                            //if the home team can't host the game, it will be played in a random stadium
                            match = new Match(home, away, leagueController.getRandomStadium());
                        }
                        tempMatchTable.add(match);
                        //away.getMatches().add(match); //todo need to call the system controller
                        //home.getMatches().add(match); //todo need to call the system controller
                    }
                }
            }
            scheduleDates(tempMatchTable, gamesTable);
        }
        return gamesTable;
    }

}
