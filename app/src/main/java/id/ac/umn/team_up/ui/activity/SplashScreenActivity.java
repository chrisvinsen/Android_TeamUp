package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;

public class SplashScreenActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Intent intent;
            if (UserController.isLogin()) {
                intent = new Intent(this.getApplicationContext(), MainActivity.class);
            } else {
                intent = new Intent(this.getApplicationContext(), WelcomeActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1000L); //1000 L = 1 second
    }
}