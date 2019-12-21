package com.example.androidfcmkilled.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.androidfcmkilled.Main2Activity;
import com.example.androidfcmkilled.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData() != null)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                sentNotification(remoteMessage);
        }
    }

    private void sentNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String content = data.get("content");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "notificationtest";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            //Only active on Android O or higher because it need Notification Channel
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "THAIHA Notification", NotificationManager.IMPORTANCE_MAX);

            //configure the notification channel
            notificationChannel.setDescription("Channel for app test FCM");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[] {0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("message", message);  //To use create value "message" to transmit
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setSmallIcon(R.drawable.ic_warning)
                .setColor(ContextCompat.getColor(MyFirebaseMessagingService.this, R.color.colorPrimary))
                .setTicker("Hearty365")
                .setContentTitle(title)
                .setContentText(content)
                .setContentInfo("info")
                .setContentIntent(pendingIntent);
        notificationManager.notify(1, notificationBuilder.build());

    }
}
