package com.example.notificationexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MessageContentActivity extends AppCompatActivity {
    TextView txt_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);

        txt_message = findViewById(R.id.textView_message);

        String message = getIntent().getStringExtra("message");
        txt_message.setText(message);
    }
}
