package id.ac.umn.team_up.ui.ProjectUtensil;

import java.io.Serializable;

public class ToDoList implements Serializable {
    private String projectId;
    private String title;
    private String description;
    private boolean status;

    public ToDoList(){
        // Empty Constructor
    }

    public ToDoList(String projectId,String title, String description, boolean status){
        this.projectId = projectId;
        this.description = description;
        this.status = status;
        this.title = title;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getStatus() {
        return status;
    }
}