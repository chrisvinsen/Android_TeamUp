package id.ac.umn.team_up.controllers;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.activity.LoginActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.WelcomeActivity;

public class UserController {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
    private static String userId;

    public static void register(final AppCompatActivity app, final User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                final Intent intent = new Intent(app, LoginActivity.class);
                                                app.startActivity(intent);
                                                Utils.show(app, "Registration Successfully! Log in now.");
                                            } else {
                                                Utils.show(app, "Registration Failed! Please try again.");
                                            }
                                        }
                                    });
                        } else {
                            Utils.show(app, "Registration Failedss! Please try again.");
                        }
                    }
                });
    }

    public static void login(final AppCompatActivity app, final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Utils.show(app, "Login Successfully!");
                            final Intent intent = new Intent(app, MainActivity.class);
                            app.startActivity(intent);
                        } else {
                            Utils.show(app, "Login Failed! Incorrect Email or Password.");
                        }
                    }
                });
    }

    public static void logout(final AppCompatActivity app) {
        mAuth.signOut();
        final Intent intent = new Intent(app, WelcomeActivity.class);
        app.startActivity(intent);
    }

    public static boolean isLogin() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            return true;
        }
        return false;
    }

    public static String getUserId() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user.getUid();
    }

    public static void setUserProfile(final AppCompatActivity app, TextView tvFullname) {
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        db.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User currentUser = snapshot.getValue(User.class);
                Log.d("user", currentUser.getFullName());
                tvFullname.setText(currentUser.getFullName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.show(app, "Something went wrong, please try again later.");
            }
        });
    }

    public static void insert(final AppCompatActivity app, final DatabaseReference db, final User user) {
        if (user == null) {
            Utils.showInfoDialog(app, "VALIDATION FAILED", "User is null");
        }


        db.child("User").push().setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Utils.openActivity(app, MainActivity.class);
                            Utils.show(app, "Registration Successfully");
                        } else {
                            Utils.showInfoDialog(app, "Registration Failed", task.getException().getMessage());
                        }
                    }
                });
    }

    public static void update(final AppCompatActivity app, final DatabaseReference db, final User oldUser, final User newUser) {
        if (oldUser == null) {
            Utils.showInfoDialog(app,"VALIDATION FAILED","Old data is null");
            return;
        }

        db.child("User").child(oldUser.getKey()).setValue(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Utils.show(app, oldUser.getFullName() + " Update Successful.");
                        } else {
                            Utils.showInfoDialog(app,"Update Failed", task.getException().
                                    getMessage());
                        }
                    }
                });
    }

}
