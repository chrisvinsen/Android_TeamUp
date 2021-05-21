package id.ac.umn.team_up.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        mAdapter = new ProjectListAdapter(ProjectListController.getLoadUsersProjectOptions(UserController.getUserId()));
        rvProjectList = view.findViewById(R.id.rvProject);
        rvProjectList.setHasFixedSize(true);
        rvProjectList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProjectList.setAdapter(mAdapter);

//        Utils.delayForSomeSeconds(1000, new Runnable() {
//            @Override
//            public void run() {
//                Log.e("LISTPROJECTSIZE", String.valueOf(listOfProject.size()));
//                Log.e("USERID", UserController.getUserId());
//
//                // get recent messages of each project
//                for (Project project : listOfProject) {
//                    Log.e("PROJECTID", project.getDocumentId());
//                    Message recentMessage = ProjectListController.getRecentMessage(project.getDocumentId());
//                    Log.e("RECENTMESSAGELOADING", recentMessage.getMessage());
//                    listOfRecentMessage.add(recentMessage);
//                }
//
//                if(!(listOfProject.size() < 1)){
//                    mAdapter = new ProjectListAdapter(getContext(), listOfProject, listOfRecentMessage);
//                    rvProjectList.setAdapter(mAdapter);
//                    rvProjectList.setLayoutManager(new LinearLayoutManager(getContext()));
//                }
//                else{
//                    Utils.show(getContext(), "You have no projects at the moment.");
//                }
//            }
//        });
//        ProjectListController.getProjectList(rvProjectList,view);

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