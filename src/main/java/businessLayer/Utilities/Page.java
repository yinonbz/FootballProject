package businessLayer.Utilities;

import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.SystemController;

import java.util.HashSet;
import java.util.Stack;

public class Page {
private HashSet<String> owners;
private Stack<String> posts;
private String name;
private String bDate;
private HasPage pageOwner;
private SystemController systemController;

    /**
     * constractor for player and coach
     * @param username
     * @param name
     * @param birthDate
     */
    public Page(String username, String name, String birthDate, HasPage pageOwner, String ownerName) {
        posts = new Stack<>();
        this.name = name;
        this.bDate = birthDate;
        owners = new HashSet<>();
        owners.add(username);
        systemController = SystemController.SystemController();
        systemController.addPageToDB(ownerName, this);
    }

    /**
     * Constructor - contains HasPage element which holds the owner of the page
     * @param teamOwners
     * @param teamName
     * @param establishedYear
     * @param pageOwner
     */
    public Page(HashSet<TeamOwner> teamOwners, String teamName, int establishedYear, HasPage pageOwner, String ownerName) {
        posts = new Stack<>();
        this.name = teamName;
        this.bDate = String.valueOf(establishedYear);
        owners = new HashSet<>();
        for (TeamOwner teamOwner: teamOwners) {
            owners.add(teamOwner.getUsername());
        }
        systemController = SystemController.SystemController();
        this.pageOwner = pageOwner;
        systemController.addPageToDB(ownerName, this);
    }



    /**
     * The function receives a new event, updates it within the page and causes the page to update its followers about the new event
     * @param post
     * @return
     */
    public boolean update(String post){
        if(post!=null &&post.length()!=0){
            posts.push(post);
            systemController.updatePageFollowers(this, post);
            return true;
        }
        return false;
    }
}
