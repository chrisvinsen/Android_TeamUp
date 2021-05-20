package id.ac.umn.team_up.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.activity.ProfileSettingAboutActivity;
import id.ac.umn.team_up.ui.activity.ProfileSettingMainActivity;
import id.ac.umn.team_up.ui.activity.ProfileSettingProjectActivity;
import id.ac.umn.team_up.ui.activity.ProfileSettingSkillActivity;

public class ProfileFragment extends Fragment {
    private ImageButton btnMenu, btnEditMain, btnEditAbout, btnEditProject, btnEditSkill;
    private User currentUser;
    private TextView tvFullname, tvHeadline, tvAbout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("info", "crete view");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvFullname = view.findViewById(R.id.tvFullname);
        tvHeadline = view.findViewById(R.id.tvHeadline);
        tvAbout = view.findViewById(R.id.tvAbout);

        btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout
                 UserController.logout((AppCompatActivity) getActivity());
            }
        });

        btnEditMain = view.findViewById(R.id.btnEditMain);
        btnEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingMainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnEditAbout = view.findViewById(R.id.btnEditAbout);
        btnEditAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingAboutActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnEditProject = view.findViewById(R.id.btnEditProject);
        btnEditProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingProjectActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btnEditSkill = view.findViewById(R.id.btnEditSkill);
        btnEditSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingSkillActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return view;
    }

    private void refreshUI() {
        currentUser = UserController.getCurrentUser(getContext());

        tvFullname.setText(currentUser.getFullName());
        String headline = (currentUser.getHeadline() == "") ? "Configure your Headline" : currentUser.getHeadline();
        tvHeadline.setText(headline);
        String about = (currentUser.getAbout() == "") ? "Describe about yourself here" : currentUser.getAbout();
        tvAbout.setText(about);
    }


    @Override
    public void onStart() {
        super.onStart();

        Log.d("info", "start");
        refreshUI();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("info", "resume");
    }
}