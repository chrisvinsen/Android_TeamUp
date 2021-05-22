package id.ac.umn.team_up.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;

public class ProjectListController {
    private static FirebaseAuth auth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference projectsRef = db.collection("Projects");
    private static CollectionReference messagesRef = db.collection("Message");
    private static List<Project> listOfProject = new ArrayList<Project>();
    private static Message recentMessage;

    public static FirestoreRecyclerOptions<Project> getLoadUsersProjectOptions(String userId, boolean isOngoing){
        Query query;

        if(isOngoing){
            query =  projectsRef.whereArrayContains("members", userId).whereEqualTo("isOngoing", true);
        }
        else{
            query =  projectsRef.whereArrayContains("members", userId).whereEqualTo("isOngoing", false);
        }

        FirestoreRecyclerOptions<Project> options = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(query, Project.class)
                .build();
        Log.e("LOADPROJECT", options.getSnapshots().toString());
        return options;
    }

    public static Message getRecentMessage(String projectDocumentId){
        projectsRef.document(projectDocumentId)
                .collection("messages")
//                .orderBy("sentAt", Query.Direction.DESCENDING)
//                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.e("GETRECENTMESSAGE", String.valueOf(queryDocumentSnapshots.size()));
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            recentMessage = documentSnapshot.toObject(Message.class);
                            Log.e("RECENTMESSAGELOADING", recentMessage.getMessage());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("GETRECENTMESSAGE", "getRecentMessage failed");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e("GETRECENTMESSAGES", "completed");
                    }
                });
//        Log.e("RECENTMESSAGELOADING", recentMessage.getMessage());
        return recentMessage;
    }

    public static void addProjectsListener(String userId, List<Project> projectList){
//        projectsRef.whereEqualTo("adminId", userId)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error != null){
//                    Log.e("ADDPROJECTSLISTENER", error.toString());
//                }
//                for (DocumentChange doc : value.getDocumentChanges()){
//                    if(doc.getType() == DocumentChange.Type.ADDED){
//                        Project project = doc.getDocument().toObject(Project.class);
//                        project.setDocumentId(doc.getDocument().getId());
//                        projectList.add(project);
//                        Log.e("PROJECTLOADING", project.getDocumentId());
//
//                        doc.getDocument().getReference().collection("messages")
//                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                        if(error != null){
//                                            Log.e("ADDPROJECTSLISTENER", error.toString());
//                                        }
//                                        for (DocumentChange doc : value.getDocumentChanges()){
//
//                                        }
//                                    }
//                                });
//                    }
//                }
//            }
//        });
    }
}
