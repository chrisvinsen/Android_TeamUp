package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.ProjectUtensil.DummyFragment;
import id.ac.umn.team_up.ui.ProjectUtensil.TodolistFragment;

public class ProjectUtensil extends AppCompatActivity {
    Button tdlBtn, chatBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_utensil);
        tdlBtn = findViewById(R.id.tdlButton);
        chatBtn = findViewById(R.id.chatButton);
        initView();

        tdlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(TodolistFragment.newInstance("", ""));
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(DummyFragment.newInstance("", ""));
            }
        });
    }

    public void initView(){
        openFragment(TodolistFragment.newInstance("", ""));
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerPU, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}