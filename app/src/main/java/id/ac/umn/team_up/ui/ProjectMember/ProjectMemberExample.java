package id.ac.umn.team_up.ui.ProjectMember;

import java.io.Serializable;

public class ProjectMemberExample implements Serializable {
    private String memberName;
    private String position;
    private Boolean isMember;
    public ProjectMemberExample(String memberName, String position,
                           Boolean isMember){
        this.memberName = memberName;
        this.position = position;
        this.isMember = isMember;
    }
    public String getMemberName() { return this.memberName; }
    public String getPosition() { return this.position; }
    public  Boolean getIsMember() { return this.isMember; }
}
