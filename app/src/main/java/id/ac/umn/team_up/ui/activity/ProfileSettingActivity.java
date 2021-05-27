package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;

public class ProfileSettingActivity extends AppCompatActivity {
    private User currentUser;
    private EditText etEmail, etCurrentPassword, etNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        currentUser = UserController.getCurrentUser(this);

        etEmail = findViewById(R.id.etEmail);
        etEmail.setText(currentUser.getEmail());
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
    }

    public void backActivity(View view) {
        onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void saveData(View view) {
        boolean isAllFilled = true;

        String currentPass = etCurrentPassword.getText().toString();
        String newPass = etNewPassword.getText().toString();
        if (currentPass.isEmpty()) {
            isAllFilled = false;
            etCurrentPassword.setError("Current password must be filled");
        }
        if (newPass.isEmpty()) {
            isAllFilled = false;
            etNewPassword.setError("New password must be filled");
        } else if (newPass.length() < 6) {
            isAllFilled = false;
            etNewPassword.setError("New Password must be at least 6 characters long");
        }

        if (isAllFilled) {
            closeKeyboard();
            UserController.changePassword(this, currentPass, newPass);
            etCurrentPassword.setText("");
            etNewPassword.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please fill all required data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("frgToLoad", "profile");
        finish();
        startActivity(intent);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void logout(View view) {
        UserController.logout(this);
    }
}