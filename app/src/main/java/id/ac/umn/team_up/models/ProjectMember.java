package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class ProjectMember implements Serializable {
    private String userId;
    private String role;
    private String fullName;
    private String projectId;
    private String picture;
    private boolean isMember;
    private String projectName;
    private String documentId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Nullable
    private boolean isAdmin;

    public ProjectMember() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectMember(String userId, String role, String fullName, String projectId, String picture, boolean isMember, String projectName) {
        this.userId = userId;
        this.role = role;
        this.fullName = fullName;
        this.projectId = projectId;
        this.picture = picture;
        this.isMember = isMember;
        this.projectName = projectName;
    }

    public ProjectMember(String userId, String role, String fullName, String projectId, String picture, boolean isMember, boolean isAdmin) {
        this.userId = userId;
        this.role = role;
        this.fullName = fullName;
        this.projectId = projectId;
        this.picture = picture;
        this.isMember = isMember;
        this.isAdmin= isAdmin;
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

    public String getPicture(){
        return picture;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
