package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.ProjectMembers;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.activity.LoginActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.notification.NotificationItemAdapter;
import id.ac.umn.team_up.ui.activity.post.PostAdapter;
import id.ac.umn.team_up.ui.activity.project.ProjectListAdapter;

public class ProjectController {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static DatabaseReference db ;
    private static FirebaseFirestore db_firestore = FirebaseFirestore.getInstance();
    private static CollectionReference noteRef = db_firestore.collection("ProjectDetails");
    private static CollectionReference noteRef2 = db_firestore.collection("ProjectDetails");

    private static final String KEY_MEMBER_ID = "id";
    private static final String KEY_MEMBER_FNAME = "firstName";
    private static final String KEY_MEMBER_LNAME = "lastName";
    private static final String KEY_MEMBER_IMG = "profilePicture";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ADMIN = "adminId";
    private static final String KEY_ICON = "groupIcon";
    private static final String KEY_IMAGES = "images";
    private static final String KEY_ONGOING = "isOngoing";
    private static final String KEY_TODO_TITLE = "title";
    private static final String KEY_TODO_DESC = "description";
    private static final String KEY_TODO_STATUS = "status";
    private static final String KEY_MEMBERS = "members";
    private static final String KEY_TODOLIST = " to_do_list";
    private static final String KEY_CREATED_AT = "createdAt";

    private static ArrayList<Project> dataProject;
    private static ArrayList<ProjectMembers> members;

    // For home
    private static PostAdapter post_adapter;
    private static List<Project> projects;

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
               //Log.d("recentmessage message", dataProject.get(0).getRecentMessageMessage());

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
        // Get current time
        FieldValue createdAt = FieldValue.serverTimestamp();
        // Array of todolist and members
        List<Map<String, String>> members = new ArrayList<Map<String, String>>();
        List<Map<String, String>> to_do_list = new ArrayList<Map<String, String>>();

        // Put to do list into map
        Map<String, String> todolist = new HashMap<>();
        todolist.put(KEY_TODO_TITLE, "Title of your to do list");
        todolist.put(KEY_TODO_DESC, "Description of your to do list");
        todolist.put(KEY_TODO_STATUS, "false");
        to_do_list.add(todolist);


        // Put member into map
        Map<String, String> member = new HashMap<>();
        member.put(KEY_MEMBER_ID, mAuth.getUid());
        member.put(KEY_MEMBER_FNAME, "Felix");
        member.put(KEY_MEMBER_LNAME, "Laynardi");
        member.put(KEY_MEMBER_IMG, "https://firebasestorage.googleapis.com/v0/b/team-up-solib.appspot.com/o/uploads%2F1621347046743.jpg?alt=media&token=03ead921-1e56-4acf-a06d-dcb243dda1d1");
        members.add(member);

        // Get random id
        String document_id = noteRef.document().getId();

        // Put input into map
        Map<String, Object> project = new HashMap<>();
        project.put(KEY_ID, document_id);
        project.put(KEY_TITLE, project_title);
        project.put(KEY_DESCRIPTION, project_description);
        project.put(KEY_ADMIN, mAuth.getUid());
        project.put(KEY_ICON, "");
        project.put(KEY_IMAGES, upload_url);
        project.put(KEY_ONGOING, true);
        project.put(KEY_MEMBERS, members);
        project.put(KEY_TODOLIST, to_do_list);
        project.put(KEY_CREATED_AT, createdAt);

        // Set map into collection
        noteRef.document(document_id).set(project)
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
        // Set members into collection of collection
//        noteRef.document(document_id).collection("members").document().set(members)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                    }
//
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Utils.show(app, "Something went wrong, please try again later.");
//                    }
//                });
//        // Set to do lists into collection of collection
//        noteRef.document(document_id).collection("to-do-list").document().set(todolist)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        final Intent intent = new Intent(app, MainActivity.class);
//                        app.startActivity(intent);
//                        Utils.show(app, "Your project has been successfully created!");
//                    }
//
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Utils.show(app, "Something went wrong, please try again later.");
//                    }
//                });
        
    }

    public static void getProjectPost(RecyclerView recycler_view, View view){
        // Get from firebase database ProjectDetails collection
        Log.d("check", "3");
        projects= new ArrayList<Project>();

        noteRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("check", "2");
                    for(DocumentSnapshot document : task.getResult()){
                        Log.d("check", "1");
                        Project project = document.toObject(Project.class);
                        Log.d("id", project.getId());
                        Log.d("timestamp", project.getCreatedAt().toString());
                        projects.add(project);
                    }
                    post_adapter= new PostAdapter(view.getContext(),projects);

                    recycler_view.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    recycler_view.setAdapter(post_adapter);
                }
                else{
                    Log.d("check", "2");
                    Toast.makeText(view.getContext(), "There is a problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}
