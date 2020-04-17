package serviceLayer;


import businessLayer.Team.TeamController;
import businessLayer.userTypes.SystemController;
import com.sun.org.apache.xpath.internal.operations.Bool;

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

    /**
     * UC 6.4
     * @param username manager user name
     * @param permission permission granted
     * @param teamName team name
     * @param salary
     * @return action was successful or failed.
     */

    public Boolean addManager(String teamOwner,String username,String permission,String teamName, String salary){
        return teamController.addManager(teamOwner,username,permission,teamName,salary);
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
