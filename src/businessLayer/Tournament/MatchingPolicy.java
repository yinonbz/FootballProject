package businessLayer.Tournament;

import serviceLayer.LeagueController;

public interface MatchingPolicy {
    String name = null;
    Season season = null;
    League league = null;
    LeagueController leaguesController = null;
}
