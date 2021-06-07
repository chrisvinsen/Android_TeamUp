package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
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

import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.recycleviews.notification.NotificationItemAdapter;

public class NotificationController {
    private static NotificationItemAdapter notifAdapter;
    private ArrayList<Message> recentMessage;
    private static CollectionReference messageRef = FirebaseFirestore
            .getInstance().collection("MessageDetails");
    private static CollectionReference projectRef = FirebaseFirestore
            .getInstance().collection("ProjectDetails");
    private static CollectionReference projectMembers = FirebaseFirestore
            .getInstance().collection("ProjectMembers");

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final String KEY_ID = "id";
    private static final String KEY_FROM_ID = "fromId";
    private static final String KEY_GROUP_ID = "projectId";
    private static final String KEY_message = "message";
    private static final String KEY_createdAt = "createdAt";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_ATTACHMENT = "attachment";

    public static List<ProjectMember> reqMember = new ArrayList<ProjectMember>();


    private static RecyclerView recyclerView;
    private static Context contex;

    public static void setRecyclerview(RecyclerView rv, Context contex){
        recyclerView =rv;
        contex = contex;
    }

    //    public void ArrayList<Message>
//    public static void getRecentMessage(RecyclerView rv, Context context){
//        List<String> projectListId = new ArrayList<String>();
//
//        projectRef.whereArrayContains("members",UserController.getUserId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<Project> projectListId = new ArrayList<Project>();
//                if(error != null){
//                    Log.e("GETTINGDATAPROJEFCT", "error");
//                    return;
//                }
//                for(DocumentChange dc : value.getDocumentChanges()){
//
//                    Log.e("DOCUMENTCHANGE", "ADA DATA");
////                    if(dc.getType() == DocumentChange.Type.MODIFIED){
////                        projectListId.add(dc.getDocument().toObject(Project.class).getId());
////                    }
//
//                    //if it has field recentMessage
//                    if(dc.getDocument().toObject(Project.class).getRecentMessage() != null){
//                        projectListId.add(dc.getDocument().toObject(Project.class));
//                    }
//                }
//
//                //set adapter
//                notifAdapter = new NotificationItemAdapter(context,projectListId);
//
//                //render rv
//                rv.setHasFixedSize(true);
//                rv.setLayoutManager(new LinearLayoutManager(context));
//
//                //set adapter
//                rv.setAdapter(notifAdapter);
//            }
//        });
//    }

    public static void loadProjectMemberRequestNotification(Context context){
        projectMembers.whereEqualTo("adminId", mAuth.getUid()).whereEqualTo("isMember", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Utils.show(context, "Error listening to project changes");
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()) {
                            Log.e("DOCUMENTID", dc.getDocument().getId());

                            ProjectMember pm = dc.getDocument().toObject(ProjectMember.class);
                            pm.setDocumentId(dc.getDocument().getId());
                            reqMember.add(pm); //adding to the arraylist
                            Log.e("MEMBERNAME", pm.getFullName());
                            Log.e("PROJECTID", pm.getProjectId());
//                    adapter.notifyDataSetChanged();
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Log.e("NOTIF", "something added");
//                                adapter.notifyDataSetChanged();
//                                Log.e("LISTENTOPROJECTCHANGES", "added");


                            }
                            if(dc.getType() == DocumentChange.Type.MODIFIED){
                                Log.e("NOTIF", "something modified");
//                                adapter.notifyDataSetChanged();
//                                Log.e("LISTENTOPROJECTCHANGES", "modified");
                            }

                            if(dc.getType() == DocumentChange.Type.REMOVED){
//                                Log.e("OLDINDEX", String.valueOf(dc.getOldIndex())); //
//                                Log.e("NEWINDEX", String.valueOf(dc.getNewIndex()));
                                reqMember.remove(dc.getOldIndex());

                                List<String> mReq = new ArrayList<String>();
                                for(int i=0;i < reqMember.size();i++){
                                    mReq.add(reqMember.get(i).getUserId());
                                }

                                Map<String, Object> req = new HashMap<>();
                                req.put("membersRequest", reqMember);

                                //push the req member
                                FirebaseFirestore projectDetails = FirebaseFirestore.getInstance();

                                projectDetails.collection("ProjectDetails").document(pm.getProjectId()).update(req).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.e("SUCCESSUPDATE", "DocumentSnapshot successfully updated!");
                                    }
                                });


                            }
                        }
                    }
                });
    }


    public static void onDelete(ProjectMember member){
        Log.e("ONDELETEDOCUMENT", member.getDocumentId());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ProjectMembers").document(member.getDocumentId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("REMOVED",member.getDocumentId());
            }
        });

    }
}
