package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Message {
    private String fromId;

    @Nullable
    private String message;

//    @Nullable
//    private String image;

    private Date sentAt;

    public Message() {
    }

    public Message(String fromId, String messages, Timestamp sentAt){
        this.fromId = fromId;
        this.message = messages;
        this.sentAt = sentAt.toDate();
    }

    public String getFromId() {
        return fromId;
    }

    public String getMessages() {
        return message;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public void setMessages(String messages) {
        this.message = messages;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
