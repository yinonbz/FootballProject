package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;
import serviceLayer.LeagueController;

import java.util.HashMap;
import java.util.LinkedList;

public interface MatchingPolicy {

    /*
    String name = null;
    Season season = null;
    League league = null;
    LeagueController leaguesController = null;
    */

    public HashMap <Integer, Match> activatePolicy (HashMap <Integer,Team> teams, LeagueController leagueController);

}
