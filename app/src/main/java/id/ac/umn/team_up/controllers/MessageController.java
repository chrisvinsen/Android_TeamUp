package id.ac.umn.team_up.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.recycleviews.message.MessageListAdapter;

public class MessageController {

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

    private static boolean statusSending = false;

    //sendmessage
    public static void sentMessage(String projectId, String fullname,String userId, String msg, String attachment, Context context){
        FieldValue createdAt = FieldValue.serverTimestamp();
        Date currDate = new Date(System.currentTimeMillis());

        SharedPreferences sharedPref = Utils.getSharedPref(context);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putLong("createdAt", currDate.getTime()).apply();
        Log.e("date", String.valueOf(currDate.getTime()));

        Map<String, Object> message = new HashMap<>();

        //time id
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
    public static void getMessage(RecyclerView rv, View v, String userId, String projectId, Context context){
        ArrayList<Message> messageList = new ArrayList<Message>();
        Log.d("GroupID", projectId);

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
                    Log.e("MESSAGEREF","error");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        Message message = dc.getDocument().toObject(Message.class);
                        Log.e("CREATEDAT", String.valueOf(message.getCreatedAt()));
                        if(message.getCreatedAt() == null) {
                            SharedPreferences sharedPref = Utils.getSharedPref(context);
                            Date date = new Date(sharedPref.getLong("createdAt", 0));
                            Log.e("msg_sent", Utils.dateToString(date));
                            message.setCreatedAt(date);
                            messageList.add(message);
                        } else{
                            messageList.add(message);
                        }
                    }
                    mMessageAdapter.notifyDataSetChanged();
                    linearLayoutManager.scrollToPosition(messageList.size()-1);
                }

            }
        });


    }


    public static void listenToRecentMessageChanges(Context c){
        messagesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("listenToRecentMsgChng", "Error getting recent message");
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
                                                        project.setRecentMessageSender(recentMessage.getFullName());
                                                        Log.d("GETRECENTMESSAGE", recentMessage.getMessage());
                                                        Log.d("GETRECENTMESSAGESENDER", recentMessage.getFullName());
                                                        if(recentMessage.getCreatedAt() != null){
                                                            project.setSentAt(recentMessage.getCreatedAt());
                                                        } else {
                                                            messagesRef.document(recentMessage.getId()).get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if(task.isSuccessful()){
                                                                                DocumentSnapshot ds = task.getResult();
                                                                                if(ds.exists()){
                                                                                    Message m = ds.toObject(Message.class);
                                                                                    Log.e("NULLCREATEDATHANDLER", String.valueOf(m.getCreatedAt()));
                                                                                    ProjectController.updateProjectRecentMessage(m, projectRef);
                                                                                }
                                                                            }
                                                                            else {
                                                                                Log.e("NULLCREATEDATHANDLER", "failed");
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                        project.setSentAt(recentMessage.getCreatedAt());
                                                        Log.d("GETRECENTMESSAGE", String.valueOf(recentMessage.getCreatedAt()));
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
                                Log.e("RECENTMESSAGE", recentMessage.getMessage());
                                Log.e("RECENTMESSAGE", String.valueOf(recentMessage.getCreatedAt()));
//                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}
