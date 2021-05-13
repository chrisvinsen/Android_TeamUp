package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.notification.NotificationItemAdapter;
import id.ac.umn.team_up.ui.activity.project.ProjectListAdapter;

public class ProjectController {
    private static FirebaseAuth auth;
    private static DatabaseReference db ;
    private static FirebaseFirestore db_firestore = FirebaseFirestore.getInstance();

    private static final String KEY_TITLE = "name";
    private static final String KEY_DESCRIPTION = "description";

    private static ArrayList<Project> dataProject;

    public static void getProjectList(RecyclerView rv, View view){
       db = FirebaseDatabase.getInstance().getReference();
       db.child("ProjectDetail").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               dataProject = new ArrayList<Project>();
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                   Project projects = dataSnapshot.getValue(Project.class);

                   projects.setKey(snapshot.getKey());
                   dataProject.add(projects);

               }

               Log.d("size data", String.valueOf(dataProject.size()));
               Log.d("recentmessage message", dataProject.get(0).getRecentMessageMessage());

                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                rv.setAdapter(new ProjectListAdapter(view.getContext(),dataProject));

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.d("Error","Please try again letter");
           }
       });

    }

    public static void postProject(String project_title, String project_description){
        Map<String, Object> project = new HashMap<>();
        project.put(KEY_TITLE, project_title);
        project.put(KEY_DESCRIPTION, project_description);

        db_firestore.collection("ProjectDetails").document().set(project)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Success", "Collection Added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Fail", "Error on adding collection");
                    }
                });
    }




}
