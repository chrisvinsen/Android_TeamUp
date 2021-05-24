package id.ac.umn.team_up.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;

public class NotificationController {

    private ArrayList<Message> recentMessage;
    private static CollectionReference messageRef = FirebaseFirestore
            .getInstance().collection("MessageDetails");
    private static CollectionReference projectRef = FirebaseFirestore
            .getInstance().collection("ProjectDetails");




    private static final String KEY_ID = "id";
    private static final String KEY_FROM_ID = "fromId";
    private static final String KEY_GROUP_ID = "projectId";
    private static final String KEY_message = "message";
    private static final String KEY_createdAt = "createdAt";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_ATTACHMENT = "attachment";

    //    public void ArrayList<Message>
    public static void getRecentMessage(){
        List<String> projectListId = new ArrayList<String>();
//        projectsRef.get()
       // Map<String, Object> message = new HashMap<>();
//        projectRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                    Project project = documentSnapshot.toObject(Project.class);
//                    Log.d("PROJECTTITLE", project.getId());
//                    projectListId.add(project.getId());
//                }
//            }
//        });

        projectRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("GETTINGDATAPROJEFCT", "error");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        projectListId.add(dc.getDocument().toObject(Project.class).getId());
                    }
                }
            }
        });


//        messageRef.whereEqualTo("projectId",projectId)
//                .whereNotEqualTo("fromId",userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error != null){
////                    if(progressDialog.isShowing())
////                        progressDialog.dismiss();
//                    Log.e("MESSAGEREF","error");
//                    return;
//                }
//                for(DocumentChange dc : value.getDocumentChanges()){
//
//                }
//            }
//        });

    }
}
