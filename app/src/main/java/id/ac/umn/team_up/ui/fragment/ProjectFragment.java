package id.ac.umn.team_up.ui.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.MessageController;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.recycleviews.project.ProjectListAdapter;

public class ProjectFragment extends Fragment {
    private RecyclerView rvProjectList;
    private ProjectListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if(this.getArguments().getBoolean("isOngoing")) {
            view = inflater.inflate(R.layout.fragment_project, container, false);
            mAdapter = new ProjectListAdapter(ProjectController.loadUsersProjectOptions(UserController.getUserId(), true, false), (AppCompatActivity) getActivity() );
        } else {
            view = inflater.inflate(R.layout.fragment_project_history, container, false);
            mAdapter = new ProjectListAdapter(ProjectController.loadUsersProjectOptions(UserController.getUserId(), false, true), (AppCompatActivity) getActivity());
        }
        ProjectController.listenToProjectChanges(view.getContext(), mAdapter, UserController.getUserId());
//        MessageController.listenToRecentMessageChanges(view.getContext(), mAdapter);
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