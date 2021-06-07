package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
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
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.models.ToDoList;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.recycleviews.post.PostAdapter;
import id.ac.umn.team_up.ui.activity.recycleviews.project.ProjectListAdapter;
import id.ac.umn.team_up.ui.activity.recycleviews.projectmember.ProjectMemberAdapter;
import id.ac.umn.team_up.ui.activity.recycleviews.todolist.TodoListAdapter;
import id.ac.umn.team_up.ui.adapter.ProfileProjectAdapter;

public class ProjectController {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static DatabaseReference db ;
    private static FirebaseFirestore db_firestore = FirebaseFirestore.getInstance();
    private static CollectionReference projectsRef = db_firestore.collection("ProjectDetails");
    private static CollectionReference memberRef = db_firestore.collection("ProjectMembers");
    private static CollectionReference todolistRef = db_firestore.collection("ToDoList");


    private static ArrayList<Project> dataProject;
    private static ArrayList<ProjectMember> members;

    // For home
    private static PostAdapter post_adapter;
    private static List<Project> projects;
    private static DocumentSnapshot lastResult;
    private static ProgressBar progress_bar;
    private static NestedScrollView nested_scroll_view;
    private static Task<QuerySnapshot> load_task;

    public static void postProject(final AppCompatActivity app, String project_title, String project_description, List<String> upload_url){
        // Get Shared Preference
        SharedPreferences sharedPref = Utils.getSharedPref(app);

        String fullname = sharedPref.getString("ufirstname", "") + " " + sharedPref.getString("ulastname", "");
        String picture = sharedPref.getString("upicture", "");

        // Get current time
        FieldValue createdAt = FieldValue.serverTimestamp();
        // Array of todolist and members
        List<String> members = new ArrayList<String>();
        List<String> to_do_list = new ArrayList<String>();

        // Get random id
        String document_id = projectsRef.document().getId();

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
        project.put("adminFullname", fullname);
        project.put("adminPicture", picture);
        project.put("groupIcon", "");
        project.put("images", upload_url);
        project.put("isOngoing", true);
        project.put("members", members);
        project.put("toDoList", to_do_list);
        project.put("createdAt", createdAt);

        // Set map into collection
        projectsRef.document(document_id).set(project)
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
        Map<String, Object> member = new HashMap<>();
        member.put("userId", mAuth.getUid());
        member.put("projectId", document_id);
        member.put("fullName", fullname);
        member.put("isAdmin", true);
        member.put("picture", picture);
        member.put("isMember", true);
        member.put("adminId", mAuth.getUid());

        // Set map into collection
        memberRef.document().set(member)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        final Intent intent = new Intent(app, MainActivity.class);
                        app.startActivity(intent);
                        Utils.show(app, "Your project has been successfully created, pull down to refresh!");
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


        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        nested_scroll_view = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);

        nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    loadPost(recycler_view, view);
                    progress_bar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static void loadPost(RecyclerView recycler_view, View view){
        Query query;

        if (lastResult == null) {
            query = projectsRef.orderBy("createdAt", Query.Direction.DESCENDING)
                    .limit(3);
        } else {
            query = projectsRef.orderBy("createdAt", Query.Direction.DESCENDING)
                    .startAfter(lastResult)
                    .limit(3);
        }

        load_task = query.get()
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
                        progress_bar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public static void getAllProjectPost(RecyclerView recycler_view, View view, CharSequence s){
        projects= new ArrayList<Project>();

        Query query;

        query = projectsRef.orderBy("createdAt", Query.Direction.DESCENDING);

        load_task = query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Project project = documentSnapshot.toObject(Project.class);
                            projects.add(project);
                        }
                        if (queryDocumentSnapshots.size() > 0) {
                            post_adapter = new PostAdapter(view.getContext(),projects);

                            recycler_view.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            recycler_view.setAdapter(post_adapter);

                            post_adapter.getFilter().filter(s.toString());
                        }
                    }
                });

        nested_scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }

    public static FirestoreRecyclerOptions<Project> loadUsersProjectOptions(String userId, boolean isOngoing, boolean isNotGoingOn){
        Query query;
        Log.e("USERID", userId);
        if (isOngoing && isNotGoingOn) {
            query =  projectsRef.whereArrayContains("members", userId);
        } else if (isOngoing) {
            query =  projectsRef.whereArrayContains("members", userId).whereEqualTo("isOngoing", true);
        } else if (isNotGoingOn){
            query =  projectsRef.whereArrayContains("members", userId).whereEqualTo("isOngoing", false);
        } else {
            query =  projectsRef.whereArrayContains("members", userId);
        }

        FirestoreRecyclerOptions<Project> options = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(query, Project.class)
                .build();
        Log.e("LOADPROJECT", String.valueOf(options.getSnapshots().size()));
        Log.e("USERID", userId);
        return options;
    }

    public static void listenToProjectChanges(Context c, ProjectListAdapter adapter, String userId){
        projectsRef.whereArrayContains("members", userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Utils.show(c, "Error listening to project changes");
                    return;
                }
                for(DocumentChange dc: value.getDocumentChanges()) {
//                    adapter.notifyDataSetChanged();
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        adapter.notifyDataSetChanged();
                        Log.e("LISTENTOPROJECTCHANGES", "added");
                    }
                    if (dc.getType() == DocumentChange.Type.MODIFIED){
                        adapter.notifyDataSetChanged();
                        Log.e("LISTENTOPROJECTCHANGES", "modified");
                    }
                }
            }
        });
    }

    public static void listenToProfileProjectChanges(Context c, ProfileProjectAdapter adapter, String userId){
        projectsRef.whereArrayContains("members", userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Utils.show(c, "Error listening to project changes");
                    return;
                }
                for(DocumentChange dc: value.getDocumentChanges()) {
//                    adapter.notifyDataSetChanged();
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        adapter.notifyDataSetChanged();
                        Log.e("LISTENTOPROJECTCHANGES", "added");
                    }
                    if (dc.getType() == DocumentChange.Type.MODIFIED){
                        adapter.notifyDataSetChanged();
                        Log.e("LISTENTOPROJECTCHANGES", "modified");
                    }
                }
            }
        });
    }

    public static void updateProjectRecentMessage(Message message, DocumentReference projectRef){
        projectRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Project project = document.toObject(Project.class);
                                Log.d("GETRECENTMESSAGE", "DocumentSnapshot data: " + document.getData());
                                project.setRecentMessage(message.getMessage());
                                Log.d("GETRECENTMESSAGE", message.getMessage());
                                project.setSentAt(message.getCreatedAt());
                                Log.d("GETRECENTMESSAGE", String.valueOf(message.getCreatedAt()));
                                Log.e("GETRECENTMESSAGE", String.valueOf(project.getSentAt()));
                                projectRef.set(project, SetOptions.merge());
                            } else {
                                Log.d("GETRECENTMESSAGE", "No such document");
                            }
                        } else {
                            Log.d("GETRECENTMESSAGE", "get failed with ", task.getException());
                        }
                    }
                });
    }
    public static void getTodolist(RecyclerView rvTdl, View v, String projectId){

        Log.d("PROJECTIDTDL", projectId);
        todolistRef.whereEqualTo("projectId", projectId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<ToDoList> todoLists = new ArrayList<>();
                if(error != null){
                    Log.e("TODOLISTREF","error");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){



                    todoLists.add(dc.getDocument().toObject(ToDoList.class));
                }
                TodoListAdapter mTodolistAdapter;
                mTodolistAdapter = new TodoListAdapter(v.getContext(), todoLists);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
                rvTdl.setHasFixedSize(true);
                rvTdl.setLayoutManager(linearLayoutManager);
                rvTdl.setAdapter(mTodolistAdapter);
            }
        });

    }
    public static void addTodolist(String projectId, String title, String desc){
        String todolist_id = todolistRef.document().getId();

        // Put to do list into map
        Map<String, String> todolist = new HashMap<>();
        todolist.put("title", title);
        todolist.put("description", desc);
        todolist.put("status", "false");
        todolist.put("todolistId", todolist_id);
        todolist.put("projectId", projectId);

        Log.e("ADDTODOLISTID", todolist_id);

        todolistRef.document(todolist_id).set(todolist);
    }

    public static void updateStatusTodolist(String todolistId, String status){
        DocumentReference tdlRef = todolistRef.document(todolistId);
        todolistRef.document(todolistId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        ToDoList toDoList = document.toObject(ToDoList.class);
                        if(toDoList.getStatus().equals("true")){
                            toDoList.setStatus("false");
                        }else{
                            toDoList.setStatus("true");
                        }
                        tdlRef.set(toDoList, SetOptions.merge());
                    }
                }
            }
        });
    }

    public static void getProjectMembers(RecyclerView rvProjectMember, Context context, String projectId){
        Log.d("PROJECTIDPM", projectId);

        memberRef.whereEqualTo("projectId", projectId)
                .whereEqualTo("isMember", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<ProjectMember> projectMembers = new ArrayList<>();
                if(error != null){
                    Log.e("ProjectMemberRef","error");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    projectMembers.add(dc.getDocument().toObject(ProjectMember.class));
                }
                ProjectMemberAdapter mProjectMemberAdapter;
                mProjectMemberAdapter = new ProjectMemberAdapter(context, projectMembers);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                rvProjectMember.setHasFixedSize(true);
                rvProjectMember.setLayoutManager(linearLayoutManager);
                rvProjectMember.setAdapter(mProjectMemberAdapter);
            }
        });
    }
}
