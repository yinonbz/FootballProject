package businessLayer.Tournament.Match;

import businessLayer.Team.Team;

public class Stadium {
    private String name;
    private Match currentMatch;
    private Team currentPlayingTeam1;
    private Team currentPlayingTeam2;

    /**
     * @param name
     * @param currentMatch
     * @param currentPlayingTeam1
     * @param currentPlayingTeam2
     */
    public Stadium(String name, Match currentMatch, Team currentPlayingTeam1, Team currentPlayingTeam2) {
        this.name = name;
        this.currentMatch = currentMatch;
        this.currentPlayingTeam1 = currentPlayingTeam1;
        this.currentPlayingTeam2 = currentPlayingTeam2;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public Match getCurrentMatch() {
        return currentMatch;
    }

    /**
     * @return
     */
    public Team getCurrentPlayingTeam1() {
        return currentPlayingTeam1;
    }

    /**
     * @return
     */
    public Team getCurrentPlayingTeam2() {
        return currentPlayingTeam2;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param currentMatch
     */
    public void setCurrentMatch(Match currentMatch) {
        this.currentMatch = currentMatch;
    }

    /**
     * @param currentPlayingTeam1
     */
    public void setCurrentPlayingTeam1(Team currentPlayingTeam1) {
        this.currentPlayingTeam1 = currentPlayingTeam1;
    }

    /**
     * @param currentPlayingTeam2
     */
    public void setCurrentPlayingTeam2(Team currentPlayingTeam2) {
        this.currentPlayingTeam2 = currentPlayingTeam2;
    }
}
