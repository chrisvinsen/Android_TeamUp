package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

public class Message {
    private String fromId;
    private String _id;
    @Nullable
    private String attachment;
    private String message;

    public Message(String _id, @Nullable String attachment, String fromId, String messages){
        this._id = _id;
        this.attachment = attachment;
        this.fromId = fromId;
        this.message = messages;
        this.sentAt = sentAt;
    }

    public String get_id() {
        return _id;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getMessage() {
        return message;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
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
