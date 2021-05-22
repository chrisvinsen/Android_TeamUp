package id.ac.umn.team_up.ui.ProjectMember;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;

import id.ac.umn.team_up.R;

public class ProjectMember extends AppCompatActivity {
    RecyclerView rvPMList;
    ProjectMemberAdapter pmAdapter;
    LinkedList<ProjectMemberExample> pmLists = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_member);
        isiPm();
        rvPMList = findViewById(R.id.rvProjectMember);
        pmAdapter = new ProjectMemberAdapter(this, pmLists);
        rvPMList.setAdapter(pmAdapter);
        rvPMList.setLayoutManager(new LinearLayoutManager(this));
    }
    public void isiPm(){
        pmLists.add(new ProjectMemberExample("Delvin Chianardi", "Leader", Boolean.TRUE));
        pmLists.add(new ProjectMemberExample("Laurencia", "Member", Boolean.TRUE));
        pmLists.add(new ProjectMemberExample("Vanessa", "Member", Boolean.FALSE));
    }
}