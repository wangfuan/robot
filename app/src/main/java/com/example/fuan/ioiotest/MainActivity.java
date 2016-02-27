package com.example.fuan.ioiotest;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import ioio.lib.api.Uart;
import ioio.lib.util.android.IOIOActivity;


public class MainActivity extends Activity {
    BaseService ba=new BaseService();
    private TextView textView_;
    private SeekBar seekBar_;

    private ToggleButton circle;
    private ToggleButton leftMotor;
    private ToggleButton rightMotor;

    private Button forwardButton;
    private Button backwardButton;
    private Button stopButton;

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, BaseService.class));

        forwardButton=(Button)findViewById(R.id.button2);
        backwardButton=(Button)findViewById(R.id.button);
        stopButton=(Button)findViewById(R.id.button3);

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ba.moveForward(seekBar_.getProgress());
            }
        });
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ba.moveBackward(seekBar_.getProgress());
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ba.moveStop();
                circle.setText("Circle");
                leftMotor.setText("LeftMotor");
                rightMotor.setText("RightMotor");
            }
        });

       //turn();

        addListenerToggleButtonCircle();
        addListenerToggleButtonRightMotor();
        addListenerToggleButtonLeftMotor();

        addListenSeekbar();

        //addListenDetectEdge();
    }
    @Override
    protected void onPause() {
        super.onPause();
//        startService(new Intent(this, BaseService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
//        startService(new Intent(this, BaseService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//      startService(new Intent(this, BaseService.class));
        stopService(new Intent(this, BaseService.class));
        finish();
    }

    public void addListenerToggleButtonCircle(){
        circle=(ToggleButton)findViewById(R.id.toggleButton);
        circle.setText("Circle");
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (circle.isChecked()) {
                    circle.setText("左转圈");
                    ba.turnCircle(seekBar_.getProgress(), true);
                } else {
                    circle.setText("右转圈");
                    ba.turnCircle(seekBar_.getProgress(), false);
                }
            }
        });
    }
    public void addListenerToggleButtonLeftMotor(){
        leftMotor=(ToggleButton)findViewById(R.id.toggleButton2);
        leftMotor.setText("LeftMotor");
        leftMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (leftMotor.isChecked()){
                    leftMotor.setText("LeftMotor前转");
                    ba.motorLeft(seekBar_.getProgress(), false);
                }
                else {
                    leftMotor.setText("LeftMotor后转");
                    ba.motorLeft(seekBar_.getProgress(), true);
                }
            }
        });
    }
    public void addListenerToggleButtonRightMotor(){
        rightMotor=(ToggleButton)findViewById(R.id.toggleButton3);
        rightMotor.setText("RightMotor");
        rightMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rightMotor.isChecked()){
                    rightMotor.setText("RightMotor前转");
                    ba.motorRight(seekBar_.getProgress(), false);

                }
                else {
                    rightMotor.setText("RightMotor后转");
                    ba.motorRight(seekBar_.getProgress(), true);
                }
            }
        });
    }
    public void addListenSeekbar(){
        textView_=(TextView)findViewById(R.id.textView2);
        seekBar_=(SeekBar)findViewById(R.id.seekBar);
        seekBar_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView_.setText(seekBar_.getProgress() + "%");
                ba.servoControl(seekBar_.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}




