package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;

public class ProfileSettingAboutActivity extends AppCompatActivity {
    private User currentUser;
    EditText etAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting_about);

        currentUser = UserController.getCurrentUser(this);

        etAbout = findViewById(R.id.etAbout);
        etAbout.setText(currentUser.getAbout());
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
        closeKeyboard();
        currentUser.setAbout(etAbout.getText().toString());

        UserController.updateUser(this, currentUser, true);
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
}