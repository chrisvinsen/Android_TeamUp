package id.ac.umn.team_up.models;

public class Message {
    private String fromId;
    private String message;

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
