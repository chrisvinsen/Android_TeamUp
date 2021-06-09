package id.ac.umn.team_up.models;

import java.io.Serializable;

public class ToDoList implements Serializable {
    private String projectId, description, title, status, todolistId;

    public ToDoList(){
        // Empty Constructor
    }

    public ToDoList(String projectId, String description, String status, String title, String todolistId){
        this.projectId = projectId;
        this.description = description;
        this.status = status;
        this.title = title;
        this.todolistId = todolistId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTodolistId() {
        return todolistId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }
}
