package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.UserController;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
    }

    public void sendEmail(View view) {
        String email = etEmail.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Please enter your Email");
        } else {
            UserController.resetPassword(this, email);
        }
    }

    public void goToLoginActivity(View view) {
        Utils.openActivity(this, LoginActivity.class);
    }
}