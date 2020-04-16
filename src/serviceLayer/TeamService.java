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
     *
     * @param teamId
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean addAsset(String userOwner,int teamId, String assetType, String assetUserName) {
        return teamController.addAsset(userOwner, teamId,assetType,assetUserName);
    }

    /**
     *

     public boolean confirmAssetAdd(String userowner,int teamId, String assetType, String assetUserName){
     if(systemController.selectUserFromDB(userowner) instanceof TeamOwner){
     TeamOwner owner = (TeamOwner)systemController.selectUserFromDB(userowner);
     return owner.addAsset(teamId,assetType,assetUserName);
     }
     return false;
     }
     */


}
