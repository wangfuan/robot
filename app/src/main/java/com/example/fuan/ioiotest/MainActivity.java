package com.example.fuan.ioiotest;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        forwardButton=(Button)findViewById(R.id.button2);
        backwardButton=(Button)findViewById(R.id.button);
        stopButton=(Button)findViewById(R.id.button3);

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

        //addListenDetectEdge();
    }

    public void addListenerToggleButtonCircle(){
        circle=(ToggleButton)findViewById(R.id.toggleButton);
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
    public void addListenerToggleButtonLeftMotor(){
        leftMotor=(ToggleButton)findViewById(R.id.toggleButton2);
        leftMotor.setText("LeftMotor");
        leftMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (leftMotor.isChecked()){
                    leftMotor.setText("LeftMotor前转");
                    motorLeft(seekBar_.getProgress(), false);
                }
                else {
                    leftMotor.setText("LeftMotor后转");
                    motorLeft(seekBar_.getProgress(), true);
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
                    motorRight(seekBar_.getProgress(), false);

                }
                else {
                    rightMotor.setText("RightMotor后转");
                    motorRight(seekBar_.getProgress(), true);
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
                steeringEngine(seekBar_.getProgress());

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




