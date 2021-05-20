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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.fragment.HomeFragment;
import id.ac.umn.team_up.ui.fragment.NotificationFragment;
import id.ac.umn.team_up.ui.fragment.PostFragment;
import id.ac.umn.team_up.ui.fragment.ProfileFragment;
import id.ac.umn.team_up.ui.fragment.ProjectFragment;
import id.ac.umn.team_up.ui.fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }
        } else {
            openFragment(HomeFragment.newInstance("", ""));
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
                            openFragment(ProjectFragment.newInstance("", ""));
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
}