package id.ac.umn.team_up.ui.ProjectUtensil;

import java.io.Serializable;

public class TodoListExample implements Serializable {
    private String title;
    private String desc;
    private Boolean status;
    public TodoListExample(String title, String desc,
                       Boolean status){
        this.title = title;
        this.desc = desc;
        this.status = status;
    }
    public String getTitle() { return this.title; }
    public String getDesc() { return this.desc; }
    public Boolean getStatus() { return this.status; }

}
