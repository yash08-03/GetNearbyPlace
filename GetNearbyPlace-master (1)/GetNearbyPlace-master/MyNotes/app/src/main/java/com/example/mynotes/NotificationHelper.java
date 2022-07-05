package com.example.mynotes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
// AndroidX is the new extension libraries for backward compatibility support. In future new feature backward compatibility support will be addressed in AnddroidX.
// Android Studio can only import files which are included in your classpath. This includes Androidx libraries
import androidx.core.app.NotificationCompat;
// Returns which type of notifications in a group are responsible for audibly alerting the user
import androidx.core.app.NotificationManagerCompat;
// Returns all notification channel groups belonging to the calling app or an empty list on older SDKs which don't support Notification Channels.

import java.util.Random;
// Random class is used to generate pseudo-random numbers in java.

public class NotificationHelper extends ContextWrapper {

    private static final String TAG = "NotificationHelper";

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
//if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) is TRUE - means the device running the app has Android SDK 26 or up - and the Block of code inside you "if" statement will be executed.
// otherwise- the SDK version is lower than 26. (SDK 25 or lower)
//To avoid executing that block of code on devices older than Android 8.0. Notification channels were added in Android 8.0. Attempting to call createNotificationChannel() on older devices would result in a crash, as that method will not exist.
      }

    private String CHANNEL_NAME = "High priority channel";
    private String CHANNEL_ID = "com.example.notifications" + CHANNEL_NAME;

    @RequiresApi(api = Build.VERSION_CODES.O)
// @RequiresApi - Denotes that the annotated element should only be called on the given API level or higher.
    private void createChannels() {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("this is the description of the channel.");
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
    }

    public void sendHighPriorityNotification(String title, String body, Class activityName) {

        Intent intent = new Intent(this, activityName);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 267, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(body)
                .setSmallIcon(R.drawable.ic_beenhere_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().setSummaryText("summary").setBigContentTitle(title).bigText(body))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat.from(this).notify(new Random().nextInt(), notification);


    }

}
