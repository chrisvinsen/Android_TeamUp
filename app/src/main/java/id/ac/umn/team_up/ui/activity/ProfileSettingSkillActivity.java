package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.adapter.ProfileSettingSkillAdapter;

public class ProfileSettingSkillActivity extends AppCompatActivity {
    private User currentUser;
    static ListView listview;
    EditText etSkill;
    Button btnAddSkill;

    static ArrayList<String> listSkills;
    static ProfileSettingSkillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting_skill);

        currentUser = UserController.getCurrentUser(this);

        listview = findViewById(R.id.listview);
        etSkill = findViewById(R.id.etSkill);
        btnAddSkill = findViewById(R.id.btnAddSkill);

        if (currentUser.getSkills().size() > 0) {
            listSkills = currentUser.getSkills();
        } else {
            listSkills = new ArrayList<>();
        }

        adapter = new ProfileSettingSkillAdapter
                (getApplicationContext(), listSkills);
        listview.setAdapter(adapter);

        btnAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSkill.getText().toString().equals("")) {
                    etSkill.setError("Skill can not be empty");
                } else {
                    closeKeyboard();
                    addSkill(etSkill.getText().toString());
                    etSkill.setText("");
                }
            }
        });
    }

    public void addSkill(String skillName) {
        if (listSkills.size() > 9) {
            Utils.show(this, "Your skills has reached the maximum limit.");
        } else if (listSkills.contains(skillName)) {
            Utils.show(this, "Skill already exists");
        } else {
            listSkills.add(skillName);
            listview.setAdapter(adapter);
        }
    }

    public static void removeSkill(int position) {
        listSkills.remove(position);
        listview.setAdapter(adapter);
    }

    public void saveData(View view) {
        currentUser.setSkills(listSkills);

        UserController.updateUser(this, currentUser);
    }

    public void backActivity(View view) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}