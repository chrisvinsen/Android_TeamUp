package id.ac.umn.team_up.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //
    private EditText project_title;
    private EditText project_description;
    private Button post_button;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post, container, false);

        project_title = (EditText) view.findViewById(R.id.project_title);
        project_description = (EditText) view.findViewById(R.id.project_description);
        post_button = (Button) view.findViewById(R.id.post_button);

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = project_title.getText().toString();
                final String description = project_description.getText().toString();
                boolean isAllFilled = true;

                if (title.isEmpty()) {
                    project_title.setError("Project title must be filled");
                    isAllFilled = false;
                }
                if (description.isEmpty()) {
                    project_description.setError("Project description must be filled");
                    isAllFilled = false;
                }

                if (isAllFilled) {
                    Log.d("data", "title: " + project_title.getText().toString());
                    Log.d("data", "description: " + project_description.getText().toString());

                    ProjectController.postProject(title, description);
                } else {
                    Toast.makeText(getActivity(), "Please fill all required data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}