package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.activity.LoginActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.notification.NotificationItemAdapter;
import id.ac.umn.team_up.ui.activity.project.ProjectListAdapter;

public class ProjectController {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static DatabaseReference db ;
    private static FirebaseFirestore db_firestore = FirebaseFirestore.getInstance();
    private static CollectionReference noteRef = db_firestore.collection("ProjectDetails");

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ADMIN = "adminId";
    private static final String KEY_ADMIN_NAME = "adminName";
    private static final String KEY_MEMBER = "members";
    private static final String KEY_ICON = "groupIcon";
    private static final String KEY_IMAGES = "images";
    private static String fullname;

    private static ArrayList<Project> dataProject;

    public static void getProjectList(RecyclerView rv, View view){
       db = FirebaseDatabase.getInstance().getReference();
       db.child("ProjectDetail").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               dataProject = new ArrayList<Project>();
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                   Project projects = dataSnapshot.getValue(Project.class);

//                   projects.setKey(snapshot.getKey());
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

    public static void postProject(final AppCompatActivity app, String project_title, String project_description, List<String> upload_url){
        // Get fullname of user


        // Put input into map
        Map<String, Object> project = new HashMap<>();
        List<String> member = new ArrayList<String>();
        member.add(mAuth.getUid());
        project.put(KEY_TITLE, project_title);
        project.put(KEY_DESCRIPTION, project_description);
        project.put(KEY_ADMIN, mAuth.getUid());
        project.put(KEY_ADMIN_NAME, fullname);
        project.put(KEY_MEMBER, member);
        if(upload_url.isEmpty()){
            project.put(KEY_ICON, "");
        }
        else{
            project.put(KEY_ICON, upload_url.get(0));
        }
        project.put(KEY_IMAGES, upload_url);

        // Set map into collection
        noteRef.document().set(project)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        final Intent intent = new Intent(app, MainActivity.class);
                        app.startActivity(intent);
                        Utils.show(app, "Your project has been successfully created!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.show(app, "Something went wrong, please try again later.");
                    }
                });
    }




}
