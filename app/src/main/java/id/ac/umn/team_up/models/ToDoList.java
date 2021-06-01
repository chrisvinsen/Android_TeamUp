package id.ac.umn.team_up.models;

import java.io.Serializable;

public class ToDoList implements Serializable {
    private String id, description, title, status, todolistId;

    public ToDoList(){
        // Empty Constructor
    }

    public ToDoList(String id, String description, String status, String title, String todolistId){
        this.id = id;
        this.description = description;
        this.status = status;
        this.title = title;
        this.todolistId = todolistId;
    }

    public String getTodolistId() {
        return todolistId;
    }

    public String getId() {
        return id;
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
