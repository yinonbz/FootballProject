package businessLayer.Tournament;

import serviceLayer.LeagueController;

public interface RankingPolicy {
    String name = null;
    Season season = null;
    League league = null;
    LeagueController leaguesController = null;
}
