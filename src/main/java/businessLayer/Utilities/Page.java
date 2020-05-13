package businessLayer.Utilities;

import businessLayer.userTypes.Administration.TeamOwner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Page {
private HashSet<String> owners;
private Stack<String> posts;
private String name;
private String bDate;

    /**
     * constractor for player and coach
     * @param  username
     * @param name
     * @param birthDate
     */
    public Page(String username, String name, String birthDate) {
        posts = new Stack<>();
        this.name = name;
        this.bDate = birthDate;
        owners = new HashSet<>();
        owners.add(username);
    }

    /**
     * costractor for team
     * @param teamOwners
     * @param teamName
     * @param establishedYear
     */
    public Page(HashSet<TeamOwner> teamOwners, String teamName, int establishedYear) {
        posts = new Stack<>();
        this.name = teamName;
        this.bDate = String.valueOf(establishedYear);
        owners = new HashSet<>();
        for (TeamOwner teamOwner: teamOwners) {
        owners.add(teamOwner.getUsername());
        }
    }
    public boolean update(String post){
        if(post!=null &&post.length()!=0){
            posts.push(post);
            return true;
        }
        return false;
    }
}
