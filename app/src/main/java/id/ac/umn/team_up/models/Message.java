package id.ac.umn.team_up.models;

public class Message {
    private String fromId;
    private String _id;
    private String attachment;
    private String message;

    public Message(String _id, String attachment,String fromId, String messages){
        this._id = _id;
        this.attachment = attachment;
        this.fromId = fromId;
        this.message = messages;
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
}
