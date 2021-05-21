package id.ac.umn.team_up.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

import id.ac.umn.team_up.Utils;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String headline;
    private String picture;
    private String about;
    private ArrayList<String> skills;
    private String attachment;
    private Date createdAt;

    public User() {
        this.skills = new ArrayList<>();
        this.createdAt = new Date(System.currentTimeMillis());
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = Utils.sha256(password);
        this.createdAt = new Date(System.currentTimeMillis());
        this.skills = new ArrayList<>();
    }

    public User(Map<String, Object> map) {
        this.firstName = map.get("firstName").toString();
        this.lastName = (map.get("lastName") != null) ? map.get("lastName").toString() : null;
        this.email = map.get("email").toString();
        this.password = map.get("password").toString();
        this.headline = (map.get("headline") != null) ? map.get("headline").toString() : null;
        this.picture = (map.get("picture") != null) ? map.get("picture").toString() : null;
        this.about = (map.get("about") != null) ? map.get("about").toString() : null;
        this.attachment = (map.get("attachment") != null) ? map.get("attachment").toString() : null;
        this.skills = (map.get("skills") != null) ? (ArrayList<String>) map.get("skills") : new ArrayList<>();
        this.createdAt = new Date(System.currentTimeMillis());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getcreatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(createdAt);
    }
    public void setcreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
