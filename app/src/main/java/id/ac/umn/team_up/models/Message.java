package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

public class Message {
    private String fromId;

    @Nullable
    private String message;

    @Nullable
    private String image;

    public Message(String fromId, String messages){
        this.fromId = fromId;
        this.message = messages;
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
}
