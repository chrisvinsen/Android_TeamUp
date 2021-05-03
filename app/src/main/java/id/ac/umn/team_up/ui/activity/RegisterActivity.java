package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.RegisterCommunicator;
import id.ac.umn.team_up.ui.fragment.HomeFragment;
import id.ac.umn.team_up.ui.fragment.RegisterContdFragment;
import id.ac.umn.team_up.ui.fragment.RegisterFragment;

public class RegisterActivity extends AppCompatActivity implements RegisterCommunicator {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addFragment();
    }

    public void addFragment() {
        RegisterFragment fragment = new RegisterFragment();
        fragment.setRegisterCommunicator(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment() {
        fragment = new RegisterContdFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment() {
        replaceFragment();
    }
}