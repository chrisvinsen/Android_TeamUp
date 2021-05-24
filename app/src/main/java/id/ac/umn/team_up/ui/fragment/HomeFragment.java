package id.ac.umn.team_up.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.CircleTransform;
import id.ac.umn.team_up.ui.activity.post.PostAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //
    private static RecyclerView recycler_view;
    private static PostAdapter post_adapter;
    private static DatabaseReference database_reference;
    private static List<Project> projects;
    private static View view;
    private static EditText search_edit;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        if(view == null){
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_home, container, false);

            search_edit = (EditText) view.findViewById(R.id.search_edit);
            final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pull_to_refresh);
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Utils.delayForSomeSeconds(1000, new Runnable() {
                        @Override
                        public void run() {
                            search_edit.setText("");
                        }
                    });
                    ProjectController.getProjectPost(recycler_view, view);
                    pullToRefresh.setRefreshing(false);
                }
            });

            SharedPreferences sharedPref = Utils.getSharedPref(getActivity().getApplicationContext());
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/team-up-solib.appspot.com/o/uploads%2F1621347046743.jpg?alt=media&token=03ead921-1e56-4acf-a06d-dcb243dda1d1").placeholder(R.mipmap.ic_launcher).transform(new CircleTransform()).into((ImageView) view.findViewById(R.id.profile_picture_beside_search));


            // Search functionality
            search_edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() != 0){
                        ProjectController.getAllProjectPost(recycler_view, view, s);
                    }
                    else{
                        ProjectController.getProjectPost(recycler_view, view);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // Set up recycler view
            recycler_view = view.findViewById(R.id.recycler_view);
            ProjectController.getProjectPost(recycler_view, view);

        }

        return view;
    }


}