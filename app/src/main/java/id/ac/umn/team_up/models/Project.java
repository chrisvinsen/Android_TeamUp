package id.ac.umn.team_up.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {
    private String adminId, adminName;
    private String groupIcon;
    private List<String> member;
    private String title;
    private String _id;
    //private Map<String,String> recentMessage;

    public Project(){
        //empty constructor
    }

    public Project( String adminName,String adminId,Map<String,String> recentMessage, List<String> member, String title, String _id, String groupIcon){
        this.groupIcon = "";
        this.adminId = adminId;
        this.adminName = adminName;
        this.member = member;
        this._id = _id;
        this.title = title;
        //this.recentMessage = recentMessage;
        this.groupIcon = groupIcon;
    }


    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }

    public void setProjectTitle(String projectTitle) {
        this.title = projectTitle;
    }


    public String getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public List<String> getMember() {
        return member;
    }

    public String getProjectTitle() {
        return title;
    }

    @Override
    public String toString(){
        return getProjectTitle();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

//    public void setRecentMessage(Map<String, String> recentMessage) {
//        this.recentMessage = recentMessage;
//    }
//
//    public Map<String, String> getRecentMessage() {
//        return recentMessage;
//    }
//
//    public String getRecentMessageFrom(){
//        return this.recentMessage.get("fromId");
//    }
//
//    public String getRecentMessageMessage(){
//        return this.recentMessage.get("message");
//    }

}
