package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    @Nullable
    private String recentMessage;
    @Nullable
    private Date sentAt;
    @Nullable
    private Message recentM;
    // private Map<String,String> recentMessage;

    public Project(){
        //empty constructor
    }

    public Project(String id, String adminId, String adminFullname, String adminPicture, String description, String groupIcon, String title, List<String> images, boolean isOngoing, List<String> members, List<String> membersRequest, List<String> toDoList, String createdAt, @Nullable Date endedAt, @Nullable String recentMessage, @Nullable Date sentAt){
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
        this.endedAt = endedAt;
        this.recentMessage = recentMessage;
        this.sentAt = sentAt;
        Long t = Long.parseLong(createdAt);
        this.createdAt = new Date(t*1000);
    }

    public Project(String id, String adminId, String adminFullname, String adminPicture, String description, String groupIcon, String title, List<String> images, boolean isOngoing, List<String> members, List<String> toDoList, String createdAt, @Nullable Date endedAt, @Nullable String recentMessage, @Nullable Date sentAt){
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
        this.endedAt = endedAt;
        this.recentMessage = recentMessage;
        this.sentAt = sentAt;
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

    @Nullable
    public Date getEndedAt() {
        return endedAt;
    }

    @Nullable
    public String getRecentMessage() {
        return recentMessage;
    }

    @Nullable
    public Date getSentAt() {
        return sentAt;
    }

    public void setRecentMessage(@Nullable String recentMessage) {
        this.recentMessage = recentMessage;
    }

    public void setSentAt(@Nullable Date sentAt) {
//        Long t = Long.parseLong(sentAt);
//        this.sentAt = new Date(t*1000);
        this.sentAt = sentAt;
    }

    @Nullable
    public Message getRecentM() {
        return recentM;
    }

    public void setRecentM(@Nullable Message recentM) {
        this.recentM = recentM;
    }
}
