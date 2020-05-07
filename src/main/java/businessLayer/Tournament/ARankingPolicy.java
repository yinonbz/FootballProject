package businessLayer.Tournament;


//will be implemented in the future

import businessLayer.Team.Team;
import businessLayer.Tournament.Match.Match;

import java.util.HashMap;
import java.util.LinkedList;

public class ARankingPolicy implements RankingPolicy {

    private int win;
    private int lose;
    private int tie;

    /**
     * constructor
     * @param win
     * @param lose
     * @param tie
     */
    public ARankingPolicy (int win, int lose, int tie){
        this.win = win;
        this.lose = lose;
        this.tie = tie;
    }

    /**
     * this function activates the new ranking after a game
     * @param match the match we update on
     * @param rankTable the ranking table of the season
     * @return true if it was updates successfully
     */
    //the table structure - team name |numOfGames| num-goalsOfTeam|num-goals against team|points
    public boolean updateRank(Match match, HashMap<Team, LinkedList<Integer>> rankTable){
        if(match!=null && rankTable!=null){
            Team home = match.getHomeTeam();
            Team away = match.getAwayTeam();
            int [] score = match.getScore();
            int pointsHome;
            int pointsAway;
            if(score[0]<score[1]){
                pointsHome=lose;
                pointsAway=win;
            }
            else if(score[0]>score[1]){
                pointsHome=win;
                pointsAway=lose;
            }
            else{
                pointsAway=tie;
                pointsHome=tie;
            }
            updateTeamInfo(home,score[0],score[1],pointsHome,rankTable);
            updateTeamInfo(away,score[1],score[0],pointsAway,rankTable);
            return true;
        }
        return false;
    }

    /**
     * private method that updates the new information based on the last game
     * @param team the team we update
     * @param goalsFor number of goals of the team in the game
     * @param goalsAgainst number of goals against the team in the game
     * @param points the points from the game
     * @param rankTable the total rank table of the season
     */
    private void updateTeamInfo (Team team, int goalsFor, int goalsAgainst, int points,
                                 HashMap<Team, LinkedList<Integer>> rankTable){
        LinkedList<Integer> newInfo = new LinkedList<>();
        LinkedList<Integer> oldInfo = rankTable.get(team);
        int numOfGames = oldInfo.get(0) + 1;
        int numOfGoalsFor = oldInfo.get(1) + goalsFor;
        int numOfGoalAgainst = oldInfo.get(2) + goalsAgainst;
        int totalPoints = oldInfo.get(3) + points;
        newInfo.add(numOfGames);
        newInfo.add(numOfGoalsFor);
        newInfo.add(numOfGoalAgainst);
        newInfo.add(totalPoints);
        rankTable.remove(team);
        rankTable.put(team,newInfo);
    }


}
