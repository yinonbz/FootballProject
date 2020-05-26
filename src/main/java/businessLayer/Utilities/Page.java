package businessLayer.Utilities;

import businessLayer.userTypes.Administration.TeamOwner;
import businessLayer.userTypes.SystemController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Page {
private ArrayList<String> owners;
private LinkedList<String> posts;
private String name;
private String bDate;
private HasPage pageOwner;
private SystemController systemController;
private int pageID;
static int pageCounter=0;

    /**
     * constractor for player and coach
     * @param username
     * @param name
     * @param birthDate
     */
    public Page(String username, String name, String birthDate, HasPage pageOwner) {
        posts = new LinkedList<>();
        this.name = name;
        this.bDate = birthDate;
        owners = new ArrayList<>();
        owners.add(username);
        this.pageOwner = pageOwner;
        pageID=pageCounter;
        pageCounter++;
        systemController = SystemController.SystemController();
    }

    /**
     * Constructor - contains HasPage element which holds the owner of the page
     * @param teamOwners
     * @param teamName
     * @param establishedYear
     * @param pageOwner
     */
    public Page(ArrayList<TeamOwner> teamOwners, String teamName, int establishedYear, HasPage pageOwner, String ownerName) {
        posts = new LinkedList<>();
        this.name = teamName;
        this.bDate = String.valueOf(establishedYear);
        owners = new ArrayList<>();
        for (TeamOwner teamOwner: teamOwners) {
            //owners.add(teamOwner.getUsername());
        }
        systemController = SystemController.SystemController();
        this.pageOwner = pageOwner;
        //systemController.addPageToDB(ownerName, this);
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


    public ArrayList<String> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<String> owners) {
        this.owners = owners;
    }

    public LinkedList<String> getPosts() {
        return posts;
    }

    public void setPosts(LinkedList<String> posts) {
        this.posts = posts;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public HasPage getPageOwner() {
        return pageOwner;
    }

    public void setPageOwner(HasPage pageOwner) {
        this.pageOwner = pageOwner;
    }

}
