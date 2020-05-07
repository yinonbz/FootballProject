package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;

import java.util.HashMap;

public interface MatchingPolicy {

    /*
    String name = null;
    Season season = null;
    League league = null;
    LeagueController leaguesController = null;
    */

    public HashMap <Integer, Match> activatePolicy (HashMap <String,Team> teams, LeagueController leagueController);

}
