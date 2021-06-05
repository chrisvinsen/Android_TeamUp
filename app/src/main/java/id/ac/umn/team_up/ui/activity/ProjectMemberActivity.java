package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import id.ac.umn.team_up.R;

public class ProjectMemberActivity extends AppCompatActivity {
    private RecyclerView rvProjectMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_member);
        rvProjectMember = findViewById(R.id.rvProjectMembers);
    }
}