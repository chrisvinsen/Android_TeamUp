package id.ac.umn.team_up.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.ui.fragment.ChatFragment;
import id.ac.umn.team_up.ui.fragment.TodolistFragment;

public class ChatActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager = getFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    private Button btnTodoList;
    private Button btnChat;
    private Button btnProjectMember;
    private String projectID;
    private String fullname;
    private String projectTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        btnTodoList = findViewById(R.id.btnTodoList);
        btnChat = findViewById(R.id.btnChat);
//        btnProjectMember = findViewById(R.id.btnProjectMember);

        Intent intent = getIntent();
        this.projectID = intent.getStringExtra("groupID");
        this.fullname = intent.getStringExtra("curretUser");
        this.projectTitle = intent.getStringExtra("projectTitle");

        TextView tvProjectTitle = findViewById(R.id.project_details_title_text);
        tvProjectTitle.setText(this.projectTitle);
        //initiate chat fragment
        initView();
        btnChat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openFragment(new ChatFragment().newInstance(projectID,fullname));
            }
        });

        btnTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new TodolistFragment().newInstance(projectID,""));
            }
        });

//        btnProjectMember.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                ProjectMemberIntent();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);

        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.projectmember:
//                ProjectMemberIntent();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    private Intent ProjectMemberIntent() {
        Intent intent = new Intent(this, ProjectMemberActivity.class);
        intent.putExtra("projectId", this.projectID);
        return intent;
    }

    public void initView(){
        openFragment(new ChatFragment().newInstance(projectID,fullname));
    }

    private void openFragment(Fragment fragment) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.chatActivityContainer, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showProjectDropDown(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.project_drop_down_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.project_members:
                        Intent intent = ProjectMemberIntent();
                        startActivityForResult(intent, 1);
                        return true;
                    case R.id.project_settings:
//                        HashMap<String,String> projectInfo = ProjectController.getProjectTitleAndDescription(projectID);
//                        Log.e("HASHMAP", projectInfo.get("title"));
//                        openFragment(new SettingsFragment().newInstance(projectInfo.get("title"), projectInfo.get("description")));
                        Intent intentSettings = new Intent(ChatActivity.this, ProjectSettingsActivity.class);
                        startActivityForResult(intentSettings, 1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void endTheProject(View view) {
        ProjectController.endProject(projectID);
    }
}