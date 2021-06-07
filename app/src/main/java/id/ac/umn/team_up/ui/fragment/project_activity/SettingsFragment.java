package id.ac.umn.team_up.ui.fragment.project_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.ui.activity.ChatActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;
//import id.ac.umn.team_up.ui.activity.ProjectActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String projectTitle;
    private String projectDescription;
    private String projectId;
    Button btnEndProject, btnSaveChanges;
    EditText edtTitle, edtDesctiption;

    private String title, desc, fullname;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2, String param3, String param4) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectId = getArguments().getString(ARG_PARAM1);
            projectTitle = getArguments().getString(ARG_PARAM2);
            projectDescription = getArguments().getString(ARG_PARAM3);
            fullname = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_settings, container, false);

        btnEndProject = view.findViewById(R.id.btn_end_project);
        btnSaveChanges = view.findViewById(R.id.btn_save_changes);
        edtTitle = view.findViewById(R.id.project_fragment_title);
        edtDesctiption = view.findViewById(R.id.project_fragment_description);


        edtTitle.setText(projectTitle);
        edtDesctiption.setText(projectDescription);

        btnEndProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectController.endProject(projectId);
                Log.e("END PROJECT", "PROJECT PUTUS UDAH KEA PACARAN PASTI ADA YANG PUTUS");
                Utils.show(getContext(), "Project has ended.");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = edtTitle.getText().toString();
                desc = edtDesctiption.getText().toString();
                ProjectController.updateProjectSetting(projectId, title, desc);
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                //sending project id
                intent.putExtra("groupID", projectId);
                intent.putExtra("projectDesc", projectDescription);
                intent.putExtra("curretUser", fullname);
                intent.putExtra("projectTitle", projectTitle);
                startActivity(intent);
            }
        });

        return view;
    }
}
