package id.ac.umn.team_up.controllers;


import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.MainActivity;

public class ProjectController {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db_firestore = FirebaseFirestore.getInstance();
    private static CollectionReference noteRef = db_firestore.collection("ProjectNael");
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ADMIN = "adminId";
    private static final String KEY_ADMIN_NAME = "adminName";
    private static final String KEY_MEMBER = "members";
    private static final String KEY_ICON = "groupIcon";
    private static final String KEY_IMAGES = "images";
    private static String fullname;

    private static ArrayList<Project> dataProject;
    //public static void getProjectList(RecyclerView rv, View view, AppCompatActivity app){




//       dataProject = new ArrayList<Project>();
//       CollectionReference colRef = db_firestore.collection("ProjectNael");
//       colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//           @Override
//           public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(DocumentSnapshot document : task.getResult()){
//                        Project project = document.toObject(Project.class);
//                        //project.setMembers((List<String>)document.get("members"));
//                        Log.d("project", document.toString());
//                    }
//
//                    rv.setHasFixedSize(true);
//                    rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                    rv.setAdapter(new ProjectListAdapter(view.getContext(),dataProject, app));
//
//                }else{
//                    Toast.makeText(app, "Query failed", Toast.LENGTH_SHORT).show();
//                }
//           }
//       });


//       db.child("ProjectDetail").addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               dataProject = new ArrayList<Project>();
//
//               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                   Project projects = dataSnapshot.getValue(Project.class);
//
//                   for(String id : projects.getMember()){
//
//                       if(mAuth.getUid().equals(id)){
//                           Log.d("idTest",id);
//                           dataProject.add(projects);
//                       }
//                   }
//
//               }
//
//               Log.d("size data", String.valueOf(dataProject.size()));
//
//                rv.setHasFixedSize(true);
//                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                rv.setAdapter(new ProjectListAdapter(view.getContext(),dataProject, app));
//
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//               Log.d("Error","Please try again letter");
//           }
//       });
  //  }


    public static ArrayList<DocumentSnapshot> queryMember(String docCol, String docDoc, String subCol){
        Log.d("docCol", docCol);
        CollectionReference memberRef = db.collection(docCol).document(docDoc).collection(subCol);
        ArrayList<DocumentSnapshot> memberQuery = new ArrayList<>();
        memberRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

            }
        });

        //        memberRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for(DocumentSnapshot document : task.getResult()){
//                    memberQuery.add(document);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("queryMember", "fail to get member collection");
//            }
//        });

        return memberQuery;
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
