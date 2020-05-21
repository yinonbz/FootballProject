package businessLayer.Team;

import businessLayer.Exceptions.MissingInputException;
import businessLayer.Exceptions.NotApprovedException;
import businessLayer.userTypes.Administration.OwnerEligible;
import businessLayer.userTypes.Administration.Permissions;
import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.Subscriber;
import businessLayer.userTypes.SystemController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;


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
     * @param teamName
     * @param assetType
     * @param assetUserName
     * @return
     */
    public boolean addAsset(String userOwner, String teamName, String assetType, String assetUserName) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if (subscriber instanceof TeamOwner && subscriber != null) {
            TeamOwner teamOwner = (TeamOwner) subscriber;
            return teamOwner.addAsset(teamName, assetType, assetUserName);
        } else if (subscriber instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) subscriber;
            if (ownerEligible.isOwner()) {
                TeamOwner owner = ownerEligible.getTeamOwner();
                return owner.addAsset(teamName, assetType, assetUserName);
            }
        }
        return false;
    }

    public boolean removeAsset(String userOwner, String teamName, String assetType, String assetUserName) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if (subscriber instanceof TeamOwner && subscriber != null) {
            TeamOwner teamOwner = (TeamOwner) subscriber;
            return teamOwner.deleteAsset(teamName, assetType, assetUserName);
        } else if (subscriber instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) subscriber;
            if (ownerEligible.isOwner()) {
                TeamOwner owner = ownerEligible.getTeamOwner();
                return owner.deleteAsset(teamName, assetType, assetUserName);
            }
        }
        return false;
    }


    public boolean editPlayer(String userOwner, String teamName, String playerUser, String typeEdit, String edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if (subscriber instanceof TeamOwner && subscriber != null) {
            TeamOwner teamOwner = (TeamOwner) subscriber;
            return teamOwner.editPlayer(teamName, playerUser, typeEdit, edit);
        } else if (subscriber instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) subscriber;
            if (ownerEligible.isOwner()) {
                TeamOwner owner = ownerEligible.getTeamOwner();
                return owner.editPlayer(teamName, playerUser, typeEdit, edit);
            }
        }
        return false;
    }

    public boolean editCoach(String userOwner, String teamName, String coachUser, String typeEdit, String edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if (subscriber instanceof TeamOwner && subscriber != null) {
            TeamOwner teamOwner = (TeamOwner) subscriber;
            return teamOwner.editCoach(teamName, coachUser, typeEdit, edit);
        } else if (subscriber instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) subscriber;
            if (ownerEligible.isOwner()) {
                TeamOwner owner = ownerEligible.getTeamOwner();
                return owner.editCoach(teamName, coachUser, typeEdit, edit);
            }
        }
        return false;
    }

    public boolean editTeamManager(String userOwner, String teamName, String teamManagerUser, String typeEdit, int edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if (subscriber instanceof TeamOwner && subscriber != null) {
            TeamOwner teamOwner = (TeamOwner) subscriber;
            return teamOwner.editTeamManager(teamName, teamManagerUser, typeEdit, edit);
        } else if (subscriber instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) subscriber;
            if (ownerEligible.isOwner()) {
                TeamOwner owner = ownerEligible.getTeamOwner();
                return owner.editTeamManager(teamName, teamManagerUser, typeEdit, edit);
            }
        }
        return false;
    }

    public Boolean addManager(String teamOwner, String username, String permission, String teamName, String salary) {
        if (teamOwner != null && username != null && teamName != null && salary != null) {
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
                throw new NotApprovedException("The selected user must be a Team Owner or a Team Manager.");
            }
        }
       throw new MissingInputException("Please fill the form completely before adding a new Team Manager.");
    }

    public boolean fireManager(String ownerUser, String username, String teamName) {
        if (ownerUser != null && username != null && teamName != null) {
            Subscriber subscriber = systemController.getSubscriberByUserName(ownerUser);
            if (subscriber instanceof TeamOwner) {
                TeamOwner owner = (TeamOwner) subscriber;
                return owner.fireManager(username, systemController.getTeamByName(teamName));
            } else if (subscriber instanceof OwnerEligible) {
                OwnerEligible ownerEligible = (OwnerEligible) subscriber;
                if (ownerEligible.isOwner()) {
                    TeamOwner owner = ownerEligible.getTeamOwner();
                    return owner.fireManager(username, systemController.getTeamByName(teamName));
                }
            }
        }
        return false;
    }

    public boolean editStadium(String userOwner, String teamName, String editStadiumName, String typeEdit, int edit) {
        Subscriber subscriber = systemController.getSubscriberByUserName(userOwner);
        if (subscriber instanceof TeamOwner && subscriber != null) {
            TeamOwner teamOwner = (TeamOwner) subscriber;
            return teamOwner.editStadium(teamName, editStadiumName, typeEdit, edit);
        } else if (subscriber instanceof OwnerEligible) {
            OwnerEligible ownerEligible = (OwnerEligible) subscriber;
            if (ownerEligible.isOwner()) {
                TeamOwner owner = ownerEligible.getTeamOwner();
                return owner.editStadium(teamName, editStadiumName, typeEdit, edit);
            }
        }
        return false;
    }

    public int reportExpanse(String teamOwnerUser, String teamName) {
        if (teamOwnerUser != null && teamName != null) {
            Subscriber subscriber = systemController.getSubscriberByUserName(teamOwnerUser);
            if (subscriber != null && subscriber instanceof TeamOwner) {
                TeamOwner teamOwner = (TeamOwner) subscriber;
                return teamOwner.reportExpanse(teamName);
            } else if (subscriber instanceof OwnerEligible) {
                OwnerEligible ownerEligible = (OwnerEligible) subscriber;
                if (ownerEligible.isOwner()) {
                    TeamOwner owner = ownerEligible.getTeamOwner();
                    return owner.reportExpanse(teamName);
                }
            }
        }
        return -1;
    }

    public int reportIncome(String teamOwnerUser, String teamName) {
        if (teamOwnerUser != null && teamName != null) {
            Subscriber subscriber = systemController.getSubscriberByUserName(teamOwnerUser);
            if (subscriber != null && subscriber instanceof TeamOwner) {
                TeamOwner teamOwner = (TeamOwner) subscriber;
                return teamOwner.reportIncome(teamName);
            } else if (subscriber instanceof OwnerEligible) {
                OwnerEligible ownerEligible = (OwnerEligible) subscriber;
                if (ownerEligible.isOwner()) {
                    TeamOwner owner = ownerEligible.getTeamOwner();
                    return owner.reportIncome(teamName);
                }
            }
        }
        return -1;
    }


}
