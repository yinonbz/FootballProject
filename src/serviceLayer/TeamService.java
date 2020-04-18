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
     *uc-6.1.1
     * @param teamName
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean addAsset(String userOwner,String teamName, String assetType, String assetUserName) {
        return teamController.addAsset(userOwner, teamName,assetType,assetUserName);
    }

    /**
     * this function delete the asset from a team of a team owner
     * @param userOwner
     * @param teamName
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean deleteAsset(String userOwner,String teamName, String assetType, String assetUserName){
        return teamController.removeAsset(userOwner, teamName,assetType,assetUserName);
    }

    public boolean editPlayer(String userOwner,String teamName, String playerUser, String typeEdit, String edit){
        return teamController.editPlayer(userOwner,teamName,playerUser,typeEdit,edit);
    }
    public boolean editCoach(String userOwner,String teamName, String coachUser, String typeEdit, String edit){
        return teamController.editCoach(userOwner,teamName,coachUser,typeEdit,edit);
    }
    public boolean editTeamManager(String userOwner,String teamName, String teamManagerUser, String typeEdit, int edit){
        return teamController.editTeamManager(userOwner,teamName,teamManagerUser,typeEdit,edit);
    }
    public boolean editStadium(String userOwner,String teamName, String editStadiumName, String typeEdit, int edit){
        return teamController.editStadium(userOwner,teamName,editStadiumName,typeEdit,edit);
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

    public boolean fireManager(String ownerUser,String username,String teamName) {
        return teamController.fireManager(ownerUser,username,teamName);
    }

    public int reportExpanse(String teamOwnerUser, String teamName) {
        return teamController.reportExpanse(teamOwnerUser,teamName);
    }

    public int reportIncome(String teamOwnerUser, String teamName) {
        return teamController.reportIncome(teamOwnerUser,teamName);
    }

    public Boolean appoinTeamOwnerToTeam(String teamName, String newUserName, String userName){
        return systemController.appoinTeamOwnerToTeam(teamName, newUserName,userName);
    }



}
