package id.ac.umn.team_up.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {
    private String id, adminId, description, groupIcon, title;
    private boolean isOngoing;
    private List<String> images;
    private List<ProjectMembers> members;
    private List<ToDoList> to_do_list;
    private Date createdAt;
    @Nullable
    private Date endedAt;

    // private Map<String,String> recentMessage;

    public Project(){
        //empty constructor
    }

    public Project(String id, String adminId, String description, String groupIcon, String title, List<String> images, boolean isOngoing, List<Map<String, String>> members, List<Map<String, String>> to_do_list, String createdAt){
        this.id = id;
        this.adminId = adminId;
        this.description = description;
        this.groupIcon = groupIcon;
        this.title = title;
        this.images = images;
        this.members = new ArrayList<ProjectMembers>();
        for(Map<String, String> map : members){
            ProjectMembers member = new ProjectMembers(map.get("id"), map.get("firstName"), map.get("lastName"), map.get("profilePicture"));
            this.members.add(member);
        }
        for(Map<String, String> map : to_do_list){
            ToDoList to_do_list_ = new ToDoList(map.get("id"), map.get("description"), map.get("status"), map.get("title"));
            this.to_do_list.add(to_do_list_);
        }
        this.isOngoing = isOngoing;
        Long t = Long.parseLong(createdAt);
        this.createdAt = new Date(t*1000);
    }

    // Get method
    public String getId(){
        return id;
    }
    public String getAdminId(){
        return adminId;
    }
    public String getDescription(){
        return description;
    }
    public String getGroupIcon(){
        return groupIcon;
    }
    public String getTitle(){
        return title;
    }
    public boolean getIsOngoing(){
        return isOngoing;
    }
    public List<String> getImages(){
        return images;
    }
    public List<ProjectMembers> getMembers(){
        return members;
    }
    public List<ToDoList> getToDoList(){
        return to_do_list;
    }
    public Date getCreatedAt(){
        return createdAt;
    }
    public void setMembers(List<ProjectMembers> members){
        this.members = members;
    }


//    public void setAdminId(String adminId) {
//        this.adminId = adminId;
//    }
//
//    public void setAdminName(String adminName) {
//        this.adminName = adminName;
//    }
//
//    public void setGroupIcon(String groupIcon) {
//        this.groupIcon = groupIcon;
//    }
//
//    public void setMember(List<String> member) {
//        this.member = member;
//    }
//
//    public void setProjectTitle(String projectTitle) {
//        this.title = projectTitle;
//    }
//
//
//    public String getAdminId() {
//        return adminId;
//    }
//
//    public String getAdminName() {
//        return adminName;
//    }
//
//    public String getGroupIcon() {
//        return groupIcon;
//    }
//
//    public List<String> getMember() {
//        return member;
//    }
//
//    public String getProjectTitle() {
//        return title;
//    }
//
//    @Override
//    public String toString(){
//        return getProjectTitle();
//    }
//
//    public String get_id() {
//        return _id;
//    }
//
//    public void set_id(String _id) {
//        this._id = _id;
//    }
//
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
