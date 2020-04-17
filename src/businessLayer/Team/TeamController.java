package businessLayer.Team;

import businessLayer.userTypes.Administration.OwnerEligible;
import businessLayer.userTypes.Administration.Permissions;
import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;

import java.security.acl.Owner;

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

    public Boolean addManager(String teamOwner, String username, String permission, String teamName, String salary) {
        if (teamOwner!=null && username != null && teamName != null && salary != null) {
            Subscriber subscriber = systemController.getSubscriberByUserName(teamOwner);
            if (subscriber.tryParseInt(salary)) {
                if (subscriber instanceof TeamOwner) {
                    TeamOwner owner = (TeamOwner) subscriber;
                    return owner.addManager(username, Permissions.valueOf(permission), systemController.getTeamByName(teamName), Integer.parseInt(salary));
                } else if (subscriber instanceof OwnerEligible) {
                    OwnerEligible ownerEligible = (OwnerEligible) subscriber;
                    if (ownerEligible.isOwner()) {
                        TeamOwner owner = ownerEligible.getTeamOwner();
                        return owner.addManager(username, Permissions.valueOf(permission), systemController.getTeamByName(teamName), Integer.parseInt(salary));
                    }
                }
            }
        }
        return false;
    }


}
