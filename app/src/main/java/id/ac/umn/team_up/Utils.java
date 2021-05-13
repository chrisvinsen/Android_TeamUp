package id.ac.umn.team_up;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.ac.umn.team_up.models.User;

public class Utils {
    public static void show(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean validate(EditText editText, String error_message  ){
        if(editText.getText() == null || editText.getText().toString().isEmpty()){
            editText.setError(error_message);
            return false;
        }
        return true;
    }

    public static void openActivity(Context c, Class clazz) {
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    public static void showInfoDialog(final AppCompatActivity activity, String title, String message) {
        new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
            .setTopColorRes(R.color.orange)
            .setButtonsColorRes(R.color.orange)
            .setIcon(R.drawable.ic_home)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Relax", v -> {})
//            .setNeutralButton("Go Home", v -> openActivity(activity, DashboardActivity.class))
            .setNegativeButton("Go Back", v -> activity.finish())
            .show();
    }

    public static Date giveMeDate(String stringDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
            return sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    public static void sendUserToActivity(Context c, User user, Class clazz) {
        Intent intent = new Intent(c, clazz);
        intent.putExtra("USER_KEY", user);
        c.startActivity(intent);
    }

    public static User receiveUser(Intent intent, Context c) {
        try {
            return (User) intent.getSerializableExtra("USER_KEY");
        } catch (Exception e) {
            e.printStackTrace();
            show(c, "RECEIVING USER ERROR: " + e.getMessage());
        }
        return null;
    }

    public static void showProgressBar(ProgressBar pb) {
        pb.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar pb) {
        pb.setVisibility(View.GONE);
    }

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
