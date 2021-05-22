package id.ac.umn.team_up.controllers;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;

public class MessageController {

    private String attachment, fistName, fromId, groupId, message;
    private Timestamp createAt;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference messagesRef = db.collection("Message");




    //argument group id
    public void getMessage(String GroupID){


    }

    //sendmessage
    public static void sentMessage(String GroupID,String userId, String message){
        FieldValue createdAt = FieldValue.serverTimestamp();
        Long t = Long.parseLong(createdAt.toString());

        
        Log.d("MESSAGE", message);


    }



}
