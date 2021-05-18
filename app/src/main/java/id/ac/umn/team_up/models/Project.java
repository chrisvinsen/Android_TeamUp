package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {
    private String documentId;
    private String adminId;
    private String description;
    private String groupIcon;
    private String name;
    private Timestamp createdAt;
    private boolean isOngoing;

    private List<String> members;

    // subcollection of messages (including images in the chat) --belum tau bener apa kaga String,String
    @Nullable
    private Map<String, String> messages;

    public Project(){
        //empty constructor
    }

    public Project(String adminId, String description, String groupIcon, String name, Timestamp createdAt, boolean isOngoing, List<String> members) {
        this.adminId = adminId;
        this.description = description;
        this.groupIcon = groupIcon;
        this.name = name;
        this.createdAt = createdAt;
        this.isOngoing = isOngoing;
        this.members = members;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public void setMember(List<String> members) {
        this.members = members;
    }

    public void setProjectName(String projectName) {
        this.name = projectName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Exclude
    public void setDocumentId(String documentId){
        this.documentId = documentId;
    }
    

    public String getAdminId() {
        return adminId;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public List<String> getMembers() {
        return members;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public String getProjectName() {
        return name;
    }

    @Exclude
    public String getDocumentId(){
        return documentId;
    }

    @Override
    public String toString(){
        return getProjectName();
    }
}
