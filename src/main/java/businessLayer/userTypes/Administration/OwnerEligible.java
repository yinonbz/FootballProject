package businessLayer.userTypes.Administration;

public interface OwnerEligible {

    /**
     * this function determine if the ownerEli is also an Owner
     * @return true if also an owner, false if only coach
     */
     boolean isOwner();

    /**
     * this function return the fictive TeamOwner of the owner Eligible
     * @return the fictive TeamOwner object of the owner eligible
     */
    TeamOwner getTeamOwner();

    /**
     * this function set the fictive team owner
     */
    void setTeamOwner(TeamOwner teamOwner);

    String getType();


}
