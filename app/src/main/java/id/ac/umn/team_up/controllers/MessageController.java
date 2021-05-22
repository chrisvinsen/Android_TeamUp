package id.ac.umn.team_up.controllers;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class MessageController {

    private String attachment, fistName, fromId, groupId, message;
    private Timestamp createAt;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference messagesRef = db.collection("MessageDetails");


    private static final String KEY_ID = "id";
    private static final String KEY_FROM_ID = "fromId";
    private static final String KEY_GROUP_ID = "groupId";
    private static final String KEY_message = "message";
    private static final String KEY_createdAt = "createdAt";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_ATTACHMENT = "attachment";



    //argument group id
    public void getMessage(String GroupID){


    }

    //sendmessage
    public static void sentMessage(String GroupID,String userId, String msg, String attachment){
        FieldValue createdAt = FieldValue.serverTimestamp();
        Map<String, Object> message = new HashMap<>();

        //random id
        String document_id = messagesRef.document().getId();


        message.put(KEY_ID, document_id);
        message.put(KEY_FROM_ID, userId);
        message.put(KEY_createdAt, createdAt);
        message.put(KEY_GROUP_ID, GroupID);
        message.put(KEY_message, msg);
        message.put(KEY_ATTACHMENT, attachment);

        messagesRef.document(document_id).set(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("SUCCESS","sending message sucess");
                    }
                });
    }



}
