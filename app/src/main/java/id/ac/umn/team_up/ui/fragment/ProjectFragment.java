package id.ac.umn.team_up.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.controllers.ProjectListController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.recycleviews.project.ProjectListAdapter;
import okhttp3.internal.Util;

public class ProjectFragment extends Fragment {
    private RecyclerView rvProjectList;
    private List<Project> listOfProject;
    private ProjectListAdapter mAdapter;
    private List<Message> listOfRecentMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if(this.getArguments().getBoolean("isOngoing")) {
            view = inflater.inflate(R.layout.fragment_project, container, false);
            mAdapter = new ProjectListAdapter(ProjectListController.getLoadUsersProjectOptions(UserController.getUserId(), true));
        } else {
            view = inflater.inflate(R.layout.fragment_project_history, container, false);
            mAdapter = new ProjectListAdapter(ProjectListController.getLoadUsersProjectOptions(UserController.getUserId(), false));
        }
        rvProjectList = view.findViewById(R.id.rvProject);
        rvProjectList.setHasFixedSize(true);
        rvProjectList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProjectList.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}