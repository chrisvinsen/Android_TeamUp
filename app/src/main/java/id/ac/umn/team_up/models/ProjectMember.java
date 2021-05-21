package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

public class ProjectMember {
    private String userId;
    private String role;
    private String fullName;
    private String projectId;

    @Nullable
    private boolean hasBeenAccepted;

    public ProjectMember() {
    }

    public ProjectMember(String userId, String role, String fullName, String projectId) {
        this.userId = userId;
        this.role = role;
        this.fullName = fullName;
        this.projectId = projectId;
    }

    public ProjectMember(String userId, String role, String fullName, String projectId, boolean hasBeenAccepted) {
        this.userId = userId;
        this.role = role;
        this.fullName = fullName;
        this.projectId = projectId;
        this.hasBeenAccepted = hasBeenAccepted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public boolean isHasBeenAccepted() {
        return hasBeenAccepted;
    }

    public void setHasBeenAccepted(boolean hasBeenAccepted) {
        this.hasBeenAccepted = hasBeenAccepted;
    }
}
