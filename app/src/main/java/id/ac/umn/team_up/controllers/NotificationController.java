package id.ac.umn.team_up.controllers;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.team_up.models.Message;

public class NotificationController {

    private ArrayList<Message> recentMessage;
    private static CollectionReference messageRef = FirebaseFirestore
            .getInstance().collection("MessageDetails");

    private static final String KEY_ID = "id";
    private static final String KEY_FROM_ID = "fromId";
    private static final String KEY_GROUP_ID = "projectId";
    private static final String KEY_message = "message";
    private static final String KEY_createdAt = "createdAt";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_ATTACHMENT = "attachment";

    //    public void ArrayList<Message>
    public void getRecentMessage(String projectId, String userId){
//        projectsRef.get()
       // Map<String, Object> message = new HashMap<>();

        messageRef.whereEqualTo("projectId",projectId)
                .whereNotEqualTo("fromId",userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
//                    if(progressDialog.isShowing())
//                        progressDialog.dismiss();
                    Log.e("MESSAGEREF","error");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){

                }
            }
        });

    }
}
