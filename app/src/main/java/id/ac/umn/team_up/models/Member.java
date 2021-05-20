package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

public class Member {
    private String userId;
    private String role;

    @Nullable
    private boolean hasBeenAccepted;

    public Member() {
    }

    public Member(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public Member(String userId, String role, boolean hasBeenAccepted) {
        this.userId = userId;
        this.role = role;
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

    public boolean hasBeenAccepted() {
        return hasBeenAccepted;
    }

    public void setHasBeenAccepted(boolean hasBeenAccepted) {
        this.hasBeenAccepted = hasBeenAccepted;
    }
}
