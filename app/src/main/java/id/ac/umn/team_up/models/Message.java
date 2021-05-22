package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

public class Message {
    private String fromId;
    private String groupId;
    @Nullable
    private String attachment;
    private String message;
    private FieldValue createdAt;
    private String fullName;
    //private String picURL;

    public Message(String groupId, String fromId, String messages,FieldValue createdAt){
        this.groupId = groupId;
        this.fromId = fromId;
        this.message = messages;
        this.createdAt = createdAt;
    }


    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public FieldValue getCreatedAt() {
        return createdAt;
    }


//    public String getCurrentDate() {
//        return createdAt;
//    }

    public void setCreatedAt(FieldValue createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
