package id.ac.umn.team_up.controllers;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import id.ac.umn.team_up.models.Project;

public class ProjectListController {
    private static FirebaseAuth auth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference projectsRef = db.collection("Projects");

    private static List<Project> listOfProject;

    public static void loadUsersProjects(){
//        projectsRef.whereEqualTo()
    }

}
