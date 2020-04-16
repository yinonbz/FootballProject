package serviceLayer;


import businessLayer.Team.TeamController;
import businessLayer.userTypes.SystemController;

public class TeamService {
    private TeamController teamController;
    private SystemController systemController;

    public TeamService() {
        this.systemController = SystemController.SystemController();
        teamController = systemController.getTeamController();
    }

    /**
     * this function add the asset to the chosen team
     *uc-6.1.1
     * @param teamId
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean addAsset(String userOwner,int teamId, String assetType, String assetUserName) {
        return teamController.addAsset(userOwner, teamId,assetType,assetUserName);
    }

    /**
     * this function delete the asset from a team of a team owner
     * @param userOwner
     * @param teamId
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean deleteAsset(String userOwner,int teamId, String assetType, String assetUserName){
        return teamController.removeAsset(userOwner, teamId,assetType,assetUserName);
    }

    public boolean editPlayer(String userOwner,int teamId, String playerUser, String typeEdit, String edit){
        return teamController.editPlayer(userOwner,teamId,playerUser,typeEdit,edit);
    }
    public boolean editCoach(String userOwner,int teamId, String coachUser, String typeEdit, String edit){
        return teamController.editCoach(userOwner,teamId,coachUser,typeEdit,edit);
    }
    public boolean editTeamManager(String userOwner,int teamId, String teamManagerUser, String typeEdit, int edit){
        return teamController.editTeamManager(userOwner,teamId,teamManagerUser,typeEdit,edit);
    }
    public boolean editStadium(String userOwner,int teamId, String editStadiumName, String typeEdit, int edit){
        return teamController.editStadium(userOwner,teamId,editStadiumName,typeEdit,edit);
    }
       /**
     *  UC 6.6
     * @param teamName the team's name
     * @param userName the user's user name who tries to enable the team status
     * @return true if the team status has been enabled
     */
    public Boolean enableTeamStatus(String teamName, String userName){
        return systemController.enableTeamStatus(teamName,userName);
    }


    /**
     *  UC 6.6
     * @param teamName the team's name
     * @param userName the user's user name who tries to disable the team status
     * @return true if the team status has been disabled
     */
    public Boolean disableTeamStatus(String teamName, String userName){
        return systemController.disableTeamStatus(teamName,userName);
    }





}
