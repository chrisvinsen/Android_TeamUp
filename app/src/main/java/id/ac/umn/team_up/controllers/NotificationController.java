package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import id.ac.umn.team_up.ui.activity.recycleviews.notification.NotificationItemAdapter;

public class NotificationController {
    private static NotificationItemAdapter notifAdapter;
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
    public static void getRecentMessage(RecyclerView rv, Context context){
        List<String> projectListId = new ArrayList<String>();

        projectRef.whereArrayContains("members",UserController.getUserId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Project> projectListId = new ArrayList<Project>();
                if(error != null){
                    Log.e("GETTINGDATAPROJEFCT", "error");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){

                    Log.e("DOCUMENTCHANGE", "ADA DATA");
//                    if(dc.getType() == DocumentChange.Type.MODIFIED){
//                        projectListId.add(dc.getDocument().toObject(Project.class).getId());
//                    }
                    projectListId.add(dc.getDocument().toObject(Project.class));
                }

                //set adapter
                notifAdapter = new NotificationItemAdapter(context,projectListId);

                //render rv
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(context));

                //set adapter
                rv.setAdapter(notifAdapter);
            }
        });




    }
}
