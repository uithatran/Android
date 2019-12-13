package com.example.notificationexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.notificationexample.App.CHANNEL_1_ID;
import static com.example.notificationexample.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    NotificationManagerCompat notificationManagerCompat;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat =NotificationManagerCompat.from(this);

        // Thông báo sẽ được hiển thị ngay khi khởi động app
        notification_appear();

        editTextMessage = findViewById(R.id.edit_text_message);
        editTextTitle = findViewById(R.id.edit_text_title);

    }

    public void sendOnChannel1(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentText(message)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(1,notification);

    }

    public void sendOnChannel2(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentText(message)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(2,notification);

    }

    public void notification_appear() {
        String message = "This is a message notification example";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("WARNING Mushroom!!!")
                .setContentText("Chỉ số CO2 trong nhà nấm đang vượt quá mức quy định.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        Intent intent = new Intent(MainActivity.this, MessageContentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

}
