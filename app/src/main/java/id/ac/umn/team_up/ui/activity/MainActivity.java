package id.ac.umn.team_up.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.NotificationController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.fragment.HomeFragment;
import id.ac.umn.team_up.ui.fragment.NotificationFragment;
import id.ac.umn.team_up.ui.fragment.PostFragment;
import id.ac.umn.team_up.ui.fragment.ProfileFragment;
import id.ac.umn.team_up.ui.fragment.ProjectFragment;



public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Utils.overrideFont(getApplicationContext(), "SERIF", "fonts/roboto.xml");

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // Get the extras (if there are any)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            switch (extras.getString("frgToLoad")){
                case "profile":
                    Log.d("open", "profile");
                    openFragment(new ProfileFragment());
                    bottomNavigation.setSelectedItemId(R.id.navigation_profile);
                    break;
                case "home":
                    openFragment(HomeFragment.newInstance("1", "1"));
                    bottomNavigation.setSelectedItemId(R.id.navigation_home);
                    break;
            }
        } else {
            NotificationController.loadProjectMemberRequestNotification(getApplicationContext());
            openFragment(HomeFragment.newInstance("1", "1"));
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Log.d("menu", "home");
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_projects:
                            Log.d("menu", "projects");
                            goToProjectList(true);
                            return true;
                        case R.id.navigation_posts:
                            Log.d("menu", "posts");
                            openFragment(PostFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_notifications:
                            Log.d("menu", "notif");
                            openFragment(NotificationFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_profile:
                            Log.d("menu", "profile");
                            openFragment(new ProfileFragment());
                            return true;
                    }

                    return false;
                }
            };

    public void goToProjectHistory(View view) {
        goToProjectList(false);
    }

    public void goToOngoingProject(View view) {
        goToProjectList(true);
    }

    public void goToProjectList(boolean isOngoing){
        Fragment fragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isOngoing", isOngoing);
        fragment.setArguments(bundle);
        openFragment(fragment);
    }

    public void endTheProject(View view) {
//        String projectId = "6AKu0PSo41AeRZ5UjPh6";
//        List<ProjectMember>
    }

    @Override
    protected void onStart() {
        super.onStart();
        //NotificationController.loadProjectMemberRequestNotification(getApplicationContext());
        //NotificationController.getProject(getApplicationContext());
    }
}