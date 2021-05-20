package id.ac.umn.team_up.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Document;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.activity.LoginActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.WelcomeActivity;

public class UserController {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String userId;

    public static void register(final AppCompatActivity app, final User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userId = getUserId();

                            db.collection("Users")
                                    .document(userId)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            final Intent intent = new Intent(app, LoginActivity.class);
                                            app.startActivity(intent);
                                            Utils.show(app, "Registration Successfully! Log in now.");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Utils.show(app, "Registration Failed! Please try again.");
                                        }
                                    });

//                            FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                final Intent intent = new Intent(app, LoginActivity.class);
//                                                app.startActivity(intent);
//                                                Utils.show(app, "Registration Successfully! Log in now.");
//                                            } else {
//                                                Utils.show(app, "Registration Failed! Please try again.");
//                                            }
//                                        }
//                                    });
                        } else {
                            Utils.show(app, "Registration Failed! Please try again.");
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
                            setCurrentUser(app);
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

        SharedPreferences sharedPref = Utils.getSharedPref(app.getApplicationContext());
        SharedPreferences.Editor preferencesEditor = sharedPref.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();

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

    public static void setCurrentUser(final AppCompatActivity app) {
        userId = getUserId();

        DocumentReference docRef = db.collection("Users").document(userId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> data = documentSnapshot.getData();
                        User currentUser = new User(data);

                        SharedPreferences sharedPref = Utils.getSharedPref(app.getApplicationContext());
                        SharedPreferences.Editor prefEditor = sharedPref.edit();
                        prefEditor.putString("uid", userId);
                        prefEditor.putString("ufirstname", currentUser.getFirstName());
                        prefEditor.putString("ulastname", currentUser.getLastName());
                        prefEditor.putString("uemail", currentUser.getEmail());
                        prefEditor.putString("upassword", currentUser.getPassword());
                        prefEditor.putString("ucreatedat", currentUser.getcreatedAt());

                        prefEditor.putString("uheadline", currentUser.getHeadline());
                        prefEditor.putString("uabout", currentUser.getAbout());
                        Gson gson = new Gson();
                        String jsonSkill = gson.toJson(currentUser.getSkills());
                        prefEditor.putString("uskills", jsonSkill);
                        prefEditor.apply();

                        Log.d("set_user", sharedPref.getString("uid", ""));
                        Log.d("set_user", sharedPref.getString("ufirstname", ""));
                        Log.d("set_user", sharedPref.getString("ulastname", ""));
                        Log.d("set_user", sharedPref.getString("uemail", ""));
                        Log.d("set_user", sharedPref.getString("upassword", ""));
                        Log.d("set_user", sharedPref.getString("ucreatedat", ""));

                        Log.d("set_user", sharedPref.getString("uheadline", ""));
                        Log.d("set_user", sharedPref.getString("uabout", ""));
                        Log.d("set_user", sharedPref.getString("uskills", ""));
                    } else {
                        Utils.show(app, "Something went wrong, please try again later.");
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Utils.show(app, "Something went wrongs, please try again later.");
                }
            });
    }

    public static User getCurrentUser(Context c) {
        SharedPreferences sharedPref = Utils.getSharedPref(c);
        String id = sharedPref.getString("uid", "");
        String firstname = sharedPref.getString("ufirstname", "");
        String last_name = sharedPref.getString("ulastname", "");
        String email = sharedPref.getString("uemail", "");
        String password = sharedPref.getString("upassword", "");
        String createdat = sharedPref.getString("ucreatedat", "");
        String headline = sharedPref.getString("uheadline", "");
        String about = sharedPref.getString("uabout", "");
        String jsonSkill = sharedPref.getString("uskills", "");
        ArrayList<String> skill;
        if (!jsonSkill.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            skill = gson.fromJson(jsonSkill, type);
        } else {
            skill = new ArrayList<String>();
        }

        User user = new User(firstname, last_name, email, password);
        user.setHeadline(headline);
        user.setAbout(about);
        user.setSkills(skill);

        Log.d("get_user", id);
        Log.d("get_user", firstname);
        Log.d("get_user", last_name);
        Log.d("get_user", email);
        Log.d("get_user", password);
        Log.d("get_user", createdat);
        Log.d("get_user", headline);
        Log.d("get_user", about);
        Log.d("get_user", jsonSkill);

        return user;
    }

    public static void updateUser(final AppCompatActivity app, User updated_user) {
        userId = getUserId();

        Log.d("update", "want to update");

        db.collection("Users")
                .document(userId)
                .set(updated_user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        setCurrentUser(app);
                        Utils.show(app, "Data changed successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.show(app, "Something went wrong, please try again later.");
                    }
                });
    }
}
