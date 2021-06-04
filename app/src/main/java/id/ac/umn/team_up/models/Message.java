package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import java.util.Date;

public class Message {
    private String id;
    private String fromId;
    private String projectId;
    @Nullable
    private String attachment;
    private String message;
    private Date createdAt;
    private String fullName;

    //private String picURL;

    public Message(){

    }


    public Message(String id,String projectId, String fromId, String messages,String createdAt){
        this.projectId = projectId;
        this.fromId = fromId;
        this.message = messages;
        Long t = Long.parseLong(createdAt);
        this.createdAt = new Date(t*1000);
        this.id = id;
    }

    public Message(String id,String projectId, String fromId, String messages,String createdAt, @Nullable String attachment){
        this.projectId = projectId;
        this.fromId = fromId;
        this.message = messages;
        Long t = Long.parseLong(createdAt);
        this.createdAt = new Date(t*1000);
        this.id = id;
        this.attachment = attachment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public String getFromId() {
        return fromId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Nullable
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(@Nullable String attachment) {
        this.attachment = attachment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

