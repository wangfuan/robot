package com.example.fuan.ioiotest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends BaseActivity {
    private TextView textView_;
    private SeekBar seekBar_;

    private ToggleButton circle;
    private ToggleButton leftMotor;
    private ToggleButton rightMotor;

    private Button forwardButton;
    private Button backwardButton;
    private Button stopButton;

    private TextView ultrasonicsSensorText;
    private ProgressBar ultrasonicsSensorPrograss;
    private ToggleButton ultrasonicsSensorButton;

    private volatile Boolean flag = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        forwardButton = (Button) findViewById(R.id.button2);
        backwardButton = (Button) findViewById(R.id.button);
        stopButton = (Button) findViewById(R.id.button3);


        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveForward(seekBar_.getProgress());
            }
        });
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveBackward(seekBar_.getProgress());
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveStop();
                circle.setText("Circle");
                leftMotor.setText("LeftMotor");
                rightMotor.setText("RightMotor");
            }
        });


        addListenerToggleButtonCircle();
        addListenerToggleButtonRightMotor();
        addListenerToggleButtonLeftMotor();

        addListenSeekbar();

        ultrasonicsSensorButton = (ToggleButton) findViewById(R.id.button6);
        ultrasonicsSensorPrograss = (ProgressBar) findViewById(R.id.progressBar);
        ultrasonicsSensorText = (TextView) findViewById(R.id.textSet8);
        addListenerUltrasonicsSensor();

        Log.d("Ultrasonic", "the final output");

        //addListenDetectEdge();
    }

    /**
     * 电机转圈控制按钮
     */
    public void addListenerToggleButtonCircle() {
        circle = (ToggleButton) findViewById(R.id.toggleButton);
        circle.setText("Circle");
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (circle.isChecked()) {
                    circle.setText("左转圈");
                    turnCircle(seekBar_.getProgress(), true);
                } else {
                    circle.setText("右转圈");
                    turnCircle(seekBar_.getProgress(), false);
                }
            }
        });
    }

    /**
     * 左电机控制按钮
     */
    public void addListenerToggleButtonLeftMotor() {
        leftMotor = (ToggleButton) findViewById(R.id.toggleButton2);
        leftMotor.setText("LeftMotor");
        leftMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (leftMotor.isChecked()) {
                    leftMotor.setText("LeftMotor前转");
                    motorLeft(seekBar_.getProgress(), false);
                } else {
                    leftMotor.setText("LeftMotor后转");
                    motorLeft(seekBar_.getProgress(), true);
                }
            }
        });
    }

    /**
     * 右电机控制按钮
     */
    public void addListenerToggleButtonRightMotor() {
        rightMotor = (ToggleButton) findViewById(R.id.toggleButton3);
        rightMotor.setText("RightMotor");
        rightMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rightMotor.isChecked()) {
                    rightMotor.setText("RightMotor前转");
                    motorRight(seekBar_.getProgress(), false);

                } else {
                    rightMotor.setText("RightMotor后转");
                    motorRight(seekBar_.getProgress(), true);
                }
            }
        });
    }

    /**
     * 电机速度和舵机角度设置进度条
     */
    public void addListenSeekbar() {
        textView_ = (TextView) findViewById(R.id.textView2);
        seekBar_ = (SeekBar) findViewById(R.id.seekBar);
        seekBar_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView_.setText(seekBar_.getProgress() + "%");
                servoControl(seekBar_.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    };

    /**
     * 超声波测距监听按钮
     */
    public void addListenerUltrasonicsSensor(){
        ultrasonicsSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ultrasonicsSensorButton.isChecked()) {
                    updateUI();
                }
                else{
                    updateUI();
                }
            }
        });
    }

    /**
     * 用于接收超声波测距的数据并更新UI界面
     */
    public void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ultrasonicsSensorText.setText(String.valueOf(echoDistanceCm));
                ultrasonicsSensorPrograss.setProgress(echoSeconds);
                Log.d("UI","receive the data");
            }
        });
    }

}





