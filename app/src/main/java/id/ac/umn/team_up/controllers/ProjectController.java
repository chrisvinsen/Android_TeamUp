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
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    private static CollectionReference projectRef = db_firestore.collection("ProjectDetails");
    private static CollectionReference memberRef = db_firestore.collection("ProjectMembers");
    private static CollectionReference todolistRef = db_firestore.collection("ToDoList");


    private static ArrayList<Project> dataProject;
    private static ArrayList<ProjectMembers> members;

    // For home
    private static PostAdapter post_adapter;
    private static List<Project> projects;
    private static DocumentSnapshot lastResult;
    private static Button show_more_button;

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
        List<String> members = new ArrayList<String>();
        List<String> to_do_list = new ArrayList<String>();

        // Get random id
        String document_id = projectRef.document().getId();

        // Get random id for todolist
        String todolist_id = todolistRef.document().getId();

        // Input id to array
        members.add(mAuth.getUid());
        to_do_list.add(todolist_id);

        // Put input into map
        Map<String, Object> project = new HashMap<>();
        project.put("id", document_id);
        project.put("title", project_title);
        project.put("description", project_description);
        project.put("adminId", mAuth.getUid());
        project.put("groupIcon", "");
        project.put("images", upload_url);
        project.put("isOngoing", true);
        project.put("members", members);
        project.put("toDoList", to_do_list);
        project.put("createdAt", createdAt);

        // Set map into collection
        projectRef.document(document_id).set(project)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.show(app, "Something went wrong, please try again later.");
                    }
                });



        // Put to do list into map
        Map<String, String> todolist = new HashMap<>();
        todolist.put("title", "Title of your to do list");
        todolist.put("description", "Description of your to do list");
        todolist.put("status", "false");
        todolist.put("todolistId", todolist_id);
        todolist.put("projectId", document_id);

        // Set map into collection
        todolistRef.document(todolist_id).set(todolist)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.show(app, "Something went wrong, please try again later.");
                    }
                });

        // Put member into map
        Map<String, String> member = new HashMap<>();
        member.put("userId", mAuth.getUid());
        member.put("projectId", document_id);
        member.put("fullName", "Felix Laynardi");
        member.put("role", "Admin");
        member.put("picture", "https://firebasestorage.googleapis.com/v0/b/team-up-solib.appspot.com/o/uploads%2F1621347046743.jpg?alt=media&token=03ead921-1e56-4acf-a06d-dcb243dda1d1");

        // Set map into collection
        memberRef.document().set(member)
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

    public static void getProjectPost(RecyclerView recycler_view, View view){
        projects= new ArrayList<Project>();

        lastResult = null;

        loadPost(recycler_view, view);

        show_more_button = (Button) view.findViewById(R.id.show_more_button);
        show_more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPost(recycler_view, view);
            }
        });
    }

    public static void loadPost(RecyclerView recycler_view, View view){
        Query query;

        if (lastResult == null) {
            query = projectRef.orderBy("createdAt")
                    .limit(3);
        } else {
            query = projectRef.orderBy("createdAt")
                    .startAfter(lastResult)
                    .limit(3);
        }

        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Log.d("project", documentSnapshot.toString());
                            Project project = documentSnapshot.toObject(Project.class);
                            Log.d("project", project.toString());
                            projects.add(project);
                            Log.d("project", "1");
                        }
                        if (queryDocumentSnapshots.size() > 0) {
                            lastResult = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                            post_adapter= new PostAdapter(view.getContext(),projects);

                            recycler_view.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            recycler_view.setAdapter(post_adapter);
                        }
                    }
                });
    }




}