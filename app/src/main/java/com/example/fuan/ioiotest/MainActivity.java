package com.example.fuan.ioiotest;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;


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

    private SeekBar seekBarServo;
    private TextView textViewServo;


    private TextView ultrasonicsSensorText;
    private ProgressBar ultrasonicsSensorPrograss;
    private ToggleButton ultrasonicsSensorButton;

    private ToggleButton detectEdgeButton;

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, BaseService.class));

        forwardButton=(Button)findViewById(R.id.button2);
        backwardButton=(Button)findViewById(R.id.button);
        stopButton=(Button)findViewById(R.id.button3);

        seekBar_=(SeekBar)findViewById(R.id.seekBar);//电机速度调节模块

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



        addListenerToggleButtonCircle();
        addListenerToggleButtonRightMotor();
        addListenerToggleButtonLeftMotor();

        addListenSeekbar();

        ultrasonicsSensorButton = (ToggleButton) findViewById(R.id.button6);
        ultrasonicsSensorPrograss = (ProgressBar) findViewById(R.id.progressBar);
        ultrasonicsSensorText = (TextView) findViewById(R.id.textSet8);
        addListenerUltrasonicsSensor();

        addListenerDetectEdge();
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

                if (leftMotor.isChecked()) {
                    leftMotor.setText("LeftMotor前转");
                    ba.motorLeft(seekBar_.getProgress(), false);
                } else {
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
        ba.servoControl(90);
        textViewServo=(TextView)findViewById(R.id.textSet9);
        seekBarServo=(SeekBar)findViewById(R.id.seekBar2);
        seekBarServo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewServo.setText(seekBarServo.getProgress() + "度");
                ba.servoControl(seekBarServo.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 超声波测距监听按钮
     */
    public void addListenerUltrasonicsSensor(){
        ultrasonicsSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ultrasonicsSensorButton.isChecked()) {
                    updateUI();
                } else {
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
                ultrasonicsSensorText.setText(String.valueOf(BaseService.echoDistanceCm));
                ultrasonicsSensorPrograss.setProgress(BaseService.echoSeconds);
                Log.d("UI", "receive the data");
            }
        });
    }

    public void addListenerDetectEdge(){
        detectEdgeButton=(ToggleButton)findViewById(R.id.button7);
        detectEdgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detectEdgeButton.isChecked()){
                    ba.detectEdgeEnable=true;
                    detectEdgeButton.setText("边缘检测开");
                }
                else {
                    ba.detectEdgeEnable=false;
                    detectEdgeButton.setText("边缘检测关");
                }
            }
        });
    }
}




