package id.ac.umn.team_up.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    private String documentId;
    private String adminId;
    private String description;
    private String groupIcon;
    private String name;
    private Timestamp createdAt;
    private boolean isOngoing;
    private int memberCapacity;

    public Project(){
        //empty constructor
    }

    public Project(String adminId, String description, String groupIcon, String name, Timestamp createdAt, boolean isOngoing, int memberCapacity) {
        this.adminId = adminId;
        this.description = description;
        this.groupIcon = groupIcon;
        this.name = name;
        this.createdAt = createdAt;
        this.isOngoing = isOngoing;
        this.memberCapacity = memberCapacity;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public void setMember(List<String> members) {
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

    public int getMemberCapacity() {
        return memberCapacity;
    }

    public void setMemberCapacity(int memberCapacity) {
        this.memberCapacity = memberCapacity;
    }
}
