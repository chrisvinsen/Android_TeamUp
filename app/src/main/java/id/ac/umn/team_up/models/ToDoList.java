package id.ac.umn.team_up.models;

import java.io.Serializable;

public class ToDoList implements Serializable {
    private String id, description, title, status;

    public ToDoList(){
        // Empty Constructor
    }

    public ToDoList(String id, String description, String status, String title){
        this.id = id;
        this.description = description;
        this.status = status;
        this.title = title;
    }
}
