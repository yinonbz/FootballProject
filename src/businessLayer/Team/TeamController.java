package businessLayer.Team;

import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

public class TeamController {
    private SystemController systemController;
    private static TeamController single_instance;

    public TeamController() {
        //systemController = SystemController.SystemController();
    }

    public void setSystemController(SystemController systemController) {
        this.systemController = systemController;
    }

    public static TeamController SystemController() {
        if (single_instance == null) {
            single_instance = new TeamController();
        }
        return single_instance;
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
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if(subscriber instanceof TeamOwner && subscriber!=null){
            TeamOwner teamOwner = (TeamOwner)subscriber;
            return teamOwner.addAsset(teamId,assetType,assetUserName);
        }
        return false;
    }

    public boolean removeAsset(String userOwner,int teamId, String assetType, String assetUserName){
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if(subscriber instanceof TeamOwner && subscriber!=null){
            TeamOwner teamOwner = (TeamOwner)subscriber;
            return teamOwner.deleteAsset(teamId,assetType,assetUserName);
        }
        return false;
    }


    public boolean editPlayer(String userOwner, int teamId, String playerUser, String typeEdit, String edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if(subscriber instanceof TeamOwner && subscriber!=null){
            TeamOwner teamOwner = (TeamOwner)subscriber;
            return teamOwner.editPlayer(teamId,playerUser,typeEdit,edit);
        }
        return false;
    }

    public boolean editCoach(String userOwner, int teamId, String coachUser, String typeEdit, String edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if(subscriber instanceof TeamOwner && subscriber!=null){
            TeamOwner teamOwner = (TeamOwner)subscriber;
            return teamOwner.editCoach(teamId,coachUser,typeEdit,edit);
        }
        return false;
    }

    public boolean editTeamManager(String userOwner, int teamId, String teamManagerUser, String typeEdit, int edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if(subscriber instanceof TeamOwner && subscriber!=null){
            TeamOwner teamOwner = (TeamOwner)subscriber;
            return teamOwner.editTeamManager(teamId,teamManagerUser,typeEdit,edit);
        }
        return false;
    }

    public boolean editStadium(String userOwner, int teamId, String editStadiumName, String typeEdit, int edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if(subscriber instanceof TeamOwner && subscriber!=null){
            TeamOwner teamOwner = (TeamOwner)subscriber;
            return teamOwner.editStadium(teamId,editStadiumName,typeEdit,edit);
        }
        return false;
    }
}
