package businessLayer.userTypes.Administration;

public interface OwnerEligible {

    /**
     * this function determine if the ownerEli is also an Owner
     * @return true if also an owner, false if only coach
     */
     boolean isOwner();
     TeamOwner getTeamOwner();

}
