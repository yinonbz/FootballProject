package businessLayer.Tournament;

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;

import java.util.HashMap;
import java.util.LinkedList;

public interface RankingPolicy {

    public boolean updateRank (Match match, HashMap<Team, LinkedList<Integer>> rankTable);
}
