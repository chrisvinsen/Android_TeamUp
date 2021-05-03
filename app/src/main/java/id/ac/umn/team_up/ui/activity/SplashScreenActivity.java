package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import id.ac.umn.team_up.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            final Intent intent = new Intent(this.getApplicationContext(), WelcomeActivity.class);
            startActivity(intent);
            finish();
        }, 1000L); //1000 L = 1 second
    }
}