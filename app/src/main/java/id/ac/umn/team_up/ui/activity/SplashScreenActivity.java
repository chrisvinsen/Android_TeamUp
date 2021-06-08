package id.ac.umn.team_up.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.controllers.MessageController;
import id.ac.umn.team_up.controllers.NotificationController;
import id.ac.umn.team_up.controllers.UserController;
import id.ac.umn.team_up.models.Message;
import id.ac.umn.team_up.ui.activity.recycleviews.notification.NotificationItemAdapter;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "My Notif");
        mBuilder.setSmallIcon(R.drawable.img_logo_up);
        mBuilder.setContentTitle("Let's Team Up!");
        mBuilder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, mBuilder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notif", "My Notif", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        new Handler().postDelayed(() -> {
            MessageController.listenToRecentMessageChanges(getApplicationContext());

            Intent intent;
            if (UserController.isLogin()) {
                intent = new Intent(this.getApplicationContext(), MainActivity.class);
            } else {
                intent = new Intent(this.getApplicationContext(), WelcomeActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1000L); //1000 L = 1 second
    }
}