package id.ac.umn.team_up.ui.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.User;

public class ProfileFragment extends Fragment {
    private ImageView btnLogout;
    private User currentUser;
    private TextView tvFullname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        Log.d("ABC", currentUser.getFullName());

        tvFullname = view.findViewById(R.id.tvFullname);
        UserController.setUserProfile((AppCompatActivity) getActivity(), tvFullname);

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserController.logout((AppCompatActivity) getActivity());
            }
        });

        return view;
    }
}