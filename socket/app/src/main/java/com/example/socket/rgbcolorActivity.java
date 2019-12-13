package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Color;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.socket.R;

public class rgbcolorActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    RelativeLayout relativeLayout;

    Switch aSwitch;

    SeekBar mySeekBar;
    SeekBar mySeekBar1;
    SeekBar mySeekBar2;

    TextView textView;

    String  redColor="00", greenColor="00", blueColor="00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgbcolor);

        this.relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        this.textView = (TextView) findViewById(R.id.textView);

        this.aSwitch = (Switch) findViewById(R.id.switch1);
        this.aSwitch.setOnCheckedChangeListener(this);

        this.mySeekBar = (SeekBar) findViewById(R.id.mySeekBar);
        this.mySeekBar1 = (SeekBar) findViewById(R.id.mySeekBar1);
        this.mySeekBar2 = (SeekBar) findViewById(R.id.mySeekBar2);

        this.mySeekBar.setOnSeekBarChangeListener(this);
        this.mySeekBar1.setOnSeekBarChangeListener(this);
        this.mySeekBar2.setOnSeekBarChangeListener(this);

        this.mySeekBar.setMax(100);
        this.mySeekBar1.setMax(100);
        this.mySeekBar2.setMax(100);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d("Test", "" + b);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar.getId() == R.id.mySeekBar) {
            this.redColor = String.format("%02x", (int) ((double) i / 100 * 255));
            Log.d("Test", "Red Changed");
        } else if (seekBar.getId() == R.id.mySeekBar1) {
            this.greenColor = String.format("%02x", (int) ((double) i / 100 * 255));
            Log.d("Test", "Green Changed");
        } else {
            this.blueColor = String.format("%02x", (int) ((double) i / 100 * 255));
            Log.d("Test", "Blue Changed");
        }

        // Change background color
        relativeLayout.setBackgroundColor(Color.parseColor("#"+this.redColor+this.greenColor+this.blueColor));

        // Color Code display
        this.textView.setText("Color Code (RGB): " + "#" + this.redColor + this.greenColor + this.blueColor);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
//        Log.d("Test", "" + seekBar);

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
//        Log.d("Test", "" + seekBar);
    }
}
