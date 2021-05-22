package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {
    private String id, adminId, adminFullname, adminPicture, description, groupIcon, title;
    private boolean isOngoing;
    private List<String> images;
    private List<String> members;
    private List<String> membersRequest;
    private List<String> toDoList;
    private Date createdAt;
    @Nullable
    private Date endedAt;

    // private Map<String,String> recentMessage;

    public Project(){
        //empty constructor
    }

    public Project(String id, String adminId, String adminFullname, String adminPicture, String description, String groupIcon, String title, List<String> images, boolean isOngoing, List<String> members, List<String> membersRequest, List<String> toDoList, String createdAt){
        this.id = id;
        this.adminId = adminId;
        this.adminFullname = adminFullname;
        this.adminPicture = adminPicture;
        this.description = description;
        this.groupIcon = groupIcon;
        this.title = title;
        this.images = images;
        this.members = members;
        this.membersRequest = membersRequest;
        this.toDoList = toDoList;
        this.isOngoing = isOngoing;
        Long t = Long.parseLong(createdAt);
        this.createdAt = new Date(t*1000);
    }

    public Project(String id, String adminId, String adminFullname, String adminPicture, String description, String groupIcon, String title, List<String> images, boolean isOngoing, List<String> members, List<String> toDoList, String createdAt){
        this.id = id;
        this.adminId = adminId;
        this.adminFullname = adminFullname;
        this.adminPicture = adminPicture;
        this.description = description;
        this.groupIcon = groupIcon;
        this.title = title;
        this.images = images;
        this.members = members;
        this.toDoList = toDoList;
        this.isOngoing = isOngoing;
        Long t = Long.parseLong(createdAt);
        this.createdAt = new Date(t*1000);
    }

    // Get method
    public String getId(){
        return id;
    }
    public String getAdminId(){
        return adminId;
    }
    public String getAdminFullname(){
        return adminFullname;
    }
    public String getAdminPicture(){
        return adminPicture;
    }
    public String getDescription(){
        return description;
    }
    public String getGroupIcon(){
        return groupIcon;
    }
    public String getTitle(){
        return title;
    }
    public boolean getIsOngoing(){
        return isOngoing;
    }
    public List<String> getImages(){
        return images;
    }
    public List<String> getMembers(){
        return members;
    }
    public List<String> getMembersRequest(){
        return membersRequest;
    }
    public List<String> getToDoList(){
        return toDoList;
    }
    public Date getCreatedAt(){
        return createdAt;
    }
    public void addMemberRequest(String id){
        if(membersRequest == null){
            membersRequest = new ArrayList<String>();
            membersRequest.add(id);
        }
        else{
            membersRequest.add(id);
        }
    }
}
