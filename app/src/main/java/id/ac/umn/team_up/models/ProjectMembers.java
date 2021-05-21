package id.ac.umn.team_up.models;

import java.io.Serializable;

public class ProjectMembers implements Serializable {
    private String id, firstName, lastName, profilePicture;

    public ProjectMembers(){
        // Empty Constructor
    }

    public ProjectMembers(String id, String firstName, String lastName, String profilePicture){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
    }

    public String getId(){
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getProfilePicture(){
        return profilePicture;
    }
}
