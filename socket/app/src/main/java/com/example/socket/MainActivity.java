package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URISyntaxException;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.socket.App.CHANNEL_1_ID;
import static com.example.socket.App.CHANNEL_2_ID;



public class MainActivity extends AppCompatActivity {
    private EditText editText, editText_receice;
    private Button btn_send, btn_rgbcolor;
    private Socket mSocket;
    private NotificationManagerCompat notificationManagerCompat;

    {
        try {
            mSocket = IO.socket("http://192.168.1.5:3000/");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        mSocket.connect();

        msocket_ON();
        msocket_Emit();
        change_Color();
    }

    /**------------------------------------------------------------------
     * ------------------------------------------------------------------
     * ------------------------------------------------------------------*/

    // Hàm ánh xạ từ File Xml
    private void initView() {
        editText = findViewById(R.id.edt_send);
        btn_send = findViewById(R.id.btn_send);
        btn_rgbcolor = findViewById(R.id.btn_rgbcolor);
        editText_receice = findViewById(R.id.edt_receice);
    }

    private void msocket_Emit() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                editText.setText("");
                if(message.equals("notify"))
                    mSocket.emit("turn on notify lock screen", message);
                else if(message.equals("notify 1"))
                    mSocket.emit("turn on notify 1", message);
                else if(message.equals("notify 2"))
                    mSocket.emit("turn on notify 2", message);
                else
                    mSocket.emit("message",message);
            }
        });
    }

    private void msocket_ON() {
        mSocket.on("e_Notification_screen", o_Notification_screen);
        mSocket.on("e_Notification1", o_Notification1);
        mSocket.on("e_Notification2", o_Notification2);
        mSocket.on("e_message", o_message);
    }



    private void change_Color() {
        btn_rgbcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, rgbcolorActivity.class);
                startActivity(intent);
            }
        });
    }



    /** EMIT  **/
    private Emitter.Listener o_Notification_screen = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        Log.d("test","123");
                        noidung = data.getString("noidung");
                        if(noidung.equals("notify")) {
                            lock_screen_notification();
                        }
                        editText_receice.setText(noidung);
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener o_Notification1 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("noidung");
                        if(noidung.equals("notify1")) {
                                notification1();
                            }
                        editText_receice.setText(noidung);
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener o_Notification2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("noidung");
                        if(noidung.equals("notify2")) {
                            notification2();
                        }
                        editText_receice.setText(noidung);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    private Emitter.Listener o_message = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("noidung");
                        editText_receice.setText(noidung);
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    public void notification1() {
        String title = "Title 1";
        String message = "Message 1";

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

    public void notification2() {
        String title = "Title 2";
        String message = "Message 2";

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

    public void lock_screen_notification() {
        String message = "This is a message notification example";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("WARNING in lock screen!!!")
                .setContentText("content text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
