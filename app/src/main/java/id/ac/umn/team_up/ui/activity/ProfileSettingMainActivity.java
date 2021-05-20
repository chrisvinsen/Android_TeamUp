package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.fragment.ProfileFragment;

public class ProfileSettingMainActivity extends AppCompatActivity {
    private User currentUser;
    private EditText etFirstname, etLastname, etHeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("info", "activity create");
        setContentView(R.layout.activity_profile_setting_main);

        currentUser = UserController.getCurrentUser(this);

        etFirstname = findViewById(R.id.etFirstname);
        etFirstname.setText(currentUser.getFirstName());
        etLastname = findViewById(R.id.etLastname);
        etLastname.setText(currentUser.getLastName());
        etHeadline = findViewById(R.id.etHeadline);
        etHeadline.setText(currentUser.getHeadline());
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
        if (etFirstname.getText().toString().isEmpty()) {
            etFirstname.setError("First name must be filled");
            Toast.makeText(getApplicationContext(), "Please fill all required data", Toast.LENGTH_SHORT).show();
        } else {
            closeKeyboard();
            currentUser.setFirstName(etFirstname.getText().toString());
            currentUser.setLastName(etLastname.getText().toString());
            currentUser.setHeadline(etHeadline.getText().toString());

            UserController.updateUser(this, currentUser);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("back", "back clicked");

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