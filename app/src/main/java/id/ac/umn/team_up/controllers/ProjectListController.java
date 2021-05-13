 package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.models.Project;
import id.ac.umn.team_up.ui.activity.notification.NotificationItemAdapter;
import id.ac.umn.team_up.ui.activity.project.ProjectListAdapter;

public class ProjectListController {

    private static DatabaseReference db ;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static ArrayList<Project> dataProject;

    public static void getProjectList(RecyclerView rv, View view, AppCompatActivity app){
       db = FirebaseDatabase.getInstance().getReference();
       db.child("ProjectDetail").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               dataProject = new ArrayList<Project>();

               for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                   Project projects = dataSnapshot.getValue(Project.class);
                   projects.setKey(snapshot.getKey());

                   for(String id : projects.getMember()){

                       if(mAuth.getUid().equals(id)){
                           Log.d("idTest",id);
                           dataProject.add(projects);
                       }
                   }

               }

               Log.d("size data", String.valueOf(dataProject.size()));
               Log.d("recentmessage message", dataProject.get(0).getRecentMessageMessage());

                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                rv.setAdapter(new ProjectListAdapter(view.getContext(),dataProject, app));

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.d("Error","Please try again letter");
           }
       });
    }




}
