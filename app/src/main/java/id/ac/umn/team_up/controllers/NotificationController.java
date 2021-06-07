package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;
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

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.models.ProjectMember;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.recycleviews.notification.NotificationAddMember;
import id.ac.umn.team_up.ui.activity.recycleviews.notification.NotificationItemAdapter;
import id.ac.umn.team_up.ui.activity.recycleviews.project.ProjectListAdapter;

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
    public static List<Project> projects = new ArrayList<Project>();


    private static RecyclerView recyclerView;
    private static Context contex;
    private static NotificationItemAdapter madapter;

    public static void setRecyclerview(RecyclerView rv, Context contex, NotificationItemAdapter adapter){
        madapter = adapter;
        recyclerView = rv;
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
        Log.e("STARTMEMBERREQ", "TRUE");
        Log.e("USERIDNOTIF", mAuth.getUid());
        projectMembers.whereEqualTo("adminId", mAuth.getUid()).whereEqualTo("isMember", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        //reqMember = new ArrayList<ProjectMember>();
                        if(error != null){
                            Utils.show(context, "Error listening to project changes");
                            return;
                        }
                        Log.e("Data","TRUE");

                            for(DocumentChange dc: value.getDocumentChanges()) {

                                Log.e("DOCUMENTID", dc.getDocument().getId());

                                ProjectMember pm = dc.getDocument().toObject(ProjectMember.class);
                                pm.setDocumentId(dc.getDocument().getId());
                                //Log.e("IFMEMBERISINARRAYLIST", String.valueOf(reqMember.contains(pm)));

//                            if(dc.getOldIndex() != -1){
//                                //get the old index
//                                Log.e("OLDINDEX", String.valueOf(dc.getOldIndex()));
//                                //check if is the same in old index
//                                Log.e("SAMEWITHOLD", String.valueOf(reqMember.get(dc.getOldIndex()).getFullName().equals(pm.getFullName())));
//
//                                //if true, we don't want to include because it's the same perventing double item in recyclerview
//                                if(!reqMember.get(dc.getOldIndex()).getFullName().equals(pm.getFullName())){
//                                    reqMember.add(pm);
//                                }
//                            }
//
                                Log.d("DOCUMENTTYPE", dc.getType().toString());
                                Log.e("OLDINDEX", String.valueOf(dc.getOldIndex()));
                                // not new item and not equal to the prev index in reqMember
//                                if(dc.getOldIndex() == -1 ) {
//                                    reqMember.add(pm);
//                                    Log.e("SIZEREQMEMBER", String.valueOf(reqMember.size()));
//
//                                    if(madapter != null){
//                                        madapter.notifyDataSetChanged();
//                                    }
//                                }
//                                else if(!reqMember.get(dc.getOldIndex()).getFullName().equals(pm.getFullName()) && (dc.getType() == DocumentChange.Type.ADDED) ){
//                                    reqMember.add(pm);
//                                    Log.e("SIZEREQMEMBER", String.valueOf(reqMember.size()));
//
//                                    if(madapter != null){
//                                        madapter.notifyDataSetChanged();
//                                    }
//
//
//                                }

                                if(dc.getType() == DocumentChange.Type.ADDED  ){
                                    reqMember.add(pm);
                                    Log.e("SIZEREQMEMBER", String.valueOf(reqMember.size()));

                                    if(madapter != null){
                                        madapter.notifyDataSetChanged();
                                    }
                                }




                                //adding to the arraylist


                                Log.e("MEMBERNAME", pm.getFullName());
                                Log.e("PROJECTID", pm.getProjectId());
//                    adapter.notifyDataSetChanged();
//                            if (dc.getType() == DocumentChange.Type.ADDED) {
//                                Log.e("NOTIF", "something added");
////                                adapter.notifyDataSetChanged();
////                                Log.e("LISTENTOPROJECTCHANGES", "added");
//                                reqMember.add(pm);
//                                madapter.notifyDataSetChanged();
//
//                            }//end of added
                                if(dc.getType() == DocumentChange.Type.MODIFIED){
                                    Log.e("NOTIF", "something modified");
//                                adapter.notifyDataSetChanged();
//                                Log.e("LISTENTOPROJECTCHANGES", "modified");

                                    String projectId = reqMember.get(dc.getOldIndex()).getProjectId();
                                    reqMember.remove(dc.getOldIndex());

                                    if(madapter != null){
                                        madapter.notifyDataSetChanged();
                                    }

                                    //updateFieldReqMember(reqMember,projectId);





                                }//end of modified

                                else if(dc.getType() == DocumentChange.Type.REMOVED){
//                                Log.e("OLDINDEX", String.valueOf(dc.getOldIndex())); //to get the index
//                                Log.e("NEWINDEX", String.valueOf(dc.getNewIndex()));
                                    String projectId = reqMember.get(dc.getOldIndex()).getProjectId();

                                    reqMember.remove(dc.getOldIndex());

                                    if(madapter != null){
                                        madapter.notifyDataSetChanged();
                                    }

                                    updateFieldReqMember(reqMember,projectId);
                                    Log.e("NOTIFYADAPTER","TRUE REMOVED");
                                    Log.e("ADAPTERSIZE", String.valueOf(reqMember.size()));


                                }//end of removed

                            }//end for
                        }

                });
    }


    public static void onDelete(ProjectMember member, Integer position){
        Log.e("ONDELETEDOCUMENT", member.getDocumentId());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ProjectMembers").document(member.getDocumentId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Log.d("REMOVED",member.getDocumentId());

                }
            }
        });

    }

    public static void onAccept(ProjectMember member, Integer position){
        member.setMember(true);
        //Log.e("ACCEPTDOCUMENTID",member.getDocumentId());

        Map<String,Object> req = new HashMap<>();
        req.put("isMember", member.isMember());
        FirebaseFirestore.getInstance().collection("ProjectMembers").document(member.getDocumentId()).update(req).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) { //ON SUCCESS ADDING ISMEMBER TRUE
                Log.d("UPDATEISMEMBER","SUCESS");

                FirebaseFirestore.getInstance().collection("ProjectDetails").document(member.getProjectId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) { //ON SUCCESS GET PROJECT MEMBER
                        if(documentSnapshot.exists()){
                            List<String> members = documentSnapshot.toObject(Project.class).getMembers();
                            Log.e("ARRAYMEMBER",members.toString());

                            members.add(member.getUserId());// add member
                            Map<String, Object> req = new HashMap<>();
                            req.put("members", members);

                            FirebaseFirestore.getInstance().collection("ProjectDetails").document(member.getProjectId()).update(req).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) { //ON SUCCESS UPDATE PROJECT MEMBER
                                    Log.e("SUCCESSUPDATEMEMBER", "DocumentSnapshot successfully updated!");
                                }
                            });

                        }

                    }
                });

            }
        });

        //taking the members field and document ID


        //remove members requests

    }

    public static void updateFieldReqMember(List<ProjectMember> member,String projectId){ //this function remove the current request user from the memberRequest Field
        List<String> mReq = new ArrayList<String>();
        for(int i=0;i < member.size();i++){
            mReq.add(member.get(i).getUserId());
        }

        Map<String, Object> req = new HashMap<>();
        req.put("membersRequest", mReq);

        //push the req member
        FirebaseFirestore projectDetails = FirebaseFirestore.getInstance();

        projectDetails.collection("ProjectDetails").document(projectId).update(req).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("SUCCESSUPDATE", "DocumentSnapshot successfully updated!");
            }
        });
    }


    public static void getProject(Context context){
        FirebaseFirestore.getInstance().collection("ProjectDetails").whereEqualTo("adminId", mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Utils.show(context, "Error listening to project changes");
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    Project p = dc.getDocument().toObject(Project.class);
                    projects.add(p);
                    Log.e("PROJECTADMIN", String.valueOf(projects.size()));
                }
            }
        });
    }



}


