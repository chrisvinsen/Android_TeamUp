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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.ProjectController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.project.ProjectListAdapter;
import okhttp3.internal.cache.DiskLruCache;

public class ProjectFragment extends Fragment {
    //private RecyclerView rvProjectList;
    private String docCol = "ProjectNael";
    private ArrayList<Project> listProject;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference projectRef = db.collection(docCol);
    public static ProjectListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        //ProjectController.getProjectList(rvProjectList,view, (AppCompatActivity) getActivity());

        setUpRecyclerView(view);


        return view;
    }

    private void setUpRecyclerView(View view){
        Query query = projectRef;
        FirestoreRecyclerOptions<Project> options  = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(query, Project.class)
                .build();


        if (options.getSnapshots().isEmpty()){
            Log.d("query","Empty");
        }

        adapter = new ProjectListAdapter(options, (AppCompatActivity) getActivity());
        RecyclerView rvProjectList = view.findViewById(R.id.rvProject);
        rvProjectList.setHasFixedSize(true);
        rvProjectList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvProjectList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}