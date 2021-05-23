package id.ac.umn.team_up.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.SetOptions;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.recycleviews.message.MessageListAdapter;
import id.ac.umn.team_up.ui.activity.recycleviews.project.ProjectListAdapter;

public class MessageController {

    private String attachment, fistName, fromId, groupId, message;
    private Timestamp createAt;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference messagesRef = db.collection("MessageDetails");
    private static CollectionReference projectsRef = db.collection("ProjectDetails");

    private static final String KEY_ID = "id";
    private static final String KEY_FROM_ID = "fromId";
    private static final String KEY_GROUP_ID = "projectId";
    private static final String KEY_message = "message";
    private static final String KEY_createdAt = "createdAt";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_ATTACHMENT = "attachment";

    private static MessageListAdapter mMessageAdapter;

    //argument group id
    public void getMessage(String GroupID){


    }

    //sendmessage
    public static void sentMessage(String projectId, String fullname,String userId, String msg, String attachment){
        FieldValue createdAt = FieldValue.serverTimestamp();

        Map<String, Object> message = new HashMap<>();

        //random id
        AtomicLong LAST_TIME_MS = new AtomicLong();
        long now = System.currentTimeMillis();
        while(true) {
            long lastTime = LAST_TIME_MS.get();
            if (lastTime >= now)
                now = lastTime+1;
            if (LAST_TIME_MS.compareAndSet(lastTime, now))
                break;
        }

        String document_id = String.valueOf(now);

        message.put(KEY_ID, document_id);
        message.put(KEY_FROM_ID, userId);
        message.put(KEY_createdAt, createdAt);
        message.put(KEY_GROUP_ID, projectId);
        message.put(KEY_message, msg);
        message.put(KEY_ATTACHMENT, attachment);
        message.put(KEY_FULLNAME,fullname );

        messagesRef.document(document_id).set(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("SUCCESS","sending message sucess");
                    }
                });


    }

    //getMessage
    public static void getMessage(RecyclerView rv, View v, String userId, String projectId){
        ArrayList<Message> messageList = new ArrayList<Message>();
        Log.d("GroupID", projectId);

//        messagesRef.whereEqualTo("groupId",groupId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(DocumentSnapshot document : task.getResult()){
//                        Log.d("check", document.toString());
//                        Message message = document.toObject(Message.class);
//                        messageList.add(message);
//                    }
//
////                    Log.d("MESSAGELIST", messageList.get(0).getMessage());
////                    Log.d("UserID", userId);
//                    mMessageAdapter = new MessageListAdapter(v.getContext(),messageList,userId);
//                    rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
//                    rv.setAdapter(mMessageAdapter);
//
//                }
//            }
//        });

//        messagesRef.whereEqualTo("groupId",groupId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if(!queryDocumentSnapshots.isEmpty()){
//                    Log.d("CHEKPOINT","1");
//                    for(DocumentSnapshot document : queryDocumentSnapshots){
//                        Log.d("check", document.toString());
//                        Message message = document.toObject(Message.class);
//                        messageList.add(message);
//                    }
//
////                    Log.d("MESSAGELIST", messageList.get(0).getMessage());
////                    Log.d("UserID", userId);
//                    mMessageAdapter = new MessageListAdapter(v.getContext(),messageList,userId);
//                    rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
//                    rv.setAdapter(mMessageAdapter);
//
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//
        //setting progress dialog for user experience
        ProgressDialog progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");

        mMessageAdapter = new MessageListAdapter(v.getContext(), messageList,userId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(mMessageAdapter);

        messagesRef.whereEqualTo("projectId",projectId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
//                    if(progressDialog.isShowing())
//                        progressDialog.dismiss();
                    Log.e("MESSAGEREF","error");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        messageList.add(dc.getDocument().toObject(Message.class));
                    }
                    mMessageAdapter.notifyDataSetChanged();
                    linearLayoutManager.scrollToPosition(messageList.size()-1);
//                    if(progressDialog.isShowing())
//                        progressDialog.dismiss();
                }

            }
        });


    }

    public static void getRecentMessage(Context c, ProjectListAdapter adapter){
//        final Message[] recentMessage = new Message[1];
        messagesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Utils.show(c, "Error getting recent message");
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                Message recentMessage = dc.getDocument().toObject(Message.class);
                                DocumentReference projectRef = projectsRef.document(recentMessage.getProjectId());
                                projectRef.get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        Project project = document.toObject(Project.class);
                                                        Log.d("GETRECENTMESSAGE", "DocumentSnapshot data: " + document.getData());
                                                        project.setRecentMessage(recentMessage.getMessage());
                                                        Log.d("GETRECENTMESSAGE", recentMessage.getMessage());
                                                        project.setSentAt(recentMessage.getCreatedAt());
//                                                        Log.d("GETRECENTMESSAGE", recentMessage.getCreatedAt().toString());
                                                        projectRef.set(project, SetOptions.merge());
                                                    } else {
                                                        Log.d("GETRECENTMESSAGE", "No such document");
                                                    }
                                                } else {
                                                    Log.d("GETRECENTMESSAGE", "get failed with ", task.getException());
                                                }
                                            }
                                        });
                                Log.e("RECENTMESSAGE", recentMessage.getMessage());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
//        return recentMessage[0];
    }
}
