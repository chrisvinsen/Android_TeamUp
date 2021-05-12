package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.RegisterCommunicator;
import id.ac.umn.team_up.ui.fragment.HomeFragment;
import id.ac.umn.team_up.ui.fragment.RegisterContdFragment;
import id.ac.umn.team_up.ui.fragment.RegisterFragment;

public class RegisterActivity extends AppCompatActivity implements RegisterCommunicator {
    final String FIRSTNAME = "first_name";
    final String LASTNAME = "last_name";

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterFragment fragment = new RegisterFragment();
        fragment.setRegisterCommunicator(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void nextRegistration(String first_name, String last_name) {
        fragment = new RegisterContdFragment();
        Bundle arguments = new Bundle();
        arguments.putString(FIRSTNAME, first_name);
        arguments.putString(LASTNAME, last_name);

        fragment.setArguments(arguments);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}