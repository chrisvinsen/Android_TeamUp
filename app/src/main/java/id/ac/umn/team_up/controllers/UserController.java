package id.ac.umn.team_up.controllers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.Utils;
import id.ac.umn.team_up.models.User;
import id.ac.umn.team_up.ui.activity.LoginActivity;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.SplashScreenActivity;
import id.ac.umn.team_up.ui.activity.WelcomeActivity;

public class UserController {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String userId;
    private static StorageReference storageRef = FirebaseStorage.getInstance().getReference("profile");
    private static CollectionReference memberRef = FirebaseFirestore.getInstance().collection("ProjectMembers");
    private static CollectionReference projectRef = FirebaseFirestore.getInstance().collection("ProjectDetails");

    public static void register(final AppCompatActivity app, final User user, String password) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
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
                                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(app.getApplicationContext(), "My Notif");
                                            mBuilder.setSmallIcon(R.drawable.img_logo_up);
                                            mBuilder.setContentTitle("Welcome to the club " + user.getFirstName() + "!");
                                            mBuilder.setContentText("Let's login and find your partner now!");
                                            mBuilder.setAutoCancel(true);

                                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(app.getApplicationContext());
                                            managerCompat.notify(1, mBuilder.build());

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                NotificationChannel channel = new NotificationChannel("My Notif", "My Notif", NotificationManager.IMPORTANCE_DEFAULT);
                                                NotificationManager manager = app.getSystemService(NotificationManager.class);
                                                manager.createNotificationChannel(channel);
                                            }

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
                            setCurrentUser(app,true);

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(app.getApplicationContext(), "My Notif");
                            mBuilder.setSmallIcon(R.drawable.img_logo_up);
                            mBuilder.setContentTitle("Welcome to Team Up!");
                            mBuilder.setContentText("Let's find your partner now!");
                            mBuilder.setAutoCancel(true);

                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(app.getApplicationContext());
                            managerCompat.notify(1, mBuilder.build());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel("My Notif", "My Notif", NotificationManager.IMPORTANCE_DEFAULT);
                                NotificationManager manager = app.getSystemService(NotificationManager.class);
                                manager.createNotificationChannel(channel);
                            }

                            final Intent intentt = new Intent(app, MainActivity.class);
                            app.startActivity(intentt);
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

            Utils.show(app, "Successfully logout");

        final Intent intent = new Intent(app, WelcomeActivity.class);
        app.startActivity(intent);

    }

    public static void changePassword(final AppCompatActivity app, String oldPassword, String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        user.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Utils.show(app, "Password successfully updated");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Utils.show(app, "Something went wrong, please try again later.");
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.show(app, "Your password is wrong.");
                    }
                });
    }

    public static void resetPassword(final AppCompatActivity app, String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Utils.show(app, "Reset password has been sent to your email");
                        }
                    }
                });
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

    public static void setCurrentUser(final AppCompatActivity app, boolean afterLogin) {
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
                        prefEditor.putString("ucreatedat", currentUser.getcreatedAt());

                        prefEditor.putString("uheadline", currentUser.getHeadline());
                        prefEditor.putString("uabout", currentUser.getAbout());
                        prefEditor.putString("upicture", currentUser.getPicture());
                        prefEditor.putString("ulocalpicture", currentUser.getLocalPicture());
                        Gson gson = new Gson();
                        String jsonSkill = gson.toJson(currentUser.getSkills());
                        prefEditor.putString("uskills", jsonSkill);
                        prefEditor.apply();

                        if (afterLogin) {
                            checkAvailabilityProfileImageOnInternalStorage(app, currentUser);
                        }
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
        String createdat = sharedPref.getString("ucreatedat", "");
        String headline = sharedPref.getString("uheadline", "");
        String about = sharedPref.getString("uabout", "");
        String picture = sharedPref.getString("upicture", "");
        String localPicture = sharedPref.getString("ulocalpicture", "");
        String jsonSkill = sharedPref.getString("uskills", "");
        ArrayList<String> skill;
        if (!jsonSkill.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            skill = gson.fromJson(jsonSkill, type);
        } else {
            skill = new ArrayList<String>();
        }

        User user = new User(firstname, last_name, email);
        user.setHeadline(headline);
        user.setAbout(about);
        user.setPicture(picture);
        user.setLocalPicture(localPicture);
        user.setSkills(skill);

        return user;
    }

    public static void updateUser(final AppCompatActivity app, User updated_user, boolean notifyWhenDone) {
        userId = getUserId();

        db.collection("Users")
                .document(userId)
                .set(updated_user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        setCurrentUser(app, false);
                        if (notifyWhenDone) {
                            Utils.show(app, "Data changed successfully.");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.show(app, "Something went wrong, please try again later.");
                    }
                });
    }

    public static void updateProfileImage(final AppCompatActivity app, User currentUser, Uri imageUri, String filename) {
        StorageReference fileReference = storageRef.child(filename);

        // Put image into FirebaseStorage
        fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get download url
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                currentUser.setPicture(uri.toString());
                                updateUser(app, currentUser, false);

                                // Update project member image
                                memberRef.whereEqualTo("userId",getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                memberRef.document(document.getId()).update("picture", uri.toString());
                                                Log.e("TAG", document.getId() + " => " + document.getData());
                                            }
                                        } else {
                                            Log.e("TAG", "Error getting documents.", task.getException());
                                        }
                                    }
                                });

                                // Update project details image
                                projectRef.whereEqualTo("adminId",getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                projectRef.document(document.getId()).update("adminPicture", uri.toString());
                                                Log.e("TAG", document.getId() + " => " + document.getData());
                                            }
                                        } else {
                                            Log.e("TAG", "Error getting documents.", task.getException());
                                        }
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.show(app, e.getMessage());
                    }
                });
    }


    public static void checkAvailabilityProfileImageOnInternalStorage(final AppCompatActivity app, User currentUser) {
        Picasso.get().load(currentUser.getPicture()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    ContextWrapper cw = new ContextWrapper(app);
                    String filename = currentUser.getLocalPicture().split("/data/user/0/id.ac.umn.team_up/app_image_profile/")[1];
                    File directory = cw.getDir("image_profile", Context.MODE_PRIVATE);
                    File file = new File(directory, filename);
                    if (!file.exists()) {
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

}
