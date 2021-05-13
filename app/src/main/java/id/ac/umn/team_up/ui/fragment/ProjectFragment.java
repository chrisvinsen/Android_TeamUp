package id.ac.umn.team_up.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectListController;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.notification.NotificationItemAdapter;
import id.ac.umn.team_up.ui.activity.project.ProjectListAdapter;

public class ProjectFragment extends Fragment {
    private RecyclerView rvProjectList;
    private ArrayList<Project> listProject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_project, container, false);
        rvProjectList = view.findViewById(R.id.rvProject);
        ProjectListController.getProjectList(rvProjectList,view,(AppCompatActivity) getActivity());

        return view;
    }



}