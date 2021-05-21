package id.ac.umn.team_up.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nullable;

public class Project implements Serializable {
    private String id;
    private String adminId;
    private String description;
    private String groupIcon;
    private String title;
    private Timestamp createdAt;
    private boolean isOngoing;
    private List<String> members;

    @Nullable
    private Timestamp endedAt;

    @Nullable
    private String recentMessage;

    @Nullable
    private Timestamp sentAt;

    public Project(){
        //empty constructor
    }

    public Project(String adminId, String description, String groupIcon, String title, Timestamp createdAt, boolean isOngoing, List<String> members) {
        this.adminId = adminId;
        this.description = description;
        this.groupIcon = groupIcon;
        this.title = title;
        this.createdAt = createdAt;
        this.isOngoing = isOngoing;
        this.members = members;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    @Nullable
    public Timestamp getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(@Nullable Timestamp endedAt) {
        this.endedAt = endedAt;
    }

    @Nullable
    public String getRecentMessage() {
        return recentMessage;
    }

    public void setRecentMessage(@Nullable String recentMessage) {
        this.recentMessage = recentMessage;
    }

    @Nullable
    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(@Nullable Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
