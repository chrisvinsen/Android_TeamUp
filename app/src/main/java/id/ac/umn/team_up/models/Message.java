package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.Date;

public class Message {
    private String from;


    private String message;

//    @Nullable
//    private String image;

    private Timestamp sentAt;

    public Message() {
    }

    public Message(String from, String messages, Timestamp sentAt){
        this.from = from;
        this.message = messages;
        this.sentAt = sentAt;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessages(String messages) {
        this.message = messages;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }
}
