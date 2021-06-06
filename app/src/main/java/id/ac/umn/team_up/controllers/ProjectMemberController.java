package id.ac.umn.team_up.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProjectMemberController {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db_firestore = FirebaseFirestore.getInstance();
    private static CollectionReference projectMembers = db_firestore.collection("ProjectMembers");

//    public static

}
